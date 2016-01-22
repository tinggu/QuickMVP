package me.tinggu.sample.api;

import java.util.List;

import me.tinggu.sample.model.PrettyGirl;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface GirlApi {

    @GET("v1/channels/images.json")
    Observable<List<PrettyGirl>> fetchPrettyGirl(@Query("tags") String tags);
}
