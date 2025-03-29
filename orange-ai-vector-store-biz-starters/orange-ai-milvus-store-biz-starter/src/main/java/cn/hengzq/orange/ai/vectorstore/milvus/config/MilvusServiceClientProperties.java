package cn.hengzq.orange.ai.vectorstore.milvus.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;


@Getter
@ConfigurationProperties(MilvusServiceClientProperties.CONFIG_PREFIX)
public class MilvusServiceClientProperties {

    public static final String CONFIG_PREFIX = "spring.ai.vectorstore.milvus.client";

    /**
     * Secure the authorization for this connection, set to True to enable TLS.
     */
    protected boolean secure = false;

    /**
     * Milvus host name/address.
     */
    private String host = "localhost";

    /**
     * Milvus the connection port. Value must be greater than zero and less than 65536.
     */
    private int port = 19530;

    /**
     * The uri of Milvus instance
     */
    private String uri;

    /**
     * Token serving as the key for identification and authentication purposes.
     */
    private String token;

    /**
     * Connection timeout value of client channel. The timeout value must be greater than
     * zero.
     */
    private long connectTimeoutMs = 10000;

    /**
     * Keep-alive time value of client channel. The keep-alive value must be greater than
     * zero.
     */
    private long keepAliveTimeMs = 55000;

    /**
     * Enables the keep-alive function for client channel.
     */
    // private boolean keepAliveWithoutCalls = false;

    /**
     * The keep-alive timeout value of client channel. The timeout value must be greater
     * than zero.
     */
    private long keepAliveTimeoutMs = 20000;

    /**
     * Deadline for how long you are willing to wait for a reply from the server. With a
     * deadline setting, the client will wait when encounter fast RPC fail caused by
     * network fluctuations. The deadline value must be larger than or equal to zero.
     * Default value is 0, deadline is disabled.
     */
    private long rpcDeadlineMs = 0; // Disabling deadline

    /**
     * The client.key path for tls two-way authentication, only takes effect when "secure"
     * is True.
     */
    private String clientKeyPath;

    /**
     * The client.pem path for tls two-way authentication, only takes effect when "secure"
     * is True.
     */
    private String clientPemPath;

    /**
     * The ca.pem path for tls two-way authentication, only takes effect when "secure" is
     * True.
     */
    private String caPemPath;

    /**
     * server.pem path for tls one-way authentication, only takes effect when "secure" is
     * True.
     */
    private String serverPemPath;

    /**
     * Sets the target name override for SSL host name checking, only takes effect when
     * "secure" is True. Note: this value is passed to grpc.ssl_target_name_override
     */
    private String serverName;

    /**
     * Idle timeout value of client channel. The timeout value must be larger than zero.
     */
    private long idleTimeoutMs = TimeUnit.MILLISECONDS.convert(24, TimeUnit.HOURS);

    /**
     * The username and password for this connection.
     */
    private String username = "root";

    /**
     * The password for this connection.
     */
    private String password = "milvus";

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setConnectTimeoutMs(long connectTimeoutMs) {
        this.connectTimeoutMs = connectTimeoutMs;
    }

    public void setKeepAliveTimeMs(long keepAliveTimeMs) {
        this.keepAliveTimeMs = keepAliveTimeMs;
    }

    public void setKeepAliveTimeoutMs(long keepAliveTimeoutMs) {
        this.keepAliveTimeoutMs = keepAliveTimeoutMs;
    }


    public void setRpcDeadlineMs(long rpcDeadlineMs) {
        this.rpcDeadlineMs = rpcDeadlineMs;
    }

    public void setClientKeyPath(String clientKeyPath) {
        this.clientKeyPath = clientKeyPath;
    }

    public void setClientPemPath(String clientPemPath) {
        this.clientPemPath = clientPemPath;
    }

    public void setCaPemPath(String caPemPath) {
        this.caPemPath = caPemPath;
    }

    public void setServerPemPath(String serverPemPath) {
        this.serverPemPath = serverPemPath;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public void setIdleTimeoutMs(long idleTimeoutMs) {
        this.idleTimeoutMs = idleTimeoutMs;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
