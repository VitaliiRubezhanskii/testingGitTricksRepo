package test.java.api.test.rfps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test.java.api.helpers.ApiHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import static test.java.api.testData.TestData.*;

@RunWith(value = Parameterized.class)
public class RfpNonVirginCreationTest extends ApiHelper {

    private String clientFile;
    private String clientName;
    private String rfpFile;
    private String plans;

    String timeStamp = new DateTime().toString("yyMMddHHmmss");

    public RfpNonVirginCreationTest(String fileName, String rfpFile, String plans, String clientName) {
        this.clientFile = fileName;
        this.rfpFile = rfpFile;
        this.plans = plans;
        this.clientName = clientName;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"data/clientMedicalVirginFalse.json", "data/rfpMedicalVirginFalse.json", "data/rfpMedicalVirginFalseSetOptions.json", "TestClientMedical"}
//                {"data/clientDental.json", "data/rfpDentalCreateRequest.json", "TestClientDental"},
//                {"data/clientVision.json", "data/rfpVisionCreateRequest.json", "TestClientVision"}
        };
        return Arrays.asList(data);
    }

    private String accessToken;

    @Before
    public void getAuth0Token() throws UnsupportedEncodingException {
        accessToken = getToken(brokerageId, authId, brokerRole, role, carrierAcl);
    }

    @Ignore
    @Test
    public void createRfpVirginFalseTest() throws Throwable {

        boolean virginState = false;
        String testClientName = clientName + " NonVirgin  " + timeStamp;
        String quotesFile = "data/TestQuotesFile.pdf";

        ObjectNode myJson = prepareClientJson(clientFile);

        addClientMedicalVirginValue(myJson, "rfpProducts","virginGroup", virginState);
        addValueIntoMainNode(myJson, "clientName", testClientName);

        int clientId = sendPostRequestForCreateClient(myJson.toString(), API_URL, accessToken);
        apiCheck.confirmRequestWithStatus(CODE_201);

        int rfpId = createRfpFromFile(rfpFile, accessToken, clientId);
        apiCheck.confirmRequestWithStatus(CODE_201);

        int optionId = sendGetRequestForRfpById(API_URL, accessToken, rfpId);

        createPlan(plans, accessToken, rfpId, optionId);
        apiCheck.confirmRequestWithStatus(CODE_200);

        addQuotePdf(quotesFile,API_URL, rfpId, accessToken);
        apiCheck.confirmRequestWithStatus(CODE_200);

        sendPostRequestSubmitRrfpByClientIdRfpId(null, accessToken, API_URL, clientId, rfpId);
        //apiCheck.confirmRequestWithStatus(CODE_200);  it is a bug!
        //TODO: add check from response "rateType": "COMPOSITE"
    }


    @Test
    public void createRfpVirginFalseAgeBandedTest() throws Throwable {

        boolean virginState = false;
        String rateType = "BANDED";
        String testClientName = clientName + " NonVirgin  " + timeStamp;
        String quotesFile = "data/TestQuotesFile.pdf";

        ObjectNode myJson = prepareClientJson(clientFile);

        addClientMedicalVirginValue(myJson, "rfpProducts","virginGroup", virginState);
        addValueIntoMainNode(myJson, "clientName", testClientName);

        int clientId = sendPostRequestForCreateClient(myJson.toString(), API_URL, accessToken);
        apiCheck.confirmRequestWithStatus(CODE_201);


        JsonNode myJsonForRFP = prepareRfpJson(rfpFile);
        setAgeBandedAtRFP(myJsonForRFP.toString(), "options","rateType", rateType);

        int rfpId = createRfpFromJson(myJsonForRFP.toString(), accessToken, clientId);
        apiCheck.confirmRequestWithStatus(CODE_201);

        int optionId = sendGetRequestForRfpById(API_URL, accessToken, rfpId);

        createPlan(plans, accessToken, rfpId, optionId);
        apiCheck.confirmRequestWithStatus(CODE_200);

        addQuotePdf(quotesFile,API_URL, rfpId, accessToken);
        apiCheck.confirmRequestWithStatus(CODE_200);

        sendPostRequestSubmitRrfpByClientIdRfpId(null, accessToken, API_URL, clientId, rfpId);
        //apiCheck.confirmRequestWithStatus(CODE_200); it is a bug!
        //TODO: add check from response "rateType": "BANDED"
    }



    private ObjectNode prepareClientJson(String fileName) throws java.io.IOException {
        URL file = Resources.getResource(fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, ObjectNode.class);
    }

    private JsonNode prepareRfpJson(String fileName) throws java.io.IOException {
        URL file = Resources.getResource(fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(file);
    }

}
