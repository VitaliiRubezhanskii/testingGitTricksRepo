package test.java.api.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;
import test.java.api.helpers.ApiHelper;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import static test.java.api.testData.TestData.*;

public class ClientControllerTest extends ApiHelper {

    private String accessToken;

    @Before
    public void getAuth0Token() throws UnsupportedEncodingException {
        accessToken = getToken(brokerageId, authId, brokerRole, role, carrierAcl);
    }

    @Test
    public void retrievingClientsForCurrentBrokerTest() throws Throwable {

        sendGetRequestToClients(API_URL, accessToken);

        apiCheck.confirmRequestWithStatus(CODE_200);
        apiCheck.brokerNameCheck("Stanislav Brokerage House");
    }

    @Test
    public void retrievingPlansForClientByClientIdTest() throws Throwable {

        int id = createClientAndRetrieveId("data/clientMedical.json", "TestClientMedical", accessToken);
        sendGetRequestPlansForClientByClientId(API_URL, accessToken, id);

        apiCheck.confirmRequestWithStatus(CODE_200);
        apiCheck.planTypeCheckFromList("PPO");
    }

    @Test
    public void updateCurrentClientPlanById() throws Throwable {
        int planId = 4661;
        String clientId = Integer.toString(createClientAndRetrieveId("data/clientMedical.json", "TestClientMedical", accessToken));

        URL file = Resources.getResource("data/testPlans.json");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode myJson = mapper.readValue(file, ObjectNode.class);
        addValueIntoMainNode(myJson, "client_id", clientId);

        sendPutRequestForUpdatePlan(myJson.toString(), API_URL, accessToken, planId);

        apiCheck.confirmRequestWithStatus(CODE_200);
        apiCheck.planTypeCheck("PPO");
    }

    @Test
    public void setExtProduct() throws Throwable {
        URL clientFile = Resources.getResource("data/clientMedical.json");
        URL extProductFile = Resources.getResource("data/extProduct.json");

        String clientJsonFile = Resources.toString(clientFile, Charsets.UTF_8);
        String extProductJsonFile = Resources.toString(extProductFile, Charsets.UTF_8);

        sendPostRequestSetClientExtProducts(clientJsonFile, extProductJsonFile, accessToken, API_URL);
    }

    @Test
    public void createRfpForClientIdTest() throws Throwable {
        URL createRfp = Resources.getResource("data/rfpMedicalCreateRequest.json");

        String rfpFile = Resources.toString(createRfp, Charsets.UTF_8);

        int clientId = createClientAndRetrieveId("data/clientMedical.json", "TestClientMedical", accessToken);

        int rfpId = sendPostRequestForCreateRFP(rfpFile, API_URL, accessToken, clientId);
        apiCheck.confirmRequestWithStatus(CODE_201);

        sendPostRequestSubmitRrfpByClientIdRfpId(null, accessToken, API_URL, clientId, rfpId);
        apiCheck.confirmRequestWithStatus(CODE_200);
    }

}

