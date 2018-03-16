package cn.edots.nest.factory;

import cn.edots.nest.Controller;
import cn.edots.nest.cache.AppCachePool;

/**
 * @Author WETOOP
 * @Date 2018/3/16.
 * @Description
 */

public class ControllerProvider {

    protected static AppCachePool<String, Controller> cache = AppCachePool.getInstance().newTAG(ControllerProvider.class.getSimpleName());

    public static <T extends Controller> T get(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T t = (T) cache.get(clazz.getSimpleName());
        if (t == null) {
            t = clazz.newInstance();
            cache.put(clazz.getSimpleName(), t);
        }
        return t;
    }
}
