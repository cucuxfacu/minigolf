package ccx.gamestudio.masterminigolf.Menus;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;


import ccx.gamestudio.masterminigolf.Helpers.SharedResources;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.Manager.SFXManager;
import ccx.gamestudio.masterminigolf.Manager.SceneManager;

public class SplashScreens extends ManagedSplashScreen {
	// ====================================================
	// CONSTANTS
	// ====================================================
	private static float pDuration = 1f;
	private static float pFromAlpha = 0f;
	private static float pToAlpha = 1f;
	private static final float mEachAnimationDuration = 0.25f;

	private static final BitmapTextureAtlas mCcxLogoTexture = new BitmapTextureAtlas(ResourceManager.getEngine().getTextureManager(), 768, 768, TextureOptions.BILINEAR);
	private static final ITextureRegion mCxxLogoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mCcxLogoTexture, ResourceManager.getContext(), "MasterGolf/Splash/ccxgame_Logo.png", 0, 0);
	private static final Sprite mCcxLogoSprite = new Sprite(ResourceManager.getInstance().cameraWidth / 2f, 0f, mCxxLogoTextureRegion, ResourceManager.getEngine().getVertexBufferObjectManager());

	private static final BitmapTextureAtlas mLogoTexture = new BitmapTextureAtlas(ResourceManager.getEngine().getTextureManager(), 1024, 472, TextureOptions.BILINEAR);
	private static final ITextureRegion mLogoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mLogoTexture, ResourceManager.getContext(), "MasterGolf/Splash/start.png", 0, 0);
	private static final Sprite mLogoSprite = new Sprite(ResourceManager.getInstance().cameraWidth / 2f, 0f, mLogoTextureRegion, ResourceManager.getEngine().getVertexBufferObjectManager());

	private static final SequenceEntityModifier mCcxGamestudioLogo_SequenceEntityModifier =
			new SequenceEntityModifier(
					new ParallelEntityModifier(
							new AlphaModifier(pDuration, pFromAlpha, pToAlpha),
							new FadeInModifier(mEachAnimationDuration)),
					new DelayModifier(2f),
					new ParallelEntityModifier(
							new AlphaModifier(pDuration, pToAlpha, pFromAlpha),
							new FadeOutModifier(mEachAnimationDuration)));

	private static final SequenceEntityModifier mTankLogo_SequenceEntityModifier =
			new SequenceEntityModifier(
					new ParallelEntityModifier(
							new AlphaModifier(pDuration, pFromAlpha, pToAlpha),
							new FadeInModifier(mEachAnimationDuration)),
					new DelayModifier(2f),
					new ParallelEntityModifier(
							new AlphaModifier(pDuration, pToAlpha, pFromAlpha),
							new FadeOutModifier(mEachAnimationDuration)));

	// ====================================================
	// METHODS
	// ====================================================
	@Override
	public void onLoadScene() {
		mCcxLogoTexture.load();
		mLogoTexture.load();
		ResourceManager.getCamera().setCenterDirect(ResourceManager.getInstance().cameraWidth / 2f, ResourceManager.getInstance().cameraHeight / 2f);

		this.setBackgroundEnabled(true);
		this.setBackground(new Background(255f, 255f, 255f));

		mCcxLogoSprite.setAlpha(0.001f);
		this.attachChild(mCcxLogoSprite);
		mLogoSprite.setAlpha(0.001f);
		mLogoSprite.setScale(SharedResources.SCALE_BACKGROUND_MENU);
		this.attachChild(mLogoSprite);

		mCcxGamestudioLogo_SequenceEntityModifier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(final IModifier<IEntity> pModifier, final IEntity pItem) {
				mLogoSprite.registerEntityModifier(SplashScreens.mTankLogo_SequenceEntityModifier);
			}

			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				SFXManager.PlayIntro();
			}
		});

		mTankLogo_SequenceEntityModifier.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierFinished(final IModifier<IEntity> pModifier, final IEntity pItem) {
				//InAppConsent.getInstance().GetCosent();
				SceneManager.getInstance().showMainMenu();
			}

			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				if (SharedResources.getStringFromSharedPreferences(SharedResources.SHARED_PREFS_MUSIC_VOLUMEN).equals("0")) {
					SharedResources.writeStringToSharedPreferences(SharedResources.SHARED_PREFS_MUSIC_VOLUMEN, "1");
				}

				SFXManager.playMusic();

				if (SharedResources.getIntFromSharedPreferences(SharedResources.SHARED_PREFS_MUSIC_MUTED) > 0)
					SFXManager.setMusicMuted(true);
				if (SharedResources.getIntFromSharedPreferences(SharedResources.SHARED_PREFS_SOUNDS_MUTED) > 0)
					SFXManager.setSoundMuted(true);
			}
		});

		this.registerUpdateHandler(new IUpdateHandler() {
			int counter = 0;
			@Override
			public void onUpdate(final float pSecondsElapsed) {
				this.counter++;
				if (this.counter > 2) {
					mCcxLogoSprite.registerEntityModifier(mCcxGamestudioLogo_SequenceEntityModifier);
					thisManagedSplashScene.unregisterUpdateHandler(this);
				}
			}

			@Override
			public void reset() {
			}
		});
	}

	@Override
	public void unloadSplashTextures() {
		mCcxLogoTexture.unload();
		mLogoTexture.unload();
	}
}