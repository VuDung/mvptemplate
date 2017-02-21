package com.tnc.template.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.tnc.template.R;
import com.tnc.template.common.base.BaseActivity;
import com.tnc.template.common.di.Injector;
import com.tnc.template.common.navigator.Navigator;
import com.tnc.template.common.widget.RadioViewGroupController;
import com.tnc.template.data.api.ItemManager;
import com.tnc.template.data.entity.WebItem;
import com.tnc.template.data.storage.AppPreference;
import com.tnc.template.data.storage.SessionManager;
import com.tnc.template.data.util.StringUtils;
import com.tnc.template.main.list.ListStoryFragment;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;

/**
 * Created by CUSDungVT on 1/10/2017.
 */

public class MainActivity extends BaseActivity implements Injector, ItemListener {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.container) FrameLayout container;
  @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
  @BindView(R.id.svDrawer) ScrollView svDrawer;

  @BindViews({
      R.id.drawerTop,
      R.id.drawerNew,
      R.id.drawerShow,
      R.id.drawerAsk,
      R.id.drawerJob,
      R.id.drawerBest
  })
  Button[] drawerItems;
  String[] fetchModes = new String[] {
      ItemManager.TOP_FETCH_MODE,
      ItemManager.NEW_FETCH_MODE,
      ItemManager.SHOW_FETCH_MODE,
      ItemManager.ASK_FETCH_MODE,
      ItemManager.JOBS_FETCH_MODE,
      ItemManager.BEST_FETCH_MODE
  };
  private ActionBarDrawerToggle drawerToggle;
  private MainComponent mainComponent;
  private String fetchMode;
  @Inject AppPreference appPreference;
  @Inject SessionManager sessionManager;
  @Inject Navigator navigator;
  private final String TAG = MainActivity.class.getSimpleName();

  @Override protected int layoutRes() {
    return R.layout.activity_main;
  }

  @Override protected void dependencyInjection(Bundle savedInstanceState) {
    mainComponent().inject(this);
    RadioViewGroupController radioViewGroupController = new RadioViewGroupController();
    radioViewGroupController.setViews(drawerItems);
    radioViewGroupController.setOnCheckedChangeListener((int checkedId, int position) -> {
      fetchMode = fetchModes[position];
      setUpFetchMode();
    });

    if (savedInstanceState == null) {
      fetchMode = appPreference.getFetchMode();
      radioViewGroupController.setSelectionAtFirst(findPositionDependOnFetchMode());
    }
    setSupportActionBar(toolbar);
    drawerToggle = new ActionBarDrawerToggle(
        this,
        drawerLayout,
        toolbar,
        R.string.drawer_open,
        R.string.drawer_close);
    drawerLayout.addDrawerListener(drawerToggle);
  }

  private int findPositionDependOnFetchMode() {
    for (int i = 0; i < fetchModes.length; i++) {
      if (fetchMode.equals(fetchModes[i])) {
        return i;
      }
    }
    return 0;
  }

  private void setUpFetchMode() {
    setTitleToolbarByFetchMode();
    getSupportFragmentManager().beginTransaction().replace(
        R.id.container,
        ListStoryFragment.newInstance(fetchMode)).commit();

    appPreference.setFetchMode(fetchMode);
    drawerLayout.closeDrawer(svDrawer);
  }

  private void setTitleToolbarByFetchMode() {
    String title = String.format(
        Locale.US,
        getString(R.string.title_story_format),
        StringUtils.upperCaseFirstCharacter(fetchMode));
    toolbar.setTitle(title);
  }

  @Override protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
  }

  @Override public void onItemSelected(WebItem item, int cacheMode) {
    sessionManager.view(this, item.getItemId());
    navigator.goToItemDetailActivity(this, item, cacheMode);
  }

  @Override public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }

  @Override public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(svDrawer)) {
      drawerLayout.closeDrawer(svDrawer);
    } else {
      super.onBackPressed();
    }
  }

  private MainComponent mainComponent() {
    if (mainComponent == null) {
      mainComponent = superComponent().plus(new MainModule());
    }
    return mainComponent;
  }

  @Override public void inject(Object object) {
    if (object instanceof ListStoryFragment) {
      mainComponent().inject((ListStoryFragment) object);
    }
  }
}
