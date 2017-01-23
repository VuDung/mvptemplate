package com.tnc.template.data;

import android.content.Context;
import android.util.Log;
import com.tnc.template.data.api.HackerNewsManager;
import com.tnc.template.data.api.factory.RestServiceFactory;
import com.tnc.template.data.util.AppUtils;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by CUSDungVT on 1/11/2017.
 */
@Module
public class NetworkModule {

  private static final int CACHE_SIZE = 20 * 1024 * 1024; //20mb

  @Singleton
  @Provides
  RestServiceFactory provideRestServiceFactory(Call.Factory callFactory){
    return new RestServiceFactory.Implement(callFactory);
  }

  @Singleton
  @Provides
  Call.Factory provideCallFactory(Context context) {
    return new OkHttpClient.Builder()
        .cache(new Cache(context.getCacheDir(), CACHE_SIZE))
        .addNetworkInterceptor(new CacheOverrideNetworkInterceptor())
        .addInterceptor(new ConnectionAwareInterceptor(context))
        .addInterceptor(new LoggingInterceptor())
        .followRedirects(false)
        .build();
  }

  private static class ConnectionAwareInterceptor implements Interceptor{
    static final Map<String, String> CACHE_ENABLE_HOST = new HashMap<>();
    static {
      CACHE_ENABLE_HOST.put(HackerNewsManager.HOST, RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M);
    }
    private Context context;
    ConnectionAwareInterceptor(Context context) {
      this.context = context;
    }

    @Override public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      boolean forceCache = CACHE_ENABLE_HOST.containsKey(request.url().host())
          && !AppUtils.hasConnection(context);
      return chain.proceed(forceCache
          ?
          request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
          :
          request);
    }
  }

  private static class CacheOverrideNetworkInterceptor implements Interceptor{
    @Override public Response intercept(Chain chain) throws IOException {
      Request request = chain.request();
      Response response = chain.proceed(request);
      if(!ConnectionAwareInterceptor.CACHE_ENABLE_HOST.containsKey(request.url().host())){
        return response;
      }else {
        return response.newBuilder()
            .header("Cache-Control", ConnectionAwareInterceptor.CACHE_ENABLE_HOST.get(request.url().host()))
            .build();
      }
    }
  }

  private static class LoggingInterceptor implements Interceptor {
    private final Interceptor debugLoggingInterceptor = new HttpLoggingInterceptor().setLevel(
        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    @Override public Response intercept(Chain chain) throws IOException {
      return debugLoggingInterceptor.intercept(chain);
    }
  }
}
