package com.r212.pokemon.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author r212
 */
public interface YSortable {
	
	public float getWorldX();
	
	public float getWorldY();
	
	public TextureRegion getSprite();
	
	public float getSizeX();
	
	public float getSizeY();
}
