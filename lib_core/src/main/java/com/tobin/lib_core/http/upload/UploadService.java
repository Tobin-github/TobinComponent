package com.tobin.lib_core.http.upload;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Tobin on 2021/3/15
 * Email: 616041023@qq.com
 * Description:
 */
public interface UploadService {
    @Streaming
    @GET
    Flowable<ResponseBody> startDownLoad(@Url String fileUrl);
}
