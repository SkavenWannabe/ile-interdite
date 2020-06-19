package m2104.ile_interdite.util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author IUT2-Dept Info
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Utils.Commandes commande;
    private final Integer idAventurier;
    private final Integer idCarte;
    private final Utils.Tresor tresor;
    private final Integer idTuile;
    private final Integer nbJoueurs;
    private final Integer difficulte;
    private final ArrayList<Integer> deffausse;
    
    private Message(Utils.Commandes commande, Integer idAventurier, Integer idCarte, Utils.Tresor tresor, Integer idTuile, Integer nbJoueurs, Integer difficulte, ArrayList<Integer> deffausse) {
        this.commande = commande;
        this.idAventurier = idAventurier;
        this.idCarte = idCarte;
        this.tresor = tresor;
        this.idTuile = idTuile;
        this.nbJoueurs = nbJoueurs;
        this.difficulte = difficulte;
        this.deffausse = deffausse;
    }

    /**
     *
     * @param nbJoueurs
     * @return un nouveau {@link #Message} pour la commande {@link m2104.ile_interdite.util.Utils.Commandes#VALIDER_JOUEURS}
     */
    public static Message validerJoueurs(int nbJoueurs, int difficulte) {
        return new Message(Utils.Commandes.VALIDER_JOUEURS, null, null, null, null, nbJoueurs, difficulte, null);
    }

    /**
     *
     * @param idAventurier
     * @return un nouveau {@link #Message} pour la commande {@link m2104.ile_interdite.util.Utils.Commandes#BOUGER}
     */
    public static Message testBouger() {
        return new Message(Utils.Commandes.TEST_BOUGER, null, null, null, null, null, null, null);
    }

    public static Message bouger(int tuile) {
        return new Message(Utils.Commandes.BOUGER, null, null, null, tuile, null, null, null);
    }

    /**
     *
     * @param idAventurier
     * @return un nouveau {@link #Message} pour la commande {@link m2104.ile_interdite.util.Utils.Commandes#ASSECHER}
     */
    public static Message testAssecher() {
        return new Message(Utils.Commandes.TEST_ASSECHER, null, null, null, null, null, null, null);
    }
    public static Message assecher(int tuile) {
        return new Message(Utils.Commandes.ASSECHER, null, null, null, tuile, null, null, null);
    }

    /**
     *
     * @param idAventurier
     * @return un nouveau {@link #Message} pour la commande {@link m2104.ile_interdite.util.Utils.Commandes#DONNER}
     */
    public static Message testDonner() {
        return new Message(Utils.Commandes.TEST_DONNER, null, null, null, null, null, null, null);
    }
    public static Message donner(int idAventurier, int idCarte) {
        return new Message(Utils.Commandes.DONNER, idAventurier, idCarte, null, null, null, null, null);
    }

    /**
     *
     * @param idAventurier
     * @return un nouveau {@link #Message} pour la commande {@link m2104.ile_interdite.util.Utils.Commandes#RECUPERER_TRESOR}
     */
    public static Message recupererTresor() {
        return new Message(Utils.Commandes.RECUPERER_TRESOR, null, null, null, null, null, null, null);
    }

    /**
     *
     * @param idAventurier
     * @return un nouveau {@link #Message} pour la commande {@link m2104.ile_interdite.util.Utils.Commandes#TERMINER}
     */
    public static Message terminer(int idAventurier) {
        return new Message(Utils.Commandes.TERMINER, idAventurier, null, null, null, null, null, null);
    }

    /**
     *
     * @param idAventurier
     * @return un nouveau {@link #Message} pour la commande {@link m2104.ile_interdite.util.Utils.Commandes#RECEVOIR}
     */
    public static Message recevoir(int idAventurier) {
        return new Message(Utils.Commandes.RECEVOIR, idAventurier, null, null, null, null, null, null);
    }

    /**
     *
     * @return un nouveau {@link #Message} pour la commande {@link m2104.ile_interdite.util.Utils.Commandes#CHOISIR_CARTE}
     */
    public static Message choisirCarteInnondation() {
        return new Message(Utils.Commandes.CHOISIR_CARTE_INNONDE, null, null, null, null, null, null, null);
    }
    public static Message choisirCarteTresors() {
        return new Message(Utils.Commandes.CHOISIR_CARTE_TRESORS, null, null, null, null, null, null, null);
    }
    /**
     *
     * @return un nouveau {@link #Message} pour la commande {@link m2104.ile_interdite.util.Utils.Commandes#CHOISIR_TUILE}
     */
    public static Message choisirTuile() {
        return new Message(Utils.Commandes.CHOISIR_TUILE, null, null, null, null, null, null, null);
    }

    /**
     *
     * @param idAventurier
     * @return un nouveau {@link #Message} pour la commande {@link m2104.ile_interdite.util.Utils.Commandes#DEPLACER}
     */
    public static Message deplacer(int idAventurier) {
        return new Message(Utils.Commandes.DEPLACER, idAventurier, null, null, null, null, null, null);
    }

    /**
     *
     * @return un nouveau {@link #Message} pour la commande {@link m2104.ile_interdite.util.Utils.Commandes#VOIR_DEFAUSSE}
     */
    public static Message voirDefausse() {
        return new Message(Utils.Commandes.VOIR_DEFAUSSE, null, null, null, null, null, null, null);
    }

    public static Message noyade(int idAventurier){
        return new Message(Utils.Commandes.NOYADE, idAventurier, null, null, null, null, null, null);
    }
    
    public static Message sacDeSable(int idAventurier, int idCarte){
        return new Message(Utils.Commandes.SAC_DE_SABLE, idAventurier, idCarte, null, null, null, null, null);
    }

    public static Message nage(int tuile) {
        return new Message(Utils.Commandes.NAGE, null, null, null, tuile, null, null, null);
    }

    public static Message nvMain(int idAventurier, ArrayList<Integer> deffausse){
        return new Message(Utils.Commandes.NOUVELLE_MAIN, idAventurier, null, null, null, null, null, deffausse);
    }
    

    public static Message tromain(int idAventurier){
        return new Message(Utils.Commandes.TROMAIN, idAventurier, null, null, null, null, null, null);

    }

    public static Message setDepart(){
        return new Message(Utils.Commandes.SETDEPART, null, null, null, null, null, null, null);
    }

    public static Message setArrivee(){
        return new Message(Utils.Commandes.SETARRIVEE, null, null, null, null, null, null, null);
    }

    public static Message helico(){
        return new Message(Utils.Commandes.HELICO, null, null, null, null, null, null, null);
    }
    
    public static Message defaite(){
        return new Message(Utils.Commandes.DEFAITE, null, null, null, null, null, null, null);
    }
    
    public static Message victoire(){
        return new Message(Utils.Commandes.VICTOIRE, null, null, null, null, null, null, null);
    }
    
    /**
     * @return the commande
     */
    public Boolean hasCommande() {
        return commande != null;
    }
    public Utils.Commandes getCommande() {
        return commande;
    }

    /**
    *
    * @return the difficulte
    */
    public Integer getDifficulte() {
    	return difficulte+1;
    }
    
    /**
     * @return the idAventurier`
     */
    public Boolean hasIdAventurier() {
        return idAventurier != null;
    }
    public Integer getIdAventurier() {
        return idAventurier;
    }

    /**
     * @return the idCarte
     */
    public Boolean hasIdCarte() {
        return idCarte != null;
    }
    public Integer getIdCarte() {
        return idCarte;
    }

    /**
     * @return the tresor
     */
    public Boolean hasTresor() {
        return tresor != null;
    }
    public Utils.Tresor getTresor() {
        return tresor;
    }

    /**
     *
     * @return the nbJoueurs
     */
    public Boolean hasNbJoueurs() {
        return nbJoueurs != null;
    }
    public Integer getNbJoueurs() {
        return nbJoueurs;
    }

    /**
     * @return the idTuile
     */
    public Boolean hasIdTuile() {
        return idTuile != null;
    }
    public Integer getIdTuile() {
        return idTuile;
    }
    
    public ArrayList<Integer> getDeffausseMain(){
    	return deffausse;
    }
    

    @Override
    public String toString() {
        String txt = "";
        txt += commande.toString() + " ";
        if (hasIdAventurier()) {
            txt += " aventurier=" + idAventurier;
        }
        if (hasIdCarte()) {
            txt += " carte=" + idCarte;
        }
        if (hasIdTuile()) {
            txt += " tuile=" + idTuile;
        }
        if (hasTresor()) {
            txt += " tresor=" + tresor.toString();
        }
        return txt;
    }
}
