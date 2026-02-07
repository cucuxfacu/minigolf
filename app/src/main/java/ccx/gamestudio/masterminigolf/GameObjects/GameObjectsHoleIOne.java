package ccx.gamestudio.masterminigolf.GameObjects;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class GameObjectsHoleIOne {
    private static final GameObjectsHoleIOne INSTANCE = new GameObjectsHoleIOne();
    private static final TextureOptions mTransparentTextureOption = TextureOptions.BILINEAR;
    public static TextureRegion mHoleInOne;
    public static TextureRegion mHoleInOneDesert;
    public static TextureRegion mHoleInOneNight;
    public static TextureRegion mHoleInOneWinter;


    public GameObjectsHoleIOne(){

    }

    public static GameObjectsHoleIOne getInstance(){
        return INSTANCE;
    }

    public void LoadObjects()
    {
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/HoleInOne/");

        if(mHoleInOne==null) mHoleInOne = ResourceManager.getInstance().getLimitableTR("HoleInOne.png", mTransparentTextureOption);
        if(mHoleInOneDesert==null) mHoleInOneDesert = ResourceManager.getInstance().getLimitableTR("HoleInOneDesert.png", mTransparentTextureOption);
        if(mHoleInOneNight==null) mHoleInOneDesert = ResourceManager.getInstance().getLimitableTR("HoleInOneNight.png", mTransparentTextureOption);
        if(mHoleInOneWinter==null) mHoleInOneDesert = ResourceManager.getInstance().getLimitableTR("HoleInOneWinter.png", mTransparentTextureOption);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);

    }
}
