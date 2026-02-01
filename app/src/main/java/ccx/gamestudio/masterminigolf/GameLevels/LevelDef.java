package ccx.gamestudio.masterminigolf.GameLevels;


import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.CoinInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.ObjectsInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.RocksInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.TreeInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.WaterInLevelDef;

public class LevelDef {
    public final int mWorldIndex;
    public final int mLevelIndex;
    public final CoinInLevelDef[] mCoin;
    public final TreeInLevelDef[] mTree;
    public final RocksInLevelDef[] mRock;
    public final ObjectsInLevelDef[] mObjects;
    public final GroundInLevelDef[] mGround;
    public final WaterInLevelDef[] mWater;


    public LevelDef(final int pWorldIndex,
                    final int pLevelIndex,
                    final CoinInLevelDef[] pCoin,
                    final TreeInLevelDef[] pTree,
                    final RocksInLevelDef[] pRock,
                    final ObjectsInLevelDef[] pObjects,
                    final GroundInLevelDef[] pGround,
                    final WaterInLevelDef[] pWater) {

        mWorldIndex = pWorldIndex;
        mLevelIndex = pLevelIndex;
        mCoin = pCoin;
        mTree = pTree;
        mRock = pRock;
        mObjects = pObjects;
        mGround = pGround;
        mWater =pWater;
    }

    public boolean doIndicesMatch(final int pWorldIndex, final int pLevelIndex) {
        if (mWorldIndex == pWorldIndex) {
            return mLevelIndex == pLevelIndex;
        }
        return false;
    }
}
