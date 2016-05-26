package me.tinggu.sample.girl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.tinggu.sample.R;
import me.tinggu.sample.adapter.GirlAdapter;
import me.tinggu.sample.base.BaseLceFragment;
import me.tinggu.sample.model.PrettyGirl;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : wangjia_bi
 * Date       : 2016/1/20 17:22
 */
public class GirlFragment
        extends BaseLceFragment<List<PrettyGirl>, GirlView, GirlPresenter>
        implements GirlView {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private GirlAdapter mGirlAdapter;

    private List<PrettyGirl> mPrettyGirlList = new ArrayList<>();

    private Activity mActivity;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        setupRecyclerView();
        onImageTouch();
        showLoading(false);
        loadData(false);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_girl;
    }

    private void setupRecyclerView() {

        mGirlAdapter = new GirlAdapter(mActivity, mPrettyGirlList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mGirlAdapter);
    }

    private void onImageTouch() {
        mGirlAdapter.setOnTouchListener(new GirlAdapter.OnTouchListener() {
            @Override
            public void onImageTouch(final View v, final PrettyGirl girl) {
                Picasso.with(mActivity).load(girl.url).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(mActivity, PictureActivity.class);
                        intent.putExtra("url", girl.url);
                        ActivityOptionsCompat compat =
                                ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, v, "girl");
                        ActivityCompat.startActivity(mActivity, intent, compat.toBundle());
                    }

                    @Override
                    public void onError() {
                    }
                });
            }
        });
    }


    @Override
    protected String getErrorMessage(Throwable throwable, boolean b) {
        return null;
    }

    @Override
    public GirlPresenter createPresenter() {
        return new GirlPresenter();
    }

    @Override
    public void setData(List<PrettyGirl> list) {
        mPrettyGirlList.clear();
        mPrettyGirlList.addAll(list);
        mGirlAdapter.notifyDataSetChanged();
        showContent();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        getPresenter().fetchGirlData(pullToRefresh);
    }

    @Override
    public void addData(List<PrettyGirl> list) {
        mPrettyGirlList.addAll(list);
        mGirlAdapter.notifyDataSetChanged();
    }

}
