package cn.edots.nest;

import android.app.Application;
import android.support.annotation.DrawableRes;

/**
 * @author Parck.
 * @date 2017/9/29.
 * @desc
 */

public abstract class SlugApplication extends Application {

    public abstract
    @DrawableRes
    int getBackButtonImageResource();
}
