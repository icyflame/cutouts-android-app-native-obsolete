package io.github.icyflame.cutouts;

/**
 * Created by siddharth on 8/8/16.
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class DialogFragmentCustom extends DialogFragment {

    protected double WIDTH_RATIO = 0.75;
    protected double HEIGHT_RATIO = 1;

    @Override
    public void onResume() {
        super.onResume();

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        ViewGroup.LayoutParams p = getDialog().getWindow().getAttributes();

        p.width = ((int) (getResources().getDisplayMetrics().widthPixels * WIDTH_RATIO));
        p.height = ((int) (ViewGroup.LayoutParams.WRAP_CONTENT * HEIGHT_RATIO));

        getDialog().getWindow().setAttributes(((WindowManager.LayoutParams) p));
    }
}