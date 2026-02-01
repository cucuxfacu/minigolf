package ccx.gamestudio.masterminigolf.GameObjects;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class GameObjectsGreenGround  {
    private static final GameObjectsGreenGround INSTANCE = new GameObjectsGreenGround();
    private static final TextureOptions mTransparentTextureOption = TextureOptions.BILINEAR;
    public static TextureRegion bushGreen;
    public static TextureRegion bushGreenOne;
    public static TextureRegion bushGreenTwo;
    public static TextureRegion bushGreenThree;
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

    public GameObjectsGreenGround(){

    }

    public static GameObjectsGreenGround getInstance(){
        return INSTANCE;
    }

    public void LoadObjects()
    {
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/Objects/GreenGround/");

        if(bushGreen==null) bushGreen = ResourceManager.getInstance().getLimitableTR("Bush.png", mTransparentTextureOption);
        if(bushGreenOne==null) bushGreenOne = ResourceManager.getInstance().getLimitableTR("BushOne.png", mTransparentTextureOption);
        if(bushGreenTwo==null) bushGreenTwo = ResourceManager.getInstance().getLimitableTR("BushTwo.png", mTransparentTextureOption);
        if(bushGreenThree==null) bushGreenThree = ResourceManager.getInstance().getLimitableTR("BushThree.png", mTransparentTextureOption);
        if(crateGreen==null) crateGreen = ResourceManager.getInstance().getLimitableTR("Crate.png", mTransparentTextureOption);
        if(mushroom==null) mushroom = ResourceManager.getInstance().getLimitableTR("Mushroom.png", mTransparentTextureOption);
        if(mushroomOne==null) mushroomOne = ResourceManager.getInstance().getLimitableTR("MushroomOne.png", mTransparentTextureOption);
        if(rocks==null) rocks = ResourceManager.getInstance().getLimitableTR("Rocks.png", mTransparentTextureOption);
        if(rocksOne==null) rocksOne = ResourceManager.getInstance().getLimitableTR("RocksOne.png", mTransparentTextureOption);
        if(sign==null) sign = ResourceManager.getInstance().getLimitableTR("Sign.png", mTransparentTextureOption);
        if(signOne==null) signOne = ResourceManager.getInstance().getLimitableTR("SignOne.png", mTransparentTextureOption);
        if(tree==null) tree = ResourceManager.getInstance().getLimitableTR("Tree.png", mTransparentTextureOption);
        if(treeOne==null) treeOne = ResourceManager.getInstance().getLimitableTR("TreeOne.png", mTransparentTextureOption);
        if(treeTwo==null) treeTwo = ResourceManager.getInstance().getLimitableTR("TreeTwo.png", mTransparentTextureOption);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);

        LoadPlatformsGreenGround();
    }

    private void LoadPlatformsGreenGround(){
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/Tiles/GreenGround/");

        if(mGround01==null) mGround01 = ResourceManager.getInstance().getLimitableTR("ground01.png", mTransparentTextureOption);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
    }
}
