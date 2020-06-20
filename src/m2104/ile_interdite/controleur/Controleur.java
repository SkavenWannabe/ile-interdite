package m2104.ile_interdite.controleur;

import java.util.ArrayList;
import java.util.HashMap;

import m2104.ile_interdite.modele.IleInterdite;
import m2104.ile_interdite.util.Message;
import m2104.ile_interdite.util.Parameters;
import m2104.ile_interdite.vue.IHM;
import patterns.observateur.Observateur;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@iut2.univ-grenoble-alpes.fr>
 */
public class Controleur implements Observateur<Message> {

    private final IleInterdite ileInterdite;
    private final IHM ihm;
    private int nbInondAVenir;
    
    private int joueurSac = -1;
    private int carteSac = -1;
    private int id;
    private boolean helico = false;
    
    public Controleur() {
        this.ileInterdite = new IleInterdite(this);
        this.ihm = new IHM(this);
    }
//
    @Override
    public void traiterMessage(Message msg) {
        if (Parameters.LOGS)
            System.out.println("Controleur.traiterMessage : " + msg);

        switch (msg.getCommande()) {
            case VALIDER_JOUEURS: // initialisation de la partie
                assert msg.hasNbJoueurs();
                this.ileInterdite.initialisation(msg.getNbJoueurs(),msg.getDifficulte());
                String[] nomAventuriers = this.ileInterdite.inscrireJoueurs(msg.getNbJoueurs());
                
                System.out.println("\n--Partie initialisée !--");
                System.out.println("Nombre de joueurs : " + this.ileInterdite.getAventuriers().size());
                System.out.println("Rôles : ");
                System.out.println(this.ileInterdite.getAventuriers());
                System.out.println(ileInterdite);
                
                HashMap<String,Integer> aventuriers = new HashMap<String, Integer>();   //Créé une HashMap des rôles des aventuriers et de leurs positions
                for (int i = 0; i < msg.getNbJoueurs(); i++)
                    aventuriers.put(this.ileInterdite.getAventuriers().get(i).toString(), this.ileInterdite.getAventuriers().get(i).getPosition());
                
                HashMap<Integer, ArrayList> mains = new HashMap<Integer, ArrayList>();  //Créé une HashMap des numéros des aventuriers et de chacune de leur main
                for (int i = 0; i < msg.getNbJoueurs(); i++) {
                	ArrayList<String> cartes = new ArrayList<>();
                	ileInterdite.getMain(i).forEach(x -> cartes.add(x.toString()));
                	mains.put(i, cartes);
                }
                
                this.ihm.creerVuesJeu(nomAventuriers, msg.getDifficulte(), ileInterdite.getGrille(), aventuriers, mains,ileInterdite.getPositions());

                this.nouveauTour();
                break;
                
            case CHOISIR_CARTE_TRESORS: //Pioche une carte tresors
                joueurSac = -1; carteSac = -1; helico = false;
                ihm.piocheTresors(this.ileInterdite.piocheTresor());    //Envoie la nouvelle main après pioche
                nbInondAVenir = this.ileInterdite.getNbInondations();   //Calcul le nombre de Carte Inondations à piocher juste après
                
                ArrayList<String> cartes = new ArrayList<>();
            	ileInterdite.getMain(ileInterdite.getNumeroAventurierEnCours()).forEach(x -> cartes.add(x.toString()));
                ihm.afficherMain(ileInterdite.getNumeroAventurierEnCours(), cartes);
                ihm.augmentNiveau(ileInterdite.getNiveau());            //Met à jour l'affichage du niveau d'eau
                this.ileInterdite.mainPleine();                         //Test le nombre de carte de la main du joueur
            	break;
            	
            case CHOISIR_CARTE_INNONDE: //Pioche une carte innondation
            	helico = false;
                ArrayList<Integer> res = this.ileInterdite.piocheInonde();
                int tuile = res.get(0);                     //Inonde ou fait sombrer la tuille pioché
            	ihm.changerEtatTuile(tuile, ileInterdite.getGrille().getTuille(tuile).getEtat().toString());
                if(res.size() == 1)
                    nbInondAVenir--;
                if(nbInondAVenir < 1){this.nouveauTour();}  //Lorsque l'on a pioché toutes les cartes qu'il fallait piocher, commence un nouveau tour
            	break;
            	
            case VOIR_DEFAUSSE: //permet de voir la defausse
            	ihm.afficherDefausse(ileInterdite.getDefausseTresor());
            	break;

            case TEST_BOUGER: //clique sur le bouton deplacer
            	joueurSac = -1; carteSac = -1; helico = false;
            	ihm.clickPossible(this.ileInterdite.deplacementPossible()); //Envoie la liste des tuiles sur lesquelles l'aventurier peut aller
            	break;
            	
            case BOUGER: //après avoir choisis la tuille permet de se deplacer
            	ileInterdite.deplace(msg.getIdTuile());
            	ihm.deplacerAventurier(ileInterdite.getAventurierEnCours().toString(),msg.getIdTuile());    //Déplace l'aventurier sur la tuile choisie
                this.ihm.actionsPossibles(this.ileInterdite.clicable());                                    //Met à jour les boutons d'actions
                this.ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());                     //Met à jour le nombre d'actions restantes
            	break;
            	
            case TEST_ASSECHER: //clique sur le bouton assecher
            	joueurSac = -1; carteSac = -1; helico = false;
                ihm.clickPossible(this.ileInterdite.assechePossible()); //Envoie la liste des tuiles que l'aventurier peut assécher
            	break;
            	
            case ASSECHER: //après avoir choisis une tuille a assecher
            	ileInterdite.asseche(msg.getIdTuile()); //Assèche la tuile choisie
            	ihm.changerEtatTuile(msg.getIdTuile(), ileInterdite.getGrille().getTuille(msg.getIdTuile()).getEtat().toString());

            	if (joueurSac != -1 && carteSac != -1) {                //Si c'est avec un sac de sable que l'on a asséché ...
            		ileInterdite.sacDeSable(joueurSac, carteSac);   //- Supprime le sac de la main du joueur qui l'a utilisé
            		
                	ArrayList<String> cartesJoueurSac = new ArrayList<String>();
                	ileInterdite.getMain(joueurSac).forEach(x -> cartesJoueurSac.add(x.toString()));
                	ihm.afficherMain(joueurSac, cartesJoueurSac);   //- Met à jour l'affichage de sa main
                	
            		joueurSac = -1; carteSac = -1;
            	}
	        this.ihm.actionsPossibles(this.ileInterdite.clicable());
	        this.ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
            	break;
            	
            case TEST_DONNER: //clique sur donner
            	joueurSac = -1; carteSac = -1; helico = false;
            	ihm.mainSelectionnable(ileInterdite.getNumeroAventurierEnCours());  //Envoie le numéro de l'aventurier qui veut donner
                ihm.autreMains(ileInterdite.PersonnagesProches());                  //Envoie les numéros des aventuriers qui peuvent recevoir
            	break;
            	
            case DONNER: // après avoir choisis une tuile a donner ainsi qu'un utilisateur
            	ileInterdite.donnerTresor(msg.getIdAventurier(),msg.getIdCarte());
            	
            	//Afficher Main du receveur
            	ArrayList<String> cartesReceveur = new ArrayList<>();
            	ileInterdite.getMain(msg.getIdAventurier()).forEach(x -> cartesReceveur.add(x.toString()));
            	ihm.afficherMain(msg.getIdAventurier(), cartesReceveur);
            	
            	//Afficher Main du donneur
            	ArrayList<String> cartesDonneur = new ArrayList<>();
            	ileInterdite.getMain(ileInterdite.getNumeroAventurierEnCours()).forEach(x -> cartesDonneur.add(x.toString()));
            	ihm.afficherMain(ileInterdite.getNumeroAventurierEnCours(), cartesDonneur);
                
            	this.ihm.actionsPossibles(this.ileInterdite.clicable());
                ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
            	break;
            	
            case RECUPERER_TRESOR: // clique sur gagner tresors
            	joueurSac = -1; carteSac = -1;
            	ileInterdite.gagneTresor();                     //Ajoute le trésor dans la liste de ceux qui sont possédés
                ihm.afficherTresors(ileInterdite.getTresors()); //Affiche le trésor obtenu sur la grille
                
                ArrayList<String> main = new ArrayList<>();
            	ileInterdite.getMain(ileInterdite.getNumeroAventurierEnCours()).forEach(x -> main.add(x.toString()));
                ihm.afficherMain(ileInterdite.getNumeroAventurierEnCours(),main);
                
                this.ihm.actionsPossibles(this.ileInterdite.clicable());
                this.ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
            	break;
            	
            case SAC_DE_SABLE: // clique sur la carte spéciale sac de sable
            	joueurSac = msg.getIdAventurier(); 
            	carteSac = msg.getIdCarte();
            	ihm.clickPossible(ileInterdite.assechePossibleSacDeSable());
            	break;
            	
            case SETDEPART: //Clic sur une carte Hélicoptère
                if(ileInterdite.estGagnable())
                    traiterMessage(Message.victoire());                                         //Si les conditions sont remplis, la partie est remportée
                else
                    ihm.clickPossible(ileInterdite.positionsJoueurs(msg.getIdAventurier()));    //Sinon, envoie la liste des tuiles sur lesquelles se trouvent des joueurs
                break;
                
            case SETARRIVEE:
                id = msg.getIdTuile();                          //Sauvegarde la position de départ de l'Hélicoptère
                ihm.clickPossible(ileInterdite.pasInondee());   //Envoie la liste des tuiles vers lesquelles l'Hélicoptère peut aller
                break;
                
            case HELICO:
                ileInterdite.helico(id, msg.getIdTuile(), helico);              //Déplace les joueurs utilisant l'Hélicoptère
                id = 0;
                for(int i = 0; i < ileInterdite.getAventuriers().size(); i++)   //Met à jour la position de tous les aventuriers
                    ihm.deplacerAventurier(ileInterdite.getAventuriers().get(i).toString(),ileInterdite.getAventuriers().get(i).getPosition());
                ArrayList<String> main2 = new ArrayList<>();
                ileInterdite.getMain(ileInterdite.getUtilisateur()).forEach(x -> main2.add(x.toString()));
                ihm.afficherMain(ileInterdite.getUtilisateur(),main2);
                break;
                
            case NOYADE:
                if(this.ileInterdite.nagePossible(msg.getIdAventurier()).length == 0)
                    traiterMessage(Message.defaite());                                          //S'il n'y a pas d'endroit où nager à terre, la partie est perdue
                else
                    ihm.clickPossible(this.ileInterdite.nagePossible(msg.getIdAventurier()));   //Sinon, envoie la liste des tuiles que l'aventurier peut atteindre en nageant
                ihm.noyadeEnCours();                                                            //Met la partie en pause tant que l'aventurier n'est pas sauvé
                break;
                
            case NAGE:
                ileInterdite.deplace(msg.getIdTuile());
                ihm.deplacerAventurier(ileInterdite.getAventurierEnCours().toString(),msg.getIdTuile());
                ihm.noyadeTerminée();   //Indique au jeu que la partie peut reprendre son cours
                nbInondAVenir--;
                if(nbInondAVenir == 0){
                    this.nouveauTour();
                }
                break;
                
            case TROMAIN: // activé par l'ile interdite quand une main a plus de 5 cartes           	
            	ArrayList<String> mainTropPleine = new ArrayList<>();
            	ileInterdite.getMain(msg.getIdAventurier()).forEach(x -> mainTropPleine.add(x.toString()));
                this.ihm.tropDeCarteEnMain(msg.getIdAventurier(), mainTropPleine);
                break;

            case NOUVELLE_MAIN: // après avoir fait choisir a l'utilisateur les cartes a supprimer de sa main
            	ileInterdite.majMain(msg.getIdAventurier(), msg.getDeffausseMain());
            	
                ArrayList<String> cartesNV = new ArrayList<>();
            	ileInterdite.getMain(msg.getIdAventurier()).forEach(x -> cartesNV.add(x.toString()));

                ihm.afficherMain(msg.getIdAventurier(), cartesNV);
            	
            	ihm.continuerPartie();

            	if (msg.getAction() == "Decolage") {
            		helico = true;
            		ihm.nvActionCourante(msg.getAction());
                    ihm.clickPossible(ileInterdite.positionsJoueurs(msg.getIdAventurier()));
                    
            	} else if (msg.getAction() == "Assecher") {
            		ihm.nvActionCourante(msg.getAction());
            		ihm.clickPossible(ileInterdite.assechePossibleSacDeSable());
            	}
                         
            	break;

            case DEFAITE: // en cas de defaite
                ihm.creerVueFinJeu(Boolean.FALSE);
                break;
                
            case VICTOIRE: // en cas de victoire
                ihm.creerVueFinJeu(Boolean.TRUE);
                break;
                
            default:
                if (Parameters.LOGS)
                    System.err.println("Action interdite : " + msg.getCommande().toString());
                break;
        }
    }
    
    private void nouveauTour(){
    	// initialisation pour chaque nouveau tour, du n° du tour, du nombre d'action restantes, et des différentes action possible (deplacer/assecher/...)
        this.ihm.nouveauTour(this.ileInterdite.nouveauTour());
        this.ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
        this.ihm.actionsPossibles(this.ileInterdite.clicable());
    }
}
