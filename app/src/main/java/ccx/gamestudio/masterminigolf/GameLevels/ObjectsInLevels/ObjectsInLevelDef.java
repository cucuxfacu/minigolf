package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels;

public class ObjectsInLevelDef {
    public enum ObjectsType {
        Barrel,
        Bridge,
        BoxWooden,
        Mine,
        MetalBoxDynamic,
        GroundExplosion,
        BoxWoodenKinematic,
        BoxStatic,
        MetalBoxStatic,
        GroundNormal,
    }
    public final float mX;
    public final float mY;
    public final float mLength;
    public final float mRotation;
    public final ObjectsType mObjectsType;

    public ObjectsInLevelDef(final float pX, final float pY, final float pLength,final float pRotation, final ObjectsType pObjectsType) {
        mX = pX;
        mY = pY;
        mLength = pLength;
        mRotation = pRotation;
        mObjectsType = pObjectsType;
    }
}
