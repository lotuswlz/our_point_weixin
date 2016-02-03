package cathy.ourpoint.weixin.handler;

import cathy.jfinal.weixin.sdk.msg.in.InTextMsg;
import cathy.jfinal.weixin.sdk.msg.out.OutTextMsg;

/**
 * @author lzwu
 * @since 2/3/16
 */
public interface TextMessageHandler {
    OutTextMsg processMessage(InTextMsg inTextMsg);
}
