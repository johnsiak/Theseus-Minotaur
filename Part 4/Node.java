/*Ioannis Siakavaras, 10053, 6946937774, siakavari@ece.auth.gr
  Christoforos Chatziantoniou, 10258, 6946495698, cchristofo@ece.auth.gr
  Team 186
 */

import java.util.ArrayList;

public class Node { // this class simulates the nodes of the tree that will  be used in min max algorithm
	private Node parent;                      // the parent node of the node
    private ArrayList <Node> children;        // the children nodes of the node stored in an arraylist structure
    private int nodeDepth;                    // the depth (max 2 in our case) of the node
    private int[] nodeMove;                   // an array that contains the x and y coordinates and the dice that corresponds to the node 
    private Board nodeBoard;                  // the board of the game for this node 
    private double nodeEvaluation;            // the evaluation of this node move
	
	//the default constructor of the class
	public Node(){
		
	}
	
	//initialization of the variables of the class to the arguments
  	//this constructor will be used in the creation of the Nodes
	public Node(int nodeDepth, int[] nodeMove, Board nodeBoard, double nodeEvaluation){
		this.children = new ArrayList<Node>();
		this.nodeDepth = nodeDepth;
		this.nodeMove = nodeMove;
		this.nodeBoard = new Board(nodeBoard);
		this.nodeEvaluation = nodeEvaluation;
	}
	
	//the setter and getter functions of the variables of the class
	public Node getParent() {
        return parent;
    }

    public ArrayList <Node> getChildren() {
        return children;
    }

    public int getNodeDepth() {
        return nodeDepth;
    }

    public int[] getNodeMove() {
        return nodeMove;
    }

    public Board getNodeBoard() {
        return nodeBoard;
    }

    public double getNodeEvaluation() {
        return nodeEvaluation;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setChildren(ArrayList<Node>children) {
        this.children = new ArrayList<Node>(children);
    }

    public void setNodeDepth(int nodeDepth) {
        this.nodeDepth = nodeDepth;
    }

    public void setNodeMove(int[] nodeMove) {
        this.nodeMove = nodeMove;
    }

    public void setNodeBoard(Board nodeBoard) {
        this.nodeBoard = new Board(nodeBoard);
    }

    public void setNodeEvaluation(double nodeEvaluation) {
        this.nodeEvaluation = nodeEvaluation;
    }
}