package ccx.gamestudio.masterminigolf.GameLevels.Players;

import static org.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.debugdraw.DebugRenderer;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.math.MathUtils;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameLevels.MagneticPhysObject;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.HoleInOne.Hole;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.SplashWater;
import ccx.gamestudio.masterminigolf.GameLevels.WorldOne.GreenLevelOne;
import ccx.gamestudio.masterminigolf.GameObjects.GamePlayers;
import ccx.gamestudio.masterminigolf.Helpers.SharedResources;
import ccx.gamestudio.masterminigolf.Input.BoundTouchInput;
import ccx.gamestudio.masterminigolf.Manager.MenuResourceManager;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.Manager.SFXManager;


public class Players implements IUpdateHandler  {

	// ====================================================
	// CONSTANTS
	// ====================================================
    public static final float mSHOOTING_POWER_MINIMUM = 40f;
    public static final float mSHOOTING_POWER_MAXIMUM = 100f;
	private static final float mCAMERA_LOWER_BOUND_FROM_VEHICLE_X = -250f;
	private static final float mCAMERA_LOWER_BOUND_FROM_VEHICLE_Y = -400f;
	private static final float mTURRET_SHOOT_GRABBED_OBJECT_IF_WITHIN_RANGE = 30f;
	private static final float mTURRET_ROTATION_DRAG = 4f;
	private static final float mTURRET_SPRITE_OFFSET_FROM_VEHICLE_CENTER_X = 20;
	private static final float mTURRET_SPRITE_OFFSET_FROM_VEHICLE_CENTER_Y = -120f;
	private static final float mTURRET_JOINT_LOWER_ANGLE = 0.2285f;
	private static final float mTURRET_JOINT_UPPER_ANGLE = 1.3f;
    private static final float mTURRET_BODY_CENTER_OF_GRAVITY_X = -20f;
    private static final float mTURRET_BODY_CENTER_OF_GRAVITY_Y = 0f;
	private static final float mTURRET_JOINT_MAX_MOTOR_TORQUE = 5000000000f;
    private static final float mMAGNET_OFFSET_FROM_TURRET_BODY_CENTER_X = 1.5f;
    private static final float mMAGNET_OFFSET_FROM_TURRET_BODY_CENTER_Y = 0f;
	private static final float mPLAYER_DENSITY = 300f;
	private static final float mPLAYER_ELASTICITY = 0.1f;
	private static final float mPLAYER_FRICTION = 0.5f;
    private static final float mTURRET_DENSITY = 1f;
    private static final float mTURRET_ELASTICITY = 0.1f;
    private static final float mTURRET_FRICTION = 0.5f;
	private static final FixtureDef mVEHICLE_FIXTURE_DEF = PhysicsFactory.createFixtureDef(mPLAYER_DENSITY, mPLAYER_ELASTICITY, mPLAYER_FRICTION);
	private static final FixtureDef mTURRET_FIXTURE_DEF = PhysicsFactory.createFixtureDef(mTURRET_DENSITY, mTURRET_ELASTICITY, mTURRET_FRICTION);

	private static final Vector2 mVECTOR2_ZERO = new Vector2(0f, 0f);
	private static final Vector2 mVECTOR2_VERTICAL_AXIS = new Vector2(0f, 1f);


	// ====================================================
	// VARIABLES
	// ====================================================
	public final GameLevel mGameLevel;
	public float mShootingPower;
	public final Sprite mPlayer;
	public boolean mTurretMagnetOn = true;
	public MagneticPhysObject<?> mGrabbedMagneticObject;
    public final Sprite mSensorShoot;
	public final Body mPlayerBody;
	private final Body mSensorBody;
	private final RevoluteJoint mTurretRevoluteJoint;
	private float mTurretDesiredAngle;
	private final float mCHARACTER_START_X;
	private final float mCHARACTER_START_Y;
	private AnimatedSprite mAnimatedPlayers;
    public Sprite mBall;
    private Hole currentHole;
    private GreenLevelOne currentGreen;




    // ====================================================
	// BOUND TOUCH INPUTS
	// ====================================================

