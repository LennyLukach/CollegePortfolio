//Update ghost death to have 2 lives to increase survival rate
//Add trading of tea leaves
//Add marriage
//Add ghost babies
//Fix tea gathering rate
//Reactor maybe?
//Clean up un-used code maybe?

import java.util.ArrayList;

public class ghostLemmings {

	public static void main(String[] args) {
		
		Population GhostPopulation = new Population();

		//int num = 5;
		
		//CHANGE NUMBER OF DAYS: 100 FOR SMALL TEST \ 1000 FOR BIG TEST \ DONT USE 10,000
		
		int days = 1000;
		
		//GhostPopulation.printPopulation();
		int startGhost = GhostPopulation.popSize();
		int ghostsBorn = 0;
		int marriedGhosts = 0;
		int deadGhosts = 0;
		int g = 1;
		int w = 2;
		//GhostPopulation.getGhost(num).ghostDescription();
		System.out.println("Total Ghosts: " + GhostPopulation.popSize());
		System.out.println("\n");
		//int test = in.nextInt();
		//Count Days
		for(days = 0; days < 500; days++) {
			//Goes through each ghost to do actions
			for (int ghostIter = 0; ghostIter < GhostPopulation.popSize(); ghostIter++) {
				
				//Kills ghost that did not eat yesterday + resets ateToday Value
				GhostPopulation.getGhost(ghostIter).killGhost(w);
			
				//Makes ghosts eat food,
				GhostPopulation.getGhost(ghostIter).eatTea(GhostPopulation.getGhost(ghostIter).getTeaLike());
				
				if (GhostPopulation.getGhost(ghostIter).superLike == true) {
					int randAction = (int) (Math.random() * 8);
					if (randAction == 0 || randAction == 1) {
						//Makes ghosts farm and gather tea leaves
						GhostPopulation.getGhost(ghostIter).giveTeaLeaves();
					}
					else if (randAction == 2 && ghostIter < GhostPopulation.popSize() - 1){
						//Picks 2 random eligible ghosts to get married
						GhostPopulation.getMarried(ghostIter, g);
					}
					
					else if (ghostIter < GhostPopulation.popSize() - 1 && (randAction == 3 || randAction == 4 || randAction == 5 || randAction == 6)) {
						//Makes ghosts trade for their favorite tea leaf
						GhostPopulation.tradeTea(GhostPopulation.getGhost(ghostIter), GhostPopulation.getGhost(ghostIter += 1));	
					}	
					
					else {
						//Makes another ghost if able
						GhostPopulation.getGhost(ghostIter).makeGhost(GhostPopulation, ghostsBorn);
					}
				}
				else {
					//Makes ghosts farm and gather tea leaves
					GhostPopulation.getGhost(ghostIter).giveTeaLeaves();
				}
				
				//Resets one time values
				GhostPopulation.getGhost(ghostIter).superLike = false;
			}
			
			
		}
		for (int t = 0; t < GhostPopulation.popSize(); t++) {
			if (GhostPopulation.getGhost(t).alive == false) {
				deadGhosts++;
			}
			if (GhostPopulation.getGhost(t).married == true) {
				marriedGhosts++;
			}
		}
		
		ghostsBorn = GhostPopulation.popSize() - startGhost;
		
		GhostPopulation.popStatPage(startGhost, GhostPopulation.amtAlive(), deadGhosts, ghostsBorn, marriedGhosts, GhostPopulation.popStrawLike(), GhostPopulation.popCocoLike(), GhostPopulation.popChocoLike());
	}

}
class Population{
	ArrayList<GhostNpc> population;
	String ghostNames[] = {"Veng", "Shazz", "Glo", "Starf", "Clem", "Phol", "Ytrei", "Marin", "Qic", "Cal", "Lux", "Isk", "Minim", "Zehb", "Ibot", "Yra", "Kiv", "Osh"};
	String ghostGenders[] = {"Male", "Female"};
	String ghostTeaLikes[] = {"Strawberry", "Coconut", "Chocolate"};
	Population(){
		
		this.population = new ArrayList<GhostNpc>();
		
		int idNumber = 0;
		for (int x = (int) (Math.random() * 10) + 20; x > 0; x--) {
			String namePicked = ghostNames[(int) (Math.random() * (ghostNames.length))];
			String genderPicked = ghostGenders[(int) (Math.random() * (ghostGenders.length))];
			String teaLikePicked = ghostTeaLikes[(int) (Math.random() * (ghostTeaLikes.length))];
			population.add(new GhostNpc(namePicked, genderPicked, teaLikePicked, true, false, true, false, (int) (Math.random() + 1), (int) (Math.random() + 1), (int) (Math.random() + 1), 2, idNumber));
			idNumber++;
			
		}
	}
	
