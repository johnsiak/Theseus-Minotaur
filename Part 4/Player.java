import java.util.ArrayList;

public class Player {
	protected int playerId;	//the id of the player, 1 for Theseus and 2 for Minotaur
	protected String name;	//the name of the player
    protected Board board;	//the board of the game
    protected int score;		//the number of supplies a player has gathered
    protected int x;			//the first coordinate of the player
    protected int y;			//the second coordinate of the player
    protected double moveScore;	//the evaluation of the player's move
	public ArrayList <Integer[]> path; //an arraylist that contains information about each player's move

	//the default constructor of the class
    public Player() {
    	
    }

    //initialization of the variables of the class to the arguments
  	//this constructor will be used in the creation of the Players
	public Player(int playerId, String name, Board board, int score, int x, int y) {
        this.playerId = playerId;
        this.name = name;
	    this.board = board; 
        this.score = score;
        this.x = x;
        this.y = y;
        }

	//the setter and getter functions of the variables of the class
	public int getPlayerId() {
        return playerId;
    }
	
    public String getName() {
    	return name;
	}

    public Board getBoard() {
    	return board;
	}

    public int getScore() {
    	return score;
	}

    public int getX() {
    	return x;
	}

    public int getY() {
    	return y;
	}
    
    public double getMoveScore() {
        return moveScore;
    }

    public void setPlayerId(int playerId) {
    	this.playerId = playerId;
	}
    
    public void setName(String name) {
    	this.name = name;
	}

    public void setBoard(Board board) {
    	this.board = new Board(board);
	}

    public void setScore(int score) {
    	this.score = score;
	}

    public void setX(int x) {
    	this.x = x;
	}

    public void setY(int y) {
    	this.y = y;
    }
    
    public void setMoveScore(double moveScore) {
        this.moveScore = moveScore;
    }
    
    //this function decides the move of Minotaur and moves Theseus to a specific direction according to the argument dice(if it's not a random move)
    //the arguments are the tile id of the player before the move, the dice, realMove is true only when the player moves in main, gatherSup is true when we want to erase the supply from board and randomMove is true when we want a random dice
    public int[] move(int id, int dice, Board board, boolean realMove, boolean gatherSup, boolean randomMove) {
    	
    	int oldId = id; //oldId is a variable to store the current id
    	
    	int k = 2;		/*k is selected randomly and decides where the player will move (only if it's a random move)
    					  k must be {1, 3, 5, 7}*/

    	while((k % 2) != 1) k = (int) (Math.random() * 8);
    	if(!randomMove) k = dice; //the move isn't random
    	
    	switch(k) {
    		case 1: 
    			if(!board.getTiles()[id].getUp()) {		//if there is no wall upwards
    				id += board.getN();
    				x++;
    				if(realMove) System.out.println(name + " moved upwards\n"); //only if player moves in function main() then the move will be printed
    			}
    			break;
    			
    		case 3:
   				if(!board.getTiles()[id].getRight()) {
   					id++;
   					y++;
   					if(realMove) System.out.println(name + " moved to the right\n");
   				}
   				break;
    				
    		case 5:
    			if(!board.getTiles()[id].getDown()) {
    				id -= board.getN();
    				x--;
    				if(realMove) System.out.println(name + " moved downwards\n");
   				}
   				break;	
    				
   			case 7:
   				if(!board.getTiles()[id].getLeft()) {
    				id--;
    				y--;
    				if(realMove) System.out.println(name + " moved to the left\n");
    			}
    			break;		
    	}	

    	//if the player cannot move because of a wall
    	if(oldId == id && realMove) System.out.println(name + " cannot move" + "\n");
    	
    	
    	int supplyId = -1;	//we use this as a default in case no supplies are gathered
    	if(gatherSup && (board.hasSupply(id) > 0) && (playerId != 2)) { //if we want the player to gather supplies & player isn't Minotaur & if tile id contains a supply
    		if(realMove) { //if it is a real move we want to print the status of the player and increase his score
    			System.out.println(name + " collected supply S" + board.hasSupply(id) + "\n");
    			score++;						//because the player gathered a supply
    		}
    		supplyId = board.hasSupply(id); //takes the gathered supply id and places it in supplyId
    		board.getSupplies()[board.hasSupply(id) - 1].setSupplyTileId(-1);	//sets the supply tile id as -1 so that it is not considered a supply anymore
    	}
    	int[] move = {id, x, y, supplyId};
    	return move;
    }
    
    //this assistant function moves the player and returns the current position of the player
    //the argument are the positions of both player
	public int getNextMove(int currentPos, int opponentCurrentPos) {
		int[] a = move(currentPos, 0, board, true, true, true);
		return a[0];
	}
	
	//this function prints the statistics of the game
	//the argument is a boolean which indicates the end of the game
	public String statistics(boolean endOfGame) {	
		//there's no statistics for the random player
		return "";
	}
}
