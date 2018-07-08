package com.wyq.firehelper.ui.placeholderview;

import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.SingleTop;
import com.wyq.firehelper.R;

@Parent
@SingleTop
@Layout(R.layout.developkit_placeholderview_expandable_head_layout)
public class HeadView {

    @View(R.id.developkit_placeholderview_expandable_head_layout_tv)
    public TextView headTv;

    @ParentPosition
    int mParentPosition;

    public String headText;

    public HeadView(String headText) {
        this.headText = headText;
    }

    @Resolve
    public void onResolved(){
        headTv.setText(headText);
    }
}
