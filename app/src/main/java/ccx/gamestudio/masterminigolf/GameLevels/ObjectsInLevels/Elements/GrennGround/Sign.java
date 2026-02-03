package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.GrennGround;

import org.andengine.entity.sprite.Sprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsGreenGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class Sign {

    // ====================================================
    // VARIABLES
    // ====================================================
    public Sprite mSignSprite;
    public final GameLevel mGameLevel;

    public Sign(float pX, float pY, final GameLevel pGameLevel, final int numSign) {
        mSignSprite = null;
        mGameLevel = pGameLevel;
        switch (numSign) {
            case 0:
                mSignSprite = new Sprite(pX, pY, GameObjectsGreenGround.sign, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
            case 1:
                mSignSprite = new Sprite(pX, pY, GameObjectsGreenGround.signOne, ResourceManager.getActivity().getVertexBufferObjectManager());
                mSignSprite.setRotation(-5f);
                break;
        }
        assert mSignSprite != null;
        mSignSprite.setScale(2f);
        mSignSprite.setPosition(pX, pY);
        mGameLevel.attachChild(mSignSprite);

    }
}
