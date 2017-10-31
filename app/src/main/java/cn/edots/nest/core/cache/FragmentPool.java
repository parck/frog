package cn.edots.nest.core.cache;

import android.support.v4.app.Fragment;

import cn.edots.nest.ui.fragment.BaseFragment;

/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */

public class FragmentPool {

    private static AppCachePool<String, BaseFragment> cache = AppCachePool.getInstance();

    public static <T extends Fragment> T getFragment(Class<T> clazz) {
        if (cache.get(clazz.getSimpleName()) == null) {
            try {
                cache.cache(clazz.getSimpleName(), clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (T) cache.get(clazz.getSimpleName());
    }

    public static void remove(String key) {
        cache.remove(key);
    }

}
