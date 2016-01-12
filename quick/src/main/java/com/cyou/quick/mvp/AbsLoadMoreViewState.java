package com.cyou.quick.mvp;

import android.util.Log;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : wangjia_bi
 * Date       : 2015/7/13 14:54
 */
public class AbsLoadMoreViewState<D, V extends LoadMoreView<D>> implements LoadMoreViewState<D, V> {

    public static final String TAG = "UPing";
    
    protected int currentViewState;
    protected boolean pullToRefresh;
    protected Throwable exception;
    protected D loadedData;
//    protected boolean loadMore;
    
    public AbsLoadMoreViewState(){
        Log.d(TAG, "create AbsLoadMoreViewState");
    }

    @Override
    public void setStateShowContent(D loadedData) {
        Log.d(TAG, "setStateShowContent");
        currentViewState = STATE_SHOW_CONTENT;
        this.loadedData = loadedData;
        exception = null;
    }

    @Override
    public void setStateShowError(Throwable e, boolean pullToRefresh) {
        Log.d(TAG, "setStateShowError");
        currentViewState = STATE_SHOW_ERROR;
        exception = e;
        this.pullToRefresh = pullToRefresh;
        if (!pullToRefresh) {
            loadedData = null;
        }
        // else, don't clear loaded data, because of pull to refresh where previous data may
        // be displayed while showing error
    }

    @Override
    public void setStateShowLoading(boolean pullToRefresh) {
        Log.d(TAG, "setStateShowLoading");
        currentViewState = STATE_SHOW_LOADING;
        this.pullToRefresh = pullToRefresh;
        exception = null;

        if (!pullToRefresh) {
            loadedData = null;
        }
        // else, don't clear loaded data, because of pull to refresh where previous data
        // may be displayed while showing error
    }

    @Override
    public void setStateShowLoadmoreError(Throwable e) {
        Log.d(TAG, " setStateShowLoadmoreError");
        currentViewState = STATE_SHOW_LOADMORE_ERROR;
        exception = e;
        loadedData = null;
    }

    @Override
    public void setStateShowLoadmore() {
        Log.d(TAG, " setStateShowLoadmore");
        currentViewState = STATE_SHOW_LOADMORE;
        exception = null;
        loadedData = null;
    }


    @Override
    public void apply(V view, boolean retained) {

        Log.d(TAG, "apply state == " + currentViewState + "; retained == " + retained); 
        boolean ptr;
        switch (currentViewState) {
            case STATE_SHOW_CONTENT:
                view.setData(loadedData);
                view.showContent();
                break;
            case STATE_SHOW_LOADING:
                 ptr = pullToRefresh;
                if (pullToRefresh) {
                    view.setData(loadedData);
                    view.showContent();
                }

                if (retained) {
                    view.showLoading(ptr);
                } else {
                    view.loadData(ptr);
                }
                break;
            case STATE_SHOW_ERROR:
                 ptr = pullToRefresh; 
                if (pullToRefresh) {
                    view.setData(loadedData);
                    view.showContent();
                }
                view.showError(exception, ptr);
                break;
            case STATE_SHOW_LOADMORE:
                if (retained) {
                    view.showMoreLoading();
                } else {
                    view.addMoreData(loadedData);
                }
                break;
            case STATE_SHOW_LOADMORE_ERROR: 
                view.showLoadMoreError(exception);
                break;
        }
    }
}
