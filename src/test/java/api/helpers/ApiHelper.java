package test.java.api.helpers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import groovy.json.JsonException;
import sun.security.util.PendingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import test.java.api.TestBase;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.urlEncodingEnabled;
import static test.java.api.testData.TestData.API_URL;
import static test.java.api.testData.TestData.CODE_200;
import static test.java.api.testData.TestData.CODE_201;

public class ApiHelper extends TestBase {

    public final ApiCheck apiCheck = new ApiCheck();

    public int createClientAndRetrieveId(String resourceFile, String clientName, String accessToken) throws Throwable {
        int clientId;
        URL file = Resources.getResource(resourceFile);
        String jsonFile = Resources.toString(file, Charsets.UTF_8);
        clientId = sendPostRequestForCreateClient(jsonFile, API_URL, accessToken);
        apiCheck.clientNameCheck(clientName);
        apiCheck.confirmRequestWithStatus(CODE_201);
        return clientId;
    }

    public int createRfpFromJson(String jsonNode, String accessToken, int clientId) throws Throwable {
        int rfpId;
        rfpId = sendPostRequestForCreateRFP(jsonNode, API_URL, accessToken, clientId);
        apiCheck.confirmRequestWithStatus(CODE_201);
        return rfpId;
    }

    public int createRfpFromFile(String resourceFile, String accessToken, int clientId) throws Throwable {
        int rfpId;
        URL file = Resources.getResource(resourceFile);
        String jsonFile = Resources.toString(file, Charsets.UTF_8);
        rfpId = sendPostRequestForCreateRFP(jsonFile, API_URL, accessToken, clientId);
        apiCheck.confirmRequestWithStatus(CODE_201);
        return rfpId;
    }

    public void createPlan(String resourceFile, String accessToken, int rfpId, int optionId) throws Throwable {
        URL file = Resources.getResource(resourceFile);
        String jsonFile = Resources.toString(file, Charsets.UTF_8);
        ArrayNode formJson = (ArrayNode) new ObjectMapper().readTree(jsonFile);
        ObjectNode jsonUpdateOptionId = (ObjectNode) formJson.get(0);

        jsonUpdateOptionId.put("optionId", optionId);
        formJson.insert(0, jsonUpdateOptionId);

        sendPostRequestForCreatePlansInRfp(formJson.toString(), API_URL, accessToken, rfpId);
        apiCheck.confirmRequestWithStatus(CODE_200);
    }

    public void addValueIntoMainNode(ObjectNode myJson, String key, String value) {
        myJson.put(key, value);
    }

    public void addClientMedicalVirginValue(ObjectNode myJson, String nodeName, String key, boolean value) {
        ObjectNode node = (ObjectNode) myJson.get(nodeName).get(0);
        node.put(key, value);
        myJson.putArray(nodeName).add(node);
//        Example:
//        ObjectNode objectNode = (ObjectNode) getInputJsonNode();
//        for (List<String> list : data) {
//            ((ObjectNode) objectNode.get("TEST").get("key")).put("test_test", list.get(0));
//            canonicalObj = messageMapper.getMappedPayload(objectNode);
//            String actual = canonicalObj.get("booking").get("bookingIdentifier").get("bookingSystem").asText();
//            errorCollector.checkThat(actual, equalTo(list.get(1)));
//        }
    }

    public JsonNode setAgeBandedAtRFP(String jsonString, String nodeName, String key, String status) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode myJson = (ObjectNode) mapper.readTree(jsonString).get(0);
        ObjectNode optionNode = (ObjectNode) myJson.get(nodeName).get(0);
        optionNode.put(key, status);
        myJson.putArray(nodeName).add(optionNode);
        //return mapper.readTree("["+myJson.toString()+"]");

        //ObjectNode _node = JsonNodeFactory.instance.objectNode();
        //_node.putArray("").add(myJson);
        ArrayNode arr = new ArrayNode(JsonNodeFactory.instance);
        arr.add(myJson);
        return arr;


