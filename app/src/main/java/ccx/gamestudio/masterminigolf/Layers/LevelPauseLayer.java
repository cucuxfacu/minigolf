package ccx.gamestudio.masterminigolf.Layers;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.Input.GrowButtonExit;
import ccx.gamestudio.masterminigolf.Manager.MenuResourceManager;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.Manager.SFXManager;
import ccx.gamestudio.masterminigolf.Manager.SceneManager;
import ccx.gamestudio.masterminigolf.R;


public class LevelPauseLayer extends ManagedLayer {
	
	// ====================================================
	// CONSTANTS
	// ====================================================
	private static final LevelPauseLayer INSTANCE = new LevelPauseLayer();
	private static float mScale= 3f;
	private static Color mColor = new Color(0.212f, 0.275f, 0.212f);

	// ====================================================
	// INSTANCE GETTERS
	// ====================================================
	public static LevelPauseLayer getInstance() {
		return INSTANCE;
	}
	
	public static LevelPauseLayer getInstance(final GameLevel pCurrentLevel) {
		INSTANCE.setCurrentLevel(pCurrentLevel);
		return INSTANCE;
	}
	
	// ====================================================
	// VARIABLES
	// ====================================================
	public GameLevel mCurrentLevel;
	private boolean mIsGoingToRestartLevel = false;
	private boolean mIsGoingToNextLevel = false;
	private boolean mIsGoingBackToLevel = true;
    private  Rectangle fadableBGRect;
    private Sprite mLayerBG;
	private boolean howIsTheSound;

	// ====================================================
	// UPDATE HANDLERS
	// ====================================================
	IUpdateHandler mSlideInUpdateHandler = new IUpdateHandler() {
		@Override
		public void onUpdate(final float pSecondsElapsed) {
            if(LevelPauseLayer.this.mLayerBG.getY() > 0f) {
                LevelPauseLayer.this.mLayerBG.setY(Math.max(LevelPauseLayer.this.mLayerBG.getY() - (pSecondsElapsed * mSLIDE_PIXELS_PER_SECONDS), 0f));
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
            if(LevelPauseLayer.this.mLayerBG.getY() < ((ResourceManager.getInstance().cameraHeight / 2f) + (LevelPauseLayer.this.mLayerBG.getHeight() / 2f))) {
                LevelPauseLayer.this.mLayerBG.setY(Math.min(LevelPauseLayer.this.mLayerBG.getY() + (pSecondsElapsed * mSLIDE_PIXELS_PER_SECONDS), (ResourceManager.getInstance().cameraHeight / 2f) + (LevelPauseLayer.this.mLayerBG.getHeight() / 2f)));
			} else {
				ResourceManager.getInstance().engine.unregisterUpdateHandler(this);
				SceneManager.getInstance().hideLayer();
			  if(LevelPauseLayer.this.mIsGoingBackToLevel) {
					ActivateSound();
					return;
				}
				LevelPauseLayer.this.mCurrentLevel.disposeLevel();
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
		fadableBGRect.setColor(0f, 0f, 0f, 0.6f);
        this.attachChild(fadableBGRect);

        this.mLayerBG = new Sprite(0f, ResourceManager.getInstance().cameraHeight / 2f, MenuResourceManager.layerGeneric, ResourceManager.getActivity().getVertexBufferObjectManager());
        this.attachChild(this.mLayerBG);
        mLayerBG.setAlpha(0);
        this.mLayerBG.setScale(1f);

		Text mMainText = new Text(0f, 0f, ResourceManager.fontDefault60, ResourceManager.getContext().getText(R.string.app_pause), ResourceManager.getActivity().getVertexBufferObjectManager());
		mMainText.setPosition(mLayerBG.getWidth() / 2f, mLayerBG.getHeight() / 2f + 250f);
        mMainText.setScale(2.5f);
        mLayerBG.attachChild(mMainText);

		final GrowButtonExit btnBackToMenu = new GrowButtonExit(mLayerBG.getWidth() / 2f -250, mLayerBG.getHeight() / 2f -250, MenuResourceManager.btnReturnHome) {
			@Override
			public void onClick() {
				LevelPauseLayer.this.mIsGoingBackToLevel = false;
				LevelPauseLayer.this.onHideLayer();
			}
		};
        mLayerBG.attachChild(btnBackToMenu);
		this.registerTouchArea(btnBackToMenu);

        final GrowButtonExit btnBackToLevel = new GrowButtonExit(btnBackToMenu.getX() + 350, btnBackToMenu.getY(), MenuResourceManager.btnPlayGame) {
            @Override
            public void onClick() {
                LevelPauseLayer.this.mIsGoingBackToLevel = true;
                LevelPauseLayer.this.onHideLayer();
            }
        };
        mLayerBG.attachChild(btnBackToLevel);
        this.registerTouchArea(btnBackToLevel);


		this.setPosition(ResourceManager.getInstance().cameraWidth / 2f, ResourceManager.getInstance().cameraHeight / 2f);
	}

	@Override
	public void onShowLayer() {
		//ResourceManager.getActivity().ShowAds();
		ResourceManager.getInstance().engine.registerUpdateHandler(this.mSlideInUpdateHandler);
		this.mIsGoingToRestartLevel = false;
		this.mIsGoingToNextLevel = false;
		this.mIsGoingBackToLevel = true;

		howIsTheSound = SFXManager.isSoundMuted();

		if(!SFXManager.isSoundMuted())
		{
			SFXManager.setSoundMuted(true);
		}

	}
	
	@Override
	public void onUnloadLayer() {}
	
	public void setCurrentLevel(final GameLevel pCurrentLevel) {
		this.mCurrentLevel = pCurrentLevel;
	}
}