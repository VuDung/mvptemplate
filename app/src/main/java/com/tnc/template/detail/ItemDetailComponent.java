package com.tnc.template.detail;

import com.tnc.template.detail.comment.ItemFragment;
import dagger.Subcomponent;

/**
 * Created by CUSDungVT on 1/24/2017.
 */
@Subcomponent(modules = ItemDetailModule.class)
public interface ItemDetailComponent {
  void inject(ItemDetailActivity itemDetailActivity);
  void inject(ItemFragment itemFragment);
}
