package com.tnc.template.data.api.factory;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import okhttp3.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public interface RestServiceFactory {
  String CACHE_CONTROL_FORCE_CACHE = "Cache-Control: only-if-cached, max-stale=" + Integer.MAX_VALUE;
  String CACHE_CONTROL_FORCE_NETWORK = "Cache-Control: no-cache";
  String CACHE_CONTROL_MAX_AGE_30M = "Cache-Control: max-age=" + (30 * 60); //30 minutes
  String CACHE_CONTROL_MAX_AGE_24H = "Cache-Control: max-age" + (24 * 60 * 60); //24 hours

  RestServiceFactory rxEnable(boolean enable);
  <T> T create(String baseUrl, Class<T> clazz);
  <T> T create(String baseUrl, Class<T> clazz, Executor executor);

  class Implement implements RestServiceFactory{
    private final Call.Factory callFactory;
    private boolean rxEnabled;

    public Implement(Call.Factory callFactory) {
      this.callFactory = callFactory;
    }

    @Override public RestServiceFactory rxEnable(boolean enable) {
      this.rxEnabled = enable;
      return this;
    }

    @Override public <T> T create(String baseUrl, Class<T> clazz) {
      return create(baseUrl, clazz, null);
    }

    @Override public <T> T create(String baseUrl, Class<T> clazz, Executor executor) {
      Retrofit.Builder builder = new Retrofit.Builder();
      if(rxEnabled){
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()));
      }
      builder.callFactory(callFactory)
          .callbackExecutor(executor != null ? executor : new MainThreadExecutor());
      return builder.baseUrl(baseUrl)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(clazz);
    }
  }

  class MainThreadExecutor implements Executor{
    private Handler handler = new Handler(Looper.getMainLooper());
    @Override public void execute(Runnable runnable) {
      handler.post(runnable);
    }
  }
}
