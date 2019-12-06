package com.zx;

import android.view.View;
import android.view.ViewGroup;

import com.zx.customview.RandTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @description：
 * @author：bux on 2019/12/6 17:32
 * @email: 471025316@qq.com
 */
public class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextHolder> {


    class TextHolder extends RecyclerView.ViewHolder {


        public TextHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public TextAdapter.TextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new TextAdapter.TextHolder(new RandTextView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull TextAdapter.TextHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return Utils.TEXTS.length;
    }
}
