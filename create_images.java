import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class create_images {
    public static void main(String[] args) {
        createTheseusImage();
        createMinotaurImage();
        createSupplyImage();
        System.out.println("Sample images created successfully!");
    }
    
    private static void createTheseusImage() {
        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        // Enable antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw Theseus as a blue hero figure with sword
        g2d.setColor(new Color(30, 144, 255)); // Dodger blue
        g2d.fillOval(8, 4, 16, 16); // Head
        g2d.setColor(new Color(0, 100, 200)); // Darker blue
        g2d.fillRect(12, 18, 8, 10); // Body
        g2d.fillRect(10, 24, 4, 6); // Left leg
        g2d.fillRect(18, 24, 4, 6); // Right leg
        
        // Sword
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(2, 8, 6, 2); // Sword blade
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(1, 9, 2, 2); // Sword hilt
        
        g2d.dispose();
        
        try {
            ImageIO.write(img, "PNG", new File("images/theseus.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void createMinotaurImage() {
        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        // Enable antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw Minotaur as a dark red/brown creature with horns
        g2d.setColor(new Color(139, 69, 19)); // Saddle brown
        g2d.fillOval(6, 6, 20, 16); // Head
        g2d.fillRect(10, 20, 12, 8); // Body
        g2d.fillRect(8, 26, 4, 6); // Left leg
        g2d.fillRect(20, 26, 4, 6); // Right leg
        
        // Horns
        g2d.setColor(Color.BLACK);
        g2d.fillRect(8, 4, 3, 6); // Left horn
        g2d.fillRect(21, 4, 3, 6); // Right horn
        
        // Eyes
        g2d.setColor(Color.RED);
        g2d.fillOval(10, 10, 3, 3); // Left eye
        g2d.fillOval(19, 10, 3, 3); // Right eye
        
        g2d.dispose();
        
        try {
            ImageIO.write(img, "PNG", new File("images/minotaur.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void createSupplyImage() {
        BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        
        // Enable antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw supply as a treasure chest
        g2d.setColor(new Color(139, 69, 19)); // Brown chest
        g2d.fillRect(6, 12, 20, 16); // Main chest body
        g2d.setColor(new Color(255, 215, 0)); // Gold
        g2d.fillRect(8, 8, 16, 8); // Chest lid
        
        // Lock
        g2d.setColor(Color.BLACK);
        g2d.fillRect(14, 18, 4, 3); // Lock body
        g2d.drawOval(15, 16, 2, 2); // Lock hole
        
        // Gold coins effect
        g2d.setColor(new Color(255, 215, 0));
        g2d.fillOval(10, 20, 3, 3);
        g2d.fillOval(19, 22, 3, 3);
        g2d.fillOval(14, 24, 3, 3);
        
        g2d.dispose();
        
        try {
            ImageIO.write(img, "PNG", new File("images/supply.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}