package com.tobin.lib_core.http.cookies;

import androidx.annotation.NonNull;

import com.tobin.lib_core.constant.Constants;
import com.tobin.lib_core.utils.KVUtils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by Tobin on 2021/10/12.
 * Email: 616041023@qq.com
 * Description: ReadCookiesInterceptor.
 */
public class ReadCookiesInterceptor  implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> cookies = (HashSet<String>) KVUtils.getStringSet(Constants.KEY_COOKIES_TOKEN, new HashSet<>());
        if (cookies != null) {
            for (String cookie : cookies) {
                builder.addHeader("Cookie", cookie);
                Timber.tag("Tobin").v("OkHttp Adding Header: %s", cookie);
                // This is done so I know which headers are being added;
                // this interceptor is used after the normal logging of OkHttp
            }
        }

        return chain.proceed(builder.build());
    }

}
