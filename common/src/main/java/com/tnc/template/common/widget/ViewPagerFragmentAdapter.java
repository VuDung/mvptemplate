package com.tnc.template.common.widget;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by CUSDungVT on 8/26/2016.
 */

public class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter{

  private final List<Fragment> mFragments = new ArrayList<>();
  private final Map<Integer, String> mTags = new HashMap<>();
  private final List<CharSequence> mTitles = new ArrayList<>();
  private Bundle mSavedInstanceState = new Bundle();
  private final FragmentManager mFragmentManager;

  public ViewPagerFragmentAdapter(FragmentManager fm) {
    super(fm);
    mFragmentManager = fm;
  }

  @Override
  public Fragment getItem(int position) {
    String tagName = mSavedInstanceState.getString(makeTagName(position));
    if (!TextUtils.isEmpty(tagName)) {
      Fragment fragment = mFragmentManager.findFragmentByTag(tagName);
      return fragment != null ? fragment : mFragments.get(position);
    }
    return mFragments.get(position);
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    Object object = super.instantiateItem(container, position);
    mTags.put(position, ((Fragment) object).getTag());
    return object;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return mTitles.get(position);
  }

  public void onRestoreInstanceState(Bundle savedInstanceState) {
    mSavedInstanceState = savedInstanceState != null ? savedInstanceState : new Bundle();
  }
  public void onSaveInstanceState(Bundle outState) {
    Iterator<Map.Entry<Integer, String>> iterator = mTags.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<Integer, String> entry = iterator.next();
      outState.putString(makeTagName(entry.getKey()), entry.getValue());
    }
  }
  public void addFragment(CharSequence title, Fragment fragment) {
    mTitles.add(title);
    mFragments.add(fragment);
  }

  @Override
  public int getCount() {
    return mFragments.size();
  }

  private static String makeTagName(int position) {
    return ViewPagerFragmentAdapter.class.getName() + ":" + position;
  }
}