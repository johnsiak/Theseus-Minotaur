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
		System.out.println("The Game starts!\n");
		
		Board board = new Board(15, 4, (15 * 15 * 3 + 1) / 2 ); 	//initializes the board
		board.createBoard();										//creates the labyrinth
		
		//initializes the players and places them in their initial tiles
		MinMaxPlayer player1 = new MinMaxPlayer(1, "Theseus", board, 0, 0, 0);	
		Player player2 = new Player(2, "Minotaur", board, 0, board.getN()/2, board.getN()/2);
		
		//prints the initial state of the labyrinth when Theseus enters
		printer(board.getStringRepresentation(0, (board.getN() * board.getN())/2));
		
		//we close the labyrinth
		board.getTiles()[0].setDown(true);
		
		//Round 1 begins
		System.out.println("ROUND 1\n");
		round++;
		int n = 0; //counts the moves. if n = 100 the game is a tie
		int currentPos = player1.getNextMove(0, board.getN() * board.getN()/2); 	//the first move of Theseus. currentPos stores the position of Theseus after he moves.
		int[] move2 = player2.move(board.getN() * board.getN()/2, 0, board, true, false);	//the first move of Minotaur. move2 stores what the array move() function returns
		n++;
		
		//prints the statistics of Theseus' first move
		player1.statistics(false);
		//prints the board after the first move of each player
		printer(board.getStringRepresentation(currentPos, move2[0]));

		while (n < 100) {
			System.out.println("ROUND " + (++round) + "\n"); 
			
			//Theseus plays
			currentPos = player1.getNextMove(currentPos, move2[0]);
            
            //if he has gathered all the supplies, Theseus wins
            if (player1.getScore() == board.getS()) {
        		player1.statistics(false);
                printer(board.getStringRepresentation(currentPos, move2[0]));
                System.out.println("Theseus Won!\n"); 
        		player1.statistics(true);
                break;
            }
            //if Theseus meets Minotaur, Minotaur wins
            else if (currentPos == move2[0]) {
            	player1.statistics(false);
                printer(board.getStringRepresentation(currentPos, move2[0]));
                System.out.println("Minotaur Won!\n");
        		player1.statistics(true);
                break;
            }
            
            //Minotaur plays
            move2 = player2.move(move2[0], 0, board, true, false);
            
          //if Minotaur meets Theseus, Minotaur wins
            if (currentPos == move2[0]) {
            	player1.statistics(false);
                printer(board.getStringRepresentation(currentPos, move2[0])); 
                System.out.println("Minotaur Won!\n");
        		player1.statistics(true);
                break;
            }
            //prints the statistics of Theseus and the board in each round
            player1.statistics(false);
            printer(board.getStringRepresentation(currentPos, move2[0])); 
            n++;
        }
		//if it's a tie 
		if (n == 100) {
			System.out.println("Tie!\n");
    		player1.statistics(true);
		}
	}
}
