/*Ioannis Siakavaras, 10053, 6946937774, siakavari@ece.auth.gr
  Christoforos Chatziantoniou, 10258, 6946495698, cchristofo@ece.auth.gr
  Team 186
 */

import java.util.ArrayList;
import java.util.HashMap;

public class HeuristicPlayer extends Player{ //this class inherits the Player class
	private ArrayList <Integer[]> path; //an arraylist that contains information about each HeuristicPlayer's move
	
	//the default constructor of the class
	public HeuristicPlayer() {
		
	}
	
	//initialization of the variables of the class to the arguments
  	//this constructor will be used in the creation of the Player
	public HeuristicPlayer(int playerId, String name, Board board, int score, int x, int y) {
		super(playerId, name, board, score, x, y); //
    	path = new ArrayList <Integer[]>();
	}
	
	//this function returns the evaluation of a move to a certain direction
	//the arguments are the current position of Theseus, the dice and the current position of Minotaur
	public double evaluate(int currentPos, int dice, int minotaurTile) {
		double nearSupplies = 0, opponentDist = 0; 	//the variables of the target function
		int firstPos = currentPos; 					//we save the initial position of Theseus
		int count = 0; 								//the number of tiles that are visible to the HeuristicPlayer
		int foundSup = 0, foundMino = 0, foundWall = 0; //0 if HeuristicPlayer doesn't find Supply/Minotaur/wall and 1 if he finds one
		int[] tmp;									//stores the array that function move returns
		int a = 0, b = 0;							//temporary variables, explained later
		
		//explores the direction of the dice 
		while(count < 3) {
			switch(dice) {
				case 1:
					if(!board.getTiles()[currentPos].getUp()) { //if Theseus can see the neighbor tile (there is no wall)
						if(board.hasSupply(currentPos + board.getN()) != -1) foundSup = 1; //if he finds a supply in the neighbor tile
						if((currentPos + board.getN()) == minotaurTile) foundMino = 1;	   //if he finds Minotaur in the neighbor tile
					}
					else foundWall = 1; //if there is a wall
					break;
					
				case 3:
					if(!board.getTiles()[currentPos].getRight()) {
						if(board.hasSupply(currentPos + 1) != -1) foundSup = 1;
						if((currentPos + 1) == minotaurTile) foundMino = 1;
					}
					else foundWall = 1;
					break;
					
				case 5:
					if(!board.getTiles()[currentPos].getDown()) {
						if(board.hasSupply(currentPos - board.getN()) != -1) foundSup = 1;
						if((currentPos - board.getN()) == minotaurTile) foundMino = 1;
					}
					else foundWall = 1;
					break;
					
				case 7:
					if(!board.getTiles()[currentPos].getLeft()) {
						if(board.hasSupply(currentPos - 1) != -1) foundSup = 1;
						if((currentPos - 1) == minotaurTile) foundMino = 1;
					}
					else foundWall = 1;
					break;
			}
			//count < 2 because we want Theseus to move only 2 times to check the neighbor tile (if there isn't a wall)
			//this way he checks 3 neighbor tiles in total (if there isn't a wall)
			if(count < 2 && foundWall == 0) {
				tmp = move(currentPos, dice, false); //we move the player if there isn't a wall. the third argument is false because it's not a real move
				currentPos = tmp[0];				 //we change the current position of Theseus after he moves
			}
			
			//if Theseus finds a wall in his initial tile we return -1 because we want the player to move in every round
			if(foundWall == 1 && count == 0) return -1;
			
			//if Theseus finds a wall in another tile (not the initial)
			if(foundWall == 1) break; //break from while
			
			//if Theseus finds a supply
			if(foundSup == 1 && a == 0) {
				if(count == 0) nearSupplies = 1; 			//if the distance is 1
				else if(count == 1) nearSupplies = 0.5;		//if the distance is 2
				else if(count == 2) nearSupplies = 0.3;		//if the distance is 3
				a++; //because we want to calculate the score when Theseus finds the supply the first time
			}
			
			//if Theseus finds Minotaur
			if(foundMino == 1 && b == 0) {
				if(count == 0) opponentDist = -1;
				else if(count == 1) opponentDist = -0.5;
				else if(count == 2) opponentDist = -0.3;
				b++;
			}
			count++; //for the next check
		}
		//we restore the coordinates of Theseus to his initial position
		x = firstPos/board.getN();
		y = firstPos%board.getN();
		
		return nearSupplies * 0.46 + opponentDist * 0.54; //we return the final score 
	}
	
