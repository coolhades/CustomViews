package com.hades.commenviews.customview.sortview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hades.commenviews.R;

import java.util.HashMap;


/**
 * Created by Hades on 2017/4/13.
 * 创建对象
 * 设置图片资源和title【默认降序】
 * 点击事件时调用onClickChangeStatus()
 */

public class SortItem extends FrameLayout {
    private TextView sortTitle;
    private ImageView sortIcon;
    private LinearLayout containerLy;
    HashMap<SortType, String> iconArray = new HashMap<>();

    SortType mSortType = SortType.DOWN_SORT;

    public enum SortType {
        UP_SORT,
        DOWN_SORT,
        UP_SORT_NORMAL,
        DOWN_SORT_NOMAL
    }

    public SortItem(Context context) {
        super(context);
        initViews(context);
    }

    public SortItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    private void initViews(Context context) {
        View.inflate(context, R.layout.custom_sortview_ly, this);
        sortTitle = (TextView) findViewById(R.id.sort_title);
        sortIcon = (ImageView) findViewById(R.id.sort_icon);
        containerLy = (LinearLayout) findViewById(R.id.container_ly);

    }

    public void setSortTitle(String title) {
        sortTitle.setText(title);
    }

    public void setSortTitle(@StringRes int titleId) {
        sortTitle.setText(titleId);
    }

    public void setSortTitleSize(float size){
        sortTitle.setTextSize(size);
    }

    /**
    * 创建时间 2017/5/15
    * auther Hades
    * 描述 设置背景色
    **/
    public void setBackGround(int color){
        containerLy.setBackgroundColor(color);
    }

    /**
     * 创建时间 2017/4/13
     * auther Hades
     * 描述
     *
     * @param sortType 覆盖默认排序方法，默认降序
     **/
    public void setSortType(SortType sortType) {
        mSortType = sortType;
        setSortIcon();//更新图标
    }


    //设置两个Icon resource 分别对应升序降序
    public void setSortIcons(HashMap<SortType, String> array) {
        if (array.size() > 0) {
            iconArray = array;
        }

        setSortIcon();//默认降序
    }

    public void setSortTitleColor(@ColorRes int color){
        sortTitle.setTextColor(getResources().getColor(color));
    }

    /**
    * 创建时间 2017/4/13
    * auther Hades
    * 描述 图标变换 触发点击事件是调用
    **/
    public void onClickChangeStatus() {
        sortTitle.setTextColor(Color.BLACK);
        setSortIcon();
    }

    private void setSortIcon(){
        if (SortType.UP_SORT == mSortType) {
            //升序
            setSortIcon(Integer.valueOf(iconArray.get(SortType.UP_SORT) ));
            mSortType = SortType.DOWN_SORT;
        } else {
            //降序
            setSortIcon(Integer.valueOf(iconArray.get(SortType.DOWN_SORT) ));
            mSortType = SortType.UP_SORT;
        }
    }

    //点击其他Item时 改Item文字和图标需要变更颜色
    public void setUnSort(){
        //文本颜色
        sortTitle.setTextColor(Color.GRAY);
        //之前SortType已经改变，需要改回
        if (SortType.UP_SORT == mSortType){
            mSortType = SortType.DOWN_SORT;
        }else {
            mSortType = SortType.UP_SORT;
        }
        //icon颜色
        if (SortType.UP_SORT == mSortType) {
            //升序 实际是降序图标
            setSortIcon(Integer.valueOf(iconArray.get(SortType.UP_SORT_NORMAL) ));
            mSortType = SortType.DOWN_SORT;//下次点击直接触发排序规则，所以此处需要变更规则
        } else {
            //降序
            setSortIcon(Integer.valueOf(iconArray.get(SortType.DOWN_SORT_NOMAL) ));
            mSortType = SortType.UP_SORT;
        }
    }

    private void setSortIcon(@DrawableRes int resid){
        sortIcon.setImageResource(resid);
    }


}
