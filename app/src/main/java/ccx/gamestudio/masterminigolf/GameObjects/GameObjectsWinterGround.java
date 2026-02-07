package ccx.gamestudio.masterminigolf.GameObjects;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class GameObjectsWinterGround {
    private static final GameObjectsWinterGround INSTANCE = new GameObjectsWinterGround();
    private static final TextureOptions mTransparentTextureOption = TextureOptions.BILINEAR;
    public static TextureRegion crateGreen;
    public static TextureRegion mushroom;
    public static TextureRegion mushroomOne;
    public static TextureRegion rocks;
    public static TextureRegion rocksOne;
    public static TextureRegion sign;
    public static TextureRegion signOne;
    public static TextureRegion tree;
    public static TextureRegion treeOne;
    public static TextureRegion treeTwo;
    public static TextureRegion mGround01;
    public static TextureRegion mGround02;
    public static TextureRegion mGroundGreenWinter;

    public GameObjectsWinterGround(){

    }

    public static GameObjectsWinterGround getInstance(){
        return INSTANCE;
    }

    public void LoadObjects()
    {
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/Objects/WinterGround/");

        if(crateGreen==null) crateGreen = ResourceManager.getInstance().getLimitableTR("Crate.png", mTransparentTextureOption);
        if(mushroom==null) mushroom = ResourceManager.getInstance().getLimitableTR("Crystal.png", mTransparentTextureOption);
        if(mushroomOne==null) mushroomOne = ResourceManager.getInstance().getLimitableTR("IceBox.png", mTransparentTextureOption);
        if(rocks==null) rocks = ResourceManager.getInstance().getLimitableTR("Igloo.png", mTransparentTextureOption);
        if(rocksOne==null) rocksOne = ResourceManager.getInstance().getLimitableTR("Stone.png", mTransparentTextureOption);
        if(sign==null) sign = ResourceManager.getInstance().getLimitableTR("Sign.png", mTransparentTextureOption);
        if(signOne==null) signOne = ResourceManager.getInstance().getLimitableTR("SignOne.png", mTransparentTextureOption);
        if(tree==null) tree = ResourceManager.getInstance().getLimitableTR("SnowMan.png", mTransparentTextureOption);
        if(treeOne==null) treeOne = ResourceManager.getInstance().getLimitableTR("TreeOne.png", mTransparentTextureOption);
        if(treeTwo==null) treeTwo = ResourceManager.getInstance().getLimitableTR("TreeTwo.png", mTransparentTextureOption);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);

        LoadPlatformsGreenGround();
        LoadGreenGround();
    }

    private void LoadPlatformsGreenGround(){
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/Tiles/Winter/");

        if(mGround01==null) mGround01 = ResourceManager.getInstance().getLimitableTR("ground01.png", mTransparentTextureOption);
        if(mGround02==null) mGround02 = ResourceManager.getInstance().getLimitableTR("ground02.png", mTransparentTextureOption);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
    }

    private void LoadGreenGround(){
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/Green/");

        if(mGroundGreenWinter==null) mGroundGreenWinter = ResourceManager.getInstance().getLimitableTR("GreenWinter.png", mTransparentTextureOption);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
    }
}
