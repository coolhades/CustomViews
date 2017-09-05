package com.hades.commenviews.customview.sortview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hades.commenviews.R;

/**
 * Created by Hades on 2017/7/10.
 */

public class TabSortView extends LinearLayout {
    //顶部菜单Item布局
    LinearLayout menuItemLayout;
    //弹出菜单布局
    FrameLayout menuLayout;
    //半透明遮罩
    View maskView;
    //当前tab索引
    int currentTabIndex = -1;
    //左右分割线颜色
    private int dividerColor = 0xffcccccc;
    //tab选中颜色
    private int textSelectedColor = 0xff890c85;
    //tab未选中颜色
    private int textUnselectedColor = 0xff111111;
    //遮罩颜色
    private int maskColor = 0x88888888;
    //tab字体大小
    private int menuTextSize = 14;
    //tab选中图标
    private int menuSelectedIcon;
    //tab未选中图标
    private int menuUnselectedIcon;
    //图标和文字的padding
    private float paddind;


    public TabSortView(Context context) {
        this(context, null);
    }

    public TabSortView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabSortView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TabSortView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //初始化
    private void initView(Context context) {
        setOrientation(VERTICAL);
        //为DropDownMenu添加自定义属性
        int menuBackgroundColor = 0xffffffff;
        int underlineColor = 0xffcccccc;
        float menuHeigh = FrameLayout.LayoutParams.MATCH_PARENT;

        //初始化tabMenuView并添加到tabMenuView
        menuItemLayout = new LinearLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuItemLayout.setOrientation(HORIZONTAL);
        menuItemLayout.setBackgroundColor(menuBackgroundColor);
        menuItemLayout.setLayoutParams(params);
        addView(menuItemLayout, 0);

        //为tabMenuView添加下划线
        View underLine = new View(getContext());
        underLine.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpTpPx(1.0f)));
        underLine.setBackgroundColor(underlineColor);
        addView(underLine, 1);

        //初始化menuLayout,
        menuLayout = new FrameLayout(context);
        menuLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dpTpPx(menuHeigh) ));
        addView(menuLayout, 2);

        maskView = new View(getContext());
        maskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 0, 1));
        maskView.setBackgroundColor(maskColor);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        addView(maskView, 3);
    }




    private void closeMenu() {
        if (currentTabIndex != -1) {
            ((SortItem) menuItemLayout.getChildAt(currentTabIndex)).setSortTitleColorInt(textUnselectedColor);
            ((TextView) menuItemLayout.getChildAt(currentTabIndex)).setCompoundDrawablesWithIntrinsicBounds(null, null,
                    getResources().getDrawable(menuUnselectedIcon), null);
            menuLayout.setVisibility(View.GONE);
            menuLayout.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
            maskView.setVisibility(GONE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
            currentTabIndex = -1;
        }
    }


    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }
}
