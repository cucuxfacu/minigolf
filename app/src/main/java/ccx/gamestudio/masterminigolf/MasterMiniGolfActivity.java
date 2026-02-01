package ccx.gamestudio.masterminigolf;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.andengine.engine.Engine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

import ccx.gamestudio.masterminigolf.Helpers.SharedResources;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.Manager.SFXManager;
import ccx.gamestudio.masterminigolf.Manager.SceneManager;
import ccx.gamestudio.masterminigolf.Menus.MainMenu;
import ccx.gamestudio.masterminigolf.Menus.SplashScreens;

public class MasterMiniGolfActivity extends BaseGameActivity {

    static float MAX_WIDTH_PIXELS = 2400, MAX_HEIGHT_PIXELS = 1080;
    static float MIN_WIDTH_PIXELS = 1920, MIN_HEIGHT_PIXELS = 1080;
    static float DESIGN_WINDOW_WIDTH_PIXELS = 2400;
    static float DESIGN_WINDOW_HEIGHT_PIXELS = 1080;
    static float DESIGN_WINDOW_WIDTH_INCHES = 5.0813646f;
    static float DESIGN_WINDOW_HEIGHT_INCHES = 2.5511832f;

    // ====================================================
    // VARIABLES
    // ====================================================
    public float cameraWidth;
    public float cameraHeight;
    public float actualWindowWidthInches;
    public float actualWindowHeightInches;
    public MasterMiniGolfSmoothCamera mCamera;
    public static int numTimesActivityOpened;

    @Override
    public Engine onCreateEngine(final EngineOptions pEngineOptions) {
        return new SwitchableFixedStepEngine(pEngineOptions, 120, false);
    }
    @Override
    public EngineOptions onCreateEngineOptions() {
        FillResolutionPolicy EngineFillResolutionPolicy = new FillResolutionPolicy() {
            @Override
            public void onMeasure(final IResolutionPolicy.Callback pResolutionPolicyCallback,
                                  final int pWidthMeasureSpec, final int pHeightMeasureSpec) {
                super.onMeasure(pResolutionPolicyCallback, pWidthMeasureSpec, pHeightMeasureSpec);

                final int measuredWidth = View.MeasureSpec.getSize(pWidthMeasureSpec);
                final int measuredHeight = View.MeasureSpec.getSize(pHeightMeasureSpec);

                // Uncomment the following lines to log the pixel values needed
                // for setting up the design-window's values
                //Log.v("Andengine","Design window width & height (in pixels): " + measuredWidth + ", " + measuredHeight);
                //Log.v("Andengine","Design window width & height (in inches):	" + measuredWidth / getResources().getDisplayMetrics().xdpi + ", " + measuredHeight / getResources().getDisplayMetrics().ydpi);

                // Determine the device's physical window size.
                actualWindowWidthInches = measuredWidth / getResources().getDisplayMetrics().xdpi;
                actualWindowHeightInches = measuredHeight / getResources().getDisplayMetrics().ydpi;
                //Log.v("Andengine","Design window width & height (actual): "+actualWindowWidthInches + "," +actualWindowHeightInches);

                // Get an initial width for the camera, and bound it to the
                // minimum or maximum values.
                float actualScaledWidthInPixels = DESIGN_WINDOW_WIDTH_PIXELS * (actualWindowWidthInches / DESIGN_WINDOW_WIDTH_INCHES);
                float boundScaledWidthInPixels = Math.round(Math.max(Math.min(actualScaledWidthInPixels, MAX_WIDTH_PIXELS), MIN_WIDTH_PIXELS));

                // Get the height for the camera based on the width and the
                // height/width ratio of the device
                float boundScaledHeightInPixels = boundScaledWidthInPixels
                        * (actualWindowHeightInches / actualWindowWidthInches);
                // If the height is outside of the set bounds, scale the width
                // to match it.
                if (boundScaledHeightInPixels > MAX_HEIGHT_PIXELS) {
                    float boundAdjustmentRatio = MAX_HEIGHT_PIXELS / boundScaledHeightInPixels;
                    boundScaledWidthInPixels *= boundAdjustmentRatio;
                    boundScaledHeightInPixels *= boundAdjustmentRatio;
                } else if (boundScaledHeightInPixels < MIN_HEIGHT_PIXELS) {
                    float boundAdjustmentRatio = MIN_HEIGHT_PIXELS / boundScaledHeightInPixels;
                    boundScaledWidthInPixels *= boundAdjustmentRatio;
                    boundScaledHeightInPixels *= boundAdjustmentRatio;
                }

                cameraHeight = boundScaledHeightInPixels;
                cameraWidth = boundScaledWidthInPixels;
                mCamera.set(0f, 0f, cameraWidth, cameraHeight);

            }
        };

        // Create the Camera and EngineOptions.
        mCamera = new MasterMiniGolfSmoothCamera(0, 0, 2400, 1080, 4000f, 2000f, 0.1f);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, EngineFillResolutionPolicy, mCamera);

