package cn.edots.nest.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import cn.edots.nest.core.SlugResourceProvider;
import cn.edots.nest.core.Standardize;
import cn.edots.nest.log.Logger;
import cn.edots.slug.core.fragment.SlugBinder;

/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected final String TAG = this.getClass().getSimpleName();
    protected static final String EXIT_ACTION = "EXIT_ACTION";
    protected static final String INTENT_DATA = "INTENT_DATA";

    protected Fragment THIS;
    protected Activity activity;
    protected Logger logger;
    protected SlugBinder sbinder;
    protected EventBus eventBus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        THIS = this;
        activity = THIS.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        try {
            SlugResourceProvider resourceProvider = (SlugResourceProvider) activity.getApplication();
            logger = new Logger(TAG, resourceProvider.isDebug());
        } catch (ClassCastException e) {
            throw new ClassCastException("This application has not to implements \"SlugResourceProvider\"");
        }
        sbinder = SlugBinder.getInstance(THIS, container);
        eventBus = EventBus.getDefault();
        logger.i("SlugBinder Init Successful!");
        return sbinder.getContentView();
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
        return (V) sbinder.getContentView().findViewById(id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sbinder.finish();
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

    protected boolean isBackToExit() {
        return false;
    }

    protected void onBack() {
        this.getActivity().finish();
    }

    protected void onExit() {
        Intent exitIntent = new Intent();
        exitIntent.setAction(EXIT_ACTION);
        THIS.getActivity().sendBroadcast(exitIntent);
    }
}
