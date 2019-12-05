package com.zx.transition;

import android.os.Bundle;
import android.transition.Fade;

import com.zx.customview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @description：
 * @author：bux on 2019/11/29 19:19
 * @email: 471025316@qq.com
 */
public class ActivityB extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_edit_text);
        //setupWindowAnimations();


    }



    private void setupWindowAnimations() {
        getWindow().setReenterTransition(new Fade());
    }
}
