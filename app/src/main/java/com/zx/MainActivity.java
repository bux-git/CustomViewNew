package com.zx;

import android.os.Bundle;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayList<PagerItem> items = new ArrayList<>();
        items.add(new PagerItem("MaterialEditText", R.layout.material_edit_text));
        items.add(new PagerItem("SquareImageView", R.layout.square_image_view));
        items.add(new PagerItem("CircleView", R.layout.circle_view_layout));
        items.add(new PagerItem("TagLayout", R.layout.tag_layout));
        items.add(new PagerItem("TouchView", R.layout.touch_view));
        items.add(new PagerItem("ScaleImageView", R.layout.scale_image_view));
        items.add(new PagerItem("ScaleImageView", R.layout.scale_image_view));
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

        viewPager.setCurrentItem(5);
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