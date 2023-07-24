package com.r212.pokemon.battle.moves;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Gdx;
import com.r212.pokemon.battle.BATTLE_PARTY;
import com.r212.pokemon.battle.BattleMechanics;
import com.r212.pokemon.battle.STAT;
import com.r212.pokemon.battle.animation.BattleAnimation;
import com.r212.pokemon.battle.animation.BlinkingAnimation;
import com.r212.pokemon.battle.event.AnimationBattleEvent;
import com.r212.pokemon.battle.event.BattleEventQueuer;
import com.r212.pokemon.battle.event.HPAnimationEvent;
import com.r212.pokemon.battle.event.TextEvent;
import com.r212.pokemon.model.Pokemon;

/**
 * We're going to do some real important shit around here Morty.
 * 
 * @author r212
 */
public class DamageMove extends Move {

	public DamageMove(MoveSpecification spec, Class<? extends BattleAnimation> clazz) {
		super(spec, clazz);
	}

	@Override
	public BattleAnimation animation() {
		try {
			return animationClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException	| NoSuchMethodException | SecurityException e) {
			System.err.println(animationClass.getName()+" does not seem to have a constructor");
			e.printStackTrace();
			Gdx.app.exit();
		}
		return null;
	}

	@Override
	public String message() {
		return null;
	}

	@Override
	public boolean isDamaging() {
		return true;
	}
	
	@Override
	public int useMove(BattleMechanics mechanics, Pokemon user, Pokemon target, BATTLE_PARTY party, BattleEventQueuer broadcaster) {
		int hpBefore = target.getCurrentHitpoints();
		int damage = super.useMove(mechanics, user, target, party, broadcaster);
		
		/* Broadcast animations */
		broadcaster.queueEvent(new AnimationBattleEvent(party, animation()));
		
		/* Broadcast blinking */
		broadcaster.queueEvent(new AnimationBattleEvent(BATTLE_PARTY.getOpposite(party), new BlinkingAnimation(1f, 5)));
		
		//float hpPercentage = ((float)target.getCurrentHitpoints())/(float)target.getStat(STAT.HITPOINTS);
		
		/* Broadcast HP change */
		broadcaster.queueEvent(
				new HPAnimationEvent(
						BATTLE_PARTY.getOpposite(party), 
						hpBefore,
						target.getCurrentHitpoints(), 
						target.getStat(STAT.HITPOINTS), 
						0.5f));
		
		if (mechanics.hasMessage()) {
			broadcaster.queueEvent(new TextEvent(mechanics.getMessage(), 0.5f));
		}
		return damage;
	}

	@Override
	public Move clone() {
		return new DamageMove(spec, animationClass);
	}
}
