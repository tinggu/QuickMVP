package me.tinggu.sample.girl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import me.tinggu.sample.R;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : wangjia_bi
 * Date       : 2016/1/19 11:08
 */
public class GirlActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG_GIRL = "girlFiagment";
    private GirlFragment girlFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        
//        toolbar.setTitle("妹子图");
//        toolbar.setHorizontalFadingEdgeEnabled(true);
        setSupportActionBar(toolbar);

        girlFragment = (GirlFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_GIRL);
        if (girlFragment == null) {
            girlFragment = new GirlFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentPane, girlFragment, FRAGMENT_TAG_GIRL)
                    .commit();
        }
        
    }

}
