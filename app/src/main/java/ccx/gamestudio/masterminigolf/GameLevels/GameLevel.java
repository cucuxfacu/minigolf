package ccx.gamestudio.masterminigolf.GameLevels;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.touch.TouchEvent;

import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.math.MathUtils;
import org.andengine.util.modifier.ease.EaseElasticOut;
import android.hardware.SensorManager;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.GrennGround.Bushes;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.GrennGround.Mushroom;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.GrennGround.Sign;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.GrennGround.Trees;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.HoleInOne.Hole;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.SplashWater;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.Elements.Water;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.ObjectsInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.ParallaxLayer;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.WaterInLevelDef;
import ccx.gamestudio.masterminigolf.GameLevels.Players.Players;
import ccx.gamestudio.masterminigolf.GameLevels.WorldOne.GreenLevelOne;
import ccx.gamestudio.masterminigolf.GameLevels.WorldOne.GroundLevelOne;
import ccx.gamestudio.masterminigolf.GameObjects.GameObjectsBackGround;
import ccx.gamestudio.masterminigolf.GameObjects.GamePlayers;
import ccx.gamestudio.masterminigolf.Helpers.SharedResources;
import ccx.gamestudio.masterminigolf.Input.GrowButton;
import ccx.gamestudio.masterminigolf.Input.GrowButtonControls;
import ccx.gamestudio.masterminigolf.Input.GrowToggleButton;
import ccx.gamestudio.masterminigolf.Manager.GameManager;
import ccx.gamestudio.masterminigolf.Manager.MenuResourceManager;
import ccx.gamestudio.masterminigolf.Manager.ResourceManager;
import ccx.gamestudio.masterminigolf.Manager.SFXManager;
import ccx.gamestudio.masterminigolf.Manager.SceneManager;
import ccx.gamestudio.masterminigolf.MasterMiniGolfSmoothCamera;
import ccx.gamestudio.masterminigolf.GameLevels.ObjectsInLevels.ParallaxLayer.ParallaxEntity;
import ccx.gamestudio.masterminigolf.SwitchableFixedStepEngine;

public class GameLevel extends ManagedGameScene implements IOnSceneTouchListener, GameManager.GameLevelGoal {
	
	// ====================================================
	// CONSTANTS
	// ====================================================
	
	public static final float mLEVEL_WIDTH = 7200f;
	public static final float mTRAILING_DOTS_SPACING = 64f;
	public static final int mCRATE_POINT_VALUE = 100;
	private static final float mPHYSICS_WORLD_GRAVITY = -SensorManager.GRAVITY_EARTH * 4f;
	private static final int mPHYSICS_WORLD_POSITION_ITERATIONS = 20;
	private static final int mPHYSICS_WORLD_VELOCITY_ITERATIONS = 20;
	private static final float mCAMERA_ZOOM = 0.8888f;
	private static final float mSECONDS_FOR_LEVEL_TO_SETTLE = 1f;
	private static final float mBASE_MOVEMENT_SPEED_THRESHOLD = 1f;
	private static final float mBASE_MOVEMENT_TIME_THRESHOLD = 0.75f;
	private static final int mNUMBER_TRAILING_DOTS = 92;
	private static final String mLOADING_STEP_STRING_1 = "";
	private static final String mLOADING_STEP_STRING_2 = "";
	private static final String mLOADING_STEP_STRING_3 = "";
	private static final String mLOADING_STEP_STRING_4 = "";
	private static final String mLOADING_STEP_STRING_5 = "";
	private static final String mLOADING_STEP_STRING_6 = "";
	private static final String mLOADING_STEP_STRING_7 = "";
	private static final String mLEVEL_NUMBER_PRETEXT = "";
	private static final String mON_SCREEN_SCORE_PRETEXT = "";
	
	

