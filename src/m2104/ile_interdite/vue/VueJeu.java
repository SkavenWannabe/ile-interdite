package m2104.ile_interdite.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import m2104.ile_interdite.modele.CarteTresor;
import m2104.ile_interdite.modele.Grille;
import m2104.ile_interdite.util.Message;

public class VueJeu {
    private final IHM ihm;

	private JFrame fenetre;
	
	private JPanel mainPanel;
	private JPanel panelNorth;
	private JPanel panelSouth;
	private JPanel panelEast;
	private JPanel panelWeast;
	private JPanel panelCentre;
	
	private JPanel panelMvt;
	private JPanel panelBtn;
	private JPanel panelInnondation;
	private JPanel panelNiveau;
	
	private JPanel panelJ1;
	private JPanel panelJ2;
	private JPanel panelJ3;
	private JPanel panelJ4;
	private JPanel panelCartesJ1;
	private JPanel panelCartesJ2;
	private JPanel panelCartesJ3;
	private JPanel panelCartesJ4;

	private VueReglesDuJeu regles;
	private VueInscriptionJoueurs init;
	private VueNiveau niveau;
	private VueDefausse defausse;
	
	private JLabel nomTour;
	private int nbCoup = 3;
	private String [] nomsJoueurs;
	private int nbJoueur;
	private int dif;
	private Grille grille;
	
	private JButton tresors;
	private JButton tresorsDef;
	private JLabel indication;
	private JLabel indication2;
	private JButton deplacer;
	private JButton assecher;
	private JButton gagnerT;
	private JButton donnerT;
	private JButton rdj;
	private JButton retour;
	
	private JButton innonde;
	private JButton innondeDef;
	private JLabel nom;
	
