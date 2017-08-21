package com.libertymutual.blackjack.models;

public class Card {

	private String name;
	private String suit;
	private int value;

	public Card(String name, String suit, int value) {
		this.name = name;
		this.suit = suit;
		this.value = value;
	}

	public String showCardInfo() {
		String cardInfo = name + " of " + suit;
		return cardInfo;
	}

	public int getCardValue() {
		int cardValue = this.value;
		return cardValue;
	}

}
