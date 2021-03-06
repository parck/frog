package cn.edots.nest.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import cn.edots.nest.BuildConfig;
import cn.edots.nest.Controller;
import cn.edots.nest.R;
import cn.edots.nest.Standardize;
import cn.edots.nest.factory.ControllerProvider;
import cn.edots.nest.log.Logger;
import cn.edots.nest.model.protocol.Protocol;
import cn.edots.nest.model.view.ViewModel;
import cn.edots.nest.ui.BaseActivity;
import cn.edots.slug.core.fragment.SlugBinder;

import static cn.edots.nest.ui.BaseActivity.EXIT_ACTION;
import static cn.edots.nest.ui.BaseActivity.FINISH_PARAMETER_INTENT_DATA;
import static cn.edots.nest.ui.BaseActivity.INTENT_DATA;
import static cn.edots.nest.ui.BaseActivity.VIEW_PROTOCOL;

/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */

public abstract class BaseFragment<VM extends ViewModel> extends Fragment implements View.OnClickListener {

    protected final String TAG = this.getClass().getSimpleName();
    protected static final String DEFAULT_BACK_ICON = "BACK_ICON";
    protected static final String DEFAULT_DEBUG_MODE = "DEBUG_MODE";

    protected long CURRENT_TIME_MILLIS;
    protected Fragment THIS;
    protected Activity activity;
    protected View rootView;
    protected SlugBinder sb;
    protected Logger logger;
    protected
    @DrawableRes
    int defaultBackIconRes = R.drawable.default_back_icon;
    protected boolean defaultDebugMode = BuildConfig.DEBUG;
    protected VM viewModel;
    protected Class<VM> clazz;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        THIS = this;
        activity = THIS.getActivity();
        try {
            ApplicationInfo appInfo = getActivity().getPackageManager().getApplicationInfo(getActivity().getPackageName(), PackageManager.GET_META_DATA);
            Bundle metaData = appInfo.metaData;
            if (metaData != null) {
                defaultBackIconRes = metaData.getInt(DEFAULT_BACK_ICON);
                defaultDebugMode = metaData.getBoolean(DEFAULT_DEBUG_MODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger = Logger.getInstance(TAG, defaultDebugMode);
        CURRENT_TIME_MILLIS = System.currentTimeMillis();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        try {
            clazz = (Class<VM>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            if (clazz != null) viewModel = clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        sb = SlugBinder.getInstance(THIS, container, viewModel);
        rootView = sb.getContentView();
        logger.i("\"鼻涕虫\" 初始化消耗 " + (System.currentTimeMillis() - CURRENT_TIME_MILLIS) + "ms");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (THIS instanceof Standardize) {
            ((Standardize) THIS).setupData((Map<String, Object>) activity.getIntent().getSerializableExtra(INTENT_DATA));
            ((Standardize) THIS).initView();
            ((Standardize) THIS).setListeners();
            ((Standardize) THIS).onCreateLast();
        }
    }

    public <V extends View> V findViewById(@IdRes int id) {
        return (V) sb.getContentView().findViewById(id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sb != null) sb.finish();
    }

    @Override
    public void onClick(View v) {
    }

    public void TOAST(CharSequence message) {
        Toast.makeText(THIS.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void TOAST(CharSequence message, @BaseTransientBottomBar.Duration int duration) {
        Toast.makeText(THIS.getActivity(), message, duration).show();
    }

    public void startActivity(Class<? extends Activity> clazz) {
        THIS.startActivity(new Intent(THIS.getActivity(), clazz));
    }

    public void startActivity(Class<? extends Activity> clazz, HashMap<String, Object> data) {
        Intent intent = new Intent(THIS.getActivity(), clazz);
        intent.putExtra(INTENT_DATA, data);
        THIS.startActivity(intent);
    }

    public boolean isBackAndExit() {
        return false;
    }

    public void onBack() {
        this.getActivity().finish();
    }

    public void onExit() {
        Intent finishIntent = new Intent();
        finishIntent.setAction(EXIT_ACTION);
        finishIntent.putExtra(FINISH_PARAMETER_INTENT_DATA, new BaseActivity.FinishParameter());
        THIS.getActivity().sendBroadcast(finishIntent);
    }

    public VM getViewModel() {
        return this.viewModel;
    }
}
