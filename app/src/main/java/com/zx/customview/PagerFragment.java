package com.zx.customview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.customview.view.RandTextView;
import com.zx.customview.view.TagLayout;

import java.util.Random;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * description：
 * author：bux on 2019/11/5 11:11
 * email: 471025316@qq.com
 */
public class PagerFragment extends Fragment {
    private static final String TAG = "PagerFragment";

    private int mLayoutId;

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

        mLayoutId=getArguments().getInt("layoutId");
        ViewGroup view= (ViewGroup) inflater.inflate(mLayoutId, null, false);;
      /*  View child = view.getChildAt(0);
        ViewPager.LayoutParams params= (ViewPager.LayoutParams) view.getLayoutParams();
*/
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

       if(mLayoutId==R.layout.tag_layout){
           final TagLayout tagLayout = view.findViewById(R.id.tagLayout);
           addTagChild(tagLayout);

           view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addTagChild(tagLayout);
                }
            });
                   ;
       }
    }

    private void addTagChild(TagLayout tagLayout) {
        int childCount = new Random().nextInt(30);
        tagLayout.removeAllViews();
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = Utils.dp2px(12);
        params.bottomMargin= Utils.dp2px(12);
        params.leftMargin= Utils.dp2px(12);
        params.topMargin= Utils.dp2px(12);
        for (int i = 0; i < childCount; i++) {
            tagLayout.addView(new RandTextView(getActivity()),params);
        }
    }
}
