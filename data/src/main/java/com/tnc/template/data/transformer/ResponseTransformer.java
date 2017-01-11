package com.tnc.template.data.transformer;

import com.tnc.template.data.api.response.Response;
import com.tnc.template.data.exception.ServerException;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by CUSDungVT on 12/23/2016.
 */

public class ResponseTransformer<T> implements Observable.Transformer<Response<T>,T>{

  public static <T> ResponseTransformer<T> create(){
    return new ResponseTransformer<>();
  }
  @Override public Observable<T> call(Observable<Response<T>> responseObservable) {
    return responseObservable.flatMap(new Func1<Response<T>, Observable<T>>() {
      @Override public Observable<T> call(Response<T> response) {
        if(response.isSuccess()){
          return Observable.just(response.getData());
        }else {
          return Observable.error(new ServerException(response.getMessage()));
        }
      }
    });
  }
}
