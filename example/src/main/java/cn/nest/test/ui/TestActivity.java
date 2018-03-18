package cn.nest.test.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.edots.nest.ui.TitleBarActivity;
import cn.edots.slug.annotation.Slug;
import cn.nest.test.R;
import cn.nest.test.model.view.TestView;

/**
 * Created by parck on 2018/3/17.
 */

@Slug(layout = R.layout.activity_test)
public class TestActivity extends TitleBarActivity<TestView> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCenterTitleContent("Test Controller");
    }
}