        engineOptions.getTouchOptions().setNeedsMultiTouch(true);
        engineOptions.getAudioOptions().setNeedsSound(true);
        engineOptions.getAudioOptions().setNeedsMusic(true);
        engineOptions.getRenderOptions().setDithering(true);
        engineOptions.getRenderOptions().getConfigChooserOptions().setRequestedMultiSampling(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);

        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback onCreateResourcesCallback) throws IOException {
        ResourceManager.setup(this,
                (SwitchableFixedStepEngine) this.getEngine(),
                this.getApplicationContext(),
                cameraWidth,
                cameraHeight,
                cameraWidth / DESIGN_WINDOW_WIDTH_PIXELS,
                cameraHeight / DESIGN_WINDOW_HEIGHT_PIXELS);


        if (SharedResources.getIntFromSharedPreferences(SharedResources.SHARED_PREFS_ACTIVITY_START_COUNT) == 0) {
            SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_PREFS_ACTIVITY_START_COUNT, 1);
        }

        numTimesActivityOpened = SharedResources.getIntFromSharedPreferences(SharedResources.SHARED_PREFS_ACTIVITY_START_COUNT_RATING) + 1;
        SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_PREFS_ACTIVITY_START_COUNT_RATING, numTimesActivityOpened);

        SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_PREFS_ACTIVITY_START_USER, SharedResources.getIntFromSharedPreferences(SharedResources.SHARED_PREFS_ACTIVITY_START_USER) + 1);

        onCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback onCreateSceneCallback) throws IOException {
        ResourceManager.loadMenuResources();
        //SceneManager.getInstance().showScene(new SplashScreens());
        SceneManager.getInstance().showScene(new MainMenu());

        onCreateSceneCallback.onCreateSceneFinished(mEngine.getScene());
        SharedResources.writeFloatToSharedPreferences(SharedResources.SHARED_WINDOWS_WIDTH_INCHES, actualWindowWidthInches);

        //InAppLoginGooglePlayGameServices.getInstance().GoogleSignInitializate();

        SharedResources.ResetCountGif();
    }

    @Override
    public void onPopulateScene(Scene scene, OnPopulateSceneCallback onPopulateSceneCallback) throws IOException {
        onPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    protected void onDestroy() {
        //adView.destroy();
        super.onDestroy();
        System.exit(0);
    }

    protected void onPause() {
       // adView.pause();
        super.onPause();
        /*if (isGameLoaded()) {
            SFXManager.pauseMusic();
            if(!SharedResources.getStringFromSharedPreferences(SharedResources.SHARED_USER_EMAIL).isEmpty() &&
                    !SharedResources.getStringFromSharedPreferences(SharedResources.SHARED_USER_EMAIL).equals("0")) {
                SaveGame.getInstance().SaveUsersData();
            }
        }*/
    }

    protected void onResume() {
        //adView.resume();
        super.onResume();
        System.gc();
       /* if (isGameLoaded()) {
            SaveGame.getInstance().SaveUsersData();
            SFXManager.pauseMusic();
            if (SceneManager.getInstance().mCurrentScene != null) {
                if (SceneManager.getInstance().mCurrentScene.getClass().equals(GameLevel.class)) {
                    ((GameLevel) SceneManager.getInstance().mCurrentScene).GamePause();
                }
            }
        }*/
    }
}

