package m2104.ile_interdite.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
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
	private PanelGrille panelGrille;
	
	// Panel pour le Sud, pour faciliter l'affichage de la main
	private JButton btnJ1;
	private JButton btnJ2;
	private JButton btnJ3;
	private JButton btnJ4;
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
	private String actionCourante = "";
	private String [] nomsJoueurs;
	private int nbJoueur;
	private int dif;
	private Grille grille;
	private int carteADonner = -1;
	
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
	private JLabel labelNom1;
	private JLabel labelNom2;
	private JLabel labelNom3;
	private JLabel labelNom4;
	
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
        fenetre.setSize(1380,1480);
        
        mainPanel = new JPanel(new BorderLayout());
        panelNorth = new JPanel(new GridLayout(1,nbJoueur+4));
        panelSouth = new JPanel(new GridLayout(1,4));
        panelEast = new JPanel(new GridLayout(2,1));
        panelWeast = new JPanel(new GridLayout(4,1));
        panelCentre = new JPanel(new BorderLayout());
        
        panelMvt = new JPanel(new GridLayout(6,1));
        panelBtn = new JPanel(new GridLayout(2,1));
        panelInnondation = new JPanel(new GridLayout(2,1));
        
        panelJ1 = new JPanel(new BorderLayout());
        panelJ2 = new JPanel(new BorderLayout());
        panelJ3 = new JPanel(new BorderLayout());
        panelJ4 = new JPanel(new BorderLayout());

        mainPanel.add(panelNorth, BorderLayout.NORTH);
        mainPanel.add(panelSouth, BorderLayout.SOUTH);
        mainPanel.add(panelCentre, BorderLayout.CENTER);
        mainPanel.add(panelEast, BorderLayout.EAST);
        mainPanel.add(panelWeast, BorderLayout.WEST);
        
        // Initialisation Haut de page
        nomTour = new JLabel("Tour 1 : ");
        ArrayList<String> nomsPif = initNomsPif();
        panelNorth.add(nomTour); panelNorth.add(new JLabel());
        labelNom1 = new JLabel();
        if (nomsJoueurs[0].equals(""))
            labelNom1.setText(nomsPif.get(0));
        else
            labelNom1.setText(nomsJoueurs[0]);
        labelNom2 = new JLabel();
        if (nomsJoueurs[1].equals(""))
            labelNom2.setText(nomsPif.get(1));
        else
            labelNom2.setText(nomsJoueurs[0]);
        labelNom2.setForeground(Color.red);
        panelNorth.add(labelNom1);panelNorth.add(labelNom2);
        
        if (nbJoueur == 3) {
        	labelNom3 = new JLabel();
                if (nomsJoueurs[2].equals(""))
                    labelNom3.setText(nomsPif.get(2));
                else
                    labelNom3.setText(nomsJoueurs[2]);
        	panelNorth.add(labelNom3);
        } else if (nbJoueur == 4) {
        	labelNom3 = new JLabel();
                if (nomsJoueurs[2].equals(""))
                    labelNom3.setText(nomsPif.get(2));
                else
                    labelNom3.setText(nomsJoueurs[2]);
        	labelNom4 = new JLabel();
                if (nomsJoueurs[3].equals(""))
                    labelNom4.setText(nomsPif.get(3));
                else
                    labelNom4.setText(nomsJoueurs[3]);
        	panelNorth.add(labelNom3);panelNorth.add(labelNom4);
        }
        panelNorth.add(new JLabel());
        
        // Initialisation Centre de page
        panelGrille = new PanelGrille(grille.getTuilles(), aventuriers); // Creation d'une Grille
        panelGrille.addMouseListener(this);
        panelCentre.add(panelGrille);

        
        // Initialisation partie East
        innonde = new JButton("Carte Innondation");
        innonde.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                System.out.println("J\'INNONDE SON PERE");
                ihm.notifierObservateurs(Message.choisirCarteInnondation());
                actionCourante = "Innonde";
        	}
        });
        innonde.setEnabled(false);
        innondeDef = new JButton();
        innondeDef.setEnabled(false); // Pas de possibilité d'obtenir la defaussse
        
        panelInnondation.add(innonde);
        panelInnondation.add(innondeDef);

        niveau = new VueNiveau(dif);
        
        panelEast.add(panelInnondation);
        panelEast.add(niveau);
        
        
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
                    innonde.setEnabled(true);
                    tresors.setEnabled(false);
                    nbActionsRestantes(0);
                    ihm.notifierObservateurs(Message.choisirCarteTresors());
        	}
        });
        indication = new JLabel("Pioche = fin de tour");
        
        indication2 = new JLabel("Action Restantes : 3");
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
        		ihm.notifierObservateurs(Message.recupererTresor());
        	}
        });
        donnerT = new JButton("Donner Tresors");
        donnerT.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		actionCourante = "Donner";
        		ihm.notifierObservateurs(Message.testDonner());
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
        btnJ1 = new JButton(labelNom1.getText());
        btnJ1.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(carteADonner != -1) {
                                System.out.println("IHM : Receveur choisi : 0");
                                System.out.println("IHM : Carte choisie : " + carteADonner);
        			ihm.notifierObservateurs(Message.donner(0, carteADonner));
        			carteADonner = -1;
        			desactiverBoutonJoueur();
        		}
        	}
        });
        btnJ1.setEnabled(false);
        panelJ1.add(btnJ1, BorderLayout.NORTH);
        panelCartesJ1 = new PanelMain(mains.get(0));
        panelCartesJ1.addMouseListener(this);
        panelJ1.add(panelCartesJ1);
        
        btnJ2 = new JButton(labelNom2.getText());
        btnJ2.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(carteADonner !=-1) {
                                System.out.println("IHM : Receveur choisi : 1");
        			ihm.notifierObservateurs(Message.donner(1, carteADonner));
        			carteADonner = -1;
        			desactiverBoutonJoueur();
        		}
        	}
        });
        btnJ2.setEnabled(false);
        panelJ2.add(btnJ2, BorderLayout.NORTH);
        panelCartesJ2 = new PanelMain(mains.get(1));
        panelCartesJ2.addMouseListener(this);
        panelJ2.add(panelCartesJ2);
        

        if (nbJoueur == 4) {
        	btnJ3 = new JButton(labelNom3.getText());
        	btnJ3.addActionListener(new java.awt.event.ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		if(carteADonner !=-1) {
                                System.out.println("IHM : Receveur choisi : 2");
            			ihm.notifierObservateurs(Message.donner(2, carteADonner));
            			carteADonner = -1;
            			desactiverBoutonJoueur();
            		}
            	}
            });
            btnJ3.setEnabled(false);
            panelJ3.add(btnJ3, BorderLayout.NORTH);
            panelCartesJ3 = new PanelMain(mains.get(2));
            panelCartesJ3.addMouseListener(this);
            panelJ3.add(panelCartesJ3);
            
            btnJ4 = new JButton(labelNom4.getText());
            btnJ4.addActionListener(new java.awt.event.ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		if(carteADonner !=-1) {
                                System.out.println("IHM : Receveur choisi : 3");
            			ihm.notifierObservateurs(Message.donner(3, carteADonner));
            			carteADonner = -1;
            			desactiverBoutonJoueur();
            		}
            	}
            });
            btnJ4.setEnabled(false);
            panelJ4.add(btnJ4, BorderLayout.NORTH);
            panelCartesJ4 = new PanelMain(mains.get(3));
            panelCartesJ4.addMouseListener(this);
            panelJ4.add(panelCartesJ4);
           
        }else if (nbJoueur == 3) {
        	btnJ3 = new JButton(labelNom3.getText());
        	btnJ3.addActionListener(new java.awt.event.ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		if(carteADonner !=-1) {
                                System.out.println("Receveur choisi : 1");
            			ihm.notifierObservateurs(Message.donner(2, carteADonner));
            			carteADonner = -1;
            			desactiverBoutonJoueur();
            		}
            	}
            });
            btnJ3.setEnabled(false);
            panelJ3.add(btnJ3, BorderLayout.NORTH);
            panelCartesJ3 = new PanelMain(mains.get(2));
            panelCartesJ3.addMouseListener(this);
            panelJ3.add(panelCartesJ3);
            
        }
        
        if(nbJoueur == 2){
            panelSouth.add(new JPanel());
            panelSouth.add(panelJ1);
            panelSouth.add(panelJ2);
            panelSouth.add(new JPanel());
        } else if(nbJoueur == 3){
            panelSouth.add(panelJ1);
            panelSouth.add(panelJ2);
            panelSouth.add(panelJ3);
            panelSouth.add(new JPanel());
        } else {
            panelSouth.add(panelJ1);
            panelSouth.add(panelJ2);
            panelSouth.add(panelJ3);
            panelSouth.add(panelJ4);
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
	
	public void afficherMain(int i, ArrayList<String> carte) {
		if(i == 0) {
			panelCartesJ1.setMain(carte);
                        panelCartesJ1.initCarteSelectionnable();
		}else if(i == 1) {
			panelCartesJ2.setMain(carte);
                        panelCartesJ2.initCarteSelectionnable();
		}else if(i == 2) {
			panelCartesJ3.setMain(carte);
                        panelCartesJ3.initCarteSelectionnable();
		}else if(i == 3) {
			panelCartesJ4.setMain(carte);
                        panelCartesJ4.initCarteSelectionnable();
		}
		
	}
	
	public void afficherTresors(HashMap tresors) {
		panelGrille.changerEtatTresor(tresors);
	}
	
    private ArrayList<String> initNomsPif(){
        ArrayList<String> noms = new ArrayList<>();
        
        noms.add("Vincenzo");
        noms.add("Anne");
        noms.add("Marion");
        noms.add("Lucas");
        noms.add("Gaston");
        noms.add("Sophie");
        noms.add("George");
        noms.add("Michelle");
        noms.add("Robert");
        noms.add("Samantha");
        noms.add("Hector");
        noms.add("Camille");
        noms.add("Tom");
        noms.add("Zoé");
        noms.add("Félix");
        noms.add("Philippe");
        noms.add("Gilbert");
        noms.add("Achille");
        noms.add("Maurice");
        noms.add("Etienne");
        noms.add("Victoria");
        noms.add("Catherine");
        noms.add("Cécile");
        noms.add("Marianne");
        noms.add("Véronique");
        noms.add("Thomas");
        noms.add("Maxime");
        noms.add("Stéphane");
        noms.add("Bernadette");
        noms.add("Mireille");
        noms.add("Anita");
        noms.add("Karine");
        
        Collections.shuffle(noms);
        return noms;
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
    
    //rend les tuilles selectionnable
    public void mainSelectionnable(int numero) {
    	if (numero==0) {
    		panelCartesJ1.toutSelectionnable();
    	} else if (numero ==1) {
    		panelCartesJ2.toutSelectionnable();
    	} else if (numero ==2) {
    		panelCartesJ3.toutSelectionnable();
    	}else if (numero ==3) {
    		panelCartesJ4.toutSelectionnable();
    	}
    }
    
    public void nouveauTour(int tour, int difficulte) {
        innonde.setEnabled(false);
        tresors.setEnabled(true);
    	nomTour.setText("Tour "+ tour+ ":");
    	labelNom1.setForeground(Color.black);
    	labelNom2.setForeground(Color.black);
    	if (nbJoueur == 3) {
    		labelNom3.setForeground(Color.black);
    	} else if (nbJoueur == 4) {
    		labelNom3.setForeground(Color.black);
        	labelNom4.setForeground(Color.black);}
    	
    	if ((tour % nbJoueur) == 0) {
    		labelNom1.setForeground(Color.red);
    		System.out.println(0);
    	}else if ((tour % nbJoueur) == 1) {
    		labelNom2.setForeground(Color.red);
    		System.out.println(1);
    	}else if ((tour % nbJoueur) == 2){
    		labelNom3.setForeground(Color.red);
    		System.out.println(2);
    	}else if ((tour % nbJoueur) == 3){
    		labelNom4.setForeground(Color.red);
    		System.out.println(3);
    	}
    	niveau.setNiveau(difficulte);
    }
    
    public void nbActionsRestantes(int action) {
    	indication2.setText("Action restantes : " + action);
        if(action == 0){
            deplacer.setEnabled(false);
            assecher.setEnabled(false);
            donnerT.setEnabled(false);
            gagnerT.setEnabled(false);
        }
    }

    public void actionsPossibles(ArrayList<Boolean> actionsPossibles){
        
        deplacer.setEnabled(actionsPossibles.get(0));
        assecher.setEnabled(actionsPossibles.get(1));
        donnerT.setEnabled(actionsPossibles.get(2));
        gagnerT.setEnabled(actionsPossibles.get(3));
        
    }
    public void desactiverBoutonJoueur() {
    	btnJ1.setEnabled(false);
    	btnJ2.setEnabled(false);
    	if (nbJoueur == 3) {
    		btnJ3.setEnabled(false);
    	}else if (nbJoueur == 4) {
    		btnJ3.setEnabled(false);
    		btnJ4.setEnabled(false);
    	}
    }
    
    public void traiterCartes(PanelMain panel, int x, int y, int joueur) {
    	int numCarte = panel.getNumeroCarte(x, y);
        System.out.println("IHM : numCarte = " + numCarte);
    	if (panel.helicoSelectionner(numCarte)) {
    		actionCourante = "Deplacer";
    		
    	} else if (panel.sacSelectionner(numCarte)) {
    		actionCourante = "Assecher";
    		ihm.notifierObservateurs(Message.sacDeSable(joueur, numCarte));
    		
    	} else if (actionCourante == "Donner") {
            if (panel.estSelectionnables(numCarte)) {
            	if (carteADonner == -1) {
                    carteADonner = numCarte;
            	}	
            }
    	}
    }

    public void autreMains(ArrayList<Integer> persoProches){
        for(int i = 0; i < persoProches.size(); i++){
            switch(persoProches.get(i)){
                case 0:
                    btnJ1.setEnabled(true);
                    break;
                case 1:
                    btnJ2.setEnabled(true);
                    break;
                case 2:
                    btnJ3.setEnabled(true);
                    break;
                case 3:
                    btnJ4.setEnabled(true);
                    break;
            }
        }
    }
    
    public void noyadeEnCours(){
        actionCourante = "Noyade";
        innonde.setEnabled(false);
    }
    
    public void noyadeFinie(){
        actionCourante = "";
        innonde.setEnabled(true);
    }
    
    public String getActionEnCours(){
        return actionCourante;
    }
    
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == panelGrille) {
			if (panelGrille.estSelectionnable(panelGrille.getNumeroTuile(e.getX(), e.getY()))) {
                            System.out.println("Case valide");
				switch (actionCourante) {
					case "Deplacer" : 
                                            ihm.notifierObservateurs(Message.bouger(panelGrille.getNumeroTuile(e.getX(), e.getY())));
                                            break;
					case "Assecher" :
                                            ihm.notifierObservateurs(Message.assecher(panelGrille.getNumeroTuile(e.getX(), e.getY())));
                                            break;
                                        case "Noyade" :
                                            System.out.println("Nage valide");
                                            ihm.notifierObservateurs(Message.nage(panelGrille.getNumeroTuile(e.getX(), e.getY())));
                                            break;
				}
			}
		} else if(e.getSource() == panelCartesJ1) {
			traiterCartes(panelCartesJ1, e.getX(), e.getY(), 0);
		} else if (e.getSource() == panelCartesJ2){
			traiterCartes(panelCartesJ2, e.getX(), e.getY(), 1);
		} else if (e.getSource() == panelCartesJ3){
			traiterCartes(panelCartesJ3, e.getX(), e.getY(), 2);
		} else if (e.getSource() == panelCartesJ4){
			traiterCartes(panelCartesJ4, e.getX(), e.getY(), 3);
		}
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
