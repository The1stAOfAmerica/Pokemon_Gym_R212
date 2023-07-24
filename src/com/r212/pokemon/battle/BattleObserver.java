package com.r212.pokemon.battle;

import com.r212.pokemon.battle.event.BattleEvent;

/**
 * Objects can implement this interface and subscribe to a {@link Battle}.
 * 
 * @author r212
 */
public interface BattleObserver {
	
	/**
	 * {@link com.r212.pokemon.battle.event.BattleEvent} spat out from a {@link Battle}.
	 * @param event	Catch it fast, and get free visuals for a live fight.
	 */
	public void queueEvent(BattleEvent event);
}
