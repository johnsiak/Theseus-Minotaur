import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//in this class we draw the board of the game as a GUI
public class MyPanel extends JPanel{
	
	private Board board;	//the board
	private int theseusTile, minotaurTile;	//the players' positions
	private static final long serialVersionUID = 1L;
	
	// Image variables for game elements
	private BufferedImage theseusImage, minotaurImage, supplyImage;
	private boolean useImages = false; // Flag to toggle between images and text

	//the constructor of the class
	MyPanel(Board board, int theseusTile, int minotaurTile){
		this.board = new Board(board);
		this.theseusTile = theseusTile;
		this.minotaurTile = minotaurTile;
		loadImages();
	}
	
	//method to load images for game elements
	private void loadImages() {
		try {
			// Try to load images from the project directory
			theseusImage = ImageIO.read(new File("images/theseus.png"));
			minotaurImage = ImageIO.read(new File("images/minotaur.png"));
			supplyImage = ImageIO.read(new File("images/supply.png"));
			useImages = true;
			System.out.println("Images loaded successfully!");
		} catch (IOException e) {
			// If images can't be loaded, fall back to text
			useImages = false;
			System.out.println("Images not found, using text representation.");
		}
	}
	
	//method to update the board data without recreating the panel
	public void updateBoard(Board newBoard, int newTheseusTile, int newMinotaurTile) {
		this.board = new Board(newBoard);
		this.theseusTile = newTheseusTile;
		this.minotaurTile = newMinotaurTile;
	}
	
	//method to toggle between image and text mode
	public void toggleImageMode() {
		if (theseusImage != null && minotaurImage != null && supplyImage != null) {
			useImages = !useImages;
			repaint();
		}
	}
	
	//method to check if images are being used
	public boolean isUsingImages() {
		return useImages;
	}
	
	//in this function we draw the board
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D gBackground = (Graphics2D) g;	//typecast
        gBackground.setColor(new Color(180, 210, 140));	//greener background color
		gBackground.fillRect(5, 5, 495, 495);		//we fill the background with the color
		
		//we draw these gray lines so that we can create the tiles
		for(int i = 0; i <= 15; i++) {
				Graphics2D gline = (Graphics2D) g;
	            gline.setColor(Color.GRAY);
	            gline.drawLine(5 + 33*i, 5, 5 + 33*i, 500); //vertical lines
	            gline.drawLine(5, 5 + 33*i, 500, 5 + 33*i); //horizontal lines
        }
		
		//we draw the border of the board
		Graphics2D gline = (Graphics2D) g;	//typecast
        gline.setColor(Color.BLACK);		
        gline.setStroke(new BasicStroke(4)); //the thickness of the border
        gline.drawRect(5, 5, 495, 495);

        //we draw the walls of each tile
        gline.setStroke(new BasicStroke(2));
        for(int i = 0; i < 14; i++) {	//i < 14 because we don't want to check the top row (we already filled these walls with the border)
            for(int j = 0; j < 15; j++) {
            	if(board.getTiles()[15*i + j].getUp()) { //we check if there is a wall
            		gline.drawLine(5+33*j, 5+33*(15-i-1), 5+33*(j+1), 5+33*(15-i-1));	//we draw the wall
            	}
            }
        }
        
        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 14; j++) {	//j < 14 because we don't want to check the far right column (we already filled these walls with the border)
    			if(board.getTiles()[15*i + j].getRight()) {
    					gline.drawLine(5+33*(j+1), 5+33*(15-i-1), 5+33*(j+1), 5+33*(15-i));
    			}
            }
    	}
        
        //we draw the players according to their tile
        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
            	int tileId = 15*i + j;
            	int x = 5 + 33*j + 2; // X position with small offset
            	int y = 5 + 33*(15-i-1) + 2; // Y position with small offset
            	
            	if(useImages) {
            		// Draw with images
            		if(tileId == minotaurTile && tileId == theseusTile) {
            			// Both players on same tile - handle collision carefully
            			g.drawImage(theseusImage, x + 2, y + 2, 14, 14, this);
            			g.drawImage(minotaurImage, x + 16, y + 16, 14, 14, this);
            		}
            		else if(tileId == minotaurTile) {
                		if(board.hasSupply(tileId) >= 0) {
                			// Minotaur and supply - only show images, no text
                			g.drawImage(minotaurImage, x + 2, y + 2, 18, 18, this);
                			g.drawImage(supplyImage, x + 18, y + 18, 12, 12, this);
                		}
                		else {
                			// Only Minotaur
                			g.drawImage(minotaurImage, x + 5, y + 5, 20, 20, this);
                		}
                	}
                	else if(tileId == theseusTile) {
                		if(board.hasSupply(tileId) >= 0) {
                			// Theseus and supply - only show images, no text
                			g.drawImage(theseusImage, x + 2, y + 2, 18, 18, this);
                			g.drawImage(supplyImage, x + 18, y + 18, 12, 12, this);
                		}
                		else {
                			// Only Theseus
                			g.drawImage(theseusImage, x + 5, y + 5, 20, 20, this);
                		}
                	}
                	else if(board.hasSupply(tileId) >= 0) {
                		// Only supply - just the image, no number text
                		g.drawImage(supplyImage, x + 8, y + 8, 15, 15, this);
                	}
            	} else {
            		// Original text representation
            		if(tileId == minotaurTile) {
                		if(tileId == theseusTile) {
                    		gline.drawString("  T  M", 5+33*j, -5 + 33*(15-i));
                		}
                		else if(board.hasSupply(tileId) >= 0) {
                    		gline.drawString(" M S" + board.hasSupply(tileId) + " ", 5+33*j, -5 + 33*(15-i));
                		}
                		else gline.drawString("    M ", 5+33*j, -5 + 33*(15-i));
                	}
                	else if(tileId == theseusTile) {
                		gline.drawString("    T ", 5+33*j, -5 + 33*(15-i));
                	}
                	else if(board.hasSupply(tileId) >= 0) {
                		gline.drawString("   S" + board.hasSupply(tileId) + " ", 5+33*j, -5 + 33*(15-i));
                	}
            	}
            }
        }
	}
}
