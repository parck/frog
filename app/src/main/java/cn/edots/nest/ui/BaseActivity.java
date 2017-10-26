package cn.edots.nest.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.edots.nest.core.SlugResourceProvider;
import cn.edots.nest.core.Standardize;
import cn.edots.nest.log.Logger;
import cn.edots.slug.core.activity.SlugBinder;


/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final String EXIT_ACTION = "EXIT_ACTION";
    protected static final String INTENT_DATA = "INTENT_DATA";

    protected final Activity THIS = this;
    protected final String TAG = this.getClass().getSimpleName();
    protected final ExitReceiver exitReceiver = new ExitReceiver();

    protected Logger logger;
    protected SlugBinder sbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            SlugResourceProvider resourceProvider = (SlugResourceProvider) THIS.getApplication();
            logger = new Logger(TAG, resourceProvider.isDebug());
        } catch (ClassCastException e) {
            throw new ClassCastException("This application has not implements \"SlugResourceProvider\"");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isTranslucentStatus()) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (isFeatureNoTitle()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        registerExitReceiver();
        sbinder = SlugBinder.getInstance(this);
        if (THIS instanceof Standardize) {
            ((Standardize) THIS).setupData((Map<String, Object>) getIntent().getSerializableExtra(INTENT_DATA));
            ((Standardize) THIS).initView();
            ((Standardize) THIS).setListeners();
            ((Standardize) THIS).onCreateLast();
        }
        logger.i("SlugBinder init successful!");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterExitReceiver();
        if (sbinder != null) sbinder.finish();
    }

    private void registerExitReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(EXIT_ACTION);
        registerReceiver(exitReceiver, filter);
    }

    private void unregisterExitReceiver() {
        unregisterReceiver(exitReceiver);
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
        Intent exitIntent = new Intent();
        exitIntent.setAction(EXIT_ACTION);
        THIS.sendBroadcast(exitIntent);
    }

    protected void onBack() {
        finish();
    }

    @Override
    public void onClick(View v) {
    }

    protected void TOAST(CharSequence message) {
        Toast.makeText(THIS, message, Toast.LENGTH_SHORT).show();
    }

    protected void TOAST(CharSequence message, @BaseTransientBottomBar.Duration int duration) {
        Toast.makeText(THIS, message, duration).show();
    }

    public void startActivity(Class<? extends Activity> clazz) {
        THIS.startActivity(new Intent(THIS, clazz));
    }

    public void startActivity(Class<? extends Activity> clazz, HashMap<String, Object> data) {
        Intent intent = new Intent(THIS, clazz);
        intent.putExtra(INTENT_DATA, data);
        THIS.startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    protected boolean isTranslucentStatus() {
        return false;
    }

    protected boolean isFeatureNoTitle() {
        return false;
    }

    protected boolean isBackToExit() {
        return false;
    }

    protected void addFragment(@IdRes int layoutId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(layoutId, fragment, fragment.getClass().getSimpleName()).commit();
    }

    protected void removeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    protected void replaceFragment(@IdRes int layoutId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(layoutId, fragment).commit();
    }

    //======================================================
    // inner class
    //======================================================

    class ExitReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (EXIT_ACTION.equals(intent.getAction())) THIS.finish();
        }
    }
}