	//assistant function that will be used to find the distance Theseus has of the nearest supply and Minotaur
	//the arguments are the current position of Theseus and the current position of Minotaur
	public int[] distances(int pos, int minotaurTile) {
        int dis[] = {0, 0};		//this is the array we will return. we initialize the distances as 0
        int[] s = {0, 0, 0, 0}; //we store the distance of a supply in every direction
        int m = 0;				//the distance from Minotaur
        
        //each for is to explore a different direction
        for ( int i = 0; i < 3; i++ ) {
            if ( !board.getTiles()[pos + i * board.getN()].getUp() ) {	//if there isn't a wall
                if ( board.hasSupply(pos + (i + 1) * board.getN()) > 0 && s[0] == 0 ) s[0] = i + 1; //we want s[0] == 0 because we want to store the nearest supply
                if ( minotaurTile == pos + (i + 1) * board.getN() ) m = i + 1;
            }
            else break; //if there is a wall we leave the for
        }
        for ( int i = 0; i < 3; i++ ) {
            if ( !board.getTiles()[pos + i].getRight() ) {
                if ( board.hasSupply(pos + i + 1) > 0 && s[1] == 0 ) s[1] = i + 1;
                if ( minotaurTile == pos + i + 1 ) m = i + 1;
            }
            else break;
        }
        for ( int i = 0; i < 3; i++ ) {
            if ( !board.getTiles()[pos - i * board.getN()].getDown() ) {
                if ( board.hasSupply(pos - (i + 1) * board.getN()) > 0 && s[2] == 0 ) s[2] = i + 1;
                if ( minotaurTile == pos - (i + 1) * board.getN() ) m = i + 1;
            }
            else break;
        }
        for ( int i = 0; i < 3; i++ ) {
            if ( !board.getTiles()[pos - i].getLeft() ) {
                if ( board.hasSupply(pos - i - 1) > 0 && s[3] == 0 ) s[3] = i + 1;
                if ( minotaurTile == pos - i - 1 ) m = i + 1;
            }
            else break;
        }
        
        //we want to return the distance from the nearest supply of all directions (more in the report)
        int min = 4; //we set the min as 4
        for (int i = 0; i < 4; i++) if (s[i] < min && s[i] != 0) min = s[i];
        if(min == 4) min = 0; //if there isn't a supply around Theseus' eyesight
        dis[0] = min;
        dis[1] = m;
        return dis;
    }
	
	//in this function we choose the best move for Theseus and we move him
	//the arguments are the current position of Theseus and the current position of Minotaur
	public int getNextMove(int currentPos, int minotaurTile) {
        HashMap <Integer, Double> options = new HashMap <Integer, Double> (); //stores the dice(key) with its target score(value)
        for ( int i = 1; i <= 7; i += 2 ) options.put(i, evaluate(currentPos, i, minotaurTile)); 
        
        //we find the maximum target score and we store it in max
        double max = Double.NEGATIVE_INFINITY;
        for (int m: options.keySet()) 
            if (options.get(m) > max) max = options.get(m);
        
        //we store the directions which have the maximum score in the array bestMoves
        int[] bestMoves = new int[4];
        int tmp = 0;
        for (int m: options.keySet()) {
            if (options.get(m) == max) bestMoves[tmp++] = m;
        }
        //we choose the direction of the final move randomly from the bestMoves array
        int random = (int)(Math.random() * tmp); 
        int finalMove = bestMoves[random]; 
       
        //we find the distances from Theseus before he moves
        int[] d = distances(currentPos, minotaurTile);
        //we move the player
        int[] move = move(currentPos, finalMove, true);
        int el0 = finalMove;
        int el1 = move[3] > 0 ? 1 : 0; 
        int el2 = d[0];
        int el3 = d[1];
        //we add the new elements in path
        Integer[] a = { el0, el1, el2, el3 }; 
        path.add(a); 
        return move[0]; //we return the tile id of the new position of Theseus
    }
	
	//this function prints the statistics of the game
	//the argument is a boolean which indicates the end of the game
	public void statistics(boolean endOfGame) {	
		//we print these statistics in every round
		if(!endOfGame) {
			int supDis = path.get(path.size() - 1)[2]; //path.size() - 1 is the index of the last element in the arraylist
			int oppDis = path.get(path.size() - 1)[3]; 
		
			System.out.println("Theseus has gathered " + score + " supplies.\n");
		
			if(supDis == 0) System.out.println("Theseus cannot see any Supply at the moment.");
			else System.out.println("Distance from Supply before Theseus moved: " + supDis);
		
			if(oppDis == 0) System.out.println("Theseus cannot see his opponent at the moment.\n");
			else System.out.println("Distance from opponent before Theseus moved: " + oppDis + "\n");
		}
		//at the end of the game we print the final statistics
		if(endOfGame) {
			int move1 = 0, move3 = 0, move5 = 0, move7 = 0;	//the amount of times Theseus moved to a certain direction
			int round = 0;
			for(Integer[] a: path) {
				if(a[0] == 1) {
					System.out.println("In round " + ++round + " Theseus moved upwards");
					move1++;
				}
				else if(a[0] == 3) {
					System.out.println("In round " + ++round + " Theseus moved to the right");
					move3++;
				}
				else if(a[0] == 5) {
					System.out.println("In round " + ++round + " Theseus moved downwards");
					move5++;
				}
				else if(a[0] == 7) {
					System.out.println("In round " + ++round + " Theseus moved to the left");
					move7++;
				}
			}
			System.out.println("\nStatistics of the game: ");
			System.out.println("Theseus moved upwards " + move1 + " times.");
			System.out.println("Theseus moved to the right " + move3 + " times.");
			System.out.println("Theseus moved downwards " + move5 + " times.");
			System.out.println("Theseus moved to the left " + move7 + " times.\n");
		}
	}
}