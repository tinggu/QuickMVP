package com.cyou.quick.mvp;

import com.cyou.quick.mvp.viewstate.ViewState;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : wangjia_bi
 * Date       : 2015/7/13 14:29
 */
public interface LoadMoreViewState<D, V extends LoadMoreView<D>> extends ViewState<V> {
    /**
     * Used as currentViewState to indicate that loading is currently displayed on screen
     */
    int STATE_SHOW_LOADING = 0;

    /**
     * Used as currentViewState to indicate that the content is currently displayed on
     * screen
     */
    int STATE_SHOW_CONTENT = 1;

    /**
     * 显示加载更多
     */
    int STATE_SHOW_LOADMORE = 2;

    /**
     * Used as currentViewState to indicate that the error is currently displayed on screen
     */
    int STATE_SHOW_ERROR = 3;

    /**
     * 显示加载更多错误
     */
    int STATE_SHOW_LOADMORE_ERROR = 4;

    /**
     * Set the view state to showing content
     *
     * @param loadedData The content data that is currently dipslayed
     */
    void setStateShowContent(D loadedData);

    /**
     * Set the view state to showing the errorview
     *
     * @param e The reason why the errorview is displayed on screen
     * @param pullToRefresh Was is a pull to refresh operation that has failed?
     */
    void setStateShowError(Throwable e, boolean pullToRefresh);

    /**
     * Set the state to show loading
     *
     * @param pullToRefresh Was it a pull to refresh operation?
     */
    void setStateShowLoading(boolean pullToRefresh);

    /**
     * 设置当前View的状态为加载更多错误
     *
     * @param e
     */
    void setStateShowLoadmoreError(Throwable e);

    void setStateShowLoadmore();
}
 