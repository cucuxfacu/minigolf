package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels;

public class WaterInLevelDef {
    public enum WaterType {
        water, lava
    }
    public final float mX;
    public final float mY;

    public final WaterType mWaterType;

    public WaterInLevelDef(final float pX, final float pY,  WaterType pWaterType)
    {
        mX = pX;
        mY = pY;
        mWaterType = pWaterType;
    }
}
