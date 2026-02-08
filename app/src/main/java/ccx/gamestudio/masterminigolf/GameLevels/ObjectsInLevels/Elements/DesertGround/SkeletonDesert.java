package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.DesertGround;

import org.andengine.entity.sprite.Sprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsDesertGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class SkeletonDesert {

    // ====================================================
    // VARIABLES
    // ====================================================
    public Sprite mSkeletonSprite;
    public final GameLevel mGameLevel;

    public SkeletonDesert(float pX, float pY, final GameLevel pGameLevel) {
        mSkeletonSprite = null;
        mGameLevel = pGameLevel;
        mSkeletonSprite = new Sprite(pX, pY, GameObjectsDesertGround.skeleton, ResourceManager.getActivity().getVertexBufferObjectManager());
        mGameLevel.attachChild(mSkeletonSprite);
    }
}