        //return myJson;
    }

    public class JsonModifier<V> {
        ObjectNode myJson;

        public JsonModifier(ObjectNode myJson) {
            this.myJson = myJson;
        }

        public void updateValueInJson(String key, V value) {
            try {
                if (value instanceof String) {
                    String stringValue = (String) value;
                    myJson.put(key, stringValue);
                }
                if (value instanceof Boolean) {
                    Boolean booleanValue = (Boolean) value;
                    myJson.put(key, booleanValue);
                }
                if (value instanceof Long) {
                    Long longValue = (Long) value;
                    myJson.put(key, longValue);
                }
            } catch (Throwable e) {
                e.getCause();
                e.getStackTrace();
                e.getMessage();
            }
        }

        public void updateValueInJsonArray(String key, String arrayKey, int index, V value) {
            try {
                JsonNode node = myJson.get(key);
                if (node.isArray()) {
                    if (value instanceof String) {
                        String stringValue = (String) value;
                        ObjectNode newValue = (ObjectNode) node.get(index);
                        newValue.put(arrayKey, stringValue);
                        myJson.putArray(key).add(newValue);
                    }
                    if (value instanceof Boolean) {
                        Boolean booleanValue = (Boolean) value;
                        ObjectNode newValue = (ObjectNode) node.get(index);
                        newValue.put(arrayKey, booleanValue);
                        myJson.putArray(key).add(newValue);
                    }
                    if (value instanceof Long) {
                        Long longValue = (Long) value;
                        ObjectNode newValue = (ObjectNode) node.get(index);
                        newValue.put(arrayKey, longValue);
                        myJson.putArray(key).add(newValue);
                    }
                }
                else {
                    throw new JsonException("value is not an array");
                }
            } catch (Throwable e) {
                e.getCause();
                e.getStackTrace();
                e.getMessage();
            }

        }
    }

    public String getToken(String brokerageId, String authId, String brokerRole, String role, String carrierAcl) throws UnsupportedEncodingException {

        /*accessToken = createTokenForBroker(brokerageId, authId, brokerRole, role, carrierAcl);
        logger.debug("Access token =  " + accessToken);*/
        return createTokenForBroker(brokerageId, authId, brokerRole, role, carrierAcl);
    }

    public void sendGetRequestToClients(String apiUrl, String accessToken) throws FileNotFoundException, PendingException {

        apiCheck.validatableResponse = sendGetRequestWithToken(null, apiUrl + "v1/clients", accessToken);
        logger.debug("Response body = " + apiCheck.validatableResponse.extract().response().asString());
    }

    public void sendGetRequestPlansForClientByClientId(String apiUrl, String accessToken, int clientId) throws IOException, PendingException {
        apiCheck.validatableResponse = sendGetRequestWithTokenAndJson(null, apiUrl + "v1/clients/" + clientId + "/plans", accessToken);
        logger.debug("Response body = " + apiCheck.validatableResponse.extract().response().asString());
    }

    public void sendPutRequestForUpdatingRrfpByRfpId(String myJson, String apiUrl, String accessToken, int clientId) throws IOException, PendingException {

        apiCheck.validatableResponse = sendPutRequestWithTokenAndJson(myJson, apiUrl + "v1/clients/" + clientId + "/rfps", accessToken);
        logger.debug("Response body = " + apiCheck.validatableResponse.extract().response().asString());
    }

    public void sendPutRequestForUpdatePlan(String myJson, String apiUrl, String accessToken, int planId) throws IOException, PendingException {

        apiCheck.validatableResponse = sendPutRequestWithTokenAndJson(myJson, apiUrl + "v1/clients/plans/" + planId, accessToken);
        logger.debug("Response body = " + apiCheck.validatableResponse.extract().response().asString());
    }

    public int sendPostRequestForCreateClient(String myJson, String apiUrl, String accessToken) throws IOException, PendingException {

        apiCheck.validatableResponse = sendPostRequestWithTokenAndJson(myJson, apiUrl + "v1/clients", accessToken);
        JsonNode mainNode = apiCheck.validatableResponse.extract().body().jsonPath().getObject("", JsonNode.class);
        Assert.assertNotNull(mainNode.get("id"));

        logger.debug("Response body = " + apiCheck.validatableResponse.extract().response().asString());
        return mainNode.get("id").asInt();
    }

    /**Created by Vitalii Rubezhnaskii to perform clientState Change with PUT http method
     *
     * @param myJson
     * @param apiUrl
     * @param accessToken
     * @return
     * @throws IOException
     * @throws PendingException
     */
    public void sendPutRequestForChangeClient(String myJson, String apiUrl, String accessToken) throws IOException, PendingException {
        apiCheck.validatableResponse=sendPutRequestWithTokenAndJson(myJson,apiUrl+"v1/clients",accessToken);
        logger.debug("Response body = " + apiCheck.validatableResponse.extract().response().asString());
    }

    public int sendPostRequestForCreateRFP(String myJson, String apiUrl, String accessToken, int clientId) throws IOException, PendingException {


        apiCheck.validatableResponse = sendPostRequestWithTokenAndJson(myJson, apiUrl + "v1/clients/" + clientId + "/rfps", accessToken);
        JsonNode mainNode = apiCheck.validatableResponse.extract().body().jsonPath().getObject("", JsonNode.class);
        logger.debug("Response body = " + apiCheck.validatableResponse.extract().response().asString());

        Assert.assertNotNull(mainNode.get(0).get("id"));
        return mainNode.get(0).get("id").asInt();
    }

    public void sendPostRequestForCreatePlansInRfp(String myJson, String apiUrl, String accessToken, int rfpId) throws IOException, PendingException {
        apiCheck.validatableResponse = sendPostRequestWithTokenAndJson(myJson, apiUrl + "v1/plans/rfp/" + rfpId + "/create", accessToken);
        logger.debug("Response body = " + apiCheck.validatableResponse.extract().response().asString());
    }

    public void sendPostRequestSetClientExtProducts(String clientDataJson, String extProductJson, String accessToken, String apiUrl) throws PendingException {

        ValidatableResponse vr = sendPostRequestWithTokenAndJson(clientDataJson, apiUrl + "v1/clients", accessToken);
        JsonNode mainNode = vr.extract().body().jsonPath().getObject("", JsonNode.class);
        Assert.assertNotNull(mainNode);

        int createdClientId = mainNode.get("id").asInt();

        apiCheck.validatableResponse = sendPostRequestWithTokenAndJson(extProductJson, apiUrl + "v1/clients/" + createdClientId + "/extProducts", accessToken);
        logger.debug("Response body = " + apiCheck.validatableResponse.extract().response().asString());
    }

    public int sendGetRequestForRfpById(String apiUrl, String accessToken, int rfpId) throws IOException, PendingException {
        apiCheck.validatableResponse = sendGetRequestWithTokenAndJson(null, apiUrl + "v1/rfps/" + rfpId, accessToken);
        JsonNode mainNode = apiCheck.validatableResponse.extract().body().jsonPath().getObject("", JsonNode.class);

        logger.debug("Response body = " + apiCheck.validatableResponse.extract().response().asString());
        Assert.assertNotNull(mainNode);
        return mainNode.get("options").get(0).get("id").asInt();
    }

    public void sendPostRequestInstantQuoteRfpByClientIdRfpId(String extProductJson, String accessToken, String apiUrl, int clientId, int rfpId) throws PendingException {
        apiCheck.validatableResponse = sendPostRequestWithTokenAndJson(extProductJson, apiUrl + "v1/instantQuote/client/" + clientId + "/" + rfpId, accessToken);
        logger.debug("Response body = " + apiCheck.validatableResponse.extract().response().asString());
    }

    public void sendPostRequestSubmitRrfpByClientIdRfpId(String submitJson, String accessToken, String apiUrl, int clientId, int rfpId) throws PendingException {
        apiCheck.validatableResponse = sendPostRequestWithTokenAndJson(submitJson, apiUrl + "v1/clients/" + clientId + "/rfps/submit/?rfpIds=" + rfpId, accessToken);
        logger.debug("Response body = " + apiCheck.validatableResponse.extract().response().asString());
    }

    public ApiCheck getApiCheck() {
        return apiCheck;
    }

    protected ValidatableResponse sendPostRequest(Map<String, String> body, String url) throws PendingException {
        ValidatableResponse response;

        response = given().contentType("application/json").body(body).post(url).then();

//        logger.debug("header = " + response.extract().response().getStatusCode());

        if (response.extract().response().getStatusCode() > 299) {
            throw new PendingException(
                    "POST request is not successful: " + "Status:" + response.extract().response().getStatusCode() + " Body: " + response.extract().body()
                            .asString());
        }
        return response;
    }


    protected ValidatableResponse sendGetRequestWithToken(Map<String, String> body, String url, String token) throws PendingException {
        ValidatableResponse response;

        if (body == null) {
            response = given().contentType("application/json").header("authorization", "Bearer " + token).get(url).then();
        } else {
            response = given().contentType("application/json").header("authorization", "Bearer " + token).body(body).get(url).then();
        }
        return response;
    }


    protected ValidatableResponse sendPostRequestWithTokenAndJson(String body, String url, String token) {
        ValidatableResponse response;

        if (body == null) {
            response = given().contentType("application/json").header("authorization", "Bearer " + token).post(url).then();
        } else {
            response = given().contentType("application/json").header("authorization", "Bearer " + token).body(body).post(url).then();
        }
        return response;
    }

    protected ValidatableResponse sendPutRequestWithTokenAndJson(String body, String url, String token) throws PendingException {
        ValidatableResponse response;

        if (body == null) {
            response = given().contentType("application/json").header("authorization", "Bearer " + token).put(url).then();
        } else {
            response = given().contentType("application/json").header("authorization", "Bearer " + token).body(body).put(url).then();
        }
        return response;
    }

    protected ValidatableResponse sendGetRequestWithTokenAndJson(String body, String url, String token) throws PendingException {
        ValidatableResponse response;

        if (body == null) {
            response = given().contentType("application/json").header("authorization", "Bearer " + token).get(url).then();
        } else {
            response = given().contentType("application/json").header("authorization", "Bearer " + token).body(body).get(url).then();
        }
        return response;
    }

    protected ValidatableResponse addQuotePdf(String fileName, String apiUrl, int rfpId, String token) throws FileNotFoundException {
        File testFile;
        String url = apiUrl + "v1/rfp/" + rfpId + "/files/filesSummary/upload";

        try {
            URL file = Resources.getResource(fileName);
            if ((fileName.trim()).isEmpty() || fileName == null) {
                throw new NullPointerException();
            }
            testFile = new File(file.getFile());
            if (!testFile.exists()) {
                throw new FileNotFoundException();
            }
        } catch (NullPointerException ex) {
            throw new FileNotFoundException("File name is not specified!");
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("File not found by specified path: " + Resources.getResource(fileName));
        }
        return given()
                .header("authorization", "Bearer " + token)
                .multiPart("file", testFile)
                .multiPart("name", testFile.getName())
                .multiPart("type", "application/pdf")
                .multiPart("section", "filesSummary")
                .post(url)
                .then();
    }


    /** Methods for clientStatus change flow
     *
     */

    public void changeClientStateFromStartedToSubmitted(){


    }
}