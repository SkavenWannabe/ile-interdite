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
                	ArrayList<String> cartes = new ArrayList<String>();
                	ileInterdite.getMain(i).forEach(x -> cartes.add(x.toString()));
                	mains.put(i, cartes);
                }
                
                this.ihm.creerVuesJeu(nomAventuriers, msg.getDifficulte(), ileInterdite.getGrille(), aventuriers, mains);

                this.nouveauTour();
                break;
            case CHOISIR_CARTE_TRESORS:
            	ihm.piocheTresors(this.ileInterdite.piocheTresor());
                this.ileInterdite.mainPleine();
                nbInondAVenir = this.ileInterdite.getNbInondations();
            	break;
            case CHOISIR_CARTE_INNONDE:
            	int tuile = this.ileInterdite.piocheInonde();
            	ihm.changerEtatTuile(tuile, ileInterdite.getGrille().getTuille(tuile).getEtat().toString());
                nbInondAVenir--;
                if(nbInondAVenir == 0){
                    this.nouveauTour();
                }
            	break;
            case VOIR_DEFAUSSE:
            	ihm.afficherDefausse(ileInterdite.getDefausseTresor());
            	break;
            case TEST_BOUGER:
            	ihm.clickPossible(this.ileInterdite.deplacementPossible());
            	break;
            case BOUGER:
            	ileInterdite.deplace(msg.getIdTuile());
            	ihm.deplacerAventurier(ileInterdite.getAventurierEnCours().toString(),msg.getIdTuile());
                //this.ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
                //this.ihm.actionsPossibles(this.ileInterdite.clicable());
            	break;
            case TEST_ASSECHER:
                ihm.clickPossible(this.ileInterdite.assechePossible());
            	break;
            case ASSECHER:
            	ileInterdite.asseche(msg.getIdTuile());
            	ihm.changerEtatTuile(msg.getIdTuile(), ileInterdite.getGrille().getTuille(msg.getIdTuile()).getEtat().toString());
                //this.ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
            	break;
            case DONNER:
            	ileInterdite.donnerTresor(msg.getIdAventurier(),msg.getIdCarte());
                //this.ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
            	break;
            case RECUPERER_TRESOR:
            	ileInterdite.gagneTresor(msg.getIdTuile());
                //this.ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
            	break;
            default:
                if (Parameters.LOGS) {
                    System.err.println("Action interdite : " + msg.getCommande().toString());
                }
        }
    }
    
    public void nouveauTour(){
        //this.ihm.nouveauTour(this.ileInterdite.nouveauTour());
        //this.ihm.nbActionsRestantes(this.ileInterdite.getNbActionsRestantes());
        //this.ihm.actionsPossibles(this.ileInterdite.clicable());
    }
}
