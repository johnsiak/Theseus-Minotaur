public class Game {

	private static int round;	//the round of the game, we define it as static so that we can use this in main
	
	//the default constructor of the class which sets round as 0
	public Game() {
		round = 0;
	}
	
	//initialization of the variable
	public Game(int thisRound) {
		round = thisRound;
	}
	
	//the setter and getter functions of the variable of the class
	public int getRound() {
		return round;
	}
	
	public void setRound(int thisRound) {
		round = thisRound;
	}
	
	//this assistant function prints the board of the game
	public static void printer(String[][] board) {
		int size = board[0].length; 			//size = N
		for(int i = 2 * size; i >= 0; i--) {	//i-- because we want to print it from the top to the bottom
			for(int j = 0; j < size; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		System.out.println("The Game starts!" + "\n");
		
		Board board = new Board(15, 4, (15 * 15 * 3 + 1) / 2 ); 	//initializes the board
		board.createBoard();										//creates the labyrinth
		
		//initializes the players and places them in their initial tiles
		Player player1 = new Player(1, "Theseus", board, 0, 0, 0);	
		Player player2 = new Player(2, "Minotaur", board, 0, board.getN()/2, board.getN()/2);
		
		//prints the initial state of the labyrinth when Theseus enters
		printer(board.getStringRepresentation(0, (board.getN() * board.getN())/2));
		
		//we close the labyrinth
		board.getTiles()[0].setDown(true);
		
		//Round 1 begins
		System.out.println("ROUND 1" + "\n");
		round++;
		int n = 0; //counts the moves. if n = 100 the game is a tie
		int[] move1 = player1.move(0);									//the first move of Theseus. move1 stores the array move() function returns
		int[] move2 = player2.move(board.getN() * board.getN()/2);		//the first move of Minautaur. move2 stores the array move() function returns
		n++;
		
		//prints the board after the first move of each player
		printer(board.getStringRepresentation(move1[0], move2[0]));
		
		
		while (n < 100) {
			System.out.println("ROUND " + (++round) + "\n"); 
			
			//Theseus plays
            move1 = player1.move(move1[0]); 
            
            //if he has gathered all the supplies, Theseus wins
            if (player1.getScore() == board.getS()) {
                printer(board.getStringRepresentation(move1[0], move2[0]));
                System.out.println("Theseus Won!"); 
                break;
            }
            //if Theseus meets Minotaur, Minautor wins
            else if (move1[0] == move2[0]) {
                printer(board.getStringRepresentation(move1[0], move2[0]));
                System.out.println("Minotaur Won!"); 
                break;
            }
            
            //Minotaur plays
            move2 = player2.move(move2[0]);
            
          //if Minotaur meets Theseus, Minautor wins
            if (move1[0] == move2[0]) {
                printer(board.getStringRepresentation(move1[0], move2[0])); 
                System.out.println("Minotaur Won!"); 
                break;
            }
            //prints the board in each round
            printer(board.getStringRepresentation(move1[0], move2[0])); 
            n++;
        }
		//if it's a tie 
		if (n == 100) System.out.println("Tie!");
	}
}
