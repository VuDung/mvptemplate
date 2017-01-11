package com.tnc.template.data.transformer;

import com.tnc.template.data.api.response.ListResponse;
import com.tnc.template.data.exception.ServerException;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by CUSDungVT on 12/23/2016.
 */

public class ListResponseTransformer<T> implements Observable.Transformer<ListResponse<T>, List<T>> {
  public static <T> ListResponseTransformer<T> create(){
    return new ListResponseTransformer<>();
  }
  @Override public Observable<List<T>> call(Observable<ListResponse<T>> listResponseObservable) {
    return listResponseObservable.flatMap(new Func1<ListResponse<T>, Observable<List<T>>>() {
      @Override public Observable<List<T>> call(ListResponse<T> tListResponse) {
        if(tListResponse.isSuccess()) {
          return Observable.just(tListResponse.getData());
        }else{
          return Observable.error(new ServerException(tListResponse.getMessage()));
        }
      }
    });
  }
}
