package com.tnc.template.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.tnc.template.R;
import com.tnc.template.common.base.BaseActivity;
import com.tnc.template.data.entity.WebItem;

/**
 * Created by CUSDungVT on 1/23/2017.
 */

public class ItemDetailActivity extends BaseActivity{

  private static final String EXTRA_ITEM = "extra_item";
  public static void start(Context context, WebItem item) {
      Intent starter = new Intent(context, ItemDetailActivity.class);
      starter.putExtra(EXTRA_ITEM, item);
      context.startActivity(starter);
  }
  @Override protected int layoutRes() {
    return R.layout.activity_item_detail;
  }

  @Override protected void dependencyInjection(Bundle savedInstanceState) {

  }
}
