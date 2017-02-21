package com.tnc.template.detail.comment;

import com.tnc.template.common.base.MvpPresenter;
import com.tnc.template.data.api.ItemManager;
import com.tnc.template.data.entity.Item;

/**
 * Created by CUSDungVT on 2/17/2017.
 */

public class ItemPresenter extends MvpPresenter<ItemView>{
  private ItemManager itemManager;
  private Item item;
  public ItemPresenter(ItemManager itemManager) {
    this.itemManager = itemManager;
  }

  public void setItem(Item item){
    this.item = item;
  }


  public void load(){
    if(getView() == null){
      return;
    }
    if(item.getKidCount() == 0){
      getView().showEmptyView();
    }else{
      getView().bindKidData();
    }
  }
}
