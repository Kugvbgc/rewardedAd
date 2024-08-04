package com.khair.rewardedad;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


public class MainActivity extends AppCompatActivity {
    private RewardedAd rewardedAd;
    private final String TAG = "MainActivity";
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);

        MobileAds.initialize(this, initializationStatus -> {});
        lodRewardedAd();
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ShowRewardedAd();
           }
       });


    }
    //==============================================================================================
  public void lodRewardedAd() {
      AdRequest adRequest = new AdRequest.Builder().build();
      RewardedAd.load(MainActivity.this, "ca-app-pub-3940256099942544/5224354917",
              adRequest, new RewardedAdLoadCallback() {
                  @Override
                  public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                      // Handle the error.
                      Log.d(TAG, loadAdError.toString());
                      rewardedAd = null;
                      rewardedAdCallback();
                  }

                  @Override
                  public void onAdLoaded(@NonNull RewardedAd ad) {
                      rewardedAd = ad;
                      Log.d(TAG, "Ad was loaded.");
                  }
              });
  }
/////////////////////////////////////////////////////////////////////////////////////////////////////
public void rewardedAdCallback(){
    rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
        @Override
        public void onAdClicked() {
            // Called when a click is recorded for an ad.
            Log.d(TAG, "Ad was clicked.");
        }

        @Override
        public void onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            // Set the ad reference to null so you don't show the ad a second time.
            Log.d(TAG, "Ad dismissed fullscreen content.");
            rewardedAd = null;
        }

        @Override
        public void onAdFailedToShowFullScreenContent(AdError adError) {
            // Called when ad fails to show.
            Log.e(TAG, "Ad failed to show fullscreen content.");
            rewardedAd = null;
        }

        @Override
        public void onAdImpression() {
            // Called when an impression is recorded for an ad.
            Log.d(TAG, "Ad recorded an impression.");
        }

        @Override
        public void onAdShowedFullScreenContent() {
            // Called when ad is shown.
            Log.d(TAG, "Ad showed fullscreen content.");
        }
    });
}
//*****************************************************************************

    public void ShowRewardedAd(){
        if (rewardedAd != null) {
            Activity activityContext = MainActivity.this;
            rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d(TAG, "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                }
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
        }
    }
}