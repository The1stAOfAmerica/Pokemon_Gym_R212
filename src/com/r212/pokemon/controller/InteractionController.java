package com.r212.pokemon.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.r212.pokemon.PokemonGame;
import com.r212.pokemon.dialogue.Dialogue;
import com.r212.pokemon.model.DIRECTION;
import com.r212.pokemon.model.Tile;
import com.r212.pokemon.model.actor.Actor;

/**
 * Controller that interacts with what is in front of the player Actor.
 * 
 * @author r212
 */
public class InteractionController extends InputAdapter {
	
	private Actor a;
	private DialogueController dialogueController;
	private PokemonGame game;
	private StorylineController storylineController;


	public InteractionController(Actor a, DialogueController dialogueController, PokemonGame game, StorylineController storylineController) {
		this.a = a;
		this.dialogueController = dialogueController;
		this.game = game;
		this.storylineController = storylineController;

	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.ENTER) {
			Tile target = a.getWorld().getMap().getTile(a.getX()+a.getFacing().getDX(), a.getY()+a.getFacing().getDY());
			if (target.getActor() != null) {
				Actor targetActor = target.getActor();
				if (targetActor.getDialogue() != null) {
					if (targetActor.refaceWithoutAnimation(DIRECTION.getOpposite(a.getFacing()))){
						Dialogue dialogue = targetActor.getDialogue();
						dialogueController.startDialogue(dialogue, game, targetActor, storylineController);
					}
				}
			}

			return false;
		}
		return false;
	}

}
