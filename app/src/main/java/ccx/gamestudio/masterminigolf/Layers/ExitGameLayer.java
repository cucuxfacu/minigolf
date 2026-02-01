package ccx.gamestudio.masterminigolf.Layers;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import ccx.gamestudio.masterminigolf.Input.GrowButton;
import ccx.gamestudio.masterminigolf.Manager.MenuResourceManager;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.Manager.SceneManager;
import ccx.gamestudio.masterminigolf.R;

public class ExitGameLayer extends ManagedLayer {
	
	// ====================================================
	// CONSTANTS
	// ====================================================
	private static final ExitGameLayer INSTANCE = new ExitGameLayer();
	
	// ====================================================
	// INSTANCE GETTER
	// ====================================================
	public static ExitGameLayer getInstance() {
		return INSTANCE;
	}
	
	// ====================================================
	// VARIABLES
	// ====================================================
	private Sprite LayerBG;
	private Text TitleText;
		private Text AreYousure;
	private GrowButton btnBack;
	private GrowButton btnExit;

	// ====================================================
	// UPDATE HANDLERS
	// ====================================================
	// Animates the layer to slide in from the top.
	IUpdateHandler mSlideInUpdateHandler = new IUpdateHandler() {
		@Override
		public void onUpdate(final float pSecondsElapsed) {
			if(ExitGameLayer.this.LayerBG.getY() > 0f) {
				ExitGameLayer.this.LayerBG.setY(Math.max(ExitGameLayer.this.LayerBG.getY() - (pSecondsElapsed * mSLIDE_PIXELS_PER_SECONDS), 0f));
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
			if(ExitGameLayer.this.LayerBG.getY() < ((ResourceManager.getInstance().cameraHeight / 2f) + (ExitGameLayer.this.LayerBG.getHeight() / 2f))) {
				ExitGameLayer.this.LayerBG.setY(Math.min(ExitGameLayer.this.LayerBG.getY() + (pSecondsElapsed * mSLIDE_PIXELS_PER_SECONDS), (ResourceManager.getInstance().cameraHeight / 2f) + (ExitGameLayer.this.LayerBG.getHeight() / 2f)));
			} else {
				ResourceManager.getInstance().engine.unregisterUpdateHandler(this);
				SceneManager.getInstance().hideLayer();
			}
		}
		
		@Override
		public void reset() {}
	};
	
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
		
		final Rectangle fadableBGRect = new Rectangle(0f, 0f, ResourceManager.getInstance().cameraWidth, ResourceManager.getInstance().cameraHeight, ResourceManager.getActivity().getVertexBufferObjectManager());
		fadableBGRect.setColor(0f, 0f, 0f, 0.6f);
		this.attachChild(fadableBGRect);
		
		this.attachChild(this.LayerBG = new Sprite(0f, (ResourceManager.getInstance().cameraHeight / 2f) + (MenuResourceManager.layerGeneric.getHeight() / 2f), MenuResourceManager.layerGeneric, ResourceManager.getActivity().getVertexBufferObjectManager()));
		this.LayerBG.setScale(1f);

		this.TitleText = new Text(0f, 0f, ResourceManager.fontDefault60, ResourceManager.getContext().getText(R.string.app_exitgame), ResourceManager.getActivity().getVertexBufferObjectManager());
		this.TitleText.setScale(0.85f);
		this.TitleText.setPosition(LayerBG.getWidth() / 2f , LayerBG.getHeight() / 2f + 250f);
		this.LayerBG.attachChild(this.TitleText);

		this.AreYousure = new Text(0f, 0f,ResourceManager.fontDefault60, ResourceManager.getContext().getText(R.string.app_areyousure), ResourceManager.getActivity().getVertexBufferObjectManager());
		this.AreYousure.setScale(1.25f);
		this.AreYousure.setColor(0.212f, 0.275f, 0.212f);
		this.AreYousure.setPosition(LayerBG.getWidth() / 2f, LayerBG.getHeight() / 2f);
		this.LayerBG.attachChild(this.AreYousure);

		this.btnBack = new GrowButton(LayerBG.getWidth() / 2f + 200f, LayerBG.getHeight() / 2f - 250f, MenuResourceManager.bntExitInLayer) {
			@Override
			public void onClick() {
				ExitGameLayer.this.onHideLayer();
			}
		};
		this.LayerBG.attachChild(btnBack);
		this.registerTouchArea(btnBack);


		this.btnExit = new GrowButton(LayerBG.getWidth() / 2f + -200f, LayerBG.getHeight() / 2f -250f, MenuResourceManager.bntAcceptInLayer) {
			@Override
			public void onClick() {
				System.exit(0);
			}
		};
		this.LayerBG.attachChild(btnExit);
		this.registerTouchArea(btnExit);

		this.setPosition(ResourceManager.getInstance().cameraWidth / 2f, ResourceManager.getInstance().cameraHeight / 2f);
	}
	
	@Override
	public void onShowLayer() {
		ResourceManager.getInstance().engine.registerUpdateHandler(this.mSlideInUpdateHandler);
	}
	
	@Override
	public void onUnloadLayer() {
		
	}
}