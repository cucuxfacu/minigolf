package ccx.gamestudio.masterminigolf.GameLevels.WorldOne;


import ccx.gamestudio.masterminigolf.GameLevels.GroundInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.LevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.CoinInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.ObjectsInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.RocksInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.TreeInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.WaterInLevelDef;

public class WorldOneLevelOne {
    public LevelDef Level() {
        return new LevelDef(1, 1,
                new CoinInLevelDef[]{

                },
                new TreeInLevelDef[]{

                },
                new RocksInLevelDef[]{

                },
                new ObjectsInLevelDef[]{

                },
                new GroundInLevelDef[]{
                        new GroundInLevelDef(0f, 0f, 0f, false, GroundInLevelDef.GroundType.One),
                },
                new WaterInLevelDef[]{

                }
        );
    }
}