	// ====================================================
	// VARIABLES
	// ====================================================
	public FixedStepPhysicsWorld mPhysicsWorld;
	public final LevelDef mLevelDef;
	public float mBasePositionX;
	public float mBasePositionY;
	public ArrayList<float[]> mBasePositions = new ArrayList<float[]>();
	public Players mPlayer;
	public int mNumberEnemiesLeft;
	public Entity mCrateLayer = new Entity();
	public boolean mIsLevelSettled = false;
	public float mBaseTotalMovementTime;
	public boolean mIsThereBaseMovement = false;
	public int TotalScorePossible;
	public int CurrentScore;
	private final MasterMiniGolfSmoothCamera mCamera = ResourceManager.getCamera();
	private static float SCALED_CAMERA_ZOOM = mCAMERA_ZOOM * ResourceManager.getInstance().cameraScaleFactorX;
	private boolean mHasCompletionTimerRun = false;
	private int mTrailingDotCounter;
	private Sprite[] mTrailingDotSprites = null;
	private Text ScoreText;
    private TextureRegion mBackGroundTR;
	private GroundLevelOne mGroundLevelOne;
    private boolean mTurretMovingUp = false;
    private boolean mTurretMovingDown = false;
    private  GrowButtonControls btnUp;
    private  GrowButtonControls btnDown;
    public GrowButtonControls btnShoot;
    public ArrayList<MagneticPhysObject<?>> mMagneticObjects = new ArrayList<>();
    public Water mWater;
    public GreenLevelOne currentGreen;
    public Hole currentHole;
    float margin = 400f;
    public boolean ballEnteredHole = false;
    // ====================================================
	// UPDATE HANDLERS
	// ====================================================
	public IUpdateHandler SettleHandlerUpdateHandler = new IUpdateHandler() {
		float mTotalElapsedTime = 0f;
		
		@Override
		public void onUpdate(final float pSecondsElapsed) {
			this.mTotalElapsedTime += pSecondsElapsed;
			if(this.mTotalElapsedTime >= mSECONDS_FOR_LEVEL_TO_SETTLE) {
				GameLevel.this.mIsLevelSettled = true;
				GameLevel.this.mPlayer.createBall();
				GameLevel.this.unregisterUpdateHandler(this);
			}
		}
		
		@Override
		public void reset() {}
	};
	
	public IUpdateHandler mMovementReportingTimer = new IUpdateHandler() {
		@Override
		public void onUpdate(final float pSecondsElapsed) {
			if(GameLevel.this.mIsThereBaseMovement) {
				GameLevel.this.mBaseTotalMovementTime += pSecondsElapsed;
				if(GameLevel.this.mBaseTotalMovementTime >= mBASE_MOVEMENT_TIME_THRESHOLD)
					if(GameLevel.this.mIsThereBaseMovement)
						GameLevel.this.mIsThereBaseMovement = false;
			}
		}
		@Override public void reset() {}
	};
	
	public IUpdateHandler onCompletionTimer = new IUpdateHandler() {
		final float COMPLETION_SECONDS_DELAY = 3f;
		public float mTotalElapsedTime = 0f;
		
		@Override
		public void onUpdate(final float pSecondsElapsed) {
			this.mTotalElapsedTime += pSecondsElapsed;
			if(this.mTotalElapsedTime >= this.COMPLETION_SECONDS_DELAY) {
				GameLevel.this.mHasCompletionTimerRun = true;
				if(!GameLevel.this.mIsThereBaseMovement) {
					if(GameLevel.this.isLevelCompleted()) {
						GameLevel.this.onLevelCompleted();
					} else {
						GameLevel.this.onLevelFailed();
					}
					GameLevel.this.unregisterUpdateHandler(this);
				}
			}
		}
		@Override public void reset() {}
	};

