package ccx.gamestudio.masterminigolf.GameLevels.Players;


import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.math.MathUtils;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameLevels.MagneticPhysObject;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.SplashWater;
import ccx.gamestudio.masterminigolf.Layers.FailedLevelLayer;
import ccx.gamestudio.masterminigolf.Manager.MenuResourceManager;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.Manager.SFXManager;
import ccx.gamestudio.masterminigolf.Manager.SceneManager;
import ccx.gamestudio.masterminigolf.MasterMiniGolfSmoothCamera;


public class MagneticCrate extends MagneticPhysObject<Sprite> {

    // ====================================================
    // CONSTANTS
    // ====================================================;
    private static final float mCRATE_ANGULAR_DAMPING = 0.4f;
    public static float mCRATE_DENSITY = 100;
    private static final float mCRATE_ELASTICITY = 0.2f;
    private static final float mCRATE_FRICTION = 0.2f;
    private static final int mMAX_SOUNDS_PER_SECOND = 5;
    private static final float mMINIMUM_SECONDS_BETWEEN_SOUNDS = 1f / mMAX_SOUNDS_PER_SECOND;
    private static final float mScaleBall = 0.08f;

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
    private Body crateBody;

    private boolean mOnPostSolve;

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
                    MagneticCrate.this.mGameLevel.reportBaseBodySpeed(MagneticCrate.this.mBody.getLinearVelocity().len2());
                }
            }
        };

        mBallSprite.setScale(mScaleBall);
        mBallSprite.setPosition(pLocationTorret.x * 32, pLocationTorret.y * 32);

        final FixtureDef mCRATE_FIXTURE_DEF = PhysicsFactory.createFixtureDef(mCRATE_DENSITY, mCRATE_ELASTICITY, mCRATE_FRICTION);
        crateBody = PhysicsFactory.createCircleBody(this.mGameLevel.mPhysicsWorld, mBallSprite, BodyType.DynamicBody, mCRATE_FIXTURE_DEF);
        final PhysicsConnector physConnector = new PhysicsConnector(mBallSprite, crateBody);
        this.mGameLevel.mPhysicsWorld.registerPhysicsConnector(physConnector);
        crateBody.setAngularDamping(mCRATE_ANGULAR_DAMPING);
        this.set(crateBody, mBallSprite, physConnector, this.mGameLevel);
        this.mGameLevel.mMagneticObjects.add(this);

        this.mGameLevel.mCrateLayer.attachChild(mBallSprite);
        this.mGameLevel.mCrateLayer.setZIndex(998);
        this.mEntity.setScale(mScaleBall);
        this.mIsGrabbed = true;
        this.mBody.setActive(false);
    }


    // ====================================================
    // METHODS
    // ====================================================
    @Override
    public void onUpdate(float pSecondsElapsed) {
        super.onUpdate(pSecondsElapsed);
        if (!mHasImpacted) {
            final Sprite lastTrailingDot = mGameLevel.getLastTrailingDot();
            if (MathUtils.distance(lastTrailingDot.getX(), lastTrailingDot.getY(), MagneticCrate.this.mEntity.getX(), MagneticCrate.this.mEntity.getY()) > GameLevel.mTRAILING_DOTS_SPACING) {
                mGameLevel.setNextTrailingDot(MagneticCrate.this.mEntity.getX(), MagneticCrate.this.mEntity.getY());
            }
        }

        if (mOnPostSolve)
            this.mGameLevel.resetTrailingDots();

        if (this.mEntity.getY() + 100 >= ResourceManager.getEngine().getCamera().getHeight()  ||
                this.mEntity.getX() + 100 >= ResourceManager.getEngine().getCamera().getWidth())
            ResourceManager.getCamera().setChaseEntity(this.mEntity);

        if (this.mEntity.getScaleX() < mScaleBall) {
            this.mEntity.setScale(mScaleBall);
        } else if (!this.mBody.isActive()) {
            this.mBody.setActive(true);
        } else if (this.mEntity.getY() < -20 && !mHasImpacted) {
            mHasImpacted = true;

            if (SplashWater.getInstance().waterContact) {
                SplashWater.getInstance().SplashWaterAnimation(this.mEntity.getX(), this.mEntity.getY(), true, mGameLevel);
                this.destroy();
            }else {
                getACtivateBall();
                this.destroy();
                ((MasterMiniGolfSmoothCamera)ResourceManager.getEngine().getCamera()).goToPlayer();
            }
        }
        mBodySpeed = 0f;
        if (secondsSinceLastSound < mMINIMUM_SECONDS_BETWEEN_SOUNDS)
            secondsSinceLastSound += pSecondsElapsed;
    }


    @Override
    public void onPreSolve(Contact pContact, Manifold pOldManifold) {
        mBodySpeed = Math.max(this.mBody.getLinearVelocity().len2(), mBodySpeed);
    }

    @Override
    public void onPostSolve(float pMaxImpulse) {
        if (!this.mIsGrabbed) {
            if (this.mEntity != null) {
                if (pMaxImpulse > 2f) {

                    this.mGameLevel.mMagneticObjects.remove(this);

                    if (this.mGameLevel.mPlayer.mGrabbedMagneticObject == this)
                        this.mGameLevel.mPlayer.mGrabbedMagneticObject = null;

                    mOnPostSolve = true;
                }
            }
        }
    }

    @Override
    public void onBeginContact(Contact pContact) {
        Log.v("Ball", "onBeginContact:" + pContact.getFixtureA().getBody().getUserData().toString());
        if (pContact.getFixtureA().getBody().getUserData() != null) {
            if (pContact.getFixtureA().getBody().getUserData().toString().split("@")[0].contains("ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.HoleInOne.Hole")) {
                getACtivateBall();
                SFXManager.playBallCup(1,0.5f);
                DestroyBall();
            }
            if (pContact.getFixtureA().getBody().getUserData().toString().split("@")[0].contains("ccx.gamestudio.masterminigolf.GameLevels.WorldOne.GroundLevelOne")) {
                getACtivateBall();
                DestroyBall();
            }
        }
    }

    private void getACtivateBall() {
        mGameLevel.mPlayer.mTurretMagnetOn = true;
        mGameLevel.mPlayer.mBall.setVisible(true);
        mGameLevel.btnShoot.mIsEnabled = true;
    }

    @Override
    public void onEndContact(Contact pContact) {
    }
    private void DestroyBall(){
        this.mGameLevel.registerUpdateHandler(this.onCompleteTimeBallInhole);
    }
    private final IUpdateHandler onCompleteTimeBallInhole = new IUpdateHandler() {
        private float mTotalElapsedTime = 1.9f;
        @Override
        public void onUpdate(float pSecondsElapsed) {
            this.mTotalElapsedTime -= pSecondsElapsed;
            if (mTotalElapsedTime <= 0) {
                destroy();
                ((MasterMiniGolfSmoothCamera) ResourceManager.getEngine().getCamera()).goToPlayer();
            }
        }

        @Override
        public void reset() {

        }
    };

}