package com.wyq.firehelper.ui.layout.placeholderview;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.wyq.firehelper.R;

@Layout(R.layout.developkit_placeholderview_expandable_item_layout)
public class ItemView {

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
    public Context context;

    public ItemView(Context context, String itemText, String descText) {
        this.context = context;
        this.itemText = itemText;
        this.descText = descText;
    }

    @Resolve
    public void onResolved() {
//        Logger.i(itemText + " is Resolve");
        if (itemTv == null || descTv == null) {
            return;
        }
        itemTv.setText(itemText);

        if (descText == null || descText.isEmpty()) {
            descTv.setVisibility(android.view.View.GONE);
        } else {
            descTv.setVisibility(android.view.View.VISIBLE);
            descTv.setText(descText);
        }
    }

    @Click(R.id.developkit_placeholderview_expandable_item_tv)
    public void onKitClick() {
//        Logger.i(itemText + " is click");
//        Logger.i("mParentPosition:" + mParentPosition + " mChildPosition:" + mChildPosition);

        String name = "com.wyq.firehelper.developKit." + itemText + "." + itemText + "Activity";
//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName(context,name));
//        context.startActivity(intent);

        try {
            Class clazz = Class.forName(name, true, context.getClassLoader());
//            Logger.i(clazz.toString());
            context.startActivity(new Intent(context, clazz));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
