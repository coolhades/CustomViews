package com.hades.commenviews.customview.status;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hades.commenviews.R;


/**
 * Created by ZHT on 2017/4/15.
 * 加载状态的自定义View
 */

public class NetworkStateView extends LinearLayout {

    //当前的加载状态
    private int mCurrentState;
    private static final int STATE_SUCCESS = 0;
    private static final int STATE_LOADING = 1;
    private static final int STATE_NETWORK_ERROR = 2;
    private static final int STATE_NO_NETWORK = 3;
    private static final int STATE_EMPTY = 4;

    private static final int STATE_EMPTYORDER = 5;//无购买记录
    private static final int STATE_EMPTYSEARCH = 6;//无搜索记录
    private static final int STATE_NEEDADD = 7;//有添加按钮

    //布局添加位置
    private static final int VIEW_POSITION = 0;

    private int mLoadingViewId;

    private int mErrorViewId;
    private int mErrorImageId;
    private String mErrorText;

    private int mNoNetworkViewId;
    private int mNoNetworkImageId;
    private String mNoNetworkText;

    private int mEmptyViewId;
    private int mEmptyImageId;
    private String mEmptyText;

    private int mRefreshViewId;

    //新增状态页面
    //无搜索结果
    private int mEmptySearchViewId;
    private int mEmptySearchImageId;
    private String mEmptySearchText;

    //无购买记录
    private int mEmptyOrderViewId;
    private int mEmptyOrderImageId;
    private String mEmptyOrderText;

    //需要添加内容
    private int mNeedAddViewId;
    private int mNeedAddImageId;
    private String mNeedAddText;


    private final int mSetNetWorkImageId;//按钮图片
    private final int mAddThingsImageId;//添加内容图片


    private int mTextColor;
    private int mTextSize;

    private View mLoadingView;
    private View mErrorView;
    private View mNoNetworkView;
    private View mEmptyView;

    //无搜索结果view
    private View mEmptySearchView;
    private View mEmptyOrderView;
    private View mNeedAddView;

    private LayoutInflater mInflater;
    private ViewGroup.LayoutParams params;

    private OnRefreshListener mRefreshListener;

    public NetworkStateView(@NonNull Context context) {
        this(context, null);
    }

