package m2104.ile_interdite.vue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelCartes extends JPanel{
	
    private BufferedImage[] img;
    private ArrayList<String> main;

    private boolean[] cartesSelectionner;
	
    
    PanelCartes(ArrayList<String> main) {
    	System.out.println("PAN : controleur");
        this.main = main;
        initImg();
        initCartesSelectionner();
    }

    private void initImg() {
    	System.out.println("PAN: init image");
        img = new BufferedImage[7];

        try {
            img[0] = ImageIO.read(new File("src/assets/cartes/Calice.png"));
            img[1] = ImageIO.read(new File("src/assets/cartes/Cristal.png"));
            img[2] = ImageIO.read(new File("src/assets/cartes/Helicoptere.png"));
            img[3] = ImageIO.read(new File("src/assets/cartes/MonteeDesEaux.png"));
            img[4] = ImageIO.read(new File("src/assets/cartes/Pierre.png"));
            img[5] = ImageIO.read(new File("src/assets/cartes/SacsDeSable.png"));
            img[6] = ImageIO.read(new File("src/assets/cartes/Zephyr.png"));
        	System.out.println("PAN: fin init en dur");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void initCartesSelectionner() {
    	System.out.println("PAN : init selection");
    	cartesSelectionner = new boolean[main.size()];
    	for (int i = 0; i < main.size(); i++) {
    		cartesSelectionner[i] = Boolean.FALSE;
    	}
    	System.out.println("PAN: fin init selection");
    }
    
    public int getNumeroCarte(int x, int y) {
    	System.out.print("get numero de carte :");
    	int nbCartes = main.size();
        int width = getWidth() / 3;
        int heigh = getHeight() / 3;
        
    	int colonne = (int) (x / width);
    	int ligne= (int) (y / heigh);
    	System.out.println((ligne *3+colonne));
    	return ligne * 3 + colonne;
    }
	
    
    public void setSelection(int idCartes) {
    	System.out.println("SetSelection");
    	for (int i = 0; i < cartesSelectionner.length; i++) {
    		if(i == idCartes) {
    			if(cartesSelectionner[i]) {
    				cartesSelectionner[i] = Boolean.FALSE;
    			}else {
    				cartesSelectionner[i] = Boolean.TRUE;
    			}
    			
    		}
    	}
    	repaint();
    }
	
	public boolean estSelectionner(int idCartes) {
		return cartesSelectionner[idCartes];
	}
	
	public int getSelection() {
		int somme = 0;
		for (int i = 0; i < cartesSelectionner.length; i++) {
    		if(cartesSelectionner[i]) {
    			somme += 1;
    		}
    	}
		return main.size() - somme;
	}
	
	public boolean dejaCliquer(int idCartes) {
		return cartesSelectionner[idCartes];
	}
	
    @Override
    protected void paintComponent(Graphics g) {
    	System.out.println("PAN : paintComponent");

    	super.paintComponent(g);
    	
    	dessinerCartes(g);
    	dessinerSelection(g);

    }
    
    private void dessinerCartes(Graphics g) {
    	System.out.println("PAN : dessiner Carte");

    	int nbCartes = main.size();

        int width = getWidth() / 3;
        int heigh = getHeight() / 3;

        for(int i = 0; i < nbCartes; i++) {

            int ligne = i / 3;
            int col = i % 3;

            switch (main.get(i)) {
                case "HELICO":
                    g.drawImage(img[2].getScaledInstance(width, heigh, Image.SCALE_DEFAULT), col * width, ligne * heigh, null, null);
                    break;
                case "TRESOR_STATUE":
                    g.drawImage(img[6].getScaledInstance(width, heigh, Image.SCALE_DEFAULT), col * width, ligne * heigh, null, null);
                    break;
                case "TRESOR_CRISTAL":
                    g.drawImage(img[1].getScaledInstance(width, heigh, Image.SCALE_DEFAULT), col * width, ligne * heigh, null, null);
                    break;
                case "TRESOR_CALICE":
                    g.drawImage(img[0].getScaledInstance(width, heigh, Image.SCALE_DEFAULT), col * width, ligne * heigh, null, null);
                    break;
                case "TRESOR_PIERRE":
                    g.drawImage(img[4].getScaledInstance(width, heigh, Image.SCALE_DEFAULT), col * width, ligne * heigh, null, null);
                    break;
                case "MONTEE_EAU":
                    g.drawImage(img[3].getScaledInstance(width, heigh, Image.SCALE_DEFAULT), col * width, ligne * heigh, null, null);
                    break;
                case "SAC_SABLE":
                    g.drawImage(img[5].getScaledInstance(width, heigh, Image.SCALE_DEFAULT), col * width, ligne * heigh, null, null);
                    break;
            }
        }
    }
    
    private void dessinerSelection(Graphics g) {
    	System.out.println("PAN : dessiner Selection");

    	Graphics2D g2 = (Graphics2D) g;
    	float epaisseur = 5;
    	Stroke trait = g2.getStroke();
    	g2.setStroke(new BasicStroke(epaisseur));
    	
    	for(int i = 0; i < main.size(); i++) {
	        if(cartesSelectionner[i]) {
	        	g.setColor(Color.green);
	        	g.drawRect((i % 3) * getWidth() / 3, (i / 3) * getHeight() / 3, getWidth() / 3, getHeight() / 3);
	        }
    	}
    	g2.setStroke(trait);
    }
}
