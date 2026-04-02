import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

//this class runs the Game
public class Game {

	private static JButton button1, button2, button3; // we have three buttons: button1 for Play, button2 for Generate
														// Board, button3 for Quit
	private static JButton toggleImageButton; // button to toggle between images and text

	private static Player player1; // a static variable for Theseus /we declare the players as type Player
	private static Player player2; // a static variable for Minotaur and they might be Heuristic or MinMax later
	private static int cb1 = 0; // holds the index of combo box of player type of Theseus
	private static int cb2 = 0; // holds the index of combo box of player type of Minotaur

	private static JPanel panelBoard; // this panel simulates the board of the game
	private static Board board; // the game board

	// New variables for enhanced GUI
	private static boolean autoPlay = false; // determines if game should auto-play
	private static Timer autoPlayTimer; // timer for automatic play
	private static JButton infoButton1, infoButton2; // info buttons for player types
	private static JComboBox<String> theseusCombo, minotaurCombo; // ComboBoxes for player selection

	private static int n = 0; // counts the moves. if n = 100 the game is a tie
	private static int turn; // if turn == 0 then Theseus plays first, else Minotaur plays first

	private static int currentPos1; // currentPos of Teseus
	private static int currentPos2; // currentPos of Minotaur

	private static boolean endOfGame = false; // true if the game ends, tie or with a winner

	private static String verdict = ""; // a string that holds the result of the game
										// "Tie!" , "Theseus Won!" or "Minotaur Won!"

	private static int round = 0; // the round of the game

	// the default constructor of the class
	public Game() {

	}

