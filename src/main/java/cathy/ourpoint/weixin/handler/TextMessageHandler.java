package cathy.ourpoint.weixin.handler;

import cathy.jfinal.weixin.sdk.msg.in.InTextMsg;

/**
 * @author lzwu
 * @since 2/3/16
 */
public interface TextMessageHandler {
    void processMessage(InTextMsg inTextMsg);
}
