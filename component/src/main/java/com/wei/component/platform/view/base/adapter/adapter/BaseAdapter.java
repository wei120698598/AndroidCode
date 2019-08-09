package com.wei.component.platform.view.base.adapter.adapter;

import androidx.annotation.CallSuper;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<VH extends BaseViewHolder, T>
        extends RecyclerView.Adapter<VH>
        implements IHeaderAdapter, IFooterAdapter {
    private ObservableList<T> dataList;

    @Nullable
    protected RecyclerView recyclerView;

    @CallSuper
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @CallSuper
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = null;
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getData().size() + getFooterCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public int getDataViewType(int dataPosition) {
        return getItemViewType(getHeaderCount() + dataPosition);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    //<editor-fold desc="数据操作">  ------------------------------------------------------------------------

    public ObservableList<T> getData() {
        return dataList;
    }

    public int getDataSize() {
        return dataList == null ? 0 : dataList.size();
    }

    public boolean isDataEmpty() {
        return dataList == null || dataList.size() == 0;
    }


    private final ObservableList.OnListChangedCallback<ObservableList<T>> dataChangeCallback =
            new ObservableList.OnListChangedCallback<ObservableList<T>>() {
                @Override
                public void onChanged(ObservableList<T> sender) {
                    int start = getHeaderCount();
                    notifyItemRangeChanged(start, getDataSize());
                }

                @Override
                public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                    int start = getHeaderCount() + positionStart;
                    notifyItemRangeChanged(start, itemCount);
                }

                @Override
                public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                    int start = getHeaderCount() + positionStart;
                    notifyItemRangeInserted(start, itemCount);
                }

                @Override
                public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition,
                                             int itemCount) {
                    int from = getHeaderCount() + fromPosition;
                    int to = getHeaderCount() + toPosition;
                    notifyItemMoved(from, to);
                }

                @Override
                public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                    int start = getHeaderCount() + positionStart;
                    notifyItemRangeRemoved(start, itemCount);
                }
            };


    /**
     * @param isRefresh 是否下拉刷新
     */
    public boolean bindData(boolean isRefresh, @NonNull final List<? extends T> data) {
        if (data.size() == 0) {
            return true;
        }
        //下拉刷新
        if (isRefresh) {
            //避免动画闪屏
            getData().removeOnListChangedCallback(dataChangeCallback);
            getData().clear();
            notifyDataSetChanged();
            getData().addOnListChangedCallback(dataChangeCallback);
            return getData().addAll(data);
        } else {
            //上拉加载 不能为空,并且不包含
            getData().addAll(data);
        }
        return false;
    }

    /**
     * 对比后刷新，适合下拉刷新
     */
    public void diffDataUpdate(@NonNull final List<? extends T> data) {
        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return dataList.size();
            }

            @Override
            public int getNewListSize() {
                return data.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return dataList.get(oldItemPosition) == data.get(newItemPosition);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return dataList.get(oldItemPosition).equals(data.get(newItemPosition));
            }
        }).dispatchUpdatesTo(this);
    }

    public void clearData() {
        getData().clear();
    }


    /**
     * @param index 相对于List的位置
     */
    public T getItem(@IntRange(from = 0) int index) {
        return getData().get(index);
    }

    /**
     * 获取角标
     */
    public int getIndex(@NonNull T t) {
        return getData().indexOf(t);
    }

    /**
     * @param index 相对于List的位置
     */
    public final void addItem(@IntRange(from = 0) int index, @Nullable T t) {
        getData().add(index, t);
    }

    /**
     * 添加多个Item
     */
    public final void addItem(@IntRange(from = 0) int index, @Nullable ArrayList<T> t) {
        if (t != null) {
            getData().addAll(index, t);
        }
    }

    public final boolean addItem(@NonNull T t) {
        return getData().add(t);
    }

    /**
     * 更新item
     */
    public final T updateItem(@NonNull T t) {
        return getData().set(getIndex(t), t);
    }

    /**
     * @param index 相对于List的位置
     */
    public final T removeItem(@IntRange(from = 0) int index) {
        return getData().remove(index);
    }

    /**
     * 移除item
     */
    public final boolean removeItem(@NonNull T t) {
        return getData().remove(t);
    }

    public interface OnItemClickListener<T> {
        void onItemClick(BaseViewHolder viewHolder, T data, int position);
    }
}
