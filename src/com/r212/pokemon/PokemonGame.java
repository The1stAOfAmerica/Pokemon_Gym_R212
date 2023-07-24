package com.r212.pokemon;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.r212.pokemon.battle.animation.AnimatedBattleSprite;
import com.r212.pokemon.battle.animation.BattleAnimation;
import com.r212.pokemon.battle.animation.BattleAnimationAccessor;
import com.r212.pokemon.battle.animation.BattleSprite;
import com.r212.pokemon.battle.animation.BattleSpriteAccessor;
import com.r212.pokemon.battle.moves.MoveDatabase;
import com.r212.pokemon.model.world.World;
import com.r212.pokemon.screen.*;
import com.r212.pokemon.screen.transition.BattleBlinkTransition;
import com.r212.pokemon.screen.transition.BattleBlinkTransitionAccessor;
import com.r212.pokemon.screen.transition.Transition;
import com.r212.pokemon.util.Action;
import com.r212.pokemon.util.SkinGenerator;
import com.r212.pokemon.worldloader.DialogueDb;
import com.r212.pokemon.worldloader.DialogueLoader;
import com.r212.pokemon.worldloader.LTerrainDb;
import com.r212.pokemon.worldloader.LTerrainLoader;
import com.r212.pokemon.worldloader.LWorldObjectDb;
import com.r212.pokemon.worldloader.LWorldObjectLoader;
import com.r212.pokemon.worldloader.WorldLoader;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * Topmost class of the game. Logic is delegated to Screens from here.
 * 
 * @author r212
 */
public class PokemonGame extends Game {
	
	private GameScreen gameScreen;
	private BattleScreen battleScreen;
	private TransitionScreen transitionScreen;
	
	private MoveDatabase moveDatabase;
	
	private AssetManager assetManager;
	
	private Skin skin;
	
	private TweenManager tweenManager;
	
	private ShaderProgram overlayShader;
	private ShaderProgram transitionShader;
	
	private String version;

	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	public BitmapFont font;
	private IntroScreen introScreen;
	private BradyBattleScreen bradyBattleScreen;
	private AngadBattleScreen angadBattleScreen;

