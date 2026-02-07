package ccx.gamestudio.masterminigolf.Menus;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import java.util.ArrayList;
import java.util.List;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameLevels.Level;
import ccx.gamestudio.masterminigolf.Helpers.Scroll3DEffect;
import ccx.gamestudio.masterminigolf.Helpers.SharedResources;
import ccx.gamestudio.masterminigolf.Input.GrowButton;
import ccx.gamestudio.masterminigolf.Manager.MenuResourceManager;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.Manager.SceneManager;
import ccx.gamestudio.masterminigolf.MasterMiniGolfSmoothCamera;
import ccx.gamestudio.masterminigolf.R;

public class SelectPlayerMenu extends ManagedMenuScene {

    private static final SelectPlayerMenu INSTANCE = new SelectPlayerMenu();
    public static SelectPlayerMenu getInstance() { return INSTANCE; }

    private static final float mCameraHeight = ResourceManager.getInstance().cameraHeight;
    private static final float mCameraWidth = ResourceManager.getInstance().cameraWidth;
    private static final float mHalfCameraHeight = mCameraHeight / 2f;
    private static final float mHalfCameraWidth = mCameraWidth / 2f;

    private Entity mHomeMenuScreen;
    private Sprite mMiniGolfBGSprite;
    private Sprite mBGSprite;

    // Scroll por botones
    private Entity scrollContainer;
    private List<Float> playerCenters = new ArrayList<>();
    private int selectedIndex = 0;
    private int intChange;
    public SelectPlayerMenu() {
        super(0.001f);
        this.setTouchAreaBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionMoveEnabled(true);
    }

    @Override
    public void onHideScene() {}

    @Override
    public Scene onLoadingScreenLoadAndShown() {
        MasterMiniGolfSmoothCamera.setupForMenus();
        final Scene MenuLoadingScene = new Scene();

        this.mMiniGolfBGSprite = new Sprite(0f, 0f, MenuResourceManager.backgroundOptions,
                ResourceManager.getActivity().getVertexBufferObjectManager());
        this.mMiniGolfBGSprite.setScale(mCameraHeight / MenuResourceManager.backgroundOptions.getHeight());
        this.mMiniGolfBGSprite.setPosition(
                (this.mMiniGolfBGSprite.getWidth() * this.mMiniGolfBGSprite.getScaleX()) / 2f,
                (this.mMiniGolfBGSprite.getHeight() * this.mMiniGolfBGSprite.getScaleY()) / 2f);
        this.mMiniGolfBGSprite.setZIndex(-998);
        this.mMiniGolfBGSprite.setAlpha(0.8f);

        this.mBGSprite = new Sprite(0f, 0f, MenuResourceManager.menuBackground,
                ResourceManager.getActivity().getVertexBufferObjectManager());
        this.mBGSprite.setPosition(
                (this.mBGSprite.getWidth() * this.mBGSprite.getScaleX()) / 2f,
                (this.mBGSprite.getHeight() * this.mBGSprite.getScaleY()) / 2f);
        this.mBGSprite.setZIndex(-999);
        this.mMiniGolfBGSprite.attachChild(mBGSprite);

        MenuLoadingScene.attachChild(this.mMiniGolfBGSprite);

        MenuLoadingScene.attachChild(new Text(
                mHalfCameraWidth,
                mHalfCameraHeight,
                ResourceManager.fontDefault60,
                ResourceManager.getContext().getText(R.string.app_loading),
                ResourceManager.getActivity().getVertexBufferObjectManager()));

        return MenuLoadingScene;
    }

    @Override
    public void onLoadingScreenUnloadAndHidden() {
        this.mMiniGolfBGSprite.detachSelf();
    }

