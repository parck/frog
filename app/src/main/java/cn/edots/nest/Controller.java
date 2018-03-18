package cn.edots.nest;

import java.io.Serializable;

import cn.edots.nest.model.view.ViewModel;

/**
 * @Author WETOOP
 * @Date 2018/3/16.
 * @Description
 */

public abstract class Controller<VM extends ViewModel> implements Serializable {

    protected VM viewModel;

    public abstract void initialize();

    public abstract void restore();

    public abstract void destroy();

    public VM getViewModel() {
        return viewModel;
    }

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }
}