package com.llt.superlibs.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.llt.superlibs.R;


/**
 * 
* TODO 自定义Toast
 */
public class ToastUtil {
	
	public static void show(Context context,  String content){
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.layout_custom_toast, null);
        TextView tvContent = (TextView) layout.findViewById(R.id.tv_toast_content);
        tvContent.setText(content); 
        Toast toast = new Toast(context.getApplicationContext());   
        //toast.setGravity(Gravity.BOTTOM, 0, 0);  
        toast.setDuration(Toast.LENGTH_SHORT);   
        toast.setView(layout);  
        toast.show(); 
	}
	 
	public static void show(Context context,int strid){
        LayoutInflater inflater = LayoutInflater.from(context);  
        View layout = inflater.inflate(R.layout.layout_custom_toast, null);   
        TextView tvContent = (TextView) layout.findViewById(R.id.tv_toast_content);
        tvContent.setText(strid); 
        Toast toast = new Toast(context.getApplicationContext());   
        //toast.setGravity(Gravity.BOTTOM, 0, 0);  
        toast.setDuration(Toast.LENGTH_SHORT);   
        toast.setView(layout);  
        toast.show(); 
	}
}
