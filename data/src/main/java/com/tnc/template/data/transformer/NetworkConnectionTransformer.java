package com.tnc.template.data.transformer;

import android.content.Context;
import com.tnc.template.data.exception.NetworkException;
import com.tnc.template.data.util.AppUtils;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by CUSDungVT on 12/23/2016.
 */

public class NetworkConnectionTransformer<T> implements Observable.Transformer<T, T>{
  private Context context;
  private NetworkConnectionTransformer(Context context){
    this.context = context;
  }
  public static <T> NetworkConnectionTransformer<T> create(Context context){
    return new NetworkConnectionTransformer<>(context);
  }
  @Override public Observable<T> call(Observable<T> tObservable) {
    if(AppUtils.hasConnection(context)){
      return tObservable;
    }else {
      return Observable.timer(1, TimeUnit.SECONDS, Schedulers.immediate())
          .flatMap(new Func1<Long, Observable<T>>() {
            @Override public Observable<T> call(Long aLong) {
              return Observable.error(new NetworkException());
            }
          });
    }
  }
}
