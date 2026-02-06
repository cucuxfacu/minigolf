package ccx.gamestudio.masterminigolf.Layers;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.Helpers.SharedResources;
import ccx.gamestudio.masterminigolf.Input.GrowButtonExit;
import ccx.gamestudio.masterminigolf.Manager.MenuResourceManager;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.Manager.SFXManager;
import ccx.gamestudio.masterminigolf.Manager.SceneManager;
import ccx.gamestudio.masterminigolf.R;

public class FailedLevelLayer extends ManagedLayer {
	
	// ====================================================
	// CONSTANTS
	// ====================================================
	private static final FailedLevelLayer INSTANCE = new FailedLevelLayer();
	private static float mScale= 3f;
	private static Color mColor = new Color(0.55f, 0.40f, 0.20f, 0.55f);
	private static Color mColorTextos = new Color(0.55f, 0.40f, 0.20f, 0.55f);

	// ====================================================
	// INSTANCE GETTERS
	// ====================================================
	public static FailedLevelLayer getInstance() {
		return INSTANCE;
	}
	
	public static FailedLevelLayer getInstance(final GameLevel pCurrentLevel) {
		INSTANCE.setCurrentLevel(pCurrentLevel);
		return INSTANCE;
	}
	
	// ====================================================
	// VARIABLES
	// ====================================================
	public GameLevel mCurrentLevel;
	private boolean mIsGoingBackToLevel = true;
    private  Rectangle fadableBGRect;
    private Sprite mLayerBG;
	private boolean howIsTheSound;
    private Text mMainText;
    private Text mHighScoreText;
    private Text mScoreText;
    private Text mHighScoreNumber;
    private Text mScoreNumber;

	// ====================================================
	// UPDATE HANDLERS
	// ====================================================
	IUpdateHandler mSlideInUpdateHandler = new IUpdateHandler() {
		@Override
		public void onUpdate(final float pSecondsElapsed) {
            if(FailedLevelLayer.this.mLayerBG.getY() > 0f) {
                FailedLevelLayer.this.mLayerBG.setY(Math.max(FailedLevelLayer.this.mLayerBG.getY() - (pSecondsElapsed * mSLIDE_PIXELS_PER_SECONDS), 0f));
            } else {
                ResourceManager.getInstance().engine.unregisterUpdateHandler(this);
            }
        }
		
		@Override
		public void reset() {}
	};
	IUpdateHandler mSlideOutUpdateHandler = new IUpdateHandler() {
		@Override
		public void onUpdate(final float pSecondsElapsed) {
            if(FailedLevelLayer.this.mLayerBG.getY() < ((ResourceManager.getInstance().cameraHeight / 2f) + (FailedLevelLayer.this.mLayerBG.getHeight() / 2f))) {
                FailedLevelLayer.this.mLayerBG.setY(Math.min(FailedLevelLayer.this.mLayerBG.getY() + (pSecondsElapsed * mSLIDE_PIXELS_PER_SECONDS), (ResourceManager.getInstance().cameraHeight / 2f) + (FailedLevelLayer.this.mLayerBG.getHeight() / 2f)));
			} else {
                ResourceManager.getInstance().engine.unregisterUpdateHandler(this);
                SceneManager.getInstance().hideLayer();

                if (FailedLevelLayer.this.mIsGoingBackToLevel) {
                    mCurrentLevel.restartLevel();
                    ActivateSound();
                    return;
                }

                FailedLevelLayer.this.mCurrentLevel.disposeLevel();
                ActivateSound();
                SceneManager.getInstance().showMainMenu();
            }
		}
		
		@Override
		public void reset() {}
	};

	private void ActivateSound() {

		if(!howIsTheSound && SFXManager.isSoundMuted())
			SFXManager.setSoundMuted(false);
	}

	// ====================================================
	// METHODS
	// ====================================================
	@Override
	public void onHideLayer() {
		ResourceManager.getInstance().engine.registerUpdateHandler(this.mSlideOutUpdateHandler);
	}
	
