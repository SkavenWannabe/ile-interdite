package m2104.ile_interdite.vue;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Stack;
import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueDeffausse {
	private JFrame fenetre;
	private Stack deffausse;
	
	private JPanel mainPanel;
	private JPanel panelCarte;
	private JPanel panelSud;
	
	private JButton retour;
	
	
	public VueDeffausse(Stack deffausse) {
		this.deffausse = deffausse;
		
		fenetre = new JFrame("Deffausse carte Tresors");
        fenetre.setResizable(false);
        fenetre.setLayout(new BorderLayout());
        fenetre.setSize(610, 500);
        
        mainPanel = new JPanel(new BorderLayout());
        panelCarte = new JPanel(new GridLayout(6,4));
        panelSud = new JPanel(new GridLayout(1,3));
        
        for(int i = 0; i < deffausse.size(); i++ ) {
            // TODO : afficher les images de deffausse 
        }
        
        
        retour = new JButton("Retour");
        retour.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		fenetre.dispose();
        	}
        });
        
        
        panelSud.add(new JLabel()); panelSud.add(retour); panelSud.add(new JLabel());
        mainPanel.add(panelSud, BorderLayout.SOUTH);
        fenetre.add(mainPanel);
        fenetre.setVisible(true);
	}
}
