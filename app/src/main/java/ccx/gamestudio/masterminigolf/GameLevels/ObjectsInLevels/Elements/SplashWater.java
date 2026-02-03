package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements;


import org.andengine.entity.sprite.AnimatedSprite;

import ccx.gamestudio.masterminigolf.GameLevels.GameLevel;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsBackGround;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.Manager.SFXManager;


public class SplashWater {

    private static final SplashWater INSTANCE = new SplashWater();
    public boolean waterContact = false;
    boolean mContactTank = false;
    boolean mContactCrate = false;
    public AnimatedSprite mWaterSplash;
    private GameLevel mGameLevel;

    public SplashWater(){
    }
    public static SplashWater getInstance(){
        return INSTANCE;
    }
    public void SplashWaterAnimation(final float pX, final float pY, final boolean pContactCrate, GameLevel pGameLevel) {
        this.mGameLevel = pGameLevel;
        getInstance().mContactCrate = pContactCrate;
        getInstance().mWaterSplash  = new AnimatedSprite(pX, pY -5f, GameObjectsBackGround.gameWaterSplashTTR, ResourceManager.getActivity().getVertexBufferObjectManager());
        getInstance().mWaterSplash.setScale(2.5f);
        getInstance().mWaterSplash.setZIndex(999);
        getInstance().mWaterSplash.animate(30, new AnimatedSprite.IAnimationListener() {
            @Override
            public void onAnimationFinished(final AnimatedSprite pAnimatedSprite) {
            }

            @Override
            public void onAnimationFrameChanged(final AnimatedSprite pAnimatedSprite, final int pOldFrameIndex, final int pNewFrameIndex) {}

            @Override
            public void onAnimationLoopFinished(final AnimatedSprite pAnimatedSprite, final int pRemainingLoopCount, final int pInitialLoopCount) {
                pAnimatedSprite.stopAnimation();
                ResourceManager.getActivity().runOnUpdateThread(() -> {
                    pAnimatedSprite.detachSelf();
                    if(!pAnimatedSprite.isDisposed()) {
                        SplashWater.getInstance().waterContact = true;
                    }
                });
            }

            @Override
            public void onAnimationStarted(final AnimatedSprite pAnimatedSprite, final int pInitialLoopCount) {
                SFXManager.playWaterSplash(1f, 0.1f);
                getInstance().waterContact = false;
            }
        });
        pGameLevel.attachChild(mWaterSplash);
    }
}