package ccx.gamestudio.masterminigolf.GameLevels.World;


import ccx.gamestudio.masterminigolf.GameLevels.GroundInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.LevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.CoinInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.ObjectsInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.WaterInLevelDef;

public class WorldLevelGreen {
    public LevelDef Level() {
        return new LevelDef(1, 1,
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
                        new WaterInLevelDef(-512, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(-256, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(0, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(256, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(512, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(768, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(1024, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(1280, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(1536, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(1792, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(2048, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(2304, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(2560, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(2816, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(3072, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(3328, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(3584, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(3840, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(4096, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(4352, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(4608, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(4864, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(5120, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(5376, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(5632, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(5888, -50, WaterInLevelDef.WaterType.water),
                        new WaterInLevelDef(6144, -50, WaterInLevelDef.WaterType.water),
                }
        );
    }
}
