package test.java.api.test.rfps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.io.Resources;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test.java.api.helpers.ApiHelper;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import static test.java.api.testData.TestData.*;

@RunWith(value = Parameterized.class)
public class RfpVirginCreationTest extends ApiHelper {

    private String fileName;
    private String clientName;
    private String rfpFile;

    String timeStamp = new DateTime().toString("yyMMddHHmmss");

    public RfpVirginCreationTest(String fileName, String rfpFile, String clientName) {
        this.fileName = fileName;
        this.rfpFile = rfpFile;
        this.clientName = clientName;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"data/clientMedical.json", "data/rfpMedicalCreateRequest.json", "TestClientMedical"},
                {"data/clientDental.json", "data/rfpDentalCreateRequest.json", "TestClientDental"},
                {"data/clientVision.json", "data/rfpVisionCreateRequest.json", "TestClientVision"}
        };
        return Arrays.asList(data);
    }

//    private String accessToken;
//
//    @Before
//    public void getAuth0Token() throws UnsupportedEncodingException {
//        accessToken = getToken(brokerageId, authId, brokerRole, role, carrierAcl);
//    }


    @Test
    public void createRfpVirginTrueTest() throws Throwable {

        boolean virginState = true;
        String testClientName = clientName + " Virgin " + timeStamp;

        ObjectNode myJson = prepareJson();

        addClientMedicalVirginValue(myJson, "rfpProducts","virginGroup", virginState);
        addValueIntoMainNode(myJson, "clientName", testClientName);

        int clientId = sendPostRequestForCreateClient(myJson.toString(), API_URL, accessToken);
        apiCheck.confirmRequestWithStatus(CODE_201);

        int rfpId = createRfpFromFile(rfpFile, accessToken, clientId);
        apiCheck.confirmRequestWithStatus(CODE_201);

        sendPostRequestSubmitRrfpByClientIdRfpId(null, accessToken, API_URL, clientId, rfpId);
        apiCheck.confirmRequestWithStatus(CODE_200);

    }

    private ObjectNode prepareJson() throws java.io.IOException {
        URL file = Resources.getResource(fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, ObjectNode.class);
    }
}
