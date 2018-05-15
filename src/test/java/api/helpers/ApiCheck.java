package test.java.api.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;


public class ApiCheck  {


    public ValidatableResponse validatableResponse = null;

    public void planTypeCheckFromList(String planType) throws Throwable {
        JsonNode mainNode = validatableResponse.extract().body().jsonPath().getObject("", JsonNode.class);
        Assert.assertNotNull(mainNode);
        Assert.assertEquals(mainNode.get(0).get("planType").asText(), planType);
    }

    public void brokerNameCheck(String brokerNam) throws Throwable {
        JsonNode mainNode = validatableResponse.extract().body().jsonPath().getObject("", JsonNode.class);
        Assert.assertNotNull(mainNode);
        //List<JsonNode> list = mainNode.get(2).findValues("brokerName");
        Assert.assertEquals(mainNode.get(2).get("brokerName").asText(), brokerNam);
    }

    public void planTypeCheck(String planType) throws Throwable {
        JsonNode mainNode = validatableResponse.extract().body().jsonPath().getObject("", JsonNode.class);
        Assert.assertNotNull(mainNode);
        Assert.assertEquals(mainNode.get("planType").asText(), planType);
    }

    public void clientNameCheck(String clientName) throws Throwable {
        JsonNode mainNode = validatableResponse.extract().body().jsonPath().getObject("", JsonNode.class);
        Assert.assertNotNull(mainNode);
        Assert.assertEquals(mainNode.get("clientName").asText(), clientName);
    }

    public void virginStateCheck(boolean state) throws Throwable {
        JsonNode mainNode = validatableResponse.extract().body().jsonPath().getObject("", JsonNode.class);
        Assert.assertNotNull(mainNode);
        Assert.assertEquals(mainNode.path("rfpProducts").get(0).get("virginGroup").asBoolean(), state);
    }

    public void confirmRequestWithStatus(int status) throws Throwable {
        validatableResponse.statusCode(status);
    }
/**
* Created by Vitalii Rubezhnskii (to assert clientState Changes)
 */
    public void clientStateCheck(String clientStatus){
        JsonNode mainNode = validatableResponse.extract().body().jsonPath().getObject("", JsonNode.class);
        Assert.assertNotNull(mainNode);
        Assert.assertEquals(mainNode.get("clientState").asText(), clientStatus);
    }

}

