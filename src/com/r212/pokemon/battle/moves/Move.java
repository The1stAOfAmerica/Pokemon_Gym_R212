package com.r212.pokemon.battle.moves;

import com.r212.pokemon.battle.BATTLE_PARTY;
import com.r212.pokemon.battle.BattleMechanics;
import com.r212.pokemon.battle.animation.BattleAnimation;
import com.r212.pokemon.battle.event.BattleEventQueuer;
import com.r212.pokemon.model.Pokemon;

/**
 * Represents a move a pokemon can do in battle. 
 * 
 * Do not make new instances of these! 
 * Instead, use {@link #clone()}.
 * 
 * @author r212
 */
public abstract class Move {
	
	protected MoveSpecification spec;
	protected Class<? extends BattleAnimation> animationClass;
	
	public Move(MoveSpecification spec, Class<? extends BattleAnimation> animationClass) {
		this.spec = spec;
		this.animationClass = animationClass;
	}
	
	public int useMove(BattleMechanics mechanics, Pokemon user, Pokemon target, BATTLE_PARTY party, BattleEventQueuer broadcaster) {
//		System.out.println(target.getLevel());
		int damage = mechanics.calculateDamage(this, user, target);
				//(int)(1+(0.2*user.getLevel()));
		target.applyDamage(damage);
		return damage;
	}
	
	public abstract BattleAnimation animation();
	
	public abstract String message();
	
	/**
	 * @return If this move deals damage
	 */
	public abstract boolean isDamaging();
	
	public String getName() {
		return spec.getName();
	}
	
	public MOVE_TYPE getType(){
		return spec.getType();
	}
	
	public MOVE_CATEGORY getCategory() {
		return spec.getCategory();
	}
	
	public int getPower() {
		return spec.getPower();
	}
	
	public float getAccuracy() {
		return spec.getAccuracy();
	}
	
	public MoveSpecification getMoveSpecification() {
		return spec;
	}
	
	/**
	 * @return A copy of this instance.
	 */
	public abstract Move clone();
}
