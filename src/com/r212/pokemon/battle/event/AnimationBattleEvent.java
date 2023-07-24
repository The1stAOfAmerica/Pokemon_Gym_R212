package com.r212.pokemon.battle.event;

import com.r212.pokemon.battle.BATTLE_PARTY;
import com.r212.pokemon.battle.animation.BattleAnimation;

/**
 * A BattleEvent where a BattleAnimation is played.
 * 
 * @author r212
 */
public class AnimationBattleEvent extends BattleEvent {
	
	private BATTLE_PARTY primary;
	private BattleAnimation animation;

	public AnimationBattleEvent(BATTLE_PARTY primary, BattleAnimation animation) {
		this.animation = animation;
		this.primary = primary;
	}

	@Override
	public void begin(BattleEventPlayer player) {
		super.begin(player);
		player.playBattleAnimation(animation, primary);
	}
	
	@Override
	public void update(float delta) {
		animation.update(delta);
	}

	@Override
	public boolean finished() {
		return this.getPlayer().getBattleAnimation().isFinished();
	}

}
