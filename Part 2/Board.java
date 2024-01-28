public class Board {
	
	private int N;				//the dimension of the labyrinth
	private int S;				//the initial number of the supplies in the labyrinth
	private int W;				//the maximum number of walls in the labyrinth
	private Tile[] tiles;		//the array that contains the tiles of the board with a size of N * N
	private Supply[] supplies;	//the array that contains the supplies of the board with a size of S
	
	//the default constructor of the class
	public Board() {
		
	}
	
	//initialization of the variables of the class to the arguments
	//this constructor will be used in the creation of the board
	public Board(int N, int S, int W) {
		this.N = N;
		this.S = S;
		this.W = W;
		tiles = new Tile[N * N];
		supplies = new Supply[S];
	}
	
	//the getter functions of the variables of the class
	public int getN() {
		return N;
	}
	
	public int getS() {
		return S;
	}
	
	public int getW() {
		return W;
	}
	 
	public Tile[] getTiles() {
        return tiles;
    }

    public Supply[] getSupplies() {
    	return supplies;
	}
    
    //initialization of the variables of the object through a type Board argument
	public Board(Board theBoard) {
		N = theBoard.getN();
	    S = theBoard.getS();
	    W = theBoard.getW();
	    tiles = new Tile[N * N];
	    supplies = new Supply[S];
    }

	//the setter functions of the variables of the class
	public void setN(int N) {
		this.N = N;
	}

	public void setS(int S) {
		this.S = S;
	}

	public void setW(int W) {
        this.W = W;	
	}        

	public void setTiles(Tile[] tiles) {
		this.tiles = tiles;
	}

    public void setSupplies(Supply[] supplies) {
    	this.supplies = supplies;
	}
    
    /*assistant function that will be used in createTile function
    counts the number of walls of a tile
    takes a tile as argument and returns its number of walls*/
    public int numberOfWalls(Tile theTile) {
        int numberOfWalls = 0;
        if (theTile.getUp()) numberOfWalls++;
        if (theTile.getDown()) numberOfWalls++;
        if (theTile.getLeft()) numberOfWalls++;
        if (theTile.getRight()) numberOfWalls++;
        return numberOfWalls; 
    }
     
    //this function creates the tiles randomly
    public void createTile() {

    	//the tiles are initialized (id, x, y and the walls) through the constructor
        for (int i = 0; i < tiles.length; i++) 
        	tiles[i] = new Tile(i, i/N, i%N, false, false, false, false); /*based on picture 3 and 4 on the assignment file, the coordinates are x = i/N 
        																	and y = i%N , where i is the tile id. We also initialize all the walls
        																	as false*/

        int count = 0; //number of walls placed in the board
        
        //the perimeter is initialized, apart from the first tile(down wall) so that Theseus can enter the board
        for (int i = 0; i < tiles.length; i++) {
            if ((tiles[i].getX() == 0) && (tiles[i].getTileId() != 0)) {
                tiles[i].setDown(true);
                count++;
            }
            if (tiles[i].getY() == 0) {
                tiles[i].setLeft(true);
                count++;
            }
            if (tiles[i].getY() == (N-1)) {
                tiles[i].setRight(true);
                count++;
            }
            if (tiles[i].getX() == (N-1)) {
                tiles[i].setUp(true);
                count++;
            }
        }
        
        //w is a random number between [W/2, W]
        int w = ((int) (W/2 + Math.random() * (W/2 + 1)));
        
        //w % 2 explained in the report
        if (w % 2 == 0) w--;
        while (count < w) { //w is the limit for the creation of the walls
        	
            int k = ((int) (1 + Math.random() * (N * N - 1))); /*picks a random tile with tile id from [1, N*N-1]. 
            												The 0 id tile is excluded because we will add an extra wall after theseus enters the board*/ 
            
            if (numberOfWalls(tiles[k]) < 2) { 		//checks if there is room for another wall(the limit is 2 walls per tile)
                int a = (int) (Math.random() * 5);	//decides where and if a wall will be placed
                
                switch (a) {
                	//the program also checks if the neighbor tile also has space for an extra wall
                
                    case 0: if (!tiles[k].getUp() && (numberOfWalls(tiles[k+N]) < 2)) { 
                            tiles[k].setUp(true);	  		//adds the wall to the tile
                            tiles[k+N].setDown(true); 		//adds the wall to the neighbor tile too
                            count += 2;				  		//because we count each inner wall twice 
                            }
                            break;
                    case 1: if (!tiles[k].getDown() && (k != N) &&(numberOfWalls(tiles[k-N]) < 2)) { //*if k = N a down wall will be placed in id = N. 
                            tiles[k].setDown(true);													//This way id = 0(Theseus initial tile) will have 3 walls when we close the wall after he enters the labyrinth
                            tiles[k-N].setUp(true);
                            count += 2; 
                            }
                            break;
                    case 2: if (!tiles[k].getLeft() && (k != 1) && (numberOfWalls(tiles[k-1]) < 2)) { //k != 1 like we did before (more in the report)
                            tiles[k].setLeft(true);
                            tiles[k-1].setRight(true);
                            count += 2;
                            }
                            break;
                    case 3: if (!tiles[k].getRight() && (numberOfWalls(tiles[k+1]) < 2)) {
                            tiles[k].setRight(true);
                            tiles[k+1].setLeft(true);
                            count += 2;
                            }
                            break;
                    default: break; //if a = 4 it doesn't add a wall
                }
            }
        }
    }
    
    //this function creates the supplies randomly
    public void createSupply() {
    	
    	int i, j, k;
    	
    	for(i = 0; i < S; i++)
    		supplies[i] = new Supply(i + 1, -1, -1, -1); //initializes the supply (i + 1 because the supply id is between [1,S]
    	
    	int[] a = new int[S]; 	//this array stores the supply tile ids for the supplies already created
    	for(i = 0; i < S; i++)
    		a[i] = -1;			//this initializes the elements of the array as -1
    	
    	for(i = 0; i < S; i++) {
    		k = (int) (Math.random() * N * N); // k is a random tile id between [0, N*N-1] where the supply is going to be placed
    		for(j = i; j >= 0; j--) {
    			if(k == a[j] || k == 0 || k == N * N / 2) { 	//checks if k is acceptable (more in the report)
    				i--;										//i-- because we didn't add a supply
    				break;
    			}
    			else {
    				a[i] = k;									//if k is acceptable we add the supply tile id in the array
    				supplies[i].setSupplyTileId(k);				//we set the supply tile id as k
    			}
    		}
    	}
    	
    	//we calculate the coordinates
    	for(i = 0; i < S; i++) {
    		supplies[i].setX(supplies[i].getSupplyTileId()/N);
    		supplies[i].setY(supplies[i].getSupplyTileId()%N);
    	}
    }
    
    //calls the other 2 functions and creates the board
    public void createBoard() {
    	createTile();
    	createSupply();
    }  
    
    //an assistant function
    //takes a tile id and returns the supply id if this tile id contains a supply else returns -1
    public int hasSupply(int tileId) {
        for (int i = 0; i < S; i++) {
            if (tileId == supplies[i].getSupplyTileId())
                return i + 1; //because supply ids are between [1,S]
        }
        return -1;
    }
    
    //creates a 2 dimensional string array that represents the labyrinth
    public String[][] getStringRepresentation(int theseusTile, int minotaurTile) {
		
		String[][] board = new String[2 * N + 1][N]; 
		
		for (int i = 0; i < (2 * N - 1); i+=2) { //i < (2 * N - 1) because the maximum we want to reach is the 2 * N - 2 (third from the top) row an in page 6 in the assignment pdf
			for (int j = 0; j < N; j++) {
				
				//finds the tile id (better explained in the report)
				int id = (i * N)/2 + j; 
				board[i][j] = tiles[id].getDown() ? "+---" : "+   "; 	//creates the down walls 
				board[i+1][j] = tiles[id].getLeft() ? "|" : " ";		//creates the left walls (i is always an odd number as it increases by 2)
																		//board[i] represents the rows with horizontal walls
																		//board[i+1] represents the rows with vertical walls
				
				//explained in the report
				if (id == minotaurTile) {
					if (id == theseusTile)
						board[i+1][j] += "T M"; 
					else if (hasSupply(id) >= 0)
						board[i+1][j] += ("S" + hasSupply(id) + "M"); 
					else
						board[i+1][j] += " M ";
				}
				else if (id == theseusTile)
					board[i+1][j] += " T ";
				else if (hasSupply(id) >= 0)
					board[i+1][j] += ("S" + hasSupply(id) + " "); 
				else
					board[i+1][j] += "   ";
				
			}
		}
		
		//creates the top row of the board
		for (int j = 0; j < N; j++)
			board[2 * N][j] = "+---";
		
		//creates the far-right boarder
		for (int i = 0; i < (2 * N + 1); i++) 
			board[i][N-1] += (i%2 == 0 ? "+" : "|");
		
		return board;
	}
}
