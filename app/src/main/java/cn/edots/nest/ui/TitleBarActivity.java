package cn.edots.nest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.edots.nest.R;
import cn.edots.slug.annotation.Slug;

/**
 * @author Parck.
 * @date 2017/9/28.
 * @desc
 */
@Slug(layout = R.layout.activity_base_title_bar)
public abstract class TitleBarActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
