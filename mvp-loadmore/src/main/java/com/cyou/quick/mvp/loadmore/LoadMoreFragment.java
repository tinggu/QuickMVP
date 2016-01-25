package com.cyou.quick.mvp.loadmore;

import android.view.View;

import com.cyou.quick.mvp.MvpPresenter;
import com.cyou.quick.mvp.lce.MvpLceFragment;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : wangjia_bi
 * Date       : 2016/1/25 17:15
 */
public abstract class LoadMoreFragment<CV extends View, M, V extends LoadMoreView<M>, P extends MvpPresenter<V>>
        extends MvpLceFragment<CV, M, V, P> implements LoadMoreView<M> {
    
}
