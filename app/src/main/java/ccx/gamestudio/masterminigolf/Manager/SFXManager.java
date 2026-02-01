package ccx.gamestudio.masterminigolf.Manager;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.util.debug.Debug;

import java.io.IOException;

import ccx.gamestudio.masterminigolf.Helpers.SharedResources;

public class SFXManager
{
	// ====================================================
	// CONSTANTS
	// ====================================================
	private static final SFXManager INSTANCE = new SFXManager();
	
	private static Music mMusicMenu;
	private static Music mMusicGame;
	private static Sound mClick;
	private static Sound mClose;
	private static Sound mCrate;
	private static Sound mExplosion;
	private static Sound mShoot;
	private static Sound mWood;
	private static Sound mTankEngine;
	private static Sound mcoin;
	private static Sound mWaterSplash;
	private static Sound mIntro;
	
	// ====================================================
	// INSTANCE GETTER
	// ====================================================
	public static SFXManager getInstance(){
		return INSTANCE;
	}
	
	// ====================================================
	// VARIABLES
	// ====================================================
	public boolean mSoundsMuted;
	public boolean mMusicMuted;
	public boolean mMusicGameMuted;
	
	// ====================================================
	// CONSTRUCTOR
	// ====================================================
	public SFXManager() {
		MusicFactory.setAssetBasePath("Sounds/");
		try {
			mMusicMenu = MusicFactory.createMusicFromAsset(ResourceManager.getActivity().getMusicManager(), ResourceManager.getActivity(), "MusicMainMenu.mp3");
			mMusicMenu.setLooping(true);
			mMusicGame = MusicFactory.createMusicFromAsset(ResourceManager.getActivity().getMusicManager(), ResourceManager.getActivity(), "MusicGamePlay.mp3");
			mMusicGame.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}

		SoundFactory.setAssetBasePath("Sounds/");
		try {
			mClick = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "click.mp3");
		} catch (final IOException e) { Debug.e(e); }

