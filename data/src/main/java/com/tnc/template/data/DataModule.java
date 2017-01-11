package com.tnc.template.data;

import com.tnc.template.data.api.RestService;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CUSDungVT on 12/22/2016.
 */
@Module
public class DataModule {
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

  @Singleton
  @Provides
  public RestService provideAgoraService(OkHttpClient client,
      @Named("END_POINT_URL") String endPointUrl) {
    Retrofit retrofit = new Retrofit.Builder()
        .client(client)
        .baseUrl(endPointUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    return retrofit.create(RestService.class);
  }

}
