package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements;

import org.andengine.entity.sprite.AnimatedSprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsBackGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;


public class Water {
    public final AnimatedSprite mWater;

    // ====================================================
    // CONSTRUCTOR
    // ====================================================
    public Water(float pX, float pY, GameLevel pGameLevel) {
        mWater = new AnimatedSprite(pX, pY, GameObjectsBackGround.gameWaterTTR, ResourceManager.getActivity().getVertexBufferObjectManager()) {
            @Override
            public void onManagedUpdate(final float pSecondsElapsed) {
                super.onManagedUpdate(pSecondsElapsed);
            }
        };
        mWater.animate(100, true);
        mWater.setScale(1);
        pGameLevel.attachChild(mWater);
    }
}
