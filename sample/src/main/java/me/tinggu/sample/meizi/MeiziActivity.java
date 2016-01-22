package me.tinggu.sample.meizi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import me.tinggu.sample.R;
import me.tinggu.sample.adapter.GirlAdapter;
import me.tinggu.sample.api.GirlApi;
import me.tinggu.sample.common.rest.RestUtils;
import me.tinggu.sample.common.utils.NetUtils;
import me.tinggu.sample.girl.PictureActivity;
import me.tinggu.sample.model.PrettyGirl;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MeiziActivity extends RxAppCompatActivity {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private GirlAdapter mGirlAdapter;
    private List<PrettyGirl> mPrettyGirlList = new ArrayList<>();
    private GirlApi mGirlApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGirlApi = RestUtils.createApi(GirlApi.class);

        swipeRefresh();
        setupRecyclerView();
        onImageTouch();

    }

    private void swipeRefresh() {
        /**
         * 相当于这样的写法
         * mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override public void onRefresh() {

        }
        });
         */
        RxSwipeRefreshLayout.refreshes(mRefreshLayout)
                .compose(this.<Void>bindToLifecycle())
                //当 Observable un subscribe 时， 该方法会调用
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        //Log.e("RxSwipeRefreshLayout ", " un subscribe ");
                    }
                })
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        fetchGirlData(true);
                        mRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void setupRecyclerView() {

        mGirlAdapter = new GirlAdapter(this, mPrettyGirlList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mGirlAdapter);

        /**
         * 相当于这样的写法
         * mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        }
        });
         */
        RxRecyclerView.scrollEvents(mRecyclerView)
                .compose(this.<RecyclerViewScrollEvent>bindUntilEvent(ActivityEvent.DESTROY))
                //当 Observable un subscribe 时， 该方法会调用
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        //Log.e("RxRecyclerView ", " un subscribe ");
                    }
                })
                .subscribe(new Action1<RecyclerViewScrollEvent>() {
                    @Override
                    public void call(RecyclerViewScrollEvent recyclerViewScrollEvent) {
                        //Log.e("recycler view ", "　is scrolling");
                    }
                });

    }


    private void onImageTouch() {
        mGirlAdapter.setOnTouchListener(new GirlAdapter.OnTouchListener() {
            @Override
            public void onImageTouch(final View v, final PrettyGirl girl) {
                Picasso.with(MeiziActivity.this).load(girl.url).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(MeiziActivity.this, PictureActivity.class);
                        intent.putExtra("url", girl.url);
                        ActivityOptionsCompat compat =
                                ActivityOptionsCompat.makeSceneTransitionAnimation(MeiziActivity.this,
                                        v, "girl");
                        ActivityCompat.startActivity(MeiziActivity.this,
                                intent, compat.toBundle());
                    }

                    @Override
                    public void onError() {
                    }
                });
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (NetUtils.checkNet(this)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                    fetchGirlData(false);
                }
            }, 350);
        } else {
            Snackbar.make(mRecyclerView, "无网络状态不能获取妹纸", Snackbar.LENGTH_INDEFINITE)
                    .setAction("知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    })
                    .show();
        }

    }

    private void fetchGirlData(final boolean clean) {
        mGirlApi.fetchPrettyGirl(null)
                .compose(this.<List<PrettyGirl>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PrettyGirl>>() {
                    @Override
                    public void call(List<PrettyGirl> prettyGirlList) {
                        if (clean) mPrettyGirlList.clear();
                        mPrettyGirlList.addAll(prettyGirlList);
                        mGirlAdapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showError(throwable);
                    }
                });

    }

    private void showError(Throwable throwable) {
        throwable.printStackTrace();
        mRefreshLayout.setRefreshing(false);
        Toast.makeText(this, "服务器出错了不能获取数据", Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
