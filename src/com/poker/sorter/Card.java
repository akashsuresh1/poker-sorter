package com.poker.sorter;

public class Card implements Comparable<Card> {
	private int value;
	private char suit;

	public Card(String cardValue) {

		// set 1st character as the value of the card
		char val = cardValue.charAt(0);
		switch (val) {
		case 'T':
			this.value = 10;
			break;
		case 'J':
			this.value = 11;
			break;
		case 'Q':
			this.value = 12;
			break;
		case 'K':
			this.value = 13;
			break;
		case 'A':
			this.value = 14;
			break;
		default:
			this.value = Integer.parseInt(Character.toString(val));
			break;
		}

		// set 2nd character as the suit of the card
		this.suit = cardValue.charAt(1);

	}

	public int compareTo(Card aCard) {
		// return difference in value
		return this.value - ((Card) aCard).getValue();

	}

	public String toString() {
		// readable value of card
		return String.valueOf(this.value) + this.suit;
	}

	public int getValue() {
		return this.value;
	}

	public char getSuit() {
		return this.suit;
	}
}
