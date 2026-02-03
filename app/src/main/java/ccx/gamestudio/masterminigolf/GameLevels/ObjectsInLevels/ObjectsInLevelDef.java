package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels;

public class ObjectsInLevelDef {
    public enum ObjectsType {
        Tree,
        TreeOne,
        TreeTwo,
        Bush,
        BushOne,
        BushTwo,
        BushThree,
        Crate,
        Rock,
        RockOne,
        Sign,
        SignOne,
        Mushroom,
        MushroomOne,
        Ground
    }
    public final float mX;
    public final float mY;
    public final ObjectsType mObjectsType;

    public ObjectsInLevelDef(final float pX, final float pY,final ObjectsType pObjectsType) {
        mX = pX;
        mY = pY;
        mObjectsType = pObjectsType;
    }
}
