package cn.edots.nest.cache;

import java.util.HashMap;

/**
 * @author Parck.
 * @date 2017/10/25.
 * @desc
 */

public class AppCachePool<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = -4912064188710623560L;
    
    protected static AppCachePool pool;

    public static AppCachePool getInstance() {
        if (pool == null) {
            pool = CachePoolHolder.getPool();
        }
        return pool;
    }

    public AppCachePool newTAG(String tag) {
        if (pool.get(tag) == null) pool.put(tag, new AppCachePool<K, V>());
        return (AppCachePool) pool.get(tag);
    }

    private AppCachePool() {
    }

    public void clear() {
        if (pool != null) pool.clear();
    }

    // =============================================================================================
    // inner class
    // =============================================================================================

    private static class CachePoolHolder {
        static AppCachePool pool;

        private CachePoolHolder() {
        }

        static AppCachePool getPool() {
            if (pool == null) pool = new AppCachePool();
            return pool;
        }
    }
}
