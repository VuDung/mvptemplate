package com.tnc.template.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import butterknife.BindView;
import com.tnc.template.R;
import com.tnc.template.common.base.BaseActivity;
import com.tnc.template.common.di.Injector;
import com.tnc.template.main.list.ListStoryFragment;

/**
 * Created by CUSDungVT on 1/10/2017.
 */

public class MainActivity extends BaseActivity implements Injector{

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.container) FrameLayout container;
  @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
  @BindView(R.id.svDrawer) ScrollView svDrawer;
  private ActionBarDrawerToggle drawerToggle;
  private MainComponent mainComponent;

  @Override protected int layoutRes() {
    return R.layout.activity_main;
  }

  @Override protected void dependencyInjection(Bundle savedInstanceState) {
    mainComponent().inject(this);

    setSupportActionBar(toolbar);
    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    drawerLayout.addDrawerListener(drawerToggle);

    if(savedInstanceState == null){
      getSupportFragmentManager().beginTransaction().replace(R.id.container, new ListStoryFragment()).commit();
    }
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
    if(drawerLayout.isDrawerOpen(svDrawer)){
      drawerLayout.closeDrawer(svDrawer);
    }else {
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
    if(object instanceof ListStoryFragment){
      mainComponent().inject((ListStoryFragment)object);
    }
  }
}
