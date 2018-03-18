package cn.nest.test.controller;

import cn.edots.nest.Controller;
import cn.nest.test.model.view.TestView;
import cn.nest.test.ui.TestActivity;

/**
 * Created by parck on 2018/3/17.
 */

public class Test02Controller extends Controller<TestView> {

    private static final long serialVersionUID = 3199800018208904394L;

    @Override
    public void initialize() {
        getViewModel().titleView.setText("Controller 02");
        ((TestActivity) getContext()).setCenterTitleContent("Test01Controller");
    }

    @Override
    public void restore() {

    }

    @Override
    public void destroy() {

    }
}