    private final IUpdateHandler turretUpdateHandler = new IUpdateHandler() {
        @Override
        public void onUpdate(float pSecondsElapsed) {

            if (mTurretMovingUp) {
                mPlayer.rotateTurretUp();
            }

            if (mTurretMovingDown) {
                mPlayer.rotateTurretDown();
            }
        }

        @Override
        public void reset() {}
    };
    private final IUpdateHandler holeEventHandler = new IUpdateHandler() {
        private float holeTimer = 0f;
        private final float HOLE_DELAY = 2f;

        @Override
        public void onUpdate(float pSecondsElapsed) {
            if (ballEnteredHole) {
                holeTimer += pSecondsElapsed;
                if (holeTimer >= HOLE_DELAY) {
                    // Reset
                    ballEnteredHole = false;
                    holeTimer = 0f;

                    onBallInHoleSafe();
                }
            }
        }
        @Override
        public void reset() {}
    };


	// ====================================================
	// OBJECT POOLS
	// ====================================================
	GenericPool<Text> ScoreTextPool = new GenericPool<Text>() {
		@Override
		protected Text onAllocatePoolItem() {
			return new Text(0f, 0f, ResourceManager.fontDefault48, "", 15, ResourceManager.getActivity().getVertexBufferObjectManager()) {
				Text ThisText = this;
				
				@Override
				public void onAttached() {
					this.setVisible(true);
					this.setAlpha(1f);
					this.setScale(4f);
					this.setRotation(MathUtils.random(-35f, 35f));
				}
				
				@Override
				protected void onManagedUpdate(final float pSecondsElapsed) {
					super.onManagedUpdate(pSecondsElapsed);
					this.setAlpha(this.getAlpha() - (pSecondsElapsed / 2f));
					this.setScale(this.getScaleX() - pSecondsElapsed);
					this.setRotation(this.getRotation() - (this.getRotation() * pSecondsElapsed * 2f));
					if(this.getAlpha() <= 0.1f) {
						this.setVisible(false);
						ResourceManager.getActivity().runOnUpdateThread(new Runnable() {
							@Override
							public void run() {
								ThisText.detachSelf();
								GameLevel.this.ScoreTextPool.recyclePoolItem(ThisText);
							}
						});
					}
				}
			};
		}
	};
	
	// ====================================================
	// CONSTRUCTOR
	// ====================================================
	public GameLevel(final LevelDef pLevelDef) {
		this.mLevelDef = pLevelDef;
	}
	
	// ====================================================
	// METHODS
	// ====================================================
	public void addPointsToScore(final IEntity pEntity, final int pPoints) {
		this.CurrentScore += pPoints;
		this.ScoreText.setText(mON_SCREEN_SCORE_PRETEXT.toString() + this.CurrentScore);
		final Text scorePopup = this.ScoreTextPool.obtainPoolItem();
		scorePopup.setText(String.valueOf(pPoints));
		scorePopup.setPosition(pEntity.getX(), pEntity.getY());
		if(pEntity.hasParent()) {
			pEntity.getParent().attachChild(scorePopup);
		} else {
			this.attachChild(scorePopup);
		}
	}
	
	public void createExplosion(final Vector2 pBombPos, final float pExplosionConstant) {
		final Iterator<Body> bodies = this.mPhysicsWorld.getBodies();
		while(bodies.hasNext()) {
			final Body b = bodies.next();
			if(b.getType() == BodyType.DynamicBody) {
				final Vector2 BodyPos = Vector2Pool.obtain(b.getWorldCenter());
				final Vector2 NormalizedDirectionFromBombToBody = Vector2Pool.obtain(BodyPos).sub(pBombPos).nor();
				final float dist = BodyPos.dst(pBombPos);
				final Vector2 ForceBasedOnDist = Vector2Pool.obtain(NormalizedDirectionFromBombToBody).mul(pExplosionConstant * (1f / dist));
				b.applyForce(ForceBasedOnDist, b.getWorldCenter());
				Vector2Pool.recycle(ForceBasedOnDist);
				Vector2Pool.recycle(NormalizedDirectionFromBombToBody);
				Vector2Pool.recycle(BodyPos);
			}
		}
	}
	
