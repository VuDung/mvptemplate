package com.tnc.template.main.list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.tnc.template.R;
import com.tnc.template.common.base.BaseFragment;
import com.tnc.template.data.api.ItemManager;
import com.tnc.template.data.entity.Item;
import com.tnc.template.data.util.AppUtils;
import com.tnc.template.main.adapter.StoriesAdapter;
import java.util.Arrays;
import javax.inject.Inject;

/**
 * Created by CUSDungVT on 1/12/2017.
 */

public class ListStoryFragment extends BaseFragment implements ListStoryView {

  @BindView(R.id.rvList) RecyclerView rvList;
  @BindView(R.id.vLoading) FrameLayout vLoading;
  @BindView(R.id.vEmpty) LinearLayout vEmpty;
  @BindView(R.id.tvErrorMessage) TextView tvErrorMessage;
  @BindView(R.id.vError) LinearLayout vError;
  @BindView(R.id.vErrorNetwork) LinearLayout vErrorNetwork;
  @Inject ListStoryPresenter presenter;

  private int cacheMode = ItemManager.MODE_DEFAULT;
  private String fetchMode;
  private StoriesAdapter storiesAdapter;

  private static final String ARGS_FETCH_MODE = "args_fetch_mode";
  private static final String STATE_FILTER_MODE = "state_filter_mode";
  private static final String STATE_CACHE_MODE = "state_cache_mode";
  private final String TAG = ListStoryFragment.class.getSimpleName();
  public static ListStoryFragment newInstance(String fetchMode) {
    Bundle args = new Bundle();
    args.putString(ARGS_FETCH_MODE, fetchMode);
    ListStoryFragment fragment = new ListStoryFragment();
    fragment.setArguments(args);
    return fragment;
  }
  @Override protected int layoutRes() {
    return R.layout.fragment_list;
  }

  @Override protected void dependencyInjection(Bundle savedInstanceState) {
    injector().inject(this);
    if(savedInstanceState == null) {
      fetchMode = getArguments().getString(ARGS_FETCH_MODE);
    }else{
      fetchMode = savedInstanceState.getString(STATE_FILTER_MODE);
      cacheMode = savedInstanceState.getInt(STATE_CACHE_MODE);
    }

    Log.i(TAG, "[FetchMode:" + fetchMode + "][CacheMode:" + cacheMode + "]");
    storiesAdapter = new StoriesAdapter();
    storiesAdapter.attach(getActivity(), rvList);
    storiesAdapter.setCacheMode(cacheMode);
    storiesAdapter.setHotThresHold(AppUtils.HOT_THRESHOLD_NORMAL);
    switch (fetchMode){
      case ItemManager.BEST_FETCH_MODE:
        storiesAdapter.setHotThresHold(AppUtils.HOT_THRESHOLD_HIGH);
        break;
      case ItemManager.NEW_FETCH_MODE:
        storiesAdapter.setHotThresHold(AppUtils.HOT_THRESHOLD_LOW);
    }
    rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
    rvList.setAdapter(storiesAdapter);

    presenter.attachView(this);
    presenter.getStories(fetchMode, cacheMode);
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(STATE_FILTER_MODE, fetchMode);
    outState.putInt(STATE_CACHE_MODE, cacheMode);
  }

  @Override public void onDestroyView() {
    presenter.detachView();
    super.onDestroyView();
  }

  @Override public void showLoading() {
    hideEmptyView();
    hideErrorView();
    hideErrorNetwork();
    vLoading.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    vLoading.setVisibility(View.GONE);
  }

  @Override public void showErrorView(String message) {
    hideLoading();
    hideEmptyView();
    hideContent();
    vError.setVisibility(View.VISIBLE);
    if (!TextUtils.isEmpty(message)) {
      tvErrorMessage.setText(message);
    }
  }

  @OnClick({R.id.vError, R.id.vErrorNetwork, R.id.vEmpty})
  public void onClickViewError(){
    presenter.getStories(fetchMode, cacheMode);
  }

  @Override public void hideErrorView() {
    vError.setVisibility(View.GONE);
  }

  @Override public void showErrorNetwork() {
    hideLoading();
    hideEmptyView();
    hideContent();
    hideErrorView();
    vErrorNetwork.setVisibility(View.VISIBLE);
  }

  @Override public void hideErrorNetwork() {
    vErrorNetwork.setVisibility(View.GONE);
  }

  @Override public void hideEmptyView() {
    vEmpty.setVisibility(View.GONE);
  }

  @Override public void showEmptyView() {
    hideLoading();
    hideErrorView();
    hideContent();
    vEmpty.setVisibility(View.VISIBLE);
  }

  @Override public void hideContent() {
    rvList.setVisibility(View.GONE);
  }

  @Override public void swapContent(Item[] items) {
    hideLoading();
    hideErrorView();
    hideEmptyView();
    hideErrorNetwork();
    rvList.setVisibility(View.VISIBLE);
    storiesAdapter.setItems(Arrays.asList(items));

  }

}
