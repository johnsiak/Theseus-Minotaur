import java.util.ArrayList;

public class MinMaxPlayer extends Player{ // this class simulates the player that uses the MinMax Algorithm
	private ArrayList <Integer[]> path; //an arraylist that contains information about each MinMaxPlayer's move
	
	//the default constructor of the class
	public MinMaxPlayer() {
		
	}
	
	//initialization of the variables of the class to the arguments
  	//this constructor will be used in the creation of the Player
	public MinMaxPlayer(int playerId, String name, Board board, int score, int x, int y) {
		super(playerId, name, board, score, x, y); //
    	path = new ArrayList <Integer[]>();
	}
	
	//this function returns the evaluation of a move to a certain direction
	//the arguments are the name of the player we want the evaluation, the current position of Theseus, the dice, the current position of Minotaur
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
				tmp = move(currentPos, dice, board, false, false); //we move the player if there isn't a wall. we set both realMove and gatherSup as false
				currentPos = tmp[0];							   //we change the current position of the player after he moves
			}
			
			//if the player is Theseus and he finds a wall in his initial tile we return -1 because we want the player to move in every round
			if(foundWall == 1 && count == 0 && playerName != "Minotaur") return -1;
			
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
	
	//this function uses the MinMax Algorithm to choose the best move for the player
	//the argument is the root node of the tree
    public int chooseMinMaxMove(Node root) {

        for(Node a: root.getChildren()) {        // a are the nodes in depth 1
            double min = Double.POSITIVE_INFINITY;
            for(Node b: a.getChildren()) {        // b are the nodes in depth 2
                if(min > b.getNodeEvaluation()) min = b.getNodeEvaluation();    // we find the leaf node with the minimum evaluation of every child of the root
            }
            a.setNodeEvaluation(min); // we set the min evaluation to the nodes in depth 1
        }

        double max = Double.NEGATIVE_INFINITY;
        for(Node a: root.getChildren()) {
            if(max < a.getNodeEvaluation()) max = a.getNodeEvaluation(); // we find the maximum evaluation of the minimums that we got earlier
        }
        root.setNodeEvaluation(max); // we set the max evaluation in the root node

        int[] bestDices = new int[root.getChildren().size()]; 
        int tmp = 0;
        for(Node a: root.getChildren()) {
            if(max == a.getNodeEvaluation()) bestDices[tmp++] = a.getNodeMove()[2]; // we store the dices with the best evaluation
        }
        int random = (int)(Math.random() * tmp); // we choose the dice randomly in case there are more than one dices with the same evaluation
        return bestDices[random]; // we return the final dice

    }
	
    //this function creates level 2 of the tree
  	//the arguments are the position of the player, the position of the opponent, the parent, the depth of the tree we create in this function and the parent's evaluation
	public void createOpponentSubtree(int currentPos, int opponentCurrentPos, Node parent, int depth, double parentEval) {
		int[] dices = new int[4 - board.numberOfWalls(board.getTiles()[opponentCurrentPos])]; //we initialize an array in which we will store the dices where the player can move (there isn't a wall)
		int count = 0;	//the number of available dices
		// we increase the number of available moves if there isn't a wall in this direction
		if(!parent.getNodeBoard().getTiles()[opponentCurrentPos].getUp()) dices[count++] = 1;
		if(!parent.getNodeBoard().getTiles()[opponentCurrentPos].getRight()) dices[count++] = 3;
		if(!parent.getNodeBoard().getTiles()[opponentCurrentPos].getDown()) dices[count++] = 5;
		if(!parent.getNodeBoard().getTiles()[opponentCurrentPos].getLeft()) dices[count++] = 7;
		
		//for every available dice
		for(int dice: dices) {
			Board opponentBoard = new Board(parent.getNodeBoard()); //we clone the board of the parent because we don't want the changes to affect our main board
			double evaluation = parentEval - evaluate("Minotaur", opponentCurrentPos, dice, currentPos, opponentBoard); //we store the evaluation of the move
			int[] m = move(opponentCurrentPos, dice, opponentBoard, false, false);  //we simulate the move in the clone board	
			//we create the Node opponent
			int opponentMove[] = {m[1], m[2], dice};
			Node opponent = new Node(depth, opponentMove, opponentBoard, evaluation);
			opponent.setParent(parent); //we set the parent of the child as the argument parent
			parent.getChildren().add(opponent); //we add the opponent node at the parent's arraylist
			
			//we restore the coordinates of the player
			x = currentPos/board.getN();
			y = currentPos%board.getN();
		}
	}
	
	//this function creates level 1 of the tree
	//the arguments are the position of the player, the position of the opponent, the root and the depth of the tree we create in this function
	public void createMySubtree(int currentPos, int opponentCurrentPos, Node root, int depth) {
		int[] dices = new int[4 - board.numberOfWalls(board.getTiles()[currentPos])]; //we initialize an array in which we will store the dices where the player can move (there isn't a wall)
		int count = 0; //the number of available dices
		// we increase the number of available moves if there isn't a wall in this direction
		if(!root.getNodeBoard().getTiles()[currentPos].getUp()) dices[count++] = 1;
		if(!root.getNodeBoard().getTiles()[currentPos].getRight()) dices[count++] = 3;
		if(!root.getNodeBoard().getTiles()[currentPos].getDown()) dices[count++] = 5;
		if(!root.getNodeBoard().getTiles()[currentPos].getLeft()) dices[count++] = 7;
		
		//for every available dice
		for(int dice: dices) {
			Board childBoard = new Board(root.getNodeBoard()); //we clone the board of the root because we don't want the changes to affect our main board
			double evaluation = evaluate("Theseus", currentPos, dice, opponentCurrentPos, childBoard); //we store the evaluation of the move
			int[] m = move(currentPos, dice, childBoard, false, true);	//we simulate the move in the clone board
			//we create the Node child
			int childMove[] = {m[1], m[2], dice};
			Node child = new Node(depth, childMove, childBoard, evaluation);
			child.setParent(root); //we set the parent of the child as the root
			root.getChildren().add(child); //we add the child at root's arraylist
			
			//we call this function to create the opponent subtree (level 2)
			createOpponentSubtree(m[0], opponentCurrentPos, child, depth + 1, evaluation);
			//we restore the coordinates of the player
			x = currentPos/board.getN();
			y = currentPos%board.getN();
		}
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
	public int getNextMove(int currentPos, int opponentCurrentPos) {
		int[] move = {x, y, 0}; //we initialize the NodeMove array of the node
		Node root = new Node(0, move, board, 0); //we create the root
		createMySubtree(currentPos, opponentCurrentPos, root, 1); //we create the subtree of the root
		int dice = chooseMinMaxMove(root); //we store the best dice the function chooseMinMaxMove returns
		
		//we place the final dice in the root
		move[2] = dice;
		root.setNodeMove(move);
		
		//we find the distances of Theseus before he moves
        int[] d = distances(currentPos, opponentCurrentPos);
        //we move the player
        int[] finalMove = move(currentPos, dice, board, true, true); //we set both realMove and gatherSup as true because it is a real move and we want Theseus to gather supplies
        int el0 = dice;
        int el1 = finalMove[3] > 0 ? 1 : 0; 
        int el2 = d[0];
        int el3 = d[1];
        //we add the new elements in path
        Integer[] a = { el0, el1, el2, el3 }; 
        path.add(a); 
        return finalMove[0]; //we return the tile id of the new position of Theseus
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
