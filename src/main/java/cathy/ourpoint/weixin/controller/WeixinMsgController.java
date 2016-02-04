/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package cathy.ourpoint.weixin.controller;

import cathy.jfinal.weixin.sdk.api.ApiConfig;
import cathy.jfinal.weixin.sdk.api.ApiResult;
import cathy.jfinal.weixin.sdk.api.UserApi;
import cathy.jfinal.weixin.sdk.jfinal.MsgController;
import cathy.jfinal.weixin.sdk.msg.in.*;
import cathy.jfinal.weixin.sdk.msg.in.event.*;
import cathy.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import cathy.jfinal.weixin.sdk.msg.out.OutImageMsg;
import cathy.jfinal.weixin.sdk.msg.out.OutNewsMsg;
import cathy.jfinal.weixin.sdk.msg.out.OutTextMsg;
import cathy.jfinal.weixin.sdk.msg.out.OutVoiceMsg;
import cathy.ourpoint.weixin.cache.UserMapCache;
import cathy.ourpoint.weixin.handler.TestHandler;
import cathy.ourpoint.weixin.handler.TextMessageHandler;
import com.jfinal.kit.PropKit;
import org.apache.log4j.Logger;

public class WeixinMsgController extends MsgController {
	
	private static final String helpStr = "您好，欢迎来到【风景在途一起走】，测试期间，发送：\n\n Test: 查看测试帖;";

	private static final Logger LOGGER = Logger.getLogger(WeixinMsgController.class);

	public ApiConfig getApiConfig() {
		ApiConfig ac = new ApiConfig();
		
		ac.setToken(PropKit.get("token"));
		LOGGER.info("Get token: " + ac.getToken());
		ac.setAppId(PropKit.get("appId"));
		LOGGER.info("Get AppId: " + ac.getAppId());
		ac.setAppSecret(PropKit.get("appSecret"));
		
		ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
		ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
		return ac;
	}
	
	protected void processInTextMsg(InTextMsg inTextMsg) {
		String msgContent = inTextMsg.getContent().trim();
		LOGGER.info("message: " + msgContent);
		if ("help".equalsIgnoreCase(msgContent) || "帮助".equals(msgContent)) {
			OutTextMsg outMsg = new OutTextMsg(inTextMsg);
			outMsg.setContent(helpStr);
			render(outMsg);
		}
		else if ("Test".equalsIgnoreCase(msgContent) || msgContent.contains("测试帖")) {
			OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
			outMsg.addNews(
					"Test",
					"Test",
					"http://mmbiz.qpic.cn/mmbiz/I1xX9hNeTicibjKuHNK7yj7ddjx9REp28UpBJyJ0O4OmrAwHG67rbNEorCJWpnTu6icMAy8zTLQDQgicSA2ucg1dMg/640?wx_fmt=jpeg&wxfrom=5",
					"http://mp.weixin.qq.com/s?__biz=MzAxNzYxNjIxMw==&mid=206637505&idx=1&sn=d570b595f5fce2300b9297933ac92f5b&key=af154fdc40fed003c88dcf4d499e50e4829752966a6598417c0807674484ee6e9a4132bd77a29d8dcb585874cab8e76c&ascene=0&uin=MTg1NzU0NTM2MQ%3D%3D&devicetype=iMac+MacBookPro11%2C2+OSX+OSX+10.10.3+build%2814D136%29&version=11020012&pass_ticket=urG60AbY9HaRqyLuSvAzkYzAz26iibGUhcL6U1tpzFG82NIu4X0Cwf3tZPN5WbqP");

			render(outMsg);
		} else if (msgContent.toLowerCase().startsWith("RG")) {
			String phoneNumber = msgContent.replaceAll("^RG ?([\\+\\d]+)$", "$1");
			UserMapCache.getInstance().addUser(inTextMsg.getFromUserName(), phoneNumber);
			TextMessageHandler handler = new TestHandler();
			OutTextMsg outTextMsg = handler.processMessage(inTextMsg);
			outTextMsg.setContent("注册成功！");
			render(outTextMsg);
		} else {
			try {
				TextMessageHandler handler = new TestHandler();
				OutTextMsg outTextMsg = handler.processMessage(inTextMsg);
				render(outTextMsg);

				ApiResult userInfo = UserApi.getUserInfo("oVSvds4xQTUrzMmHR5O4UyOTPa-I");
				String userInfoJson = userInfo.getJson();
				OutTextMsg outTextMsg2 = handler.processMessage(inTextMsg);
				outTextMsg2.setContent(userInfoJson);
				render(outTextMsg2);
			} catch (Exception e) {
				e.printStackTrace();
				OutTextMsg outMsg = new OutTextMsg(inTextMsg);
				outMsg.setContent(e.getMessage());
				render(outMsg);
			}
		}
	}
	
