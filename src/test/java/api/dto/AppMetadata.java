package test.java.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppMetadata {

    static ObjectMapper mapper = new ObjectMapper();

    private String email;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private String role;
    private String brokerage;
    private String brokerageRole;
    private String brokerageId;
    private String carrierAcl;

    public AppMetadata() {}

    @JsonCreator
    public static AppMetadata fromJson(String json) throws IOException {
        return mapper.readValue(json, AppMetadata.class);
    }

    @JsonIgnore
    private AppMetadata(Builder builder) {
        setEmail(builder.email);
        setRole(builder.role);
        setBrokerage(builder.brokerage);
        setBrokerageRole(builder.brokerageRole);
        setBrokerageId(builder.brokerageId);
        setCarrierAcl(builder.carrierAcl);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(String brokerage) {
        this.brokerage = brokerage;
    }

    public String getBrokerageRole() {
        return brokerageRole;
    }

    public void setBrokerageRole(String brokerageRole) {
        this.brokerageRole = brokerageRole;
    }

    public String getBrokerageId() {
        return brokerageId;
    }

    public void setBrokerageId(String brokerageId) {
        this.brokerageId = brokerageId;
    }

    public String getCarrierAcl() {
        return carrierAcl;
    }

    public void setCarrierAcl(String carrierAcl) {
        this.carrierAcl = carrierAcl;
    }

    public static final class Builder {

        private String email;
        private String role;
        private String brokerage;
        private String brokerageRole;
        private String brokerageId;
        private String carrierAcl;

        public Builder() {}

        public Builder withEmail(String val) {
            email = val;
            return this;
        }

        public Builder withRoles(String val) {
            role = val;
            return this;
        }

        public Builder withBrokerage(String val) {
            brokerage = val;
            return this;
        }

        public Builder withBrokerageRole(String val) {
            brokerageRole = val;
            return this;
        }

        public Builder withBrokerageId(String val) {
            brokerageId = val;
            return this;
        }

        public Builder withCarrierAcl(String val) {
            carrierAcl = val;
            return this;
        }

        public AppMetadata build() {
            return new AppMetadata(this);
        }

        public AppMetadata build(Map<String, Object> val) {
            return val != null ? mapper.convertValue(val, AppMetadata.class) : null;
        }
    }
}