		try {
			mClose = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "close.mp3");
		} catch (final IOException e) { Debug.e(e); }

		try {
			mCrate = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "crate.mp3");
		} catch (final IOException e) { Debug.e(e); }

		try {
			mExplosion = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "explosion.mp3");
		} catch (final IOException e) { Debug.e(e); }

		try {
			mShoot = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "cannon.mp3");
		} catch (final IOException e) { Debug.e(e); }

		try {
			mWood = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "wood.mp3");
		} catch (final IOException e) { Debug.e(e); }

		try {mTankEngine = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "TankEngine.mp3");
			mTankEngine.setLooping(true);

		} catch (final IOException e) {	Debug.e(e);	}

		try {mcoin = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "coins.mp3");
		} catch (final IOException e) {	Debug.e(e);}

		try {
			mWaterSplash = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "splash.mp3");
		}
		catch (final IOException e) {	Debug.e(e);	}

		try {
			mIntro = SoundFactory.createSoundFromAsset(ResourceManager.getActivity().getSoundManager(), ResourceManager.getActivity(), "IntroGame.mp3");
		}
		catch (final IOException e) { Debug.e(e); }

	}
	
	// ====================================================
	// METHODS
	// ====================================================
	private static void setVolumeForAllSounds(final float pVolume) {
		mClick.setVolume(pVolume);
		mCrate.setVolume(pVolume);
		mExplosion.setVolume(pVolume);
		mShoot.setVolume(pVolume);
		mWood.setVolume(pVolume);
		mTankEngine.setVolume(pVolume);
	}
	
	public static boolean isSoundMuted() {
		return getInstance().mSoundsMuted;
	}
	
	public static void setSoundMuted(final boolean pMuted) {
		getInstance().mSoundsMuted = pMuted;
		setVolumeForAllSounds((getInstance().mSoundsMuted? 0f:1f));
		SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_PREFS_SOUNDS_MUTED, (getInstance().mSoundsMuted? 1:0));
	}
	
	public static boolean toggleSoundMuted() {
		getInstance().mSoundsMuted = !getInstance().mSoundsMuted;
		setVolumeForAllSounds((getInstance().mSoundsMuted? 0f:1f));
		SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_PREFS_SOUNDS_MUTED, (getInstance().mSoundsMuted? 1:0));
		return getInstance().mSoundsMuted;
	}
	
	public static boolean isMusicMuted() {
		return getInstance().mMusicMuted;
	}

	public static boolean isMusicGameMuted() {
		return getInstance().mMusicGameMuted;
	}

	public static void setMusicMuted(final boolean pMuted) {
		getInstance().mMusicMuted = pMuted;
		if(getInstance().mMusicMuted) {
			mMusicMenu.pause();
		}
		else {
			mMusicMenu.play();
		}
		SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_PREFS_MUSIC_MUTED, (getInstance().mMusicMuted? 1:0));
	}

	public static boolean toggleMusicMuted() {
		getInstance().mMusicMuted = !getInstance().mMusicMuted;
		if(getInstance().mMusicMuted) {
			mMusicMenu.pause();
		}
		else {
			mMusicMenu.play();
		}
		SharedResources.writeIntToSharedPreferences(SharedResources.SHARED_PREFS_MUSIC_MUTED, (getInstance().mMusicMuted? 1:0));
		return getInstance().mMusicMuted;
	}


	public static void playMusic() {
		if(!isMusicMuted())
			mMusicMenu.play();
	}

	public static void playMusicGame() {
		if(!isMusicGameMuted())
		{
			float vol = Float.parseFloat(SharedResources.getStringFromSharedPreferences(SharedResources.SHARED_PREFS_MUSIC_GAME_VOLUMEN));
			if(vol != 0.0)
			{
				setMusicGameVolume(vol);
			}else
			{
				setMusicGameVolume(0);
				SharedResources.writeStringToSharedPreferences(SharedResources.SHARED_PREFS_MUSIC_GAME_VOLUMEN, String.valueOf(vol));
			}
			mMusicGame.play();
		}
	}
	
	public static void pauseMusic() {
		mMusicMenu.pause();
	}

	public static void pauseMusicGame() {
		mMusicGame.pause();
	}
	
	public static void resumeMusic() {
		if(!isMusicMuted())
			mMusicMenu.resume();
	}

	public static void resumeMusicGame() {
		if(!isMusicMuted())
			mMusicGame.resume();
	}
	
	public static float getMusicVolume() {
        return mMusicMenu.getVolume();
	}
	
	public static void setMusicVolume(final float pVolume) {
		mMusicMenu.setVolume(pVolume);
	}

	public static void setMusicGameVolume(final float pVolume) {
		mMusicGame.setVolume(pVolume);
	}

	public static void playClick(final float pRate, final float pVolume) {
		playSound(mClick,pRate,pVolume);
	}

	public static void playCloseLayer(final float pRate, final float pVolume) {
		playSound(mClose,pRate,pVolume);
	}
	
	public static void playCrate(final float pRate, final float pVolume) {
		playSound(mCrate,pRate,pVolume);
	}
	
	public static void playExplosion(final float pRate, final float pVolume) {
		playSound(mExplosion,pRate,pVolume);
	}
	
	public static void playShoot(final float pRate, final float pVolume) {
		playSound(mShoot,pRate,pVolume);
	}
	
	public static void playWood(final float pRate, final float pVolume) {
		playSound(mWood,pRate,pVolume);
	}

	public static void playCoin(final float pRate, final float pVolume) {
		playSound(mcoin,pRate,pVolume);
	}

	public static void playWaterSplash(float pRate, float pVolume) {
		playSound(mWaterSplash,pRate,pVolume);
	}

	public static void PlayIntro() {

        mIntro.setVolume(1f);
		mIntro.setRate(1f);
		mIntro.play();
	}

	public static void playTankEngine(final float pVolume, final float pRate) {
		if(!SharedResources.getBooleanFromSharedPreferences(SharedResources.SHARED_PREFS_SOUNDS_ENGINE_TANK)) {
			playSoundTank(mTankEngine,pRate,pVolume);
			SharedResources.writeBooleanToSharedPreferences(SharedResources.SHARED_PREFS_SOUNDS_ENGINE_TANK, true);
		}
	}
	private static void playSoundTank(final Sound pSound, final float pRate, final float pVolume) {
		if (SFXManager.isSoundMuted()) return;
		pSound.setRate(pRate);
		pSound.setVolume(pVolume);
		pSound.onMasterVolumeChanged(1f);
		pSound.setLooping(true);
		pSound.play();
	}
	public static void IncrementTankEngine(final float pVolume, final float pRate) {
		IncrementSoundTank(mTankEngine, pVolume, pRate);
	}

	public static void StopSoundTank() {
		if (SFXManager.isSoundMuted()) return;
		mTankEngine.setRate(0);
		mTankEngine.setVolume(0);
		mTankEngine.setLooping(false);
		mTankEngine.stop();
	}
	private static void playSound(final Sound pSound, final float pRate, final float pVolume) {
		if(SFXManager.isSoundMuted()) return;
		pSound.setRate(pRate);
		pSound.setVolume(pVolume);
		pSound.play();
	}

	private static void IncrementSoundTank(final Sound pSound, final float pVolume, final float pRate) {
		if(SFXManager.isSoundMuted()) return;
		pSound.setVolume(pVolume);
		pSound.setRate(pRate);
	}
}