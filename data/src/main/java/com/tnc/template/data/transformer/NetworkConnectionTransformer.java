package com.tnc.template.data.transformer;

import com.tnc.template.data.Network;
import com.tnc.template.data.exception.NetworkException;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by CUSDungVT on 12/23/2016.
 */

public class NetworkConnectionTransformer<T> implements Observable.Transformer<T, T>{
  private Network network;
  private NetworkConnectionTransformer(Network network){
    this.network = network;
  }
  public static <T> NetworkConnectionTransformer<T> create(Network network){
    return new NetworkConnectionTransformer<>(network);
  }
  @Override public Observable<T> call(Observable<T> tObservable) {
    if(network.isNetworkAvailable()){
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
