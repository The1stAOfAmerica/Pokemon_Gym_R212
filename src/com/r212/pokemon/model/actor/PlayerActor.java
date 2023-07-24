package com.r212.pokemon.model.actor;

import com.r212.pokemon.model.world.World;
import com.r212.pokemon.model.world.cutscene.CutscenePlayer;
import com.r212.pokemon.util.AnimationSet;

/**
 * @author r212
 */
public class PlayerActor extends Actor {
	
	private CutscenePlayer cutscenePlayer;

	public PlayerActor(World world, int x, int y, AnimationSet animations,  CutscenePlayer cutscenePlayer) {
		super(world, x, y, animations);
		this.cutscenePlayer = cutscenePlayer;
	}
	
	public CutscenePlayer getCutscenePlayer() {
		return cutscenePlayer;
	}
}
