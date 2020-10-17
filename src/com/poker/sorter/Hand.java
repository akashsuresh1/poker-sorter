package com.poker.sorter;

import java.util.Arrays;

public class Hand {

	public Integer handValue;
	public Rank rank;
	public Card[] cards;

	public Hand(Card[] cards) {
		this.cards = cards;
	}

	public Hand(String[] cardArray) {
		if (cardArray.length != 5) {
			System.out.println("Wrong hand format");
		} else {
			Card[] cards = new Card[5];
			for (int i = 0; i < 5; i++) {
				cards[i] = new Card(cardArray[i]);
			}
			this.cards = cards;
		}
	}

	public void sortCards() {
		Arrays.sort(this.cards);
	}

	public Card getCard(int index) {
		if (index >= 5) {
			return null;
		}
		return cards[index];
	}

	public Rank getHandRank() {
		return this.rank;
	}

	public Integer getHandRankValue() {
		return this.handValue;

	}

	public void calculateRank() {

		if (this.straight() != -1 && this.sameSuit() != -1) {
			if (this.getCard(0).getValue() == 10) {
				this.rank = Rank.ROYAL_FLUSH;
				this.handValue = 10;
				return;
			} else {
				this.rank = Rank.STRAIGHT_FLUSH;
				return;
			}
		}

		if (this.four() != -1) {
			this.rank = Rank.FOUR_OF_A_KIND;
			return;
		}

		if (this.fullHouse() != -1) {
			this.rank = Rank.FULL_HOUSE;
			return;
		}

		if (this.sameSuit() != -1) {
			this.rank = Rank.FLUSH;
			return;
		}

		if (this.straight() != -1) {
			this.rank = Rank.STRAIGHT;
			return;
		}

		if (this.three() != -1) {
			this.rank = Rank.THREE_OF_A_KIND;
			return;
		}

		if (this.twoPair() != -1) {
			this.rank = Rank.TWO_PAIRS;
			return;
		}

		if (this.pair() != -1) {
			this.rank = Rank.ONE_PAIR;
			return;
		}

		this.handValue = this.getCard(4).getValue();
		this.rank = Rank.HIGH_CARD;
	}

	private int pair() {
		int previousCard = this.cards[4].getValue();
		int total = 0;
		int numberOfCards = 1;

		// check for two consecutive same card values
		for (int i = 3; i >= 0; i--) {
			if (this.cards[i].getValue() == previousCard) {
				total += this.cards[i].getValue();
				numberOfCards++;
			}

			// pair found
			if (numberOfCards == 2) {
				break;
			}
			// get the next card and perform check again
			previousCard = this.cards[i].getValue();
		}

		if (numberOfCards == 2) {
			this.handValue = total;
			return total;
		}
		return -1;
	}

	private int twoPair() {
		int previousCard = this.cards[4].getValue();
		int i = 3;
		int total = 0;
		int numberOfCards = 1;

		for (; i >= 0; i--) {
			if (this.cards[i].getValue() == previousCard) {
				total += this.cards[i].getValue();
				numberOfCards++;
			}

			// one pair found
			if (numberOfCards == 2) {
				break;
			} else {
				total = 0;
				numberOfCards = 1;
			}
			previousCard = this.cards[i].getValue();
		}

		// check for second pair
		if (numberOfCards == 2 && i > 0) {
			numberOfCards = 1;
			previousCard = this.cards[i - 1].getValue();
			for (i = i - 2; i >= 0; i--) {
				if (this.cards[i].getValue() == previousCard) {
					total += this.cards[i].getValue();
					numberOfCards++;
				}
				// second pair found
				if (numberOfCards == 2) {
					break;
				} else {
					total = 0;
					numberOfCards = 1;
				}
				previousCard = this.cards[i].getValue();
			}
		} else {
			return -1;
		}

		if (numberOfCards == 2) {
			this.handValue = total;
			return total;
		}
		return -1;
	}

	private int three() {
		int previousCard = this.cards[4].getValue();
		int total = 0;
		int numberOfCards = 1;

		for (int i = 3; i >= 0; i--) {
			if (this.cards[i].getValue() == previousCard) {
				total += this.cards[i].getValue();
				numberOfCards++;
			} else {
				total = 0;
				numberOfCards = 1;
			}

			previousCard = this.cards[i].getValue();
		}

		// triple found
		if (numberOfCards == 3) {
			this.handValue = total;
			return total;
		}
		return -1;
	}

	private int fullHouse() {
		boolean changed = false;
		int previousCard = this.cards[4].getValue();
		int total = 0;
		int numberOfCards = 1;

		for (int i = 3; i >= 0; i--) {
			// pair found
			if (this.cards[i].getValue() == previousCard) {
				total += this.cards[i].getValue();
				numberOfCards++;

			} else if (!changed) {
				changed = true;
				// pair not found
				if (numberOfCards < 2) {
					this.handValue = -1;
					return -1;
				}

				// triplet found
				if (numberOfCards == 3)
					this.handValue = total;

			} else {
				this.handValue = -1;
				return -1;
			}
			previousCard = this.cards[i].getValue();
		}
		this.handValue = total;
		return total;
	}

	private int four() {
		int prev = this.cards[4].getValue();
		int total = 0;
		int numberOfCards = 1;

		for (int i = 3; i >= 0 && numberOfCards < 4; i--) {
			if (this.cards[i].getValue() == prev) {
				total += this.cards[i].getValue();
				numberOfCards++;
			} else {
				total = 0;
				numberOfCards = 1;
			}

			prev = this.cards[i].getValue();
		}

		// four of a kind found
		if (numberOfCards == 4) {
			this.handValue = total;
			return total;
		}
		return -1;
	}

	private int sameSuit() {

		char previousCard = this.cards[0].getSuit();
		int total = this.cards[0].getValue();

		for (int i = 1; i < 5; i++) {
			// different suit found
			if (this.cards[i].getSuit() != previousCard) {
				return -1;
			}
			total += this.cards[i].getValue();
			previousCard = this.cards[i].getSuit();
		}
		this.handValue = total;
		return total;
	}

	private int straight() {

		int previousCard = this.cards[0].getValue();
		int total = 0;

		// look for sequence of 5
		for (int i = 1; i < 5; i++) {
			if (this.cards[i].getValue() != previousCard + 1) {
				return -1;
			}
			previousCard = this.cards[i].getValue();
			total += 1;
		}
		this.handValue = total;
		return total;
	}
}
