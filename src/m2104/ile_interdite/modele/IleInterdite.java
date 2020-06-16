package m2104.ile_interdite.modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;
import m2104.ile_interdite.util.Message;
import patterns.observateur.Observable;
import patterns.observateur.Observateur;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@iut2.univ-grenoble-alpes.fr>
 */
public class IleInterdite extends Observable<Message> {
    
    private int diff;
    private int tour = 0;
    private HashMap tresors = new HashMap(4);
    private Stack paquetTresor = new Stack<CarteTresor>();
    private Stack defausseTresor = new Stack<CarteTresor>();
    private Stack paquetInonde = new Stack();
    private Stack defausseInonde = new Stack();
    private Grille grille = new Grille();
    private ArrayList<Aventurier> aventuriers = new ArrayList<>();
    private int nbActions;
    private int nbInondations;
    private HashMap specialAbysse = new HashMap(4);
    
    public IleInterdite(Observateur<Message> observateur) {
        this.addObservateur(observateur);
    }

    /*
        GET
     */
    public ArrayList<Aventurier> getAventuriers(){
        return aventuriers;
    }
    public int getDiff() {
        return diff;
    }
    public int getTour() {
        return tour;
    }
    public HashMap getTresors() {
        return tresors;
    }
    public Stack getPaquetTresor() {
        return paquetTresor;
    }
    public Stack getDefausseTresor() {
        return defausseTresor;
    }
    public Stack getPaquetInonde() {
        return paquetInonde;
    }
    public Stack getDefausseInonde() {
        return defausseInonde;
    }
    public Grille getGrille() {
        return grille;
    }
    public int getNbActionsRestantes(){
        return nbActions;
    }
    public int getNbInondations(){
        return nbInondations;
    }
    public ArrayList<CarteTresor> getMain(int numAv){
        return aventuriers.get(numAv).getMain();
    }

    /*
        METHODES
    */
    public String[] inscrireJoueurs(int nbJoueurs) {
        // TODO: à remplacer par une réelle assignation des types d'aventuriers
        String[] nomAventuriers = new String[nbJoueurs];
        for(int i = 0; i < nbJoueurs; i++)
            nomAventuriers[i] = aventuriers.get(i).toString();
        return nomAventuriers;
    }
    
    public void initialisation(int nbJoueurs, int difficulte){

        System.out.println("INITIALISATION ...");
        diff = difficulte;
        System.out.println("DIFFICULTE INITIALISEE");
        tresors.put(CarteTresor.TRESOR_PIERRE,false);
        tresors.put(CarteTresor.TRESOR_CALICE,false);
        tresors.put(CarteTresor.TRESOR_CRISTAL,false);
        tresors.put(CarteTresor.TRESOR_STATUE,false);
        specialAbysse.put(CarteTresor.TRESOR_PIERRE,0);
        specialAbysse.put(CarteTresor.TRESOR_CALICE,0);
        specialAbysse.put(CarteTresor.TRESOR_CRISTAL,0);
        specialAbysse.put(CarteTresor.TRESOR_STATUE,0);
        System.out.println("TRESORS INITIALISES");
        paquetTresor = initPaquetTresor();
        paquetInonde = grille.tuillesValide();
        Collections.shuffle(paquetInonde);
        System.out.println("PAQUETS INITIALISES");
        for(int i = 0; i<6; i++){
            grille.changeEtat((int) paquetInonde.peek(),-1);
            defausseInonde.push(paquetInonde.pop());
        }
        System.out.println("GRILLE INITIALISEE");
        Stack roles = initRoles();
        int p;
        for(int i = 0; i<nbJoueurs; i++){
            p = 0;
            while(!(grille.getTuille(p).getSpecial().equals(roles.peek())))
                p++;
            switch((String) roles.pop()) {
                case "MESSAGER":
                    aventuriers.add(new Messager(p));
                    break;
                case "PLONGEUR":
                    aventuriers.add(new Plongeur(p));
                    break;
                case "PILOTE":
                    aventuriers.add(new Pilote(p));
                    break;
                case "INGENIEUR":
                    aventuriers.add(new Ingenieur(p));
                    break;
                case "NAVIGATEUR":
                    aventuriers.add(new Navigateur(p));
                    break;
                case "EXPLORATEUR":
                    aventuriers.add(new Explorateur(p));
                    break;
            }
            for(int j = 0; j<2; j++){
                if(paquetTresor.peek() == CarteTresor.MONTEE_EAU){
                    defausseTresor.push(paquetTresor.pop());
                    j--;
                }
                else
                    aventuriers.get(i).ajouterCarte((CarteTresor) paquetTresor.pop());
            }
        }
        System.out.println("ROLES INITIALISES");
        System.out.println("MAINS DE DEPART INITIALISEES");
        resetPiocheTresor();
        nbActions = 3;
        System.out.println("INITIALISATION TERMINEE !");
    }

