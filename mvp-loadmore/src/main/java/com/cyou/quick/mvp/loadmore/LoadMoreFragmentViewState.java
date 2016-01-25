package com.cyou.quick.mvp.loadmore;

import android.support.v4.app.Fragment;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : wangjia_bi
 * Date       : 2015/7/8 15:22
 */
public class LoadMoreFragmentViewState<D, V extends LoadMoreView<D>>
        extends AbsLoadMoreViewState<D, V> {
    /**
     * Creates a new instance. Since most of developers forget to call {@link
     * Fragment#setRetainInstance(boolean)} you have to pass the fragment and
     * setRetaineInstanceState(true) will be called for that fragment
     *
     * @param f The fragment for this view state. Can be null if you really don't want to
     *          setRetainInstanceState(true) automatically, but you should really have a good reason for doing
     *          so
     */
    public LoadMoreFragmentViewState(Fragment f) {
        if (f != null) {
            f.setRetainInstance(true);
        }
    }
}
