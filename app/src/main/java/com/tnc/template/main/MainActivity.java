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
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.tnc.template.R;
import com.tnc.template.common.base.BaseActivity;
import com.tnc.template.common.di.Injector;
import com.tnc.template.common.widget.RadioViewGroupController;
import com.tnc.template.data.api.ItemManager;
import com.tnc.template.data.storage.AppPreference;
import com.tnc.template.main.list.ListStoryFragment;
import javax.inject.Inject;

/**
 * Created by CUSDungVT on 1/10/2017.
 */

public class MainActivity extends BaseActivity implements Injector {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.container) FrameLayout container;
  @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
  @BindView(R.id.svDrawer) ScrollView svDrawer;

  @BindView(R.id.drawerTop) Button drawerTop;
  @BindView(R.id.drawerNew) Button drawerNew;
  @BindView(R.id.drawerShow) Button drawerShow;
  @BindView(R.id.drawerAsk) Button drawerAsk;
  @BindView(R.id.drawerJob) Button drawerJob;
  @BindView(R.id.drawerBest) Button drawerBest;
  private RadioViewGroupController radioViewGroupController;
  private ActionBarDrawerToggle drawerToggle;
  private MainComponent mainComponent;
  private String fetchMode;
  @Inject AppPreference appPreference;
  private final String TAG = MainActivity.class.getSimpleName();

  @Override protected int layoutRes() {
    return R.layout.activity_main;
  }

  @Override protected void dependencyInjection(Bundle savedInstanceState) {
    mainComponent().inject(this);

    setSupportActionBar(toolbar);
    drawerToggle = new ActionBarDrawerToggle(
        this,
        drawerLayout,
        toolbar,
        R.string.drawer_open,
        R.string.drawer_close){
      @Override public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        Log.i(TAG, "onDrawerOpened [FetchMode: " + fetchMode + "]");
        switch (fetchMode){
          case ItemManager.TOP_FETCH_MODE:
            radioViewGroupController.setSelection(0);
            break;
          case ItemManager.ASK_FETCH_MODE:
            radioViewGroupController.setSelection(1);
            break;
          case ItemManager.BEST_FETCH_MODE:
            radioViewGroupController.setSelection(2);
            break;
          case ItemManager.JOBS_FETCH_MODE:
            radioViewGroupController.setSelection(3);
            break;
          case ItemManager.NEW_FETCH_MODE:
            radioViewGroupController.setSelection(4);
            break;
          case ItemManager.SHOW_FETCH_MODE:
            radioViewGroupController.setSelection(5);
            break;
        }
      }
    };
    drawerLayout.addDrawerListener(drawerToggle);
    radioViewGroupController = new RadioViewGroupController();
    radioViewGroupController.setRadioButtons(drawerTop, drawerAsk, drawerBest, drawerJob, drawerNew, drawerShow);
    radioViewGroupController.setOnCheckedChangeListener((int checkedId, int position) ->{
        setUpFetchMode(checkedId);
    });
    if (savedInstanceState == null) {
      fetchMode = appPreference.getFetchMode();
      radioViewGroupController.setSelection(0);
      getSupportFragmentManager().beginTransaction().replace(
          R.id.container,
          ListStoryFragment.newInstance(fetchMode)).commit();
    }
  }

  private void setUpFetchMode(int checkId){
    switch (checkId){
      case R.id.drawerTop:
        fetchMode = ItemManager.TOP_FETCH_MODE;
        break;
      case R.id.drawerAsk:
        fetchMode = ItemManager.ASK_FETCH_MODE;
        break;
      case R.id.drawerBest:
        fetchMode = ItemManager.BEST_FETCH_MODE;
        break;
      case R.id.drawerJob:
        fetchMode = ItemManager.JOBS_FETCH_MODE;
        break;
      case R.id.drawerNew:
        fetchMode = ItemManager.NEW_FETCH_MODE;
        break;
      case R.id.drawerShow:
        fetchMode = ItemManager.SHOW_FETCH_MODE;
        break;
    }
    appPreference.setFetchMode(fetchMode);
    drawerLayout.closeDrawer(svDrawer);
    getSupportFragmentManager().beginTransaction().replace(
        R.id.container,
        ListStoryFragment.newInstance(fetchMode)).commit();
  }

  @Override protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
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