	public VueJeu(IHM ihm, String[] nomsJoueurs, int nbJoueur, int difficulte, Grille grille) {
		//initialisation attribut
		this.ihm = ihm;
		this.nomsJoueurs = nomsJoueurs;
		this.nbJoueur = nbJoueur;
		this.dif = difficulte;
		
		//initialisation Fenetre
		fenetre = new JFrame("Ile interdite");
        fenetre.setLayout(new BorderLayout());
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(1480,1480);
        
        mainPanel = new JPanel(new BorderLayout());
        panelNorth = new JPanel(new GridLayout(1,nbJoueur+4));
        panelSouth = new JPanel();
        panelEast = new JPanel(new GridLayout(2,1));
        panelWeast = new JPanel(new GridLayout(4,1));
        panelCentre = new JPanel(new BorderLayout());
        
        panelMvt = new JPanel(new GridLayout(6,1));
        panelBtn = new JPanel(new GridLayout(2,1));
        panelInnondation = new JPanel(new GridLayout(2,1));
        panelNiveau = new JPanel(new BorderLayout());
        
        panelJ1 = new JPanel(new GridLayout(2,1));
        panelJ2 = new JPanel(new GridLayout(2,1));
        panelJ3 = new JPanel(new GridLayout(2,1));
        panelJ4 = new JPanel(new GridLayout(2,1));
        panelCartesJ1 = new JPanel(new BorderLayout());
        panelCartesJ2 = new JPanel(new BorderLayout());
        panelCartesJ3 = new JPanel(new BorderLayout());
        panelCartesJ4 = new JPanel(new BorderLayout());

        mainPanel.add(panelNorth, BorderLayout.NORTH);
        mainPanel.add(panelSouth, BorderLayout.SOUTH);
        mainPanel.add(panelCentre, BorderLayout.CENTER);
        mainPanel.add(panelEast, BorderLayout.EAST);
        mainPanel.add(panelWeast, BorderLayout.WEST);
        
        // Initialisation Haut de page
        nomTour = new JLabel("Tour 1 : ");
        panelNorth.add(nomTour); panelNorth.add(new JLabel());
        for (int i =0; i<nbJoueur; i++) {
        	JLabel labelnom = new JLabel (nomsJoueurs[i]);
        	panelNorth.add(labelnom);panelNorth.add(new JLabel());
        	if (i==1) {
        		labelnom.setForeground(Color.red);
        	}
        }        
        
        // Initialisation Centre de page
        panelCentre.add(new PannelGrille(grille.getTuilles()));

        
        // Initialisation partie East
        innonde = new JButton("Carte Innondation");
        innonde.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                ihm.notifierObservateurs(Message.choisirCarteInnondation());
        	}
        });
        innondeDef = new JButton();
        innondeDef.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.out.println("Click sur deffause innonde");
        	}
        });
        
        panelInnondation.add(innonde);
        panelInnondation.add(innondeDef);
        niveau = new VueNiveau(dif);
        panelNiveau.add(niveau);
        
        panelEast.add(panelInnondation);
        panelEast.add(panelNiveau);
        
        
        // Initialisation partie Weast
        tresorsDef = new JButton();
        tresorsDef.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ihm.notifierObservateurs(Message.voirDefausse());
        	}
        });
        tresors = new JButton("Carte Tresors");
        tresors.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                ihm.notifierObservateurs(Message.choisirCarteTresors());
        	}
        });
        indication = new JLabel("Pioche = fin de tour");
        
        indication2 = new JLabel("Action Restantes : " + nbCoup);
        deplacer = new JButton("Deplacer");
        deplacer.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ihm.notifierObservateurs(Message.bouger());
        	}
        });
        assecher = new JButton("Assecher");
        assecher.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ihm.notifierObservateurs(Message.testAssecher());
        	}
        });
        gagnerT = new JButton("Gagner Tresors");
        gagnerT.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//ihm.notifierObservateurs(Message.recupererTresor());
        	}
        });
        donnerT = new JButton("Donner Tresors");
        donnerT.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//ihm.notifierObservateurs(Message.donner());
        	}
        });
        
        rdj = new JButton("Regles Du Jeu");
        rdj.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		regles = new VueReglesDuJeu();
        	}
        });
        retour = new JButton("Retour");
        retour.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		init = new VueInscriptionJoueurs(null);
        		fenetre.dispose();
        	}
        });
        
        panelWeast.add(tresorsDef);
        panelWeast.add(tresors);
        
        panelMvt.add(indication);
        panelMvt.add(indication2);
        panelMvt.add(deplacer);
        panelMvt.add(assecher);
        panelMvt.add(gagnerT);
        panelMvt.add(donnerT);
        panelWeast.add(panelMvt);
        
        panelBtn.add(rdj);
        panelBtn.add(retour);
        panelWeast.add(panelBtn);
        
        // Initialisation Bas de page    
        panelJ1.add(new JLabel(nomsJoueurs[0]));
        panelJ1.add(panelCartesJ1);
        panelJ2.add(new JLabel(nomsJoueurs[1]));
        panelJ2.add(panelCartesJ2);
        
        if (nbJoueur == 4) {
            panelJ3.add(new JLabel(nomsJoueurs[2]));
            panelJ3.add(panelCartesJ3);
            panelJ4.add(new JLabel(nomsJoueurs[3]));
            panelJ4.add(panelCartesJ4);
        }else if (nbJoueur == 3) {
            panelJ3.add(new JLabel(nomsJoueurs[2]));
            panelJ3.add(panelCartesJ3);
        }
        panelSouth.add(panelJ1);
        panelSouth.add(panelJ2);
        panelSouth.add(panelJ3);
        panelSouth.add(panelJ4);
        
        fenetre.add(mainPanel);
        fenetre.setVisible(true);
	}
	
	
	public void piocheTresors() {
		
	}
	
	public void piocheInnondation() {
		System.out.println("MVC pioche innondation");
	}
	
	public void afficherDefausse(Stack defausse) {
		this.defausse = new VueDefausse(defausse);
		System.out.println("MVC montrer defausse");
	}
	
	public void afficherMain(int i, ArrayList<CarteTresor> carte) {
		if(i == 0) {
			//modifier panelCartesJ1
		}else if(i == 1) {
			//modifier panelCartesJ2
		}else if(i == 2) {
			//modifier panelCartesJ3
		}else if(i == 3) {
			//modifier panelCartesJ4
		}
		
	}
}
