package com.wyq.firehelper.architecture.mvvm;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class NameUtils {
    public static String capitalize(String word) {
        if (word.length() > 1) {
            return String.valueOf(word.charAt(0)).toUpperCase() + word.substring(1);
        }
        return word;
    }

    @BindingAdapter({"headImage"})
    public static void showHead(ImageView imageView,String url){
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
