/*Ioannis Siakavaras, 10053, 6946937774, siakavari@ece.auth.gr
  Christoforos Chatziantoniou, 10258, 6946495698, cchristofo@ece.auth.gr
  Team 186
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.Border;

//this class runs the Game
public class Game {

	private static JButton button1, button2, button3; //we have three buttons: button1 for Play, button2 for Generate Board, button3 for Quit

	private static Player player1;    //a static variable for Theseus            /we declare the players as type Player 
	private static Player player2;    //a static variable for Minotaur            and they might be Heuristic or MinMax later
	private static int cb1 = 0;        //holds the index of combo box of player type of Theseus
	private static int cb2 = 0;        //holds the index of combo box of player type of Minotaur

	private static JPanel panelBoard;    //this panel simulates the board of the game
	
	private static int n = 0; //counts the moves. if n = 100 the game is a tie
	private static int turn;    //if turn == 0 then Theseus plays first, else Minotaur plays first

	private static int currentPos1; //currentPos of Teseus
	private static int currentPos2; //currentPos of Minotaur

	private static boolean endOfGame = false; //true if the game ends, tie or with a winner

	private static String verdict = ""; //a string that holds the result of the game
                                      //"Tie!" , "Theseus Won!" or "Minotaur Won!"

	private static int round = 0;    //the round of the game

	//the default constructor of the class
	public Game() {

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
	
	//this assistant function returns a string representation of the move score of the player that we use to update the move score label 
	public static String calculateMoveScore(int id, int oldPos, int size, int round) { 	//id the playerId, oldPos the position of player before the move, size the size N of the board, round the round of the game
		
        int dice = 0;		//the dice of the move, initialized 0 and never changed if we have random player
        double score = 0;	//the move score		

        if (id == 1) {					//if player is Theseus
            if (cb1 == 1 || cb1 == 2) {				
                dice = player1.path.get(round)[0];	//if player is Heuristic or MinMax we use the path
                score = player1.getMoveScore();		//we get score using the getter of the class
            }
            else {
                int distPreviousFromNew = currentPos1 - oldPos;
                if (distPreviousFromNew == 1) dice = 3;
                else if (distPreviousFromNew == size) dice = 1;
                else if (distPreviousFromNew == -1) dice = 7;
                else if (distPreviousFromNew == -size) dice = 5;
            }
        }

        else {
            if (cb2 == 1 || cb2 == 2) {
                dice = player2.path.get(round)[0];
                score = player2.getMoveScore();
            }
            else {
                int distPreviousFromNew = currentPos2 - oldPos;
                if (distPreviousFromNew == 1) dice = 3;
                else if (distPreviousFromNew == size) dice = 1;
                else if (distPreviousFromNew == -1) dice = 7;
                else if (distPreviousFromNew == -size) dice = 5;
            }
        }

        DecimalFormat df = new DecimalFormat("#.##");	

        String d = String.valueOf(dice);	
        String s = df.format(score);		//we keep 2 decimal places

        String str = "Move Score: " + d + " -> " + s;	//we get string representation of move score
        return str;										//we return the string representation

    }
	
	//this method decides if Theseus or Minotaur plays first randomly
	public static int setTurns() {
		if((int) (Math.random() * 2) == 0) return 0;
		else return 1;
	}
	
	//this method simulates each round of the game
	public static Board mainBody(Board board) {			//takes the board of the game as argument
		//initializes the players and places them in their initial tiles
		if(round == 1) {
			//we close the labyrinth
			board.getTiles()[0].setDown(true);
		
			//Round 1 begins
			System.out.println("ROUND 1\n");
		
			if(cb1 == 1) {
				player1 = new HeuristicPlayer(1, "Theseus", board, 0, 0, 0);
			}
			else if(cb1 == 2) {
				player1 = new MinMaxPlayer(1, "Theseus", board, 0, 0, 0);
			}
			else {
				player1 = new Player(1, "Theseus", board, 0, 0, 0);
			}
			if(cb2 == 1) {
				player2 = new HeuristicPlayer(2, "Minotaur", board, 0, board.getN()/2, board.getN()/2);
			}
			else if(cb2 == 2) {
				player2 = new MinMaxPlayer(2, "Minotaur", board, 0, board.getN()/2, board.getN()/2);
			}
			else {
				player2 = new Player(2, "Minotaur", board, 0, board.getN()/2, board.getN()/2);
			}
				
			turn = setTurns();		//if turn == 0 Theseus plays first, else if turn == 1 Minotaur plays first
			if(turn == 0) {
				currentPos1 = player1.getNextMove(0, board.getN() * board.getN()/2); 	//the first move of Theseus. currentPos1 stores the position of Theseus after he moves.
				currentPos2 = player2.getNextMove(board.getN() * board.getN()/2, currentPos1);	//the first move of Minotaur. currentPos2 stores the position of Minotaur after he moves.
			}
			else {
				currentPos2 = player2.getNextMove(board.getN() * board.getN()/2, 0);	//the first move of Minotaur. currentPos2 stores the position of Minotaur after he moves.
				currentPos1 = player1.getNextMove(0, currentPos2); 	//the first move of Theseus. currentPos1 stores the position of Theseus after he moves.
			}
			n++;  //we update n
				
			//prints the statistics of the first move for the players that are not random
			player1.statistics(false);
			player2.statistics(false);
			//prints the board after the first move of each player
			printer(board.getStringRepresentation(currentPos1, currentPos2));
		}
		
		if(round != 1) {	//in every following round
			System.out.println("ROUND " + round + "\n"); 
			
			if(turn == 0) {
				//Theseus plays
				currentPos1 = player1.getNextMove(currentPos1, currentPos2);
		          
				//if he has gathered all the supplies, Theseus wins
				if (player1.getScore() == board.getS() && endOfGame == false) {
					printer(board.getStringRepresentation(currentPos1, currentPos2));
					System.out.println("Theseus Won!\n"); 
					verdict = "Theseus Won!";	
					endOfGame = true;
				}
				//if Theseus meets Minotaur, Minotaur wins
				else if (currentPos1 == currentPos2 && endOfGame == false) {
					printer(board.getStringRepresentation(currentPos1, currentPos2));
					System.out.println("Minotaur Won!\n");
					verdict = "Minotaur Won!";
					endOfGame = true;
				}
		            
				//Minotaur plays
				if(!endOfGame) currentPos2 = player2.getNextMove(currentPos2, currentPos1);
		            
				//if Minotaur meets Theseus, Minotaur wins
				if (currentPos1 == currentPos2 && endOfGame == false) {
					printer(board.getStringRepresentation(currentPos1, currentPos2)); 
					System.out.println("Minotaur Won!\n");
					verdict = "Minotaur Won!";
					endOfGame = true;
				}
			}
					
			else {
				//Minotaur plays
				currentPos2 = player2.getNextMove(currentPos2, currentPos1);
		            
				//if Minotaur meets Theseus, Minotaur wins	
				if (currentPos1 == currentPos2 && endOfGame == false) {
					printer(board.getStringRepresentation(currentPos1, currentPos2)); 
					System.out.println("Minotaur Won!\n");
					verdict = "Minotaur Won!";
					endOfGame = true;
				}
				
				//Theseus plays
				if(!endOfGame) currentPos1 = player1.getNextMove(currentPos1, currentPos2);
		            
				//if he has gathered all the supplies, Theseus wins
				if (player1.getScore() == board.getS() && endOfGame == false) {
					printer(board.getStringRepresentation(currentPos1, currentPos2));
					System.out.println("Theseus Won!\n"); 
					verdict = "Theseus Won!";
					endOfGame = true;
				}
				
				//if Theseus meets Minotaur, Minotaur wins
				else if (currentPos1 == currentPos2 && endOfGame == false) {
					printer(board.getStringRepresentation(currentPos1, currentPos2));
					System.out.println("Minotaur Won!\n");
					verdict = "Minotaur Won!";
					endOfGame = true;
				}	
			}
			//prints the statistics of non random players and the board in each round
			player1.statistics(false);
			player2.statistics(false);
			printer(board.getStringRepresentation(currentPos1, currentPos2)); 
			n++;
		}
		
		//if it's a tie 
		if (n == 100) {
			endOfGame = true;
			System.out.println("Tie!\n");
			verdict = "Tie!";
		}
		
		if(endOfGame) {
			//in case we want to play again we reset these 
			n = 0;
			round = 0; 
			button1.setEnabled(false);
			button2.setEnabled(true);
		}
		return board;	//mainBody returns the updated board
	}
	
	public static void main(String[] args) {
		
		//a window to ask if we want to play or not
		int answer = JOptionPane.showConfirmDialog(null, "Do you want to play?", "Welcome!", JOptionPane.YES_NO_OPTION);
		if (answer == 1) System.exit(0);
		
		//the frame of the game
		JFrame frame = new JFrame();
		frame.setTitle("Theseus and Minotaur"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 800);
		frame.setResizable(false);	//we can't change the size of the frame
		
		//the border of the frame
		Border border = BorderFactory.createLineBorder(new Color(0x68ACDE), 3); //custom color
        JLabel label = new JLabel(); 
        label.setBorder(border);
		
        //we create our board
        Board board = new Board(15, 4, (15 * 15 * 3 + 1) / 2 ); 	
		board.createBoard();
        
		//we create the ComboBoxes for each player
		String[] typeOfPlayer = {"Random Player", "Heuristic Player", "MinMax Player"}; 
		JComboBox <String> theseusCombo = new JComboBox<>(typeOfPlayer); 
		JComboBox <String> minotaurCombo = new JComboBox<>(typeOfPlayer);
		
		//we store the choice of player (Random, Heuristic, MinMax)
		theseusCombo.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cb1 = theseusCombo.getSelectedIndex();
			}
		});
		
		minotaurCombo.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cb2 = minotaurCombo.getSelectedIndex();
			}
		});
		
		//we disable the ComboBoxes after we choose the players we want to play with
		theseusCombo.setEnabled(false);
		minotaurCombo.setEnabled(false);
		
		//we add the ComboBoxes to their panel
		JPanel panel1 = new JPanel();
		panel1.setBounds(3, 600, 140, 50);
		panel1.setLayout(new BorderLayout());
		panel1.add(theseusCombo);
		
		JPanel panel2 = new JPanel();
		panel2.setBounds(843, 600, 140, 50);
		panel2.setLayout(new BorderLayout());
		panel2.add(minotaurCombo);
		
		
		//we create the Round label
	    JLabel labelRound = new JLabel();
	    labelRound.setBounds(25, 25, 70, 20);
	    labelRound.setText("Round: " + round);

	    //we create the Theseus labels
	    JLabel labelTheseus = new JLabel("Theseus");
	    labelTheseus.setBounds(60, 500, 70, 15);

	    JLabel labelTheseusDice = new JLabel();
	    labelTheseusDice.setText("Move Score: 0");
	    labelTheseusDice.setBounds(45, 515, 135, 15);

	    JLabel labelTheseusScore = new JLabel();
	    labelTheseusScore.setText("Total Score: 0");
	    labelTheseusScore.setBounds(45, 530, 135, 15);

	    //we create the Minotaur labels
	    JLabel labelMinotaur = new JLabel("Minotaur");
	    labelMinotaur.setBounds(855, 500, 70, 15);

	    JLabel labelMinotaurDice = new JLabel();
	    labelMinotaurDice.setText("Move Score: 0");
	    labelMinotaurDice.setBounds(840, 515, 135, 15);

	    JLabel labelMinotaurScore = new JLabel();
	    labelMinotaurScore.setText("Total Score: 0");
	    labelMinotaurScore.setBounds(840, 530, 135, 15);

		//we create the buttons
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        
        //we set button1
        button1.setBounds(493, 700, 70, 30);	//we place Play button to its position
		button1.setText("Play");				//we set the name of the button
		button1.setFocusable(false);			//we don't want Play button to be focusable
		button1.setForeground(Color.green);		

		//if we press "Play"
		button1.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				round++;								//we update the round of the game
                minotaurCombo.setEnabled(false);		//once we press Play the combo boxes will be disabled
                theseusCombo.setEnabled(false);
                
                int rd = round - 1;		//we need this for the case of the end of the game because round is set to 0 after the call of mainBody()
                
                int oldPos1 = currentPos1;		//we store the positions before the move of each player
                int oldPos2 = currentPos2;
                
                frame.setVisible(true);		//we set the frame as visible
                
                Board boardTest = new Board (mainBody(board)); 	//run mainBody and store the new board
                panelBoard.setBounds(0, 0, 0, 0);				
                frame.remove(panelBoard);						//remove previous panelBoard from frame
                panelBoard = new MyPanel(boardTest, currentPos1, currentPos2);	//update the panelBoard
                panelBoard.setBounds(240, 35, 506, 506);
                
                labelRound.setBounds(0, 0, 0, 0); 
                frame.remove(labelRound);		//remove previous labelRound
                rd++;
                labelRound.setText("Round: " + rd);		//update 
                labelRound.setBounds(25, 25, 70, 20);
                //remove and update the labels of move scores
                labelTheseusDice.setBounds(0, 0, 0, 0);
                frame.remove(labelTheseusDice);
                labelTheseusDice.setText(calculateMoveScore(1, oldPos1, board.getN(), rd - 1));
        		labelTheseusDice.setBounds(45, 515, 135, 15);
        		labelMinotaurDice.setBounds(0, 0, 0, 0);
        		frame.remove(labelMinotaurDice);
        		labelMinotaurDice.setText(calculateMoveScore(2, oldPos2, board.getN(), rd - 1));
        		labelMinotaurDice.setBounds(840, 515, 135, 15);
        		//remove and update only label of Theseus total score
        		labelTheseusScore.setBounds(0, 0, 0, 0);
        		frame.remove(labelTheseusScore);
        		labelTheseusScore.setText("Total Score: " + player1.getScore());
        		labelTheseusScore.setBounds(45, 530, 135, 15);
        		
                //add new contents on frame
                frame.add(panelBoard);
                frame.add(labelRound);
                frame.add(labelTheseusDice);
                frame.add(labelMinotaurDice);
                frame.add(labelTheseusScore);
                
                //if the game ends we showcase the verdict of the game and the statistics for each player
                if(endOfGame) {
                	JOptionPane.showMessageDialog(null, verdict, "Verdict of the Game", JOptionPane.PLAIN_MESSAGE);
                	if(cb1 != 0) {
        				JTextArea textArea = new JTextArea(player1.statistics(true));
        				JScrollPane scrollPane = new JScrollPane(textArea);  
        				scrollPane.setPreferredSize(new Dimension(500, 500));
        				JOptionPane.showMessageDialog(null, scrollPane, "Statistics for Theseus", JOptionPane.PLAIN_MESSAGE);
        			}
        			if(cb2 != 0) {
        				JTextArea textArea = new JTextArea(player2.statistics(true));
        				JScrollPane scrollPane = new JScrollPane(textArea); 
        				scrollPane.setPreferredSize(new Dimension(500, 500));
        				JOptionPane.showMessageDialog(null, scrollPane, "Statistics for Minotaur", JOptionPane.PLAIN_MESSAGE);
        			}
                }
			}
		});
		button1.setEnabled(false); //we initially set this button as disabled
		
		
		
		//we set button2
		button2.setBounds(348, 700, 140, 30);
		button2.setText("Generate Board");
		button2.setFocusable(false);
		button2.setForeground(Color.blue);
		
		//if we press "Generate Board"
		button2.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//we set endOfGame as false(in case we want to play again)
				endOfGame = false;
				//button 3("Quit") is disabled only in the beginning when we haven't pressed "Generate Board"
				if(!button3.isEnabled()) {
					printer(board.getStringRepresentation(0, (board.getN() * board.getN())/2));
					panelBoard.setVisible(true);	//we set the board as visible in the GUI
					//we enable the other buttons
					button1.setEnabled(true);		
					button3.setEnabled(true);
					theseusCombo.setEnabled(true);
					minotaurCombo.setEnabled(true);
				}
				else {
					//if the "Quit" button is enabled and we press "Generate Board" again, it means that we want to play again(with a new board)
					board.createBoard();	//we create our new board
					printer(board.getStringRepresentation(0, (board.getN() * board.getN())/2));
					
					frame.setVisible(true);		
					
	                frame.remove(panelBoard);	//we remove our old board from the GUI
	                panelBoard = new MyPanel(board, 0, (board.getN() * board.getN())/2);	//we create our new board GUI
	                panelBoard.setBounds(240, 35, 506, 506);	//we set the bounds of our new board
	               
	                //we update the round labels
	                frame.remove(labelRound);
	                labelRound.setText("Round: " + round);
	                labelRound.setBounds(25, 25, 70, 20);
	                
	                //we update the Theseus and Minotaur labels
	                labelTheseusDice.setBounds(0, 0, 0, 0);
	                frame.remove(labelTheseusDice);
	                labelTheseusDice.setText("Move Score: 0");
	        		labelTheseusDice.setBounds(45, 515, 135, 15);
	        		labelMinotaurDice.setBounds(0, 0, 0, 0);
	        		frame.remove(labelMinotaurDice);
	        		labelMinotaurDice.setText("Move Score: 0");
	        		labelMinotaurDice.setBounds(840, 515, 135, 15);
	        		
	        		labelTheseusScore.setBounds(0, 0, 0, 0);
	        		frame.remove(labelTheseusScore);
	        		labelTheseusScore.setText("Total Score: 0");
	        		labelTheseusScore.setBounds(45, 530, 135, 15);
	                
	        		//we add everything to the frame
	                frame.add(panelBoard);
	                frame.add(labelRound);
	                frame.add(labelTheseusDice);
	                frame.add(labelMinotaurDice);
	                frame.add(labelTheseusScore);
	                
	                //we enable the "Play" button and the ComboBoxes
	                button1.setEnabled(true);
					theseusCombo.setEnabled(true);
					minotaurCombo.setEnabled(true);
				}
				button2.setEnabled(false);	//we disable the "Generate" button after we're done
			}
		});
		
		
		//we set button3
		button3.setBounds(568, 700, 70, 30);
		button3.setText("Quit");
		button3.setFocusable(false);
		button3.setForeground(Color.red);
		
		//if we press "Quit"
		button3.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button3.setEnabled(false);
		
		//we create our board
        panelBoard = new MyPanel(board, 0, board.getN() * board.getN()/2);
        panelBoard.setBounds(240, 35, 506, 506);
        panelBoard.setVisible(false);

        frame.setVisible(true);

        //we add everything to the frame
        frame.add(panelBoard);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);

        frame.add(labelRound);

        frame.add(labelTheseus);
        frame.add(labelTheseusDice);
        frame.add(labelTheseusScore);

        frame.add(labelMinotaur);
        frame.add(labelMinotaurDice);
        frame.add(labelMinotaurScore);

        frame.add(label);  
	}
}