package cathy.ourpoint.weixin.handler;

import cathy.jfinal.weixin.sdk.jfinal.MsgController;
import cathy.jfinal.weixin.sdk.msg.in.InTextMsg;

/**
 * @author lzwu
 * @since 2/3/16
 */
public class TestHandler {

    private MsgController controller;

    public TestHandler(MsgController controller) {
        this.controller = controller;
    }

    public void processMessage(InTextMsg inTextMsg) {
        this.controller.renderOutTextMsg("User Name: " + inTextMsg.getFromUserName() + "; to: " + inTextMsg.getToUserName() + "; Text: " + inTextMsg.getContent() + ";(msgId:" + inTextMsg.getMsgId() + ", type:" + inTextMsg.getMsgType() + ")");
    }
}
