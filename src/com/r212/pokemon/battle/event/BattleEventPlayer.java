package com.r212.pokemon.battle.event;

import com.badlogic.gdx.graphics.Texture;
import com.r212.pokemon.battle.BATTLE_PARTY;
import com.r212.pokemon.battle.animation.BattleAnimation;
import com.r212.pokemon.ui.DialogueBox;
import com.r212.pokemon.ui.StatusBox;

import aurelienribon.tweenengine.TweenManager;

/**
 * @author r212
 */
public interface BattleEventPlayer {
	
	public void playBattleAnimation(BattleAnimation animation, BATTLE_PARTY party);
	
	public void setPokemonSprite(Texture region, BATTLE_PARTY party);
	
	public DialogueBox getDialogueBox();
	
	public StatusBox getStatusBox(BATTLE_PARTY party);
	
	public BattleAnimation getBattleAnimation();
	
	public TweenManager getTweenManager();
	
	public void queueEvent(BattleEvent event);
}
