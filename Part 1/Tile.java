/*Ioannis Siakavaras, 10053, 6946937774, siakavari@ece.auth.gr
  Christoforos Chatziantoniou, 10258, 6946495698, cchristofo@ece.auth.gr
  Team 186
 */

public class Tile {

	private int tileId;		//the id of the tile that ranges from 0 to N * N-1 ( [0, N * N) , N the size of the board)
	private int x;			//the first coordinate
	private int y;			//the second coordinate
	private boolean up;		//true if the tile has a wall upwards 
	private boolean down;	//true if the tile has a wall downwards
	private boolean left;	//true if the tile has a wall to the left
	private boolean right;	//true if the tile has a wall to the right
	
	//the default constructor of the class
	public Tile() {
		
	}
	
	//initialization of the variables of the class to the arguments
	//this constructor will be used in the creation of the tiles
	public Tile(int tileId, int x, int y, boolean up, boolean down, boolean left, boolean right) {
		this.tileId = tileId;
		this.x = x;
		this.y = y;
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
	}
	
	//initialization of the variables of the object through a type Tile argument
	public Tile(Tile theTile) {
		tileId = theTile.tileId;
		x = theTile.x;
		y = theTile.y;
		up = theTile.up;
		down = theTile.down;
		left = theTile.left;
		right = theTile.right;
	}
	
	//the setter and getter functions of the variables of the class
	public int getTileId() {
		return tileId;
	}

	public void setTileId(int tileId) {
		this.tileId = tileId;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean getUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}
	
	public boolean getDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public boolean getLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public boolean getRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}	
}