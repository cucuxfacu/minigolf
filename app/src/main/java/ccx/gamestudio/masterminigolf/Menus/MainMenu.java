package ccx.gamestudio.masterminigolf.Menus;



import android.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.adt.align.HorizontalAlign;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameLevels.Level;
import ccx.gamestudio.masterminigolf.Helpers.SharedResources;
import ccx.gamestudio.masterminigolf.Input.GrowButton;
import ccx.gamestudio.masterminigolf.Input.GrowToggleButton;
import ccx.gamestudio.masterminigolf.Layers.ExitGameLayer;
import ccx.gamestudio.masterminigolf.ManagedScene;
import ccx.gamestudio.masterminigolf.Manager.MenuResourceManager;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.Manager.SFXManager;
import ccx.gamestudio.masterminigolf.Manager.SceneManager;
import ccx.gamestudio.masterminigolf.R;
import ccx.gamestudio.masterminigolf.MasterMiniGolfSmoothCamera;

public class MainMenu extends ManagedMenuScene {

	// ====================================================
	// CONSTANTS
	// ====================================================
	private static final MainMenu INSTANCE = new MainMenu();
	private static final float mCameraHeight = ResourceManager.getInstance().cameraHeight;
	private static final float mCameraWidth = ResourceManager.getInstance().cameraWidth;
	private static final float mHalfCameraHeight = (ResourceManager.getInstance().cameraHeight / 2f);
	private static final float mHalfCameraWidth = (ResourceManager.getInstance().cameraWidth / 2f);

	// ====================================================
	// INSTANCE GETTER
	// ====================================================
	public static MainMenu getInstance() {
		return INSTANCE;
	}
	private Entity mHomeMenuScreen;
	private Entity mPowerTankScreen;
	private Sprite mMiniGolfBGSprite;
	private GrowButton mSettingButton;
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
	public MainMenu() {
		super(0.001f);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);
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
		this.mMiniGolfBGSprite = new Sprite(0f, 0f, MenuResourceManager.menuBackground, ResourceManager.getActivity().getVertexBufferObjectManager());
		this.mMiniGolfBGSprite.setScale(ResourceManager.getInstance().cameraHeight / MenuResourceManager.menuBackground.getHeight());
		this.mMiniGolfBGSprite.setPosition((this.mMiniGolfBGSprite.getWidth() * this.mMiniGolfBGSprite.getScaleX()) / 2f, (this.mMiniGolfBGSprite.getHeight() * this.mMiniGolfBGSprite.getScaleY()) / 2f);
		this.mMiniGolfBGSprite.setZIndex(-999);
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

		//=================Buttons Music and Sound=================================
		GrowToggleButton BtnSoundToggle = new GrowToggleButton(MenuResourceManager.btnSoundTTR.getWidth() / 2 + 50f,ResourceManager.getCamera().getHeight() - (MenuResourceManager.btnSoundTTR.getHeight() / 2f), MenuResourceManager.btnSoundTTR,!SFXManager.isSoundMuted()) {
			@Override
			public boolean checkState() {
				return !SFXManager.isSoundMuted();
			}

			@Override
			public void onClick() {
				SFXManager.toggleSoundMuted();
			}
		};
		this.registerTouchArea(BtnSoundToggle);

		GrowToggleButton BtnMusicToggle = new GrowToggleButton(BtnSoundToggle.getX() + 200f, BtnSoundToggle.getY(), MenuResourceManager.btnMusicTTR,!SFXManager.isMusicMuted()) {
			@Override
			public boolean checkState() {
				return !SFXManager.isMusicMuted();
			}

			@Override
			public void onClick() {
				SFXManager.toggleMusicMuted();
			}
		};
		this.registerTouchArea(BtnMusicToggle);
		//=================END=================================/


