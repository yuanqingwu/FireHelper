package com.wyq.firehelper.ui.android.emoji;

import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.widget.EmojiEditText;
import android.support.text.emoji.widget.EmojiTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;

import butterknife.BindView;

/**
 * https://developer.android.google.cn/guide/topics/ui/look-and-feel/emoji-compat#java
 */
public class EmojiActivity extends BaseActivity {

    @BindView(R.id.ui_activity_emoji_tv)
    public EmojiTextView emojiTextView;

    @BindView(R.id.ui_activity_tv)
    public TextView textView;

    @BindView(R.id.ui_activity_emoji_et)
    public EmojiEditText editText;

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_emoji;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {

        /**
         * You can always use the process() method to preprocess the CharSequence in your app and add it to any widget that can render Spanned instances; for example, TextView.
         */
        CharSequence sequence = EmojiCompat.get().process("neutral face \uD83D\uDE10");
        textView.setText(sequence);

        emojiTextView.setText("neutral face \uD83D\uDE10");

        //Fixme what's the effect
        Logger.d("MaxEmojiCount:"+editText.getMaxEmojiCount());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Logger.d(s+" start:"+start+" before:"+before+" count:"+count);
                textView.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
