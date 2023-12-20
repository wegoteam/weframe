package com.wegoteam.framework.core.logable;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
/**
 * @description: Logable注解的切面实现
 * @author: XUCHANG
 * @create: 2021-04-05 14:43
 */
@Component
public class ServerEnv implements ApplicationListener<WebServerInitializedEvent> {
	private int serverPort;

	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		this.serverPort = event.getWebServer().getPort();
	}

	public int getServerPort() {
		return this.serverPort;
	}
	
	public String getServerIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "0.0.0.0";
        }
	}
}
