package ccx.gamestudio.masterminigolf.Manager;


import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import java.util.ArrayList;
import java.util.List;

public class MenuResourceManager {

    private static final MenuResourceManager INSTANCE = new MenuResourceManager();
    private static final TextureOptions mTransparentTextureOption = TextureOptions.BILINEAR;
    private static final String mPathPlayer ="MasterGolf/GUI/Players/";
    private static final String mPathBalls ="MasterGolf/Balls/";
    private static final String mPathHeads ="MasterGolf/GUI/Heads/";

    //====================================================
    // CONSTRUCTOR
    //====================================================
    public MenuResourceManager(){
    }

    //====================================================
    // GETTERS & SETTERS
    //====================================================
    public static MenuResourceManager getInstance(){
        return INSTANCE;
    }
    // ======================== Menu Resources TextureRegion================= //
    public static TextureRegion menuBackground;
    public static TextureRegion backgroundOptions;
    public static TextureRegion titleGame;
    public static TextureRegion btnGeneric;
    public static TextureRegion btnUser;
    public static TextureRegion btnMenu;
    public static TextureRegion btnPause;
    public static TextureRegion btnGenericSmall;
    public static TextureRegion btnExit;
    public static TextureRegion layerGeneric;
    public static TextureRegion layerFailed;
    public static TextureRegion bntExitInLayer;
    public static TextureRegion bntAcceptInLayer;
    public static TextureRegion btnTrophy;
    public static TextureRegion btnReturnHome;
    public static TextureRegion btnReturn;
    public static TextureRegion btnPlayGame;
    public static TextureRegion btnInformacion;
    public static TextureRegion btnBalls;
    public static TextureRegion arrow;


    // ======================== Menu Resources TiledTextureRegion================= //
    public static TiledTextureRegion btnMusicTTR;
    public static TiledTextureRegion btnSoundTTR;
    public static TiledTextureRegion btnGenericTTR;
    // ======================== END ================= //

    // ======================== Players================= //
    public static List<TextureRegion> mPlayers;
    public static List<TextureRegion> mListBall;
    public static List<TextureRegion> mListHeads;
    public static List<TextureRegion> mListScene;
    // ======================== END ================= //

    public void LoadImgMenu() {
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/GUI/");

        if (menuBackground == null)menuBackground = ResourceManager.getInstance().getLimitableTR("menuBackground.png", mTransparentTextureOption);
        if (backgroundOptions == null)backgroundOptions = ResourceManager.getInstance().getLimitableTR("options.png", mTransparentTextureOption);
        if (titleGame == null)titleGame = ResourceManager.getInstance().getLimitableTR("titleGame.png", mTransparentTextureOption);
        if (btnGeneric == null) btnGeneric = ResourceManager.getInstance().getLimitableTR("buttongeneric.png", mTransparentTextureOption);
        if (btnGenericTTR == null) btnGenericTTR = ResourceManager.getInstance().getLimitableTTR("buttongenericTTR.png",2,1, mTransparentTextureOption);
        if (btnUser == null)btnUser = ResourceManager.getInstance().getLimitableTR("buttonuser.png", mTransparentTextureOption);
        if (btnMenu == null) btnMenu = ResourceManager.getInstance().getLimitableTR("menu.png", mTransparentTextureOption);
        if (btnPause == null) btnPause = ResourceManager.getInstance().getLimitableTR("pause.png", mTransparentTextureOption);
        if (btnMusicTTR == null)btnMusicTTR = ResourceManager.getInstance().getLimitableTTR("music.png", 2, 1, mTransparentTextureOption);
        if (btnSoundTTR == null)btnSoundTTR = ResourceManager.getInstance().getLimitableTTR("sound.png", 2, 1, mTransparentTextureOption);
        if (btnGenericSmall == null) btnGenericSmall = ResourceManager.getInstance().getLimitableTR("buttongenericsmall.png", mTransparentTextureOption);
        if (btnExit == null) btnExit = ResourceManager.getInstance().getLimitableTR("buttonexit.png", mTransparentTextureOption);
        if (btnTrophy == null) btnTrophy = ResourceManager.getInstance().getLimitableTR("buttontrofeo.png", mTransparentTextureOption);
        if (btnReturnHome == null) btnReturnHome = ResourceManager.getInstance().getLimitableTR("returnhome.png", mTransparentTextureOption);
        if (btnReturn == null) btnReturn = ResourceManager.getInstance().getLimitableTR("return.png", mTransparentTextureOption);
        if (btnPlayGame == null) btnPlayGame = ResourceManager.getInstance().getLimitableTR("playgame.png", mTransparentTextureOption);
        if (btnInformacion == null) btnInformacion = ResourceManager.getInstance().getLimitableTR("informacion.png", mTransparentTextureOption);
        if (arrow == null) arrow = ResourceManager.getInstance().getLimitableTR("Arrow.png", mTransparentTextureOption);

        LayerExit();
        LayerFailed();
        LoadHeadsMenu();
        LoadSelectablePlayers();
        LoadSelectableBalls();

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
    }

    public void LoadHeadsMenu(){
        mListHeads = AssetTextureLoader.loadTexturesFromFolder(
                mPathHeads,
                98,
                98,
                mTransparentTextureOption);
    }


    public void LoadSelectablePlayers(){
        mPlayers =         AssetTextureLoader.loadTexturesFromFolder(
                mPathPlayer,
                140,
                265,
                mTransparentTextureOption
        );


    }


    public void LoadSelectableBalls() {
        mListBall = AssetTextureLoader.loadTexturesFromFolder(
                mPathBalls,
                98,
                98,
                mTransparentTextureOption
        );
    }


    private void LayerExit() {
        if (layerGeneric == null)
            layerGeneric = ResourceManager.getInstance().getLimitableTR("LayerGeneric.png", mTransparentTextureOption);
        if (bntExitInLayer == null)
            bntExitInLayer = ResourceManager.getInstance().getLimitableTR("buttonexitinlayer.png", mTransparentTextureOption);
        if (bntAcceptInLayer == null)
            bntAcceptInLayer = ResourceManager.getInstance().getLimitableTR("buttonacceptinlayer.png", mTransparentTextureOption);
    }

    private void LayerFailed() {
        if (layerFailed == null)
            layerFailed = ResourceManager.getInstance().getLimitableTR("LayerFailed.png", mTransparentTextureOption);

    }
}
