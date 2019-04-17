package com.wyq.firehelper.ui.widget.multiedittext;

public interface MultiEditText {

     enum TextEffect{
        /**
         * 经典
         */
        CLASSIC,
        /**
         * 增强
         */
        BOLD,
        /**
         * 霓虹灯
         */
        NEON,
        /**
         * 描边
         */
        STROKE
    }

    void setTextEffect(TextEffect effect);

}