	public void disposeLevel() {
		this.mCamera.setChaseEntity(null);
		final HUD oldHUD = this.mCamera.getHUD();
		if(oldHUD != null) {
			oldHUD.detachSelf();
			oldHUD.dispose();
			this.mCamera.setHUD(null);
		}
        MasterMiniGolfSmoothCamera.setupForMenus();
	}
	
	public Sprite getLastTrailingDot() {
		if(this.mTrailingDotSprites == null) {
			this.resetTrailingDots();
		}
		if(this.mTrailingDotCounter == 0) {
			return this.mTrailingDotSprites[0];
		}
		return this.mTrailingDotSprites[this.mTrailingDotCounter - 1];
	}
	
	@Override
	public boolean isLevelCompleted() {
		return this.mNumberEnemiesLeft <= 0;
	}
	
	@Override
	public boolean isLevelFailed() {
		//return ((this.mNumberEnemiesLeft > 0) && (this.mCratesLeft.size() == 0));
        return false;
	}
	
	@Override
	public void onLevelCompleted() {
		if(this.mHasCompletionTimerRun) {
			// player won - show winning screen
			//SceneManager.getInstance().showLayer(LevelWonLayer.getInstance(this), false, false, true);
		} else {
			GameLevel.this.registerUpdateHandler(this.onCompletionTimer);
		}
	}
	
	@Override
	public void onLevelFailed() {
		if(this.mHasCompletionTimerRun) {
			// player lost - restart level
			//this.restartLevel();
		} else {
			GameLevel.this.registerUpdateHandler(this.onCompletionTimer);
		}
	}

    public void spawnGreenAndHole() {
        float randomX = MathUtils.random(margin, 2400f - margin);
        float randomY = MathUtils.random(margin, 1080f -margin);
        currentGreen = new GreenLevelOne(randomX, randomY, this);
        currentHole = new Hole(randomX, randomY, this);
    }


