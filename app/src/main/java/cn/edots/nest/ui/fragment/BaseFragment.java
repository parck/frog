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

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected final String TAG = this.getClass().getSimpleName();
    protected static final String DEFAULT_BACK_ICON = "BACK_ICON";
    protected static final String DEFAULT_DEBUG_MODE = "DEBUG_MODE";

    private Protocol protocol;
    private Controller controller;

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
    private ViewModel viewModel;

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
        viewModel = getViewModel();
        sb = SlugBinder.getInstance(THIS, container, viewModel);
        rootView = sb.getContentView();
        logger.i("\"鼻涕虫\" 初始化消耗 " + (System.currentTimeMillis() - CURRENT_TIME_MILLIS) + "ms");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        protocol = (Protocol) getActivity().getIntent().getSerializableExtra(VIEW_PROTOCOL);
        if (protocol.getController() != null)
            try {
                ControllerProvider.get(protocol.getController());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
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

    protected void TOAST(CharSequence message) {
        Toast.makeText(THIS.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void TOAST(CharSequence message, @BaseTransientBottomBar.Duration int duration) {
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

    protected boolean isBackAndExit() {
        return false;
    }

    protected void onBack() {
        this.getActivity().finish();
    }

    protected void onExit() {
        Intent finishIntent = new Intent();
        finishIntent.setAction(EXIT_ACTION);
        finishIntent.putExtra(FINISH_PARAMETER_INTENT_DATA, new BaseActivity.FinishParameter());
        THIS.getActivity().sendBroadcast(finishIntent);
    }

    public <T extends Protocol> T getProtocol(Class<T> clazz) {
        return (T) protocol;
    }

    public <T extends Controller> T getController(Class<T> clazz) {
        return (T) controller;
    }

    public abstract ViewModel getViewModel();
}
