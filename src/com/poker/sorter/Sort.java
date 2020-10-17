package com.poker.sorter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Sort {

	public static void main(String[] args) {
		int player1wins = 0;
		int player2wins = 0;

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				String input = reader.readLine();
				if (input == null) {
					break;
				}

				// input validation
				if (!input.matches("(?:[2-9TJQKA][SCHD] ){9}[2-9TJQKA][SCHD]")) {
					System.out.println("Wrong input format.");
					break;
				}

				String[] cards = input.split(" ");
				String[] firstHandString = Arrays.copyOfRange(cards, 0, 5);
				String[] secondHandString = Arrays.copyOfRange(cards, 5, 10);

				Hand firstHand = new Hand(firstHandString);
				Hand secondHand = new Hand(secondHandString);

				firstHand.sortCards();
				secondHand.sortCards();

				firstHand.calculateRank();
				secondHand.calculateRank();
				int result = winner(firstHand, secondHand);
				if (result == 1) {
					player1wins++;
				} else if (result == 2) {
					player2wins++;
				} else {
					System.out.println("It's a tie");
				}
			}

			System.out.println("Player 1: " + player1wins + " hands");
			System.out.println("Player 2: " + player2wins + " hands");

			System.exit(0);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int winner(Hand hand1, Hand hand2) {

		if (hand1.getHandRank().getValue() > hand2.getHandRank().getValue()) {
			return 1;
		} else if (hand1.getHandRank().getValue() < hand2.getHandRank().getValue()) {
			return 2;
		} else if (hand1.getHandRankValue() > hand2.getHandRankValue()) {
			return 1;
		} else if (hand1.getHandRankValue() < hand2.getHandRankValue()) {
			return 2;
		} else {
			// compare high cards
			for (int i = 4; i >= 0; i--) {
				if (hand1.getCard(i).getValue() > hand2.getCard(i).getValue()) {
					return 1;
				} else if (hand1.getCard(i).getValue() < hand2.getCard(i).getValue()) {
					return 2;
				}
			}
			return -1;
		}

	}
}
