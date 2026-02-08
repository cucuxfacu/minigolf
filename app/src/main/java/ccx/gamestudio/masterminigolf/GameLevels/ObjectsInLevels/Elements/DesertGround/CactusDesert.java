package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.DesertGround;

import org.andengine.entity.sprite.Sprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsDesertGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class CactusDesert {

    // ====================================================
    // VARIABLES
    // ====================================================
    public Sprite mCactusSprite;
    public final GameLevel mGameLevel;
    public int nCactus;
    public CactusDesert(float pX, float pY, final GameLevel pGameLevel, final int nCactus) {
        mCactusSprite = null;
        mGameLevel = pGameLevel;
        this.nCactus = nCactus;
        switch (nCactus) {
            case 0:
                mCactusSprite = new Sprite(pX, pY, GameObjectsDesertGround.cactus, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
            case 1:
                mCactusSprite = new Sprite(pX, pY, GameObjectsDesertGround.cactusOne, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
            case 2:
                mCactusSprite = new Sprite(pX, pY, GameObjectsDesertGround.cactusTwo, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
        }
        assert mCactusSprite != null;
        mCactusSprite.setAnchorCenter(0.5f,1f);
        mCactusSprite.setScale(1.5f);
        mGameLevel.attachChild(mCactusSprite);

    }
}
