package cn.nest.test.controller;

import android.support.annotation.Nullable;

import java.util.Map;

import cn.edots.nest.Controller;
import cn.edots.nest.Standardize;
import cn.nest.test.model.view.TestView;
import cn.nest.test.ui.TestActivity;

/**
 * Created by parck on 2018/3/17.
 */

public class Test01Controller extends Controller<TestView> implements Standardize {

    private static final long serialVersionUID = 2724156953538565008L;


    @Override
    public void setupData(@Nullable Map<String, Object> intentData) {
        viewModel.titleView.setText("Controller 01");
        ((TestActivity) getContext()).setCenterTitleContent("Test01Controller");
    }

    @Override
    public void initView() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onCreateLast() {

    }
}

