package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.DesertGround;

import org.andengine.entity.sprite.Sprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsDesertGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class TreeDesert {

    // ====================================================
    // VARIABLES
    // ====================================================
    public Sprite mTreeSprite;
    public final GameLevel mGameLevel;

    public TreeDesert(float pX, float pY, final GameLevel pGameLevel) {
        mTreeSprite = null;
        mGameLevel = pGameLevel;

        mTreeSprite = new Sprite(pX, pY, GameObjectsDesertGround.tree, ResourceManager.getActivity().getVertexBufferObjectManager());
        mTreeSprite.setAnchorCenter(0.5f,1f);
        mTreeSprite.setScale(2f);
        mGameLevel.attachChild(mTreeSprite);

    }
}
