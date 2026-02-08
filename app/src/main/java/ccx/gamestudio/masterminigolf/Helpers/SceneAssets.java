package ccx.gamestudio.masterminigolf.Helpers;


import org.andengine.opengl.texture.region.TextureRegion;

import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsDesertGround;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsGreenGround;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsHoleIOne;

public class SceneAssets {

    public static int getScene() {
        return SharedResources.getSelectedScene();
    }

    // ============================
    // HOLE TEXTURE
    // ============================
    public static TextureRegion getHoleTexture() {
        switch (getScene()) {
            case 0:
                return GameObjectsHoleIOne.mHoleInOne;
            case 1:
                return GameObjectsHoleIOne.mHoleInOneDesert;
            case 3:
                return GameObjectsHoleIOne.mHoleInOneDesert;
            case 4:
                return GameObjectsHoleIOne.mHoleInOneDesert;
        }
        return GameObjectsHoleIOne.mHoleInOne; // default
    }

    // ============================
    // GROUND TEXTURES
    // ============================
    public static TextureRegion getGround1() {
        switch (getScene()) {
            case 0:
                return GameObjectsGreenGround.mGround01;
            case 1:
                return GameObjectsDesertGround.mGround01;
        }
        return GameObjectsGreenGround.mGround01;
    }

    public static TextureRegion getGround2() {
        switch (getScene()) {
            case 0:
                return GameObjectsGreenGround.mGround02;
            case 1:
                return GameObjectsDesertGround.mGround02;
        }
        return GameObjectsGreenGround.mGround02;
    }

    // ============================
    // GREEN LEVEL TEXTURE
    // ============================
    public static TextureRegion getGreenLevelTexture() {
        switch (getScene()) {
            case 0:
                return GameObjectsGreenGround.mGroundGreen;
            case 1:
                return GameObjectsDesertGround.mGreenDesert;
        }
        return GameObjectsGreenGround.mGroundGreen;
    }
}