package ccx.gamestudio.masterminigolf.GameObjects;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import java.util.ArrayList;
import java.util.List;

import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class GamePlayers {
    private static final GamePlayers INSTANCE = new GamePlayers();
    private static final TextureOptions mTransparentTextureOption = TextureOptions.BILINEAR;
    private static final float mSELECTABLEPLAYERS = 2;
    public static TextureRegion mSensorShoot;
    public static TextureRegion mTrailingDotTR;
    public static TextureRegion mBtnUp;
    public static TextureRegion mBtnDown;
    public static TextureRegion mBtnShoot;
    public static TextureRegion mArrowShoot;
    public static TextureRegion mPowerBarBackground;
    public static TextureRegion mPowerBarLine;
    public static TiledTextureRegion mAnimPlayers;
    public static List<TiledTextureRegion> mLisPlayer;
    public GamePlayers(){

    }

    public static GamePlayers getInstance(){
        return INSTANCE;
    }

    public void Initialize()
    {
        LoadPlayers();
        LoadHud();
    }

    public void LoadPlayers(){
        String mPreviousAssetBasePathPlayers = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/Players/");

        if (mSensorShoot == null) mSensorShoot = ResourceManager.getInstance().getLimitableTR("sensorshoot.png", mTransparentTextureOption);
        if (mTrailingDotTR == null) mTrailingDotTR = ResourceManager.getInstance().getLimitableTR("TrailDot.png", mTransparentTextureOption);


        mLisPlayer = new ArrayList<>();
        for (int i = 0; i < mSELECTABLEPLAYERS; i++) {
            mAnimPlayers = ResourceManager.getInstance().getLimitableTTR("Anim" + i + ".png", 4, 1, mTransparentTextureOption);
            mLisPlayer.add(mAnimPlayers);
        }

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePathPlayers);

    }

    public void LoadHud(){
        String mPreviousAssetBasePathHud = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/Hud/");

        if (mPowerBarBackground == null) mPowerBarBackground = ResourceManager.getInstance().getLimitableTR("PowerBarBG.png", mTransparentTextureOption);
        if (mPowerBarLine == null) mPowerBarLine = ResourceManager.getInstance().getLimitableTR("PowerBarLine.png", mTransparentTextureOption);
        if (mBtnUp == null) mBtnUp = ResourceManager.getInstance().getLimitableTR("buttonup.png", mTransparentTextureOption);
        if (mBtnDown == null) mBtnDown = ResourceManager.getInstance().getLimitableTR("buttondown.png", mTransparentTextureOption);
        if (mBtnShoot == null) mBtnShoot = ResourceManager.getInstance().getLimitableTR("buttonshoot.png", mTransparentTextureOption);
        if (mArrowShoot == null) mArrowShoot = ResourceManager.getInstance().getLimitableTR("ArrowShoot.png", mTransparentTextureOption);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePathHud);
    }
}
