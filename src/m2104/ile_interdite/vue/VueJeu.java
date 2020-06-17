package m2104.ile_interdite.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import m2104.ile_interdite.modele.CarteTresor;
import m2104.ile_interdite.modele.Grille;
import m2104.ile_interdite.util.Message;

public class VueJeu implements MouseListener {
    private final IHM ihm;

	private JFrame fenetre;
	
	//Panel principal, pour découper la fenêtre
	private JPanel mainPanel;
	private JPanel panelNorth;
	private JPanel panelSouth;
	private JPanel panelEast;
	private JPanel panelWeast;
	private JPanel panelCentre;
	
	// Panel secondaire pour faciliter l'affichage 
	private JPanel panelMvt;
	private JPanel panelBtn;
	private JPanel panelInnondation;
	private JPanel panelNiveau;
	private PanelGrille panelGrille;
	
	// Panel pour le Sud, pour faciliter l'affichage de la main 
	//TODO: Créer la main dans une classe apart comme pour la grille (ex: PannelMain)
	private JPanel panelJ1;
	private JPanel panelJ2;
	private JPanel panelJ3;
	private JPanel panelJ4;
	private PanelMain panelCartesJ1;
	private PanelMain panelCartesJ2;
	private PanelMain panelCartesJ3;
	private PanelMain panelCartesJ4;
	
	//Pour acceder au autre vue nécessaire
	private VueReglesDuJeu regles;
	private VueInscriptionJoueurs init;
	private VueNiveau niveau;
	private VueDefausse defausse;
	
	private JLabel nomTour;
	private int nbCoup = 3;
	private String actionCourante = "";
	private String [] nomsJoueurs;
	private int nbJoueur;
	private int dif;
	private Grille grille;
	
	//Ensemble des bouttons necessaires
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
	