	@Override
	public void onLoadLayer() {
		if(this.mHasLoaded) {
			return;
		}
		this.mHasLoaded = true;
		
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setTouchAreaBindingOnActionMoveEnabled(true);

        fadableBGRect = new Rectangle(0,0, ResourceManager.getInstance().cameraWidth, ResourceManager.getInstance().cameraHeight, ResourceManager.getActivity().getVertexBufferObjectManager());
        fadableBGRect.setColor(mColor);
        this.attachChild(fadableBGRect);

        this.mLayerBG = new Sprite(0f, ResourceManager.getInstance().cameraHeight / 2f, MenuResourceManager.layerFailed, ResourceManager.getActivity().getVertexBufferObjectManager());
        this.attachChild(this.mLayerBG);

        mMainText = new Text(0f, 0f, ResourceManager.fontDefault60, ResourceManager.getContext().getText(R.string.app_failed),255, ResourceManager.getActivity().getVertexBufferObjectManager());
		mMainText.setPosition(mLayerBG.getWidth() / 2f, mLayerBG.getHeight() / 2f + 250f);
        mMainText.setScale(1.3f);
        mLayerBG.attachChild(mMainText);

        mHighScoreText = new Text(0f, 0f, ResourceManager.fontDefault60, ResourceManager.getContext().getText(R.string.app_bestscore),255, new TextOptions(AutoWrap.WORDS, 1000, HorizontalAlign.CENTER),ResourceManager.getActivity().getVertexBufferObjectManager());
        mHighScoreText.setPosition(mLayerBG.getWidth() / 2f - 50f, mLayerBG.getHeight() / 2f + 50f);
        mHighScoreText.setScale(1f);
        mHighScoreText.setColor(mColorTextos);
        mLayerBG.attachChild(mHighScoreText);

        mScoreText = new Text(0f, 0f, ResourceManager.fontDefault60, ResourceManager.getContext().getText(R.string.app_score),255, new TextOptions(AutoWrap.WORDS, 1000, HorizontalAlign.CENTER),ResourceManager.getActivity().getVertexBufferObjectManager());
        mScoreText.setPosition(mLayerBG.getWidth() / 2f -200f, mLayerBG.getHeight() / 2f );
        mScoreText.setScale(1f);
        mScoreText.setColor(mColorTextos);
        mLayerBG.attachChild(mScoreText);

        mHighScoreNumber = new Text(0f, 0f, ResourceManager.fontDefault60, "0",100, new TextOptions(AutoWrap.WORDS, 10, HorizontalAlign.LEFT),ResourceManager.getActivity().getVertexBufferObjectManager());
        mHighScoreNumber.setPosition(mLayerBG.getWidth() / 2f + 250f, mLayerBG.getHeight() / 2f + 50f);
        mHighScoreNumber.setScale(1f);
        mHighScoreNumber.setColor(mColorTextos);
        mLayerBG.attachChild(mHighScoreNumber);

        mScoreNumber = new Text(0f, 0f, ResourceManager.fontDefault60, "0",100, new TextOptions(AutoWrap.WORDS, 10, HorizontalAlign.LEFT),ResourceManager.getActivity().getVertexBufferObjectManager());
        mScoreNumber.setPosition(mLayerBG.getWidth() / 2f + 250f, mLayerBG.getHeight() / 2f );
        mScoreNumber.setScale(1f);
        mScoreNumber.setColor(mColorTextos);
        mLayerBG.attachChild(mScoreNumber);

		final GrowButtonExit btnBackToMenu = new GrowButtonExit(mLayerBG.getWidth() / 2f - 250f, mLayerBG.getHeight() / 2f - 230f, MenuResourceManager.btnReturnHome) {
			@Override
			public void onClick() {
                FailedLevelLayer.this.mIsGoingBackToLevel = false;
				FailedLevelLayer.this.onHideLayer();
			}
		};
        btnBackToMenu.setScales(0.85f, 0.95f);
        mLayerBG.attachChild(btnBackToMenu);
		this.registerTouchArea(btnBackToMenu);

        final GrowButtonExit btnBackToLevel = new GrowButtonExit(btnBackToMenu.getX() + 350, btnBackToMenu.getY(), MenuResourceManager.btnPlayGame) {
            @Override
            public void onClick() {
                FailedLevelLayer.this.mIsGoingBackToLevel = true;
                FailedLevelLayer.this.onHideLayer();
            }
        };
        btnBackToLevel.setScales(0.85f, 0.95f);
        mLayerBG.attachChild(btnBackToLevel);
        this.registerTouchArea(btnBackToLevel);

		this.setPosition(ResourceManager.getInstance().cameraWidth / 2f, ResourceManager.getInstance().cameraHeight / 2f);
	}

	@Override
	public void onShowLayer() {
		//ResourceManager.getActivity().ShowAds();
		ResourceManager.getInstance().engine.registerUpdateHandler(this.mSlideInUpdateHandler);

		howIsTheSound = SFXManager.isSoundMuted();

		if(!SFXManager.isSoundMuted())
		{
			SFXManager.setSoundMuted(true);
		}

        mHighScoreNumber.setText("");
        if(SharedResources.getScorePlayer() > SharedResources.getHighScorePlayer())
        {
            SharedResources.setHighScorePlayer(SharedResources.getScorePlayer());
        }
        mHighScoreNumber.setText(String.valueOf(SharedResources.getHighScorePlayer()));
        mScoreNumber.setText("");
        mScoreNumber.setText(String.valueOf(SharedResources.getScorePlayer()));
	}
	
	@Override
	public void onUnloadLayer() {

    }
	
	public void setCurrentLevel(final GameLevel pCurrentLevel) {
		this.mCurrentLevel = pCurrentLevel;
	}
}