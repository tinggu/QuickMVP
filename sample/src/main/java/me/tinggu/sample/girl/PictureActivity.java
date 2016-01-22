package me.tinggu.sample.girl;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import me.tinggu.sample.R;
import me.tinggu.sample.widget.PullBackLayout;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by zsj on 2015/11/21 0021.
 */
public class PictureActivity extends AppCompatActivity {

    private String mGirlUrl;
    private ImageView mImageView;
    private PhotoViewAttacher mViewAttacher;
    private PullBackLayout mPullBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        mImageView = (ImageView) findViewById(R.id.iv_photo);
        mPullBackLayout = (PullBackLayout) findViewById(R.id.pullBackLayout);

        mGirlUrl = getIntent().getExtras().getString("url");

        ViewCompat.setTransitionName(mImageView, "girl");

        Picasso.with(this).load(mGirlUrl)
                .into(mImageView);

        mViewAttacher = new PhotoViewAttacher(mImageView);

        mPullBackLayout.setPullCallBack(new PullBackLayout.PullCallBack() {
            @Override
            public void onPullCompleted() {
                PictureActivity.super.onBackPressed();
            }
        });
    }

}
