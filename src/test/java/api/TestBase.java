package test.java.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import test.java.api.dto.AppMetadata;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;
import static test.java.api.testData.TestData.*;

public class TestBase {

    public static final Logger logger;

    static {
        logger = LogManager.getLogger(TestBase.class);
    }

    static Properties properties;
    private String PathToWebDriver;

    private String startPage;
    private String[] chromeOptions;
    public final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";


    protected static final String APP_METADATA = "app_metadata";
    protected String[] appCarrier;

    private static ObjectMapper mapper = new ObjectMapper();
    private static Algorithm algorithm;




    public String createTokenForBroker(String brokerageId, String authId, String brokerRole, String role, String carrierAcl) throws UnsupportedEncodingException {
        algorithm = HMAC256(secret.getBytes("UTF-8"));

        AppMetadata appMetadata = new AppMetadata.Builder()
                .withBrokerage("FTP Brokerage")
                .withBrokerageRole(brokerRole)
                .withRoles(role)
                .withBrokerageId(brokerageId)
                .withCarrierAcl(carrierAcl)
                .build();

        return JWT.create()
                .withClaim(
                        APP_METADATA,
                        Try.of(() -> mapper.writeValueAsString(appMetadata)).getOrNull()
                )
                .withIssuer(issuer)
                .withSubject(authId)
                .withAudience(clientId)
                .sign(algorithm);
    }



    private static Properties getProperties() {
        return properties;
    }

    public String getAppApiUrl() {

        if (getProperties().getProperty("test.apiHelper.url") != null) {
            return getProperties().getProperty("test.apiHelper.url");
        } else {
            return getProperties().getProperty("apiHelper.url");
        }

    }

    public String getApiUrl() {

        return getProperties().getProperty("apiHelper.url");

    }






}