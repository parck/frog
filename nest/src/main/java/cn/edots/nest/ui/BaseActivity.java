package cn.edots.nest.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cn.edots.nest.BuildConfig;
import cn.edots.nest.Controller;
import cn.edots.nest.Standardize;
import cn.edots.nest.factory.ControllerProvider;
import cn.edots.nest.log.Logger;
import cn.edots.nest.model.protocol.Protocol;
import cn.edots.nest.model.view.ViewModel;
import cn.edots.slug.core.activity.SlugBinder;


/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */
public abstract class BaseActivity<VM extends ViewModel> extends AppCompatActivity implements View.OnClickListener {

    public static final String EXIT_ACTION = "EXIT_ACTION";
    public static final String FINISH_PARAMETER_INTENT_DATA = "FINISH_PARAMETER_INTENT_DATA";

    public static final String INTENT_DATA = "INTENT_DATA";
    public static final String DEFAULT_BACK_ICON = "BACK_ICON";
    public static final String DEFAULT_DEBUG_MODE = "DEBUG_MODE";
    public static final String VIEW_PROTOCOL = "VIEW_PROTOCOL";

    private Protocol protocol;
    private Controller controller;

    protected final long CURRENT_TIME_MILLIS = System.currentTimeMillis();
    protected final Activity THIS = this;
    protected final String TAG = this.getClass().getSimpleName();
    protected final FinishReceiver finishReceiver = new FinishReceiver();

    protected Logger logger;
    protected SlugBinder sb;
    protected boolean defaultDebugMode = BuildConfig.DEBUG;
    protected Class<VM> clazz;
    protected VM viewModel;

    public BaseActivity() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && isTranslucentStatus()) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (isFeatureNoTitle()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        logger = Logger.getInstance(TAG, defaultDebugMode);
        registerFinishBroadcast();
        init();
    }

    private void init() {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = appInfo.metaData;
            if (metaData != null) {
                defaultDebugMode = metaData.getBoolean(DEFAULT_DEBUG_MODE);
            }
            clazz = (Class<VM>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            if (clazz != null) viewModel = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sb = SlugBinder.getInstance(this, viewModel);
        logger.i("\"鼻涕虫\" 初始化消耗 " + (System.currentTimeMillis() - CURRENT_TIME_MILLIS) + "ms");

        protocol = (Protocol) getIntent().getSerializableExtra(VIEW_PROTOCOL);
        if (protocol != null && protocol.getController() != null)
            try {
                controller = ControllerProvider.get(protocol.getController());
                controller.setViewModel(viewModel);
                controller.setContext(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        if (controller instanceof Standardize) {
            ((Standardize) controller).setupData((Map<String, Object>) getIntent().getSerializableExtra(INTENT_DATA));
            ((Standardize) controller).initView();
            ((Standardize) controller).setListeners();
            ((Standardize) controller).onCreateLast();
        } else if (THIS instanceof Standardize) {
            ((Standardize) THIS).setupData((Map<String, Object>) getIntent().getSerializableExtra(INTENT_DATA));
            ((Standardize) THIS).initView();
            ((Standardize) THIS).setListeners();
            ((Standardize) THIS).onCreateLast();
        }
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
        unregisterFinishBroadcast();
        if (sb != null) sb.finish();
        if (controller != null) controller.destroy();
        controller = null;
        viewModel = null;
        protocol = null;
        sb = null;
        System.gc();
    }

    private void registerFinishBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(EXIT_ACTION);
        registerReceiver(finishReceiver, filter);
    }

    private void unregisterFinishBroadcast() {
        unregisterReceiver(finishReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 4:
                if (isBackAndExit()) onExit();
                else onBack();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onExit() {
        Intent exitIntent = new Intent();
        exitIntent.setAction(EXIT_ACTION);
        THIS.sendBroadcast(exitIntent);
    }

    public void onBack() {
        finish();
    }

    @Override
    public void onClick(View v) {
    }

    public void TOAST(CharSequence message) {
        Toast.makeText(THIS, message, Toast.LENGTH_SHORT).show();
    }

    public void TOAST(CharSequence message, @BaseTransientBottomBar.Duration int duration) {
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

    public void startActivity(Class<? extends Activity> clazz, Protocol protocol) {
        Intent intent = new Intent(THIS, clazz);
        intent.putExtra(VIEW_PROTOCOL, protocol);
        THIS.startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    public boolean isTranslucentStatus() {
        return false;
    }

    public boolean isFeatureNoTitle() {
        return false;
    }

    public boolean isBackAndExit() {
        return false;
    }

    public void finishWith(Collection<String> pages) {
        Intent finishIntent = new Intent();
        finishIntent.setAction(EXIT_ACTION);
        finishIntent.putExtra(FINISH_PARAMETER_INTENT_DATA, new BaseActivity.FinishParameter(pages));
        THIS.sendBroadcast(finishIntent);
    }

    public void finishWith(Class clazz) {
        Intent finishIntent = new Intent();
        finishIntent.setAction(EXIT_ACTION);
        finishIntent.putExtra(FINISH_PARAMETER_INTENT_DATA, new BaseActivity.FinishParameter(null).add(clazz));
        THIS.sendBroadcast(finishIntent);
    }

    public void addFragment(@IdRes int layoutId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(layoutId, fragment, fragment.getClass().getSimpleName()).commit();
    }

    public void removeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public void replaceFragment(@IdRes int layoutId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(layoutId, fragment).commit();
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public Controller getController() {
        return this.controller;
    }

    public VM getViewModel() {
        return this.viewModel;
    }

    //======================================================
    // inner class
    //======================================================
    public static class FinishParameter implements Serializable {

        private static final long serialVersionUID = 2124180411032825937L;

        private final boolean exit;
        private final Collection<String> pages;

        public FinishParameter() {
            this.exit = true;
            this.pages = null;
        }

        public FinishParameter(Collection<String> pages) {
            this.exit = false;
            if (pages == null) pages = new ArraySet<>();
            this.pages = pages;
        }

        public boolean isExit() {
            return exit;
        }

        public Collection<String> getPages() {
            return pages;
        }

        public FinishParameter add(Class clazz) {
            if (pages == null) return null;
            pages.add(clazz.getSimpleName());
            return this;
        }
    }

    class FinishReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (EXIT_ACTION.equals(intent.getAction())) {
                FinishParameter parameter = (FinishParameter) intent.getSerializableExtra(FINISH_PARAMETER_INTENT_DATA);
                if (parameter.isExit()
                        || (parameter.getPages() != null
                        && parameter.getPages().contains(THIS.getClass().getSimpleName())))
                    THIS.finish();
            }
        }
    }

}