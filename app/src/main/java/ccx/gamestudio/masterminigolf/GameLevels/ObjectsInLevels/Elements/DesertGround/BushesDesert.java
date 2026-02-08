package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.DesertGround;

import org.andengine.entity.sprite.Sprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsDesertGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class BushesDesert {

    // ====================================================
    // VARIABLES
    // ====================================================
    public Sprite mBushSprite;
    public final GameLevel mGameLevel;

    public BushesDesert(float pX, float pY, final GameLevel pGameLevel, final int nBush) {
        mBushSprite = null;
        mGameLevel = pGameLevel;
        switch (nBush) {
            case 0:
                mBushSprite = new Sprite(pX, pY, GameObjectsDesertGround.bush, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
            case 1:
                mBushSprite = new Sprite(pX, pY, GameObjectsDesertGround.bushOne, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;

        }
        assert mBushSprite != null;
        mGameLevel.attachChild(mBushSprite);

    }
}
