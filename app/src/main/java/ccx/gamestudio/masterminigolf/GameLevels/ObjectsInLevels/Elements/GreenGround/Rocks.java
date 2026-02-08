package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.GreenGround;

import org.andengine.entity.sprite.Sprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsGreenGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class Rocks {

    // ====================================================
    // VARIABLES
    // ====================================================
    public Sprite mRockSprite;
    public final GameLevel mGameLevel;
    public final int nRock;

    public Rocks(float pX, float pY, final GameLevel pGameLevel, final int numRock) {
        mRockSprite = null;
        mGameLevel = pGameLevel;
        nRock = numRock;
        switch (nRock) {
            case 1:
                mRockSprite = new Sprite(pX, pY, GameObjectsGreenGround.rocks, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
            case 2:
                mRockSprite = new Sprite(pX, pY, GameObjectsGreenGround.rocksOne, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
        }
        assert mRockSprite != null;
        mRockSprite.setScale(2f);
        mRockSprite.setPosition(pX, pY);
        mGameLevel.attachChild(mRockSprite);

    }
}