    @Override
	public void onLoadLevel() {
		GameManager.setGameLevel(this);
		
		this.ScoreTextPool.batchAllocatePoolItems(8);
		
		this.mPhysicsWorld = new FixedStepPhysicsWorld(ResourceManager.getEngine().mStepsPerSecond, new Vector2(0f, mPHYSICS_WORLD_GRAVITY), true, mPHYSICS_WORLD_VELOCITY_ITERATIONS, mPHYSICS_WORLD_POSITION_ITERATIONS);
		this.registerUpdateHandler(this.mPhysicsWorld);

        this.addLoadingStep(new LoadingRunnable(mLOADING_STEP_STRING_1, this) {
			@Override
			public void onLoad() {

				final CameraScene cameraScene = new CameraScene(ResourceManager.getEngine().getCamera());
                GameLevel.this.attachChild(cameraScene);

                final ParallaxLayer BGParallaxLayer = new ParallaxLayer(GameLevel.this.mCamera, true);

                switch (mLevelDef.mWorldIndex) {
                    case 1:
                        mBackGroundTR = GameObjectsBackGround.greenBackground;
                        break;
                    case 2:
                        mBackGroundTR = GameObjectsBackGround.desertBackground;
                        break;
                }

                final Sprite Sky = new Sprite(0f, 0f, mBackGroundTR, ResourceManager.getActivity().getVertexBufferObjectManager());
				Sky.setAnchorCenter(0f, 0f);
                BGParallaxLayer.attachParallaxEntity(new ParallaxEntity(0.5f, Sky, true));

				final ParallaxLayer CloudParallaxLayer = new ParallaxLayer(GameLevel.this.mCamera, true);
				CloudParallaxLayer.setParallaxChangePerSecond(-200f);

                cameraScene.attachChild(BGParallaxLayer);
                cameraScene.setScale(Math.max(ResourceManager.getInstance().cameraWidth / 2400, ResourceManager.getInstance().cameraHeight / 1080));
			}
		});
		this.addLoadingStep(new LoadingRunnable(mLOADING_STEP_STRING_2, this) {
			@Override
			public void onLoad() {
                for (final GroundInLevelDef curGround : GameLevel.this.mLevelDef.mGround) {
                    switch (curGround.mGroundType) {
                        case One:
                            mGroundLevelOne = new GroundLevelOne(curGround.mX, curGround.mY, GameLevel.this);
                            break;
                    }
                }
			}
		});
		this.addLoadingStep(new LoadingRunnable(mLOADING_STEP_STRING_3, this) {
			@Override
			public void onLoad() {
				GameLevel.this.mCamera.setHUD(new HUD());
				GameLevel.this.mCamera.getHUD().setVisible(false);
				GameLevel.this.ScoreText = new Text(GameLevel.this.mCamera.getWidth() / 2f, 0f, ResourceManager.fontDefault48, mON_SCREEN_SCORE_PRETEXT + "0      ", ResourceManager.getActivity().getVertexBufferObjectManager());
				GameLevel.this.ScoreText.setPosition(GameLevel.this.mCamera.getWidth() / 2f, GameLevel.this.mCamera.getHeight() - (GameLevel.this.ScoreText.getHeight() / 2f));
				GameLevel.this.ScoreText.setScale(0.75f);
				GameLevel.this.ScoreText.setAlpha(0.85f);

                final GrowButton PauseButton = new GrowButton(MenuResourceManager.btnPause.getWidth() / 2f + 50f, GameLevel.this.mCamera.getHeight() - (MenuResourceManager.btnPause.getHeight() / 2f), MenuResourceManager.btnPause) {
					@Override
					public void onClick() {
						//SceneManager.getInstance().showLayer(LevelPauseLayer.getInstance(GameLevel.this), false, true, true);
					}
				};
                PauseButton.setAlpha(0.75f);
                PauseButton.setScales(0.75f, 0.80f);

				GameLevel.this.mCamera.getHUD().attachChild(GameLevel.this.ScoreText);
				GameLevel.this.mCamera.getHUD().attachChild(PauseButton);
				GameLevel.this.mCamera.getHUD().registerTouchArea(PauseButton);
				final Text LevelIndexText = new Text(GameLevel.this.mCamera.getWidth() / 2f, GameLevel.this.mCamera.getHeight() / 2f, ResourceManager.fontDefault48, mLEVEL_NUMBER_PRETEXT, ResourceManager.getActivity().getVertexBufferObjectManager());
				LevelIndexText.setAlpha(0.85f);
				LevelIndexText.registerEntityModifier(new SequenceEntityModifier(new DelayModifier(1.5f), new MoveModifier(2f, GameLevel.this.mCamera.getWidth() / 2f, GameLevel.this.mCamera.getHeight() / 2f, GameLevel.this.mCamera.getWidth() - (LevelIndexText.getWidth() * 0.6f), GameLevel.this.mCamera.getHeight() - (LevelIndexText.getHeight() * 0.6f), EaseElasticOut.getInstance())));
				GameLevel.this.mCamera.getHUD().attachChild(LevelIndexText);
				GameLevel.this.mCamera.getHUD().setTouchAreaBindingOnActionDownEnabled(true);
				GameLevel.this.mCamera.getHUD().setTouchAreaBindingOnActionMoveEnabled(true);
			}
		});
		this.addLoadingStep(new LoadingRunnable(mLOADING_STEP_STRING_4, this) {
			@Override
			public void onLoad() {
                SplashWater.getInstance().waterContact = false;
                for (final WaterInLevelDef curWater : GameLevel.this.mLevelDef.mWater) {
                    if (Objects.requireNonNull(curWater.mWaterType) == WaterInLevelDef.WaterType.water) {
                        mWater = new Water(curWater.mX, curWater.mY, GameLevel.this);
                        SplashWater.getInstance().waterContact = true;
                    }
                }

                createObjetsInLevel();
			}
		});
		this.addLoadingStep(new LoadingRunnable(mLOADING_STEP_STRING_5, this) {
			@Override
			public void onLoad() {
				GameLevel.this.resetTrailingDots();
			}
		});
		this.addLoadingStep(new LoadingRunnable(mLOADING_STEP_STRING_6, this) {
			@Override
			public void onLoad() {

				GameLevel.this.mPlayer = new Players(0f, 250f, GameLevel.this);
				GameLevel.this.mCamera.setPlayerEntity(GameLevel.this.mPlayer);
				BouncingPowerBar.attachInstanceToHud(GameLevel.this.mCamera.getHUD());

                CreateBtnUp();
                CreateBtnDown();
                CreateBtnShoot();
                GameLevel.this.attachChild(GameLevel.this.mCrateLayer);
                spawnGreenAndHole();

            }
		});
		this.addLoadingStep(new LoadingRunnable(mLOADING_STEP_STRING_7, this) {
			@Override
			public void onLoad() {
				
				GameLevel.this.mCamera.getHUD().setVisible(true);
				GameLevel.this.mCamera.setZoomFactorDirect(SCALED_CAMERA_ZOOM);
				GameLevel.this.mCamera.setCenterDirect(GameLevel.this.mBasePositionX, GameLevel.this.mBasePositionY);
				GameLevel.this.mCamera.goToBaseForSeconds(0.8f);
				
				GameLevel.this.mCamera.setBounds(-256f, -256f, 4000f, 2000f);
				GameLevel.this.mCamera.setBoundsEnabled(true);
				
				GameManager.setGameLevelGoal(GameLevel.this);
				
				ResourceManager.getEngine().EnableFixedStep();
				
				GameLevel.this.registerUpdateHandler(GameLevel.this.SettleHandlerUpdateHandler);
			}
		});

		this.mPhysicsWorld.setContactListener(PhysObject.PHYS_OBJECT_CONTACT_LISTENER);
		
		this.setBackgroundEnabled(true);
		this.setBackground(new Background(0.1f, 0.1f, 0.1f));
		this.setOnSceneTouchListener(this);

        this.registerUpdateHandler(turretUpdateHandler);
		this.registerUpdateHandler(this.mMovementReportingTimer);
        this.registerUpdateHandler(holeEventHandler);


    }

