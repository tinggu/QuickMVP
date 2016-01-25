package com.cyou.quick.mvp.load;

import com.cyou.quick.mvp.MvpView;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : wangjia_bi
 * Date       : 2015/7/27 16:44
 */
public interface MvpLoadView<M> extends MvpView {

    /**
     * Display a loading view while loading data in background.
     * <b>The loading view must have the id = R.id.loadingView</b>
     */
    void showLoading();

    /**
     * Show the content view.
     * <p/>
     * <b>The content view must have the id = R.id.contentView</b>
     */
    void showContent();

    /**
     * Show the error view.
     * <b>The error view must be a TextView with the id = R.id.errorView</b>
     *
     * @param e             The Throwable that has caused this error
     */
    void showError(Throwable e);

    /**
     * The data that should be displayed with {@link #showContent()}
     */
    void setData(M data);

    /**
     * Load the data. Typically invokes the presenter method to load the desired data.
     * <p>
     * <b>Should not be called from presenter</b> to prevent infinity loops. The method is declared
     * in
     * the views interface to add support for view state easily.
     * </p>
     */
    void loadData();
}
