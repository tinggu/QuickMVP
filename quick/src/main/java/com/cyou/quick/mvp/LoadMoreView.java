package com.cyou.quick.mvp;

import com.cyou.quick.mvp.lce.MvpLceView;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : wangjia_bi
 * Date       : 2015/7/7 9:54
 */
public interface LoadMoreView<M> extends MvpLceView<M> {

    /**
     * 下拉加载数据
     */
    void addMoreData(M data);

    void showMoreLoading();

    void showLoadMoreError(Throwable e);
    
    void enableLoadmore();

    void disableLoadmore();
    
    void refresh();
    
}
