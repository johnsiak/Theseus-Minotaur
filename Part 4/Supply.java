public class Supply {
	
	private int supplyId; 	   //the id of the supply that ranges from 1 to S ( [1, S], where S the number of the supplies)
	private int x;			   //the first coordinate
	private int y;			   //the second coordinate
	private int supplyTileId;  //the id of the tile that has a supply
	
	
	//initialization of the variables of the object to -1 
	public Supply() {
		supplyId = -1;
		x = -1;
		y = -1;
		supplyTileId = -1;
	}
	
	//initialization of the variables of the class to the arguments
	public Supply(int supplyId, int x, int y, int supplyTileId) {
		this.supplyId = supplyId;
		this.x = x;
		this.y = y;
		this.supplyTileId = supplyTileId;
	}
	
	//initialization of the variables of the object through a type Supply argument
	public Supply(Supply theSupply) {
		supplyId = theSupply.supplyId;
		x = theSupply.x;
		y = theSupply.y;
		supplyTileId = theSupply.supplyTileId;
	}
	
	//the setter and getter functions of the variables of the class
	public int getSupplyId() {
		return supplyId;
	}
	
	public void setSupplyId(int supplyId) {
		this.supplyId = supplyId;
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
	
	public int getSupplyTileId() {
		return supplyTileId;
	}
	
	public void setSupplyTileId(int supplyTileId) {
		this.supplyTileId = supplyTileId;
	}
}
