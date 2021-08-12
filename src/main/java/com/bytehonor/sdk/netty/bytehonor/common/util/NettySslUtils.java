package com.bytehonor.sdk.netty.bytehonor.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Objects;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.bytehonor.sdk.netty.bytehonor.common.constant.NettyConstants;
import com.bytehonor.sdk.netty.bytehonor.common.exception.BytehonorNettySdkException;

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

    /**
     * @param password
     * @return
     * @throws Exception
     */
    public static SslContext server(String password) {
        Objects.requireNonNull(password, "password");

        try {
            InputStream is = readJksStream();

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(is, password.toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, password.toCharArray());

            SslContext sslContext = SslContextBuilder.forServer(keyManagerFactory).clientAuth(ClientAuth.NONE).build();
            return sslContext;
        } catch (Exception e) {
            LOG.error("error", e);
            throw new BytehonorNettySdkException(e);
        }
    }

    private static InputStream readJksStream() throws IOException {
        ClassPathResource resource = new ClassPathResource(NettyConstants.JKS_FILE_PATH);
        InputStream is = resource.getInputStream(); // InputStream is = new FileInputStream(jksPath);
        if (is == null) {
            throw new BytehonorNettySdkException(NettyConstants.JKS_FILE_PATH + " no jks file!");
        }
        return is;
    }

    public static SslContext client(String password) {
        Objects.requireNonNull(password, "password");
        try {
            InputStream is = readJksStream();

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(is, password.toCharArray());

            TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init(keyStore);

            SslContext sslContext = SslContextBuilder.forClient().trustManager(factory).build();
            return sslContext;
        } catch (Exception e) {
            LOG.error("error", e);
            throw new BytehonorNettySdkException(e);
        }
    }

    public static SSLEngine serverSsl(String password) {
        Objects.requireNonNull(password, "password");

        try {
            InputStream is = readJksStream();

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(is, password.toCharArray());

            KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            factory.init(keyStore, password.toCharArray());

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(factory.getKeyManagers(), null, null);

            // 生成sslEngine
            SSLEngine sslEngine = sslContext.createSSLEngine();
            // 是否客户端模式 - 服务端模式 false, 客户端模式true
            sslEngine.setUseClientMode(false);
            // 是否需要验证客户端（双向验证） - 单向验证 fasle
            sslEngine.setNeedClientAuth(false);
            return sslEngine;
        } catch (Exception e) {
            LOG.error("error", e);
            throw new BytehonorNettySdkException(e);
        }
    }

    public static SSLEngine clientSsl(String password) {
        Objects.requireNonNull(password, "password");

        try {
            InputStream is = readJksStream();

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(is, password.toCharArray());

            TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, factory.getTrustManagers(), null);

            // 生成sslEngine
            SSLEngine sslEngine = sslContext.createSSLEngine();
            // 是否客户端模式 - 服务端模式 false, 客户端模式true
            sslEngine.setUseClientMode(true);
            // 是否需要验证客户端（双向验证） - 单向验证 fasle
            sslEngine.setNeedClientAuth(false);
            return sslEngine;
        } catch (Exception e) {
            LOG.error("error", e);
            throw new BytehonorNettySdkException(e);
        }
    }
}
