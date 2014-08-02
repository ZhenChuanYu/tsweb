package com.tsweb.server;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

public class TomcatServer {
	private Tomcat tomcat;
	private String hostName;
	private int port;
	private String appBase;
	private String baseDir;

	public TomcatServer(String hostName, int port, String appBase,
			String baseDir) {
		this.hostName = hostName;
		this.port = port;
		this.appBase = appBase;
		this.baseDir = baseDir;

		setUp();
	}

	private void setUp() {
		tomcat = new Tomcat();
		tomcat.setHostname(hostName);
		tomcat.setPort(port);
		tomcat.setBaseDir(baseDir);
		tomcat.getHost().setAppBase(appBase);

		StandardServer server = (StandardServer) tomcat.getServer();
		AprLifecycleListener listener = new AprLifecycleListener();
		server.addLifecycleListener(listener);
		try {
			tomcat.addWebapp("/", appBase);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	public void startup() {
		try {
			tomcat.start();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
		tomcat.getServer().await();
	}

	public void shutdown() {
		try {
			tomcat.stop();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
	}
}