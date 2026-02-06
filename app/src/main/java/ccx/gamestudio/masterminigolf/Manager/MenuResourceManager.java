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
    public static TextureRegion bntExitInLayer;
    public static TextureRegion bntAcceptInLayer;
    public static TextureRegion btnTrophy;
    public static TextureRegion btnReturnHome;
    public static TextureRegion btnReturn;
    public static TextureRegion btnPlayGame;
    public static TextureRegion btnInformacion;
    public static TextureRegion btnBalls;
    public static TextureRegion btnHeads;
    public static TextureRegion arrow;


    // ======================== Menu Resources TiledTextureRegion================= //
    public static TiledTextureRegion btnMusicTTR;
    public static TiledTextureRegion btnSoundTTR;
    public static TiledTextureRegion btnGenericTTR;
    // ======================== END ================= //

    // ======================== Players================= //
    public static List<TextureRegion> mPlayers;
    public static List<TextureRegion> mListBall;
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
        LoadBallsMenu();
        LoadHeadsMenu();
        LoadSelectablePlayers();
        LoadSelectableBalls();

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
    }

    public void LoadHeadsMenu(){
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/GUI/Heads/");

        if (btnHeads == null) btnHeads = ResourceManager.getInstance().getLimitableTR("headmam.png", mTransparentTextureOption);
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);

    }

    public void LoadBallsMenu(){
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/GUI/Balls/");
        if (btnBalls == null) btnBalls = ResourceManager.getInstance().getLimitableTR("ball.png", mTransparentTextureOption);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
    }

    public void LoadSelectablePlayers(){
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/GUI/Players/");
        int mSelectablePlayers = 2;
        TextureRegion mPlayeresTextureRegion;

        mPlayers = new ArrayList<>();
        for (int i = 0; i < mSelectablePlayers; i++) {
            BitmapTextureAtlas mPlayersTextureAtlas = new BitmapTextureAtlas(ResourceManager.getActivity().getTextureManager(), 140, 265, mTransparentTextureOption);
            mPlayeresTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mPlayersTextureAtlas, ResourceManager.getActivity(), "player" + i + ".png", 0, 0);
            mPlayersTextureAtlas.load();
            mPlayers.add(mPlayeresTextureRegion);
        };

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
    }
    public void LoadSelectableBalls(){
        String mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("MasterGolf/Balls/");
        int mBallsPlayers = 1;
        TextureRegion mBallsTextureRegion;

        ///Cargamos todos los tanques
        mListBall = new ArrayList<>();
        for (int i = 0; i < mBallsPlayers; i++) {
            BitmapTextureAtlas mBallsTextureAtlas = new BitmapTextureAtlas(ResourceManager.getActivity().getTextureManager(), 256, 256, mTransparentTextureOption);
            mBallsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBallsTextureAtlas, ResourceManager.getActivity(), "ball" + i + ".png", 0, 0);
            mBallsTextureAtlas.load();
            mListBall.add(mBallsTextureRegion);
        };

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
    }


    private void LayerExit() {
        if (layerGeneric == null)
            layerGeneric = ResourceManager.getInstance().getLimitableTR("LayerGeneric.png", mTransparentTextureOption);
        if (bntExitInLayer == null)
            bntExitInLayer = ResourceManager.getInstance().getLimitableTR("buttonexitinlayer.png", mTransparentTextureOption);
        if (bntAcceptInLayer == null)
            bntAcceptInLayer = ResourceManager.getInstance().getLimitableTR("buttonacceptinlayer.png", mTransparentTextureOption);
    }
}
