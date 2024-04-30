package com.wyq.firehelper.ui.widget.edittext.limitcountedittext;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

/**
 * @author yuanqingwu
 * @date 2019/04/22
 */
public class CountEditTextLayout extends RelativeLayout {

    private int countLimit = 16;
    private int textCount;

    private EditText editText;
    private TextView countView;

    private String mText = "";

    public interface OnTextChangeListener{
        void onChange(CharSequence s);
    }

    public OnTextChangeListener onTextChangeListener;

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener){
        this.onTextChangeListener = onTextChangeListener;
    }

    public CountEditTextLayout(Context context) {
        this(context, null);
    }

    public CountEditTextLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountEditTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        countView = new TextView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        addView(countView, params);
        Spanned spanned = Html.fromHtml("<front color='black' ><strong>" + 0 + "</strong></front><front color='gray'>/" + countLimit + "</front>");
        countView.setText(spanned);
    }

    /**
     * 设置字符最大限制
     *
     * @param countLimit
     */
    public void setCountLimit(int countLimit) {
        this.countLimit = countLimit;
    }

    public void setText(String s){
        mText = s;
        if(editText != null){
            editText.setText(mText);
        }
    }

    public EditText getEditText(){
        return editText;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(1);
                if (child instanceof EditText) {
                    editText = (EditText) child;

                    editText.setText(mText);

                    editText.setFilters(new InputFilter[]{new InputFilter() {
                        @Override
                        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                            //start为0; end代表本次输入字数; dest 代表之前的文字内容 dstart与dend代表之前的文字长度 keep代表还可以输入的文字个数
                            int keep = countLimit - (dest.length() - (dend - dstart));
                            Logger.d("keep:" + keep + " start：" + start + " end:" + end + "[" + dest + "] dstart:" + dstart + " dend:" + dend);
                            if ( (keep >= 0 && (end - keep > 0))) {
                                Toast.makeText(getContext(),"不能再输入了，最多输入" + countLimit + "个汉字",Toast.LENGTH_SHORT).show();
                                Logger.d("toast toast");
                            }

                            if (keep <= 0) {
                                return "";
                            } else if (keep >= end - start) {
                                return null; // keep original
                            } else {
                                keep += start;
                                if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                                    --keep;
                                    if (keep == start) {
                                        return "";
                                    }
                                }
                                return source.subSequence(start, keep);
                            }
                        }
                    }});
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            Logger.d(s + " start:" + start + " count:" + count + " after:" + after);

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            Logger.d(s + " start:" + start + " before:" + before + " count:" + count);
                            textCount = s.length();

                            if(onTextChangeListener != null) {
                                onTextChangeListener.onChange(s);
                            }

                            Spanned spanned = Html.fromHtml("<front color='black' ><strong>" + textCount + "</strong></front><front color='gray'>/" + countLimit + "</front>");
                            //countView.setText(textCount + "/" + countLimit);
                            countView.setText(spanned);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
            }
        }
    }
}
