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

    @Override
    public void traiterMessage(Message msg) {
        if (Parameters.LOGS) {
            System.out.println("Controleur.traiterMessage" + msg);
        }

        switch (msg.getCommande()) {
            case VALIDER_JOUEURS:
                assert msg.hasNbJoueurs();
                String[] nomAventuriers = this.ileInterdite.inscrireJoueurs(msg.getNbJoueurs());
                this.ileInterdite.initialisation(msg.getNbJoueurs());
                System.out.println("\n--Partie initialisée !--");
                System.out.println("Nombre de joueurs : " + this.ileInterdite.getAventuriers().size());
                System.out.println("Rôles : ");
                System.out.println(this.ileInterdite.getAventuriers());
                this.ihm.creerVuesAventuriers(nomAventuriers);
                break;
            default:
                if (Parameters.LOGS) {
                    System.err.println("Action interdite : " + msg.getCommande().toString());
                }
        }
    }
}
