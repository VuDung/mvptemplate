package com.tnc.template.common.widget;

import android.os.Handler;
import android.view.View;


/**
 * Created by TALE on 3/20/2014.
 */
public class RadioViewGroupController implements View.OnClickListener {

    private View[] radioButtons;
    private int selectedIndex;
    private OnCheckedChangeListener onCheckedChangeListener;

    public void setViews(View[] buttons) {
        if (buttons == null || buttons.length == 0) {
            return;
        }

        radioButtons = new View[buttons.length];
        for (int i = 0; i < buttons.length; i++) {
            final View view = buttons[i];
            view.setTag(i);
            view.setOnClickListener(this);
            radioButtons[i] = view;
        }
        selectedIndex = 0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                radioButtons[selectedIndex].setSelected(true);
            }
        }, 1000);
    }

    public void setSelection(int position) {
        if (radioButtons == null || radioButtons.length <= position || position < 0) {
            return;
        }
        setSelection(radioButtons[position], false);
    }

    public void setSelectionAtFirst(int position){
        if (radioButtons == null || radioButtons.length <= position || position < 0) {
            return;
        }
        setSelection(radioButtons[position], true);
    }

    private void setSelection(View v, boolean isFirst) {
        if (v == radioButtons[selectedIndex] && !isFirst) {
            return;
        }
        v.setSelected(true);
        if (selectedIndex >= 0) {
            radioButtons[selectedIndex].setSelected(false);
        }
        selectedIndex = (Integer) v.getTag();
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(v.getId(), selectedIndex);
        }
    }

    public int getCheckedViewId() {
        return radioButtons[selectedIndex].getId();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public void onClick(View v) {
        setSelection(v, false);
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(int checkedId, int position);
    }


}
