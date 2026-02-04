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
import ccx.gamestudio.masterminigolf.GameLevels.MagneticPhysObject;
import ccx.gamestudio.masterminigolf.GameLevels.PhysObject;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsGreenGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class GroundLevelOne extends PhysObject<Sprite> {

    // ===============================================================================================
    // CONSTANT
    // ===============================================================================================
    private static final float mGROUND_DENSITY = 0;
    private static final float mGROUND_ELASTICITY = 0f;
    private static final float mGROUND_FRICTION = 0f;
    private static final FixtureDef mGROUND_FIXTURE_DEF = PhysicsFactory.createFixtureDef(mGROUND_DENSITY, mGROUND_ELASTICITY, mGROUND_FRICTION);
    public  Body mGroundBody;
    public Sprite mGround;
    public Sprite mGround02;
    // ===============================================================================================
    // CONSTRUCTOR
    // ===============================================================================================
    public GroundLevelOne(float pX, float pY, final GameLevel pGameLevel) {

        mGround02 = new Sprite(pX-200, pY + 150, GameObjectsGreenGround.mGround02, ResourceManager.getActivity().getVertexBufferObjectManager());
        pGameLevel.attachChild(mGround02);

        mGround = new Sprite(pX, pY, GameObjectsGreenGround.mGround01, ResourceManager.getActivity().getVertexBufferObjectManager());
        pGameLevel.attachChild(mGround);

        final float width = mGround.getWidth() / PIXEL_TO_METER_RATIO_DEFAULT;
        final float height = mGround.getHeight() / PIXEL_TO_METER_RATIO_DEFAULT;
        final Vector2[] verticeGroundLeft = {
                new Vector2(-0.46198f * width, -0.39537f * height),
                new Vector2(+0.46216f * width, -0.39537f * height),
                new Vector2(+0.48147f * width, +0.45456f * height),
                new Vector2(-0.48405f * width, +0.45456f * height),

        };
        mGroundBody = PhysicsFactory.createPolygonBody(pGameLevel.mPhysicsWorld, mGround, verticeGroundLeft, BodyDef.BodyType.StaticBody, mGROUND_FIXTURE_DEF);
        pGameLevel.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(mGround, mGroundBody));

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




