package ccx.gamestudio.masterminigolf.GameLevels.World;


import ccx.gamestudio.masterminigolf.GameLevels.GroundInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.LevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.CoinInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.ObjectsInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.WaterInLevelDef;

public class WorldLevelDesert {
    public LevelDef Level() {
        return new LevelDef(2, 1,
                new CoinInLevelDef[]{

                },
                new ObjectsInLevelDef[]{
                        new ObjectsInLevelDef(-200, 790, ObjectsInLevelDef.ObjectsType.TreeDesert),
                        new ObjectsInLevelDef(-200, 440, ObjectsInLevelDef.ObjectsType.CactusDesert),
                        new ObjectsInLevelDef( 250, 150, ObjectsInLevelDef.ObjectsType.SkeletonDesert),
                        new ObjectsInLevelDef( 0, 150, ObjectsInLevelDef.ObjectsType.GrassDesertOne),
                        new ObjectsInLevelDef( -200, 190, ObjectsInLevelDef.ObjectsType.CactusDesertTwo),
                        new ObjectsInLevelDef(300, 190, ObjectsInLevelDef.ObjectsType.SignOne),
                },
                new GroundInLevelDef[]{
                        new GroundInLevelDef(0f, 0f, 0f, false, GroundInLevelDef.GroundType.One),
                },
                new WaterInLevelDef[]{

                }
        );
    }
}