		final Sprite MainTitleText = new Sprite(((this.mMiniGolfBGSprite.getWidth() * this.mMiniGolfBGSprite.getScaleX()) / 5), mCameraHeight / 2f - 300f, MenuResourceManager.titleGame, ResourceManager.getActivity().getVertexBufferObjectManager());
		MainTitleText.setScale(1f);
		ShowAdBanner();

		GrowButton challengeButton = new GrowButton(mCameraWidth / 2f, mCameraHeight / 2f + 250f, MenuResourceManager.btnGeneric) {
			@Override
			public void onClick() {
                SceneManager.getInstance().showScene(new GameLevel(Level.getLevelDef(1, 1)));
			}

		};
		final Text txtPlay = new Text(challengeButton.getWidth() / 2, challengeButton.getHeight() / 2 + 25, ResourceManager.fontDefault72, ResourceManager.getContext().getText(R.string.app_desafio), ResourceManager.getActivity().getVertexBufferObjectManager());
		challengeButton.attachChild(txtPlay);
		this.registerTouchArea(challengeButton);

		GrowButton timeTrialButton = new GrowButton(challengeButton.getX(), challengeButton.getY() - 200f, MenuResourceManager.btnGeneric) {
			@Override
			public void onClick() {

			}
		};
		final Text txtTimeTrial = new Text(timeTrialButton.getWidth() / 2, timeTrialButton.getHeight() / 2 + 25, ResourceManager.fontDefault60, ResourceManager.getContext().getText(R.string.app_contrareloj), ResourceManager.getActivity().getVertexBufferObjectManager());
		timeTrialButton.attachChild(txtTimeTrial);
		this.registerTouchArea(timeTrialButton);

		GrowButton practiceButton = new GrowButton(timeTrialButton.getX(), timeTrialButton.getY() - 200f, MenuResourceManager.btnGeneric) {
			@Override
			public void onClick() {

			}
		};
		final Text txtPractice = new Text(practiceButton.getWidth() / 2, practiceButton.getHeight() / 2 + 25, ResourceManager.fontDefault72, ResourceManager.getContext().getText(R.string.app_pratica), ResourceManager.getActivity().getVertexBufferObjectManager());
		practiceButton.attachChild(txtPractice);
		this.registerTouchArea(practiceButton);

		GrowButton exitButton = new GrowButton(practiceButton.getX(), practiceButton.getY() - 200f, MenuResourceManager.btnExit) {
			@Override
			public void onClick() {
				SceneManager.getInstance().showLayer(ExitGameLayer.getInstance(), false, false, true);
			}
		};
		final Text txtExit = new Text(exitButton.getWidth() / 2, exitButton.getHeight() / 2 + 25, ResourceManager.fontDefault72, ResourceManager.getContext().getText(R.string.app_exit), ResourceManager.getActivity().getVertexBufferObjectManager());
		exitButton.attachChild(txtExit);
		this.registerTouchArea(exitButton);


		//====================================== BUTTONS OPTIONS======================================//
        GrowButton selectPlayerButton = getGrowButton(challengeButton.getX() + 350f, challengeButton.getY(), MenuResourceManager.btnHeads, new SelectPlayerMenu());
        this.registerTouchArea(selectPlayerButton);

        GrowButton selectBallButton = getGrowButton(selectPlayerButton.getX() , selectPlayerButton.getY() - 200f, MenuResourceManager.btnBalls, new SelectBallMenu());
        this.registerTouchArea(selectBallButton);

		GrowButton selectTrophyButton = new GrowButton(selectBallButton.getX() , selectBallButton.getY() - 200f , MenuResourceManager.btnTrophy) {
			@Override
			public void onClick() {

			}
		};
		this.registerTouchArea(selectTrophyButton);

