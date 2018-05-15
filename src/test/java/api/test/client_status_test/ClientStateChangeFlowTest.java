package test.java.api.test.client_status_test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.google.common.io.Resources;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test.java.api.helpers.ApiHelper;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.runners.Parameterized.*;
import static test.java.api.testData.TestData.*;


@RunWith(value = Parameterized.class)
public class ClientStateChangeFlowTest extends ApiHelper {


    private String fileName;
    private String clientName;
    private String rfpFile;

    private List<String> clientStates=Arrays.asList("RFP_STARTED","RFP_SUBMITTED","QUOTED","PENDING_APPROVAL","ON_BOARDING","SOLD","CLOSED");

    String timeStamp = new DateTime().toString("yyMMddHHmmss");


    public ClientStateChangeFlowTest(String fileName, String clientName, String rfpFile) {
        this.fileName = fileName;
        this.clientName = clientName;
        this.rfpFile = rfpFile;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"data/clientMedical.json", "TestClientMedical"},
                {"data/clientDental.json", "TestClientDental"},
                {"data/clientVision.json", "TestClientVision"}
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
    public void should_change_clients_status_according_to_flow() throws Throwable {

        boolean virginState = false;
             String testClientName = clientName + " NonVirgin " + timeStamp;
/**
 *    Set for node "rfpProducts" virginGroup status false
 */
                ObjectNode myJson = prepareJson();
                addClientMedicalVirginValue(myJson, "rfpProducts", "virginGroup", virginState);


/**
 *    Set name value for client
 */
        addValueIntoMainNode(myJson, "clientName", testClientName);

/**
 *    Perform POST reguest for client  creation
 */

        sendPostRequestForCreateClient(myJson.toString(), API_URL, accessToken);

        apiCheck.confirmRequestWithStatus(CODE_201);
        apiCheck.clientNameCheck(testClientName);
        apiCheck.virginStateCheck(virginState);
        apiCheck.clientStateCheck(clientStates.get(0));
/**
 *  perform PUT HTTP request and verify proper client Status change according to flow
 */

        logger.debug("current status 0: "+  myJson.get("clientState"));
        myJson.put("clientState","RFP_SUBMITTED");
        logger.debug("changed status 1: "+  myJson.get("clientState"));
        sendPutRequestForChangeClient(myJson.toString(),API_URL,accessToken);
        apiCheck.confirmRequestWithStatus(CODE_200);
        apiCheck.clientStateCheck(clientStates.get(1));

        logger.debug("current status 1: "+  myJson.get("clientState"));
        myJson.put("clientState","QUOTED");
        logger.debug("changed status 2: "+  myJson.get("clientState"));
        sendPutRequestForChangeClient(myJson.toString(),API_URL,accessToken);
        apiCheck.confirmRequestWithStatus(CODE_200);
        apiCheck.clientStateCheck(clientStates.get(2));

        logger.debug("current status 2: "+  myJson.get("clientState"));
        myJson.put("clientState","PENDING_APPROVAL");
        logger.debug("changed status 3: "+  myJson.get("clientState"));
        sendPutRequestForChangeClient(myJson.toString(),API_URL,accessToken);
        apiCheck.confirmRequestWithStatus(CODE_200);
        apiCheck.clientStateCheck(clientStates.get(3));

        logger.debug("current status 3: "+  myJson.get("clientState"));
        myJson.put("clientState","ON_BOARDING");
        logger.debug("changed status 4: "+  myJson.get("clientState"));
        sendPutRequestForChangeClient(myJson.toString(),API_URL,accessToken);
        apiCheck.confirmRequestWithStatus(CODE_200);
        apiCheck.clientStateCheck(clientStates.get(4));

        logger.debug("current status 4: "+  myJson.get("clientState"));
        myJson.put("clientState","SOLD");
        logger.debug("changed status 5: "+  myJson.get("clientState"));
        sendPutRequestForChangeClient(myJson.toString(),API_URL,accessToken);
        apiCheck.confirmRequestWithStatus(CODE_200);
        apiCheck.clientStateCheck(clientStates.get(5));

        logger.debug("current status 5: "+  myJson.get("clientState"));
        myJson.put("clientState","CLOSED");
        logger.debug("changed status 6: "+  myJson.get("clientState"));
        sendPutRequestForChangeClient(myJson.toString(),API_URL,accessToken);
        apiCheck.confirmRequestWithStatus(CODE_200);
        apiCheck.clientStateCheck(clientStates.get(6));
    }

    private ObjectNode prepareJson() throws java.io.IOException {
        URL file = Resources.getResource(fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, ObjectNode.class);
    }
}