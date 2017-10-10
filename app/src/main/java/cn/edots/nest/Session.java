package cn.edots.nest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Parck.
 * @date 2017/9/30.
 * @desc
 */

public class Session {

    private static Map<String, Object> sessionCache = new HashMap<>();

    public static Object getAttribute(String key) {
        if (sessionCache.get(key) == null) sessionCache.put(key, readObject(key));
        return sessionCache.get(key);
    }

    public static void setAttribute(String key, Object value) {
    }

    public static void writObject(Object o) {

    }

    public static Object readObject(String key) {
        return null;
    }

    public static void finish() {
        sessionCache.clear();
        sessionCache = null;
    }
}
