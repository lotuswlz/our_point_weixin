package cathy.ourpoint.weixin.router;

import cathy.jfinal.weixin.sdk.jfinal.MsgController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzwu
 * @since 2/3/16
 */
public class TextMessageRouter {

    private static TextMessageRouter instance = null;

    private Map<String, MsgController> userMsgControllerMap;

    private TextMessageRouter() {
        userMsgControllerMap = new HashMap<String, MsgController>();
    }

    public static TextMessageRouter getInstance() {
        if (instance == null) {
            instance = new TextMessageRouter();
        }
        return instance;
    }

    public void addUserIfNotExist(String fromUserName, MsgController controller) {
        if (!userMsgControllerMap.containsKey(fromUserName)) {
            userMsgControllerMap.put(fromUserName, controller);
        }
    }

    public void sendMsg(String to, String message) {
        MsgController controller = userMsgControllerMap.get(to);
        controller.renderOutTextMsg(message);
    }
}
