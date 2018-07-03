package com.wyq.firehelper.developKit.placeholderview;

import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;

@Layout(R.layout.developkit_placeholderview_expandable_item_layout)
public class ItemView implements android.view.View.OnClickListener {

    @View(R.id.developkit_placeholderview_expandable_item_tv)
    public TextView itemTv;

    @View(R.id.developkit_placeholderview_expandable_item_desc_tv)
    public TextView descTv;

    @ParentPosition
    int mParentPosition;

    @ChildPosition
    int mChildPosition;

    public String itemText;
    public String descText;

    public ItemView(String itemText,String descText) {
        this.itemText = itemText;
        this.descText = descText;
    }

    @Resolve
    public void onResolved() {
        itemTv.setText(itemText);
        if (descText == null || descText.isEmpty()) {
            descTv.setVisibility(android.view.View.GONE);
        } else {
            descTv.setVisibility(android.view.View.VISIBLE);
            descTv.setText(descText);
        }
        itemTv.setOnClickListener(this);
    }

    @Override
    public void onClick(android.view.View v) {
        Logger.i("mParentPosition:" + mParentPosition + " mChildPosition:" + mChildPosition);

    }
}
