package ccx.gamestudio.masterminigolf.Manager;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.opengl.GLES30;

import androidx.annotation.NonNull;

import org.andengine.audio.sound.Sound;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.color.Color;

import java.net.CookieHandler;

import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsBackGround;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsDesertGround;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsGreenGround;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsHoleIOne;
import ccx.gamestudio.masterminigolf.GameObjects.GamePlayers;
import ccx.gamestudio.masterminigolf.Layers.ExitGameLayer;
import ccx.gamestudio.masterminigolf.Layers.FailedLevelLayer;
import ccx.gamestudio.masterminigolf.Layers.LevelPauseLayer;
import ccx.gamestudio.masterminigolf.MasterMiniGolfActivity;
import ccx.gamestudio.masterminigolf.MasterMiniGolfSmoothCamera;
import ccx.gamestudio.masterminigolf.SwitchableFixedStepEngine;


public class ResourceManager {

	//====================================================
	// CONSTANTS
	//====================================================
	private static final ResourceManager INSTANCE = new ResourceManager();


    //====================================================
	// VARIABLES
	//====================================================
	public SwitchableFixedStepEngine engine;
	public Context context;
	public MasterMiniGolfActivity activity;
	public float cameraWidth;
	public float cameraHeight;
	public float cameraScaleFactorX;
	public float cameraScaleFactorY;
	public boolean setMultiTouch;


	public static Sound clickSound;
	public static Font fontDefault24;
	public static Font fontDefault36;
	public static Font fontDefault48;
	public static Font fontDefault60;
	public static Font fontDefault72;



	//====================================================
	// CONSTRUCTOR
	//====================================================
	public ResourceManager(){
	}

	//====================================================
	// GETTERS & SETTERS
	//====================================================
	public static ResourceManager getInstance(){
		return INSTANCE;
	}

	//====================================================
	// PUBLIC METHODS
	//====================================================
	public static void setup(MasterMiniGolfActivity pActivity, SwitchableFixedStepEngine pEngine, Context pContext, float pCameraWidth, float pCameraHeight, float pCameraScaleX, float pCameraScaleY){
		getInstance().activity = pActivity;
		getInstance().engine = pEngine;
		getInstance().context = pContext;
		getInstance().cameraWidth = pCameraWidth;
		getInstance().cameraHeight = pCameraHeight;
		getInstance().cameraScaleFactorX = pCameraScaleX;
		getInstance().cameraScaleFactorY = pCameraScaleY;
	}

	public static void loadMenuResources() {
		getInstance().LoadFonts();
		MenuResourceManager.getInstance().LoadImgMenu();
		LoadGameResources();
		ExitGameLayer.getInstance().onLoadLayer();
        LevelPauseLayer.getInstance().onLoadLayer();
        FailedLevelLayer.getInstance().onLoadLayer();

		/*LevelWonLayer.getInstance().onLoadLayer();
		LevelPauseLayer.getInstance().onLoadLayer();
		OptionsLayer.getInstance().onLoadLayer();
		ExitGameLayer.getInstance().onLoadLayer();
		ShopCoinsAndGemsLayer.getInstance().onLoadLayer();
		WeaponsInGame.getInstance().onLoadLayer();
		ShopTanksLayer.getInstance().onLoadLayer();
		UserTanksLayer.getInstance().onLoadLayer();
		NotGemsGameLayer.getInstance().onLoadLayer();
		FreeCoinsLayer.getInstance().onLoadLayer();
		GiftOfDayLayer.getInstance().onLoadLayer();
		LeaderBoardGameLayer.getInstance().onLoadLayer();
		OptionsNextLevelLayer.getInstance().onLoadLayer();
		DefeatedLayer.getInstance().onLoadLayer();
		FreeGemsLayer.getInstance().onLoadLayer();*/
	}

	public static void LoadGameResources(){
		GameObjectsBackGround.getInstance().LoadObjects();
		GameObjectsGreenGround.getInstance().LoadObjects();
		GameObjectsDesertGround.getInstance().LoadObjects();
        GamePlayers.getInstance().Initialize();
        GameObjectsHoleIOne.getInstance().LoadObjects();
	}

	public static SwitchableFixedStepEngine getEngine()
	{
		return getInstance().engine;
	}

	public static Context getContext()
	{
		return getInstance().context;
	}

	public static MasterMiniGolfActivity getActivity()	{return getInstance().activity;	}

	public static MasterMiniGolfSmoothCamera getCamera()
	{
		return (MasterMiniGolfSmoothCamera) getInstance().engine.getCamera();
	}

