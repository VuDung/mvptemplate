package com.tnc.template.data.api;

import com.tnc.template.data.api.factory.RestServiceFactory;
import com.tnc.template.data.api.response.ResponseListener;
import com.tnc.template.data.entity.HackerNewsItem;
import com.tnc.template.data.entity.Item;
import com.tnc.template.data.entity.UserItem;
import com.tnc.template.data.storage.FavoriteManager;
import com.tnc.template.data.storage.SessionManager;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public class HackerNewsManager implements ItemManager {
  public static final String HOST = "hacker-news.firebaseio.com";
  public static final String BASE_WEB_URL = "https://news.ycombinator.com";
  public static final String WEB_ITEM_PATH = BASE_WEB_URL + "/item?id=%s";
  static final String BASE_API_URL = "https://" + HOST + "/v0/";

  private RestService restService;
  private SessionManager sessionManager;
  private FavoriteManager favoriteManager;

  @Inject
  public HackerNewsManager(
      RestServiceFactory restServiceFactory,
      SessionManager sessionManager,
      FavoriteManager favoriteManager) {
    this.restService = restServiceFactory.rxEnable(true).create(BASE_API_URL, RestService.class);
    this.sessionManager = sessionManager;
    this.favoriteManager = favoriteManager;
  }

  @Override public void getStories(@FetchMode String filter, @CacheMode int mode,
      ResponseListener<Item[]> listener) {
    if(listener == null){
      return;
    }
    getStoriesObservable(filter, mode)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            listener::onResponse,
            throwable -> listener.onError(throwable != null ? throwable.getMessage() : ""));
  }

  @Override public void getItem(String itemId, @CacheMode int mode,
      ResponseListener<Item> listener) {

  }

  private Observable<Item[]> getStoriesObservable(@FetchMode String filter, @CacheMode int cacheMode){
    Observable<int[]> observable;
    switch (filter){
      case NEW_FETCH_MODE:
        observable = (cacheMode == MODE_NETWORK) ? restService.networkNewStoriesRx() : restService.newStoriesRx();
        break;
      case SHOW_FETCH_MODE:
        observable = (cacheMode == MODE_NETWORK) ? restService.networkShowStoriesRx() : restService.showStoriesRx();
        break;
      case ASK_FETCH_MODE:
        observable = (cacheMode == MODE_NETWORK) ? restService.networkAskStoriesRx() : restService.askStoriesRx();
        break;
      case JOBS_FETCH_MODE:
        observable = cacheMode == MODE_NETWORK ? restService.networkJobStoriesRx() : restService.jobStoriesRx();
        break;
      case BEST_FETCH_MODE:
        observable = cacheMode == MODE_NETWORK ? restService.networkBestStoriesRx() : restService.bestStoriesRx();
        break;
      default:
        observable = cacheMode == MODE_NETWORK ? restService.networkTopStoriesRx() : restService.topStoriesRx();
        break;
    }
    return observable.map(this::toItems);
  }

  private Item[] toItems(int[] ids){
    if(ids == null){
      return null;
    }
    HackerNewsItem[] items = new HackerNewsItem[ids.length];
    return null;
  }

  interface RestService {
    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("topstories.json")
    Observable<int[]> topStoriesRx();

    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("newstories.json")
    Observable<int[]> newStoriesRx();

    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("showstories.json")
    Observable<int[]> showStoriesRx();

    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("askstories.json")
    Observable<int[]> askStoriesRx();

    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("jobstories.json")
    Observable<int[]> jobStoriesRx();

    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("beststories.json")
    Observable<int[]> bestStoriesRx();

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("topstories.json")
    Observable<int[]> networkTopStoriesRx();

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("newstories.json")
    Observable<int[]> networkNewStoriesRx();

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("showstories.json")
    Observable<int[]> networkShowStoriesRx();

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("askstories.json")
    Observable<int[]> networkAskStoriesRx();

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("jobstories.json")
    Observable<int[]> networkJobStoriesRx();

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("beststories.json")
    Observable<int[]> networkBestStoriesRx();

    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("item/{itemId}.json")
    Observable<HackerNewsItem> itemRx(@Path("itemId") String itemId);

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("item/{itemId}.json")
    Observable<HackerNewsItem> networkItemRx(@Path("itemId") String itemId);

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_CACHE)
    @GET("item/{itemId}.json")
    Observable<HackerNewsItem> cachedItemRx(@Path("itemId") String itemId);

    @GET("user/{userId}.json")
    Observable<UserItem> userRx(@Path("userId") String userId);

    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("topstories.json")
    Call<int[]> topStories();

    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("newstories.json")
    Call<int[]> newStories();

    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("showstories.json")
    Call<int[]> showStories();

    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("askstories.json")
    Call<int[]> askStories();

    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("jobstories.json")
    Call<int[]> jobStories();

    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("beststories.json")
    Call<int[]> bestStories();

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("topstories.json")
    Call<int[]> networkTopStories();

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("newstories.json")
    Call<int[]> networkNewStories();

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("showstories.json")
    Call<int[]> networkShowStories();

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("askstories.json")
    Call<int[]> networkAskStories();

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("jobstories.json")
    Call<int[]> networkJobStories();

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("beststories.json")
    Call<int[]> networkBestStories();

    @Headers(RestServiceFactory.CACHE_CONTROL_MAX_AGE_30M)
    @GET("item/{itemId}.json")
    Call<HackerNewsItem> item(@Path("itemId") String itemId);

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_NETWORK)
    @GET("item/{itemId}.json")
    Call<HackerNewsItem> networkItem(@Path("itemId") String itemId);

    @Headers(RestServiceFactory.CACHE_CONTROL_FORCE_CACHE)
    @GET("item/{itemId}.json")
    Call<HackerNewsItem> cachedItem(@Path("itemId") String itemId);

    @GET("user/{userId}.json")
    Call<UserItem> user(@Path("userId") String userId);
  }
}
