package com.zx;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.customview.R;
import com.zx.customview.RandTextView;
import com.zx.customview.TagLayout;
import com.zx.motionlayout.MltActivity1;
import com.zx.motionlayout.MltActivity2;
import com.zx.transition.ActivityA;
import com.zx.transition.ElementShareActivity;
import com.zx.transition.SamaPageActivity2;
import com.zx.transition.SamePageActivity1;

import java.util.Random;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        mLayoutId = getArguments().getInt("layoutId");
        ViewGroup view = (ViewGroup) inflater.inflate(mLayoutId, null, false);
        ;
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

        switch (mLayoutId) {
            case R.layout.tag_layout:
                final TagLayout tagLayout = view.findViewById(R.id.tagLayout);
                addTagChild(tagLayout);

                tagLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addTagChild(tagLayout);
                    }
                });
                break;
            case R.layout.nest_scrolling:
                nestScrolling(view);
                break;

            case R.layout.transition_layout:
                transitionGo(view);
                break;
            case R.layout.motion_layout:
                motionLayout(view);
                break;

            default:
        }

    }

    private void motionLayout(View view) {
        view.findViewById(R.id.btn_motion_one)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        starta(MltActivity1.class);
                    }
                });

        view.findViewById(R.id.btn_motion_two)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        starta(MltActivity2.class);
                    }
                });


    }

    private void transitionGo(View view) {
        view.findViewById(R.id.btn_content)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        starta(ActivityA.class);
                    }
                });

        view.findViewById(R.id.btn_share)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        starta(ElementShareActivity.class);
                    }
                });

        view.findViewById(R.id.btn_same_page1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        starta(SamePageActivity1.class);
                    }
                });
        view.findViewById(R.id.btn_same_page2)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        starta(SamaPageActivity2.class);
                    }
                });

        view.findViewById(R.id.btn_circular)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        starta(SamaPageActivity2.class);

                    }
                });

    }

    private <T extends AppCompatActivity> void starta(Class<T> cls) {
        getActivity().getWindow().setEnterTransition(new Fade());
        getActivity().startActivity(new Intent(getActivity(), cls), ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    private void nestScrolling(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rechcylerView);
        LinearLayoutManager manager;
        recyclerView.setLayoutManager(manager = new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(new TextAdapter());

    }

    class TextHolder extends RecyclerView.ViewHolder {

        public TextHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private void addTagChild(TagLayout tagLayout) {
        int childCount = new Random().nextInt(50);
        tagLayout.removeAllViews();
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = Utils.dp2px(12);
        params.bottomMargin = Utils.dp2px(12);
        params.leftMargin = Utils.dp2px(12);
        params.topMargin = Utils.dp2px(12);
        for (int i = 0; i < childCount; i++) {
            tagLayout.addView(new RandTextView(getActivity()), params);
        }
    }
}
