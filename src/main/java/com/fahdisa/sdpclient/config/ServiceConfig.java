package com.fahdisa.sdpclient.config;

public class ServiceConfig {

    private String spId;

    private String spPassword;

    private String timestamp;

    private String serviceId;

    private String productId;

    private String reference;

    private String smsServiceActivationNumber;

    private String ussdServiceActivationNumber;

    private String criteria;

    private String endpoint;

    private String interfaceName;

    private String correlator;

    private String linkid;

    private String traceUniqueID;

    private String oauthToken;

    private String userID;

    private String operCode;

    private String isAutoExtend;

    private String channelID;

    private String oa;

    private String fa;

    private String bundleId ="";

    private String presentId = "";

    private ServiceConfig() {
    }

    private ServiceConfig(String spId, String spPassword, String timestamp,
                         String serviceId, String productId, String reference,
                         String smsServiceActivationNumber, String ussdServiceActivationNumber,
                         String criteria, String endpoint, String interfaceName, String correlator,
                         String linkid, String traceUniqueID, String oauthToken, String userID,
                         String operCode, String isAutoExtend, String channelID, String oa,
                          String fa, String bundleId, String presentId) {
        this.spId = spId;
        this.spPassword = spPassword;
        this.timestamp = timestamp;
        this.serviceId = serviceId;
        this.productId = productId;
        this.reference = reference;
        this.smsServiceActivationNumber = smsServiceActivationNumber;
        this.ussdServiceActivationNumber = ussdServiceActivationNumber;
        this.criteria = criteria;
        this.endpoint = endpoint;
        this.interfaceName = interfaceName;
        this.correlator = correlator;
        this.linkid = linkid;
        this.traceUniqueID = traceUniqueID;
        this.oauthToken = oauthToken;
        this.userID = userID;
        this.operCode = operCode;
        this.isAutoExtend = isAutoExtend;
        this.channelID = channelID;
        this.oa = oa;
        this.fa = fa;
        this.bundleId = bundleId;
        this.presentId = presentId;
    }

    public String getSpId() {
        return spId;
    }

    public String getSpPassword() {
        return spPassword;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getProductId() {
        return productId;
    }

    public String getReference() {
        return reference;
    }

    public String getSmsServiceActivationNumber() {
        return smsServiceActivationNumber;
    }

    public String getUssdServiceActivationNumber() {
        return ussdServiceActivationNumber;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getCorrelator() {
        return correlator;
    }

    public String getLinkid() {
        return linkid;
    }

    public String getTraceUniqueID() {
        return traceUniqueID;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public String getUserID() {
        return userID;
    }

    public String getOperCode() {
        return operCode;
    }

    public String getIsAutoExtend() {
        return isAutoExtend;
    }

    public String getChannelID() {
        return channelID;
    }

    public String getOa(){
        return oa;
    }

    public String getFa(){
        return fa;
    }

    public String getBundleId(){
        return bundleId;
    }

    public String getPresentId(){
        return presentId;
    }

    public static class Builder {

        private String spId="";
        private String spPassword="";
        private String timestamp="";
        private String serviceId="";
        private String productId="";
        private String reference="";
        private String smsServiceActivationNumber="";
        private String ussdServiceActivationNumber="";
        private String criteria="";
        private String endpoint;
        private String interfaceName="";
        private String correlator="";
        private String linkid="";
        private String traceUniqueID="";
        private String oauthToken="";
        private String userID="";
        private String operCode="";
        private String isAutoExtend="";
        private String channelID="";
        private String oa="";
        private String fa="";
        private String bundleId="";
        private String presentId="";

        public Builder setSpId(String spId) {
            this.spId = spId;
            return this;
        }

        public Builder setSpPassword(String spPassword) {
            this.spPassword = spPassword;
            return this;
        }

        public Builder setTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setServiceId(String serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public Builder setProductId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder setReference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder setUssdServiceActivationNumber(String ussdServiceActivationNumber) {
            this.ussdServiceActivationNumber = ussdServiceActivationNumber;
            return this;
        }

        public Builder setCriteria(String criteria) {
            this.criteria = criteria;
            return this;
        }

        public Builder setEndpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public Builder setInterfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
            return this;
        }

        public Builder setCorrelator(String correlator) {
            this.correlator = correlator;
            return this;
        }

        public Builder setLinkid(String linkid) {
            this.linkid = linkid;
            return this;
        }

        public Builder setTraceUniqueID(String traceUniqueID) {
            this.traceUniqueID = traceUniqueID;
            return this;
        }

        public Builder setSmsServiceActivationNumber(String smsServiceActivationNumber) {
            this.smsServiceActivationNumber = smsServiceActivationNumber;
            return this;
        }

        public Builder setOauthToken(String oauthToken) {
            this.oauthToken = oauthToken;
            return this;
        }

        public Builder setUserID(String userID) {
            this.userID = userID;
            return this;
        }

        public Builder setOperCode(String operCode) {
            this.operCode = operCode;
            return this;
        }

        public Builder setIsAutoExtend(String isAutoExtend) {
            this.isAutoExtend = isAutoExtend;
            return this;
        }

        public Builder setChannelID(String channelID) {
            this.channelID = channelID;
            return this;
        }

        public Builder setOa(String oa){
            this.oa = oa;
            return this;
        }

        public Builder setFa(String fa){
            this.fa = fa;
            return this;
        }

        public Builder setBundleId(String bundleId){
            this.bundleId = bundleId;
            return this;
        }

        public Builder setPresentId(String presentId){
            this.presentId = presentId;
            return this;
        }

        public ServiceConfig build() {
            return new ServiceConfig(this.spId, this.spPassword, this.timestamp, this.serviceId, this.productId,
                    this.reference, this.smsServiceActivationNumber, this.ussdServiceActivationNumber,
                    this.criteria, this.endpoint, this.interfaceName, this.correlator, this.linkid, this.traceUniqueID,
                    this.oauthToken, this.userID, this.operCode, this.isAutoExtend, this.channelID, this.oa, this.fa, this.bundleId,
                    this.presentId);
        }
    }

    @Override
    public String toString() {
        return "ServiceConfig{" + "spId='" + spId + '\'' + ", spPassword='" + spPassword + '\'' + ", timestamp='" + timestamp + '\'' + ", serviceId='" + serviceId + '\'' + ", productId='" + productId + '\'' + ", reference='" + reference + '\'' + ", smsServiceActivationNumber='" + smsServiceActivationNumber + '\'' + ", ussdServiceActivationNumber='" + ussdServiceActivationNumber + '\'' + ", criteria='" + criteria + '\'' + ", endpoint='" + endpoint + '\'' + ", interfaceName='" + interfaceName + '\'' + ", correlator='" + correlator + '\'' + ", linkid='" + linkid + '\'' + ", traceUniqueID='" + traceUniqueID + '\'' + ", oauthToken='" + oauthToken + '\'' + ", userID='" + userID + '\'' + ", operCode='" + operCode + '\'' + ", isAutoExtend='" + isAutoExtend + '\'' + ", channelID='" + channelID + '\'' + '}';
    }
}
