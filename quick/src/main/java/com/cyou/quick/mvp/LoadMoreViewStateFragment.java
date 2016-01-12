/*
 * Copyright 2015 Hannes Dorfmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cyou.quick.mvp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.cyou.quick.mvp.delegate.FragmentMvpDelegate;
import com.cyou.quick.mvp.delegate.FragmentMvpViewStateDelegateImpl;
import com.cyou.quick.mvp.delegate.MvpViewStateDelegateCallback;
import com.cyou.quick.mvp.lce.MvpLceFragment;
import com.cyou.quick.mvp.viewstate.ViewState;

/**
 * This is a enhancement of {@link MvpFragment} that introduces the
 * support of {@link ViewState}.
 * <p>
 * You can change the behaviour of what to do if the viewstate is empty (usually if the fragment
 * creates the viewState for the very first time and therefore has no state / data to restore) by
 * overriding {@link #onNewViewStateInstance()}
 * </p>
 *
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
public abstract class LoadMoreViewStateFragment<CV extends View, M, V extends LoadMoreView<M>, P extends MvpPresenter<V>>
        extends MvpLceFragment<CV, M, V, P> implements LoadMoreView<M>
        , MvpViewStateDelegateCallback<V, P> {

    public static final String TAG = "LoadMore";

    /**
     * The viewstate will be instantiated by calling {@link #createViewState()} in {@link
     * #onViewCreated(View, Bundle)}. Don't instantiate it by hand.
     */
    protected LoadMoreViewState<M, V> viewState;

    /**
     * A flag that indicates if the viewstate tires to restore the view right now.
     */
    private boolean restoringViewState = false;

    /**
     * Create the view state object of this class
     */
    public abstract LoadMoreViewState<M, V> createViewState();

    @Override
    protected FragmentMvpDelegate<V, P> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new FragmentMvpViewStateDelegateImpl<V, P>(this);
        }

        return mvpDelegate;
    }

    @Override
    public ViewState getViewState() {
        return viewState;
    }

    @Override
    public void setViewState(ViewState<V> viewState) {
        this.viewState = (LoadMoreViewState<M, V>) viewState;
    }

    @Override
    public void showContent() {
        super.showContent();
        Log.d(TAG, "showContent");
        viewState.setStateShowContent(getData());
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        Log.d(TAG, "showError");
        viewState.setStateShowError(e, pullToRefresh);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
        Log.d(TAG, "showLoading");
        viewState.setStateShowLoading(pullToRefresh);
    }

    @Override
    public void setRestoringViewState(boolean restoringViewState) {
        this.restoringViewState = restoringViewState;
    }

    @Override
    public boolean isRestoringViewState() {
        return restoringViewState;
    }

    @Override
    public void onViewStateInstanceRestored(boolean instanceStateRetained) {
        // Not needed in general. override it in subclass if you need this callback
    }

    @Override
    public void onNewViewStateInstance() {
        loadData(false);
    }

    @Override
    protected void showLightError(String msg) {
        if (isRestoringViewState()) {
            return; // Do not display toast again while restoring viewstate
        }
        super.showLightError(msg);
    }

    @Override
    public void showLoadMoreError(Throwable e) {
//        showLightError(e.toString());
        Log.d(TAG, "showLoadMoreError");
        viewState.setStateShowLoadmoreError(e);
    }

    @Override
    public void showMoreLoading() {
        Log.d(TAG, "showMoreLoading");
        viewState.setStateShowLoadmore();
    }


    /**
     * Get the data that has been set before in {@link #setData(Object)}
     * <p>
     * <b>It's necessary to return the same data as set before to ensure that {@link ViewState} works
     * correctly</b>
     * </p>
     *
     * @return The data
     */
    public abstract M getData();
}