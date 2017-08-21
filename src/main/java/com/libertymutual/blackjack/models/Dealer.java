package com.libertymutual.blackjack.models;

import java.util.Iterator;
import java.util.Stack;

public class Dealer {
	
	private Stack<Card> hand;
	
	public Stack<Card> getHand() {
		return hand;
	}

	public Dealer() {
		this.hand = new Stack<Card>();
	}
	
	public void hit(Deck deck) {
		Card card = deck.popCard();
		hand.push(card);
	}
	
	public String showHandWithHoleCard() {
		String handInfoWithHoleCard = new String();
		String holeCard = "-HoleCard-";
		Iterator<Card> iter = hand.iterator();
		while (iter.hasNext()) {
			handInfoWithHoleCard = holeCard + " | " + iter.next().showCardInfo() + " | ";		
		}
		return handInfoWithHoleCard;
	}
	
	public String showHand() {
		String handInfo = new String();
		Iterator<Card> iter = hand.iterator();
		while (iter.hasNext()) {
			handInfo = handInfo + " | " + iter.next().showCardInfo() + " | ";		
		}
		return handInfo;
	}
	
	public int calculateHandValue() {
		int handValue = 0;
		Iterator<Card> iter = hand.iterator();
		while (iter.hasNext()) {
			handValue = handValue + iter.next().getCardValue();
		}
		return handValue;
	}
	
	public int getNumberOfCardsInHand() {
		return hand.size();
	}
	
}