	public VueJeu(IHM ihm, String[] nomsJoueurs, int nbJoueur, int difficulte, Grille grille, HashMap<String,Integer> aventuriers, HashMap<Integer, ArrayList> mains) {
		//initialisation attribut
		this.ihm = ihm;
		this.nomsJoueurs = nomsJoueurs;
		this.nbJoueur = nbJoueur;
		this.dif = difficulte;
		this.grille = grille;
		
		//initialisation Fenetre
		fenetre = new JFrame("Ile interdite");
        fenetre.setLayout(new BorderLayout());
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(1480,1480);
        
        mainPanel = new JPanel(new BorderLayout());
        panelNorth = new JPanel(new GridLayout(1,nbJoueur+4));
        panelSouth = new JPanel(new FlowLayout(FlowLayout.LEADING));
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

        mainPanel.add(panelNorth, BorderLayout.NORTH);
        mainPanel.add(panelSouth, BorderLayout.SOUTH);
        mainPanel.add(panelCentre, BorderLayout.CENTER);
        mainPanel.add(panelEast, BorderLayout.EAST);
        mainPanel.add(panelWeast, BorderLayout.WEST);
        
        // Initialisation Haut de page
        nomTour = new JLabel("Tour 1 : ");
        panelNorth.add(nomTour); panelNorth.add(new JLabel());
        for (int i =0; i<nbJoueur; i++) {
        	JLabel labelnom = new JLabel (nomsJoueurs[i] + " - " );
        	panelNorth.add(labelnom);panelNorth.add(new JLabel());
        	if (i==1) {
        		labelnom.setForeground(Color.red);
        	}
        }        
        
        // Initialisation Centre de page
        
        panelGrille = new PanelGrille(grille.getTuilles(), aventuriers); // Creation d'une Grille
        panelGrille.addMouseListener(this);
        panelCentre.add(panelGrille);

        
        // Initialisation partie East
        innonde = new JButton("Carte Innondation");
        innonde.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                ihm.notifierObservateurs(Message.choisirCarteInnondation());
                actionCourante = "Innonde";
        	}
        });
        innondeDef = new JButton();
        innondeDef.setEnabled(false); // Pas de possibilité d'obtenir la deffaussse 
        
        panelInnondation.add(innonde);
        panelInnondation.add(innondeDef);

        niveau = new VueNiveau(dif);
        panelNiveau.add(niveau);
        
        panelEast.add(panelInnondation);
        panelEast.add(panelNiveau);
        
        
        // Initialisation partie Weast
        tresorsDef = new JButton();
        tresorsDef.setEnabled(false);
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
        		actionCourante = "Deplacer";
        		ihm.notifierObservateurs(Message.testBouger());
        	}
        });
        assecher = new JButton("Assecher");
        assecher.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		actionCourante = "Assecher";
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
        //Minimum deux joueur, donc init de deux joueur
        panelJ1.add(new JLabel(nomsJoueurs[0]));
        panelCartesJ1 = new PanelMain(mains.get(0));
        //panelCartesJ1.setPreferredSize(new Dimension(panelJ1.getWidth(), panelJ1.getHeight()));
        panelJ1.add(panelCartesJ1);
        
        panelJ2.add(new JLabel(nomsJoueurs[1]));
        panelCartesJ2 = new PanelMain(mains.get(1));
        panelJ2.add(panelCartesJ2);
        
        panelSouth.add(panelJ1);
        panelSouth.add(panelJ2);
        
        if (nbJoueur == 4) {
            panelJ3.add(new JLabel(nomsJoueurs[2]));
            panelCartesJ3 = new PanelMain(mains.get(2));
            panelJ3.add(panelCartesJ3);
            
            panelJ4.add(new JLabel(nomsJoueurs[3]));
            panelCartesJ4 = new PanelMain(mains.get(3));
            panelJ4.add(panelCartesJ4);
            
            panelSouth.add(panelJ3);
            panelSouth.add(panelJ4);
        }else if (nbJoueur == 3) {
            panelJ3.add(new JLabel(nomsJoueurs[2]));
            panelCartesJ3 = new PanelMain(mains.get(2));
            panelJ3.add(panelCartesJ3);
            
            panelSouth.add(panelJ3);
        }
        
        fenetre.add(mainPanel);
        fenetre.setVisible(true);
	}
	
	
	public void piocheTresors() {
		tresorsDef.setEnabled(true);
	}
	
	public void afficherDefausse(Stack defausse) {
		this.defausse = new VueDefausse(defausse);
	}
	
	public void afficherMain(int i, ArrayList<CarteTresor> carte) {
//		if(i == 0) {
//			panelCartesJ1.changerMain(ArrayList<CarteTresor> carte);
//		}else if(i == 1) {
//			panelCartesJ2.changerMain(ArrayList<CarteTresor> carte);
//		}else if(i == 2) {
//			panelCartesJ3.changerMain(ArrayList<CarteTresor> carte);
//		}else if(i == 3) {
//			panelCartesJ4.changerMain(ArrayList<CarteTresor> carte);
//		}
		
	}
	
    public void clickPossible(int[] tab) {
    	panelGrille.selectionnerTuiles(tab);
    }
    
    public void deplacerAventurier(String role, int tuile) {
        panelGrille.deplacerAventurier(role, tuile);
    	actionCourante = "";
    }
    
    // en fonction de l'etat permet d'assecher ou d'innonder une tuille
    public void changerEtatTuile (int tuile, String etat) {
    	panelGrille.changerEtatTuile(tuile, etat);
    	actionCourante = "";
    }

    public void actionsPossibles(ArrayList<Boolean> actionsPossibles){
        
        deplacer.setEnabled(actionsPossibles.get(0));
        assecher.setEnabled(actionsPossibles.get(1));
        donnerT.setEnabled(actionsPossibles.get(2));
        gagnerT.setEnabled(actionsPossibles.get(3));
        
    }
    
	@Override
	public void mouseClicked(MouseEvent e) {
		switch (actionCourante) {
		case "Deplacer" : 
			ihm.notifierObservateurs(Message.bouger(panelGrille.getNumeroTuile(e.getX(), e.getY())));
			break;
		case "Assecher" :
			ihm.notifierObservateurs(Message.assecher(panelGrille.getNumeroTuile(e.getX(), e.getY())));	
			break;
		}
		//rajouter des Case en fonction des actionCourante possible - (pour la main, donner carteTresors, action speciale)
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}