	public void printPopulation() {
		for (GhostNpc ghost : population) {
			System.out.println("Ghost Name: " + ghost.name + " | Ghost ID: " + ghost.ID);
		}
	}
	
	public int popStrawLike() {
		int s = 0;
		for (int p = 0; p < this.population.size(); p++) {
			if (this.population.get(p).teaLike == "Strawberry") {
				s++;
			}
		}
		return s;
	}
	
	public int popCocoLike() {
		int c = 0;
		for (int p = 0; p < this.population.size(); p++) {
			if (this.population.get(p).teaLike == "Coconut") {
				c++;
			}
		}
		return c;
	}
	
	public int popChocoLike() {
		int ch = 0;
		for (int p = 0; p < this.population.size(); p++) {
			if (this.population.get(p).teaLike == "Chocolate") {
				ch++;
			}
		}
		return ch;
	}
	
	public int popSize() {
		return this.population.size();
	}
	
	public void popStatPage(int startGhost, int livingGhost, int deadGhost, int ghostsBorn, int marriedGhosts, int s, int c, int ch) {
		String space = "\n-----------------------------------------------\n";
		System.out.print(space
						 + "1. Amount of Starting Ghosts: " + startGhost
						 + space
						 + "2. Total Ghosts: " + this.popSize()
						 + space
						 + "3. Amount of Living Ghosts: " + livingGhost
						 + space
						 + "4. Amount of Dead Ghosts: " + deadGhost
						 + space
						 + "5. Amount of Ghosts Born: " + ghostsBorn
						 + space
						 + "6. Amount of Married Ghosts: " + marriedGhosts
						 + space
						 + "7. Amount of Ghosts that like Strawberry Tea: " + s
						 + space
						 + "8. Amount of Ghosts that like Coconut Tea: " + c
						 + space
						 + "9. Amount of Ghosts that like Chocolate Tea: " + ch
						 + space);
	}
	
	public GhostNpc getGhost(int num) {
		return this.population.get(num);
	}
	
	public GhostNpc getRandomGhost() {
		return this.population.get((int) (Math.random()) * this.popSize());
	}
	
	public GhostNpc tradeTea(GhostNpc ghost1, GhostNpc ghost2) {
		String g1L = ghost1.teaLike;
		String g2L = ghost2.teaLike;
		if (g1L == "Strawberry" && g2L == "Strawberry") {
			System.out.println(ghost1.name + " and " + ghost2.name + " both like " + g1L + " tea and did not want to trade!");
			return null;
		}
		if (g1L == "Coconut" && g2L == "Coconut") {
			System.out.println(ghost1.name + " and " + ghost2.name + " both like " + g1L + " tea and did not want to trade!");
			return null;
		}
		if (g1L == "Chocolate" && g2L == "Chocolate") {
			System.out.println(ghost1.name + " and " + ghost2.name + " both like " + g1L + " tea and did not want to trade!");
			return null;
		}
		if (g1L == "Strawberry" && g2L == "Coconut") {
			if (ghost2.strawberryTeaLeaves >= 1 && ghost1.coconutTeaLeaves >= 1) {
				ghost1.strawberryTeaLeaves++;
				ghost1.coconutTeaLeaves--;
				ghost2.coconutTeaLeaves++;
				ghost2.strawberryTeaLeaves--;
				System.out.println(ghost1.name + " and " + ghost2.name + " traded " + g1L + " tea for " + g2L + " tea!");
				return null;
			}
		}
		if (g1L == "Strawberry" && g2L == "Chocolate") {
			if (ghost2.strawberryTeaLeaves >= 1 && ghost1.chocolateTeaLeaves >= 1) {
				ghost1.strawberryTeaLeaves++;
				ghost1.chocolateTeaLeaves--;
				ghost2.chocolateTeaLeaves++;
				ghost2.strawberryTeaLeaves--;
				System.out.println(ghost1.name + " and " + ghost2.name + " traded " + g1L + " tea for " + g2L + " tea!");
				return null;
			}
		}
		if (g1L == "Coconut" && g2L == "Strawberry") {
			if (ghost2.coconutTeaLeaves >= 1 && ghost1.strawberryTeaLeaves >= 1 ) {
				ghost1.coconutTeaLeaves++;
				ghost1.strawberryTeaLeaves--;
				ghost2.strawberryTeaLeaves++;
				ghost2.coconutTeaLeaves--;
				System.out.println(ghost1.name + " and " + ghost2.name + " traded " + g1L + " tea for " + g2L + " tea!");
				return null;
			}
		}
		if (g1L == "Coconut" && g2L == "Chocolate") {
			if (ghost2.coconutTeaLeaves >= 1 && ghost1.chocolateTeaLeaves >= 1) {
				ghost1.coconutTeaLeaves++;
				ghost1.chocolateTeaLeaves--;
				ghost2.chocolateTeaLeaves++;
				ghost2.coconutTeaLeaves--;
				System.out.println(ghost1.name + " and " + ghost2.name + " traded " + g1L + " tea for " + g2L + " tea!");
				return null;
			}
		}
		if (g1L == "Chocolate" && g2L == "Strawberry") {
			if (ghost2.chocolateTeaLeaves >= 1 && ghost1.strawberryTeaLeaves >= 1) {
				ghost1.chocolateTeaLeaves++;
				ghost1.strawberryTeaLeaves--;
				ghost2.strawberryTeaLeaves++;
				ghost2.chocolateTeaLeaves--;
				System.out.println(ghost1.name + " and " + ghost2.name + " traded " + g1L + " tea for " + g2L + " tea!");
				return null;
			}
		}
		if (g1L == "Chocolate" && g2L == "Coconut") {
			if (ghost2.chocolateTeaLeaves >= 1 && ghost1.coconutTeaLeaves >= 1) {
				ghost1.chocolateTeaLeaves++;
				ghost1.coconutTeaLeaves--;
				ghost2.coconutTeaLeaves++;
				ghost2.chocolateTeaLeaves--;
				System.out.println(ghost1.name + " and " + ghost2.name + " traded " + g1L + " tea for " + g2L + " tea!");
				return null;
			}
		}
		return null;
	}
	
