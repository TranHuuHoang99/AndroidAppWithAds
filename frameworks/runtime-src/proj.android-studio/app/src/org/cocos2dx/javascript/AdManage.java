package org.cocos2dx.javascript;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AdManage  implements RewardedVideoAdListener  {
    private static final String AD_BANNER_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"; // Banner ID
    private static final String AD_VIDEO_ID = "ca-app-pub-3940256099942544/8691691433"; // fixed watch ads video
    private static final String APP_ID = "ca-app-pub-1188693098022177~9045450690"; // app ID fixed

    private Context mainActive = null;
    private static AdManage mInstace = null;

    private AdView mAdView;
    private LinearLayout bannerLayout;

    private RewardedVideoAd rewardedVideoAd;

    private static boolean isVideoRewarded = false;
    private static boolean isVideoClose = false;

    public static AdManage getInstance() {
        if (null == mInstace) {
            mInstace = new AdManage();
        }
        return mInstace;
    }

    public void init(Context context) {
        this.mainActive = context;

        // Initialize the advertising SDK.
        MobileAds.initialize(context, APP_ID);

        // Preload rewarded video ads
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        // Preload banner ads
        loadBannerAd();
    }

    /*
       Load google banner ads
     */
    public void loadBannerAd() {
        bannerLayout = new LinearLayout(this.mainActive);
        bannerLayout.setOrientation(LinearLayout.VERTICAL);

        // Create a banner ad. The ad size and ad unit ID must be set before calling loadAd.
        mAdView = new AdView(this.mainActive);
        mAdView.setAdSize(AdSize.SMART_BANNER);
        mAdView.setAdUnitId(AD_BANNER_UNIT_ID);

        // Create an ad request.
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

        // Optionally populate the ad request builder.
        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);

        // Add the AdView to the view hierarchy.
        bannerLayout.addView(mAdView);

        // Start loading the ad.
        mAdView.loadAd(adRequestBuilder.build());

        AppActivity activity = (AppActivity)this.mainActive;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity=Gravity.TOP; // hoangprodn fixed
        activity.addContentView(bannerLayout,params);
        bannerLayout.setVisibility(View.INVISIBLE);

    }

    /*
       Show google banner ad
     */
    public static void showBannerAd(){
        AppActivity mActivity = (AppActivity)AdManage.getInstance().mainActive;
        // Make sure to operate on the UI thread
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdManage.getInstance().bannerLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    /*
       Hide google banner ads
     */
    public static void hideBannerAd(){
        AppActivity mActivity = (AppActivity)AdManage.getInstance().mainActive;
        // Make sure to operate on the UI thread
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdManage.getInstance().bannerLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    /*
    Preload google video ads
     */
    public void loadRewardedVideoAd() {
        if (!rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.loadAd(AD_VIDEO_ID, new AdRequest.Builder().build());
        }
    }

    /*
    Show video ads
     */
    public static void showRewardedVideo() {
        AdManage.getInstance().isVideoRewarded = false;
        AdManage.getInstance().isVideoClose = false;

        AppActivity mActivity = (AppActivity)AdManage.getInstance().mainActive;
        // Make sure to operate on the UI thread
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (AdManage.getInstance().rewardedVideoAd.isLoaded()) {
                    AdManage.getInstance().rewardedVideoAd.show();
                }
            }
        });
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(this.mainActive, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        AdManage.getInstance().isVideoClose = true;
        // Preload the next video ad
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
    }

    @Override
    public void onRewardedVideoAdLoaded() {
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewarded(RewardItem reward) {
        AdManage.getInstance().isVideoRewarded = true;
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    @Override
    public void onRewardedVideoCompleted() {
    }

    // Used for cocos to monitor the completion of video
    // advertisement playback
    public static boolean videoRewardedListener(){
        return AdManage.getInstance().isVideoRewarded;
    }

    // Used for cocos monitoring video ad playback to close
    public static boolean videoCloseListener(){
        return AdManage.getInstance().isVideoClose;
    }

    public void onResume() {
        mAdView.resume();
        rewardedVideoAd.resume(this.mainActive);
    }

    public void onPause() {
        mAdView.pause();
        rewardedVideoAd.pause(this.mainActive);
    }

    public void onDestroy() {
        mAdView.destroy();
        rewardedVideoAd.destroy(this.mainActive);
    }
}