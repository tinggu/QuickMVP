package me.tinggu.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import me.tinggu.sample.R;
import me.tinggu.sample.model.PrettyGirl;
import me.tinggu.sample.widget.RatioImageView;
import rx.functions.Action1;

public class GirlAdapter extends RecyclerView.Adapter<GirlAdapter.GirlViewHolder> {

    private Context mContext;
    private List<PrettyGirl> mPrettyGirlList;
    private OnTouchListener onTouchListener;

    public GirlAdapter(Context context, List<PrettyGirl> prettyGirlList) {
        this.mContext = context;
        this.mPrettyGirlList = prettyGirlList;
    }


    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    @Override
    public GirlViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View girlItem = layoutInflater.inflate(R.layout.girl_item, parent, false);
        GirlViewHolder holder = new GirlViewHolder(girlItem);
        return holder;
    }

    @Override
    public void onBindViewHolder(GirlViewHolder holder, int position) {
        PrettyGirl girl = mPrettyGirlList.get(position);
        ViewGroup.LayoutParams lp = holder.imageView.getLayoutParams();
        holder.imageView.setOriginal(girl.meta.width, girl.meta.height);
        lp.height = girl.meta.height;
        
        holder.girl = girl;

        Glide.with(mContext)
                .load(girl.url)
                .into(holder.imageView);
    }

//    @Override
//    public int getItemViewType(int position) {
//        PrettyGirl girl = mPrettyGirlList.get(position);
//        return Math.round((float) girl.meta.width / (float) girl.meta.height * 10f);
//    }

    @Override
    public int getItemCount() {
        return mPrettyGirlList.size();
    }

    class GirlViewHolder extends RecyclerView.ViewHolder {

        RatioImageView imageView;

        PrettyGirl girl;

        public GirlViewHolder(View itemView) {
            super(itemView);
            this.girl = girl;
            imageView = (RatioImageView) itemView.findViewById(R.id.image);
//            imageView.setOriginal(girl.meta.width, girl.meta.height);
            //防止手抖连续点击图片打开两个页面
            RxView.clicks(imageView)
                    .throttleFirst(1000, TimeUnit.MILLISECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            if (onTouchListener != null) {
                                onTouchListener.onImageTouch(imageView, girl);
                            }
                        }
                    });
        }
    }

    public interface OnTouchListener {
        void onImageTouch(View v, PrettyGirl girl);
    }
}
