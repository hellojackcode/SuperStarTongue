package com.hellojackcode.superstartongue;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
 * App 전체에서 나눔폰트를 사용하도록 함
 * 내용은 http://androi.tistory.com/99 를 참고할 것. 
 */

public class BaseActivity extends Activity {
    private static Typeface mTypeface = null;
    
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(this.getAssets(), "fonts/NanumGothic.otf"); // 외부폰트 사용
            //mTypeface = Typeface.MONOSPACE; // 내장 폰트 사용
        }
        setGlobalFont(getWindow().getDecorView());
    }
 
    private void setGlobalFont(View view) {
        if (view != null) {
            if(view instanceof ViewGroup){
                ViewGroup vg = (ViewGroup)view;
                int vgCnt = vg.getChildCount();
                for(int i=0; i < vgCnt; i++){
                    View v = vg.getChildAt(i);
                    if(v instanceof TextView){
                        ((TextView) v).setTypeface(mTypeface);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }	
}
