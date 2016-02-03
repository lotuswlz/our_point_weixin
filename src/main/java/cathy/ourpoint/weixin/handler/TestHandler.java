package cathy.ourpoint.weixin.handler;

import cathy.jfinal.weixin.sdk.msg.in.InTextMsg;
import cathy.jfinal.weixin.sdk.msg.out.OutTextMsg;

/**
 * @author lzwu
 * @since 2/3/16
 */
public class TestHandler implements TextMessageHandler{
    @Override
    public OutTextMsg processMessage(InTextMsg inTextMsg) {
        OutTextMsg msg = new OutTextMsg(inTextMsg);
        msg.setContent("from: " + inTextMsg.getFromUserName() + "; content: " + inTextMsg.getContent());
        return msg;
    }
}