	/**
	 * 实现父类抽方法，处理图片消息
	 */
	protected void processInImageMsg(InImageMsg inImageMsg) {
		OutImageMsg outMsg = new OutImageMsg(inImageMsg);
		// 将刚发过来的图片再发回去
		outMsg.setMediaId(inImageMsg.getMediaId());
		render(outMsg);
	}
	
	protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
		OutVoiceMsg outMsg = new OutVoiceMsg(inVoiceMsg);
		// 将刚发过来的语音再发回去
		outMsg.setMediaId(inVoiceMsg.getMediaId());
		render(outMsg);
	}
	
	protected void processInVideoMsg(InVideoMsg inVideoMsg) {
		OutTextMsg outMsg = new OutTextMsg(inVideoMsg);
		outMsg.setContent("\t视频消息已成功接收，该视频的 mediaId 为: " + inVideoMsg.getMediaId());
		render(outMsg);
	}
	
	protected void processInLocationMsg(InLocationMsg inLocationMsg) {
		OutTextMsg outMsg = new OutTextMsg(inLocationMsg);
		outMsg.setContent("已收到地理位置消息:" +
							"\nlocation_X = " + inLocationMsg.getLocation_X() +
							"\nlocation_Y = " + inLocationMsg.getLocation_Y() + 
							"\nscale = " + inLocationMsg.getScale() +
							"\nlabel = " + inLocationMsg.getLabel());
		render(outMsg);
	}
	
	protected void processInLinkMsg(InLinkMsg inLinkMsg) {
		OutNewsMsg outMsg = new OutNewsMsg(inLinkMsg);
		outMsg.addNews("链接消息已成功接收", "链接使用图文消息的方式发回给你，还可以使用文本方式发回。点击图文消息可跳转到链接地址页面，是不是很好玩 :)" , "http://mmbiz.qpic.cn/mmbiz/zz3Q6WSrzq1ibBkhSA1BibMuMxLuHIvUfiaGsK7CC4kIzeh178IYSHbYQ5eg9tVxgEcbegAu22Qhwgl5IhZFWWXUw/0", inLinkMsg.getUrl());
		render(outMsg);
	}
	
	protected void processInFollowEvent(InFollowEvent inFollowEvent) {
		OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
		outMsg.setContent("感谢关注 JFinal Weixin 极速开发服务号，为您节约更多时间，去陪恋人、家人和朋友 :) \n\n\n " + helpStr);
		render(outMsg);
	}
	
	protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
		OutTextMsg outMsg = new OutTextMsg(inQrCodeEvent);
		outMsg.setContent("processInQrCodeEvent() 方法测试成功");
		render(outMsg);
	}
	
	protected void processInLocationEvent(InLocationEvent inLocationEvent) {
		OutTextMsg outMsg = new OutTextMsg(inLocationEvent);
		outMsg.setContent("processInLocationEvent() 方法测试成功");
		render(outMsg);
	}
	
	protected void processInMenuEvent(InMenuEvent inMenuEvent) {
		renderOutTextMsg("processInMenuEvent() 方法测试成功");
	}
	
	protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
		renderOutTextMsg("语音识别结果： " + inSpeechRecognitionResults.getRecognition());
	}
	
	protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {
		String status = inTemplateMsgEvent.getStatus();
		renderOutTextMsg("模板消息是否接收成功：" + status);
	}
}






