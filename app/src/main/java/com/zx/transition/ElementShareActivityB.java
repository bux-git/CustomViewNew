package com.zx.transition;

import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;

import com.zx.customview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @description：
 * @author：bux on 2019/11/30 14:40
 * @email: 471025316@qq.com
 */
public class ElementShareActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.element_shape_layout);
        getWindow().setEnterTransition(new Fade());

        ImageView ivImg = findViewById(R.id.iv_big);
        ivImg.setImageResource(R.drawable.test);
        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();

            }
        });


    }
}
