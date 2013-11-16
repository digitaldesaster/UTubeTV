package com.sickboots.sickvideos;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;

public class ScrollTriggeredAnimator {
  private int mInDuration;
  private int mOutDuration;
  private View mAnimationTarget;
  private boolean mGlowing = false;
  private final Handler mHandler = new Handler(Looper.getMainLooper());

  private final Runnable mBackgroundDimmerFadeRunnable = new Runnable() {
    @Override
    public void run() {
    mGlowing = false;
    mAnimationTarget.animate().setDuration(mOutDuration).alpha(1);
    }
  };

  public ScrollTriggeredAnimator(AbsListView absListView, View animationTarget) {
    super();

    mAnimationTarget = animationTarget;

    createAnimations();
    absListView.setOnScrollListener(setupListener());
  }

  private void createAnimations() {
    int scrollBarPanelFadeDuration = 500; // ViewConfiguration.getScrollBarFadeDuration();

    mInDuration = scrollBarPanelFadeDuration / 2;
    mOutDuration = scrollBarPanelFadeDuration * 2;
  }

  private AbsListView.OnScrollListener setupListener() {
    // listen for scroll, animate glow effect
    AbsListView.OnScrollListener listener = new AbsListView.OnScrollListener() {

      @Override
      public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
          case SCROLL_STATE_IDLE:
            if (mGlowing) {
              mHandler.removeCallbacks(mBackgroundDimmerFadeRunnable);
              mHandler.postAtTime(mBackgroundDimmerFadeRunnable, AnimationUtils.currentAnimationTimeMillis());
            }
            break;
          case SCROLL_STATE_TOUCH_SCROLL:
            // kill any animation waiting to happen
            mHandler.removeCallbacks(mBackgroundDimmerFadeRunnable);

            // make it glow bright
            if (!mGlowing) {
              mGlowing = true;
              mAnimationTarget.animate().setDuration(mInDuration).alpha(0);
            }

            break;
          case SCROLL_STATE_FLING:
            break;
          default:
            break;
        }
      }

      @Override
      public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                           int totalItemCount) {
      }
    };

    return listener;
  }
}


