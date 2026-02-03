package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.GrennGround;

import org.andengine.entity.sprite.Sprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsGreenGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class Mushroom {

    // ====================================================
    // VARIABLES
    // ====================================================
    public Sprite mMushSprite;
    public final GameLevel mGameLevel;
    public final int nTree;

    public Mushroom(float pX, float pY, final GameLevel pGameLevel, final int numMush) {
        mMushSprite = null;
        mGameLevel = pGameLevel;
        nTree = numMush;
        switch (nTree) {
            case 0:
                mMushSprite = new Sprite(pX, pY, GameObjectsGreenGround.mushroom, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
            case 1:
                mMushSprite = new Sprite(pX, pY, GameObjectsGreenGround.mushroomOne, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
        }
        assert mMushSprite != null;
        mMushSprite.setScale(2f);
        mGameLevel.attachChild(mMushSprite);

    }
}
