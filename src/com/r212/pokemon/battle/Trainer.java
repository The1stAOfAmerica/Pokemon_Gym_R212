package com.r212.pokemon.battle;

import java.util.ArrayList;
import java.util.List;

import com.r212.pokemon.model.Pokemon;

/**
 * @author r212
 */
public class Trainer {
	
	private List<Pokemon> team;
	
	public Trainer(Pokemon pokemon) {
		team = new ArrayList<Pokemon>();
		team.add(pokemon);
	}
	
	public boolean addPokemon(Pokemon pokemon) {
		if (team.size() >= 6) {
			return false;
		} else {
			team.add(pokemon);
			return true;
		}
	}

	public void addLevel() {
		for(int i = 0; i < team.size(); i++) {
			team.get(i).addLevel();
		}
	}
	
	public Pokemon getPokemon(int index) {
		return team.get(index);
	}
	
	public int getTeamSize() {
		return team.size();
	}
}
