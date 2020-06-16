package m2104.ile_interdite.controleur;

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
                
                this.ileInterdite.nouveauTour();
                this.ihm.creerVuesJeu(nomAventuriers, msg.getDifficulte(), ileInterdite.getGrille());
                for (int i = 0; i < msg.getNbJoueurs(); i++) {
                	this.ihm.afficherMain(i,this.ileInterdite.getMain(i));
                }
                break;
            case CHOISIR_CARTE_TRESORS:
            	this.ileInterdite.piocheTresor();
            	ihm.piocheTresors();
            	break;
            case CHOISIR_CARTE_INNONDE:
            	this.ileInterdite.piocheInonde();
            	ihm.piocheInnondation();
            	break;
            case VOIR_DEFAUSSE:
            	ihm.afficherDefausse(ileInterdite.getDefausseTresor());
            	break;
            case TEST_BOUGER:
            	ihm.deplacementPossible(this.ileInterdite.deplacementPossible());
            	break;
            case BOUGER:
            	ileInterdite.deplace(msg.getIdTuile());
            	break;
            case TEST_ASSECHER:
            	//ihm.assechePossible(this.ileInterdite.deplacementPossible());
            	break;
            case ASSECHER:
            	ileInterdite.asseche(msg.getIdTuile());
            	break;
            case DONNER:
            	ileInterdite.donnerTresor(msg.getIdAventurier(),msg.getIdCarte());
            	break;
            case RECUPERER_TRESOR:
            	ileInterdite.gagneTresor(msg.getIdTuile());
            	break;
            default:
                if (Parameters.LOGS) {
                    System.err.println("Action interdite : " + msg.getCommande().toString());
                }
        }
    }
}
