package com.hades.commenviews.customview.recycler;

import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.hades.commenviews.R;
import com.hades.commenviews.customview.SHSwipeRefreshLayout;

/**
 * Created by Hades on 2017/5/17.
 */

public class RushRecyclerView extends FrameLayout implements IFetchData{

    private int mPageSize = 10;
    private int mCurrentPage = 1;//默认第一页

    private SHSwipeRefreshLayout swipeLy;
    private RecyclerView recyclerview;

    public RushRecyclerView(@NonNull Context context) {
        super(context);
        initViews(context);
    }

    public RushRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public RushRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RushRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
    }

    private void initViews(Context context) {
        View.inflate(context, R.layout.rushrecycler_ly, this);
        swipeLy = (SHSwipeRefreshLayout) findViewById(R.id.swipe_ly);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        initData();
        initEvent();
    }

    //注入 分页总数
    public void setPageSize(int pageSize){
        this.mPageSize = pageSize;
    }


    /**
    * 创建时间 2017/5/17
    * auther Hades
    * 描述 开启下拉 上拉
    **/
    public void setRefreshEnable(boolean enabled){
        swipeLy.setRefreshEnable(enabled);
    }

    public void setLoadMoreEnable(boolean enabled){
        swipeLy.setLoadmoreEnable(enabled);
    }


    /**
    * 创建时间 2017/5/17
    * auther Hades
    * 描述
     * @param layoutId View 布局
    **/
    public void setHeader(@LayoutRes int layoutId){
        swipeLy.setHeaderView(layoutId);
    }

    public void setHeaderView(View v){
        swipeLy.setHeaderView(v);
    }

    /**
     * 创建时间 2017/5/17
     * auther Hades
     * 描述
     * @param layoutId View 布局
     **/
    public void setFooter(@LayoutRes int layoutId){
        swipeLy.setFooterView(layoutId);
    }

    public void setFootView(View view){
        swipeLy.setFooterView(view);
    }

    public void setNotifyAnimation(boolean enabled){
        ((SimpleItemAnimator)recyclerview.getItemAnimator()).setSupportsChangeAnimations(enabled);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        recyclerview.setLayoutManager(layoutManager);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration){
        recyclerview.addItemDecoration(itemDecoration);
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener scrollListener){
        if (swipeLy.isLoadmoreEnable()){
            swipeLy.setLoadmoreEnable(false);
        }
        recyclerview.addOnScrollListener(scrollListener);
    }

    /**
     * 创建时间 2017/5/17
     * auther Hades
     * 描述 IFetchData 接口
     * 当 loadMore 数据请求成功时调用
     **/
    @Override
    public void onSucceed() {
        mCurrentPage++;
    }


    /**
    * 创建时间 2017/5/17
    * auther Hades
    * 描述 结束刷新和加载
    **/
    public void finishRefresh(){
        swipeLy.finishRefresh();
    }

    public void finishLoadMore(){
        swipeLy.finishLoadmore();
    }

    /*
    * 内部方法
    */
    private void initData() {

    }

    private void initEvent() {
        swipeLy.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                if (null != onRefreshListener) {
                    onRefreshListener.onRefresh();
                }
            }

            @Override
            public void onLoading() {
                if (mCurrentPage > mPageSize){
                    //已经到底 设置footer文字
                    onRefreshListener.onLoadLast("已经到底");
                }else {
                    onRefreshListener.onLoading(mCurrentPage);
                }
            }

            @Override
            public void onRefreshPulStateChange(float percent, int state) {
                if (null != onRefreshListener) {
                    onRefreshListener.onRefreshPulStateChange(percent, state);
                }
                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:

                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT://松开执行load动作

                        break;
                    case SHSwipeRefreshLayout.START:

                        break;
                }
            }

            @Override
            public void onLoadmorePullStateChange(float percent, int state) {
                if (null != onRefreshListener) {
                    onRefreshListener.onLoadmorePullStateChange(percent, state);
                }
                switch (state) {
                    case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:

                        break;
                    case SHSwipeRefreshLayout.OVER_TRIGGER_POINT://松开执行load动作

                        break;
                    case SHSwipeRefreshLayout.START:

                        break;
                }
            }
        });



    }


    RushOnRefreshListener onRefreshListener;

    public void setOnRefreshListener(RushOnRefreshListener onRefreshListener) {
        if (null != onRefreshListener) {
            this.onRefreshListener = onRefreshListener;
        }
    }

    RushOnScrollListener onScrollListener;

    public void setOnScrollListener(RushOnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public void addOnScrollListener(){
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                onScrollListener.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onScrollListener.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public interface RushOnScrollListener{
        void onScrollStateChanged(RecyclerView recyclerView, int newState);
        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }



    public interface RushOnRefreshListener{
        /**
        * 创建时间 2017/5/17
        * auther Hades
        * 描述 刷新数据
        **/
        void onRefresh();
        /**
         * 创建时间 2017/5/17
         * auther Hades
         * 描述 刷新时 header的状态
         **/
        void onRefreshPulStateChange(float percent, int state);
        /**
        * 创建时间 2017/5/17
        * auther Hades
        * 描述 请求数据
        **/
        void onLoading(int page);
        /**
        * 创建时间 2017/5/17
        * auther Hades
        * 描述 上拉时 footer的状态
        **/
        void onLoadmorePullStateChange(float percent, int state);
        /**
        * 创建时间 2017/5/17
        * auther Hades
        * 描述 已经到底
        **/
        void onLoadLast(String data);
    }




}
