package cn.edots.nest.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import cn.edots.nest.log.Logger;
import cn.edots.slug.core.activity.SlugBinder;


/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected final String TAG = this.getClass().getSimpleName();
    protected Logger logger = new Logger(TAG);
    protected Activity THIS;

    protected SlugBinder sbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isTranslucentStatus()) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        super.onCreate(savedInstanceState);
        this.THIS = this;
        init();
    }

    private void init() {
        sbinder = SlugBinder.getInstance(this);
        logger.i("SlugBinder Init Successful!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sbinder.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 4:
                if (isBackToExit()) onExit();
                else onBack();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onExit() {

    }

    protected void onBack() {
        finish();
    }

    protected boolean isTranslucentStatus() {
        return false;
    }

    protected boolean isBackToExit() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
