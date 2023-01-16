import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//TODO: Check for books every time a card is drawn

public class goFish {

    public static void main(String[] args) throws InterruptedException {

        clearScr();

        Scanner in = new Scanner(System.in);

        Deck set = new Deck();
        set.shuffle();
        
        Hand userHand = new Hand(set);
        Hand dealerHand = new Hand(set);
        
        int userBooks = 0;
        int aiBooks = 0;
        int totalBooks = userBooks + aiBooks;
        
        boolean playerTurn = true;
        
        clearScr();
        while (totalBooks < 13) {
            String randChoice = dealerHand.getCard((int) (Math.random() * dealerHand.size())).idName;
            if (userHand.size() < 7 && set.size() > 0) {
                System.out.println("You have " + userHand.size() + " cards in your hand. Draw until you have 7 cards.");
                Thread.sleep(3000);
                while (userHand.size() < 7 || set.size() > 0) {
                    userHand.drawCard();
                    System.out.println("You drew a " + userHand.getCard(userHand.size() - 1).idName + ". You have " + userHand.size() + " cards in your hand.");
                    Thread.sleep(3000);
                    int occur = 0;
                    for (int x = 0; x < userHand.size(); x++) {
                        if (userHand.getCard(x).idName.equals(userHand.getCard(userHand.size() - 1).idName)) {
                            occur++;
                        }
                        if (occur == 4) {
                            Thread.sleep(3000);
                            userBooks++;
                            System.out.println("You have a set of " + userHand.getCard(userHand.size() - 1).idName + "s. You now have " + userBooks + " book(s).");
                            Thread.sleep(3000);
                            clearScr();
                            for (int i = 0; i < userHand.size(); i++) {
                                if (userHand.getCard(i).idName.equals(userHand.getCard(userHand.size() - 1).idName)) {
                                    userHand.removeCard(i);
                                    i--;
                                }
                            }
                        }
                    }
                }
                if (userHand.size() >= 7) {
                    break;
                }
                if (set.size() < 1) {
                    clearScr();
                    System.out.println("All cards have been drawn. Good luck.");
                    Thread.sleep(2500);
                }
            }
            if (dealerHand.size() < 7 && set.size() > 0) {
                System.out.println("The dealer has " + dealerHand.size() + " cards in their hand. They will draw until they have 7 cards.");
                Thread.sleep(3000);
                while (dealerHand.size() < 7 || set.size() > 0) {
                    dealerHand.drawCard();
                    System.out.println("They drew a card. They have " + dealerHand.size() + " cards in their hand.");
                    Thread.sleep(3000);
                    int occur = 0;
                    for (int x = 0; x < dealerHand.size(); x++) {
                        if (dealerHand.getCard(x).idName.equals(dealerHand.getCard(dealerHand.size() - 1).idName)) {
                            occur++;
                        }
                        if (occur == 4) {
                            Thread.sleep(3000);
                            aiBooks++;
                            System.out.println("They have a set of " + dealerHand.getCard(dealerHand.size() - 1).idName + "s. They now have " + aiBooks + " book(s).");
                            Thread.sleep(3000);
                            clearScr();
                            for (int i = 0; i < dealerHand.size(); i++) {
                                if (dealerHand.getCard(i).idName.equals(dealerHand.getCard(dealerHand.size() - 1).idName)) {
                                    dealerHand.removeCard(i);
                                    i--;
                                }
                            }
                        }
                    }
                    if (dealerHand.size() >= 7) {
                        break;
                    }
                    if (set.size() < 1) {
                        clearScr();
                        System.out.println("All cards have been drawn. Good luck.");
                        Thread.sleep(2500);
                    }
                }
            }
            clearScr();
            if (playerTurn) {
                displayScreen(userHand, dealerHand, userBooks, aiBooks);
                System.out.println();
                String askFor = in.nextLine();
                if (dealerHand.checkIfInHand(askFor) == true && userHand.checkIfInHand(askFor) == true) {
                    int amtTaken = 0;
                    for (int y = 0; y < dealerHand.size(); y++) {
                        if (dealerHand.getCard(y).idName.equals(askFor)) {
                            Card theCard = new Card(dealerHand.getCard(y).value, dealerHand.getCard(y).idName);
                            userHand.addCard(theCard);
                            dealerHand.removeCard(y);
                            y--;
                            amtTaken++;
                        }
                    }
                    System.out.println("You took " + amtTaken + " " + askFor + "(s) from the computer!");
                    Thread.sleep(3000);
                    clearScr();
                    int occur = 0;
                    for (int x = 0; x < userHand.size(); x++) {
                        if (userHand.getCard(x).idName.equals(askFor)) {
                            occur++;
                        }
                        if (occur == 4) {
                            userBooks++;
                            System.out.println("You have a set of " + askFor + "s. You now have " + userBooks + " book(s).");
                            Thread.sleep(3000);
                            clearScr();
                            for (int i = 0; i < userHand.size(); i++) {
                                if (userHand.getCard(i).idName.equals(askFor)) {
                                    userHand.removeCard(i);
                                    i--;
                                }
                            }
                        }
                    }
                }
                else {
                    clearScr();
                    System.out.println("The computer did not have any " + askFor + "s. Here's a free pity draw.");
                    userHand.drawCard();
                    Thread.sleep(2500);
                    System.out.println("You drew a " + userHand.getCard(userHand.size() - 1).idName + ".");
                    Thread.sleep(1750);
                    clearScr();
                    int occur = 0;
                    for (int x = 0; x < userHand.size(); x++) {
                        if (userHand.getCard(x).idName.equals(userHand.getCard(userHand.size() - 1).idName)) {
                            occur++;
                        }
                        if (occur == 4) {
                            userBooks++;
                            System.out.println("You have a set of " + userHand.getCard(userHand.size() - 1).idName + "s. You now have " + userBooks + " book(s).");
                            Thread.sleep(3000);
                            clearScr();
                            for (int i = 0; i < userHand.size(); i++) {
                                if (userHand.getCard(i).idName.equals(userHand.getCard(userHand.size() - 1).idName)) {
                                    userHand.removeCard(i);
                                    i--;
                                }
                            }
                        }
                    }
                    playerTurn = false;
                }
            }
            else {
                clearScr();
                System.out.println("The dealer asked for a " + randChoice + ".");
                Thread.sleep(2500);
                if (userHand.checkIfInHand(randChoice) == true && dealerHand.checkIfInHand(randChoice) == true) {
                    int amtTaken = 0;
                    for (int y = 0; y < userHand.size(); y++) {
                        if (userHand.getCard(y).idName.equals(randChoice)) {
                            Card theCard = new Card(userHand.getCard(y).value, userHand.getCard(y).idName);
                            dealerHand.addCard(theCard);
                            userHand.removeCard(y);
                            y--;
                            amtTaken++;
                        }
                    }
                    System.out.println("The dealer took " + amtTaken + " " + randChoice + "(s) from you!");
                    Thread.sleep(2500);
                    clearScr();
                    int occur = 0;
                    for (int x = 0; x < dealerHand.size(); x++) {
                        if (dealerHand.getCard(x).idName.equals(randChoice)) {
                            occur++;
                        }
                        if (occur == 4) {
                            aiBooks++;
                            System.out.println("The dealer has a set of " + randChoice + "s. They now have " + aiBooks + " book(s).");
                            Thread.sleep(3000);
                            clearScr();
                            for (int i = 0; i < dealerHand.size(); i++) {
                                if (dealerHand.getCard(i).idName.equals(randChoice)) {
                                    dealerHand.removeCard(i);
                                    i--;
                                }
                            }
                        }
                    }
                }
                else {
                    clearScr();
                    System.out.println("You did not have any " + randChoice + "s. The dealer gets a free pity draw.");
                    dealerHand.drawCard();
                    Thread.sleep(2500);
                    System.out.println("They drew a card.");
                    Thread.sleep(1500);
                    clearScr();
                    int occur = 0;
                    for (int x = 0; x < dealerHand.size(); x++) {
                        if (dealerHand.getCard(x).idName.equals(dealerHand.getCard(dealerHand.size() - 1).idName)) {
                            occur++;
                        }
                        if (occur == 4) {
                            aiBooks++;
                            System.out.println("The dealer has a set of " + randChoice + "s. They now have " + aiBooks + " book(s).");
                            Thread.sleep(3000);
                            clearScr();
                            for (int i = 0; i < dealerHand.size(); i++) {
                                if (dealerHand.getCard(i).idName.equals(randChoice)) {
                                    dealerHand.removeCard(i);
                                    i--;
                                }
                            }
                        }
                    }
                    playerTurn = true;
                }
            }
        
    }
    Thread.sleep(2500);
    clearScr();
    Thread.sleep(1500);
    System.out.println("You have: " + userBooks + "\nThe dealer has: " + aiBooks);
    Thread.sleep(1500);
    if (userBooks > aiBooks) {
        System.out.println("You win!");
    }
    else {
        System.out.println("The dealer wins!");
    }
    in.close();
}

    
    public static void clearScr() {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }

