package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.GrennGround;

import org.andengine.entity.sprite.Sprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsGreenGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class Trees {

    // ====================================================
    // VARIABLES
    // ====================================================
    public Sprite mTreeSprite;
    public final GameLevel mGameLevel;
    public final int nTree;

    public Trees(float pX, float pY, final GameLevel pGameLevel, final int numTree) {
        mTreeSprite = null;
        mGameLevel = pGameLevel;
        nTree = numTree;
        switch (nTree) {
            case 0:
                mTreeSprite = new Sprite(pX, pY, GameObjectsGreenGround.tree, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
            case 1:
                mTreeSprite = new Sprite(pX, pY, GameObjectsGreenGround.treeOne, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
            case 2:
                mTreeSprite = new Sprite(pX, pY, GameObjectsGreenGround.treeTwo, ResourceManager.getActivity().getVertexBufferObjectManager());
                break;
        }
        assert mTreeSprite != null;
        mTreeSprite.setScale(2f);
        mGameLevel.attachChild(mTreeSprite);

    }
}
