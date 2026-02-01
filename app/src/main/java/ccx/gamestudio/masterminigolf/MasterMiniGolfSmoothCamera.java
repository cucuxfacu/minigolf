package ccx.gamestudio.masterminigolf;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;

import ccx.gamestudio.masterminigolf.GameLevels.Players.Players;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;

public class MasterMiniGolfSmoothCamera extends SmoothCamera {

    private Players mPlayerEntity;
    private float mBaseX = 0f;
    private float mBaseY = 0f;
    private float mTargetCenterX;
    private float mTargetCenterY;
    public MasterMiniGolfSmoothCamera(float pX, float pY, float pWidth, float pHeight, float pMaxVelocityX, float pMaxVelocityY, float pMaxZoomFactorChange) {
        super(pX, pY, pWidth, pHeight, pMaxVelocityX, pMaxVelocityY, pMaxZoomFactorChange);
    }

    // ====================================================
    // METHODS
    // ====================================================
    public void setPlayerEntity(Players pPlayerBodyEntity) {
       mPlayerEntity = pPlayerBodyEntity;
      this.setChaseEntity(mPlayerEntity.mPlayer);
   }


    public void setBasePosition(final float pX, final float pY) {
        mBaseX = pX;
        mBaseY = pY;
    }

    public void goToMagneTank() {
        IEntity iEntity =  mPlayerEntity.mPlayer;
        this.setChaseEntity(iEntity);
    }

    public void goToBase() {
        this.setChaseEntity(null);
        this.setCenter(mBaseX, mBaseY);
    }

    public static void setupForMenus() {
        final MasterMiniGolfSmoothCamera ThisCam = ((MasterMiniGolfSmoothCamera)ResourceManager.getEngine().getCamera());
        ThisCam.setBoundsEnabled(false);
        ThisCam.setChaseEntity(null);
        ThisCam.setZoomFactorDirect(1f);
        ThisCam.setZoomFactor(1f);
        ThisCam.setCenterDirect(ResourceManager.getInstance().cameraWidth/2f, ResourceManager.getInstance().cameraHeight/2f);
        ThisCam.setCenter(ResourceManager.getInstance().cameraWidth/2f, ResourceManager.getInstance().cameraHeight/2f);
        ThisCam.clearUpdateHandlers();
    }

    public void goToBaseForSeconds(final float pSeconds) {
        goToBase();
        final MasterMiniGolfSmoothCamera ThisCam = this;
        ThisCam.registerUpdateHandler(new IUpdateHandler() {
            float timeElapsed = 0f;
            @Override
            public void onUpdate(float pSecondsElapsed) {
                timeElapsed += pSecondsElapsed;
                if (mPlayerEntity.mGameLevel.mIsThereBaseMovement)
                    timeElapsed = 0f;
                if (timeElapsed >= pSeconds) {
                    ThisCam.setChaseEntity(mPlayerEntity.mPlayer);
                    ThisCam.unregisterUpdateHandler(this);
                }
            }
            @Override public void reset() {}
        });
    }

    @Override
    public void setCenter(final float pCenterX, final float pCenterY) {
        this.mTargetCenterX = pCenterX;
        this.mTargetCenterY = pCenterY;
    }

    public void setCenterDirect(final float pCenterX, final float pCenterY) {
        super.setCenterDirect(pCenterX, pCenterY);
        this.mTargetCenterX = pCenterX;
        this.mTargetCenterY = pCenterY;
    }

    @Override
    public void onUpdate(final float pSecondsElapsed) {
        super.onUpdate(pSecondsElapsed);
        final float currentCenterX = this.getCenterX();
        final float currentCenterY = this.getCenterY();

        final float targetCenterX = this.mTargetCenterX;
        final float targetCenterY = this.mTargetCenterY;

        if(currentCenterX != targetCenterX || currentCenterY != targetCenterY) {
            final float diffX = targetCenterX - currentCenterX;
            final float dX = diffX / 4f;

            final float diffY = targetCenterY - currentCenterY;
            final float dY = diffY / 4f;

            super.setCenter(currentCenterX + dX, currentCenterY + dY);
        }
        final float targetZoomFactor = this.getTargetZoomFactor();
        this.setZoomFactorDirect(targetZoomFactor);
    }
}
