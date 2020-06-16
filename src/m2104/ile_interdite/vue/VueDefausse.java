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
	
	private JPanel mainPanel;
	private PanelDefausse panelCarte;
	private JPanel panelSud;
	
	private JButton retour;

	
	
	public VueDefausse(Stack defausse) {

		fenetre = new JFrame("Defausse carte Tresors");
        fenetre.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        fenetre.setLayout(new BorderLayout());
        fenetre.setSize(610, 500);
        
        mainPanel = new JPanel(new BorderLayout());
        panelCarte = new PanelDefausse(defausse);

        panelSud = new JPanel(new GridLayout(1,3));

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
        fenetre.setVisible(true);
    }

    public void setDefausse(Stack defausse) {
	    panelCarte.setDefausse(defausse);
    }
}
