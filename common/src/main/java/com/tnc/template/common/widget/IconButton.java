package com.tnc.template.common.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import com.tnc.template.common.R;

/**
 * Created by CUSDungVT on 1/21/2017.
 */

public class IconButton extends AppCompatImageButton{
  private static final int[][] STATES = new int[][]{
    new int[]{android.R.attr.state_enabled},
      new int[]{-android.R.attr.state_enabled}
  };
  private ColorStateList colorStateList;
  private final boolean isTinted;
  public IconButton(Context context) {
    this(context, null);
  }

  public IconButton(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public IconButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setBackgroundResource(getThemedResId(context, R.attr.selectableItemBackgroundBorderless));

    TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IconButton, 0, 0);
    int colorDisabled = ContextCompat.getColor(context, getThemedResId(context, android.R.attr.textColorSecondary));
    int colorDefault = ContextCompat.getColor(context, getThemedResId(context, android.R.attr.textColorPrimary));
    int colorEnabled = ta.getColor(R.styleable.IconButton_tint, colorDefault);
    colorStateList = new ColorStateList(STATES, new int[]{colorEnabled, colorDisabled});
    isTinted = ta.hasValue(R.styleable.IconButton_tint);
    if(getSuggestedMinimumWidth() == 0){
      setMinimumWidth(context.getResources().getDimensionPixelSize(R.dimen.icon_button_width));
    }
    setScaleType(ScaleType.CENTER);
    setImageDrawable(getDrawable());
    ta.recycle();
  }

  @Override public void setImageResource(@DrawableRes int resId) {
    setImageDrawable(ContextCompat.getDrawable(getContext(), resId));
  }

  @Override public void setImageDrawable(Drawable drawable) {
    super.setImageDrawable(tint(drawable));
  }

  private Drawable tint(Drawable drawable){
    if(drawable == null){
      return null;
    }
    Drawable tintDrawable = DrawableCompat.wrap(isTinted ? drawable.mutate() : drawable);
    DrawableCompat.setTintList(tintDrawable, colorStateList);
    return tintDrawable;
  }

  public static int getThemedResId(Context context, @AttrRes int attr) {
    TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
    final int resId = a.getResourceId(0, 0);
    a.recycle();
    return resId;
  }
}
