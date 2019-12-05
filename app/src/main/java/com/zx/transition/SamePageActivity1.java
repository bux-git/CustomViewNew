package com.zx.transition;

import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import com.zx.customview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @description：
 * @author：bux on 2019/12/1 14:38
 * @email: 471025316@qq.com
 */
public class SamePageActivity1 extends AppCompatActivity implements View.OnClickListener {

    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;
   TransitionInflater transitionInflater;
    Transition transition1;
    Transition transition2;
    Transition transition3;
    Transition transition4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations_scene0);
        transitionInflater = TransitionInflater.from(this);
        transition1 = transitionInflater.inflateTransition(R.transition.changebounds_with_arcmotion);
        transition2 = transitionInflater.inflateTransition(R.transition.slide_and_changebounds);
        transition3 = transitionInflater.inflateTransition(R.transition.slide_and_changebounds_sequential);
        transition4 = transitionInflater.inflateTransition(R.transition.slide_and_changebounds_sequential_with_interpolators);
        bind();


    }

    private void bind() {
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);

        ViewGroup root = findViewById(R.id.root);
        scene1 = Scene.getSceneForLayout(root, R.layout.activity_animations_scene1, this);
        scene2 = Scene.getSceneForLayout(root, R.layout.activity_animations_scene2, this);
        scene3 = Scene.getSceneForLayout(root, R.layout.activity_animations_scene3, this);
        scene4 = Scene.getSceneForLayout(root, R.layout.activity_animations_scene4, this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                TransitionManager.go(scene1,transition1);
                break;
            case R.id.btn_two:
                TransitionManager.go(scene2, transition2);
                break;
            case R.id.btn_three:
                TransitionManager.go(scene3, transition3);
                break;
            case R.id.btn_four:
                TransitionManager.go(scene4,transition4);
                break;
            default:
        }
        bind();
    }


}
