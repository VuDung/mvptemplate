package com.tnc.template.detail.comment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.tnc.template.R;
import com.tnc.template.common.base.BaseFragment;
import com.tnc.template.data.api.ItemManager;
import com.tnc.template.data.entity.Item;
import com.tnc.template.data.entity.WebItem;
import javax.inject.Inject;

/**
 * Created by CUSDungVT on 2/17/2017.
 */

public class ItemFragment extends BaseFragment implements ItemView{
  @BindView(R.id.rvComment) RecyclerView rvComment;
  @BindView(R.id.vEmpty) View vEmpty;
  @Inject ItemPresenter itemPresenter;
  private int cacheMode;
  private Item item;
  private static final String ARGS_ITEM = "args_item";
  private static final String ARGS_CACHE_MODE = "args_cache_mode";

  public static ItemFragment newInstance(WebItem item, int cacheMode) {
    Bundle args = new Bundle();
    args.putParcelable(ARGS_ITEM, item);
    args.putInt(ARGS_CACHE_MODE, cacheMode);
    ItemFragment fragment = new ItemFragment();
    fragment.setArguments(args);
    return fragment;
  }
  @Override protected int layoutRes() {
    return R.layout.fragment_item;
  }

  @Override protected void dependencyInjection(Bundle savedInstanceState) {
    injector().inject(this);
    item = getArguments().getParcelable(ARGS_ITEM);
    cacheMode = getArguments().getInt(ARGS_CACHE_MODE, ItemManager.MODE_NETWORK);

    itemPresenter.setItem(item);
    itemPresenter.setCacheMode(cacheMode);
    itemPresenter.attachView(this);
    itemPresenter.load();
  }

  @Override public void showEmptyView() {
    vEmpty.setVisibility(View.VISIBLE);
  }

  @Override public void bindKidData() {

  }
}
