package cn.nest.test.model.view;

import cn.edots.nest.model.view.ViewModel;
import cn.edots.nest.ui.widget.SwipeRefreshLayout;
import cn.edots.nest.ui.widget.VerticalRecyclerView;
import cn.edots.slug.annotation.FindView;
import cn.nest.test.R;

/**
 * Created by parck on 2018/3/17.
 */

public class MainView extends ViewModel {

    private static final long serialVersionUID = -650434546228044450L;

    @FindView(R.id.refresh_layout)
    public SwipeRefreshLayout refreshLayout;

    @FindView(R.id.recycle_view)
    public VerticalRecyclerView recyclerView;

}
