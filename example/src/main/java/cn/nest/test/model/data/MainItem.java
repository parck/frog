package cn.nest.test.model.data;

import android.view.View;

import cn.edots.nest.Holdable;
import cn.edots.nest.ui.adapter.RecyclerViewAdapter;
import cn.nest.test.R;
import cn.nest.test.controller.Test01Controller;
import cn.nest.test.controller.Test02Controller;
import cn.nest.test.model.protocol.TestProtocol;
import cn.nest.test.ui.MainActivity;
import cn.nest.test.ui.TestActivity;

/**
 * Created by parck on 2018/3/17.
 */

public class MainItem implements Holdable {

    private static final long serialVersionUID = 6246077893958674845L;

    public String name;

    public MainItem() {
    }

    public MainItem(String name) {
        this.name = name;
    }

    @Override
    public void holding(final RecyclerViewAdapter.ViewHolder holder) {
        holder.setText(R.id.item_text, name);
        holder.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestProtocol protocol = new TestProtocol();
                switch (holder.getPosition()) {
                    case 0:
                        protocol.setController(Test01Controller.class);
                        ((MainActivity) holder.getContext()).startActivity(TestActivity.class, protocol);
                        break;
                    case 1:
                        protocol.setController(Test02Controller.class);
                        ((MainActivity) holder.getContext()).startActivity(TestActivity.class, protocol);
                        break;
                }
            }
        });
    }
}
