package com.bytehonor.sdk.netty.bytehonor.common.util;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

/**
 * <pre>
 * tls配置
 * 
 * https://blog.csdn.net/luo15242208310/article/details/108215019
 * 
 * https://blog.csdn.net/luo15242208310/article/details/108195803
 * </pre>
 * 
 * @author lijianqiang
 *
 */
public class NettySslUtils {

    private static final Logger LOG = LoggerFactory.getLogger(NettySslUtils.class);

    private static String jksKeyPassw = "123456";
    private static String jksStorePassw = "123456";

    // private static String JKS_PATH = "";

    public static SslContext server() throws Exception {
        ClassPathResource resource = new ClassPathResource("cert/server.jks");
        InputStream is = resource.getInputStream(); // InputStream is = new FileInputStream(jksPath);

        KeyStore keyStore = KeyStore.getInstance("jks");
        keyStore.load(is, jksStorePassw.toCharArray());

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, jksKeyPassw.toCharArray());

        SslContext sslContext = SslContextBuilder.forServer(keyManagerFactory).clientAuth(ClientAuth.NONE).build();
        return sslContext;
    }

    public static SslContext client() throws Exception {
        ClassPathResource resource = new ClassPathResource("cert/server.jks");
        InputStream is = resource.getInputStream();

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(is, jksStorePassw.toCharArray());

        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init(keyStore);

        SslContext sslContext = SslContextBuilder.forClient().trustManager(factory).build();
        return sslContext;
    }

    public static SSLEngine serverSsl() {
        SSLEngine sslEngine = null;
        try {
            ClassPathResource resource = new ClassPathResource("cert/server.jks");
            InputStream is = resource.getInputStream(); // InputStream is = new FileInputStream(jksPath);

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(is, jksStorePassw.toCharArray());

            KeyManagerFactory factory = KeyManagerFactory.getInstance("SunX509");
            factory.init(keyStore, jksKeyPassw.toCharArray());

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(factory.getKeyManagers(), null, null);

            // 生成sslEngine
            sslEngine = sslContext.createSSLEngine();
            // 是否客户端模式 - 服务端模式 false, 客户端模式true
            sslEngine.setUseClientMode(false);
            // 是否需要验证客户端（双向验证） - 单向验证 fasle
            sslEngine.setNeedClientAuth(false);
        } catch (Exception e) {
            LOG.error("error", e);
        }
        return sslEngine;
    }

    public static SSLEngine clientSsl() {
        SSLEngine sslEngine = null;
        try {
            ClassPathResource resource = new ClassPathResource("cert/server.jks");
            InputStream is = resource.getInputStream(); // InputStream is = new FileInputStream(jksPath);

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(is, jksStorePassw.toCharArray());

            TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, factory.getTrustManagers(), null);

            // 生成sslEngine
            sslEngine = sslContext.createSSLEngine();
            // 是否客户端模式 - 服务端模式 false, 客户端模式true
            sslEngine.setUseClientMode(true);
            // 是否需要验证客户端（双向验证） - 单向验证 fasle
            sslEngine.setNeedClientAuth(false);
        } catch (Exception e) {
            LOG.error("error", e);
        }
        return sslEngine;
    }
}
