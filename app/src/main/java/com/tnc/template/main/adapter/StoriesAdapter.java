package com.tnc.template.main.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tnc.template.R;
import com.tnc.template.TemplateApplication;
import com.tnc.template.data.DataModule;
import com.tnc.template.data.api.ItemManager;
import com.tnc.template.data.api.response.ResponseListener;
import com.tnc.template.data.entity.Item;
import com.tnc.template.main.MainComponent;
import com.tnc.template.main.MainModule;
import com.tnc.template.main.view.StoryView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by CUSDungVT on 1/17/2017.
 */

public class StoriesAdapter extends RecyclerViewAdapter<StoriesAdapter.StoryViewHolder>{
  private List<Item> items = new ArrayList<>();
  private LongSparseArray<Integer> itemPositions = new LongSparseArray<>();
  private MainComponent mainComponent;
  private int cacheMode = ItemManager.MODE_DEFAULT;
  @Inject @Named(DataModule.HN) ItemManager hackerNewsManager;

  private MainComponent mainComponent() {
    if (mainComponent == null) {
      mainComponent = TemplateApplication.get(getContext()).plus(new MainModule());
    }
    return mainComponent;
  }

  public void setCacheMode(int cacheMode){
    this.cacheMode = cacheMode;
  }
  @Override public void attach(Context context, RecyclerView recyclerView) {
    super.attach(context, recyclerView);
    mainComponent().inject(this);
  }


  @Override
  public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new StoryViewHolder(getLayoutInflater().inflate(R.layout.item_story, parent, false));
  }

  @Override public void onBindViewHolder(StoryViewHolder holder, int position) {
    final Item item = getItem(position);
    if(!isItemAvailable(item)){
      loadItemDetailAtPosition(holder.getAdapterPosition());
      return;
    }
    holder.bind(item);
  }

  private void loadItemDetailAtPosition(int position){
    Item item = getItem(position);
    if(item.getLocalRevision() == 0){
      return;
    }
    item.setLocalRevision(0);
    hackerNewsManager.getItem(item.getItemId(), getCacheMode(), new ItemResponseListener(this, item));
  }

  public void onItemLoaded(Item item){
    int position = itemPositions.get(item.getLongItemId());
    if(position >= 0 && position < getItemCount()){
      notifyItemChanged(position);
    }
  }

  private @ItemManager.CacheMode int getCacheMode(){
    return cacheMode;
  }

  @Override public int getItemCount() {
    return items != null ? items.size() : 0;
  }

  public void setItems(List<Item> items){
    this.items.clear();
    this.items.addAll(items);
    for(int i = 0; i < items.size(); i++){
      itemPositions.put(items.get(i).getLongItemId(), i);
    }
    notifyDataSetChanged();
  }

  public List<Item> getItems(){
    return items;
  }

  public Item getItem(int position){
    return items != null && items.size() > 0 ? items.get(position) : null;
  }

  protected boolean isItemAvailable(Item item) {
    return item != null && item.getLocalRevision() > 0;
  }

  static class StoryViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.storyView) StoryView storyView;
    public StoryViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

    }

    public void bind(Item item){
      storyView.setStory(item);
    }
  }

  private static class ItemResponseListener implements ResponseListener<Item>{
    private final WeakReference<StoriesAdapter> ref;
    private final Item populateItem;

    ItemResponseListener(StoriesAdapter adapter, Item populateItem){
      this.ref = new WeakReference<>(adapter);
      this.populateItem = populateItem;
    }
    @Override public void onResponse(@Nullable Item data) {
      if(ref.get() != null && ref.get().isAttached() && data != null){
        populateItem.populate(data);
        ref.get().onItemLoaded(populateItem);
      }
    }

    @Override public void onError(Throwable throwable) {
      //do nothing
      throwable.printStackTrace();
    }
  }
}
