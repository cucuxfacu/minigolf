package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.DesertGround;

import org.andengine.entity.sprite.Sprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsDesertGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class StoneDesert {

    // ====================================================
    // VARIABLES
    // ====================================================
    public Sprite mStoneprite;
    public final GameLevel mGameLevel;

    public StoneDesert(float pX, float pY, final GameLevel pGameLevel) {
        mStoneprite = null;
        mGameLevel = pGameLevel;

        mStoneprite = new Sprite(pX, pY, GameObjectsDesertGround.stone, ResourceManager.getActivity().getVertexBufferObjectManager());

        mStoneprite.setScale(2f);
        mGameLevel.attachChild(mStoneprite);

    }
}