	private final BoundTouchInput mTurretMovementBoundTouchInput = new BoundTouchInput() {
		@Override
		protected boolean onActionDown(final float pX, final float pY) {

			return true;
		}

		@Override
		protected void onActionMove(final float pX, final float pY, final float pPreviousX, final float pPreviousY, final float pStartX, final float pStartY) {

		}

		@Override
		protected void onActionUp(final float pX, final float pY, final float pPreviousX, final float pPreviousY, final float pStartX, final float pStartY) {

		}
	};
	private final BoundTouchInput mTurretShootingBoundTouchInput = new BoundTouchInput() {
		@Override
		protected boolean onActionDown(final float pX, final float pY) {
			return true;
		}

		@Override
		protected void onActionMove(final float pX, final float pY, final float pPreviousX, final float pPreviousY, final float pStartX, final float pStartY) {}

		@Override
		protected void onActionUp(final float pX, final float pY, final float pPreviousX, final float pPreviousY, final float pStartX, final float pStartY) {}
	};

	// ====================================================
	// CONSTRUCTOR
	// ====================================================
	public Players(final float pX, final float pY, final GameLevel pGameLevel) {
        this.mCHARACTER_START_X = pX;
        this.mCHARACTER_START_Y = pY;
        this.mGameLevel = pGameLevel;

        this.mPlayer = new Sprite(this.mCHARACTER_START_X, this.mCHARACTER_START_Y, MenuResourceManager.mPlayers.get(0), ResourceManager.getActivity().getVertexBufferObjectManager()) {
            @Override
            public void setPosition(final float pX, final float pY) {

                super.setPosition(pX, pY);
            }
        };

        mSensorShoot = new Sprite(this.mPlayer.getX() + mTURRET_SPRITE_OFFSET_FROM_VEHICLE_CENTER_X, this.mPlayer.getY() + mTURRET_SPRITE_OFFSET_FROM_VEHICLE_CENTER_Y, GamePlayers.mSensorShoot, ResourceManager.getActivity().getVertexBufferObjectManager());
        mSensorShoot.setWidth(0.05f);
        mSensorShoot.setAlpha(0f);
        Sprite mArrowShoot = new Sprite(mSensorShoot.getX() + mTURRET_SPRITE_OFFSET_FROM_VEHICLE_CENTER_X + 150f, 37, GamePlayers.mArrowShoot, ResourceManager.getActivity().getVertexBufferObjectManager());
        mArrowShoot.setScale(0.5f);
        mArrowShoot.setAlpha(0.75f);
        mSensorShoot.attachChild(mArrowShoot);

        this.mGameLevel.attachChild(this.mPlayer);
        this.mGameLevel.attachChild(this.mSensorShoot);

        this.mPlayerBody = PhysicsFactory.createBoxBody(this.mGameLevel.mPhysicsWorld, this.mPlayer.getX(), this.mPlayer.getY(), this.mPlayer.getWidth()-150f, this.mPlayer.getHeight()-250f, BodyType.StaticBody, mVEHICLE_FIXTURE_DEF);
        this.mGameLevel.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this.mPlayer, this.mPlayerBody));

        mTURRET_FIXTURE_DEF.isSensor = true;
        this.mSensorBody = PhysicsFactory.createBoxBody(this.mGameLevel.mPhysicsWorld, this.mSensorShoot, BodyType.DynamicBody, mTURRET_FIXTURE_DEF);
        this.mSensorBody.setSleepingAllowed(false);

        this.mGameLevel.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this.mSensorShoot, this.mSensorBody));

        Vector2 turretLocalPivot = new Vector2(mTURRET_BODY_CENTER_OF_GRAVITY_X / PIXEL_TO_METER_RATIO_DEFAULT,mTURRET_BODY_CENTER_OF_GRAVITY_Y / PIXEL_TO_METER_RATIO_DEFAULT);
        Vector2 turretWorldPivot = this.mSensorBody.getWorldPoint(turretLocalPivot);

        final RevoluteJointDef magneTankTurretRevoluteJointDef = new RevoluteJointDef();
        magneTankTurretRevoluteJointDef.initialize(this.mPlayerBody, this.mSensorBody, turretWorldPivot);
        magneTankTurretRevoluteJointDef.enableMotor = true;
        magneTankTurretRevoluteJointDef.maxMotorTorque = mTURRET_JOINT_MAX_MOTOR_TORQUE;
        magneTankTurretRevoluteJointDef.enableLimit = true;
        magneTankTurretRevoluteJointDef.lowerAngle = mTURRET_JOINT_LOWER_ANGLE;
        magneTankTurretRevoluteJointDef.upperAngle = mTURRET_JOINT_UPPER_ANGLE;
        this.mTurretRevoluteJoint = (RevoluteJoint) this.mGameLevel.mPhysicsWorld.createJoint(magneTankTurretRevoluteJointDef);

        this.mGameLevel.registerUpdateHandler(this);


    }


	// ====================================================
	// METHODS
	// ===================================================
	public void onMagneTankTouchEvent(Scene pScene, final TouchEvent pSceneTouchEvent) {
		if (!this.mTurretMovementBoundTouchInput.onTouch(pSceneTouchEvent)) {
			this.mTurretShootingBoundTouchInput.onTouch(pSceneTouchEvent);
		}
	}

	@Override
	public void onUpdate(final float pSecondsElapsed) {
		ResourceManager.getCamera().setBounds(this.mPlayer.getX() + mCAMERA_LOWER_BOUND_FROM_VEHICLE_X, this.mPlayer.getY() + mCAMERA_LOWER_BOUND_FROM_VEHICLE_Y, GameLevel.mLEVEL_WIDTH, Float.MAX_VALUE);

		if(this.mTurretRevoluteJoint.getJointAngle() < this.mTurretDesiredAngle) {
			if(this.mTurretDesiredAngle >= this.mTurretRevoluteJoint.getUpperLimit()) {
				this.mSensorBody.setTransform(this.mSensorBody.getWorldPoint(mVECTOR2_ZERO), this.mTurretRevoluteJoint.getUpperLimit());
			} else {
				this.mSensorBody.setTransform(this.mSensorBody.getWorldPoint(mVECTOR2_ZERO), this.mSensorBody.getAngle() + ((this.mTurretDesiredAngle - this.mTurretRevoluteJoint.getJointAngle()) / mTURRET_ROTATION_DRAG));
			}
		} else if(this.mTurretRevoluteJoint.getJointAngle() > this.mTurretDesiredAngle) {
			if(this.mTurretDesiredAngle <= this.mTurretRevoluteJoint.getLowerLimit()) {
				this.mSensorBody.setTransform(this.mSensorBody.getWorldPoint(mVECTOR2_ZERO), this.mTurretRevoluteJoint.getLowerLimit());
			} else {
				this.mSensorBody.setTransform(this.mSensorBody.getWorldPoint(mVECTOR2_ZERO), this.mSensorBody.getAngle() + ((this.mTurretDesiredAngle - this.mTurretRevoluteJoint.getJointAngle()) / mTURRET_ROTATION_DRAG));
			}
		}

		if(this.mGrabbedMagneticObject != null) {
			final Vector2 newDesiredXY = Vector2Pool.obtain(mMAGNET_OFFSET_FROM_TURRET_BODY_CENTER_X, mMAGNET_OFFSET_FROM_TURRET_BODY_CENTER_Y);
			this.mGrabbedMagneticObject.mDesiredXY = this.mSensorBody.getWorldPoint(newDesiredXY);
			Vector2Pool.recycle(newDesiredXY);
		}
    }



	public void equipNextBall() {
        final Vector2 NewCrateSpawnLocation = getWorldPoint();
        mGrabbedMagneticObject = null;
        this.mGrabbedMagneticObject = new MagneticCrate(
                NewCrateSpawnLocation.x ,
                NewCrateSpawnLocation.y,
                0,
                this.mGameLevel,
                NewCrateSpawnLocation
        );
        this.mGrabbedMagneticObject.mDesiredXY = NewCrateSpawnLocation;
        mGameLevel.sortChildren();
    }
	
	public void requestShoot() {
        mGameLevel.btnShoot.mIsEnabled = false;
        if (mTurretMagnetOn) {
            mTurretMagnetOn = false;
            AnimationSwing();
        }
    }
	private void AnimationSwing() {
        AnimatedSprite animPlayer = new AnimatedSprite(this.mCHARACTER_START_X - 10f, this.mCHARACTER_START_Y + 23f, GamePlayers.mLisPlayer.get(0), ResourceManager.getActivity().getVertexBufferObjectManager());
        animPlayer.setZIndex(999);
        animPlayer.animate(new long[]{240,240,240}, 1,3,true, new AnimatedSprite.IAnimationListener() {
            @Override
            public void onAnimationFinished(final AnimatedSprite pAnimatedSprite) {
            }
            @Override
            public void onAnimationFrameChanged(final AnimatedSprite pAnimatedSprite, final int pOldFrameIndex, final int pNewFrameIndex) {
                if (pNewFrameIndex == 2) {
                    SFXManager.playShoot(1f, 2f);
                    equipNextBall();
                    if (mGrabbedMagneticObject != null) {
                        mBall.setVisible(false);
                        mGrabbedMagneticObject.release();
                        mGrabbedMagneticObject.mIsShot = true;
                       //ResourceManager.getCamera().setChaseEntity(mGrabbedMagneticObject.mEntity);

                        mGrabbedMagneticObject.mBody.setLinearVelocity(0f, 0f);
                        final float vPower = ((MathUtils.distance(mSensorBody.getWorldCenter().x, mSensorBody.getWorldCenter().y, mGrabbedMagneticObject.mBody.getWorldCenter().x, mGrabbedMagneticObject.mBody.getWorldCenter().y) < mTURRET_SHOOT_GRABBED_OBJECT_IF_WITHIN_RANGE) ? mShootingPower : 0f);
                        final float vAng = (float) Math.atan2(mGrabbedMagneticObject.mBody.getWorldCenter().y - mSensorBody.getWorldCenter().y, mGrabbedMagneticObject.mBody.getWorldCenter().x - mSensorBody.getWorldCenter().x);

                        mGrabbedMagneticObject.mBody.applyLinearImpulse((float) Math.cos(vAng) * vPower * mGrabbedMagneticObject.mBody.getMass(), (float) Math.sin(vAng) * vPower * mGrabbedMagneticObject.mBody.getMass(), mGrabbedMagneticObject.mBody.getWorldCenter().x, mGrabbedMagneticObject.mBody.getWorldCenter().y);

                        mGameLevel.resetTrailingDots();
                    }
                }

            }
            @Override
            public void onAnimationLoopFinished(final AnimatedSprite pAnimatedSprite, final int pRemainingLoopCount, final int pInitialLoopCount) {
                pAnimatedSprite.stopAnimation();
                ResourceManager.getActivity().runOnUpdateThread(() -> {
                    pAnimatedSprite.detachSelf();
                    if(!pAnimatedSprite.isDisposed()) {
                        mPlayer.setVisible(true);
                    }
                });
            }

            @Override
            public void onAnimationStarted(final AnimatedSprite pAnimatedSprite, final int pInitialLoopCount) {
                mPlayer.setVisible(false);
            }
        });
        mGameLevel.attachChild(animPlayer);
	}

    @Override
	public void reset() {}
	
	public void setMangeTankTurretDesiredAngle(final float pAngle) {
		this.mTurretDesiredAngle = MathUtils.bringToBounds(mTURRET_JOINT_LOWER_ANGLE, mTURRET_JOINT_UPPER_ANGLE, pAngle - this.mPlayerBody.getAngle());
	}


    public void rotateTurretUp() {
        float newAngle = this.mTurretDesiredAngle + 0.008f;
        this.mTurretDesiredAngle = MathUtils.bringToBounds(
                mTURRET_JOINT_LOWER_ANGLE,
                mTURRET_JOINT_UPPER_ANGLE,
                newAngle
        );

    }

    public void rotateTurretDown() {
        float newAngle = this.mTurretDesiredAngle - 0.008f;
        this.mTurretDesiredAngle = MathUtils.bringToBounds(
                mTURRET_JOINT_LOWER_ANGLE,
                mTURRET_JOINT_UPPER_ANGLE,
                newAngle
        );
    }


    public void createBall() {
        this.mBall = new Sprite(0, 0, MenuResourceManager.mListBall.get(0), ResourceManager.getActivity().getVertexBufferObjectManager());
        mBall.setScale(0.08f);
        mBall.setPosition(  this.mPlayer.getX() + mTURRET_SPRITE_OFFSET_FROM_VEHICLE_CENTER_X - 10f, this.mPlayer.getY() + mTURRET_SPRITE_OFFSET_FROM_VEHICLE_CENTER_Y);
        this.mGameLevel.attachChild(mBall);
    }

    public Vector2 getWorldPoint() {
        return this.mSensorBody.getWorldPoint(new Vector2(mMAGNET_OFFSET_FROM_TURRET_BODY_CENTER_X, mMAGNET_OFFSET_FROM_TURRET_BODY_CENTER_Y));
    }

}