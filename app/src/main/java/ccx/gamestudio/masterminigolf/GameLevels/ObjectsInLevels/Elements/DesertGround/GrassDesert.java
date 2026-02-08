package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.DesertGround;

import org.andengine.entity.sprite.Sprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsDesertGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class GrassDesert {

    // ====================================================
    // VARIABLES
    // ====================================================
    public Sprite mGrassSprite;
    public final GameLevel mGameLevel;
    public int nCactus;
    public GrassDesert(float pX, float pY, final GameLevel pGameLevel, final int pCactus) {
        mGrassSprite = null;
        mGameLevel = pGameLevel;
        nCactus = pCactus;
        switch (nCactus) {
            case 0:
                mGrassSprite = new Sprite(pX, pY, GameObjectsDesertGround.grass, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
            case 1:
                mGrassSprite = new Sprite(pX, pY, GameObjectsDesertGround.grassOne, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;

        }
        assert mGrassSprite != null;
        mGrassSprite.setScale(2f);
        mGameLevel.attachChild(mGrassSprite);

    }
}
