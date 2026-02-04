package ccx.gamestudio.masterminigolf.GameObjects;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class GameObjectsHoleIOne {
    private static final GameObjectsHoleIOne INSTANCE = new GameObjectsHoleIOne();
    private static final TextureOptions mTransparentTextureOption = TextureOptions.BILINEAR;
    public static TextureRegion mHoleInOne;


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


        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);


    }

}
