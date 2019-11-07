package com.zx.customview;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_layout);
        /*final ArrayList<PagerItem> items = new ArrayList<>();
        items.add(new PagerItem("MaterialEditText", R.layout.material_edit_text));
        items.add(new PagerItem("SquareImageView", R.layout.square_image_view));
        items.add(new PagerItem("CircleView", R.layout.circle_view_layout));
        items.add(new PagerItem("TagLayout", R.layout.tag_layout));


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

        viewPager.setCurrentItem(3);*/
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
