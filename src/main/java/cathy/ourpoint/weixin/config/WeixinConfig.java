/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package cathy.ourpoint.weixin.config;

import cathy.jfinal.weixin.sdk.api.ApiConfigKit;
import cathy.ourpoint.weixin.controller.WeixinApiController;
import cathy.ourpoint.weixin.controller.WeixinMsgController;
import com.jfinal.config.*;
import com.jfinal.kit.PropKit;

public class WeixinConfig extends JFinalConfig {
	
	public void loadProp(String fileName) {
		try {
			PropKit.use(fileName);
		}
		catch (Exception e) {
			throw new RuntimeException("loadProp exception", e);
		}
	}
	
	public void configConstant(Constants constants) {
		loadProp("configuration.properties");
		constants.setDevMode(PropKit.getBoolean("devMode", false));
		
		// ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
		ApiConfigKit.setDevMode(constants.getDevMode());
	}
	
	public void configRoute(Routes routes) {
		routes.add("/msg", WeixinMsgController.class);
		routes.add("/api", WeixinApiController.class, "/api");
	}
	
	public void configPlugin(Plugins me) {
		// C3p0Plugin c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		// me.add(c3p0Plugin);
		
		// EhCachePlugin ecp = new EhCachePlugin();
		// me.add(ecp);
	}
	
	public void configInterceptor(Interceptors me) {
		
	}
	
	public void configHandler(Handlers me) {
		
	}
	
}
