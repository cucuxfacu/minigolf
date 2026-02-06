package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.HoleInOne;

import static org.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
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
import ccx.gamestudio.masterminigolf.Manager.SFXManager;

public class Hole extends PhysObject<Sprite> {
    private static final float mGROUND_DENSITY = 10;
    private static final float mGROUND_ELASTICITY = 0.05f;
    private static final float mGROUND_FRICTION = 0.1f;
    private static final FixtureDef mGROUND_FIXTURE_DEF = PhysicsFactory.createFixtureDef(mGROUND_DENSITY, mGROUND_ELASTICITY, mGROUND_FRICTION);
    private GameLevel mGameLevel;
    private  Sprite mHoleSprite;
    private Body mBody;
    private boolean isSound;
    public Hole(float pX, float pY, GameLevel pGameLevel) {
        this.mGameLevel = pGameLevel;

        mHoleSprite = new Sprite(pX, pY, GameObjectsHoleIOne.mHoleInOne,  ResourceManager.getActivity().getVertexBufferObjectManager());
        mHoleSprite.setZIndex(999);

        final float width = mHoleSprite.getWidth() / PIXEL_TO_METER_RATIO_DEFAULT;
        final float height = mHoleSprite.getHeight() / PIXEL_TO_METER_RATIO_DEFAULT;

        final Vector2[] verticeGround = {
                new Vector2(+0.47003f*width, -0.45179f*height),
                new Vector2(-0.48683f*width, -0.44551f*height),
        };

        mBody = PhysicsFactory.createPolygonBody(pGameLevel.mPhysicsWorld, mHoleSprite, verticeGround, BodyDef.BodyType.StaticBody, mGROUND_FIXTURE_DEF);
        PhysicsConnector connector = new PhysicsConnector(mHoleSprite, mBody);
        pGameLevel.mPhysicsWorld.registerPhysicsConnector(connector);
        this.set(mBody, mHoleSprite, connector, this.mGameLevel);
        pGameLevel.attachChild(mHoleSprite);
        //this.mGameLevel.attachChild(new DebugRenderer(this.mGameLevel.mPhysicsWorld, ResourceManager.getActivity().getVertexBufferObjectManager()));
    }


    @Override
    public void onBeginContact(Contact pContact) {
        if(pContact.getFixtureB().getBody().getUserData() != null) {
            if (pContact.getFixtureB().getBody().getUserData().toString().split("@")[0].contains("ccx.gamestudio.masterminigolf.GameLevels.Players.MagneticCrate"))
            {
                if (this.mGameLevel.mIsLevelSettled) {
                    mGameLevel.ballEnteredHole = true;
                    if(!isSound)
                    {
                        isSound = true;
                        SFXManager.playCrowdClap(1,0.25f);
                        mGameLevel.addPointsToScore(this.mEntity, 1);
                    }
                }
            }
        }
    }

    @Override
    public void onEndContact(Contact pContact) {
    }

    @Override
    public void onPreSolve(Contact pContact, Manifold pOldManifold) {}

    @Override
    public void onPostSolve(float pMaxImpulse) {

    }
    @Override
    public void postSolve(final Contact pContact, ContactImpulse impulse){

    }
}