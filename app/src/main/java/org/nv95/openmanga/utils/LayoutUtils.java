package org.nv95.openmanga.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.nv95.openmanga.items.ThumbSize;

/**
 * Created by nv95 on 26.01.16.
 */
public class LayoutUtils {

    public static boolean isTablet(Context context) {
        return context.getResources().getConfiguration().isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE);
    }

    public static boolean isTabletLandscape(Context context) {
        return isTablet(context) && context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static int getScreenSize(Context context) {
        return context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }

    public static Float[] getScreenSizeDp(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = activity.getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth = outMetrics.widthPixels / density;
        return new Float[]{dpHeight, dpWidth};
    }

    public static int DpToPx(Resources res, float dp) {
        float density = res.getDisplayMetrics().density;
        return (int) (dp * density);
    }

    @Deprecated
    public static int getOptimalColumnsCount(Activity activity, int sizeDp) {
        float width = LayoutUtils.getScreenSizeDp(activity)[1];
        int count = (int) (width / sizeDp);
        return count == 0 ? 1 : count;
    }

    public static int getOptimalColumnsCount(Resources resources, ThumbSize thumbSize) {
        float width = resources.getDisplayMetrics().widthPixels;
        int count = Math.round(width / (thumbSize.getWidth() + DpToPx(resources, 8)));
        return count == 0 ? 1 : count;
    }

    public static void setAllImagesColor(ViewGroup container, @ColorRes int colorId) {
        int color = ContextCompat.getColor(container.getContext(), colorId);
        View o;
        for (int i = container.getChildCount() - 1;i >= 0;i--) {
            o = container.getChildAt(i);
            if (o instanceof ImageView) {
                ((ImageView) o).setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            } else if (o instanceof TextView) {
                for (Drawable d : ((TextView)o).getCompoundDrawables()) {
                    if (d != null) {
                        d.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                    }
                }
            } else if (o instanceof ViewGroup) {
                setAllImagesColor((ViewGroup) o, colorId);
            }
        }

    }
}
