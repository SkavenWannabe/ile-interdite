package m2104.ile_interdite.vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueReglesDuJeu {
    private final JFrame fenetre;
    private JButton continuer;
    private JLabel titre;
    private JLabel regles;
    public VueReglesDuJeu(){
    	fenetre = new JFrame("Regles Du Jeu");
        fenetre.setLayout(new BorderLayout());
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(600, 400);
        
        titre = new JLabel("Règles du jeu", JLabel.CENTER);
        regles = new JLabel("	Bonjour, le but de ce jeux est de récuperer les 4 trèsors, .....", JLabel.CENTER); 
        
        
        JPanel panelBas = new JPanel(new GridLayout(1,3));
        continuer = new JButton ("Continuer");
        continuer.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                fenetre.setVisible(false);
        	}
        });
        panelBas.add(new JLabel()); panelBas.add(continuer); panelBas.add(new JLabel());
        panelBas.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        fenetre.add(titre, BorderLayout.NORTH);
        fenetre.add(regles, BorderLayout.CENTER);
        fenetre.add(panelBas, BorderLayout.SOUTH);
        
        fenetre.setVisible(true);
    }
}
