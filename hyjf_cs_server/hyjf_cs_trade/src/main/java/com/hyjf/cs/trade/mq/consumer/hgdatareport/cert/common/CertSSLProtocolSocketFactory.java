package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.security.KeyStore;

public class CertSSLProtocolSocketFactory implements ProtocolSocketFactory {

	private SSLContext sslcontext = null;

	public static String crtPath = "" ;

	// 读取证书，并创建信任
	public SSLContext createSSLContext(String crtPath) {
		SSLContext sslc = null;
		InputStream is = null;
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			is= new FileInputStream(crtPath);
			keyStore.load(is, "changeit".toCharArray());
			// 创建TrustManagerFactory,管理授权证书
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
			tmf.init(keyStore);
			// 构造SSL环境，指定SSL版本为3.0，也可以使用TLSv1，但是SSLv3更加常用。
			sslc = SSLContext.getInstance("SSLv3");
			sslc.init(null, tmf.getTrustManagers(), null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null){
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return sslc;
	}
	

	private SSLContext getSSLContext() {
		if (this.sslcontext == null) {
			this.sslcontext = createSSLContext(crtPath);
		}
		return this.sslcontext;
	}

	public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
			throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
	}

	public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(host, port);
	}

	public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort)
			throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
	}

	public Socket createSocket(String host, int port, InetAddress localAddress, int localPort, HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException {
		if (params == null) {
			throw new IllegalArgumentException("Parameters may not be null");
		}
		int timeout = params.getConnectionTimeout();
		SocketFactory socketfactory = getSSLContext().getSocketFactory();
		if (timeout == 0) {
			return socketfactory.createSocket(host, port, localAddress, localPort);
		} else {
			Socket socket = socketfactory.createSocket();
			SocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
			SocketAddress remoteaddr = new InetSocketAddress(host, port);
			socket.bind(localaddr);
			socket.connect(remoteaddr, timeout);
			return socket;
		}
	}
}