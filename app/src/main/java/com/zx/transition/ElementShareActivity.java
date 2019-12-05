package com.zx.transition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.view.View;

import com.zx.customview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @description：
 * @author：bux on 2019/11/30 14:40
 * @email: 471025316@qq.com
 */
public class ElementShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.element_shape_small_layout);
        setupWindowAnimations();

        final View sharedView = findViewById(R.id.iv_small);
        sharedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ElementShareActivity.this, ElementShareActivityB.class);


                String transitionName = "imageScale";

                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(ElementShareActivity.this, sharedView, transitionName);
                startActivity(i, transitionActivityOptions.toBundle());

                // version may be lower than Android 5.0
                // ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(ElementShareActivity.this, sharedView, transitionName);
            }
        });


    }

    private void setupWindowAnimations() {
       /* // inflate from xml
        ChangeImageTransform changeImageTransform = TransitionInflater.from(this).inflateTransition(R.transition.changebounds);*/
        // or create directly
        ChangeImageTransform changeImageTransform = new ChangeImageTransform();
        getWindow().setSharedElementEnterTransition(changeImageTransform);
        getWindow().setExitTransition(new Fade());
    }
}
