package cn.nest.test.ui;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.edots.nest.Holdable;
import cn.edots.nest.Standardize;
import cn.edots.nest.ui.BaseActivity;
import cn.edots.nest.ui.adapter.HoldenAdapter;
import cn.edots.slug.annotation.Slug;
import cn.nest.test.R;
import cn.nest.test.model.data.MainItem;
import cn.nest.test.model.view.MainView;

@Slug(layout = R.layout.activity_main)
public class MainActivity extends BaseActivity<MainView> implements Standardize {

    @Override
    public void setupData(@Nullable Map<String, Object> intentData) {
    }

    @Override
    public void initView() {
        List<Holdable> data = new ArrayList<>();
        data.add(new MainItem("controller 01"));
        data.add(new MainItem("controller 02"));
        getViewModel().recyclerView.setAdapter(new HoldenAdapter(this, R.layout.mian_list_item, data));
    }

    @Override
    public void setListeners() {
    }

    @Override
    public void onCreateLast() {

    }

}
