package com.zx;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.zx.customview.R;

import java.util.ArrayList;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //threadTest();


        Log.d(TAG, "onCreate: " + (MainActivity.class == getClass()));
        setContentView(R.layout.activity_main);
        final ArrayList<PagerItem> items = new ArrayList<>();
        items.add(new PagerItem("MaterialEditText", R.layout.material_edit_text));
        items.add(new PagerItem("SquareImageView", R.layout.square_image_view));
        items.add(new PagerItem("CircleView", R.layout.circle_view_layout));
        items.add(new PagerItem("TagLayout", R.layout.tag_layout));
        items.add(new PagerItem("TouchView", R.layout.touch_view));
        items.add(new PagerItem("ScaleImageView", R.layout.scale_image_view));
        items.add(new PagerItem("多触—接力", R.layout.multi_touch_view));
        items.add(new PagerItem("多触—配合", R.layout.multi_touch_view2));
        items.add(new PagerItem("多触—独立", R.layout.multi_touch_view3));
        items.add(new PagerItem("嵌套滑动", R.layout.nest_scrolling));
        items.add(new PagerItem("过渡动画", R.layout.transition_layout));
        items.add(new PagerItem("MotionLayout", R.layout.motion_layout));
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return PagerFragment.instance(items.get(position).layoutResId);
            }

            @Override
            public int getCount() {
                return items.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return items.get(position).name;
            }
        });

        viewPager.setCurrentItem(11);


        Single.just(1)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Throwable {

                        return String.valueOf(integer);
                    }
                })
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


    }

    private void threadTest() {

        Thread thread1 = new Thread(new RunnableTest(MainActivity.class));
        Thread thread2 = new Thread(new RunnableTest(getClass()));

        thread1.start();
        thread2.start();
    }

    class RunnableTest implements Runnable {

        private int count = 100;

        private Object lock;


        public RunnableTest(Object lock) {
            this.lock = lock;

        }

        @Override
        public void run() {

            synchronized (lock) {
                for (int i = 0; i < count; i++) {
                    Log.d(TAG, "run: " + Thread.currentThread() + " i:" + i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    static class PagerItem {
        public String name;
        public @LayoutRes
        int layoutResId;

        public PagerItem(String name, int layoutResId) {
            this.name = name;
            this.layoutResId = layoutResId;
        }
    }
}
