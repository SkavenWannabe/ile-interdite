package m2104.ile_interdite.vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class PanelMain extends JPanel {
    private BufferedImage[] img;
    private ArrayList<String> main;

    PanelMain(ArrayList<String> main) {
        this.main = main;
        init_img();
    }

    @Override
    public Dimension getPreferredSize() {
    	Dimension rootSize = SwingUtilities.getRoot(this).getSize();
    	SwingUtilities.getRoot(this).getSize();
    	return new Dimension((int)(rootSize.width/4),(int)(rootSize.height*0.1));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	revalidate();
    	super.paintComponent(g);
    	
    	int nbCartes = main.size();

        int width = getWidth() / (nbCartes < 4 ? nbCartes % 4 : 4);
        int heigh = getHeight() / (nbCartes/5 + 1);

        for(int i = 0; i < nbCartes; i++) {

            int ligne = i / 4;
            int col = i % 4;

            switch (main.get(i)) {
                case "HELICO":
                    g.drawImage(
                            img[2].getScaledInstance(width, heigh, Image.SCALE_DEFAULT),
                            col * width,
                            ligne * heigh,
                            null,
                            null);
                    break;
                case "TRESOR_STATUE":
                    g.drawImage(
                            img[6].getScaledInstance(width, heigh, Image.SCALE_DEFAULT),
                            col * width,
                            ligne * heigh,
                            null,
                            null);
                    break;
                case "TRESOR_CRISTAL":
                    g.drawImage(
                            img[1].getScaledInstance(width, heigh, Image.SCALE_DEFAULT),
                            col * width,
                            ligne * heigh,
                            null,
                            null);
                    break;
                case "TRESOR_CALICE":
                    g.drawImage(
                            img[0].getScaledInstance(width, heigh, Image.SCALE_DEFAULT),
                            col * width,
                            ligne * heigh,
                            null,
                            null);
                    break;
                case "TRESOR_PIERRE":
                    g.drawImage(
                            img[4].getScaledInstance(width, heigh, Image.SCALE_DEFAULT),
                            col * width,
                            ligne * heigh,
                            null,
                            null);
                    break;
                case "MONTEE_EAU":
                    g.drawImage(
                            img[3].getScaledInstance(width, heigh, Image.SCALE_DEFAULT),
                            col * width,
                            ligne * heigh,
                            null,
                            null);
                    break;
                case "SAC_SABLE":
                    g.drawImage(
                            img[5].getScaledInstance(width, heigh, Image.SCALE_DEFAULT),
                            col * width,
                            ligne * heigh,
                            null,
                            null);
                    break;
            }
        }
    }


    public void setMain(ArrayList<String> main) {
        this.main = main;
        repaint();
    }

    private void init_img() {
        img = new BufferedImage[7];

        try {
            img[0] = ImageIO.read(new File("src/assets/cartes/Calice.png"));
            img[1] = ImageIO.read(new File("src/assets/cartes/Cristal.png"));
            img[2] = ImageIO.read(new File("src/assets/cartes/Helicoptere.png"));
            img[3] = ImageIO.read(new File("src/assets/cartes/MonteeDesEaux.png"));
            img[4] = ImageIO.read(new File("src/assets/cartes/Pierre.png"));
            img[5] = ImageIO.read(new File("src/assets/cartes/SacsDeSable.png"));
            img[6] = ImageIO.read(new File("src/assets/cartes/Zephyr.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