		///About button
		GrowButton mAbout = new GrowButton(selectTrophyButton.getX(), selectTrophyButton.getY() - 200f, MenuResourceManager.btnInformacion) {
			@Override
			public void onClick() {
				ResourceManager.getActivity().runOnUiThread(() -> {
					final AlertDialog.Builder builder = new AlertDialog.Builder(ResourceManager.getActivity())
							.setIcon(R.mipmap.ic_launcher)
							.setTitle(ResourceManager.getContext().getString(R.string.app_name))
							.setMessage(SharedResources.getSpanned()).setPositiveButton(ResourceManager.getContext().getText(R.string.app_back), (dialog, id) -> {
							});
					final AlertDialog alert = builder.create();
					alert.show();
					((TextView) alert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
				});
			}
		};
		this.registerTouchArea(mAbout);

//
//		GrowButton mUser = new GrowButton(ResourceManager.getCamera().getWidth() - 100f, ResourceManager.getCamera().getHeight() - 100f, ResourceManager.btnBlueCircleTTR.getTextureRegion(0)) {
//			@Override
//			public void onClick() {
//				SceneManager.getInstance().showLayer(UserTanksLayer.getInstance(), false, false, true);
//			}
//		};
//		Sprite mIconUser = new Sprite(0f, 0f, ResourceManager.btnIconsBtnTTR.getTextureRegion(9), ResourceManager.getActivity().getVertexBufferObjectManager());
//		mIconUser.setPosition(mUser.getWidth() / 2f, mUser.getHeight() / 2f);
//		mUser.attachChild(mIconUser);
//		this.registerTouchArea(mUser);
//
//		GrowButton mUserLeaderBoard = new GrowButton(mUser.getX(), mUser.getY() - 200f, ResourceManager.btnOrangeCircleTTR.getTextureRegion(1)) {
//			@Override
////			public void onClick() {
////				InAppLoginGooglePlayGameServices.getInstance().ShowLeaderboard();
////			}
//		};
//		Sprite mIconLederBoard = new Sprite(mUserLeaderBoard.getWidth() / 2f, mUserLeaderBoard.getHeight() / 2f, ResourceManager.btnIconsBtnTTR.getTextureRegion(23), ResourceManager.getActivity().getVertexBufferObjectManager());
//		mUserLeaderBoard.attachChild(mIconLederBoard);
//		this.registerTouchArea(mUserLeaderBoard);
//
//		GrowButton mGiftOfTheDay = new GrowButton(mUserLeaderBoard.getX(), mUserLeaderBoard.getY() - 200f, ResourceManager.btnGreenCircleTTR.getTextureRegion(0)) {
//			@Override
//			public void onClick() {
//				SceneManager.getInstance().showLayer(GiftOfDayLayer.getInstance(), false, false, true);
//			}
//		};
//		Sprite mIconGiftOfTheDay = new Sprite(mGiftOfTheDay.getWidth() / 2f, mGiftOfTheDay.getHeight() / 2f, ResourceManager.sackOfCoinsTR, ResourceManager.getActivity().getVertexBufferObjectManager());
//		mIconGiftOfTheDay.setScale(0.68f);
//		mGiftOfTheDay.attachChild(mIconGiftOfTheDay);
//		this.registerTouchArea(mGiftOfTheDay);
//
//		Sprite backgroundcoins = new Sprite(ResourceManager.btnCoinTR.getWidth() / 2f + 150f, ResourceManager.getCamera().getHeight() - (ResourceManager.btnGameCoinTR.getHeight() / 4f), ResourceManager.backgroundPoints, ResourceManager.getActivity().getVertexBufferObjectManager());
//		backgroundcoins.setWidth(700f);
//		backgroundcoins.setScale(0.5f);
//		backgroundcoins.setAlpha(0.50f);
//
//		Sprite mCoin = new Sprite(backgroundcoins.getWidth() / 2f - 300f, backgroundcoins.getHeight() / 2f, ResourceManager.btnGameCoinTR, ResourceManager.getActivity().getVertexBufferObjectManager());
//		mCoin.setScale(0.7f);
//		backgroundcoins.attachChild(mCoin);
//
//		Sprite backgroundGems = new Sprite(backgroundcoins.getX(), backgroundcoins.getY() - 100f, ResourceManager.backgroundPoints, ResourceManager.getActivity().getVertexBufferObjectManager());
//		backgroundGems.setWidth(700f);
//		backgroundGems.setScale(0.5f);
//		backgroundGems.setAlpha(0.50f);
//
//		Sprite mGems = new Sprite(backgroundGems.getWidth() / 2f - 300f, backgroundGems.getHeight() / 2f, ResourceManager.btnGemsTR, ResourceManager.getActivity().getVertexBufferObjectManager());
//		mGems.setScale(0.75f);
//		backgroundGems.attachChild(mGems);
//
//		mCoinText = new Text(0f, 0f, ResourceManager.fontDefault60, String.valueOf(SharedResources.getTotalsCoins()), 255, ResourceManager.getActivity().getVertexBufferObjectManager());
//		mCoinText.setPosition(backgroundcoins.getWidth() / 2f + 50f, backgroundcoins.getHeight() / 2f);
//		mCoinText.setScale(2f);
//		backgroundcoins.attachChild(mCoinText);
//
//		final int gems = SharedResources.getIntFromSharedPreferences(SharedResources.SHARED_PREFS_GEMS);
//		Text mGemsText = new Text(0f, 0f, ResourceManager.fontDefault60, String.valueOf(gems), 255, ResourceManager.getActivity().getVertexBufferObjectManager());
//		mGemsText.setPosition(backgroundGems.getWidth() / 2f + 50f, backgroundGems.getHeight() / 2);
//		mGemsText.setScale(2f);
//		backgroundGems.attachChild(mGemsText);
//
//		mHomeMenuScreen.attachChild(backgroundcoins);
//		mHomeMenuScreen.attachChild(backgroundGems);
//
//		///Setting button
//		this.mSettingButton = new GrowButton(ResourceManager.btnBlueCircleTTR.getWidth() / 2f + 50f, ResourceManager.btnBlueCircleTTR.getHeight() / 2f + 50f, ResourceManager.btnBlueCircleTTR.getTextureRegion(0)) {
//			@Override
//			public void onClick() {
//				SceneManager.getInstance().showLayer(OptionsLayer.getInstance(), false, false, true);
//			}
//		};
//		Sprite mSettingIconButton = new Sprite(0f, 0f, ResourceManager.btnIconsBtnTTR.getTextureRegion(1), ResourceManager.getActivity().getVertexBufferObjectManager());
//		mSettingIconButton.setPosition(mSettingButton.getWidth() / 2f, mSettingButton.getHeight() / 2f);
//		this.mSettingButton.attachChild(mSettingIconButton);
//		this.registerTouchArea(mSettingButton);
//

//		Sprite mIconAbout = new Sprite(0f, 0f, ResourceManager.btnIconsBtnTTR.getTextureRegion(12), ResourceManager.getActivity().getVertexBufferObjectManager());
//		mIconAbout.setPosition(mAbout.getWidth() / 2f, mAbout.getHeight() / 2f);
//		this.mAbout.attachChild(mIconAbout);
//		this.registerTouchArea(mAbout);
//
//		///Exit button
//		this.mStore = new GrowButton(mAbout.getX(), mAbout.getY() + 200f, ResourceManager.btnBlueCircleTTR.getTextureRegion(0)) {
//			@Override
//			public void onClick() {
////				SceneManager.getInstance().showScene(new ShopMenu());
//			}
//		};
//		Sprite mIconStore = new Sprite(0f, 0f, ResourceManager.btnIconsBtnTTR.getTextureRegion(3), ResourceManager.getActivity().getVertexBufferObjectManager());
//		mIconStore.setPosition(mStore.getWidth() / 2f, mStore.getHeight() / 2f);
//		this.mStore.attachChild(mIconStore);
//		this.registerTouchArea(mStore);
//
//		///Store button
//		GrowButton mExit = new GrowButton(mStore.getX(), mStore.getY() + 200f, ResourceManager.btnRedCircleTTR.getTextureRegion(0)) {
//			@Override
//			public void onClick() {
////				SceneManager.getInstance().showLayer(ExitGameLayer.getInstance(), false, false, true);
//			}
//		};
//		Sprite mIconExit = new Sprite(0f, 0f, ResourceManager.btnIconsBtnTTR.getTextureRegion(6), ResourceManager.getActivity().getVertexBufferObjectManager());
//		mIconExit.setPosition(mExit.getWidth() / 2f, mExit.getHeight() / 2f);
//		mExit.attachChild(mIconExit);
//		this.registerTouchArea(mExit);
//
////		GrowButton btnLeft = new GrowButton(0, 0, ResourceManager.btnArrowLeftTR) {
////			@Override
////			public void onClick() {
////				if (intChange >= 1) {
////					intChange--;
////					changeTankDown(intChange);
////				}
////			}
////		};
////		btnLeft.setPosition(mHalfCameraWidth - 600f, mHalfCameraHeight - 100f);
////		btnLeft.setScales(1.5f, 1.8f);
////		this.registerTouchArea(btnLeft);
//
////		GrowButton btnRight = new GrowButton(0, 0, ResourceManager.btnArrowRightTR) {
////			@Override
////			public void onClick() {
////				if (intChange <= 3) {
////					intChange++;
////					changeTankUp(intChange);
////				}
////			}
////		};
////		btnRight.setPosition(mHalfCameraWidth + 600f, mHalfCameraHeight - 100f);
////		btnRight.setScales(1.5f, 1.8f);
////		this.registerTouchArea(btnRight);
//
//
////		this.mHomeMenuScreen.attachChild(btnLeft);
////		this.mHomeMenuScreen.attachChild(btnRight);
//
//		this.mHomeMenuScreen.attachChild(mUser);
//		this.mHomeMenuScreen.attachChild(mUserLeaderBoard);
//		this.mHomeMenuScreen.attachChild(mGiftOfTheDay);
//		this.mHomeMenuScreen.attachChild(mSettingButton);
//		this.mHomeMenuScreen.attachChild(mAbout);
//		this.mHomeMenuScreen.attachChild(mStore);
//		this.mHomeMenuScreen.attachChild(mExit);



		//InAppReview.getInstance().LoadAppReveview(SharedResources.getIntFromSharedPreferences(SharedResources.SHARED_PREFS_ACTIVITY_START_COUNT_RATING));

		this.mHomeMenuScreen.attachChild(MainTitleText);
		this.mHomeMenuScreen.attachChild(challengeButton);
		this.mHomeMenuScreen.attachChild(timeTrialButton);
		this.mHomeMenuScreen.attachChild(practiceButton);
		this.mHomeMenuScreen.attachChild(BtnSoundToggle);
		this.mHomeMenuScreen.attachChild(BtnMusicToggle);
		this.mHomeMenuScreen.attachChild(exitButton);
		this.mHomeMenuScreen.attachChild(selectBallButton);
		this.mHomeMenuScreen.attachChild(selectPlayerButton);
		this.mHomeMenuScreen.attachChild(selectTrophyButton);
		this.mHomeMenuScreen.attachChild(mAbout);
		this.attachChild(this.mHomeMenuScreen);

	}

    @NonNull
    private static GrowButton getGrowButton(final float pX, final float pY, final TextureRegion pTextureRegion, final ManagedScene pScene) {
        GrowButton selectButton = new GrowButton(pX,pY, MenuResourceManager.btnGenericSmall) {
            @Override
            public void onClick() {
                SceneManager.getInstance().showScene(pScene);
            }
        };
        Sprite mIconButton = new Sprite(selectButton.getWidth() / 2f, selectButton.getHeight() / 2f, pTextureRegion, ResourceManager.getActivity().getVertexBufferObjectManager());

        selectButton.attachChild(mIconButton);
        return selectButton;
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