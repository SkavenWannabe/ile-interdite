package m2104.ile_interdite.modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;
import m2104.ile_interdite.util.Message;
import m2104.ile_interdite.util.Parameters;
import patterns.observateur.Observable;
import patterns.observateur.Observateur;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@iut2.univ-grenoble-alpes.fr>
 */
public class IleInterdite extends Observable<Message> {
    
    private int niveau;
    private int tour = 0;
    private HashMap tresors;
    private Stack paquetTresor = new Stack<CarteTresor>();
    private Stack defausseTresor = new Stack<CarteTresor>();
    private Stack paquetInonde;
    private Stack defausseInonde;
    private Grille grille;
    private ArrayList<Aventurier> aventuriers;
    private int nbActions;
    private int nbInondations;
    private HashMap specialAbysse;
    
    public IleInterdite(Observateur<Message> observateur) {
        this.addObservateur(observateur);
    }

    /*
        GET
     */
    public ArrayList<Aventurier> getAventuriers(){
        return aventuriers;
    }
    public int getNumeroAventurierEnCours() {
    	return tour % aventuriers.size();
    }
    public Aventurier getAventurierEnCours(){
        return aventuriers.get(tour % aventuriers.size());
    }
    public int getNiveau() {
        return niveau;
    }
    public int getTour() {
        return tour;
    }
    public HashMap getTresors() {
        HashMap<String,Boolean> traizor = new HashMap<>();
        
        traizor.put("calice", (Boolean) tresors.get(CarteTresor.TRESOR_CALICE));
        traizor.put("pierre", (Boolean) tresors.get(CarteTresor.TRESOR_PIERRE));
        traizor.put("statue", (Boolean) tresors.get(CarteTresor.TRESOR_STATUE));
        traizor.put("cristal", (Boolean) tresors.get(CarteTresor.TRESOR_CRISTAL));
        
        return traizor;
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
        niveau = difficulte;
        System.out.println("DIFFICULTE INITIALISEE");
        tresors = new HashMap(4);
        tresors.put(CarteTresor.TRESOR_PIERRE,false);
        tresors.put(CarteTresor.TRESOR_CALICE,false);
        tresors.put(CarteTresor.TRESOR_CRISTAL,false);
        tresors.put(CarteTresor.TRESOR_STATUE,false);
        specialAbysse = new HashMap(4);
        specialAbysse.put("TRESOR_PIERRE",0);
        specialAbysse.put("TRESOR_CALICE",0);
        specialAbysse.put("TRESOR_CRISTAL",0);
        specialAbysse.put("TRESOR_STATUE",0);
        System.out.println("TRESORS INITIALISES");
        grille = new Grille();
        paquetTresor = initPaquetTresor();
        defausseTresor = new Stack();
        paquetInonde = grille.tuillesValide();
        defausseInonde = new Stack();
        Collections.shuffle(paquetInonde);
        System.out.println("PAQUETS INITIALISES");
        for(int i = 0; i<6; i++){
            grille.changeEtat((int) paquetInonde.peek(),-1);
            defausseInonde.push(paquetInonde.pop());
        }
        System.out.println("GRILLE INITIALISEE");
        Stack roles = initRoles();
        aventuriers = new ArrayList<>();
        int p;
        for(int i = 0; i<nbJoueurs; i++){
            p = 0;
            if(roles.peek().equals("PILOTE"))
                while(!(grille.getTuille(p).getSpecial().equals("HELICO")))
                    p++;
            else
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
        System.out.println("INITIALISATION TERMINEE !");
    }

    public void resetPiocheTresor(){
        while(defausseTresor.size() > 0)                //Replace toutes les cartes de la défausse dans la pioche
            paquetTresor.push(defausseTresor.pop());
        Collections.shuffle(paquetTresor);              //Melange la nouvelle pioche ainsi formée
    }

    public void resetPiocheInonde(){
        while(defausseInonde.size() > 0)                //Replace toutes les cartes de la défausse dans la pioche
            paquetInonde.push(defausseInonde.pop());
        Collections.shuffle(paquetInonde);              //Melange la nouvelle pioche ainsi formée
    }

    public ArrayList<CarteTresor> piocheTresor(){

        boolean eau = false;
        for(int i = 0; i < 2; i ++){
            if (paquetTresor.isEmpty())                                                 //Si la pioche est vide, on la reset avant de piocher
                resetPiocheTresor();
            if (paquetTresor.peek() == CarteTresor.MONTEE_EAU){                         //Si c'est une carte 'Montée des Eaux', on :
                niveau ++;                                                               //- Augmente le cran du niveau d'eau de 1
                if (niveau == 10)                                                        //  (Et s'il atteind 10, on met fin à la partie)
                    notifierObservateurs(Message.defaite());
                defausseTresor.push(paquetTresor.pop());                                //- On met la carte dans la défausse du paquet Trésor
                eau = true;
            }
            else
                getAventurierEnCours().ajouterCarte((CarteTresor) paquetTresor.pop());  //Sinon on met la carte dans la main du joueur actuel

        }
        if (eau){
           resetPiocheInonde();                                                         //- On remélange la défausse et la pioche
           nbInondations = calculNbInondations();
        }
        
        return getAventurierEnCours().getMain();
    }

    public void mainPleine(){
        if(getAventurierEnCours().getMain().size() > 5)
            notifierObservateurs(Message.tromain());
    }
    
    public int calculNbInondations(){
        
        int j;
        switch(niveau){               //Défini le nombre de carte à piocher en fonction du niveau d'eau
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
    
    public int piocheInonde(){

        if (paquetInonde.isEmpty())                                      //Si la pioche est vide, la réinitialise
            resetPiocheInonde();

        int id = (int) paquetInonde.pop();                               //Pioche la position de la tuile a inonder
            
        grille.changeEtat(id, -1);                                       //Change l'état de la tuile à inonder
        
        if(grille.getTuille(id).getEtat() == Etat.ABYSSE){
            String spec = grille.getTuille(id).getSpecial();
            
            if(spec.equals("HELICO")){                                   //Si la tuile sombre dans l'abysse et était l'héliport, la partie est perdue
                notifierObservateurs(Message.defaite());
            }
            else{
                if(spec.equals("TRESOR_PIERRE") || spec.equals("TRESOR_CALICE") || spec.equals("TRESOR_STATUE") || spec.equals("TRESOR_CRISTAL")){
                        int a = (int) specialAbysse.get(spec)+1;
                        if (a == 2 && !((boolean) tresors.get(CarteTresor.valueOf(spec))))
                            notifierObservateurs(Message.defaite());    //Si les deux tuille d'un trésor non encore possédé sombre dans l'abysse, la partie est perdue
                        else
                            specialAbysse.replace(spec,a);
                }
                for(int k = 0; k < aventuriers.size(); k++){
                    if(aventuriers.get(k).getPosition() == id){         //Si un aventurier est sur la tuille qui tombe dans l'abysse ...
                        notifierObservateurs(Message.noyade(k));        //On prévient le contrôleur d'une potentiel noyade
                    }
                }
            }
        }
        else{
            defausseInonde.push(id);                                    //Si la tuille ne sombre pas dans l'abysse, on ajoute sa position dans la défausse
        }
        
        nbInondations--;
        return id;
    }

    public void asseche(int position){
        grille.changeEtat(position, 1); //Change l'état de la tuile à assécher
        nbActions--;                    //Réduit le compteur d'action de 1
    }

    
    public void deplace(int position){
        getAventurierEnCours().changerPosition(position,grille); //Change la position de l'aventurier en cours avec sa nouvelle position
        nbActions--;                                             //Réduit le compteur d'action de 1
    }

    public void donnerTresor(int receveur, int numCarte){
        
        System.out.println("ILE : entrée dans méthode (receveur = " + receveur + ")");
        aventuriers.get(receveur).ajouterCarte(getAventurierEnCours().enleverCarte(numCarte));  //Ajoute dans la main de l'aventurier receveur la carte qu'on enlève de la main de l'aventurier donneur
        System.out.println("ILE : méthode réussie");
        nbActions--;                                                                            //Réduit le compteur d'action de 1

    }
    
    public boolean tresorPossible(){
        int positionAventurier = getAventurierEnCours().getPosition();
        
        if(grille.getTuille(positionAventurier).getSpecial() == "TRESOR_PIERRE" && !(boolean) tresors.get(CarteTresor.TRESOR_PIERRE)){
            //Si on est sur la tuile trésor ET qu'il est toujours disponible 
            int tresorPierre = 0;
            for(CarteTresor c : getAventurierEnCours().getMain()){
                if(c == CarteTresor.TRESOR_PIERRE){
                    tresorPierre++;
                }
            }
            if(tresorPierre >= 4){
                return true;
            }}
        
        else if(grille.getTuille(positionAventurier).getSpecial() == "TRESOR_CALICE" && !(boolean) tresors.get(CarteTresor.TRESOR_CALICE)){
            //Si on est sur la tuile trésor ET qu'il est toujours disponible 
            int tresorCalice = 0;
            for(CarteTresor c : getAventurierEnCours().getMain()){
                if(c == CarteTresor.TRESOR_CALICE){
                    tresorCalice++;
                }
            }
            if(tresorCalice >= 4){
                return true;
            }}
            
        else if(grille.getTuille(positionAventurier).getSpecial() == "TRESOR_CRISTAL" && !(boolean) tresors.get(CarteTresor.TRESOR_CRISTAL)){
            //Si on est sur la tuile trésor ET qu'il est toujours disponible 
            int tresorCristal = 0;
            for(CarteTresor c : getAventurierEnCours().getMain()){
                if(c == CarteTresor.TRESOR_CRISTAL){
                    tresorCristal++;
                }
            }
            if(tresorCristal >= 4){
                return true;
            }}
            
        else if(grille.getTuille(positionAventurier).getSpecial() == "TRESOR_STATUE" && !(boolean) tresors.get(CarteTresor.TRESOR_STATUE)){
            //Si on est sur la tuile trésor ET qu'il est toujours disponible 
            int tresorStatue = 0;
            for(CarteTresor c : getAventurierEnCours().getMain()){
                if(c == CarteTresor.TRESOR_STATUE){
                    tresorStatue++;
                }
            }
            if(tresorStatue >= 4){
                return true;
            } }
        return false;
  
    }

//    public void tricheTresor(){
//        getAventurierEnCours().ajouterCarte(CarteTresor.TRESOR_CALICE);
//        getAventurierEnCours().ajouterCarte(CarteTresor.TRESOR_CALICE);
//        getAventurierEnCours().ajouterCarte(CarteTresor.TRESOR_CALICE);
//        getAventurierEnCours().ajouterCarte(CarteTresor.TRESOR_CALICE);
//    }
    
    public void gagneTresor(){
        CarteTresor tresor;
        switch(grille.getTuille(getAventurierEnCours().getPosition()).getSpecial()){  //A partir de la caractéristique Spécial de la tuille, détermine le trésor à obtenir
            case "TRESOR_PIERRE":
                tresor = CarteTresor.TRESOR_PIERRE;
                break;
            case "TRESOR_CALICE":
                tresor = CarteTresor.TRESOR_CALICE;
                break;
            case "TRESOR_CRISTAL":
                tresor = CarteTresor.TRESOR_CRISTAL;
                break;
            case "TRESOR_STATUE":
                tresor = CarteTresor.TRESOR_STATUE;
                break;
            default:
                tresor = CarteTresor.HELICO;
                break;
        }
        tresors.replace(tresor, true);                  //Place la valeur boolean associé au tresor à true
        
        int k;
        for(int i = 0; i < 4; i++){                     //Supprime 4 cartes du trésor obtenu de la main du joueur
            k = 0;
            while(getAventurierEnCours().getMain().get(k) != tresor)
                k++;
            getAventurierEnCours().enleverCarte(k);
        }
        
        nbActions--;                                    //Réduit le compteur d'action de 1
    }

    public int nouveauTour(){
        tour++;                                 //Incrémente le compteur de tour
        nbInondations = calculNbInondations();  //Recalcule le nombre de carte Inondation qu'il va falloir piocher à la fin du tour
        nbActions = 3;
        return tour;
    }    
    
    public void helico(int ancien, int nouveau){

        for(int i = 0; i < aventuriers.size(); i++)
            if(aventuriers.get(i).getPosition() == ancien)
                aventuriers.get(i).changerPosition(nouveau,grille);    //Change la position de chaque aventurier se trouvant sur la case de départ de l'hélico à celle d'arrivée
    }
    

    public boolean estGagnable(){
        
        int j = 0;
        while(!(grille.getTuille(j).getSpecial().equals("HELICO")))
            j++;
        
        int k = 0;
        while(k < aventuriers.size() && aventuriers.get(k).getPosition() == j)
            k++;
       
        if(k == aventuriers.size()){
            if((boolean) tresors.get(CarteTresor.TRESOR_CALICE) &&
               (boolean) tresors.get(CarteTresor.TRESOR_PIERRE) &&
               (boolean) tresors.get(CarteTresor.TRESOR_STATUE) &&
               (boolean) tresors.get(CarteTresor.TRESOR_CRISTAL))
                    return true;
            else
                return false;
        }
        else 
            return false;
    }
    
    public int[] deplacementPossible(){
        return getAventurierEnCours().deplacementPossible(grille).stream().mapToInt(i -> i).toArray();  //Renvoie la lise des tuiles que l'aventurier en cours peut atteindre
    }
    
    public int[] nagePossible(int numAv){
        return aventuriers.get(numAv).deplacementPossible(grille).stream().mapToInt(i -> i).toArray();
    }

    public int[] assechePossible(){
        ArrayList<Integer> possibles;
        if (getAventurierEnCours().toString().equals("Pilote") || aventuriers.get(tour % aventuriers.size()).toString().equals("Plongeur")){
            Messager m = new Messager(getAventurierEnCours().getPosition());
            possibles = m.deplacementPossible(grille);
        }
        else
            possibles = getAventurierEnCours().deplacementPossible(grille);
        ArrayList<Integer> caseAssechables = new ArrayList<>();
        for(int i = 0; i < possibles.size();i++){
            if(grille.getTuille(possibles.get(i)).getEtat() == Etat.INONDE)
                caseAssechables.add(possibles.get(i));
        }
        return caseAssechables.stream().mapToInt(i -> i).toArray();
    }
    
    public int[] assechePossibleSacDeSable() {
    	ArrayList<Integer> caseAssechables = new ArrayList<>();
        for(int i = 0; i < grille.getTuilles().length;i++){
            if(grille.getTuille(i).getEtat() == Etat.INONDE)
                caseAssechables.add(i);
        }
        return caseAssechables.stream().mapToInt(i -> i).toArray();
    }
    
    public void sacDeSable(int joueur, int carte) {
        nbActions++;
        defausseTresor.push(aventuriers.get(joueur).enleverCarte(carte));
    }
    
    public ArrayList<Integer> PersonnagesProches(){
        
        ArrayList<Integer> pp = new ArrayList<>();
        
        for(int i = 0; i < aventuriers.size(); i++){
            if(i != tour % aventuriers.size() && (aventuriers.get(i).getPosition() == getAventurierEnCours().getPosition() || getAventurierEnCours().toString().equals("Messager")) )
                pp.add(i);
        }
        
        return pp;
    }
    
    public ArrayList<Boolean> clicable(){
        ArrayList<Boolean> clic = new ArrayList<>();
        
        if(deplacementPossible().length == 0)
            clic.add(false);
        else
            clic.add(true);
        
        if(assechePossible().length == 0)
            clic.add(false);
        else
            clic.add(true);
        
        if(PersonnagesProches().isEmpty())
            clic.add(false);
        else
            clic.add(true);
        
        clic.add(tresorPossible());
        
        return clic;
    }

    /*
        INITIALISATIONS SPECIFIQUES
    */
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

    /*
        TOSTRING
    */
    @Override
    public String toString() {
        String ret; //String contenant le résultat de la méthode.
        ret = "Ile interdite, tour(s) n° " + tour + ", difficulté " + niveau + "\n"; //récupération du nombre de tours et la difficulté

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
}
