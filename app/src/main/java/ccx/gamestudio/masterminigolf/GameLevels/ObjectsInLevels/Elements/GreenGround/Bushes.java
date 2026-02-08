package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.GreenGround;

import org.andengine.entity.sprite.Sprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsGreenGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class Bushes {

    // ====================================================
    // VARIABLES
    // ====================================================
    public Sprite mBushSprite;
    public final GameLevel mGameLevel;

    public Bushes(float pX, float pY, final GameLevel pGameLevel, final int nBush) {
        mBushSprite = null;
        mGameLevel = pGameLevel;
        switch (nBush) {
            case 0:
                mBushSprite = new Sprite(pX, pY, GameObjectsGreenGround.bushGreen, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
            case 1:
                mBushSprite = new Sprite(pX, pY, GameObjectsGreenGround.bushGreenOne, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
            case 2:
                mBushSprite = new Sprite(pX, pY, GameObjectsGreenGround.bushGreenTwo, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
            case 3:
                mBushSprite = new Sprite(pX, pY, GameObjectsGreenGround.bushGreenThree, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
        }
        assert mBushSprite != null;
        mGameLevel.attachChild(mBushSprite);

    }
}
