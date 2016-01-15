package com.cyou.quick;


import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;

/**
 * 
 */
public  class QuickActivity extends FragmentActivity {
    
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }
    
}
