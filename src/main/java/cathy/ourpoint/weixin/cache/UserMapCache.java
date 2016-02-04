package cathy.ourpoint.weixin.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzwu
 * @since 2/4/16
 */
public class UserMapCache {
    private static UserMapCache instance = null;

    private Map<String, String> userMobileMap;
    private Map<String, String> mobileUserMap;

    public static UserMapCache getInstance() {
        if (instance == null) {
            instance = new UserMapCache();
        }
        return instance;
    }

    private UserMapCache() {
        userMobileMap = new HashMap<String, String>();
        mobileUserMap = new HashMap<String, String>();
    }

    public String getMobile(String userId) {
        return userMobileMap.get(userId);
    }

    public String getUserId(String mobile) {
        return mobileUserMap.get(mobile);
    }

    public void addUser(String userId, String mobile) {
        this.mobileUserMap.put(mobile, userId);
        this.userMobileMap.put(userId, mobile);
    }
}