    public static void displayScreen(Hand userHand, Hand dealerHand, int userBooks, int aiBooks) {
        userHand.sortHand();
        dealerHand.sortHand();

        System.out.println("Your hand:");
        System.out.print("---------------\n");
        userHand.displayHand(false);
        System.out.println();
        System.out.println();
        System.out.println("Dealer's hand:");
        System.out.print("---------------\n");
        dealerHand.displayHand(true);
        System.out.println("\n\nYour books: " + userBooks + "\nDealer's books: " + aiBooks);
    }
}


class Deck {
    ArrayList<Card> deck;
    String nameLists[] = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
    String idName;
    Deck() {

        this.deck = new ArrayList<Card>();
        for (int amt = 0; amt < 4; amt++) {
			for (int count = 2; count < 15; count++) {
				if (count == 11) {
                    idName = "jack";
					deck.add(new Card(11, idName));
				}
				else if (count == 12) {
                    idName = "queen";
					deck.add(new Card(12, idName));
				}
				else if (count == 13) {
                    idName = "king";
					deck.add(new Card(13, idName));
				}
				else if (count == 14) {
                    idName = "ace";
					deck.add(new Card(14, idName));
				}
				else {
                    idName = nameLists[count - 1];
					deck.add(new Card(count, idName));
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
    public int size() {
        return this.deck.size();
    }
}

class Hand {
    ArrayList<Card> hand;

    Deck set;

    public Hand(Deck set) {
        this.set = set;
        this.hand = new ArrayList<Card>();
        for (int x = 0; x < 7; x++) {
            this.drawCard();
        }
    }

    public void drawCard() {
        this.hand.add(set.dealTop());
    }

    public void sortHand() {
        int n = this.hand.size();
        for (int i = 0; i < n-1; i++) {   
            for (int j = 0; j < n-i-1; j++) {
                if (this.hand.get(j).value > this.hand.get(j + 1).value) {
                    Card temp = this.hand.get(j);
                    Card temp2 = this.hand.get(j + 1);
                    this.hand.set(j, temp2);
                    this.hand.set(j + 1, temp);
                }
            }
        }
    }
    public void displayHand(boolean hideHand) {
        for (int x = 0; x < this.hand.size(); x++) {
            if (hideHand == false) {
                System.out.print(this.hand.get(x).idName + " | ");
            }
            else {
                System.out.print("Card " + (x + 1) +  " | ");
            }
        }
    }

    public void removeCard(int x) {
        this.hand.remove(x);
    }

    public void addCard(Card card) {
        this.hand.add(new Card(card.value, card.idName));
    }

    public int size() {
        return this.hand.size();
    }

    public Card getCard(int x) {
        return this.hand.get(x);
    }

    public boolean checkIfInHand(String askFor) {
        for (int x = 0; x < this.hand.size(); x++) {
            if (this.hand.get(x).idName.equals(askFor)) {
                return true;
            }
        }
        return false;
    }

}

class Card {
    int value;
    String idName;
    Card(int value, String idName) {
        this.value = value;
        this.idName = idName;
    }

}