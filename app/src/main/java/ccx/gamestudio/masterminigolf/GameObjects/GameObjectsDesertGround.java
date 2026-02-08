package ccx.gamestudio.masterminigolf.GameObjects;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class GameObjectsDesertGround {
    private static final GameObjectsDesertGround INSTANCE = new GameObjectsDesertGround();
    private static final TextureOptions mTransparentTextureOption = TextureOptions.BILINEAR;
    private String mPreviousAssetBasePath ="";
    private TextureRegion bush;
    private TextureRegion bushOne;
    private TextureRegion cactus;
    private TextureRegion cactusOne;
    private TextureRegion cactusTwo;
    private TextureRegion crate;
    private TextureRegion grass;
    private TextureRegion grassOne;
    private TextureRegion sign;
    private TextureRegion signArrow;
    private TextureRegion skeleton;
    private TextureRegion stone;
    private TextureRegion stoneBlock;
    private TextureRegion tree;
    public static TextureRegion mGround01;
    public static TextureRegion mGround02;
    public static TextureRegion mGreenDesert;

    public GameObjectsDesertGround(){

    }

    public static GameObjectsDesertGround getInstance(){
        return INSTANCE;
    }

    public void LoadObjects()
    {
        mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/Objects/DesertGround/");

        if(bush==null) bush = ResourceManager.getInstance().getLimitableTR("Bush.png", mTransparentTextureOption);
        if(bushOne==null) bushOne = ResourceManager.getInstance().getLimitableTR("BushOne.png", mTransparentTextureOption);
        if(cactus==null) cactus = ResourceManager.getInstance().getLimitableTR("Cactus.png", mTransparentTextureOption);
        if(cactusOne==null) cactusOne = ResourceManager.getInstance().getLimitableTR("CactusOne.png", mTransparentTextureOption);
        if(cactusTwo==null) cactusTwo = ResourceManager.getInstance().getLimitableTR("CactusTwo.png", mTransparentTextureOption);
        if(crate==null) crate = ResourceManager.getInstance().getLimitableTR("Crate.png", mTransparentTextureOption);
        if(grass==null) grass = ResourceManager.getInstance().getLimitableTR("Grass.png", mTransparentTextureOption);
        if(grassOne==null) grassOne = ResourceManager.getInstance().getLimitableTR("GrassOne.png", mTransparentTextureOption);
        if(sign==null) sign = ResourceManager.getInstance().getLimitableTR("Sign.png", mTransparentTextureOption);
        if(signArrow==null) signArrow = ResourceManager.getInstance().getLimitableTR("SignArrow.png", mTransparentTextureOption);
        if(skeleton==null) skeleton = ResourceManager.getInstance().getLimitableTR("Skeleton.png", mTransparentTextureOption);
        if(stone==null) stone = ResourceManager.getInstance().getLimitableTR("Stone.png", mTransparentTextureOption);
        if(stoneBlock==null) stoneBlock = ResourceManager.getInstance().getLimitableTR("StoneBlock.png", mTransparentTextureOption);
        if(tree==null) tree = ResourceManager.getInstance().getLimitableTR("Tree.png", mTransparentTextureOption);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
        LoadPlatformsDesertGround();
        LoadDesertGround();
    }

    private void LoadPlatformsDesertGround(){
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/Tiles/Desert/");

        if(mGround01==null) mGround01 = ResourceManager.getInstance().getLimitableTR("ground01.png", mTransparentTextureOption);
        if(mGround02==null) mGround02 = ResourceManager.getInstance().getLimitableTR("ground02.png", mTransparentTextureOption);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
    }

    private void LoadDesertGround(){
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/Green/");

        if(mGreenDesert==null) mGreenDesert = ResourceManager.getInstance().getLimitableTR("GreenDesert.png", mTransparentTextureOption);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
    }
}
