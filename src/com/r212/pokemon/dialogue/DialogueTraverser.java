package com.r212.pokemon.dialogue;

/**
 * @author r212
 */
public class DialogueTraverser {
	
	private Dialogue dialogue;
	private DialogueNode currentNode;
	
	public DialogueTraverser(Dialogue dialogue) {
		this.dialogue = dialogue;
		currentNode = dialogue.getNode(dialogue.getStart());
	}
	
	public DialogueNode getNextNode(int pointerIndex) {
		if (currentNode.getPointers().isEmpty()) {
			return null;
		}
		DialogueNode nextNode = dialogue.getNode(currentNode.getPointers().get(pointerIndex));
		currentNode = nextNode;
		return nextNode;
	}
	
	public DialogueNode getNode() {
		return currentNode;
	}
}
