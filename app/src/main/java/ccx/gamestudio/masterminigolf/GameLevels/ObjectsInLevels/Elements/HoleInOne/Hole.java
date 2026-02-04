package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.HoleInOne;

import static org.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.debugdraw.DebugRenderer;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;


import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameLevels.PhysObject;

import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsHoleIOne;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class Hole extends PhysObject<Sprite> {
    private static final float mGROUND_DENSITY = 0;
    private static final float mGROUND_ELASTICITY = 0.01f;
    private static final float mGROUND_FRICTION = 0.5f;
    private static final FixtureDef mGROUND_FIXTURE_DEF = PhysicsFactory.createFixtureDef(mGROUND_DENSITY, mGROUND_ELASTICITY, mGROUND_FRICTION);
    private GameLevel mGameLevel;
    private Body mBody;
    public Hole(float pX, float pY, GameLevel pGameLevel) {
        this.mGameLevel = pGameLevel;

        Sprite holeSprite = new Sprite(pX, pY+34f, GameObjectsHoleIOne.mHoleInOne,  ResourceManager.getActivity().getVertexBufferObjectManager());
        //holeSprite.setScale(0.1f);

        FixtureDef fixture = PhysicsFactory.createFixtureDef(10, 0, 0);
        fixture.isSensor = true;

        final float width = holeSprite.getWidth() / PIXEL_TO_METER_RATIO_DEFAULT;
        final float height = holeSprite.getHeight() / PIXEL_TO_METER_RATIO_DEFAULT;

        final Vector2[] verticeGround = {
                new Vector2(+0.29647f*width, -0.40132f*height),
                new Vector2(+0.48474f*width, -0.00264f*height),
                new Vector2(-0.49722f*width, +0.01398f*height),
                new Vector2(-0.29418f*width, -0.40132f*height),
        };

        mBody = PhysicsFactory.createPolygonBody(pGameLevel.mPhysicsWorld, holeSprite, verticeGround, BodyDef.BodyType.StaticBody, mGROUND_FIXTURE_DEF);

        PhysicsConnector connector = new PhysicsConnector(holeSprite, mBody);
        pGameLevel.mPhysicsWorld.registerPhysicsConnector(connector);

        this.set(mBody, holeSprite, connector, this.mGameLevel);

        pGameLevel.attachChild(holeSprite);
        this.mGameLevel.attachChild(new DebugRenderer(this.mGameLevel.mPhysicsWorld, ResourceManager.getActivity().getVertexBufferObjectManager()));
    }


    @Override
    public void onBeginContact(Contact pContact) {
        if (this.mGameLevel.mIsLevelSettled) {
            mGameLevel.ballEnteredHole = true;
        }
    }

    @Override
    public void onEndContact(Contact pContact) { }

    @Override
    public void onPreSolve(Contact pContact, Manifold pOldManifold) {}

    @Override
    public void onPostSolve(float pMaxImpulse) {

    }

}
