import java.util.ArrayList;
import java.util.HashMap;

public class HeuristicPlayer extends Player{ //this class inherits the Player class
	
	//the default constructor of the class
	public HeuristicPlayer() {
		
	}
	
	//initialization of the variables of the class to the arguments
  	//this constructor will be used in the creation of the Player
	public HeuristicPlayer(int playerId, String name, Board board, int score, int x, int y) {
		super(playerId, name, board, score, x, y); 
    	path = new ArrayList <Integer[]>();
	}
	
	//this function returns the evaluation of a move to a certain direction
	//the arguments are the name of the player we want the evaluation, the current position of the player, the dice, the current position of the opponent and the board
	public double evaluate(String playerName, int currentPos, int dice, int opponentTile, Board board) {
		double nearSupplies = 0, opponentDist = 0; 	//the variables of the target function
		int firstPos = currentPos; 					//we save the initial position of the player
		int count = 0; 								//the number of tiles that are visible to the player
		int foundSup = 0, foundOpp = 0, foundWall = 0; //0 if player doesn't find Supply/Opponent/wall and 1 if he finds one
		int[] tmp;									//stores the array that function move returns
		int a = 0, b = 0;							//temporary variables, explained later
		 
		//explores the direction of the dice 
		while(count < 3) {
			switch(dice) {
				case 1:
					if(!board.getTiles()[currentPos].getUp()) { //if player can see the neighbor tile (there is no wall)
						if(board.hasSupply(currentPos + board.getN()) != -1) foundSup = 1; //if he finds a supply in the neighbor tile
						if((currentPos + board.getN()) == opponentTile) foundOpp = 1;      //if he finds the opponent in the neighbor tile
					}
					else foundWall = 1; //if there is a wall
					break;
					
				case 3:
					if(!board.getTiles()[currentPos].getRight()) {
						if(board.hasSupply(currentPos + 1) != -1) foundSup = 1;
						if((currentPos + 1) == opponentTile) foundOpp = 1;
					}
					else foundWall = 1;
					break;
					
				case 5:
					if(!board.getTiles()[currentPos].getDown()) {
						if(board.hasSupply(currentPos - board.getN()) != -1) foundSup = 1;
						if((currentPos - board.getN()) == opponentTile) foundOpp = 1;
					}
					else foundWall = 1;
					break;
					
				case 7:
					if(!board.getTiles()[currentPos].getLeft()) {
						if(board.hasSupply(currentPos - 1) != -1) foundSup = 1;
						if((currentPos - 1) == opponentTile) foundOpp = 1;
					}
					else foundWall = 1;
					break;
			}
			
			//count < 2 because we want the player to move only 2 times to check the neighbor tile (if there isn't a wall)
			//this way he checks 3 neighbor tiles in total (if there isn't a wall)
			if(count < 2 && foundWall == 0) {
				tmp = move(currentPos, dice, board, false, false, false); //we move the player if there isn't a wall. we set both realMove and gatherSup as false
				currentPos = tmp[0];							   //we change the current position of the player after he moves
			}
			
			//if the player finds a wall in his initial tile we return -1 because we want the player to move in every round
			if(foundWall == 1 && count == 0) return -1;
			
			//if the player finds a wall in another tile (not the initial)
			if(foundWall == 1) break;
			
			//if the player finds a supply
			if(foundSup == 1 && a == 0) {
				if(count == 0) nearSupplies = 1;
				else if(count == 1) nearSupplies = 0.5;	
				else if(count == 2) nearSupplies = 0.3;	
				a++; 
			}
			
			//if the player finds the opponent
			if(foundOpp == 1 && b == 0) {
				if(count == 0) opponentDist = 1;
				else if(count == 1) opponentDist = 0.5;
				else if(count == 2) opponentDist = 0.3;
				b++;
			}
			count++; //for the next tile-check
		}
		
		//we restore the coordinates of the player to his initial position
		x = firstPos/board.getN();
		y = firstPos%board.getN();
		
		//we return the final score depending on the player's name
		if (playerName == "Minotaur") return nearSupplies * 0.46 + opponentDist * 0.54;
		return nearSupplies * 0.46 - opponentDist * 0.54;
	}
	
	//assistant function that will be used to find the distance the player has of the nearest supply and the opponent
	//the arguments are the current position of the player and the current position of the opponent
	public int[] distances(int pos, int opponentTile) {
        int dis[] = {0, 0};		//this is the array we will return. we initialize the distances as 0
        int[] s = {0, 0, 0, 0}; //we store the distance of a supply in every direction
        int m = 0;				//the distance from the opponent
        
        //each for is to explore a different direction
        for ( int i = 0; i < 3; i++ ) {
            if ( !board.getTiles()[pos + i * board.getN()].getUp() ) {	//if there isn't a wall
                if ( board.hasSupply(pos + (i + 1) * board.getN()) > 0 && s[0] == 0 ) s[0] = i + 1; //we want s[0] == 0 because we want to store the nearest supply
                if ( opponentTile == pos + (i + 1) * board.getN() ) m = i + 1;
            }
            else break; //if there is a wall we leave the for
        }
        for ( int i = 0; i < 3; i++ ) {
            if ( !board.getTiles()[pos + i].getRight() ) {
                if ( board.hasSupply(pos + i + 1) > 0 && s[1] == 0 ) s[1] = i + 1;
                if ( opponentTile == pos + i + 1 ) m = i + 1;
            }
            else break;
        }
        for ( int i = 0; i < 3; i++ ) {
            if ( !board.getTiles()[pos - i * board.getN()].getDown() ) {
                if ( board.hasSupply(pos - (i + 1) * board.getN()) > 0 && s[2] == 0 ) s[2] = i + 1;
                if ( opponentTile == pos - (i + 1) * board.getN() ) m = i + 1;
            }
            else break;
        }
        for ( int i = 0; i < 3; i++ ) {
            if ( !board.getTiles()[pos - i].getLeft() ) {
                if ( board.hasSupply(pos - i - 1) > 0 && s[3] == 0 ) s[3] = i + 1;
                if ( opponentTile == pos - i - 1 ) m = i + 1;
            }
            else break;
        }
        
        //we want to return the distance from the nearest supply of all directions (more in the report)
        int min = 4; //we set the minimum as 4
        for (int i = 0; i < 4; i++) if (s[i] < min && s[i] != 0) min = s[i];
        if(min == 4) min = 0; //if there isn't a supply around the player's eyesight
        dis[0] = min;
        dis[1] = m;
        return dis;
    }
	
