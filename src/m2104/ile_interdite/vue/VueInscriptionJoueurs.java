package m2104.ile_interdite.vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import m2104.ile_interdite.util.Message;

/**
 *
 * @author Yann Laurillau <yann.laurillau@iut2.univ-grenoble-alpes.fr>
 */
public class VueInscriptionJoueurs {
    private final IHM ihm;
    private final JFrame fenetre;

    private JComboBox<Integer> choixNbJoueurs;
    private JLabel [] labelNomJoueurs = new JLabel[4];
    private JTextField [] saisieNomJoueurs = new JTextField[4];
    private JButton inscrire = new JButton("Inscrire");
    private VueReglesDuJeu regles;
    
    private JPanel mainPanel = new JPanel(new GridLayout(1,2));
    private JPanel panelInit = new JPanel(new GridLayout(5,1));
    
    private JPanel main2 = new JPanel(new GridLayout(3,1));
    private JPanel panelChoix = new JPanel(new GridLayout(5,2));
    private JPanel panelChoixDif = new JPanel(new GridLayout(3,2));
    private JPanel panelBtn = new JPanel(new GridLayout(3,3));
    
    private JButton nvPartie;
    private JButton rdj;
    private JButton quitter;
    private String[] nomJoueurs;
    private ButtonGroup groupeDifficulte;
    private JRadioButton [] difficulte;     // tableau de composants RadioButton
    
    public VueInscriptionJoueurs(IHM ihm) {
        this.ihm = ihm;

        fenetre = new JFrame("Inscription");
        fenetre.setLayout(new BorderLayout());
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(600, 400);
        
        //Création des composants
        nvPartie = new JButton("nouvelle partie");
        nvPartie.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		nvPartie.setEnabled(false);
        		deuxiemePartie();
        	}
        });
        
        rdj = new JButton("règle du jeu");
        rdj.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		regles = new VueReglesDuJeu();
        	}
        });
        
        quitter = new JButton("quitter");
        quitter.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        
        //Mise a jour de la fenêtre
        panelInit.add(new JLabel());
        panelInit.add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                try {
                    BufferedImage img = ImageIO.read(new File("fautladeletequandonlavireoublierpassilvousplaitsinonsavaetregenantaexpliquer.png"));
                    g.drawImage(img,0,0, null, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }); //ajout case vide, voir mettre image ?
        panelInit.add(nvPartie);
        panelInit.add(rdj);
        panelInit.add(quitter);
        
        mainPanel.add(panelInit);
        fenetre.add(mainPanel);
        fenetre.setVisible(true);
    }
    
    public void deuxiemePartie() {
    	// nombre de joueurs
        choixNbJoueurs = new JComboBox<>(new Integer[] { 2, 3, 4 });
        panelChoix.add(new JLabel("Nombre de joueurs :"));
        panelChoix.add(choixNbJoueurs);
        
        // Saisie des noms de joueurs
        for(int i = 0; i < saisieNomJoueurs.length; i++) {
            saisieNomJoueurs[i] = new JTextField();
            labelNomJoueurs[i] = new JLabel("Nom du joueur No " + (i + 1) + " :");
            panelChoix.add(labelNomJoueurs[i]);
            panelChoix.add(saisieNomJoueurs[i]);
            labelNomJoueurs[i].setEnabled(i < 2);
            saisieNomJoueurs[i].setEnabled(i < 2);
        }

        // Choix du nombre de joueurs
        choixNbJoueurs.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int nb = (Integer) choixNbJoueurs.getSelectedItem();

                for(int i = 0; i < saisieNomJoueurs.length; i++) {
                    labelNomJoueurs[i].setEnabled(i < nb);
                    saisieNomJoueurs[i].setEnabled(i < nb);
                }
            }
        });

        // Choix de la difficulté
        panelChoixDif.add(new JLabel("Choix difficulté :"));
        panelChoixDif.add(new JLabel()); //Case vide
        JRadioButton bouton;
        
        groupeDifficulte = new ButtonGroup();
        
        difficulte = new JRadioButton[4];        
        
        bouton = new JRadioButton("Novice");
        difficulte[0] = bouton;
        groupeDifficulte.add(bouton);

        bouton = new JRadioButton("Normal");
        difficulte[1] = bouton;
        groupeDifficulte.add(bouton);

        bouton = new JRadioButton("Elite");
        difficulte[2] = bouton;
        groupeDifficulte.add(bouton);
        
        bouton = new JRadioButton("Legendaire");
        difficulte[3] = bouton;
        groupeDifficulte.add(bouton);
        
        panelChoixDif.add(difficulte[0]);
        panelChoixDif.add(difficulte[1]);
        panelChoixDif.add(difficulte[2]);
        panelChoixDif.add(difficulte[3]);
        
        /* On sélectionne la difficulte Novice */
        difficulte[0].setSelected(true);
        
        
        // Inscription des joueurs
        inscrire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remplissage du tableau contenant le nom des joueurs
                int nbJoueurs = (int) choixNbJoueurs.getSelectedItem();

                nomJoueurs = new String[nbJoueurs];
                for (int i = 0; i < nbJoueurs; ++i) {
                    nomJoueurs[i] = saisieNomJoueurs[i].getText();
                }
                //récupération de la difficulté
                int dif = 0;
                for (int i = 0; i < difficulte.length; i++) {
                    if (difficulte[i].isSelected()){
                        dif = i;
                    }
                }   
                ihm.notifierObservateurs(Message.validerJoueurs(nbJoueurs,dif));
                fenetre.dispose();
            }
        });
        panelBtn.add(new JLabel());panelBtn.add(new JLabel());panelBtn.add(new JLabel());
        panelBtn.add(new JLabel());panelBtn.add(inscrire);panelBtn.add(new JLabel());
        panelBtn.add(new JLabel());panelBtn.add(new JLabel());panelBtn.add(new JLabel());
        
        
        // Ajout de tout les composants
        main2.add(panelChoix);
        main2.add(panelChoixDif);
        main2.add(panelBtn);
        mainPanel.add(main2);
        fenetre.add(mainPanel);
        
        fenetre.setVisible(true);
        
    }
    
    public String[] getNomJoueurs() {
        return Arrays.copyOf(this.nomJoueurs, this.nomJoueurs.length);
    }

}
