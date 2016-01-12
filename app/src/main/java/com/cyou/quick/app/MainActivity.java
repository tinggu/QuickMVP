package com.cyou.quick.app;

import android.os.Bundle;

import com.cyou.quick.QuickActivity;

public class MainActivity extends QuickActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void hello(String... names){
        
    }
}
