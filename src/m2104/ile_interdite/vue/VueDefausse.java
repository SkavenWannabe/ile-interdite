package m2104.ile_interdite.vue;

import m2104.ile_interdite.modele.CarteTresor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.*;

public class VueDefausse {
	private JFrame fenetre;
	private Stack defausse;
	
	private JPanel mainPanel;
	private JPanel panelCarte;
	private JPanel panelSud;
	
	private JButton retour;

	private BufferedImage[] img_defausse;
	
	
	public VueDefausse(Stack defausse) {
		this.defausse = defausse;
		init_image();

		fenetre = new JFrame("Defausse carte Tresors");
        fenetre.setResizable(false);
        fenetre.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        fenetre.setLayout(new BorderLayout());
        fenetre.setSize(610, 500);
        
        mainPanel = new JPanel(new BorderLayout());
        panelCarte = new JPanel(new GridLayout(6,4));
        panelSud = new JPanel(new GridLayout(1,3));

        int j = defausse.size();

        for(int i = 0; i < j; i++ ) {
            JLabel lab = new JLabel();
            switch ((CarteTresor) defausse.pop()) {
                case HELICO:
                    lab.setIcon(new ImageIcon(img_defausse[2]));
                    panelCarte.add(lab);
                    break;
                case TRESOR_STATUE:
                    lab.setIcon(new ImageIcon(img_defausse[6]));
                    panelCarte.add(lab);
                    break;
                case TRESOR_CRISTAL:
                    lab.setIcon(new ImageIcon(img_defausse[1]));
                    panelCarte.add(lab);
                    break;
                case TRESOR_CALICE:
                    lab.setIcon(new ImageIcon(img_defausse[0]));
                    panelCarte.add(lab);
                    break;
                case TRESOR_PIERRE:
                    lab.setIcon(new ImageIcon(img_defausse[4]));
                    panelCarte.add(lab);
                    break;
                case MONTEE_EAU:
                    lab.setIcon(new ImageIcon(img_defausse[3]));
                    panelCarte.add(lab);
                    break;
                case SAC_SABLE:
                    lab.setIcon(new ImageIcon(img_defausse[5]));
                    panelCarte.add(lab);
                    break;
            }
        }

        
        retour = new JButton("Retour");
        retour.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		fenetre.dispose();
        	}
        });
        
        
        panelSud.add(new JLabel()); panelSud.add(retour); panelSud.add(new JLabel());
        mainPanel.add(panelSud, BorderLayout.SOUTH);
        mainPanel.add(panelCarte, BorderLayout.CENTER);
        fenetre.add(mainPanel);

        affiche();
	}

	public void affiche() {
	    resetPanelCarte();
	    fenetre.setVisible(true);
    }

    public void resetPanelCarte() {
        panelCarte = new JPanel(new GridLayout(6,4));

        int j = defausse.size();

        for(int i = 0; i < j; i++ ) {
            JLabel lab = new JLabel();
            switch ((CarteTresor) defausse.pop()) {
                case HELICO:
                    lab.setIcon(new ImageIcon(img_defausse[2]));
                    panelCarte.add(lab);
                    break;
                case TRESOR_STATUE:
                    lab.setIcon(new ImageIcon(img_defausse[6]));
                    panelCarte.add(lab);
                    break;
                case TRESOR_CRISTAL:
                    lab.setIcon(new ImageIcon(img_defausse[1]));
                    panelCarte.add(lab);
                    break;
                case TRESOR_CALICE:
                    lab.setIcon(new ImageIcon(img_defausse[0]));
                    panelCarte.add(lab);
                    break;
                case TRESOR_PIERRE:
                    lab.setIcon(new ImageIcon(img_defausse[4]));
                    panelCarte.add(lab);
                    break;
                case MONTEE_EAU:
                    lab.setIcon(new ImageIcon(img_defausse[3]));
                    panelCarte.add(lab);
                    break;
                case SAC_SABLE:
                    lab.setIcon(new ImageIcon(img_defausse[5]));
                    panelCarte.add(lab);
                    break;
            }
        }
	}

	private void init_image() {
	    img_defausse = new BufferedImage[7];

	    try {
            img_defausse[0] = ImageIO.read(new File("src/assets/cartes/Caline.png"));
            img_defausse[1] = ImageIO.read(new File("src/assets/cartes/Cristal.png"));
            img_defausse[2] = ImageIO.read(new File("src/assets/cartes/Helicoptere.png"));
            img_defausse[3] = ImageIO.read(new File("src/assets/cartes/MonteeDesEaux.png"));
            img_defausse[4] = ImageIO.read(new File("src/assets/cartes/Pierre.png"));
            img_defausse[5] = ImageIO.read(new File("src/assets/cartes/SacsDeSable.png"));
            img_defausse[6] = ImageIO.read(new File("src/assets/cartes/Zephyr.png"));
        } catch (Exception e) {
	        e.printStackTrace();
        }
	}
}
