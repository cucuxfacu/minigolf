package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.DesertGround;

import org.andengine.entity.sprite.Sprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsGreenGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class CrateDesert {
    // ====================================================
    // VARIABLES
    // ====================================================
    public Sprite mCrateSprite;
    public final GameLevel mGameLevel;
    public CrateDesert(float pX, float pY, final GameLevel pGameLevel) {
        mCrateSprite = null;
        mGameLevel = pGameLevel;

        mCrateSprite = new Sprite(pX, pY, GameObjectsGreenGround.crateGreen, ResourceManager.getActivity().getVertexBufferObjectManager());

        mCrateSprite.setScale(2f);
        mGameLevel.attachChild(mCrateSprite);
    }
}