
package com.wyq.firehelper.base.utils.common;


import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wyq.firehelper.base.R;


public class DialogUtil {

   public static Dialog creatRequestDialog(final Context context, String tip){

       final Dialog dialog = new Dialog(context, R.style.base_requst_dialog);
       dialog.setContentView(R.layout.base_common_dialog_request);
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();	
		int width = ScreenUtils.getWidthPX(context);
		lp.width = (int)(0.6 * width);	
		
		TextView titleTxtv = dialog.findViewById(R.id.tvLoad);
		if (tip == null || tip.length() == 0)
		{
			titleTxtv.setText(R.string.common_default_load);
			
		}else{
			titleTxtv.setText(tip);	
		}
		
		return dialog;
	}
}
