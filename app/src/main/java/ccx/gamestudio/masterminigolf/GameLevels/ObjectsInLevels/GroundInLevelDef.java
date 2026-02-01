package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels;

public class GroundInLevelDef {
    public enum GroundType {
        One,
        Two,
        Three,
        Four,
        Five,
        GroundLevelThree,
        GroundLevelThreeTwo,
        GroundLevelThreeThree,
        GroundLevelThreeFour,
        GroundLevelThreeFive,
    }
    public final float mX;
    public final float mY;
    public final float mMoveRith;
    public final GroundType mGroundType;
    public final boolean mBridge;

    public GroundInLevelDef(final float pX, final float pY, final float pMoveRigth, final boolean pBridge, final GroundType pGroundType)
    {
        mX = pX;
        mY = pY;
        mMoveRith = pMoveRigth;
        mBridge = pBridge;
        mGroundType = pGroundType;
    }
}
