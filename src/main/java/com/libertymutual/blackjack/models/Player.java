package com.libertymutual.blackjack.models;

import java.util.Iterator;
import java.util.Stack;

public class Player {

	private double wallet;
	private double bet;
	private Stack<Card> hand;
	
	public Stack<Card> getHand() {
		return hand;
	}

	public Player() {
		this.wallet = 200;
		this.bet = 0;
		this.hand = new Stack<Card>();
	}

	public void addCash(double cash) {
		wallet = wallet + cash;
	}

	public void placeBet(double betAmount) {
		bet = betAmount;
		wallet = wallet - bet;
	}

	public double getWallet() {
		return wallet;
	}
	
	public double getBet() {
		return bet;
	}

	public void hit(Deck deck) {
		Card card = deck.popCard();
		hand.push(card);
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
		if ((handValue > 21) && (aceInHand() == true)) {
			handValue = handValue - 10;
		} 
		return handValue;		
	}

	public int getNumberOfCardsInHand() {
		return hand.size();
	}

	public double justGetsBetBack() {
		wallet = wallet + bet;
		return wallet;
	}
	
	public double getsDoubleAmount() {
		wallet = wallet + (bet * 2);
		return wallet;
	}
	
	public double getsTripleAmount() {
		wallet = wallet + (bet * 3);
		return wallet;
	}
	
	public double losesBet() {
		return wallet;
	}

	public boolean aceInHand() {
		for (Card c : hand) {
			if (c.showCardInfo().contains("Ace")) {
				return true;
			}
		}
		return false;
	}
}
