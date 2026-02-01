package ccx.gamestudio.masterminigolf.GameObjects;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class GameObjectsBackGround {
    private static final GameObjectsBackGround INSTANCE = new GameObjectsBackGround();
    private static final TextureOptions mTransparentTextureOption = TextureOptions.BILINEAR;
    public static TextureRegion greenBackground;
    public static TextureRegion desertBackground;

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

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
    }
}
