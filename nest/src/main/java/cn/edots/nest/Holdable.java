package cn.edots.nest;

import java.io.Serializable;

import cn.edots.nest.ui.adapter.RecyclerViewAdapter;

/**
 * @author Parck.
 * @date 2017/12/6.
 * @desc
 */

public interface Holdable extends Serializable {

    void holding(RecyclerViewAdapter.ViewHolder holder);

}
