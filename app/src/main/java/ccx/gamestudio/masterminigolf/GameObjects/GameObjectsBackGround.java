package ccx.gamestudio.masterminigolf.GameObjects;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class GameObjectsBackGround {
    private static final GameObjectsBackGround INSTANCE = new GameObjectsBackGround();
    private static final TextureOptions mTransparentTextureOption = TextureOptions.BILINEAR;
    public static TextureRegion greenBackground;
    public static TextureRegion desertBackground;
    public static TextureRegion nightBackground;
    public static TextureRegion winterBackground;
    public static TiledTextureRegion gameWaterTTR;
    public static TiledTextureRegion gameWaterSplashTTR;
    public static TextureRegion gameWaterBackgroundTR;
    public GameObjectsBackGround(){

    }

    public static GameObjectsBackGround getInstance(){
        return INSTANCE;
    }

    public void LoadObjects()
    {
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/BackGround/");

        if(greenBackground==null) greenBackground = ResourceManager.getInstance().getLimitableTR("BGGreen.png", mTransparentTextureOption);
        if(desertBackground==null) desertBackground = ResourceManager.getInstance().getLimitableTR("BGDesert.png", mTransparentTextureOption);
        if(winterBackground==null) winterBackground = ResourceManager.getInstance().getLimitableTR("BGWinter.png", mTransparentTextureOption);
        if(nightBackground==null) nightBackground = ResourceManager.getInstance().getLimitableTR("BGNight.png", mTransparentTextureOption);

        //water
        if(gameWaterBackgroundTR==null) gameWaterBackgroundTR = ResourceManager.getInstance().getLimitableTR("water.png", mTransparentTextureOption);
        if(gameWaterTTR==null) gameWaterTTR = ResourceManager.getInstance().getLimitableTTR("wateranimation.png", 4, 5, mTransparentTextureOption);
        if(gameWaterSplashTTR==null) gameWaterSplashTTR = ResourceManager.getInstance().getLimitableTTR("watersplash.png", 5, 6, mTransparentTextureOption);
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
    }
}
