package ccx.gamestudio.masterminigolf.GameLevels;


import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;

import ccx.gamestudio.masterminigolf.GameLevels.Players.Players;
import ccx.gamestudio.masterminigolf.GameObjects.GamePlayers;
import ccx.gamestudio.masterminigolf.Manager.GameManager;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class BouncingPowerBar extends Entity {
	
	// ====================================================
	// CONSTANTS
	// ====================================================
	private static final BouncingPowerBar INSTANCE = new BouncingPowerBar();
	private static final float mLINE_SPEED = 1.35f;
	private static final float mLINE_MIN_Y = 17f;
	private static final float mLINE_MAX_Y = 239f;
	private static final float mLINE_RANGE = mLINE_MAX_Y - mLINE_MIN_Y;
    private static final float mLINE_POSITION_X = 32f;
	private static final float mLINE_POSITION_Y = 17f;
	
	// ====================================================
	// VARIABLES
	// ====================================================
	private Players mPlayer;
	private boolean mIsLineMovingUp = true;
	public static Sprite mBackGround;
	private static Sprite mLINE;

	// ====================================================
	// CONSTRUCTOR
	// ====================================================
	private BouncingPowerBar() {
        float mBACKGROUND_POSITION_X = ResourceManager.getInstance().cameraWidth - GamePlayers.mPowerBarBackground.getWidth();
        float mBACKGROUND_POSITION_Y = ResourceManager.getInstance().cameraHeight / 2f - GamePlayers.mPowerBarBackground.getHeight();

        mBackGround = new Sprite(mBACKGROUND_POSITION_X ,  mBACKGROUND_POSITION_Y ,  GamePlayers.mPowerBarBackground, ResourceManager.getActivity().getVertexBufferObjectManager());
        mBackGround.setAlpha(0.8f);
        mLINE = new Sprite(mLINE_POSITION_X, mLINE_POSITION_Y, GamePlayers.mPowerBarLine, ResourceManager.getActivity().getVertexBufferObjectManager());
        mLINE.setScale(0.25f);

        attachChild(BouncingPowerBar.mBackGround);
        mBackGround.attachChild(mLINE);
        mPlayer = GameManager.getGameLevel().mPlayer;
    }
	
	// ====================================================
	// METHODS
	// ====================================================
	public static void attachInstanceToHud(final HUD pHud) {
		INSTANCE.mPlayer = GameManager.getGameLevel().mPlayer;
		if(INSTANCE.hasParent())
			INSTANCE.detachSelf();
		pHud.attachChild(INSTANCE);
	}
	
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		if(mPlayer.mTurretMagnetOn) {
			if(mIsLineMovingUp) {
				mLINE.setY(mLINE.getY()+(pSecondsElapsed * mLINE_SPEED * mLINE_RANGE));
				if(mLINE.getY()>mLINE_MAX_Y) {
					mIsLineMovingUp = false;
					mLINE.setY(mLINE_MAX_Y - (mLINE.getY() - mLINE_MAX_Y));
				}
			} else {
				mLINE.setY(mLINE.getY()-(pSecondsElapsed * mLINE_SPEED * mLINE_RANGE));
				if(mLINE.getY()<mLINE_MIN_Y) {
					mIsLineMovingUp = true;
					mLINE.setY(mLINE_MIN_Y + (mLINE_MIN_Y - mLINE.getY()));
				}
			}
			
			// turn the power level from a linear slope into a cubic slope.
			final float PercentageOfRange = ((mLINE.getY()-mLINE_MIN_Y)/mLINE_RANGE);
			final float PercentageOfPower = (float) Math.pow(PercentageOfRange, 3);
            mPlayer.mShootingPower = ((Players.mSHOOTING_POWER_MAXIMUM - Players.mSHOOTING_POWER_MINIMUM) * PercentageOfPower) + Players.mSHOOTING_POWER_MINIMUM;
		} else {
			mIsLineMovingUp = true;
			if(mLINE.getY()!=mLINE_MIN_Y)
				mLINE.setY(mLINE_MIN_Y);
		}
		super.onManagedUpdate(pSecondsElapsed);
	}
}