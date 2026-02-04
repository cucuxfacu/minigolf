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
    public Body mGroundBody;
    public Sprite mGroundGreen;

    public GreenLevelOne(float pX, float pY, final GameLevel pGameLevel) {

        mGroundGreen = new Sprite(pX, pY, GameObjectsGreenGround.mGroundGreen, ResourceManager.getActivity().getVertexBufferObjectManager());
        pGameLevel.attachChild(mGroundGreen);

        final float width = mGroundGreen.getWidth() / PIXEL_TO_METER_RATIO_DEFAULT;
        final float height = mGroundGreen.getHeight() / PIXEL_TO_METER_RATIO_DEFAULT;

        final Vector2[] verticeGround = {
                new Vector2(-0.25100f*width, -0.46383f*height),
                new Vector2(+0.25525f*width, -0.45092f*height),
                new Vector2(+0.46775f*width, -0.01221f*height),
                new Vector2(+0.48650f*width, +0.36198f*height),
                new Vector2(-0.49787f*width, +0.36198f*height),
                new Vector2(-0.46037f*width, +0.01359f*height),

        };

        mGroundBody = PhysicsFactory.createPolygonBody(pGameLevel.mPhysicsWorld, mGroundGreen, verticeGround, BodyDef.BodyType.StaticBody, mGROUND_FIXTURE_DEF);
        final PhysicsConnector physConnector = new PhysicsConnector(mGroundGreen, mGroundBody);
        pGameLevel.mPhysicsWorld.registerPhysicsConnector(physConnector);
        this.set(mGroundBody, mGroundGreen, physConnector,pGameLevel);
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
}