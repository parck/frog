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

public class Test02Controller extends Controller<TestView> implements Standardize {

    private static final long serialVersionUID = 3199800018208904394L;

    @Override
    public void setupData(@Nullable Map<String, Object> intentData) {
        getViewModel().titleView.setText("Controller 02");
        ((TestActivity) getContext()).setCenterTitleContent("Test02Controller");
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
