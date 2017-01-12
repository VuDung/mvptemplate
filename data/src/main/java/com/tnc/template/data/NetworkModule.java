package com.tnc.template.data;

import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by CUSDungVT on 1/11/2017.
 */
@Module
public class NetworkModule {
  private final int CONNECTION_TIME_OUT = 60000; //30s
  private final int READ_TIME_OUT = 60000; //30s

  @Singleton
  @Provides
  public OkHttpClient provideOkHttpClient() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    return new OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
        .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
        .build();
  }
}
