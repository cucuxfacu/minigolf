package ccx.gamestudio.masterminigolf.Menus;

import ccx.gamestudio.masterminigolf.ManagedScene;

public abstract class ManagedMenuScene extends ManagedScene {
	public ManagedMenuScene(float pLoadingScreenMinimumSecondsShown) {
		super(pLoadingScreenMinimumSecondsShown);
	}
	@Override
	public void onUnloadManagedScene() {
		if(isLoaded) {
			onUnloadScene();
		}
	}
}