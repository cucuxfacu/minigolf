package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.HoleInOne;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.util.math.MathUtils;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameLevels.PhysObject;
import ccx.gamestudio.masterminigolf.GameLevels.Players.MagneticCrate;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsHoleIOne;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class Hole extends PhysObject<Sprite> {

    public Hole(float pX, float pY, GameLevel pGameLevel) {


        Sprite holeSprite = new Sprite(pX, pY+33, GameObjectsHoleIOne.mHoleInOne,  ResourceManager.getActivity().getVertexBufferObjectManager()){
            protected void onManagedUpdate(final float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);

                if (Hole.this.mBody != null) {
                    //Hole.this.mGameLevel.reportBaseBodySpeed(Hole.this.mBody.getLinearVelocity().len2());
                }
            }
        };
        holeSprite.setScale(0.1f);

        FixtureDef fixture = PhysicsFactory.createFixtureDef(10, 0, 0);
        //fixture.isSensor = true;

        Body body = PhysicsFactory.createCircleBody(
                pGameLevel.mPhysicsWorld,
                holeSprite,
                BodyDef.BodyType.StaticBody,
                fixture
        );

        PhysicsConnector connector = new PhysicsConnector(holeSprite, body);
        pGameLevel.mPhysicsWorld.registerPhysicsConnector(connector);

        this.set(body, holeSprite, connector, pGameLevel);

        pGameLevel.attachChild(holeSprite);
    }



    @Override
    public void onBeginContact(Contact pContact) {
        }

    @Override
    public void onEndContact(Contact pContact) { }

    @Override
    public void onPreSolve(Contact pContact, Manifold pOldManifold) {}

    @Override
    public void onPostSolve(float pMaxImpulse) {
        if(this.mGameLevel.mIsLevelSettled) {

                this.destroy();
                this.mGameLevel.currentGreen.destroy();
                this.mGameLevel.spawnGreenAndHole();
                //.mEntity.detachSelf();
        }
    }
}
