package com.r212.pokemon.battle;

import com.r212.pokemon.battle.animation.FaintingAnimation;
import com.r212.pokemon.battle.animation.PokeballAnimation;
import com.r212.pokemon.battle.event.*;
import com.r212.pokemon.battle.moves.Move;
import com.r212.pokemon.model.Pokemon;

/**
 * A 100% real Pokemon fight! Right in your livingroom.
 *
 * @author r212
 */
public class KiyoiBattle implements BattleEventQueuer {

	public enum STATE {
		READY_TO_PROGRESS,
		SELECT_NEW_POKEMON,
		RAN,
		WIN,
		LOSE,
	}

	private STATE state;

	private BattleMechanics mechanics;

	private Pokemon player;
	private Pokemon opponent;

	private Trainer playerTrainer;
	private Trainer opponentTrainer;

	private BattleEventPlayer eventPlayer;

	public KiyoiBattle(Trainer player, Pokemon opponent) {
		this.playerTrainer = player;
		this.player = player.getPokemon(0);
		this.opponent = opponent;
		mechanics = new BattleMechanics();
		this.state = STATE.READY_TO_PROGRESS;
	}

	public KiyoiBattle(Trainer player, Trainer opponent) {
		this.playerTrainer = player;
		this.player = player.getPokemon(0);
		this.opponentTrainer = opponent;
		this.opponent = opponent.getPokemon(0);
		mechanics = new BattleMechanics();
		this.state = STATE.READY_TO_PROGRESS;
	}

