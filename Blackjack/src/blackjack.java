import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class blackjack {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Deck set = new Deck();
		boolean doAltWin = false;
		set.shuffle();
		
		Hand userHand = new Hand(set);
		Hand dealerHand = new Hand(set);
		
		
		while (true) {
			if (Hand.checkWinner(userHand, dealerHand, doAltWin) == true) {
				System.out.println("Player's Hand:\n------------");
				userHand.displayHand();
				System.out.println("Dealer's Hand:\n------------");
				dealerHand.displayHand();
				in.close();
				break;
			}
			else {
				System.out.println("Player's Hand:\n------------");
				userHand.displayHand();
				System.out.println("Dealer's Hand:\n------------");
				System.out.println(dealerHand.hand.get(0).name);
				System.out.println("Face down card\n\n");
				System.out.println("Would you like to hit or stand?\n1. Hit\n2. Stand");
				int move = in.nextInt();
				
				if (move == 1) {
					userHand.hit();
				}
				else if (move == 2) {
					while (dealerHand.calcHand() < 17) {
						dealerHand.hit();
						}	
					doAltWin = true;
				}
				else {
					System.out.println("\nThat is not an option.\n");
				}
			}
			
		}
	}
}

class Deck{
	ArrayList<Card> deck;
	String suits[] = {"spades", "hearts", "diamonds", "clubs"};
	Deck(){
		
		this.deck = new ArrayList<Card>();
		for (int amt = 0; amt < 4; amt++) {
			for (int count = 2; count < 15; count++) {
				if (count == 11) {
					deck.add(new Card(10, suits[amt], "Jack of " + suits[amt]));
				}
				else if (count == 12) {
					deck.add(new Card(10, suits[amt], "Queen of " + suits[amt]));
				}
				else if (count == 13) {
					deck.add(new Card(10, suits[amt], "King of " + suits[amt]));
				}
				else if (count == 14) {
					deck.add(new Card(11, suits[amt], "Ace of " + suits[amt]));
				}
				else {
					deck.add(new Card(count, suits[amt], count + " of " + suits[amt]));
				}
			}
		}
	}



	public void shuffle() {
		Collections.shuffle(deck);
	}

	public Card dealTop() {
		Card card = this.deck.get(0);
		this.deck.remove(0);
		return card;
		
	}

}

class Hand{
	
	ArrayList<Card> hand;
	
	Deck set;
	
	public Hand(Deck set) {
		this.set = set;
		this.hand = new ArrayList<Card>();
		this.hit();
		this.hit();
		
	}
	public void hit() {
		this.hand.add(set.dealTop());
		this.calcHand();
	}
	
	public Card displayHand() {
		for (int x = 0; x < this.hand.size(); x++) {
			System.out.println(this.hand.get(x).name);
		}
		System.out.println("Hand Value: " + this.calcHand() + "\n");
		return null;
	}
	
	public int calcHand() {
		int total = 0;
		for (int x = 0; x < this.hand.size(); x++) {
			total += this.hand.get(x).value;
		}
		if (total > 21) {
			for (int x = 0; x < this.hand.size(); x++) {
				if (this.hand.get(x).value == 11) {
					this.hand.get(x).value = 1;
					total -= 10;
				}
			}
		}
		return total;
	}
	
	public static boolean checkWinner(Hand player, Hand dealer, boolean doAltWin) {
		player.calcHand();
		dealer.calcHand();
		if (player.calcHand() == 21) {
			System.out.println("\n\nThe player won the game!\n");
			return true;
		}
		if (dealer.calcHand() == 21) {
			System.out.println("\n\nThe dealer won the game!\n");
			return true;
		}
		else if (player.calcHand() > 21 && dealer.calcHand() < 22) {
			System.out.println("\n\nThe player bust! The dealer wins!\n");
			return true;
		}
		
		else if (player.calcHand() < 22 && dealer.calcHand() > 21) {
			System.out.println("\n\nThe dealer bust! The player wins!\n");
			return true;
		}
		
		else if (player.calcHand() > 21 && dealer.calcHand() > 21) {
			System.out.println("\n\nThe player and the dealer bust! It's a tie!\n");
			return true;
		}
		
		else if (doAltWin == true) {
			int playerDiff = 21 - player.calcHand();
			int dealerDiff = 21 - dealer.calcHand();
			if (playerDiff < dealerDiff) {
				System.out.println("\n\nThe player won the game!");
				return true;
			}
			else if (dealerDiff < playerDiff) {
				System.out.println("\n\nThe dealer won the game!");
				return true;
			}
			else if (playerDiff == dealerDiff) {
				System.out.println("\n\nThe player and the dealer tied!");
				return true;
			}
		}
		return false;
	}
	
	
}

class Card{
	int value;
	String suit;
	String name;
	Card(int value, String suit, String name){
		this.value = value;
		this.suit = suit;
		this.name = name;
	}
}