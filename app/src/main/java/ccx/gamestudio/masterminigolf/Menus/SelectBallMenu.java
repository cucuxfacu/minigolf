package ccx.gamestudio.masterminigolf.Menus;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import java.util.ArrayList;
import java.util.List;

import ccx.gamestudio.masterminigolf.Helpers.Scroll3DEffect;
import ccx.gamestudio.masterminigolf.Helpers.SharedResources;
import ccx.gamestudio.masterminigolf.Input.GrowButton;
import ccx.gamestudio.masterminigolf.Manager.MenuResourceManager;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.Manager.SceneManager;
import ccx.gamestudio.masterminigolf.MasterMiniGolfSmoothCamera;
import ccx.gamestudio.masterminigolf.R;

public class SelectBallMenu extends ManagedMenuScene {

	// ====================================================
	// CONSTANTS
	// ====================================================
	private static final SelectBallMenu INSTANCE = new SelectBallMenu();
	private static final float mCameraHeight = ResourceManager.getInstance().cameraHeight;
	private static final float mCameraWidth = ResourceManager.getInstance().cameraWidth;
	private static final float mHalfCameraHeight = (ResourceManager.getInstance().cameraHeight / 2f);
	private static final float mHalfCameraWidth = (ResourceManager.getInstance().cameraWidth / 2f);

