package cn.edots.nest.core.cache;

import java.util.HashMap;

import cn.edots.nest.ui.fragment.BaseFragment;

/**
 * @author Parck.
 * @date 2017/10/25.
 * @desc
 */

public class AppCachePool<K, V> extends HashMap<K, V> {

    public static AppCachePool pool;

    public static AppCachePool getInstance() {
        if (pool == null) {
            pool = CachePoolHolder.getPool();
        }
        return pool;
    }

    private AppCachePool() {
    }

    public void cache(String key, BaseFragment fragment) {
        pool.put(key, fragment);
    }

    //=============================================================
    // inner class
    //=============================================================

    private static class CachePoolHolder {
        static AppCachePool pool;

        private CachePoolHolder() {
        }

        static AppCachePool getPool() {
            if (pool == null) {
                pool = new AppCachePool();
            }
            return pool;
        }
    }
}
