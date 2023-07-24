package com.r212.pokemon.worldloader;

import java.util.HashMap;

import com.r212.pokemon.dialogue.Dialogue;

/**
 * @author Hydrozoa
 */
public class DialogueDb {
	
	private HashMap<String, Dialogue> knownDialogue = new HashMap<String, Dialogue>();
	
	protected void addTerrain(String name, Dialogue dialogue) {
		knownDialogue.put(name, dialogue);
	}
	
	public Dialogue getDialogue(String name) {
		if (!knownDialogue.containsKey(name)) {
			throw new NullPointerException("Could not find Dialogue of name "+name);
		}
		return knownDialogue.get(name);
	}

}
