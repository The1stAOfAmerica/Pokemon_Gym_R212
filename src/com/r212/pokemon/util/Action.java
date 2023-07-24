package com.r212.pokemon.util;

/**
 * Contains a single method. 
 * 
 * This is used to pass functionality to TransitionScreen#startTransition, 
 * about what's going to happen during a transition.
 * 
 * Also used to attach execution to UI elements.
 * 
 * @author r212
 */
public interface Action {

	public void action();
	
}