	public int getMarried(int num, int x) {
		if (getGhost(num).canMarry() == true && getGhost(num++).canMarry() == true) {
			if (getGhost(num).gender == getGhost(num - x).gender) {
				getGhost(num).married = true;
				getGhost(num++).married = true;
				num -= 2;
				System.out.print(getGhost(num).name + " and ");
				num++;
				System.out.println(getGhost(num).name + " got married!");
				x++;
				return x;
			}
		}
		return x;
	}
	
	public String randName() {
		String ghostNames[] = {"Veng", "Shazz", "Glo", "Starf", "Clem", "Phol", "Ytrei", "Marin", "Qic", "Cal", "Lux", "Isk", "Minim", "Zehb", "Ibot", "Yra", "Kiv", "Osh"};
		
		
		String namePicked = ghostNames[(int) (Math.random() * (ghostNames.length))];
		return namePicked;
	}
	
	public String randGender() {
		String ghostGenders[] = {"Male", "Female"};
		String genderPicked = ghostGenders[(int) (Math.random() * (ghostGenders.length))];
		return genderPicked;
	}
	
	public String randTeaLike() {
		String ghostTeaLikes[] = {"Strawberry", "Coconut", "Chocolate"};
		String teaLikePicked = ghostTeaLikes[(int) (Math.random() * (ghostTeaLikes.length))];
		return teaLikePicked;
	}
	
	public int amtAlive() {
		int amt = 0;
		for (int x = 0; x < this.population.size(); x++) {
			if (this.getGhost(x).alive == true) {
				amt++;
			}
		}
		return amt;
	}
	
}


class GhostNpc {
	String name;
	String gender;
	String teaLike;
	boolean ateToday;
	boolean married;
	boolean alive;
	boolean superLike;
	int strawberryTeaLeaves = 0;
	int coconutTeaLeaves = 0;
	int chocolateTeaLeaves = 0;
	int health = 2;
	int ID;
	
	
	GhostNpc(String name, String gender, String teaLike, boolean ateToday, boolean married, boolean alive, boolean superLike, int strawberryTeaLeaves, int coconutTeaLeaves, int chocolateTeaLeaves, int health, int ID) {
		this.name = name;
		this.gender = gender;
		this.teaLike = teaLike;
		this.ateToday = ateToday;
		this.married = married;
		this.alive = alive;
		this.superLike = superLike;
		this.strawberryTeaLeaves = strawberryTeaLeaves;
		this.coconutTeaLeaves = coconutTeaLeaves;
		this.chocolateTeaLeaves = chocolateTeaLeaves;
		this.health = health;
		this.ID = ID;
	}
	
	public GhostNpc ghostDescription() {
		System.out.println("Name: " + this.name
							+ "\nGender: " + this.gender
							+ "\nFavorite Tea Leaf Flavor: " + this.teaLike
							+ "\nEaten Today: " + this.ateToday
							+ "\nMarried: " + this.married
							+ "\nLiving: " + this.alive
							+ "\nAte Favorite Tea: " + this.superLike
							+ "\nStrawberry Tea Leaves Owned: " + this.strawberryTeaLeaves
							+ "\nCoconut Tea Leaves Owned: " + this.coconutTeaLeaves
							+ "\nChocolate Tea Leaves Owned: " + this.chocolateTeaLeaves
							+ "\nGhost Health: " + this.health
							+ "\nGhost ID: " + this.ID);
		return null;
	}
	