	// this assistant function prints the board of the game
	public static void printer(String[][] board) {
		int size = board[0].length; // size = N
		for (int i = 2 * size; i >= 0; i--) { // i-- because we want to print it from the top to the bottom
			for (int j = 0; j < size; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	// this assistant function returns a string representation of the move score of
	// the player that we use to update the move score label
	public static String calculateMoveScore(int id, int oldPos, int size, int round) { // id the playerId, oldPos the
																						// position of player before the
																						// move, size the size N of the
																						// board, round the round of the
																						// game

		int dice = 0; // the dice of the move, initialized 0 and never changed if we have random
						// player
		double score = 0; // the move score

		if (id == 1) { // if player is Theseus
			if (cb1 == 1 || cb1 == 2) {
				dice = player1.path.get(round)[0]; // if player is Heuristic or MinMax we use the path
				score = player1.getMoveScore(); // we get score using the getter of the class
			} else {
				int distPreviousFromNew = currentPos1 - oldPos;
				if (distPreviousFromNew == 1)
					dice = 3;
				else if (distPreviousFromNew == size)
					dice = 1;
				else if (distPreviousFromNew == -1)
					dice = 7;
				else if (distPreviousFromNew == -size)
					dice = 5;
			}
		}

		else {
			if (cb2 == 1 || cb2 == 2) {
				dice = player2.path.get(round)[0];
				score = player2.getMoveScore();
			} else {
				int distPreviousFromNew = currentPos2 - oldPos;
				if (distPreviousFromNew == 1)
					dice = 3;
				else if (distPreviousFromNew == size)
					dice = 1;
				else if (distPreviousFromNew == -1)
					dice = 7;
				else if (distPreviousFromNew == -size)
					dice = 5;
			}
		}

		DecimalFormat df = new DecimalFormat("#.##");

		String d = String.valueOf(dice);
		String s = df.format(score); // we keep 2 decimal places

		String str = "Move Score: " + d + " -> " + s; // we get string representation of move score
		return str; // we return the string representation

	}

	// method to show player type information
	public static void showPlayerInfo(int playerType, String playerName) {
		String title = "Player Information - " + playerName;
		String message = "";

		switch (playerType) {
			case 0:
				message = "Random Player:\n\n" +
						"• Makes completely random valid moves\n" +
						"• No strategic planning or evaluation\n" +
						"• Suitable for baseline testing\n" +
						"• Unpredictable movement patterns\n" +
						"• Good for beginners to understand game mechanics";
				break;
			case 1:
				message = "Heuristic Player:\n\n" +
						"• Uses sophisticated evaluation functions\n" +
						"• Considers proximity to supplies and opponent\n" +
						"• Evaluates moves up to 3 tiles ahead\n" +
						"• Balances offensive and defensive strategies\n" +
						"• Moderate difficulty level\n\n" +
						"Evaluation Formula:\n" +
						"• Theseus: nearSupplies×0.46 - opponentDistance×0.54\n" +
						"• Minotaur: nearSupplies×0.46 + opponentDistance×0.54";
				break;
			case 2:
				message = "MinMax Player:\n\n" +
						"• Implements MinMax algorithm with 2-level lookahead\n" +
						"• Creates game trees to evaluate optimal moves\n" +
						"• Considers opponent's best responses\n" +
						"• Most strategic and challenging AI\n" +
						"• Highest difficulty level\n\n" +
						"Strategy:\n" +
						"• Maximizes own advantage\n" +
						"• Minimizes opponent's advantage\n" +
						"• Plans multiple moves ahead";
				break;
		}

		JTextArea textArea = new JTextArea(message);
		textArea.setFont(new Font("Arial", Font.PLAIN, 12));
		textArea.setEditable(false);
		textArea.setBackground(new Color(245, 245, 245));
		textArea.setMargin(new Insets(10, 10, 10, 10));

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(400, 300));

		JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
	}

	// method to show information about all player types
	public static void showAllPlayerInfo(String contextPlayer) {
		String title = "Player Types Information - " + contextPlayer + " Selection";
		String message = "Available Player Types:\n\n" +
				"🎲 RANDOM PLAYER:\n" +
				"• Makes completely random valid moves\n" +
				"• No strategic planning or evaluation\n" +
				"• Suitable for baseline testing\n" +
				"• Unpredictable movement patterns\n" +
				"• Good for beginners to understand game mechanics\n\n" +

				"🧠 HEURISTIC PLAYER:\n" +
				"• Uses sophisticated evaluation functions\n" +
				"• Considers proximity to supplies and opponent\n" +
				"• Evaluates moves up to 3 tiles ahead\n" +
				"• Balances offensive and defensive strategies\n" +
				"• Moderate difficulty level\n" +
				"• Evaluation Formula:\n" +
				"  - Theseus: nearSupplies×0.46 - opponentDistance×0.54\n" +
				"  - Minotaur: nearSupplies×0.46 + opponentDistance×0.54\n\n" +

				"🎯 MINMAX PLAYER:\n" +
				"• Implements MinMax algorithm with 2-level lookahead\n" +
				"• Creates game trees to evaluate optimal moves\n" +
				"• Considers opponent's best responses\n" +
				"• Most strategic and challenging AI\n" +
				"• Highest difficulty level\n" +
				"• Strategy: Maximizes own advantage while minimizing opponent's\n" +
				"• Plans multiple moves ahead using tree search\n\n" +

				"💡 TIPS:\n" +
				"• Start with Random vs Random to learn the game\n" +
				"• Try Heuristic for balanced strategic gameplay\n" +
				"• Use MinMax for maximum challenge\n" +
				"• Mix different types for varied experiences";

		JTextArea textArea = new JTextArea(message);
		textArea.setFont(new Font("Arial", Font.PLAIN, 12));
		textArea.setEditable(false);
		textArea.setBackground(new Color(245, 245, 245));
		textArea.setMargin(new Insets(15, 15, 15, 15));

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 400));

		JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
	}

	// helper method to update labels within panels
	public static void updateLabelsInPanel(JPanel panel, int oldPos1, int oldPos2, int rd) {
		Component[] components = panel.getComponents();
		for (Component comp : components) {
			if (comp instanceof JLabel) {
				JLabel label = (JLabel) comp;
				String text = label.getText();
				if (text.contains("Move Score:")) {
					// Determine if this is Theseus or Minotaur panel based on panel position
					if (panel.getBounds().x < 500) {
						// Theseus panel (left side)
						label.setText(calculateMoveScore(1, oldPos1, board.getN(), rd));
					} else {
						// Minotaur panel (right side)
						label.setText(calculateMoveScore(2, oldPos2, board.getN(), rd));
					}
				} else if (text.contains("Total Score:")) {
					// Only Theseus collects supplies, Minotaur score is always 0
					if (panel.getBounds().x < 500) {
						// Theseus panel (left side)
						label.setText("Total Score: " + player1.getScore());
					} else {
						// Minotaur panel (right side) - always 0
						label.setText("Total Score: 0");
					}
				}
			}
		}
	}

