package cn.edots.nest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

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

    protected SlugBinder sbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void onBack() {

    }

    private boolean isBackToExit() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
