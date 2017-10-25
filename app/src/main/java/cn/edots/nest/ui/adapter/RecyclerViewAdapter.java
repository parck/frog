package cn.edots.nest.ui.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @author Parck.
 * @date 2017/10/19.
 * @desc
 */

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private
    @LayoutRes
    int layoutId;
    private
    @LayoutRes
    int[] layoutIds;
    private Context context;
    private List<T> data;

    private ViewHolder holder;

    public RecyclerViewAdapter(Context context, @LayoutRes int layoutId, List<T> data) {
        this.context = context;
        this.layoutId = layoutId;
        this.data = data;
    }

    public RecyclerViewAdapter(Context context, @LayoutRes int[] layoutIds, List<T> data) {
        this.context = context;
        this.layoutIds = layoutIds;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutId != 0) {
            holder = new ViewHolder(context, layoutId, parent);
        } else {
            holder = new ViewHolder(context, layoutIds[viewType], parent);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        binding(holder, data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected abstract void binding(ViewHolder holder, T data, int position);

    //=======================================================================
    // inner class
    //=======================================================================

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> viewContainer;

        public ViewHolder(Context context, @LayoutRes int layoutId, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(layoutId, parent, false));
            this.viewContainer = new SparseArray<>();
        }

        public <V extends View> V findViewById(@IdRes int id) {
            View view = viewContainer.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                viewContainer.put(id, view);
            }
            return (V) view;
        }

        public void setText(@IdRes int id, CharSequence text) {
            ((TextView) findViewById(id)).setText(text);
        }

        public void setOnItemClickListener(View.OnClickListener listener) {
            if (listener != null) this.itemView.setOnClickListener(listener);
        }
    }
}
