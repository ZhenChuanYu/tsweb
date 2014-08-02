package com.tsweb.main;
import com.tsweb.config.Configure;
import com.tsweb.server.TomcatServer;

public class ServerMain {
	public static void main(String[] args) {
		String tomcatDir = System.getProperty("user.dir") + "/tomcat";
		String tomcatIp = Configure.getProperty("tomcat.ip");
		Integer tomcatPort = Integer.parseInt(Configure.getProperty("tomcat.port"));
		TomcatServer tomcat = new TomcatServer(tomcatIp, tomcatPort, tomcatDir, ".");
		tomcat.startup();
	}
}