package client;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * TrustManager implementation that reads the truststore from classpath.
 * 
 * @author vijay
 */
public class ResourceX509TrustManager implements X509TrustManager {

	private final X509TrustManager trustManager;

	public ResourceX509TrustManager() throws Exception {
		trustManager = loadTrustManager();
	}

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		trustManager.checkClientTrusted(chain, authType);
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		trustManager.checkServerTrusted(chain, authType);
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return trustManager.getAcceptedIssuers();
	}

	private X509TrustManager loadTrustManager() throws Exception {
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream trustStoreInputStream = getClass().getResourceAsStream("dev-sz1_clienttruststore.jks");
		if (null == trustStoreInputStream) {
			throw new RuntimeException("Error loading the client truststore. Check if the classpath entry '/client.truststore' exists");
		}
		ks.load(trustStoreInputStream, "geheim".toCharArray());

		TrustManagerFactory tmf = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(ks);

		for (TrustManager tm : tmf.getTrustManagers()) {
			if (tm instanceof X509TrustManager) {
				return (X509TrustManager) tm;
			}
		}
		throw new NoSuchAlgorithmException(
				"No X509TrustManager in TrustManagerFactory");
	}
}