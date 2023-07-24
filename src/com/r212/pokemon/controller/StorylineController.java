package com.r212.pokemon.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.r212.pokemon.PokemonGame;
import com.r212.pokemon.dialogue.DialogueTraverser;
import com.r212.pokemon.model.actor.Actor;
import com.r212.pokemon.screen.AngadBattleScreen;
import com.r212.pokemon.screen.BradyBattleScreen;
import com.r212.pokemon.screen.KiyoiBattleScreen;
import com.r212.pokemon.screen.KiyoiBattleScreen2;
import com.r212.pokemon.screen.transition.FadeInTransition;
import com.r212.pokemon.screen.transition.FadeOutTransition;
import com.r212.pokemon.ui.DialogueBox;
import com.r212.pokemon.util.Action;

import java.io.*;
import java.util.*;

import static com.r212.pokemon.screen.AngadBattleScreen.angad_defeated;
import static com.r212.pokemon.screen.BradyBattleScreen.brady_defeated;
import static com.r212.pokemon.screen.GameScreen.*;
import static com.r212.pokemon.screen.KiyoiBattleScreen.kiyoi_defeated;


public class StorylineController extends InputAdapter {

    PokemonGame game;
    DialogueBox dialogueBox;
    DialogueTraverser traverser;


    public DialogueBox getDialogueBox() {
        return dialogueBox;
    }

    public void setDialogueBox(DialogueBox dialogueBox) {
        this.dialogueBox = dialogueBox;
    }

    public Actor getTargetActor() {
        return targetActor;
    }

    public void setTargetActor(Actor targetActor) {
        this.targetActor = targetActor;
    }

    public DialogueTraverser getTraverser() {
        return traverser;
    }

    public void setTraverser(DialogueTraverser traverser) {
        this.traverser = traverser;
    }

    Actor targetActor;

    public StorylineController(PokemonGame game) {
        this.game = game;
    }

    public void update(float delta){
        if ((!angad_defeated && targetActor != null && targetActor.getName().equals("Angad")) && dialogueBox.isFinished() && traverser != null && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            background.stop();
            angad_battle_music.play();
            game.startTransition(
                    game.getGameScreen(),
                    new AngadBattleScreen(game),
                    new FadeOutTransition(0.5f, Color.BLACK, game.getTweenManager(), game.getAssetManager()),
                    new FadeInTransition(0.5f, Color.BLACK, game.getTweenManager(), game.getAssetManager()),
                    new Action(){
                        @Override
                        public void action() {
                            System.out.println("STATUS UPDATE: A fight has started");
                        }
                    });
        }
        if ((!brady_defeated && targetActor != null && targetActor.getName().equals("Brady")) && dialogueBox.isFinished() && traverser != null && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            background.stop();
            brady_battle_music.play();
            game.startTransition(
                    game.getGameScreen(),
                    new BradyBattleScreen(game),
                    new FadeOutTransition(0.5f, Color.BLACK, game.getTweenManager(), game.getAssetManager()),
                    new FadeInTransition(0.5f, Color.BLACK, game.getTweenManager(), game.getAssetManager()),
                    new Action(){
                        @Override
                        public void action() {
                            System.out.println("STATUS UPDATE: A fight has started");
                        }
                    });
        }
        if ((!kiyoi_defeated && targetActor != null && targetActor.getName().equals("Kiyoi")) && dialogueBox.isFinished() && traverser != null && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            background.stop();
            kiyoi_battle1_music.play();
            game.startTransition(
                    game.getGameScreen(),
                    new KiyoiBattleScreen(game),
                    new FadeOutTransition(0.5f, Color.BLACK, game.getTweenManager(), game.getAssetManager()),
                    new FadeInTransition(0.5f, Color.BLACK, game.getTweenManager(), game.getAssetManager()),
                    new Action(){
                        @Override
                        public void action() {
                            System.out.println("STATUS UPDATE: A fight has started");
                        }
                    });
        }
    }
}
