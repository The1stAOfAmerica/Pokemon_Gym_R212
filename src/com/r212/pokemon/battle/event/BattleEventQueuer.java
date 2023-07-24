package com.r212.pokemon.battle.event;

/**
 * @author r212
 */
public interface BattleEventQueuer {

	public void queueEvent(BattleEvent event);
	
}