	// helper method to reset labels within panels to default values
	public static void resetLabelsInPanel(JPanel panel) {
		Component[] components = panel.getComponents();
		for (Component comp : components) {
			if (comp instanceof JLabel) {
				JLabel label = (JLabel) comp;
				String text = label.getText();
				if (text.contains("Move Score:")) {
					label.setText("Move Score: 0");
				} else if (text.contains("Total Score:")) {
					label.setText("Total Score: 0");
				}
			}
		}
	}

	// method to execute a single game move (extracted from original button1 action)
	public static void executeGameMove() {
		round++; // we update the round of the game

		// Disable ComboBoxes and info buttons once the game starts (first move)
		if (round == 1) {
			theseusCombo.setEnabled(false);
			minotaurCombo.setEnabled(false);
			infoButton1.setEnabled(false);
			infoButton2.setEnabled(false);
		}

		int rd = round - 1; // we need this for the case of the end of the game because round is set to 0
							// after the call of mainBody()

		int oldPos1 = currentPos1; // we store the positions before the move of each player
		int oldPos2 = currentPos2;

		Board boardTest = new Board(mainBody(board)); // run mainBody and store the new board

		// Get the frame from one of the components
		Container parent = panelBoard.getParent();
		while (parent != null && !(parent instanceof JFrame)) {
			parent = parent.getParent();
		}
		JFrame frame = (JFrame) parent;

		// Update the existing panel instead of removing and re-adding
		if (panelBoard instanceof MyPanel) {
			MyPanel myPanel = (MyPanel) panelBoard;
			// Update the panel's data
			myPanel.updateBoard(boardTest, currentPos1, currentPos2);
			myPanel.repaint();
		}

		// Update labels without removing them
		Component[] components = frame.getContentPane().getComponents();
		for (Component comp : components) {
			if (comp instanceof JLabel) {
				JLabel label = (JLabel) comp;
				if (label.getText().contains("Round:")) {
					label.setText("Round: " + (rd + 1));
				}
			} else if (comp instanceof JPanel) {
				// Look for labels within info panels
				updateLabelsInPanel((JPanel) comp, oldPos1, oldPos2, rd);
			}
		}

		// Just repaint the frame, don't re-add components
		frame.repaint();

		// if the game ends we showcase the verdict of the game and the statistics for
		// each player
		if (endOfGame) {
			if (autoPlayTimer != null && autoPlayTimer.isRunning()) {
				autoPlayTimer.stop();
				button1.setText(autoPlay ? "▶ Auto Play" : "▶ Play");
				button1.setBackground(new Color(39, 174, 96));
			}

			JOptionPane.showMessageDialog(null, verdict, "Verdict of the Game", JOptionPane.PLAIN_MESSAGE);
			if (cb1 != 0) {
				JTextArea textArea = new JTextArea(player1.statistics(true));
				JScrollPane scrollPane = new JScrollPane(textArea);
				scrollPane.setPreferredSize(new Dimension(500, 500));
				JOptionPane.showMessageDialog(null, scrollPane, "Statistics for Theseus", JOptionPane.PLAIN_MESSAGE);
			}
			if (cb2 != 0) {
				JTextArea textArea = new JTextArea(player2.statistics(true));
				JScrollPane scrollPane = new JScrollPane(textArea);
				scrollPane.setPreferredSize(new Dimension(500, 500));
				JOptionPane.showMessageDialog(null, scrollPane, "Statistics for Minotaur", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}

	// this method decides if Theseus or Minotaur plays first randomly
	public static int setTurns() {
		if ((int) (Math.random() * 2) == 0)
			return 0;
		else
			return 1;
	}

	// this method simulates each round of the game
	public static Board mainBody(Board board) { // takes the board of the game as argument
		// initializes the players and places them in their initial tiles
		if (round == 1) {
			// we close the labyrinth
			board.getTiles()[0].setDown(true);

			// Round 1 begins
			System.out.println("ROUND 1\n");

			if (cb1 == 1) {
				player1 = new HeuristicPlayer(1, "Theseus", board, 0, 0, 0);
			} else if (cb1 == 2) {
				player1 = new MinMaxPlayer(1, "Theseus", board, 0, 0, 0);
			} else {
				player1 = new Player(1, "Theseus", board, 0, 0, 0);
			}
			if (cb2 == 1) {
				player2 = new HeuristicPlayer(2, "Minotaur", board, 0, board.getN() / 2, board.getN() / 2);
			} else if (cb2 == 2) {
				player2 = new MinMaxPlayer(2, "Minotaur", board, 0, board.getN() / 2, board.getN() / 2);
			} else {
				player2 = new Player(2, "Minotaur", board, 0, board.getN() / 2, board.getN() / 2);
			}

			turn = setTurns(); // if turn == 0 Theseus plays first, else if turn == 1 Minotaur plays first
			if (turn == 0) {
				currentPos1 = player1.getNextMove(0, board.getN() * board.getN() / 2); // the first move of Theseus.
																						// currentPos1 stores the
																						// position of Theseus after he
																						// moves.
				currentPos2 = player2.getNextMove(board.getN() * board.getN() / 2, currentPos1); // the first move of
																									// Minotaur.
																									// currentPos2
																									// stores the
																									// position of
																									// Minotaur after he
																									// moves.
			} else {
				currentPos2 = player2.getNextMove(board.getN() * board.getN() / 2, 0); // the first move of Minotaur.
																						// currentPos2 stores the
																						// position of Minotaur after he
																						// moves.
				currentPos1 = player1.getNextMove(0, currentPos2); // the first move of Theseus. currentPos1 stores the
																	// position of Theseus after he moves.
			}
			n++; // we update n

			// prints the statistics of the first move for the players that are not random
			player1.statistics(false);
			player2.statistics(false);
			// prints the board after the first move of each player
			printer(board.getStringRepresentation(currentPos1, currentPos2));
		}

		if (round != 1) { // in every following round
			System.out.println("ROUND " + round + "\n");

			if (turn == 0) {
				// Theseus plays
				currentPos1 = player1.getNextMove(currentPos1, currentPos2);

				// if he has gathered all the supplies, Theseus wins
				if (player1.getScore() == board.getS() && endOfGame == false) {
					printer(board.getStringRepresentation(currentPos1, currentPos2));
					System.out.println("Theseus Won!\n");
					verdict = "Theseus Won!";
					endOfGame = true;
				}
				// if Theseus meets Minotaur, Minotaur wins
				else if (currentPos1 == currentPos2 && endOfGame == false) {
					printer(board.getStringRepresentation(currentPos1, currentPos2));
					System.out.println("Minotaur Won!\n");
					verdict = "Minotaur Won!";
					endOfGame = true;
				}

				// Minotaur plays
				if (!endOfGame)
					currentPos2 = player2.getNextMove(currentPos2, currentPos1);

				// if Minotaur meets Theseus, Minotaur wins
				if (currentPos1 == currentPos2 && endOfGame == false) {
					printer(board.getStringRepresentation(currentPos1, currentPos2));
					System.out.println("Minotaur Won!\n");
					verdict = "Minotaur Won!";
					endOfGame = true;
				}
			}

			else {
				// Minotaur plays
				currentPos2 = player2.getNextMove(currentPos2, currentPos1);

				// if Minotaur meets Theseus, Minotaur wins
				if (currentPos1 == currentPos2 && endOfGame == false) {
					printer(board.getStringRepresentation(currentPos1, currentPos2));
					System.out.println("Minotaur Won!\n");
					verdict = "Minotaur Won!";
					endOfGame = true;
				}

				// Theseus plays
				if (!endOfGame)
					currentPos1 = player1.getNextMove(currentPos1, currentPos2);

				// if he has gathered all the supplies, Theseus wins
				if (player1.getScore() == board.getS() && endOfGame == false) {
					printer(board.getStringRepresentation(currentPos1, currentPos2));
					System.out.println("Theseus Won!\n");
					verdict = "Theseus Won!";
					endOfGame = true;
				}

				// if Theseus meets Minotaur, Minotaur wins
				else if (currentPos1 == currentPos2 && endOfGame == false) {
					printer(board.getStringRepresentation(currentPos1, currentPos2));
					System.out.println("Minotaur Won!\n");
					verdict = "Minotaur Won!";
					endOfGame = true;
				}
			}
			// prints the statistics of non random players and the board in each round
			player1.statistics(false);
			player2.statistics(false);
			printer(board.getStringRepresentation(currentPos1, currentPos2));
			n++;
		}

		// if it's a tie
		if (n == 100) {
			endOfGame = true;
			System.out.println("Tie!\n");
			verdict = "Tie!";
		}

		if (endOfGame) {
			// in case we want to play again we reset these
			n = 0;
			round = 0;
			button1.setEnabled(false);
			button2.setEnabled(true);
			theseusCombo.setEnabled(true);
			minotaurCombo.setEnabled(true);
			// Stop auto-play if it's running
			if (autoPlayTimer != null && autoPlayTimer.isRunning()) {
				autoPlayTimer.stop();
				button1.setText("Auto Play");
				button1.setBackground(new Color(39, 174, 96));
			}
		}
		return board; // mainBody returns the updated board
	}

	public static void main(String[] args) {

		// a window to ask if we want to play or not
		int answer = JOptionPane.showConfirmDialog(null, "Do you want to play?", "Welcome!", JOptionPane.YES_NO_OPTION);
		if (answer == 1)
			System.exit(0);

		// Ask for game mode preference
		String[] options = { "Manual Play (Click for each move)", "Automated Play (Watch the game)" };
		int gameMode = JOptionPane.showOptionDialog(null,
				"Choose your preferred game mode:",
				"Game Mode Selection",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);

		if (gameMode == 1)
			autoPlay = true;

		// the frame of the game
		JFrame frame = new JFrame();
		frame.setTitle("Theseus & Minotaur - Labyrinth Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1100, 850);
		frame.setResizable(false); // we can't change the size of the frame
		frame.getContentPane().setBackground(new Color(245, 245, 250));

		// the border of the frame with enhanced styling
		Border outerBorder = BorderFactory.createRaisedBevelBorder();
		Border innerBorder = BorderFactory.createLoweredBevelBorder();
		Border compoundBorder = BorderFactory.createCompoundBorder(outerBorder, innerBorder);
		JLabel frameLabel = new JLabel();
		frameLabel.setBorder(compoundBorder);

		// we create our board
		board = new Board(15, 4, (15 * 15 * 3 + 1) / 2);
		board.createBoard();

		// we create the ComboBoxes for each player
		String[] typeOfPlayer = { "Random Player", "Heuristic Player", "MinMax Player" };
		theseusCombo = new JComboBox<>(typeOfPlayer);
		minotaurCombo = new JComboBox<>(typeOfPlayer);

		// Enhanced styling for ComboBoxes
		theseusCombo.setFont(new Font("Arial", Font.BOLD, 12));
		theseusCombo.setBackground(new Color(220, 240, 255));
		theseusCombo.setBorder(BorderFactory.createRaisedBevelBorder());
		theseusCombo.setForeground(new Color(0, 100, 200));

		minotaurCombo.setFont(new Font("Arial", Font.BOLD, 12));
		minotaurCombo.setBackground(new Color(255, 220, 220));
		minotaurCombo.setBorder(BorderFactory.createRaisedBevelBorder());
		minotaurCombo.setForeground(new Color(200, 50, 50));

		// we store the choice of player (Random, Heuristic, MinMax)
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

		// Create info buttons first
		infoButton1 = new JButton("i");
		infoButton1.setFont(new Font("Arial", Font.BOLD, 14));
		infoButton1.setPreferredSize(new Dimension(25, 25));
		infoButton1.setBackground(new Color(180, 220, 255));
		infoButton1.setForeground(new Color(0, 100, 200));
		infoButton1.setBorder(BorderFactory.createRaisedBevelBorder());
		infoButton1.setFocusable(false);
		infoButton1.setToolTipText("Click for player type information");

		infoButton2 = new JButton("i");
		infoButton2.setFont(new Font("Arial", Font.BOLD, 14));
		infoButton2.setPreferredSize(new Dimension(25, 25));
		infoButton2.setBackground(new Color(255, 180, 180));
		infoButton2.setForeground(new Color(200, 50, 50));
		infoButton2.setBorder(BorderFactory.createRaisedBevelBorder());
		infoButton2.setFocusable(false);
		infoButton2.setToolTipText("Click for player type information");

		infoButton1.addActionListener(e -> showAllPlayerInfo("Theseus"));
		infoButton2.addActionListener(e -> showAllPlayerInfo("Minotaur"));

		// we disable the ComboBoxes after we choose the players we want to play with
		theseusCombo.setEnabled(false);
		minotaurCombo.setEnabled(false);
		infoButton1.setEnabled(false);
		infoButton2.setEnabled(false);

		// we add the ComboBoxes to their panel with enhanced positioning
		JPanel panel1 = new JPanel();
		panel1.setBounds(30, 650, 200, 80);
		panel1.setLayout(new BorderLayout());
		panel1.add(theseusCombo, BorderLayout.CENTER);
		panel1.add(infoButton1, BorderLayout.EAST);
		panel1.setBackground(new Color(235, 245, 255));
		panel1.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createTitledBorder(
						BorderFactory.createLoweredBevelBorder(),
						"Theseus Strategy",
						TitledBorder.CENTER,
						TitledBorder.TOP,
						new Font("Arial", Font.BOLD, 12),
						new Color(0, 80, 160))));

		JPanel panel2 = new JPanel();
		panel2.setBounds(870, 650, 200, 80);
		panel2.setLayout(new BorderLayout());
		panel2.add(minotaurCombo, BorderLayout.CENTER);
		panel2.add(infoButton2, BorderLayout.EAST);
		panel2.setBackground(new Color(255, 240, 240));
		panel2.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createTitledBorder(
						BorderFactory.createLoweredBevelBorder(),
						"Minotaur Strategy",
						TitledBorder.CENTER,
						TitledBorder.TOP,
						new Font("Arial", Font.BOLD, 12),
						new Color(160, 40, 40))));

		// we create the Round label with enhanced styling
		JLabel labelRound = new JLabel();
		labelRound.setBounds(40, 30, 150, 50);
		labelRound.setText("Round: " + round);
		labelRound.setFont(new Font("Arial", Font.BOLD, 18));
		labelRound.setForeground(new Color(40, 40, 40));
		labelRound.setOpaque(true);
		labelRound.setBackground(new Color(255, 255, 230));
		labelRound.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createEmptyBorder(8, 15, 8, 15)));
		labelRound.setHorizontalAlignment(SwingConstants.CENTER);

		// we create the Theseus info panel with enhanced styling
		JPanel theseusInfoPanel = new JPanel();
		theseusInfoPanel.setBounds(30, 540, 200, 100);
		theseusInfoPanel.setLayout(new BoxLayout(theseusInfoPanel, BoxLayout.Y_AXIS));
		theseusInfoPanel.setBackground(new Color(230, 240, 255));
		theseusInfoPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createTitledBorder(
						BorderFactory.createLoweredBevelBorder(),
						"Theseus Status",
						TitledBorder.CENTER,
						TitledBorder.TOP,
						new Font("Arial", Font.BOLD, 13),
						new Color(0, 80, 160))));

		JLabel labelTheseusDice = new JLabel();
		labelTheseusDice.setText("Move Score: 0");
		labelTheseusDice.setFont(new Font("Arial", Font.PLAIN, 12));
		labelTheseusDice.setForeground(new Color(50, 50, 150));
		labelTheseusDice.setOpaque(true);
		labelTheseusDice.setBackground(new Color(245, 250, 255));
		labelTheseusDice.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLoweredBevelBorder(),
				BorderFactory.createEmptyBorder(3, 8, 3, 8)));
		labelTheseusDice.setHorizontalAlignment(SwingConstants.CENTER);
		labelTheseusDice.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel labelTheseusScore = new JLabel();
		labelTheseusScore.setText("Total Score: 0");
		labelTheseusScore.setFont(new Font("Arial", Font.BOLD, 12));
		labelTheseusScore.setForeground(new Color(0, 120, 0));
		labelTheseusScore.setOpaque(true);
		labelTheseusScore.setBackground(new Color(240, 255, 240));
		labelTheseusScore.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLoweredBevelBorder(),
				BorderFactory.createEmptyBorder(3, 8, 3, 8)));
		labelTheseusScore.setHorizontalAlignment(SwingConstants.CENTER);
		labelTheseusScore.setAlignmentX(Component.CENTER_ALIGNMENT);

		theseusInfoPanel.add(Box.createVerticalStrut(5));
		theseusInfoPanel.add(labelTheseusDice);
		theseusInfoPanel.add(Box.createVerticalStrut(5));
		theseusInfoPanel.add(labelTheseusScore);
		theseusInfoPanel.add(Box.createVerticalStrut(5));

		// we create the Minotaur info panel with enhanced styling
		JPanel minotaurInfoPanel = new JPanel();
		minotaurInfoPanel.setBounds(870, 540, 200, 100);
		minotaurInfoPanel.setLayout(new BoxLayout(minotaurInfoPanel, BoxLayout.Y_AXIS));
		minotaurInfoPanel.setBackground(new Color(255, 235, 235));
		minotaurInfoPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createTitledBorder(
						BorderFactory.createLoweredBevelBorder(),
						"Minotaur Status",
						TitledBorder.CENTER,
						TitledBorder.TOP,
						new Font("Arial", Font.BOLD, 13),
						new Color(160, 40, 40))));

		JLabel labelMinotaurDice = new JLabel();
		labelMinotaurDice.setText("Move Score: 0");
		labelMinotaurDice.setFont(new Font("Arial", Font.PLAIN, 12));
		labelMinotaurDice.setForeground(new Color(150, 50, 50));
		labelMinotaurDice.setOpaque(true);
		labelMinotaurDice.setBackground(new Color(255, 245, 245));
		labelMinotaurDice.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLoweredBevelBorder(),
				BorderFactory.createEmptyBorder(3, 8, 3, 8)));
		labelMinotaurDice.setHorizontalAlignment(SwingConstants.CENTER);
		labelMinotaurDice.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel labelMinotaurScore = new JLabel();
		labelMinotaurScore.setText("Total Score: 0");
		labelMinotaurScore.setFont(new Font("Arial", Font.BOLD, 12));
		labelMinotaurScore.setForeground(new Color(100, 100, 100));
		labelMinotaurScore.setOpaque(true);
		labelMinotaurScore.setBackground(new Color(250, 250, 250));
		labelMinotaurScore.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLoweredBevelBorder(),
				BorderFactory.createEmptyBorder(3, 8, 3, 8)));
		labelMinotaurScore.setHorizontalAlignment(SwingConstants.CENTER);
		labelMinotaurScore.setAlignmentX(Component.CENTER_ALIGNMENT);

		minotaurInfoPanel.add(Box.createVerticalStrut(5));
		minotaurInfoPanel.add(labelMinotaurDice);
		minotaurInfoPanel.add(Box.createVerticalStrut(5));
		minotaurInfoPanel.add(labelMinotaurScore);
		minotaurInfoPanel.add(Box.createVerticalStrut(5));

		// we create the buttons with enhanced styling and better positioning
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();

		// we set button1 with enhanced styling
		button1.setBounds(480, 750, 120, 45); // we place Play button to its position
		button1.setText(autoPlay ? "Auto Play" : "Play");
		button1.setFocusable(false);
		button1.setFont(new Font("Arial", Font.BOLD, 14));
		button1.setBackground(new Color(39, 174, 96));
		button1.setForeground(Color.WHITE);
		button1.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));

		// if we press "Play"
		button1.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (autoPlay && autoPlayTimer != null && autoPlayTimer.isRunning()) {
					// Stop auto play
					autoPlayTimer.stop();
					button1.setText("Auto Play");
					button1.setBackground(new Color(39, 174, 96));
					button2.setEnabled(true); // Re-enable Generate Board when stopping auto-play
					return;
				}

				if (autoPlay && !endOfGame) {
					// Start auto play
					button1.setText("Stop Auto");
					button1.setBackground(new Color(230, 126, 34));
					button2.setEnabled(false); // Disable Generate Board during auto-play
					autoPlayTimer = new Timer(800, new ActionListener() {
						public void actionPerformed(ActionEvent timerEvent) {
							if (endOfGame) {
								autoPlayTimer.stop();
								button1.setText("Auto Play");
								button1.setBackground(new Color(39, 174, 96));
								button2.setEnabled(true); // Re-enable Generate Board when game ends
								return;
							}
							executeGameMove();
						}
					});
					autoPlayTimer.start();
					executeGameMove(); // Execute first move immediately
				} else {
					// Manual play - execute one move
					executeGameMove();
				}
			}
		});
		button1.setEnabled(false); // we initially set this button as disabled

		// we set button2 with enhanced styling
		button2.setBounds(330, 750, 140, 45);
		button2.setText("Generate Board");
		button2.setFocusable(false);
		button2.setFont(new Font("Arial", Font.BOLD, 14));
		button2.setBackground(new Color(41, 128, 185));
		button2.setForeground(Color.WHITE);
		button2.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));

		// if we press "Generate Board"
		button2.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// we set endOfGame as false(in case we want to play again)
				endOfGame = false;

				// Stop any running auto play
				if (autoPlayTimer != null && autoPlayTimer.isRunning()) {
					autoPlayTimer.stop();
					button1.setText(autoPlay ? "Auto Play" : "Play");
					button1.setBackground(new Color(46, 204, 113));
				}

				// Get the frame reference
				Container parent = panelBoard.getParent();
				while (parent != null && !(parent instanceof JFrame)) {
					parent = parent.getParent();
				}
				JFrame frame = (JFrame) parent;

				// button 3("Quit") is disabled only in the beginning when we haven't pressed
				// "Generate Board"
				if (!button3.isEnabled()) {
					printer(board.getStringRepresentation(0, (board.getN() * board.getN()) / 2));
					panelBoard.setVisible(true); // we set the board as visible in the GUI
					// we enable the other buttons
					button1.setEnabled(true);
					button3.setEnabled(true);
					theseusCombo.setEnabled(true);
					minotaurCombo.setEnabled(true);
					infoButton1.setEnabled(true);
					infoButton2.setEnabled(true);
				} else {
					// if the "Quit" button is enabled and we press "Generate Board" again, it means
					// that we want to play again(with a new board)
					board.createBoard(); // we create our new board
					printer(board.getStringRepresentation(0, (board.getN() * board.getN()) / 2));

					frame.setVisible(true);

					// Update the existing panel instead of removing and re-adding
					if (panelBoard instanceof MyPanel) {
						MyPanel myPanel = (MyPanel) panelBoard;
						myPanel.updateBoard(board, 0, (board.getN() * board.getN()) / 2);
						myPanel.repaint();
					}

					// Update labels using component search
					Component[] components = frame.getContentPane().getComponents();
					for (Component comp : components) {
						if (comp instanceof JLabel) {
							JLabel label = (JLabel) comp;
							if (label.getText().contains("Round:")) {
								label.setText("Round: " + round);
							}
						} else if (comp instanceof JPanel) {
							// Reset labels in info panels
							resetLabelsInPanel((JPanel) comp);
						}
					}

					// Just repaint the frame
					frame.repaint();

					// we enable the "Play" button and the ComboBoxes
					button1.setEnabled(true);
					theseusCombo.setEnabled(true);
					minotaurCombo.setEnabled(true);
					infoButton1.setEnabled(true);
					infoButton2.setEnabled(true);
				}
				button2.setEnabled(false); // we disable the "Generate" button after we're done
			}
		});

		// we set button3 with enhanced styling
		button3.setBounds(610, 750, 90, 45);
		button3.setText("Quit");
		button3.setFocusable(false);
		button3.setFont(new Font("Arial", Font.BOLD, 14));
		button3.setBackground(new Color(192, 57, 43));
		button3.setForeground(Color.WHITE);
		button3.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));

		// we set toggleImageButton with enhanced styling
		toggleImageButton = new JButton();
		toggleImageButton.setBounds(710, 750, 100, 45);
		toggleImageButton.setText("Images");
		toggleImageButton.setFocusable(false);
		toggleImageButton.setFont(new Font("Arial", Font.BOLD, 12));
		toggleImageButton.setBackground(new Color(52, 152, 219));
		toggleImageButton.setForeground(Color.WHITE);
		toggleImageButton.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		// Toggle between images and text
		toggleImageButton.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (panelBoard instanceof MyPanel) {
					MyPanel myPanel = (MyPanel) panelBoard;
					myPanel.toggleImageMode();

					// Update button text based on current mode
					if (myPanel.isUsingImages()) {
						toggleImageButton.setText("Text");
						toggleImageButton.setBackground(new Color(155, 89, 182));
					} else {
						toggleImageButton.setText("Images");
						toggleImageButton.setBackground(new Color(52, 152, 219));
					}
				}
			}
		});

		// if we press "Quit"
		button3.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button3.setEnabled(false);

		// we create our board with enhanced positioning
		panelBoard = new MyPanel(board, 0, board.getN() * board.getN() / 2);
		panelBoard.setBounds(280, 90, 520, 520);
		panelBoard.setVisible(false);
		panelBoard.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder()));

		frame.setVisible(true);

		// we add everything to the frame with proper organization
		frame.add(panelBoard);
		frame.add(panel1);
		frame.add(panel2);
		frame.add(button1);
		frame.add(button2);
		frame.add(button3);
		frame.add(toggleImageButton);
		frame.add(labelRound);
		frame.add(theseusInfoPanel);
		frame.add(minotaurInfoPanel);
		frame.add(frameLabel);
	}
}