    private void createObjetsInLevel() {
        for (final ObjectsInLevelDef curBeam : GameLevel.this.mLevelDef.mObjects) {
            switch (curBeam.mObjectsType) {
                case Bush:
                    new Bushes(curBeam.mX, curBeam.mY, GameLevel.this, 0);
                    break;
                case BushOne:
                    new Bushes(curBeam.mX, curBeam.mY, GameLevel.this, 1);
                    break;
                case BushTwo:
                    new Bushes(curBeam.mX, curBeam.mY, GameLevel.this, 2);
                    break;
                case BushThree:
                    new Bushes(curBeam.mX, curBeam.mY, GameLevel.this, 3);
                    break;
                case TreeOne:
                    new Trees(curBeam.mX, curBeam.mY, GameLevel.this, 1);
                    break;
                case Sign:
                    new Sign(curBeam.mX, curBeam.mY, GameLevel.this, 0);
                    break;
                case SignOne:
                    new Sign(curBeam.mX, curBeam.mY, GameLevel.this, 1);
                    break;
                case Mushroom:
                    new Mushroom(curBeam.mX, curBeam.mY, GameLevel.this, 0);
                    break;
                case MushroomOne:
                    new Mushroom(curBeam.mX, curBeam.mY, GameLevel.this, 1);
                    break;

            }
        }
    }
    private void CreateBtnUp() {
        btnUp = new GrowButtonControls(BouncingPowerBar.mBackGround.getX() + 150f, BouncingPowerBar.mBackGround.getY() + 75f, GamePlayers.mBtnUp) {
            @Override
            public void onClickDown() {
                mTurretMovingUp = true;
            }
            @Override
            public void onClickUp() {
                mTurretMovingUp = false;
            }
        };
        btnUp.setScales(0.75f,0.85f);
        GameLevel.this.mCamera.getHUD().registerTouchArea(btnUp);
        GameLevel.this.mCamera.getHUD().attachChild(btnUp);;
    }
    private void CreateBtnDown() {
        btnDown  = new GrowButtonControls(btnUp.getX(), btnUp.getY() - 150f, GamePlayers.mBtnDown) {
            @Override
            public void onClickDown() {
                mTurretMovingDown = true;
            }
            @Override
            public void onClickUp() {
                mTurretMovingDown = false;
            }
        };
        btnDown.setScales(0.75f,0.85f);
        GameLevel.this.mCamera.getHUD().registerTouchArea(btnDown);
        GameLevel.this.mCamera.getHUD().attachChild(btnDown);
    }
    private void CreateBtnShoot() {
        btnShoot  = new GrowButtonControls(BouncingPowerBar.mBackGround.getX() + 80f, btnDown.getY() - 140f, GamePlayers.mBtnShoot) {
            @Override
            public void onClickDown() {

                mPlayer.requestShoot();
            }
            @Override
            public void onClickUp() {

            }
        };
        btnShoot.setScales(0.70f,0.80f);
        GameLevel.this.mCamera.getHUD().registerTouchArea(btnShoot);
        GameLevel.this.mCamera.getHUD().attachChild(btnShoot);
    }
    @Override
	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
		this.mPlayer.onMagneTankTouchEvent(pScene, pSceneTouchEvent);
        return true;
	}
	
	public void reportBaseBodySpeed(final float pSpeed) {
		if(pSpeed >= mBASE_MOVEMENT_SPEED_THRESHOLD) {
			this.mBaseTotalMovementTime = 0f;
			this.mIsThereBaseMovement = true;
		}
	}
	
	public void resetTrailingDots() {
		if(this.mTrailingDotSprites == null) {
			this.mTrailingDotSprites = new Sprite[mNUMBER_TRAILING_DOTS];
			for(int i = 0; i < this.mTrailingDotSprites.length; i++) {
				this.mTrailingDotSprites[i] = new Sprite(0f, 0f, GamePlayers.mTrailingDotTR, ResourceManager.getActivity().getVertexBufferObjectManager());
				this.attachChild(this.mTrailingDotSprites[i]);
			}
		}
        for (Sprite mTrailingDotSprite : this.mTrailingDotSprites) {
            mTrailingDotSprite.setPosition(-10000, -10000);
            mTrailingDotSprite.setAlpha(1f);
            mTrailingDotSprite.setScale(1f);
        }
		this.mTrailingDotCounter = 0;
	}
	
	public void restartLevel() {
		this.disposeLevel();
		SceneManager.getInstance().showScene(new GameLevel(Level.getLevelDef(this.mLevelDef.mLevelIndex, this.mLevelDef.mWorldIndex)));
	}
	
	public void setNextTrailingDot(final float pX, final float pY) {
		if(this.mTrailingDotCounter == mNUMBER_TRAILING_DOTS) {
			return;
		}
		this.mTrailingDotSprites[this.mTrailingDotCounter].setPosition(pX, pY);
		this.mTrailingDotSprites[this.mTrailingDotCounter].setAlpha(1f - ((1f / mNUMBER_TRAILING_DOTS) * this.mTrailingDotCounter));
		this.mTrailingDotSprites[this.mTrailingDotCounter].setScale(0.5f - ((0.5f / mNUMBER_TRAILING_DOTS) * this.mTrailingDotCounter));
		this.mTrailingDotCounter++;
	}

    public void onBallInHoleSafe() {
        // destruir pelota
        if (mPlayer.mGrabbedMagneticObject != null) {
            mPlayer.mGrabbedMagneticObject.destroy();
            mPlayer.mGrabbedMagneticObject = null;
        }

        // destruir green y hole
        if (currentGreen != null) {
            currentGreen.destroy();
            currentGreen = null;
        }

        if (currentHole != null) {
            currentHole.destroy();
            currentHole = null;
        }

        spawnGreenAndHole();
    }
}