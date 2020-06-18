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
    
    public Controleur() {
        this.ileInterdite = new IleInterdite(this);
        this.ihm = new IHM(this);
    }
//
    @Override
    public void traiterMessage(Message msg) {
        if (Parameters.LOGS) {
            System.out.println("Controleur.traiterMessage" + msg);
        }

        switch (msg.getCommande()) {
            case VALIDER_JOUEURS:
                assert msg.hasNbJoueurs();
                this.ileInterdite.initialisation(msg.getNbJoueurs(),msg.getDifficulte());
                String[] nomAventuriers = this.ileInterdite.inscrireJoueurs(msg.getNbJoueurs());
                
                System.out.println("\n--Partie initialisée !--");
                System.out.println("Nombre de joueurs : " + this.ileInterdite.getAventuriers().size());
                System.out.println("Rôles : ");
                System.out.println(this.ileInterdite.getAventuriers());
                System.out.println(ileInterdite);
                
                HashMap<String,Integer> aventuriers = new HashMap<String, Integer>();
                for (int i = 0; i < msg.getNbJoueurs(); i++) {
                	aventuriers.put(this.ileInterdite.getAventuriers().get(i).toString(), this.ileInterdite.getAventuriers().get(i).getPosition());
                }
                
                HashMap<Integer, ArrayList> mains = new HashMap<Integer, ArrayList>();
                for (int i = 0; i < msg.getNbJoueurs(); i++) {
                	ArrayList<String> cartes = new ArrayList<>();
                	ileInterdite.getMain(i).forEach(x -> cartes.add(x.toString()));
                	mains.put(i, cartes);
                }
                
                this.ihm.creerVuesJeu(nomAventuriers, msg.getDifficulte(), ileInterdite.getGrille(), aventuriers, mains);

                this.nouveauTour();
                break;
            case CHOISIR_CARTE_TRESORS:
                joueurSac = -1; carteSac = -1;
                //ileInterdite.tricheTresor();
                ihm.piocheTresors(this.ileInterdite.piocheTresor());
                //this.ileInterdite.mainPleine();
                nbInondAVenir = this.ileInterdite.getNbInondations();
                
                ArrayList<String> cartes = new ArrayList<>();
            	ileInterdite.getMain(ileInterdite.getNumeroAventurierEnCours()).forEach(x -> cartes.add(x.toString()));
                ihm.afficherMain(ileInterdite.getNumeroAventurierEnCours(), cartes);
                ihm.augmentNiveau(ileInterdite.getNiveau());
            	break;
            case CHOISIR_CARTE_INNONDE:
                System.out.println("CON : CHOISIR_CARTE_INNONDE");
                int tuile = this.ileInterdite.piocheInonde();
                System.out.println("Tuiles piochées : " + tuile);
            	ihm.changerEtatTuile(tuile, ileInterdite.getGrille().getTuille(tuile).getEtat().toString());
                if(!(ihm.getActionEnCours().equals("Noyade")))
                    nbInondAVenir--;
                System.out.println("Nb inonde à venir : " + nbInondAVenir);
                if(nbInondAVenir == 0){
                    this.nouveauTour();
                }
            	break;
            case VOIR_DEFAUSSE:
            	ihm.afficherDefausse(ileInterdite.getDefausseTresor());
            	break;
            case TEST_BOUGER:
            	joueurSac = -1; carteSac = -1;
            	ihm.clickPossible(this.ileInterdite.deplacementPossible());
            	break;
            case BOUGER:
            	ileInterdite.deplace(msg.getIdTuile());
            	ihm.deplacerAventurier(ileInterdite.getAventurierEnCours().toString(),msg.getIdTuile());
                this.ihm.actionsPossibles(this.ileInterdite.clicable());
                this.ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
            	break;
            case TEST_ASSECHER:
            	joueurSac = -1; carteSac = -1;
                ihm.clickPossible(this.ileInterdite.assechePossible());
            	break;
            case ASSECHER:
            	ileInterdite.asseche(msg.getIdTuile());
            	ihm.changerEtatTuile(msg.getIdTuile(), ileInterdite.getGrille().getTuille(msg.getIdTuile()).getEtat().toString());

            	if (joueurSac != -1 && carteSac != -1) {
            		ileInterdite.sacDeSable(joueurSac, carteSac);
            		
                	ArrayList<String> cartesJoueurSac = new ArrayList<String>();
                	ileInterdite.getMain(joueurSac).forEach(x -> cartesJoueurSac.add(x.toString()));
                	ihm.afficherMain(joueurSac, cartesJoueurSac);
                	
            		joueurSac = -1; carteSac = -1;
            	}
	        this.ihm.actionsPossibles(this.ileInterdite.clicable());
	        this.ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
            	break;
            case TEST_DONNER:
            	joueurSac = -1; carteSac = -1;
            	ihm.mainSelectionnable(ileInterdite.getNumeroAventurierEnCours());
                ihm.autreMains(ileInterdite.PersonnagesProches());
            	break;
            case DONNER:
            	ileInterdite.donnerTresor(msg.getIdAventurier(),msg.getIdCarte());
            	
            	//Afficher Main du receveur
            	ArrayList<String> cartesReceveur = new ArrayList<>();
            	ileInterdite.getMain(msg.getIdAventurier()).forEach(x -> cartesReceveur.add(x.toString()));
            	ihm.afficherMain(msg.getIdAventurier(), cartesReceveur);
            	
            	//Afficher Main du donneur
            	ArrayList<String> cartesDonneur = new ArrayList<String>();
            	ileInterdite.getMain(ileInterdite.getNumeroAventurierEnCours()).forEach(x -> cartesDonneur.add(x.toString()));
            	ihm.afficherMain(ileInterdite.getNumeroAventurierEnCours(), cartesDonneur);         
            	this.ihm.actionsPossibles(this.ileInterdite.clicable());
                ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
            	break;
            case RECUPERER_TRESOR:
            	joueurSac = -1; carteSac = -1;
            	ileInterdite.gagneTresor();
                ihm.afficherTresors(ileInterdite.getTresors());
                ArrayList<String> main = new ArrayList<>();
            	ileInterdite.getMain(ileInterdite.getNumeroAventurierEnCours()).forEach(x -> main.add(x.toString()));
                ihm.afficherMain(ileInterdite.getNumeroAventurierEnCours(),main);
                this.ihm.actionsPossibles(this.ileInterdite.clicable());
                this.ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
            	break;
            case SAC_DE_SABLE:
            	joueurSac = msg.getIdAventurier(); 
            	carteSac = msg.getIdCarte();
            	ihm.clickPossible(ileInterdite.assechePossibleSacDeSable());
            	break;
            case NOYADE:
                if(this.ileInterdite.nagePossible(msg.getIdAventurier()).length == 0)
                    traiterMessage(Message.defaite());
                else
                    ihm.clickPossible(this.ileInterdite.nagePossible(msg.getIdAventurier()));
                ihm.noyadeEnCours();
                break;
            case NAGE:
                ileInterdite.deplace(msg.getIdTuile());
                ihm.deplacerAventurier(ileInterdite.getAventurierEnCours().toString(),msg.getIdTuile());
                ihm.noyadeTerminée();
                nbInondAVenir--;
                if(nbInondAVenir == 0){
                    this.nouveauTour();
                }
                break;
            case TROMAIN:
                //this.ihm.tropDeCarteEnMain();
                break;
            case DEFAITE:
                if(Parameters.LOGS){System.out.println("CON : OH ! Perdu !");}
                break;
            case VICTOIRE:
                //C'est gagné !!
                break;
            default:
                if (Parameters.LOGS) {
                    System.err.println("Action interdite : " + msg.getCommande().toString());
                }
        }
    }
    
    private void nouveauTour(){
        this.ihm.nouveauTour(this.ileInterdite.nouveauTour());
        this.ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
        this.ihm.actionsPossibles(this.ileInterdite.clicable());
    }
}
