/*Ioannis Siakavaras, 10053, 6946937774, siakavari@ece.auth.gr
  Christoforos Chatziantoniou, 10258, 6946495698, cchristofo@ece.auth.gr
  Team 186
 */

public class Player {
	private int playerId;	//the id of the player, 1 for Theseus and 2 for Minautor
	private String name;	//the name of the player
    private Board board;	//the board of the game
    private int score;		//the number of supplies a player has gathered
    private int x;			//the first coordinate of the player
    private int y;			//the second coordinate of the player

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
    
    //this function randomly decides the move of the player
    //the argument is the tile id of the player before the move
    public int[] move(int id) {
    	
    	int oldId = id; //oldId is a variable to store the current id
    	
    	int k = 2;		/*k is selected randomly and decides where the player will move
    					  k must be {1, 3, 5, 7}*/
    	
    	while((k % 2) != 1) k = (int) (Math.random() * 8);
    	
    	switch(k) {
    		case 1: 
    			if(!board.getTiles()[id].getUp()) {
    				id += board.getN();
    				x++;
                    System.out.println(name + " moved upwards\n"); 
    			}
    			break;
    			
    		case 3:
   				if(!board.getTiles()[id].getRight()) {
   					id++;
   					y++;
                    System.out.println(name + " moved to the right\n");

   				}
   				break;
    				
    		case 5:
    			if(!board.getTiles()[id].getDown()) {
    				id -= board.getN();
    				x--;
    				System.out.println(name + " moved downwards\n");
   				}
   				break;	
    				
   			case 7:
   				if(!board.getTiles()[id].getLeft()) {
    				id--;
    				y--;
                    System.out.println(name + " moved to the left\n");

    			}
    			break;		
    	}	

    	//if the player cannot move because of a wall
    	if(oldId == id) System.out.println(name + " cannot move" + "\n");
    	
    	
    	int supplyId = -1;	//we use this as a default in case no supplies are gathered
    	if((board.hasSupply(id) >= 0) && (playerId != 2)) { //if player isn't Minautor & if tile id contains a supply
    		System.out.println(name + " collected supply S" + board.hasSupply(id) + "\n");
    		score++;						//because the player gathered a supply
    		supplyId = board.hasSupply(id); //takes the gathered supply id and places it in supplyId
    		board.getSupplies()[board.hasSupply(id) - 1].setSupplyTileId(-1);	//sets the supply tile id as -1 so that it is not considered a supply anymore
    	}
    	int[] move = {id, x, y, supplyId}; 
    	return move;
    }
}