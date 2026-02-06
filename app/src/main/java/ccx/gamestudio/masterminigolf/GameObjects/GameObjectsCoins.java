package ccx.gamestudio.masterminigolf.GameObjects;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class GameObjectsCoins {
    private static final GameObjectsCoins INSTANCE = new GameObjectsCoins();
    private static final TextureOptions mTransparentTextureOption = TextureOptions.BILINEAR;

    public static TiledTextureRegion coinAnimTTR;
    public GameObjectsCoins(){

    }

    public static GameObjectsCoins getInstance(){
        return INSTANCE;
    }

    public void LoadObjectsCoins()
    {
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/Objects/Coins/");

        if(coinAnimTTR==null) coinAnimTTR = ResourceManager.getInstance().getLimitableTTR("CoinsInLevel.png",1,10, mTransparentTextureOption);


        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
    }
}
