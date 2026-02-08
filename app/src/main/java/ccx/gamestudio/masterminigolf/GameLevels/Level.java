package ccx.gamestudio.masterminigolf.GameLevels;

import ccx.gamestudio.masterminigolf.GameLevels.World.WorldLevelDesert;
import ccx.gamestudio.masterminigolf.GameLevels.World.WorldLevelGreen;

public class Level {
	private static final LevelDef[] AvailableLevels =
			new LevelDef[]{
                    //==================================
                    //Tutorial
                    //==================================
                    //new WorldTutorial().Level(),
					//==================================
					//World One
					//==================================
					 new WorldLevelGreen().Level(),
                    //==================================
                    //World Two
                    //==================================
                    new WorldLevelDesert().Level(),


			};

	public static LevelDef getLevelDef(final int pWorldIndex, final int pLevelIndex) {
		for (LevelDef curLevelDef : AvailableLevels)
			if (curLevelDef.doIndicesMatch(pWorldIndex, pLevelIndex))
				return curLevelDef;
		return null;
	}

	public static boolean isNextLevelInCurrentWorld(final int pCurrentWorld, final int pCurrentLevel) {
		return getLevelDef(pCurrentWorld, pCurrentLevel + 1) != null;
	}

	public static int getNumLevelsInWorld(final int pWorldIndex) {
		int LevelCounter = 0;
		for (LevelDef curLevelDef : AvailableLevels)
			if (curLevelDef.mWorldIndex == pWorldIndex)
				LevelCounter++;
		return LevelCounter;
	}

	public static int getNumWorldInGame() {
		int WolrdCounter = 0;
		for (LevelDef curLevelDef : AvailableLevels)
			WolrdCounter = curLevelDef.mWorldIndex;
		return WolrdCounter;
	}
}