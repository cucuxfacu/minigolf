package ccx.gamestudio.masterminigolf.GameLevels;

public abstract class LoadingRunnable implements Runnable {
	
	private final String mLoadingText;
	private final ManagedGameScene mGameScene;
	
	public abstract void onLoad();
	
	public LoadingRunnable(String pLoadingText, ManagedGameScene pGameScene)
	{
		mLoadingText = "";
		mGameScene = pGameScene;
	}
	
	@Override
	public void run() {
		if(!mLoadingText.trim().isEmpty())
			if(mGameScene!=null)
				if(mGameScene.mLoadingText!=null)
                    mGameScene.mLoadingText.setText(mGameScene.mLoadingText.getText() + "\n" + mLoadingText);
		onLoad();
	}
}