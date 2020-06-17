package m2104.ile_interdite.vue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import m2104.ile_interdite.modele.CarteTresor;
import m2104.ile_interdite.modele.Grille;
import m2104.ile_interdite.util.Message;
import patterns.observateur.Observable;
import patterns.observateur.Observateur;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@iut2.univ-grenoble-alpes.fr>
 */
public class IHM extends Observable<Message> {

    private final VueInscriptionJoueurs vueInscription;
    private final HashMap<Integer, VueAventurier> vueAventuriers;

    private VueJeu jeu;

    public IHM(Observateur<Message> observateur) {
        this.addObservateur(observateur);
        this.vueAventuriers = new HashMap<>();
        this.vueInscription = new VueInscriptionJoueurs(this);
    }
    
    public void creerVuesJeu(IHM this, String[] nomAventurier, int difficulte, Grille grille, HashMap<String,Integer> aventuriers) {
    	String[] nomsJoueurs = this.vueInscription.getNomJoueurs();
    	jeu = new VueJeu(this, nomsJoueurs,nomsJoueurs.length,difficulte, grille, aventuriers);
    }
    
    public void piocheTresors(ArrayList<CarteTresor> main) {
        //TODO : Faudra faire quelque chose de cette main
    	jeu.piocheTresors();
    }
    
    public void afficherDefausse(Stack defausse) {
    	jeu.afficherDefausse(defausse);
    }
    
    public void afficherMain(int i, ArrayList<CarteTresor> carte) {
    	jeu.afficherMain(i,carte);
    }
    
    public void clickPossible(int[] tab) {
    	jeu.clickPossible(tab);
    }
    
    public void deplacerAventurier(String role, int tuile) {
    	jeu.deplacerAventurier(role, tuile);
    }
    
    public void changerEtatTuile (int tuile, String etat) {
    	jeu.changerEtatTuile(tuile, etat);
    }
    
    
    //A supprimer en même temps que la vue aventurier, utilisation du prof, pas la notre
    public void creerVuesAventuriers(String[] nomAventuriers) {
        // - le pouvoir est disponible dans le modèle
        String[] nomsJoueurs = this.vueInscription.getNomJoueurs();
        assert nomsJoueurs.length == nomAventuriers.length;
        for (int id = 0; id < nomAventuriers.length; ++id) {
            this.vueAventuriers.put(
                    id,
                    new VueAventurier(
                            this,
                            id,
                            nomsJoueurs[id],
                            nomAventuriers[id],
                            "YYY",  // TODO: à remplacer par le bon pouvoir
                            id,
                            nomAventuriers.length,
                            Color.orange,
                            Color.orange
                    )
            );
        }
    }
}