	public TextureRegion getLimitableTR(String pTextureRegionPath, TextureOptions pTextureOptions) {
		final IBitmapTextureAtlasSource bitmapTextureAtlasSource = AssetBitmapTextureAtlasSource.create(activity.getAssets(), BitmapTextureAtlasTextureRegionFactory.getAssetBasePath() + pTextureRegionPath);
		final BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), bitmapTextureAtlasSource.getTextureWidth(), bitmapTextureAtlasSource.getTextureHeight(), pTextureOptions);
		final TextureRegion textureRegion = new TextureRegion(bitmapTextureAtlas, 0, 0, bitmapTextureAtlasSource.getTextureWidth(), bitmapTextureAtlasSource.getTextureHeight(), false) {
			@Override
			public void updateUV() {
				super.updateUV();
			}
		};
		bitmapTextureAtlas.addTextureAtlasSource(bitmapTextureAtlasSource, 0, 0);
		bitmapTextureAtlas.load();

		return textureRegion;
	}

	@NonNull
	public TiledTextureRegion getLimitableTTR(String pTiledTextureRegionPath, int pColumns, int pRows, TextureOptions pTextureOptions) {
		final IBitmapTextureAtlasSource bitmapTextureAtlasSource = AssetBitmapTextureAtlasSource.create(activity.getAssets(), BitmapTextureAtlasTextureRegionFactory.getAssetBasePath() + pTiledTextureRegionPath);
		final BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), bitmapTextureAtlasSource.getTextureWidth(), bitmapTextureAtlasSource.getTextureHeight(), pTextureOptions);
		final ITextureRegion[] textureRegions = new ITextureRegion[pColumns * pRows];

		final int tileWidth = bitmapTextureAtlas.getWidth() / pColumns;
		final int tileHeight = bitmapTextureAtlas.getHeight() / pRows;

		for(int tileColumn = 0; tileColumn < pColumns; tileColumn++) {
			for (int tileRow = 0; tileRow < pRows; tileRow++) {
				final int tileIndex = tileRow * pColumns + tileColumn;

				final int x = tileColumn * tileWidth;
				final int y = tileRow * tileHeight;
				textureRegions[tileIndex] = new TextureRegion(bitmapTextureAtlas, x, y, tileWidth, tileHeight, false) {
					@Override
					public void updateUV() {
						super.updateUV();
					}
				};
			}
		}

		final TiledTextureRegion tiledTextureRegion = new TiledTextureRegion(bitmapTextureAtlas, false, textureRegions);
		bitmapTextureAtlas.addTextureAtlasSource(bitmapTextureAtlasSource, 0, 0);
		bitmapTextureAtlas.load();
		return tiledTextureRegion;
	}


	// ============================ LOAD FONTS ========================== //
	private void LoadFonts(){
		if(fontDefault24==null) {
			fontDefault24 = getFont(Typeface.createFromAsset(activity.getAssets(), "Fonts/xFactor.ttf"), 24f, true);
			fontDefault24.load();
		}

		if(fontDefault36==null) {
			fontDefault36= getFont(Typeface.createFromAsset(activity.getAssets(), "Fonts/xFactor.ttf"), 36f, true);
			fontDefault36.load();
		}

		if(fontDefault48==null) {
			fontDefault48 = getFont(Typeface.createFromAsset(activity.getAssets(), "Fonts/xFactor.ttf"), 48f, true);
			fontDefault48.load();
		}

		if(fontDefault60==null) {
			fontDefault60= getFont(Typeface.createFromAsset(activity.getAssets(), "Fonts/xFactor.ttf"), 60f, true);
			fontDefault60.load();
		}

		if(fontDefault72==null) {
			fontDefault72= getFont(Typeface.createFromAsset(activity.getAssets(), "Fonts/xFactor.ttf"), 72f, true);
			fontDefault72.load();
		}

	}

	public Font getFont(Typeface pTypeface, float pSize, boolean pAntiAlias)
	{
		String DEFAULT_CHARS = "ABCDEFGHIÍJKLMNÑOPQRSTUVWXYZabcdefghiíjklmnñopqrstuvwxyz1234567890~`!@#$%^&*()-_=+[] {};:'\",<.>?/\\";
		return getFont(pTypeface, pSize, pAntiAlias, DEFAULT_CHARS);
	}

	public Font getFont(Typeface pTypeface, float pSize, boolean pAntiAlias, String pCharsToUse)
	{
		updateTextBounds(pTypeface,pSize,pAntiAlias,pCharsToUse);
		float fontTextureWidth = (float) (mTextBounds.width() - mTextBounds.left);
		float fontTextureHeight = (float) (mTextBounds.height() - mTextBounds.top);
		int fontTextureRows = Math.round((float) Math.sqrt(fontTextureWidth / fontTextureHeight));
		float FONT_TEXTURE_PADDING_RATIO = 1.04f;
		fontTextureWidth = ((fontTextureWidth / fontTextureRows) * FONT_TEXTURE_PADDING_RATIO);
		fontTextureHeight = ((fontTextureHeight * fontTextureRows) * FONT_TEXTURE_PADDING_RATIO);
		return new Font(engine.getFontManager(), new BitmapTextureAtlas(engine.getTextureManager(), (int) fontTextureWidth, (int) fontTextureHeight, BitmapTextureFormat.RGBA_8888, TextureOptions.DEFAULT), pTypeface, pSize, pAntiAlias, Color.WHITE_ARGB_PACKED_INT);
	}

	private final Rect mTextBounds = new Rect();
	private void updateTextBounds(final Typeface pTypeface, final float pSize, final boolean pAntiAlias, final String pCharacterAsString) {
		Paint mPaint = new Paint();
		mPaint.setTypeface(pTypeface);
		mPaint.setTextSize(pSize);
		mPaint.setAntiAlias(pAntiAlias);
		mPaint.getTextBounds(pCharacterAsString, 0, pCharacterAsString.length(), this.mTextBounds);
	}
}