package m2104.ile_interdite.vue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import m2104.ile_interdite.modele.CarteTresor;
import m2104.ile_interdite.modele.Grille;
import m2104.ile_interdite.util.Message;
import m2104.ile_interdite.util.Parameters;
import patterns.observateur.Observable;
import patterns.observateur.Observateur;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@iut2.univ-grenoble-alpes.fr>
 */
public class IHM extends Observable<Message> {

    private final VueInscriptionJoueurs vueInscription;
    private VueFinJeu finJeu;

    private VueJeu jeu;
    private VueTropDeCarte tropDeCarte;

    public IHM(Observateur<Message> observateur) {
        this.addObservateur(observateur);
        this.vueInscription = new VueInscriptionJoueurs(this);
    }
    
    public void creerVuesJeu(IHM this, String[] nomAventurier, int difficulte, Grille grille, HashMap<String,Integer> aventuriers, HashMap<Integer, ArrayList> mains, ArrayList<Integer> pso) {
    	String[] nomsJoueurs = this.vueInscription.getNomJoueurs();
    	jeu = new VueJeu(this, nomsJoueurs,nomsJoueurs.length,difficulte, grille, aventuriers, mains,pso);
    }
    
    public void creerVueFinJeu(IHM this,Boolean win){
        jeu.finDePartie();
        finJeu = new VueFinJeu(win,this);
    }
    
    public void piocheTresors(ArrayList<CarteTresor> main) {
        //TODO : Faudra faire quelque chose de cette main
    	jeu.piocheTresors();
    }
    
    public VueJeu getVueJeu(){
        return jeu;
    }
    
    public void afficherDefausse(Stack defausse) {
    	jeu.afficherDefausse(defausse);
    }
    
    public void afficherMain(int i, ArrayList<String> carte) {
    	jeu.afficherMain(i,carte);
    }
    
    public void afficherTresors(HashMap traizor){
        jeu.afficherTresors(traizor);
    }
    
    public void mainSelectionnable(int numero) {
    	jeu.mainSelectionnable(numero);
    }
    
    public void autreMains(ArrayList persoProches){
        jeu.autreMains(persoProches);
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
    
    public void nouveauTour(int tour) {
    	jeu.nouveauTour(tour);
    }
    
    public void augmentNiveau(int niveau){
        jeu.augmentNiveau(niveau);
    }
    
    public void nbActionsRestantes(int action) {
    	jeu.nbActionsRestantes(action);
    }
    
    public void actionsPossibles(ArrayList<Boolean> actionsPossibles){
        jeu.actionsPossibles(actionsPossibles);
    }
    
    public void tropDeCarteEnMain(int idJoueur, ArrayList<String> main) {
    	jeu.tropDeCarte(true);
    	tropDeCarte = new VueTropDeCarte(this,idJoueur, main);
    }
    
    public void nvActionCourante(String action) {
    	jeu.nvActionCourante(action);
    }
    
    public void continuerPartie() {
    	jeu.tropDeCarte(false);
    	tropDeCarte.desactiver();
    }

    public void noyadeEnCours(){
        jeu.noyadeEnCours();
    }
    public void noyadeTerminée(){
        jeu.noyadeFinie();
    }
    
    public String getActionEnCours(){
        return jeu.getActionEnCours();
    }
    
}
