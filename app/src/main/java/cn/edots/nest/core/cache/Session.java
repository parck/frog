package cn.edots.nest.core.cache;

/**
 * @author Parck.
 * @date 2017/9/30.
 * @desc
 */

public class Session {
    private static AppCachePool<String, Object> session = AppCachePool.getInstance();

    public static Object getAttribute(String key) {
        if (session.get(key) == null) session.put(key, readObject(key));
        return session.get(key);
    }

    public static void setAttribute(String key, Object value) {
        session.put(key, value);
    }

    public static void writObject(Object o) {

    }

    public static Object readObject(String key) {
        return null;
    }

    public static void remove(String key) {
        session.remove(key);
    }
}
