package ccx.gamestudio.masterminigolf.GameLevels.WorldOne;

import static org.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameLevels.PhysObject;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsGreenGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class GreenLevelOne extends PhysObject<Sprite> {

    private static final float mGROUND_DENSITY = 0;
    private static final float mGROUND_ELASTICITY = 0.01f;
    private static final float mGROUND_FRICTION = 0.5f;
    private static final FixtureDef mGROUND_FIXTURE_DEF = PhysicsFactory.createFixtureDef(mGROUND_DENSITY, mGROUND_ELASTICITY, mGROUND_FRICTION);
    public Body mGroundBodyLeft;
    public Body mGroundBodyRight;
    public Sprite mGroundGreen;
    private PhysicsConnector physConnectorGroundBodyLeft;
    private PhysicsConnector physConnectorGroundBodyRight;


    public GreenLevelOne(float pX, float pY, final GameLevel pGameLevel) {
        mGroundBodyLeft = null;
        mGroundBodyRight = null;

        mGroundGreen = new Sprite(pX, pY, GameObjectsGreenGround.mGroundGreen, ResourceManager.getActivity().getVertexBufferObjectManager());
        pGameLevel.attachChild(mGroundGreen);

        final float width = mGroundGreen.getWidth() / PIXEL_TO_METER_RATIO_DEFAULT;
        final float height = mGroundGreen.getHeight() / PIXEL_TO_METER_RATIO_DEFAULT;

        final Vector2[] verticeGroundLeft = {
                new Vector2(-0.25100f*width, -0.46383f*height),
                new Vector2(-0.08846f*width, -0.32423f*height),
                new Vector2(-0.08846f*width, -0.01455f*height),
                new Vector2(-0.08846f*width, +0.35964f*height),
                new Vector2(-0.49787f*width, +0.36198f*height),
                new Vector2(-0.46037f*width, +0.01359f*height),
        };

        final Vector2[] verticeGroundRight = {
                new Vector2(+0.08341f*width, -0.33714f*height),
                new Vector2(+0.26779f*width, -0.42746f*height),
                new Vector2(+0.36466f*width, -0.07907f*height),
                new Vector2(+0.46154f*width, +0.02416f*height),
                new Vector2(+0.48029f*width, +0.37254f*height),
                new Vector2(+0.08341f*width, +0.37254f*height),
        };

        mGroundBodyLeft = PhysicsFactory.createPolygonBody(pGameLevel.mPhysicsWorld, mGroundGreen, verticeGroundLeft, BodyDef.BodyType.StaticBody, mGROUND_FIXTURE_DEF);
        mGroundBodyRight = PhysicsFactory.createPolygonBody(pGameLevel.mPhysicsWorld, mGroundGreen, verticeGroundRight, BodyDef.BodyType.StaticBody, mGROUND_FIXTURE_DEF);

         physConnectorGroundBodyLeft = new PhysicsConnector(mGroundGreen, mGroundBodyLeft);
        pGameLevel.mPhysicsWorld.registerPhysicsConnector(physConnectorGroundBodyLeft);

        physConnectorGroundBodyRight = new PhysicsConnector(mGroundGreen, mGroundBodyRight);
        pGameLevel.mPhysicsWorld.registerPhysicsConnector(physConnectorGroundBodyRight);

        this.set(mGroundBodyLeft, mGroundGreen, physConnectorGroundBodyLeft, pGameLevel);
        this.set(mGroundBodyRight, mGroundGreen, physConnectorGroundBodyRight, pGameLevel);
    }

    @Override
    public void onBeginContact(Contact pContact) {

    }

    @Override
    public void onEndContact(Contact pContact) {
    }

    @Override
    public void onPreSolve(Contact pContact, Manifold pOldManifold) {
    }

    @Override
    public void onPostSolve(float pMaxImpulse) {
    }

    public void destroy() {

        if (physConnectorGroundBodyLeft != null) {
            mGameLevel.mPhysicsWorld.unregisterPhysicsConnector(physConnectorGroundBodyLeft);
            physConnectorGroundBodyLeft = null;
        }

        if (physConnectorGroundBodyRight != null) {
            mGameLevel.mPhysicsWorld.unregisterPhysicsConnector(physConnectorGroundBodyRight);
            physConnectorGroundBodyRight = null;
        }

        if (mGroundBodyLeft != null) {
            mGameLevel.mPhysicsWorld.destroyBody(mGroundBodyLeft);
            mGroundBodyLeft = null;
        }

        if (mGroundBodyRight != null) {
            mGameLevel.mPhysicsWorld.destroyBody(mGroundBodyRight);
            mGroundBodyRight = null;
        }

        if (mGroundGreen != null) {
            mGroundGreen.detachSelf();
            mGroundGreen.dispose();
            mGroundGreen = null;
        }
    }

}