package com.zx.transition;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import com.zx.customview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @description：
 * @author：bux on 2019/12/1 15:58
 * @email: 471025316@qq.com
 */
public class SamaPageActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations_scene1);
        final ViewGroup root = findViewById(R.id.root);
        final View red = findViewById(R.id.tv_red);
        final View tv_blue = findViewById(R.id.tv_blue);
        View tv_orange = findViewById(R.id.tv_orange);
        View tv_green = findViewById(R.id.tv_green);

        final ConstraintLayout.LayoutParams redParams = (ConstraintLayout.LayoutParams) red.getLayoutParams();
        final ConstraintLayout.LayoutParams blueParams = (ConstraintLayout.LayoutParams) tv_blue.getLayoutParams();

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(root);

                red.setLayoutParams(blueParams);
                tv_blue.setLayoutParams(redParams);
            }
        });

        tv_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circular(tv_blue);
            }
        });
    }

    public void circular(View myView){

// Check if the runtime version is at least Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            int cx = myView.getWidth() / 2;
            int cy = myView.getHeight() / 2;

            // get the final radius for the clipping circle
            float finalRadius = (float) Math.hypot(cx, cy);

            // create the animator for this view (the start radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(myView, 0, 0, 0f, finalRadius);

            // make the view visible and start the animation
            myView.setVisibility(View.VISIBLE);
            anim.start();
        } else {
            // set the view to invisible without a circular reveal animation below Lollipop
            myView.setVisibility(View.INVISIBLE);
        }
    }
}
