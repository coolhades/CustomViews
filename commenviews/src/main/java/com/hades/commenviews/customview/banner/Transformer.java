package com.hades.commenviews.customview.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.hades.commenviews.customview.banner.transformer.AccordionTransformer;
import com.hades.commenviews.customview.banner.transformer.BackgroundToForegroundTransformer;
import com.hades.commenviews.customview.banner.transformer.CubeInTransformer;
import com.hades.commenviews.customview.banner.transformer.CubeOutTransformer;
import com.hades.commenviews.customview.banner.transformer.DefaultTransformer;
import com.hades.commenviews.customview.banner.transformer.DepthPageTransformer;
import com.hades.commenviews.customview.banner.transformer.FlipHorizontalTransformer;
import com.hades.commenviews.customview.banner.transformer.FlipVerticalTransformer;
import com.hades.commenviews.customview.banner.transformer.ForegroundToBackgroundTransformer;
import com.hades.commenviews.customview.banner.transformer.RotateDownTransformer;
import com.hades.commenviews.customview.banner.transformer.RotateUpTransformer;
import com.hades.commenviews.customview.banner.transformer.ScaleInOutTransformer;
import com.hades.commenviews.customview.banner.transformer.StackTransformer;
import com.hades.commenviews.customview.banner.transformer.TabletTransformer;
import com.hades.commenviews.customview.banner.transformer.ZoomInTransformer;
import com.hades.commenviews.customview.banner.transformer.ZoomOutSlideTransformer;
import com.hades.commenviews.customview.banner.transformer.ZoomOutTranformer;


public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