    public void resetPiocheTresor(){
        for(int i = 0; i < defausseTresor.size(); i++)  //Replace toutes les cartes de la défausse dans la pioche
            paquetTresor.push(defausseTresor.pop());
        Collections.shuffle(paquetTresor);              //Melange la nouvelle pioche ainsi formée
    }

    public void resetPiocheInonde(){
        for(int i = 0; i < defausseInonde.size(); i++)  //Replace toutes les cartes de la défausse dans la pioche
            paquetInonde.push(defausseInonde.pop());
        Collections.shuffle(paquetInonde);              //Melange la nouvelle pioche ainsi formée
    }

    public void piocheTresor(){

        boolean eau = false;
        for(int i = 0; i < 2; i ++){
            if (paquetTresor.isEmpty())                                                                     //Si la pioche est vide, on la reset avant de piocher
                resetPiocheTresor();
            if (paquetTresor.peek() == CarteTresor.MONTEE_EAU){                                             //Si c'est une carte 'Montée des Eaux', on :
                diff ++;                                                                                    //- Augmente le cran du niveau d'eau de 1
                if (diff == 10)                                                                             //  (Et s'il atteind 10, on met fin à la partie)
                    notifierObservateurs(Message.defaite());
                defausseTresor.push(paquetTresor.pop());                                                    //- On met la carte dans la défausse du paquet Trésor
                eau = true;
            }
            else
                aventuriers.get(tour % aventuriers.size()).ajouterCarte((CarteTresor) paquetTresor.pop());  //Sinon on met la carte dans la main du joueur actuel

        }
        if (eau){
           resetPiocheInonde();                                                                             //- On remélange la défausse et la pioche
           nbInondations = setNbInondations();
        }
    }

    public int setNbInondations(){
        
        int j;
        switch(diff){               //Défini le nombre de carte à piocher en fonction du niveau d'eau
            case 1: case 2:
                j = 2; break;
            case 3: case 4: case 5: 
                j = 3; break;
            case 6: case 7:
                j = 4; break;
            case 8: case 9:
                j = 5; break;
            default:
                j = 0; break;
        }
        
        return j;
    }
    
    public void piocheInonde(){

        if (paquetInonde.isEmpty())                                 //Si la pioche est vide, la réinitialise
            resetPiocheInonde();

        int id = (int) paquetInonde.pop();                          //Pioche la position de la tuile a inonder
        grille.changeEtat(id, -1);                                  //Change l'état de la tuile à inonder

        
        if(grille.getTuille(id).getEtat() == Etat.ABYSSE){
            String spec = grille.getTuille(id).getSpecial();
            if(spec.equals("HELICO"))                               //Si la tuile sombre dans l'abysse et était l'héliport, la partie est perdue
                notifierObservateurs(Message.defaite());
            else{
                if(spec.equals("TRESOR_PIERRE") || spec.equals("TRESOR_CALICE") || spec.equals("TRESOR_STATUE") || spec.equals("TRESOR_CRISTAL")){
                        int a = (int) specialAbysse.get(spec)+1;
                        if (a == 2 && !((boolean) tresors.get(spec)))
                            notifierObservateurs(Message.defaite());
                        else
                            specialAbysse.replace(spec,a);
                }
                for(int k = 0; k < aventuriers.size(); k++){
                    if(aventuriers.get(k).getPosition() == id){     //Si l'aventurier est sur la tuille qui tombe dans l'abysse ...
                        notifierObservateurs(Message.noyade(id));
                    }
                }
            }
        }
        else
            defausseInonde.push(id);
        
        nbInondations--;
    }

    public void asseche(int position){
        grille.changeEtat(position, 1); //Change l'état de la tuile à assécher
        nbActions--;                    //Réduit le compteur d'action de 1
    }

    public void deplace(int position){
        aventuriers.get(tour % aventuriers.size()).changerPosition(position); //Change la position de l'aventurier en cours avec sa nouvelle position
        nbActions--;                                                          //Réduit le compteur d'action de 1
    }

    public void donnerTresor(Aventurier receveur, int numCarte){
        receveur.ajouterCarte(aventuriers.get(tour % aventuriers.size()).enleverCarte(numCarte));   //Ajoute dans la main de l'aventurier receveur la carte qu'on enlève de la main de l'aventurier donneur
        nbActions--;                                                                                //Réduit le compteur d'action de 1
    }

    public void gagneTresor(CarteTresor tresor){
        tresors.replace(tresor, true);  //Place la valeur bnoolean associé au tresor à true
        nbActions--;                    //Réduit le compteur d'action de 1
    }

    public void nouveauTour(){
        tour++;                             //Incrémente le compteur de tour
        nbInondations = setNbInondations(); //Recalcule le nombre de carte Inondation qu'il va falloir piocher à la fin du tour
    }    
    
    public void helico(int ancien, int nouveau){
        for(int i = 0; i < aventuriers.size(); i++)
            if(aventuriers.get(i).getPosition() == ancien)
                aventuriers.get(i).changerPosition(nouveau);    //Change la position de chaque aventurier se trouvant sur la case de départ de l'hélico à celle d'arrivée
    }

