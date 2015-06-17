package cathy.ourpoint.weixin.controller;

import cathy.jfinal.weixin.sdk.api.ApiConfig;
import cathy.jfinal.weixin.sdk.api.ApiResult;
import cathy.jfinal.weixin.sdk.api.MenuApi;
import cathy.jfinal.weixin.sdk.api.UserApi;
import cathy.jfinal.weixin.sdk.jfinal.ApiController;
import com.jfinal.kit.PropKit;

public class WeixinApiController extends ApiController {
	
	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();
		
		ac.setToken(PropKit.get("token"));
		ac.setAppId(PropKit.get("appId"));
		ac.setAppSecret(PropKit.get("appSecret"));
		
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		return ac;
	}
	
	/*
	public void index() {
		render("/api/index.html");
	}
	*/
	
	public void getMenu() {
		ApiResult apiResult = MenuApi.getMenu();
		if (apiResult.isSucceed())
			renderText(apiResult.getJson());
		else
			renderText(apiResult.getErrorMsg());
	}
	
	public void getFollowers() {
		ApiResult apiResult = UserApi.getFollows();
		// TODO
		renderText(apiResult.getJson());
	}
}

