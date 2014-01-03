package com.sickboots.sickvideos.misc;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.sickboots.sickvideos.Content;
import com.sickboots.sickvideos.R;
import com.sickboots.sickvideos.youtube.YouTubeAPI;

import java.util.Map;

public class ChannelAboutFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_channel_about, container, false);

    askYouTubeForAboutInfo();

    Utils.setActionBarTitle(getActivity(), "About");

    return rootView;
  }

  private void updateUI(View rootView, Map info) {
    TextView title = (TextView) rootView.findViewById(R.id.text_view);
    TextView description = (TextView) rootView.findViewById(R.id.description_view);
    final ImageView image = (ImageView) rootView.findViewById(R.id.image);

    title.setText((String) info.get("title"));
    description.setText((String) info.get("description"));


    int defaultImageResID = 0;

    UrlImageViewHelper.setUrlDrawable(image, (String) info.get("thumbnail"), defaultImageResID, new UrlImageViewCallback() {

      @Override
      public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
        if (!loadedFromCache) {
          image.setAlpha(.5f);
          image.animate().setDuration(200).alpha(1);
        } else
          image.setAlpha(1f);
      }

    });

  }

  private void askYouTubeForAboutInfo() {
    (new Thread(new Runnable() {
      public void run() {

        YouTubeAPI helper = new YouTubeAPI(getActivity(), new YouTubeAPI.YouTubeAPIListener() {
          @Override
          public void handleAuthIntent(final Intent authIntent) {
            Debug.log("handleAuthIntent inside update Service.  not handled here");
          }
        });

        final Map result = helper.channelInfo(Content.channelID());

        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
          @Override
          public void run() {
            updateUI(getView(), result);
          }
        });

      }
    })).start();
  }

}