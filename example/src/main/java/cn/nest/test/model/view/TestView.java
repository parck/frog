package cn.nest.test.model.view;

import android.widget.TextView;

import cn.edots.nest.model.view.ViewModel;
import cn.edots.slug.annotation.FindView;
import cn.nest.test.R;

/**
 * Created by parck on 2018/3/17.
 */

public class TestView extends ViewModel {

    @FindView(R.id.title_view)
    public TextView titleView;
}
