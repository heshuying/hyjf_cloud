package com.hyjf.cs.message.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiasq
 * @version MongoDbProperties, v0.1 2019/1/2 10:28
 */
@ConfigurationProperties(prefix = "mongodb")
public class MongoDbProperties {

    private String hostports;

    private String username;

    private String password;

    private String authenticationDatabase;

    private String database;

    private Integer minConnectionsPerHost = 10;

    private Integer connectionsPerHost = 100;

    private Integer connectTimeout;

    private Integer maxWaitTime;

    private Integer socketTimeout;

    public String getHostports() {
        return hostports;
    }
    public void setHostports(String hostports) {
        this.hostports = hostports;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthenticationDatabase() {
        return authenticationDatabase;
    }

    public void setAuthenticationDatabase(String authenticationDatabase) {
        this.authenticationDatabase = authenticationDatabase;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Integer getMinConnectionsPerHost() {
        return minConnectionsPerHost;
    }

    public void setMinConnectionsPerHost(Integer minConnectionsPerHost) {
        this.minConnectionsPerHost = minConnectionsPerHost;
    }

    public Integer getConnectionsPerHost() {
        return connectionsPerHost;
    }

    public void setConnectionsPerHost(Integer connectionsPerHost) {
        this.connectionsPerHost = connectionsPerHost;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(Integer maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}
