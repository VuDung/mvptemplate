package com.tnc.template.main.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tnc.template.R;
import com.tnc.template.data.entity.Item;
import com.tnc.template.data.entity.WebItem;

/**
 * Created by CUSDungVT on 1/17/2017.
 */

public class StoryView extends LinearLayout {

  @BindView(R.id.tvTitle) TextView tvTitle;
  @BindView(R.id.tvSource) TextView tvSource;
  @BindView(R.id.tvDisplayTime) TextView tvDisplayTime;
  @BindView(R.id.tvComment) TextView tvComment;
  @BindView(R.id.imgAction) ImageView imgAction;
  @BindView(R.id.tvRank) TextView tvRank;
  @BindView(R.id.tvScore) TextView tvScore;

  public StoryView(Context context) {
    this(context, null);
  }

  public StoryView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public StoryView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    inflate(context, R.layout.layout_story_view, this);
    ButterKnife.bind(this, this);
  }

  public void setStory(@NonNull WebItem story) {
    if (story instanceof Item) {
      Item item = (Item) story;
      tvRank.setText(item.getRank());
      tvScore.setText(item.getScore());
    }
    tvComment.setVisibility(View.VISIBLE);
    tvTitle.setText(story.getDisplayedTitle());
    tvDisplayTime.setText(story.getDisplayedTitle());
    tvDisplayTime.append(story.getDisplayAuthor(getContext(), false, 0));
    switch (story.getType()) {
      case Item.JOB_TYPE:
        tvSource.setText(null);
        break;
      case Item.POLL_TYPE:
        tvSource.setText(null);
        break;
      default:
        tvSource.setText(story.getSource());
        break;
    }
  }

  public void reset() {
    tvTitle.setText(getResources().getString(R.string.loading_text));
    tvDisplayTime.setText(getResources().getString(R.string.loading_text));
    tvSource.setText(getResources().getString(R.string.loading_text));
    tvComment.setVisibility(View.GONE);
  }
}
