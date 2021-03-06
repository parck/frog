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

    protected
    @LayoutRes
    int layoutId;
    protected
    @LayoutRes
    int[] layoutIds;
    protected Context context;
    protected List<T> data;

    protected ViewHolder holder;

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
            holder.setAdapter(this);
        } else {
            holder = new ViewHolder(context, layoutIds[viewType], parent);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        binding(holder, data.get(position), position);
        if (position + 1 == data.size()) onLastItemVisible(getItemViewType(position));
    }

    protected void onLastItemVisible(int type) {
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Context getContext() {
        return this.context;
    }

    public ViewHolder getHolder() {
        return holder;
    }

    public void setHolder(ViewHolder holder) {
        this.holder = holder;
    }

    protected abstract void binding(ViewHolder holder, T data, int position);

    //=======================================================================
    // inner class
    //=======================================================================

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> viewContainer;
        private Context context;
        private RecyclerViewAdapter adapter;

        public ViewHolder(Context context, @LayoutRes int layoutId, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(layoutId, parent, false));
            this.context = context;
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

        public Context getContext() {
            return this.context;
        }

        public void setAdapter(RecyclerViewAdapter adapter) {
            this.adapter = adapter;
        }

        public RecyclerViewAdapter getAdapter() {
            return adapter;
        }
    }
}
