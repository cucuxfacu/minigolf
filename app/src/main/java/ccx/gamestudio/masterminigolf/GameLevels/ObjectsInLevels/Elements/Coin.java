package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements;

import android.util.Log;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseElasticOut;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsCoins;
import ccx.gamestudio.masterminigolf.Helpers.SharedResources;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.Manager.SFXManager;


public class Coin {

    // ====================================================
    // VARIABLES
    // ====================================================
    private final GameLevel mGameLevel;
    private final AnimatedSprite mCoinDynamicSprite;
    private boolean mTouchCoin;

    // ====================================================
    // CONSTRUCTOR
    // ====================================================
    public Coin(final float pX, final float pY, final GameLevel pGameLevel) {
        this.mGameLevel = pGameLevel;
        this.mCoinDynamicSprite = new AnimatedSprite(pX, pY, GameObjectsCoins.coinAnimTTR, ResourceManager.getActivity().getVertexBufferObjectManager());
        mCoinDynamicSprite.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                if (mGameLevel.mMagneticObjects == null || mGameLevel.mMagneticObjects.isEmpty())
                    return;
                if (mGameLevel.mMagneticObjects.get(0).mEntity == null)
                    return;
                if (mCoinDynamicSprite.collidesWith(mGameLevel.mMagneticObjects.get(0).mEntity) && !mTouchCoin) {
                    mTouchCoin = true;
                    AddPointAndSoundCoin();
                }
            }

            @Override
            public void reset() {
            }
        });
        this.mCoinDynamicSprite.animate(90, true);
        this.mCoinDynamicSprite.setScale(0.1f);
        this.mGameLevel.attachChild(this.mCoinDynamicSprite);
    }

    private void AddPointAndSoundCoin() {
        if(this.mCoinDynamicSprite.getAlpha() == 1)
        {
            SFXManager.playCoin(1f, 0.5f);
        }

        this.mCoinDynamicSprite.clearEntityModifiers();

        this.mCoinDynamicSprite.registerEntityModifier(
                new FadeOutModifier(0.4f) {
                    @Override
                    protected void onModifierFinished(IEntity pItem) {
                        super.onModifierFinished(pItem);

                        mCoinDynamicSprite.setVisible(false);
                        destroy();
                    }
                }
        );
    }

    public void destroy() {
        ResourceManager.getActivity().runOnUpdateThread(this.mCoinDynamicSprite::detachSelf);
        mTouchCoin = false;
    }
}
