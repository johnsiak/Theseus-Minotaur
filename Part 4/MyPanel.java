import java.awt.*;
import javax.swing.*;

//in this class we draw the board of the game as a GUI
public class MyPanel extends JPanel{
	
	private Board board;	//the board
	private int theseusTile, minotaurTile;	//the players' positions
	private static final long serialVersionUID = 1L;

	//the constructor of the class
	MyPanel(Board board, int theseusTile, int minotaurTile){
		this.board = new Board(board);
		this.theseusTile = theseusTile;
		this.minotaurTile = minotaurTile;
	}
	
	//in this function we draw the board
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D gBackground = (Graphics2D) g;	//typecast
        gBackground.setColor(new Color(0xC7EAF0));	//we set the color of the background
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
