/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Anne
 */
public class VueFinJeu {
    
    private JFrame finJeu;
    private JPanel mainPanel;
    private JButton recommencer;
    private JButton quitter;
    private JLabel titre;
    private JTextArea victoire;
    private JTextArea defaite;
    private JPanel panelBas;
    private VueInscriptionJoueurs retour;
    private final IHM ihm;
    
    public VueFinJeu(Boolean win, IHM ihm){
        this.ihm = ihm;
        //Création de la fenêtre
        finJeu = new JFrame("Fin de partie");
        finJeu.setSize(800,200);
        
        //Ajout du titre 
        titre = new JLabel("La partie est terminée", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.PLAIN, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        finJeu.add(titre, BorderLayout.NORTH);
        
        //Création des zones de texte en fonction de si la partie est gagnée ou perdue
        victoire = new JTextArea(""
        +"Bravo ! Vous avez réussi à récupérer les trésors et à vous enfuir ! \n"
        +"Que diriez-vous de retenter votre chance ?");
        
        defaite = new JTextArea(""
        +"Vous avez perdu... Peut être que la difficulté était trop haute ou alors avez-vous beaucoup de malchance...\n"
        +"Pourquoi ne pas prendriez vous pas votre revanche ? \n"
        +"Les trésors vous attendent !");
        
        //Affichage du texte en fonction du message victoire() ou defaite() 
        if(win){
          victoire.setFont(new Font("Arial", Font.PLAIN, 14));
          victoire.setDisabledTextColor(Color.BLACK);
          finJeu.add(victoire, BorderLayout.CENTER);
          victoire.setEnabled(false);
          victoire.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
        else{
          defaite.setFont(new Font("Arial", Font.PLAIN, 14));
          defaite.setDisabledTextColor(Color.BLACK);
          finJeu.add(defaite, BorderLayout.CENTER);
          defaite.setEnabled(false);
          defaite.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
        
        //Création et ajout des boutons pour quitter le jeu ou recommencer une partie
        panelBas = new JPanel(new GridLayout(1,3));
        panelBas.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        recommencer = new JButton ("Recommencer");
        recommencer.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                finJeu.setVisible(false);
                retour = new VueInscriptionJoueurs(ihm);
                finJeu.dispose();
                ihm.getVueJeu().detruire();
        	}
        });
        
        quitter = new JButton("Quitter");
        quitter.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                System.exit(0);
        	}
        });
        //Ajout des boutons au panel
        panelBas.add(recommencer);
        panelBas.add(new JLabel());
        panelBas.add(quitter);
        
        //Ajout du panel avec les boutons en bas de la fenêtre
        finJeu.add(panelBas,BorderLayout.SOUTH);
        
        finJeu.setVisible(true);
    }
    
}
