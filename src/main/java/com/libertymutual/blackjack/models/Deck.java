package com.libertymutual.blackjack.models;

import java.util.Collections;
import java.util.Stack;

public class Deck {

	private Stack<Card> deck;

	private String[] name = new String[] { "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
			"Jack", "Queen", "King", "Ace" };
	private String[] suit = new String[] { "Hearts", "Clubs", "Diamonds", "Spades" };
	private int[] value = new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11 };

	public Deck() {
		this.deck = new Stack<Card>();
	}

	public void populateDeck() {
		int i = 0;
		Card card;

		while (i < value.length) {
			deck.push(card = new Card(name[i], suit[0], value[i]));
			deck.push(card = new Card(name[i], suit[1], value[i]));
			deck.push(card = new Card(name[i], suit[2], value[i]));
			deck.push(card = new Card(name[i], suit[3], value[i]));
			i = i + 1;
		}
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

	public Card popCard() {
		return deck.pop();
	}

	public int getDeckSize() {
		return deck.size();
	}
}