    public NetworkStateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.styleNetworkStateView);
    }

    public NetworkStateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NetworkStateView, defStyleAttr, R.style.NetworkStateView_Style);

        mLoadingViewId = typedArray.getResourceId(R.styleable.NetworkStateView_loadingView, R.layout.view_loading);

        mErrorViewId = typedArray.getResourceId(R.styleable.NetworkStateView_errorView, R.layout.view_network_error);
        mErrorImageId = typedArray.getResourceId(R.styleable.NetworkStateView_nsvErrorImage, NO_ID);
        mErrorText = typedArray.getString(R.styleable.NetworkStateView_nsvErrorText);

        mNoNetworkViewId = typedArray.getResourceId(R.styleable.NetworkStateView_noNetworkView, R.layout.view_no_network);
        mNoNetworkImageId = typedArray.getResourceId(R.styleable.NetworkStateView_nsvNoNetworkImage, NO_ID);
        mNoNetworkText = typedArray.getString(R.styleable.NetworkStateView_nsvNoNetworkText);

        mEmptyViewId = typedArray.getResourceId(R.styleable.NetworkStateView_emptyView, R.layout.view_empty);
        mEmptyImageId = typedArray.getResourceId(R.styleable.NetworkStateView_nsvEmptyImage, NO_ID);
        mEmptyText = typedArray.getString(R.styleable.NetworkStateView_nsvEmptyText);

        mRefreshViewId = typedArray.getResourceId(R.styleable.NetworkStateView_nsvRefreshImage, NO_ID);
        mSetNetWorkImageId = typedArray.getResourceId(R.styleable.NetworkStateView_nsvSetNetWorkImage, NO_ID);
        mAddThingsImageId = typedArray.getResourceId(R.styleable.NetworkStateView_nsvAddThingsImage, NO_ID);

        mTextColor = typedArray.getColor(R.styleable.NetworkStateView_nsvTextColor, 0x8a000000);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.NetworkStateView_nsvTextSize, 14);

        //新增
        mEmptySearchViewId = typedArray.getResourceId(R.styleable.NetworkStateView_emptySearchView, R.layout.view_empty_search);
        mEmptySearchImageId =  typedArray.getResourceId(R.styleable.NetworkStateView_nsvEmptySearchImage, NO_ID);
        mEmptySearchText = typedArray.getString(R.styleable.NetworkStateView_nsvEmptySearchText);

        mEmptyOrderViewId = typedArray.getResourceId(R.styleable.NetworkStateView_emptyOrderView, R.layout.view_empty_order);
        mEmptyOrderImageId =  typedArray.getResourceId(R.styleable.NetworkStateView_nsvEmptyOrderImage, NO_ID);
        mEmptyOrderText = typedArray.getString(R.styleable.NetworkStateView_nsvEmptyOrderText);

        mNeedAddViewId = typedArray.getResourceId(R.styleable.NetworkStateView_needAddView, R.layout.view_needadd);
        mNeedAddImageId =  typedArray.getResourceId(R.styleable.NetworkStateView_nsvNeedAddImage, NO_ID);
        mNeedAddText = typedArray.getString(R.styleable.NetworkStateView_nsvNeedAddText);

        typedArray.recycle();

        mInflater = LayoutInflater.from(context);
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundColor(context.getResources().getColor(R.color.ground));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showSuccess();
    }


    /**
    * 创建时间 2017/6/13
    * auther Hades
    * 描述 隐藏刷新按钮
    **/
    public void hideBtn(){
        ImageView errorRefreshView = (ImageView) mErrorView.findViewById(R.id.refresh_view);
        errorRefreshView.setVisibility(INVISIBLE);
    }

    /**
     * 加载成功，隐藏加载状态的View
     */
    public void showSuccess() {
        mCurrentState = STATE_SUCCESS;
        showViewByState(mCurrentState);
    }

    /**
     * 显示加载中的状态
     */
    public void showLoading() {
        mCurrentState = STATE_LOADING;
        if (null == mLoadingView) {
            mLoadingView = mInflater.inflate(mLoadingViewId, null);
            addView(mLoadingView, VIEW_POSITION, params);
        }
        showViewByState(mCurrentState);
    }

    /**
     * 显示加载失败(网络错误)状态
     */
    public void showError() {
        mCurrentState = STATE_NETWORK_ERROR;
        if (null == mErrorView) {
            mErrorView = mInflater.inflate(mErrorViewId, null);
            ImageView errorImage = (ImageView) mErrorView.findViewById(R.id.error_image);
            TextView errorText = (TextView) mErrorView.findViewById(R.id.error_text);
            ImageView errorRefreshView = (ImageView) mErrorView.findViewById(R.id.refresh_view);

            image(errorImage, mErrorImageId);

            text(errorText, mErrorText);

            image(errorRefreshView, mRefreshViewId);

            if (null != errorRefreshView) {
                errorRefreshView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mRefreshListener) {
                            mRefreshListener.onRefresh();
                        }
                    }
                });
            }
            addView(mErrorView, VIEW_POSITION, params);
        }
        showViewByState(mCurrentState);
    }

    /**
     * 显示没有网络状态
     */
    public void showNoNetwork() {
        mCurrentState = STATE_NO_NETWORK;
        if (null == mNoNetworkView) {
            mNoNetworkView = mInflater.inflate(mNoNetworkViewId, null);
            ImageView noNetworkImage = (ImageView) mNoNetworkView.findViewById(R.id.no_network_image);
            TextView noNetworkText = (TextView) mNoNetworkView.findViewById(R.id.no_network_text);
            ImageView networkRefreshView = (ImageView) mNoNetworkView.findViewById(R.id.refresh_view);

            image(noNetworkImage, mNoNetworkImageId);

            text(noNetworkText, mNoNetworkText);

            image(networkRefreshView, mSetNetWorkImageId);

            if (null != networkRefreshView) {
                networkRefreshView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mRefreshListener) {
                            mRefreshListener.onSetNetWork();
                        }
                    }
                });
            }
            addView(mNoNetworkView, VIEW_POSITION, params);
        }
        showViewByState(mCurrentState);
    }

    /**
     * 显示无数据状态
     */
    public void showEmpty() {
        mCurrentState = STATE_EMPTY;
        if (null == mEmptyView) {
            mEmptyView = mInflater.inflate(mEmptyViewId, null);
            ImageView emptyImage = (ImageView) mEmptyView.findViewById(R.id.empty_image);
            TextView emptyText = (TextView) mEmptyView.findViewById(R.id.empty_text);
            ImageView emptyRefreshView = (ImageView) mEmptyView.findViewById(R.id.refresh_view);

            image(emptyImage, mEmptyImageId);

            text(emptyText, mEmptyText);

            image(emptyRefreshView, mRefreshViewId);

            if (null != emptyRefreshView) {
                emptyRefreshView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mRefreshListener) {
                            mRefreshListener.onRefresh();
                        }
                    }
                });
            }
            addView(mEmptyView, VIEW_POSITION, params);
        }
        showViewByState(mCurrentState);
    }


    /**
     * 显示无搜索数据状态
     */
    public void showEmptySearch() {
        mCurrentState = STATE_EMPTYSEARCH;
        if (null == mEmptySearchView) {
            mEmptySearchView = mInflater.inflate(mEmptySearchViewId, null);
            ImageView errorImage = (ImageView) mEmptySearchView.findViewById(R.id.empty_search_image);
            TextView errorText = (TextView) mEmptySearchView.findViewById(R.id.empty_search_text);
            ImageView errorRefreshView = (ImageView) mEmptySearchView.findViewById(R.id.refresh_view);

            image(errorImage, mEmptySearchImageId);
            text(errorText, mEmptySearchText);
            image(errorRefreshView, mRefreshViewId);

            if (null != errorRefreshView) {
                errorRefreshView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mRefreshListener) {
                            mRefreshListener.onRefresh();
                        }
                    }
                });
            }
            addView(mEmptySearchView, VIEW_POSITION, params);
        }
        showViewByState(mCurrentState);
    }

    /**
     * 显示无搜索数据状态
     */
    public void showEmptyOrder() {
        mCurrentState = STATE_EMPTYORDER;
        if (null == mEmptyOrderView) {
            mEmptyOrderView = mInflater.inflate(mEmptyOrderViewId, null);
            ImageView errorImage = (ImageView) mEmptyOrderView.findViewById(R.id.empty_order_image);
            TextView errorText = (TextView) mEmptyOrderView.findViewById(R.id.empty_order_text);
            ImageView errorRefreshView = (ImageView) mEmptyOrderView.findViewById(R.id.refresh_view);

            image(errorImage, mEmptyOrderImageId);
            text(errorText, mEmptyOrderText);
            image(errorRefreshView, mRefreshViewId);

            if (null != errorRefreshView) {
                errorRefreshView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mRefreshListener) {
                            mRefreshListener.onRefresh();
                        }
                    }
                });
            }
            addView(mEmptyOrderView, VIEW_POSITION, params);
        }
        showViewByState(mCurrentState);
    }

    /**
     * 显示无搜索数据状态
     */
    public void showNeedAdd() {
        mCurrentState = STATE_NEEDADD;
        if (null == mNeedAddView) {
            mNeedAddView = mInflater.inflate(mNeedAddViewId, null);
            ImageView errorImage = (ImageView) mNeedAddView.findViewById(R.id.needadd_image);
            TextView errorText = (TextView) mNeedAddView.findViewById(R.id.needadd_text);
            ImageView errorRefreshView = (ImageView) mNeedAddView.findViewById(R.id.refresh_view);

            image(errorImage, mNeedAddImageId);

            text(errorText, mNeedAddText);

            image(errorRefreshView, mAddThingsImageId);

            if (null != errorRefreshView) {
                errorRefreshView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mRefreshListener) {
                            mRefreshListener.onRefresh();
                        }
                    }
                });
            }
            addView(mNeedAddView, VIEW_POSITION, params);
        }
        showViewByState(mCurrentState);
    }


    private void showViewByState(int state) {

        //如果当前状态为加载成功，隐藏此View，反之显示
        this.setVisibility(state == STATE_SUCCESS ? View.GONE : View.VISIBLE);

        if (null != mLoadingView) {
            mLoadingView.setVisibility(state == STATE_LOADING ? View.VISIBLE : View.GONE);
        }

        if (null != mErrorView) {
            mErrorView.setVisibility(state == STATE_NETWORK_ERROR ? View.VISIBLE : View.GONE);
        }

        if (null != mNoNetworkView) {
            mNoNetworkView.setVisibility(state == STATE_NO_NETWORK ? View.VISIBLE : View.GONE);
        }

        if (null != mEmptyView) {
            mEmptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.GONE);
        }
        //新增
        if (null != mEmptySearchView) {
            mEmptySearchView.setVisibility(state == STATE_EMPTYSEARCH ? View.VISIBLE : View.GONE);
        }

        if (null != mEmptyOrderView) {
            mEmptyOrderView.setVisibility(state == STATE_EMPTYORDER ? View.VISIBLE : View.GONE);
        }
        if (null != mNeedAddView) {
            mNeedAddView.setVisibility(state == STATE_NEEDADD ? View.VISIBLE : View.GONE);
        }
    }

    private void image(ImageView view, int imageId) {
        if (null != view && imageId != NO_ID) {
            view.setImageResource(imageId);
        }
    }

    private void text(TextView view, String str) {
        if (null != view && !TextUtils.isEmpty(str)) {
            view.setText(str);
            view.setTextColor(mTextColor);
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        }
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mRefreshListener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();
        void onSetNetWork();
    }
}
