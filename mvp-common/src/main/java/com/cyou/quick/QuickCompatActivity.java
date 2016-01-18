package com.cyou.quick;

import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : wangjia_bi
 * Date       : 2016/1/14 18:21
 */
public class QuickCompatActivity extends AppCompatActivity {
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
    }
}
