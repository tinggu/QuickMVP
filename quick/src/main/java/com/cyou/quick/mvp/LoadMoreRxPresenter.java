package com.cyou.quick.mvp;

import com.cyou.quick.mvp.rx.lce.MvpLceRxPresenter;

import rx.Observable;
import rx.Subscriber;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : wangjia_bi
 * Date       : 2015/7/8 15:51
 */
public abstract class LoadMoreRxPresenter<V extends LoadMoreView<M>, M>
        extends MvpLceRxPresenter<V, M> {

    protected int currPage = 1;

    protected void reSet() {
        currPage = 1;
    }

    /**
     * Subscribes the presenter himself as subscriber on the observable
     *
     * @param observable The observable to subscribe
     */
    public void subscribe(Observable<M> observable) {

        if (isViewAttached()) {
            getView().showMoreLoading();
        }

        unsubscribe();

        subscriber = new Subscriber<M>() {
            @Override
            public void onCompleted() {
                LoadMoreRxPresenter.this.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                LoadMoreRxPresenter.this.onLoadMoreError(e);
            }

            @Override
            public void onNext(M m) {
                LoadMoreRxPresenter.this.onLoadMoreNext(m);
            }
        };

        observable = applyScheduler(observable);
        observable.subscribe(subscriber);
    }

    protected void onLoadMoreError(Throwable e) {
        if (isViewAttached()) {
            getView().showLoadMoreError(e);
        }
        unsubscribe();
    }

    protected void onLoadMoreNext(M data) {
        if (isViewAttached()) {
            getView().addMoreData(data);
        }
    }
}
