package com.zx.motionlayout;

import android.os.Bundle;

import com.zx.customview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

/**
 * @description：
 * @author：bux on 2019/12/5 21:04
 * @email: 471025316@qq.com
 */
public class MltActivity1 extends AppCompatActivity {
    MotionLayout mMotionLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motion_layout_part1_start);

       /* RecyclerView recyclerView = findViewById(R.id.rechcylerView);
        LinearLayoutManager manager;
        recyclerView.setLayoutManager(manager = new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(new TextAdapter());*/

        mMotionLayout = findViewById(R.id.rechcylerView);


    }
}
