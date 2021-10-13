package com.tobin.lib_core.http.cookies;

import androidx.annotation.NonNull;

import com.tobin.lib_core.constant.Constants;
import com.tobin.lib_core.utils.KVUtils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by Tobin on 2021/10/12.
 * Email: 616041023@qq.com
 * Description: SaveCookiesInterceptor.
 */
public class SaveCookiesInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            HashSet<String> cookies = new HashSet<>(originalResponse.headers("Set-Cookie"));
            Timber.tag("Tobin").d("Set-Cookie: %s", cookies);
            KVUtils.put(Constants.KEY_COOKIES_TOKEN, cookies);

        }

        return originalResponse;
    }
}