	// ====================================================
	// INSTANCE GETTER
	// ====================================================
	public static SelectBallMenu getInstance() {
		return INSTANCE;
	}
	private Entity mHomeMenuScreen;
	private Sprite mMiniGolfBGSprite;
	private Sprite mBGSprite;
    private int selectedIndex = 0;
    private List<Float> ballCenters = new ArrayList<>();
	private static float mScaleTextplay= 1.5f;
    private Entity scrollContainer;
	// ====================================================
	// CONSTRUCTOR
	// ====================================================
	public SelectBallMenu() {
		super(0.001f);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);
	}

    public static float getmScaleTextplay() {
        return mScaleTextplay;
    }

    public static void setmScaleTextplay(float mScaleTextplay) {
        SelectBallMenu.mScaleTextplay = mScaleTextplay;
    }


    // ====================================================
	// METHODS
	// ====================================================

	@Override
	public void onHideScene() {}

	@Override
	public Scene onLoadingScreenLoadAndShown() {
		MasterMiniGolfSmoothCamera.setupForMenus();
		final Scene MenuLoadingScene = new Scene();

		this.mMiniGolfBGSprite = new Sprite(0f, 0f, MenuResourceManager.backgroundOptions, ResourceManager.getActivity().getVertexBufferObjectManager());
		this.mMiniGolfBGSprite.setScale(ResourceManager.getInstance().cameraHeight / MenuResourceManager.backgroundOptions.getHeight());
		this.mMiniGolfBGSprite.setPosition((this.mMiniGolfBGSprite.getWidth() * this.mMiniGolfBGSprite.getScaleX()) / 2f, (this.mMiniGolfBGSprite.getHeight() * this.mMiniGolfBGSprite.getScaleY()) / 2f);
		this.mMiniGolfBGSprite.setZIndex(-998);
		this.mMiniGolfBGSprite.setAlpha(0.8f);

		this.mBGSprite = new Sprite(0f, 0f, MenuResourceManager.menuBackground, ResourceManager.getActivity().getVertexBufferObjectManager());
		this.mBGSprite.setPosition((this.mBGSprite.getWidth() * this.mBGSprite.getScaleX()) / 2f, (this.mBGSprite.getHeight() * this.mBGSprite.getScaleY()) / 2f);
		this.mBGSprite.setZIndex(-999);
		this.mMiniGolfBGSprite.attachChild(mBGSprite);

		MenuLoadingScene.attachChild(this.mMiniGolfBGSprite);

		MenuLoadingScene.attachChild(new Text(mHalfCameraWidth, mHalfCameraHeight, ResourceManager.fontDefault60, ResourceManager.getContext().getText(R.string.app_loading), ResourceManager.getActivity().getVertexBufferObjectManager()));
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

        ballCenters.clear();

        for (int i = 0; i < MenuResourceManager.mListBall.size(); i++) {

            Sprite ball = new Sprite(0, 0,   MenuResourceManager.mListBall.get(i),ResourceManager.getEngine().getVertexBufferObjectManager());

            Text ballName = new Text(ball.getWidth() / 2, ball.getY() - 15f, ResourceManager.fontDefault36, getBallName(i), ResourceManager.getEngine().getVertexBufferObjectManager());
            ball.attachChild(ballName);

            float centerX = xOffset + ball.getWidth() / 2f;
            ball.setPosition(centerX, mHalfCameraHeight);

            scrollContainer.attachChild(ball);

            ballCenters.add(centerX);

            xOffset += ball.getWidth() + separation;
        }

        selectedIndex = SharedResources.getSelectedBall();
        if (selectedIndex < 0 || selectedIndex >= ballCenters.size()) {
            selectedIndex = 0;
        }
        centerOnBall(selectedIndex);

        update3DEffect();

        //====================================== BUTTONS OPTIONS======================================//
		GrowButton selectBallButton = new GrowButton(mCameraWidth / 2f + 120f, mCameraHeight / 2f - 400f , MenuResourceManager.btnPlayGame) {
			@Override
			public void onClick() {

			}
		};
		this.registerTouchArea(selectBallButton);

		GrowButton selectReturnHome = new GrowButton(selectBallButton.getX() - selectBallButton.getWidth() / 1.25f, selectBallButton.getY() , MenuResourceManager.btnReturnHome) {
			@Override
			public void onClick() {
				SceneManager.getInstance().showScene(new MainMenu());

			}
		};


        // ============================================================
        // BOTÓN DERECHA
        // ============================================================
        GrowButton btnRight = new GrowButton(mCameraWidth - 450f, selectBallButton.getY(), MenuResourceManager.btnGenericTTR.getTextureRegion(1)) {
            @Override
            public void onClick() {
                if (selectedIndex < ballCenters.size() - 1) {
                    selectedIndex++;
                    SharedResources.setSelectedBall(selectedIndex);
                    centerOnBall(selectedIndex);
                }
            }
        };
        btnRight.setScales(0.75f,0.85f);
        Sprite iconArrowRigth = new Sprite(btnRight.getWidth()/2f, btnRight.getHeight() /2f, MenuResourceManager.arrow, ResourceManager.getActivity().getVertexBufferObjectManager());
        btnRight.attachChild(iconArrowRigth);



        // ============================================================
        // BOTÓN IZQUIERDA
        // ============================================================
        GrowButton btnLeft = new GrowButton(450, selectReturnHome.getY(), MenuResourceManager.btnGenericTTR.getTextureRegion(1)) {
            @Override
            public void onClick() {
                if (selectedIndex > 0) {
                    selectedIndex--;
                    SharedResources.setSelectedBall(selectedIndex);
                    centerOnBall(selectedIndex);
                }
            }
        };
        btnLeft.setScales(0.75f,0.85f);
        Sprite iconArrowLeft = new Sprite(btnLeft.getWidth()/2f, btnLeft.getHeight() /2f, MenuResourceManager.arrow, ResourceManager.getActivity().getVertexBufferObjectManager());
        iconArrowLeft.setRotation(-180);
        btnLeft.attachChild(iconArrowLeft);

        this.registerTouchArea(selectReturnHome);
        this.registerTouchArea(btnRight);
        this.registerTouchArea(btnLeft);

        this.mHomeMenuScreen.attachChild(scrollContainer);
		this.mHomeMenuScreen.attachChild(selectBallButton);
		this.mHomeMenuScreen.attachChild(selectReturnHome);
        this.mHomeMenuScreen.attachChild(btnLeft);
        this.mHomeMenuScreen.attachChild(btnRight);

		this.attachChild(this.mHomeMenuScreen);

	}


	@Override
	public void onShowScene() {
		MasterMiniGolfSmoothCamera.setupForMenus();
		if(!this.mMiniGolfBGSprite.hasParent()) {
			this.attachChild(this.mMiniGolfBGSprite);
			this.sortChildren();
		}
		ShowAdBanner();
	}

	@Override
	public void onUnloadScene() {}



	public void ShowAdBanner(){
		//ResourceManager.getActivity().ShowAds();
	}

    // ============================================================
    // CENTRAR BOLAS DE GOLF
    // ============================================================

    private void centerOnBall(int index) {

        float spriteCenter = ballCenters.get(index);
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

    private CharSequence getBallName(int index) {
        switch (index) {
            case 1: return ResourceManager.getContext().getText(R.string.app_apple).toString();
            case 2: return ResourceManager.getContext().getText(R.string.app_tenis).toString();
            default: return ResourceManager.getContext().getText(R.string.app_boladefault).toString();
        }
    }
}