	//in this function we choose the best move for the player and we move him
	//the arguments are the current position of the player and the current position of the opponent
	public int getNextMove(int currentPos, int opponentTile) {
        HashMap <Integer, Double> options = new HashMap <Integer, Double> (); //stores the dice(key) with its target score(value)
        for ( int i = 1; i <= 7; i += 2 ) {
            if(playerId == 1) options.put(i, evaluate("Theseus", currentPos, i, opponentTile, board)); //if the player is Theseus
            else options.put(i, evaluate("Minotaur", currentPos, i, opponentTile, board));			//if the player is Minotaur
        }
        
        //we find the maximum target score and we store it in max
        double max = Double.NEGATIVE_INFINITY;
        for (int m: options.keySet()) 
            if (options.get(m) > max) max = options.get(m);
        
        moveScore = max;
        //we store the directions which have the maximum score in the array bestMoves
        int[] bestMoves = new int[4];
        int tmp = 0;
        for (int m: options.keySet()) {
            if (options.get(m) == max) bestMoves[tmp++] = m;
        }
        
        //we choose the direction of the final move randomly from the bestMoves array
        int random = (int)(Math.random() * tmp); 
        int dice = bestMoves[random]; 
       
        //we find the distances of the player before he moves
        int[] d = distances(currentPos, opponentTile);
        
        //we move the player according to which player is being moved
        int[] finalMove;
        if(playerId == 1) {
        	finalMove = move(currentPos, dice, board, true, true, false); //we set both realMove and gatherSup as true because it is a real move and we want Theseus to gather supplies, we set randomMove as false because it's not a random move
        }
        else {
            finalMove = move(currentPos, dice, board, true, false, false); //we set realMove as true and gatherSup as false because it is a real move and we don't want Minotaur to gather supplies, we set randomMove as false because it's not a random move
        }
        
        int el0 = dice;
        int el1 = finalMove[3] > 0 ? 1 : 0; 
        int el2 = d[0];
        int el3 = d[1];
        //we add the new elements in path
        Integer[] a = {el0, el1, el2, el3}; 
        path.add(a); 
        return finalMove[0]; //we return the tile id of the new position of the player
    }
	
	//this function prints the statistics of the game
	//the argument is a boolean which indicates the end of the game
	public String statistics(boolean endOfGame) {	
		//this string gathers all the final statistics in one variable so that we can print them in the GUI when the game is finished
		String statistics = "";
		
		//we print these statistics in every round
		if(!endOfGame) {
			int supDis = path.get(path.size() - 1)[2]; //path.size() - 1 is the index of the last element in the arraylist
			int oppDis = path.get(path.size() - 1)[3]; 
			
			System.out.println(name + " has gathered " + score + " supplies.\n");
			
			if(supDis == 0) System.out.println(name + " cannot see any Supply at the moment.");
			else System.out.println("Distance from Supply before " + name + " moved: " + supDis);
		
			if(oppDis == 0) System.out.println(name + " cannot see his opponent at the moment.\n");
			else System.out.println("Distance from opponent before " + name + " moved: " + oppDis + "\n");
		}
		
		//at the end of the game we print the final statistics
		if(endOfGame) {
			int move1 = 0, move3 = 0, move5 = 0, move7 = 0;	//the amount of times Theseus moved to a certain direction
			int round = 0;
			for(Integer[] a: path) {
				if(a[0] == 1) {
					System.out.println("In round " + ++round + " " + name + " moved upwards");
					statistics += "In round " + round + " " + name + " moved upwards\n";
					move1++;
				}
				else if(a[0] == 3) {
					System.out.println("In round " + ++round + " " + name + " moved to the right");
					statistics += "In round " + round + " " + name + " moved to the right\n";
					move3++;
				}
				else if(a[0] == 5) {
					System.out.println("In round " + ++round + " " + name + " moved downwards");
					statistics += "In round " + round + " " + name + " moved downwards\n";
					move5++;
				}
				else if(a[0] == 7) {
					System.out.println("In round " + ++round + " " + name + " moved to the left");
					statistics += "In round " + round + " " + name + " moved to the left\n";
					move7++;
				}
			}
			System.out.println("\nStatistics of the game for " + name);
			System.out.println(name + " moved upwards " + move1 + " times.");
			System.out.println(name + " moved to the right " + move3 + " times.");
			System.out.println(name + " moved downwards " + move5 + " times.");
			System.out.println(name + " moved to the left " + move7 + " times.\n");
			
			statistics += ("\nStatistics of the game for " + name + ":\n");
			statistics += (name + " moved upwards " + move1 + " times.\n");
			statistics += (name + " moved to the right " + move3 + " times.\n");
			statistics += (name + " moved downwards " + move5 + " times.\n");
			statistics += (name + " moved to the left " + move7 + " times.");
		}
		return statistics;
	}
}
