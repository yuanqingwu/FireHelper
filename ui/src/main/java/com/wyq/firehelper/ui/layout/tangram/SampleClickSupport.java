package com.wyq.firehelper.ui.layout.tangram;

import android.view.View;
import android.widget.Toast;

import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.support.SimpleClickSupport;

public class SampleClickSupport extends SimpleClickSupport {

    public SampleClickSupport(){
        setOptimizedMode(true);
    }

    @Override
    public void defaultClick(View targetView, BaseCell cell, int eventType) {
        super.defaultClick(targetView, cell, eventType);
        Toast.makeText(targetView.getContext(),"click:"+cell.stringType,Toast.LENGTH_LONG).show();
    }
}