	/**
	 * Plays appropiate animation for starting a battle
	 */
	public void beginBattle() {
		//PokeSpriteEvent p = new PokeSpriteEvent(opponent.getSprite(), BATTLE_PARTY.OPPONENT);
		//TextEvent t = new TextEvent("Go "+player.getName()+"!", 1f);
		//PokeSpriteEvent ps5 = new PokeSpriteEvent(player.getSprite(), BATTLE_PARTY.PLAYER);
		//AnimationBattleEvent abe = new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new PokeballAnimation());
		//abe.begin(eventPlayer);
		queueEvent(new PokeSpriteEvent(opponent.getSprite(), BATTLE_PARTY.OPPONENT));
		queueEvent(new TextEvent("Go "+player.getName()+"!", 1f));
		queueEvent(new PokeSpriteEvent(player.getSprite(), BATTLE_PARTY.PLAYER));
		queueEvent(new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new PokeballAnimation()));
	}


	/**
	 * Progress the battle one turn.
	 * @param input		Index of the move used by the player
	 */
	public void progress(int input) {
		if (state != STATE.READY_TO_PROGRESS) {
			return;
		}
		if (mechanics.goesFirst(player, opponent)) {
			playTurn(BATTLE_PARTY.PLAYER, input);
			if (state == STATE.READY_TO_PROGRESS) {
				playTurn(BATTLE_PARTY.OPPONENT, 0);
			}
		} else {
			playTurn(BATTLE_PARTY.OPPONENT, 0);
			if (state == STATE.READY_TO_PROGRESS) {
				playTurn(BATTLE_PARTY.PLAYER, input);
			}
		}
		/*
		 * XXX: Status effects go here.
		 */
	}

	/**
	 * Sends out a new Pokemon, in the case that the old one fainted.
	 * This will NOT take up a turn.
	 * @param pokemon	Pokemon the trainer is sending in
	 */
	public void chooseNewPokemon(Pokemon pokemon) {
		this.player = pokemon;
		queueEvent(new HPAnimationEvent(
				BATTLE_PARTY.OPPONENT,
				pokemon.getCurrentHitpoints(),
				pokemon.getCurrentHitpoints(),
				pokemon.getStat(STAT.HITPOINTS),
				0f));
		queueEvent(new PokeSpriteEvent(pokemon.getSprite(), BATTLE_PARTY.OPPONENT));
		queueEvent(new NameChangeEvent(pokemon.getName(), BATTLE_PARTY.OPPONENT));
		queueEvent(new TextEvent("Go get 'em, "+pokemon.getName()+"!"));
		queueEvent(new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new PokeballAnimation()));
		this.state = STATE.READY_TO_PROGRESS;
	}

	public void chooseNewPokemon(Pokemon pokemon, boolean trainer) {
		if(trainer) {
			this.player = pokemon;
			queueEvent(new HPAnimationEvent(
					BATTLE_PARTY.PLAYER,
					pokemon.getCurrentHitpoints(),
					pokemon.getCurrentHitpoints(),
					pokemon.getStat(STAT.HITPOINTS),
					0f));
			queueEvent(new PokeSpriteEvent(pokemon.getSprite(), BATTLE_PARTY.PLAYER));
			queueEvent(new NameChangeEvent(pokemon.getName(), BATTLE_PARTY.PLAYER));
			queueEvent(new TextEvent("Go get 'em, " + pokemon.getName() + "!"));
			queueEvent(new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new PokeballAnimation()));
			this.state = STATE.READY_TO_PROGRESS;
		}
		else {
			this.opponent = pokemon;
			queueEvent(new HPAnimationEvent(
					BATTLE_PARTY.OPPONENT,
					pokemon.getCurrentHitpoints(),
					pokemon.getCurrentHitpoints(),
					pokemon.getStat(STAT.HITPOINTS),
					0f));
			queueEvent(new PokeSpriteEvent(pokemon.getSprite(), BATTLE_PARTY.OPPONENT));
			queueEvent(new NameChangeEvent(pokemon.getName(), BATTLE_PARTY.OPPONENT));
			queueEvent(new TextEvent("Go get 'em, " + pokemon.getName() + "!"));
			queueEvent(new AnimationBattleEvent(BATTLE_PARTY.OPPONENT, new PokeballAnimation()));
			this.state = STATE.READY_TO_PROGRESS;
		}
	}

	/**
	 * Attempts to run away
	 */
	public void attemptRun() {
		queueEvent(new TextEvent("Got away successfully...", true));
		this.state = STATE.RAN;
	}

	private void playTurn(BATTLE_PARTY user, int input) {
		BATTLE_PARTY target = BATTLE_PARTY.getOpposite(user);

		Pokemon pokeUser = null;
		Pokemon pokeTarget = null;
		if (user == BATTLE_PARTY.PLAYER) {
			pokeUser = player;
			pokeTarget = opponent;
		} else if (user == BATTLE_PARTY.OPPONENT) {
			pokeUser = opponent;
			pokeTarget = player;
		}

		Move move = pokeUser.getMove(input);

		/* Broadcast the text graphics */
		queueEvent(new TextEvent(pokeUser.getName()+" used\n"+move.getName().toUpperCase()+"!", 0.5f));

		if (mechanics.attemptHit(move, pokeUser, pokeTarget)) {
			move.useMove(mechanics, pokeUser, pokeTarget, user, this);
		} else { // miss
			/* Broadcast the text graphics */
			queueEvent(new TextEvent(pokeUser.getName()+"'s\nattack missed!", 0.5f));
		}

		if (player.isFainted()) {
			queueEvent(new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new FaintingAnimation()));
			boolean anyoneAlive = false;
			for (int i = 0; i < getPlayerTrainer().getTeamSize(); i++) {
				if (!getPlayerTrainer().getPokemon(i).isFainted()) {
					anyoneAlive = true;
					break;
				}
			}
			if (anyoneAlive) {
				queueEvent(new TextEvent(player.getName()+" fainted!", true));
				this.state = STATE.SELECT_NEW_POKEMON;
			} else {
				queueEvent(new TextEvent("Unfortunately, you've lost...", true));
				this.state = STATE.LOSE;
			}
		} else if (opponent.isFainted()) {
			queueEvent(new AnimationBattleEvent(BATTLE_PARTY.OPPONENT, new FaintingAnimation()));
			boolean anyoneAlive = false;
			for (int i = 0; i < getOpponentTrainer().getTeamSize(); i++) {
				if (!getOpponentTrainer().getPokemon(i).isFainted()) {
					anyoneAlive = true;
					queueEvent(new TextEvent(opponent.getName()+" fainted!", true));
					this.chooseNewPokemon(this.getOpponentTrainer().getPokemon(i), false);
					break;
				}
			}
			//if (anyoneAlive) {
			//	queueEvent(new TextEvent(opponent.getName()+" fainted!", true));
			//	for (int i = 0; i < this.getOpponentTrainer().getTeamSize(); i++) {
			//		if (!this.getPlayerTrainer().getPokemon(i).isFainted()) {
			//			this.chooseNewPokemon(this.getPlayerTrainer().getPokemon(i));
			//			break;
			//		}
			//	}
			//}
			if(!anyoneAlive) {
				queueEvent(new TextEvent("Fine. It's time to get serious.", true));
				this.state = STATE.WIN;
			}
		}
	}

	public Pokemon getPlayerPokemon() {
		return player;
	}

	public Pokemon getOpponentPokemon() {
		return opponent;
	}

	public Trainer getPlayerTrainer() {
		return playerTrainer;
	}

	public Trainer getOpponentTrainer() {
		return opponentTrainer;
	}

	public STATE getState() {
		return state;
	}

	public void setEventPlayer(BattleEventPlayer player) {
		this.eventPlayer = player;
	}

	@Override
	public void queueEvent(BattleEvent event) {
		eventPlayer.queueEvent(event);
	}
}