	@Override
	public void create() {
		/*
		 * LOAD VERSION
		 */
		version = Gdx.files.internal("version.txt").readString();
		System.out.println("R212 Gym");
		Gdx.app.getGraphics().setTitle("R212 Gym");
		
		/*
		 * LOADING SHADERS
		 */
		ShaderProgram.pedantic = false;
		overlayShader = new ShaderProgram(
				Gdx.files.internal("res/shaders/overlay/vertexshader.txt"), 
				Gdx.files.internal("res/shaders/overlay/fragmentshader.txt"));
		if (!overlayShader.isCompiled()) {
			System.out.println(overlayShader.getLog());
		}
		transitionShader = new ShaderProgram(
				Gdx.files.internal("res/shaders/transition/vertexshader.txt"), 
				Gdx.files.internal("res/shaders/transition/fragmentshader.txt"));
		if (!transitionShader.isCompiled()) {
			System.out.println(transitionShader.getLog());
		}
		
		/*
		 * SETTING UP TWEENING
		 */
		tweenManager = new TweenManager();
		Tween.registerAccessor(BattleAnimation.class, new BattleAnimationAccessor());
		Tween.registerAccessor(BattleSprite.class, new BattleSpriteAccessor());
		Tween.registerAccessor(AnimatedBattleSprite.class, new BattleSpriteAccessor());
		Tween.registerAccessor(BattleBlinkTransition.class, new BattleBlinkTransitionAccessor());
		
		/*
		 * LOADING ASSETS
		 */
		assetManager = new AssetManager();
		assetManager.setLoader(LWorldObjectDb.class, new LWorldObjectLoader(new InternalFileHandleResolver()));
		assetManager.setLoader(LTerrainDb.class, new LTerrainLoader(new InternalFileHandleResolver()));
		assetManager.setLoader(DialogueDb.class, new DialogueLoader(new InternalFileHandleResolver()));
		assetManager.setLoader(World.class, new WorldLoader(new InternalFileHandleResolver()));
		
		assetManager.load("res/LTerrain.xml", LTerrainDb.class);
		assetManager.load("res/LWorldObjects.xml", LWorldObjectDb.class);
		assetManager.load("res/Dialogues.xml", DialogueDb.class);
		
		assetManager.load("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		assetManager.load("res/graphics_packed/ui/uipack.atlas", TextureAtlas.class);
		assetManager.load("res/graphics_packed/battle/battlepack.atlas", TextureAtlas.class);
		assetManager.load("res/graphics/pokemon/bulbasaur.png", Texture.class);
		assetManager.load("res/graphics/pokemon/slowpoke.png", Texture.class);
		assetManager.load("res/graphics/pokemon/absol.png", Texture.class);
		assetManager.load("res/graphics/pokemon/machamp.png", Texture.class);
		assetManager.load("res/graphics/pokemon/braviary.png", Texture.class);
		assetManager.load("res/graphics/pokemon/lucario.png", Texture.class);
		assetManager.load("res/graphics/pokemon/arceus.png", Texture.class);
		assetManager.load("res/graphics/pokemon/magikarp.png", Texture.class);




		for (int i = 0; i < 32; i++) {
			assetManager.load("res/graphics/statuseffect/attack_"+i+".png", Texture.class);
		}
		assetManager.load("res/graphics/statuseffect/white.png", Texture.class);
		
		for (int i = 0; i < 13; i++) {
			assetManager.load("res/graphics/transitions/transition_"+i+".png", Texture.class);
		}
		assetManager.load("res/font/small_letters_font.fnt", BitmapFont.class);
		
		File dir = new File("res/worlds/");
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				System.out.println("Loading world "+child.getPath());
				assetManager.load(child.getPath(), World.class);
		    }
		}
		
		assetManager.finishLoading();
		
		skin = SkinGenerator.generateSkin(assetManager);
		
		moveDatabase = new MoveDatabase();

		battleScreen = new BattleScreen(this);
		gameScreen = new GameScreen(this);
		bradyBattleScreen = new BradyBattleScreen(this);
		angadBattleScreen = new AngadBattleScreen(this);
		transitionScreen = new TransitionScreen(this);
		introScreen = new IntroScreen(this);

		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("res/font/o_1G3Uz344U2ofL2weCCBOZE.ttf.fnt"));

		this.setScreen(introScreen);
	}

//	public static void setty(){
//		setScreen(battleScreen);
//	}

	@Override
	public void render() {
		//System.out.println(Gdx.graphics.getFramesPerSecond());
		
		/* UPDATE */
		tweenManager.update(Gdx.graphics.getDeltaTime());
		if (getScreen() instanceof AbstractScreen) {
			((AbstractScreen)getScreen()).update(Gdx.graphics.getDeltaTime());
		}
		
		/* RENDER */
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getScreen().render(Gdx.graphics.getDeltaTime());
	}
	
	public AssetManager getAssetManager() {
		return assetManager;
	}
	
	public Skin getSkin() {
		return skin;
	}
	
	public TweenManager getTweenManager() {
		return tweenManager;
	}
	
	public GameScreen getGameScreen() {
		return gameScreen;
	}
	
	public BattleScreen getBattleScreen() {
		return battleScreen;
	}
	
	public void startTransition(AbstractScreen from, AbstractScreen to, Transition out, Transition in, Action action) {
		transitionScreen.startTransition(from, to, out, in, action);
	}
	
	public ShaderProgram getOverlayShader() {
		return overlayShader;
	}
	
	public ShaderProgram getTransitionShader() {
		return transitionShader;
	}
	
	public MoveDatabase getMoveDatabase() {
		return moveDatabase;
	}
	
	public String getVersion() {
		return version;
	}
}
