package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels;

public class RocksInLevelDef {
    public enum RocksType {
        One,Two, Three, Four, Five
    }
    public final float rX;
    public final float rY;
    public final RocksType mRocksType;

    public RocksInLevelDef(final float pX, final float pY, final RocksType pRocksType) {
        rX = pX;
        rY = pY;
        mRocksType = pRocksType;
    }
}
