package com.zx.customview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * description：
 * author：bux on 2019/11/5 11:11
 * email: 471025316@qq.com
 */
public class PagerFragment extends Fragment {
    private static final String TAG = "PagerFragment";

    public static Fragment instance(@LayoutRes int layoutId) {
        Fragment fragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("layoutId", layoutId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view= (ViewGroup) inflater.inflate(getArguments().getInt("layoutId"), null, false);;
        View child = view.getChildAt(0);
        ViewPager.LayoutParams params= (ViewPager.LayoutParams) view.getLayoutParams();

       // Log.d(TAG, "onCreateView: "+params.height+" view:"+view.toString());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* final MaterialEditText editText = findViewById(R.id.editText);
        editText.setLabelTextSize(50);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.setLabelOpen(false);
            }
        }, 6000);*/
    }
}