    public int[] deplacementPossible(){
        return aventuriers.get(tour % aventuriers.size()).deplacementPossible(grille);  //Renvoie la lise des tuiles que l'aventurier en cours peut atteindre
    }

    //to Do : Methode PersonnageProche à faire (VINC au boulot)

    public Stack<CarteTresor> initPaquetTresor (){

        Stack paquet = new Stack<CarteTresor>();
        for(int i = 0; i<5; i++)
            paquet.push(CarteTresor.TRESOR_CALICE);
        for(int i = 0; i<5; i++)
            paquet.push(CarteTresor.TRESOR_PIERRE);
        for(int i = 0; i<5; i++)
            paquet.push(CarteTresor.TRESOR_CRISTAL);
        for(int i = 0; i<5; i++)
            paquet.push(CarteTresor.TRESOR_STATUE);
        for(int i = 0; i<3; i++)
            paquet.push(CarteTresor.HELICO);
        for(int i = 0; i<3; i++)
            paquet.push(CarteTresor.MONTEE_EAU);
        for(int i = 0; i<2; i++)
            paquet.push(CarteTresor.SAC_SABLE);
        Collections.shuffle(paquet);
        return paquet;
    }

    public Stack<String> initRoles(){
        Stack roles = new Stack();
        roles.push("PLONGEUR");
        roles.push("MESSAGER");
        roles.push("EXPLORATEUR");
        roles.push("INGENIEUR");
        roles.push("PILOTE");
        roles.push("NAVIGATEUR");
        Collections.shuffle(roles);
        return roles;
    }


    @Override
    public String toString() {
        String ret; //String contenant le résultat de la méthode.
        ret = "Ile interdite, tour(s) n° " + tour + ", difficulté " + diff + "\n"; //récupération du nombre de tours et la difficulté

        //création de la partie Grille de ret
        for(int i = 0; i < grille.getTuilles().length; i++) {
            if(i%6 == 0)
                ret += "\n";

            if(grille.getTuilles()[i].getEtat() == Etat.ABYSSE)
                ret += "   ";

            else if (grille.getTuilles()[i].getSpecial() == "HELICO") {
                ret += " H ";
            }

            else if (grille.getTuilles()[i].getSpecial() == "TRESOR_PIERRE" ||
                    grille.getTuilles()[i].getSpecial() == "TRESOR_CALICE" ||
                    grille.getTuilles()[i].getSpecial() == "TRESOR_CRISTAL" ||
                    grille.getTuilles()[i].getSpecial() == "TRESOR_STATUE") {
                ret += " T ";
            }

            else {
                int j = 0;
                while(j < aventuriers.size() && i != aventuriers.get(j).getPosition()) {
                    j++;
                }
                if(j < aventuriers.size()) {
                    ret += " " + aventuriers.get(j).toString().substring(0,1) + " ";
                } else {
                    if (grille.getTuilles()[i].getEtat() == Etat.INONDE)
                        ret += " ~ ";
                    else
                        ret += " █ ";
                }
            }

        }
        return ret;
    }
    
    public void partieGagnable(){
        boolean helico = true;
        // On vérifie que l'Héliport ne soit pas coulé (déjà fait autre part du coup on le laisse ?)
        for(int i = 0; i < grille.getTuilles().length; i++){
            if (grille.getTuilles()[i].getSpecial() == "HELICO" && grille.getTuille(i).getEtat()== Etat.ABYSSE){
                helico = false;
            }
        }
        //On vérifie que chaque case qui possède encore un trésor ne soit pas coulée
        boolean tresorPierre = true;
        boolean tresorCalice = true;
        boolean tresorCristal = true;
        boolean tresorStatue = true;
        for (int i = 0; i < grille.getTuilles().length; i++){
            if(grille.getTuilles()[i].getSpecial() == "TRESOR_PIERRE"  && grille.getTuille(i).getEtat()== Etat.ABYSSE ){ //Ajout de la présence de trésor ?
                tresorPierre = false;
            }else if(grille.getTuilles()[i].getSpecial() == "TRESOR_CALICE"  && grille.getTuille(i).getEtat()== Etat.ABYSSE){
                tresorCalice = false;
            }else if(grille.getTuilles()[i].getSpecial() == "TRESOR_CRISTAL"  && grille.getTuille(i).getEtat()== Etat.ABYSSE){
                tresorCristal = false;
            }else if(grille.getTuilles()[i].getSpecial() == "TRESOR_STATUE"  && grille.getTuille(i).getEtat()== Etat.ABYSSE){
                tresorStatue = false;
            }
        }
        //La vérification que personne ne se soit noyé et déjà vérifié dans piocheInnonde()
        
        //Le niveau d'eau ne doit pas dépasser 10
        boolean eauPasTropHaute = this.diff < 10;
        
       
        if (!helico && !tresorPierre && !tresorCalice && !tresorCristal && !tresorStatue && !eauPasTropHaute){
            notifierObservateurs(Message.defaite());
        } 
    }
    
}
