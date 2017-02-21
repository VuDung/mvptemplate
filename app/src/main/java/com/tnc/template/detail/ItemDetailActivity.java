package com.tnc.template.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import com.tnc.template.R;
import com.tnc.template.common.base.BaseActivity;
import com.tnc.template.common.di.Injector;
import com.tnc.template.common.widget.ViewPagerFragmentAdapter;
import com.tnc.template.data.api.ItemManager;
import com.tnc.template.data.entity.WebItem;
import com.tnc.template.detail.comment.ItemFragment;
import com.tnc.template.detail.web.WebFragment;

/**
 * Created by CUSDungVT on 1/23/2017.
 */

public class ItemDetailActivity extends BaseActivity implements Injector{
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.tabLayout) TabLayout tabLayout;
  @BindView(R.id.viewPager) ViewPager viewPager;
  private ItemDetailComponent itemDetailComponent;

  private int cacheMode;
  private WebItem item;
  private static final String EXTRA_ITEM = "extra_item";
  private static final String EXTRA_CACHE_MODE = "extra_cache_mode";

  public static void start(Context context, WebItem item, int cacheMode) {
    Intent starter = new Intent(context, ItemDetailActivity.class);
    starter.putExtra(EXTRA_ITEM, item);
    starter.putExtra(EXTRA_CACHE_MODE, cacheMode);
    context.startActivity(starter);
  }

  @Override protected int layoutRes() {
    return R.layout.activity_item_detail;
  }

  @Override protected void dependencyInjection(Bundle savedInstanceState) {
    itemDetailComponent().inject(this);
    item = getIntent().getParcelableExtra(EXTRA_ITEM);
    cacheMode = getIntent().getIntExtra(EXTRA_CACHE_MODE, ItemManager.MODE_NETWORK);

    toolbar.setTitle(item.getDisplayedTitle());
    toolbar.setNavigationOnClickListener((view -> onBackPressed()));
    setSupportActionBar(toolbar);


    ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
    adapter.addFragment("Comment", ItemFragment.newInstance(item, cacheMode));
    adapter.addFragment("Url", new WebFragment());
    viewPager.setAdapter(adapter);
    tabLayout.setupWithViewPager(viewPager);
  }

  private ItemDetailComponent itemDetailComponent() {
    if (itemDetailComponent == null) {
      itemDetailComponent = superComponent().plus(new ItemDetailModule());
    }
    return itemDetailComponent;
  }

  @Override public void inject(Object object) {
    if (object instanceof ItemFragment) {
      itemDetailComponent().inject((ItemFragment) object);
    }
  }
}
