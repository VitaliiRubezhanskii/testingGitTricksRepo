package test.java.api.test.client;

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
public class ClientNonVirginCreationTest extends ApiHelper {

    private String fileName;
    private String clientName;

    String timeStamp = new DateTime().toString("yyMMddHHmmss");

    public ClientNonVirginCreationTest(String fileName, String clientName) {
        this.fileName = fileName;
        this.clientName = clientName;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"data/clientMedicalVirginFalse.json", "TestClientMedicalNonVirgin"},
//                {"data/clientDental.json", "TestClientDental"},
//                {"data/clientVision.json", "TestClientVision"}
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
    public void createClientVirginFalseTest() throws Throwable {
        boolean virginState = false;
        String testClientName = clientName + " NonVirgin " + timeStamp;

        ObjectNode myJson = prepareJson();
        addClientMedicalVirginValue(myJson, "rfpProducts","virginGroup", virginState);
//        JsonModifier<String> jsonModifier = new JsonModifier<>(myJson);
//        jsonModifier.updateValueInJsonArray("rfpProducts", "", 0, "NewClient");


        addValueIntoMainNode(myJson, "clientName", testClientName);
//        jsonModifier.updateValueInJson("clientName", "NewClient");

        //createClientAndRetrieveId(fileName, clientName, accessToken);
        sendPostRequestForCreateClient(myJson.toString(), API_URL, accessToken);

        apiCheck.confirmRequestWithStatus(CODE_201);
        apiCheck.clientNameCheck(testClientName);
        apiCheck.virginStateCheck(virginState);
    }

    private ObjectNode prepareJson() throws java.io.IOException {
        URL file = Resources.getResource(fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, ObjectNode.class);
    }

}

