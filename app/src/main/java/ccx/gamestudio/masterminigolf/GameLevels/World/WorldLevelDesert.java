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
                        new ObjectsInLevelDef(-250, 160, ObjectsInLevelDef.ObjectsType.Mushroom),
                        new ObjectsInLevelDef(-200, 150, ObjectsInLevelDef.ObjectsType.Bush),

                        new ObjectsInLevelDef(-200, 580, ObjectsInLevelDef.ObjectsType.TreeOne),
                        new ObjectsInLevelDef(-30, 305, ObjectsInLevelDef.ObjectsType.BushThree),
                        new ObjectsInLevelDef(-180, 310, ObjectsInLevelDef.ObjectsType.MushroomOne),

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