    @Override
    public void onLoadScene() {

        this.mHomeMenuScreen = new Entity(0f, mCameraHeight) {
            boolean hasLoaded = false;
            @Override
            protected void onManagedUpdate(final float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
                if (!this.hasLoaded) {
                    this.hasLoaded = true;
                    this.registerEntityModifier(new MoveModifier(0.20f, 0f, 0, 0f, 0f));
                }
            }
        };

        // ============================================================
        // SCROLL POR BOTONES
        // ============================================================
        scrollContainer = new Entity(0, 0);

        float separation = 150f;
        float xOffset = 0;

        playerCenters.clear();

        for (int i = 0; i < MenuResourceManager.mPlayers.size(); i++) {

            Sprite player = new Sprite(0, 0,   MenuResourceManager.mPlayers.get(i),ResourceManager.getEngine().getVertexBufferObjectManager());

            Text playerName = new Text(player.getWidth() / 2, player.getY() - 15f, ResourceManager.fontDefault36,getPlayerName(i), ResourceManager.getEngine().getVertexBufferObjectManager());
            player.attachChild(playerName);

            float centerX = xOffset + player.getWidth() / 2f;
            player.setPosition(centerX, mHalfCameraHeight);

            scrollContainer.attachChild(player);

            playerCenters.add(centerX);

            xOffset += player.getWidth() + separation;
        }

        selectedIndex = SharedResources.getSelectedPlayer();
        if (selectedIndex < 0 || selectedIndex >= playerCenters.size()) {
            selectedIndex = 0;
        }
        centerOnPlayer(selectedIndex);

        update3DEffect();


        // ============================================================
        // BOTÓN PLAY
        // ============================================================
        GrowButton selectPlayerButton = new GrowButton(mCameraWidth / 2f + 120f, mCameraHeight / 2f - 400f,MenuResourceManager.btnPlayGame) {
            @Override
            public void onClick() {
                SceneManager.getInstance().showScene(new MainMenu());
            }
        };

        this.registerTouchArea(selectPlayerButton);
        this.mHomeMenuScreen.attachChild(selectPlayerButton);

        // ============================================================
        // BOTÓN RETURN
        // ============================================================
        GrowButton selectReturnHome = new GrowButton( selectPlayerButton.getX() - selectPlayerButton.getWidth() / 1.25f, selectPlayerButton.getY(), MenuResourceManager.btnReturnHome) {
            @Override
            public void onClick() {
                SceneManager.getInstance().showScene(new MainMenu());
            }
        };
        this.registerTouchArea(selectReturnHome);
        this.mHomeMenuScreen.attachChild(selectReturnHome);

        // ============================================================
        // BOTÓN DERECHA
        // ============================================================
        GrowButton btnRight = new GrowButton(mCameraWidth - 450f, selectPlayerButton.getY(), MenuResourceManager.btnGenericTTR.getTextureRegion(1)) {
            @Override
            public void onClick() {
                if (selectedIndex < playerCenters.size() - 1) {
                    selectedIndex++;
                    SharedResources.setSelectedPlayer(selectedIndex);
                    centerOnPlayer(selectedIndex);
                }
            }
        };
        btnRight.setScales(0.75f,0.85f);
        Sprite iconArrowRigth = new Sprite(btnRight.getWidth()/2f, btnRight.getHeight() /2f, MenuResourceManager.arrow, ResourceManager.getActivity().getVertexBufferObjectManager());
        btnRight.attachChild(iconArrowRigth);
        this.registerTouchArea(btnRight);
        this.mHomeMenuScreen.attachChild(btnRight);

        // ============================================================
        // BOTÓN IZQUIERDA
        // ============================================================
        GrowButton btnLeft = new GrowButton(450, selectReturnHome.getY(), MenuResourceManager.btnGenericTTR.getTextureRegion(1)) {
            @Override
            public void onClick() {
                if (selectedIndex > 0) {
                    selectedIndex--;
                    SharedResources.setSelectedPlayer(selectedIndex);
                    centerOnPlayer(selectedIndex);
                }
            }
        };
        btnLeft.setScales(0.75f,0.85f);
        Sprite iconArrowLeft = new Sprite(btnLeft.getWidth()/2f, btnLeft.getHeight() /2f, MenuResourceManager.arrow, ResourceManager.getActivity().getVertexBufferObjectManager());
        iconArrowLeft.setRotation(-180);
        btnLeft.attachChild(iconArrowLeft);
        this.registerTouchArea(btnLeft);
        this.mHomeMenuScreen.attachChild(btnLeft);

        this.mHomeMenuScreen.attachChild(scrollContainer);
        this.attachChild(mHomeMenuScreen);
    }

    // ============================================================
    // CENTRAR JUGADOR
    // ============================================================

    private void centerOnPlayer(int index) {

        float spriteCenter = playerCenters.get(index);
        float desiredX = mHalfCameraWidth - spriteCenter;

        scrollContainer.registerEntityModifier(
                new MoveXModifier(0.25f, scrollContainer.getX(), desiredX) {
                    @Override
                    protected void onModifierFinished(org.andengine.entity.IEntity pItem) {
                        super.onModifierFinished(pItem);
                        update3DEffect();
                    }
                }
        );
    }

    private void update3DEffect() {
        Scroll3DEffect effect = new Scroll3DEffect(
                mHalfCameraWidth, // centro
                400f,             // maxDist
                1.8f,             // maxScale
                1.2f,             // minScale
                0.5f              // minAlpha
        );
        effect.apply(scrollContainer);
    }

    @Override
    public void onShowScene() {
        MasterMiniGolfSmoothCamera.setupForMenus();
        if (!this.mMiniGolfBGSprite.hasParent()) {
            this.attachChild(this.mMiniGolfBGSprite);
            this.sortChildren();
        }
    }

    @Override
    public void onUnloadScene() {}

    public static void RefreshCoins() {

    }

    private String getPlayerName(int index) {
        switch (index) {
            case 0: return ResourceManager.getContext().getText(R.string.app_nina).toString();
            case 1: return ResourceManager.getContext().getText(R.string.app_nino).toString();
            case 2: return ResourceManager.getContext().getText(R.string.app_frog).toString();
            case 3: return ResourceManager.getContext().getText(R.string.app_astronauta).toString();
            default: return ResourceManager.getContext().getText(R.string.app_defaultnameplayer).toString();
        }
    }
}