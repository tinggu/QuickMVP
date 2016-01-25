package com.cyou.quick.mvp.loadmore;

import android.util.Log;
import android.view.View;

import com.cyou.quick.mvp.MvpPresenter;
import com.cyou.quick.mvp.delegate.FragmentMvpDelegate;
import com.cyou.quick.mvp.delegate.FragmentMvpDelegateImpl;
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

    public static final String TAG = "LoadMore";


    /**
     * A flag that indicates if the viewstate tires to restore the view right now.
     */
    private boolean restoringViewState = false;


    @Override
    protected FragmentMvpDelegate<V, P> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new FragmentMvpDelegateImpl<>(this);
        }
        return mvpDelegate;
    }

    @Override
    public void showContent() {
        super.showContent();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
        Log.d(TAG, "showLoading");
    }


    @Override
    protected void showLightError(String msg) {

        super.showLightError(msg);
    }

    @Override
    public void showLoadMoreError(Throwable e) {
//        showLightError(e.toString());
        Log.d(TAG, "showLoadMoreError");
    }

    @Override
    public void showMoreLoading() {
        Log.d(TAG, "showMoreLoading");
    }


    public abstract M getData();
}
