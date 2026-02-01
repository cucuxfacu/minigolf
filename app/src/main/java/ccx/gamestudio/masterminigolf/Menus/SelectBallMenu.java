package ccx.gamestudio.masterminigolf.Menus;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

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
	private Entity mPowerTankScreen;
	private Sprite mMiniGolfBGSprite;
	private Sprite mBGSprite;
	private GrowButton mSettingButton;
	private GrowButton mAbout;
	private GrowButton mStore;
	private static Text mCoinText;
	private int intChange = 0;
	private boolean lock = false;
	private GrowButton btnChangePower;
	private GrowButton btnChangeFriction;
	private Sprite mCoinsChengePower;
	private static Text mTextCoinsChangePower;
	private Sprite mCoinsChangeFriction;
	private static Text mTextCoinsChangeFriction;
	private static float mScaleTextplay= 1.5f;
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


        Sprite ballsSelected = new Sprite(0, 0, MenuResourceManager.mListBall.get(0), ResourceManager.getEngine().getVertexBufferObjectManager());
        ballsSelected.setPosition(mCameraWidth / 2f, mCameraHeight / 2f);

        //====================================== BUTTONS OPTIONS======================================//
		GrowButton selectBallButton = new GrowButton(mCameraWidth / 2f, mCameraHeight / 2f - 400f , MenuResourceManager.btnPlayGame) {
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
		this.registerTouchArea(selectReturnHome);

        this.mHomeMenuScreen.attachChild(ballsSelected);
		this.mHomeMenuScreen.attachChild(selectBallButton);
		this.mHomeMenuScreen.attachChild(selectReturnHome);

		this.attachChild(this.mHomeMenuScreen);

	}


	private static void LeaderBoard() {
//		if(SharedResources.getStringFromSharedPreferences(SharedResources.SHARED_USER_EMAIL).equals("0")||
//				SharedResources.getStringFromSharedPreferences(SharedResources.SHARED_USER_EMAIL).isEmpty()) {
//			NotGemsGameLayer.getInstance().noGems = 6;
//			SceneManager.getInstance().showLayer(NotGemsGameLayer.getInstance(), false, false, true);
//		}
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

	public static void RefreshCoins() {
		mCoinText.setText(String.valueOf(SharedResources.getTotalsCoins()));
	}


	public void RefreshTextCoinsPower(CharSequence text) {
		if(mTextCoinsChangePower !=null) {
			mTextCoinsChangePower.setText(text);
		}
	}

	public void RefreshTextCoinsFriction(CharSequence text) {
		if(mTextCoinsChangeFriction !=null) {
			mTextCoinsChangeFriction.setText(text);
		}
	}



	public void ShowAdBanner(){
		//ResourceManager.getActivity().ShowAds();
	}
}