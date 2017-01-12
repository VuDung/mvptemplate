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
@Module(includes = NetworkModule.class)
public class DataModule {


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
