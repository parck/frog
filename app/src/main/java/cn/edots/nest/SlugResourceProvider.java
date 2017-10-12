package cn.edots.nest;

import android.support.annotation.DrawableRes;

/**
 * @author Parck.
 * @date 2017/9/29.
 * @desc
 */

public interface SlugResourceProvider {

    boolean isDebug();

    @DrawableRes
    int getBackButtonImageResource();
}
