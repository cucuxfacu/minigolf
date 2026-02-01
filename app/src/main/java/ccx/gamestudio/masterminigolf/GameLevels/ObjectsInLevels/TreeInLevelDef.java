package ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels;

public class TreeInLevelDef {
    public enum TreeType {
        One,Two, Three, Four, Five, Six
    }
    public final float tX;
    public final float tY;
    public final TreeType mTreeType;

    public TreeInLevelDef(final float pX, final float pY,  final TreeType pTreeType) {
        tX = pX;
        tY = pY;
        mTreeType = pTreeType;
    }
}