	public boolean isMarried() {
		if (this.married == false) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean hasEaten() {
		if (this.ateToday == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isAlive() {
		if (this.alive == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int killGhost(int x) {
		if (this.hasEaten() == false && this.alive == true) {
			this.health--;
			if (this.health == 0) {
				System.out.println(this.name + " has died from thirst!");
				this.alive = false;
				x++;
			}
		}
		else {
			this.ateToday = false;
		}
		return x;
	}
	
	public int getGhostId() {
		return this.ID;
	}
	
	public int makeGhost(Population gPop, int x) {
		if (this.married == true && this.superLike == true && this.alive == true) {
			gPop.population.add(new GhostNpc(gPop.randName(), gPop.randGender(), gPop.randTeaLike(), true, false, true, false, (int) (Math.random()), (int) (Math.random()), (int) (Math.random()), 2, gPop.popSize()));
			System.out.println(this.name + " gave birth to " + gPop.getGhost(gPop.popSize() - 1).name + "!");
			x++;
			//System.out.println(this.name + " gave birth to " + gPop.getGhost(gPop.popSize() - 1).name + "! ALSO LIKES " + gPop.getGhost(gPop.popSize() - 1).teaLike);
		}
		return x;
	}
	
	public String getTeaLike() {
		if (this.alive == false) {
			return null;
		}
		return this.teaLike;
	}
	
	public boolean canMarry() {
		if (this.isMarried() == false && this.isAlive() == true && this.hasEaten() == true && this.superLike == true) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	public GhostNpc giveTeaLeaves() {
		if (this.alive == false) {
			return null;
		}
		int gather = 1;
		if (this.superLike == true) {
			gather++;
		}
		for (int x = (int) (Math.random() * 3); x > 0; x--) {
			int y = (int) (Math.random() * 3);
			if (y == 0) {
				this.strawberryTeaLeaves += gather;
			}
			else if (y == 1) {
				this.coconutTeaLeaves += gather;
			}
			else if (y == 2) {
				this.chocolateTeaLeaves += gather;
			}
		}
		gather--;
		return null;
	}
	
	public GhostNpc eatTea(String favTea) {
		if (this.alive == false) {
			return null;
		}
		else {
			if (favTea == "Strawberry") {
				if (this.strawberryTeaLeaves >= 2) {
					this.strawberryTeaLeaves -= 2;
					health += 2;
					System.out.println(this.name + " drank " + favTea + " Tea and loved it!");
					this.ateToday = true;
					this.superLike = true;
					return null;
				}
				else if (this.coconutTeaLeaves >= 1) {
					health++;
					this.coconutTeaLeaves--;
					this.ateToday = true;
					System.out.println(this.name + " drank Coconut Tea.");
					return null;
				}
				else if (this.chocolateTeaLeaves >= 1) {
					health++;
					this.chocolateTeaLeaves--;
					this.ateToday = true;
					System.out.println(this.name + " drank Chocolate Tea.");
					return null;
				}
			}
			else if (favTea == "Coconut") {
				if (this.coconutTeaLeaves >= 2) {
					health += 2;
					this.coconutTeaLeaves -= 2;
					System.out.println(this.name + " drank " + favTea + " Tea and loved it!");
					this.ateToday = true;
					this.superLike = true;
					return null;
				}
				else if (this.strawberryTeaLeaves >= 2) {
					health++;
					this.strawberryTeaLeaves -= 2;
					this.ateToday = true;
					System.out.println(this.name + " drank Strawberry Tea.");
					return null;
				}
				else if (this.chocolateTeaLeaves >= 1) {
					health++;
					this.chocolateTeaLeaves--;
					this.ateToday = true;
					System.out.println(this.name + " drank Chocolate Tea.");
					return null;
				}
			}
			else if (favTea == "Chocolate") {
				if (this.chocolateTeaLeaves >= 1) {
					health += 2;
					this.chocolateTeaLeaves--;
					System.out.println(this.name + " drank " + favTea + " Tea and loved it!");
					this.ateToday = true;
					this.superLike = true;
					return null;
				}
				else if (this.strawberryTeaLeaves >= 1) {
					health++;
					this.strawberryTeaLeaves--;
					this.ateToday = true;
					System.out.println(this.name + " drank Strawberry Tea.");
					return null;
				}
				else if (this.coconutTeaLeaves >= 1) {
					health++;
					this.coconutTeaLeaves--;
					this.ateToday = true;
					System.out.println(this.name + " drank Coconut Tea.");
					return null;
				}
			}
		}
		return null;
	}
	
}





