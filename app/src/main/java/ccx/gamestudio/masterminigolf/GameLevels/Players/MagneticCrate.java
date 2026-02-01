package ccx.gamestudio.masterminigolf.GameLevels.Players;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.math.MathUtils;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameLevels.MagneticPhysObject;
import ccx.gamestudio.masterminigolf.Helpers.SharedResources;
import ccx.gamestudio.masterminigolf.Manager.MenuResourceManager;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.MasterMiniGolfSmoothCamera;


public class MagneticCrate extends MagneticPhysObject<Sprite> {

	// ====================================================
	// CONSTANTS
	// ====================================================;
	private static final float mCRATE_ANGULAR_DAMPING = 0.4f;
	public static float mCRATE_DENSITY = 100;
	private static final float mCRATE_ELASTICITY = 0.0f;
	private static final float mCRATE_FRICTION = 0.95f;
	private static final int mMAX_SOUNDS_PER_SECOND = 5;
	private static final float mMINIMUM_SECONDS_BETWEEN_SOUNDS = 1f / mMAX_SOUNDS_PER_SECOND;
	// ====================================================
	// VARIABLES
	// ====================================================
	public int mBall;

	public TextureRegion mBallCreation;
	private final GameLevel mGameLevel;
	private boolean mHasImpacted = false;
	private float mBodySpeed = 0f;
	private float secondsSinceLastSound = 0.5f;
	private final Sprite mBallSprite;
	private float force = 0;
	private boolean animationDestroy = false;
	private Body crateBody;

	// ====================================================
	// CONSTRUCTOR
	// ====================================================
	public MagneticCrate(float pX, float pY, int pBall, GameLevel pGameLevel, Vector2 pLocationTorret) {
		this.mBall = pBall;
		this.mGameLevel = pGameLevel;

        mBallCreation = MenuResourceManager.mListBall.get(mBall);

		this.mBallSprite = new Sprite(pX, pY, mBallCreation, ResourceManager.getActivity().getVertexBufferObjectManager()) {
			protected void onManagedUpdate(final float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
                float rotation = MathUtils.radToDeg((float) Math.atan2(-MagneticCrate.this.mBody.getLinearVelocity().y, MagneticCrate.this.mBody.getLinearVelocity().x));
                this.setRotation(rotation);

                if (MagneticCrate.this.mBody != null) {
                   // MagneticCrate.this.mGameLevel.reportBaseBodySpeed(MagneticCrate.this.mBody.getLinearVelocity().len2());
                }
            }
		};

        mBallSprite.setPosition(pLocationTorret.x * 32.0f, pLocationTorret.y * 32.0f);
		final FixtureDef mCRATE_FIXTURE_DEF = PhysicsFactory.createFixtureDef(mCRATE_DENSITY, mCRATE_ELASTICITY, mCRATE_FRICTION);
		crateBody = PhysicsFactory.createCircleBody(this.mGameLevel.mPhysicsWorld, mBallSprite.getX(), mBallSprite.getY(), 60, BodyType.DynamicBody, mCRATE_FIXTURE_DEF);

        final PhysicsConnector physConnector = new PhysicsConnector(mBallSprite, crateBody);
		this.mGameLevel.mPhysicsWorld.registerPhysicsConnector(physConnector);

        crateBody.setAngularDamping(mCRATE_ANGULAR_DAMPING);

		this.set(crateBody, mBallSprite, physConnector, this.mGameLevel);

		//this.mGameLevel.mMagneticObjects.add(this);
		this.mIsGrabbed = true;

        this.mGameLevel.mCrateLayer.attachChild(mBallSprite);

		this.mEntity.setScale(1.25f);
		this.mBody.setActive(false);
	}


	// ====================================================
	// METHODS
	// ====================================================
	@Override
	public void onUpdate(float pSecondsElapsed) {
        super.onUpdate(pSecondsElapsed);
        if (this.mEntity.getScaleX() < 1f) {
            this.mEntity.setScale(1f);
        } else if (!this.mBody.isActive()) {

            this.mBody.setActive(true);
            mGameLevel.mPlayer.mTurretMagnetOn = true;

        } else if (this.mEntity.getY() < -350 && !mHasImpacted) {
            mHasImpacted = true;

//            if (SplashWater.getInstance().waterContact) {
//				SplashWater.getInstance().SplashWaterAnimation(this.mEntity.getX(), this.mEntity.getHeight() - 200f, false, true, mGameLevel);
//			}

            //((MasterMiniGolfSmoothCamera) ResourceManager.getEngine().getCamera()).goToMagneTank();

            ResourceManager.getActivity().runOnUpdateThread(() -> mGameLevel.mPlayer.equipNextCrate(false));

            //this.mGameLevel.activeCrates--;
            this.destroy();
        }
        mBodySpeed = 0f;
        if (secondsSinceLastSound < mMINIMUM_SECONDS_BETWEEN_SOUNDS)
            secondsSinceLastSound += pSecondsElapsed;
    }


	@Override
	public void onPreSolve(Contact pContact, Manifold pOldManifold) {
		mBodySpeed = Math.max(this.mBody.getLinearVelocity().len2(),mBodySpeed);
	}

	@Override
	public void onPostSolve(float pMaxImpulse) {
        if (!this.mIsGrabbed) {
            if (this.mEntity != null) {
                if (pMaxImpulse > 2f) {
                    //this.mGameLevel.mMagneticObjects.remove(this);

                    if (this.mGameLevel.mPlayer.mGrabbedMagneticObject == this)
                        this.mGameLevel.mPlayer.mGrabbedMagneticObject = null;

                    this.destroy();

                }
            }
        }
    }

	@Override
	public void onBeginContact(Contact pContact) {
		if (!this.mIsGrabbed) {
			if (this.mEntity != null) {
				if (!mHasImpacted) {
					mHasImpacted = true;
					((MasterMiniGolfSmoothCamera) ResourceManager.getEngine().getCamera()).goToBaseForSeconds(0.5f);
                      ResourceManager.getActivity().runOnUpdateThread(() -> mGameLevel.mPlayer.equipNextCrate(false));
				}
			}
        }
	}

	@Override
	public void onEndContact(Contact pContact) {
	}

}