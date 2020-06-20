package m2104.ile_interdite.modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
    private int utilisateur;
    
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
    public int getUtilisateur(){
        return utilisateur;
    }
    public ArrayList<Integer> getPositions(){
        ArrayList<Integer> positions = new ArrayList<>();
        for(int y = 0; y < aventuriers.size(); y++)
            positions.add(aventuriers.get(y).getPosition());
        return positions;
    }
    
    
    /*
        INITIALISATIONS
    */
    public String[] inscrireJoueurs(int nbJoueurs) {            //Créé une liste des noms des classes d'aventuriers
        String[] nomAventuriers = new String[nbJoueurs];
        for(int i = 0; i < nbJoueurs; i++)
            nomAventuriers[i] = aventuriers.get(i).toString();  
        return nomAventuriers;
    }
    
    public void initialisation(int nbJoueurs, int niveauInit){

        System.out.println("INITIALISATION ...");
        tour=0;
        niveau = niveauInit;
        System.out.println("DIFFICULTE INITIALISEE");                           //Defini le niveau d'eau initiale
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
        System.out.println("TRESORS INITIALISES");                              //Créé les listes en lien avec les Trésors
        grille = new Grille();
        paquetTresor = initPaquetTresor();
        defausseTresor = new Stack();
        paquetInonde = grille.tuillesValide();
        defausseInonde = new Stack();
        Collections.shuffle(paquetInonde);
        System.out.println("PAQUETS INITIALISES");                              //Créé la grille et les deux paquets de cartes (trésors et inondations)
        for(int i = 0; i<6; i++){
            grille.changeEtat((int) paquetInonde.peek(),-1);
            defausseInonde.push(paquetInonde.pop());
        }
        System.out.println("GRILLE INITIALISEE");                               //Inonde les 6 premières tuiles de la grille
        Stack roles = initRoles();
        aventuriers = new ArrayList<>();
        int p;
        for(int i = 0; i<nbJoueurs; i++){                                       //Pioche un rôle au hasard dans la liste des rôle
            p = 0;
            if(roles.peek().equals("PILOTE"))
                while(!(grille.getTuille(p).getSpecial().equals("HELICO")))     //Cherche la position de départ pour le Pilote
                    p++;
            else
                while(!(grille.getTuille(p).getSpecial().equals(roles.peek()))) //Cherche la position de départ pour les autres aventuriers
                    p++;
            switch((String) roles.pop()) {                                      //Créé un objet de la bonne classe en fonction du rôle pioché
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
            for(int j = 0; j<2; j++){                                           //Créé la main de départ de l'aventurier
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

    /*
        RESETS DE PIOCHE
    */
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

    /*
        PIOCHES
    */
    public ArrayList<CarteTresor> piocheTresor(){

        boolean eau = false;
        for(int i = 0; i < 2; i ++){
            if (paquetTresor.isEmpty())                                                 //Si la pioche est vide, on la reset avant de piocher
                resetPiocheTresor();
            if (paquetTresor.peek() == CarteTresor.MONTEE_EAU){                         //Si c'est une carte 'Montée des Eaux', on :
                niveau ++;                                                              //- Augmente le cran du niveau d'eau de 1
                if (niveau == 10)                                                       //  (Et s'il atteint 10, on met fin à la partie)
                    notifierObservateurs(Message.defaite());
                defausseTresor.push(paquetTresor.pop());                                //- On met la carte dans la défausse du paquet Trésor
                eau = true;
            }
            else
                getAventurierEnCours().ajouterCarte((CarteTresor) paquetTresor.pop());  //Sinon on met la carte dans la main du joueur actuel

        }
        if (eau){
           resetPiocheInonde();                                                         //- On remélange la défausse et la pioche s'il y'a eu au moins une montée des eaux
           nbInondations = calculNbInondations();
        }
        
        return getAventurierEnCours().getMain();
    }

    public ArrayList<Integer> piocheInonde(){
        int id;
        ArrayList<Integer> resultat = new ArrayList<>();
        if (paquetInonde.isEmpty())                                     //Si la pioche est vide, la réinitialise
            resetPiocheInonde();

        id = (int) paquetInonde.pop();                                  //Pioche la position de la tuile a inonder
        
        resultat.add(id);
        grille.changeEtat(id, -1);                                      //Change l'état de la tuile à inonder
        
        if(grille.getTuille(id).getEtat() == Etat.ABYSSE){
            String spec = grille.getTuille(id).getSpecial();
            
            if(spec.equals("HELICO")){                                  //Si la tuile sombre dans l'abysse et était l'héliport, la partie est perdue
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
                        resultat.add(-1);
                        notifierObservateurs(Message.noyade(k));        //On prévient le contrôleur d'une potentiel noyade
                    }
                }
            }
        }
        else{
            defausseInonde.push(id);                                    //Si la tuille ne sombre pas dans l'abysse, on ajoute sa position dans la défausse
        }
        
        nbInondations--;
        return resultat;
    }
    
    /*
        MAIN
    */
    public boolean mainPleine(){
        boolean mainPleine = false;
    	for(int i = 0; i < aventuriers.size(); i++){
            if(aventuriers.get(i).getMain().size() > 5) {
            	mainPleine = true;
            	notifierObservateurs(Message.tromain(i));   //Si un joueur à trop de carte en main, on prévient le Controleur
            }
        }
    	return mainPleine;
    }
    
    public void majMain(int idJoueur, ArrayList<Integer> deffausse) {
        
        Collections.sort(deffausse);
        ArrayList<Integer> carteAEnlever = new ArrayList<>();
        
        for(int j = deffausse.size()-1; j > -1; j--){                                           //On trie la liste des cartes à enlever de la main de la dernière à la première
            carteAEnlever.add(deffausse.get(j));
        }
        
    	for(int i = 0; i < carteAEnlever.size(); i++) {
            defausseTresor.push(aventuriers.get(idJoueur).enleverCarte(carteAEnlever.get(i)));  //On enlève les cartes à enlever de la main du joueur qui les possède
    	}
    }

    /*
        ACTIONS
    */
    public void deplace(int position){
        if(getAventurierEnCours().toString().equals("Ingenieur")) {
            getAventurierEnCours().setPouvoir(false);
        }
        getAventurierEnCours().changerPosition(position, grille);   //Change la position de l'aventurier en cours avec sa nouvelle position
        nbActions--;                                                //Réduit le compteur d'action de 1
    }
    
    public void asseche(int position){
        grille.changeEtat(position, 1); //Change l'état de la tuile à assécher

        if(getAventurierEnCours().toString().equals("Ingenieur") && getAventurierEnCours().getPouvoir())
            getAventurierEnCours().setPouvoir(false);
        else {
            nbActions--;                //Réduit le compteur d'action de 1
            if(getAventurierEnCours().toString().equals("Ingenieur"))   
                getAventurierEnCours().setPouvoir(true);
        }
    }

    public void donnerTresor(int receveur, int numCarte){
        if(getAventurierEnCours().toString().equals("Ingenieur"))
            getAventurierEnCours().setPouvoir(false);
        aventuriers.get(receveur).ajouterCarte(getAventurierEnCours().enleverCarte(numCarte));  //Ajoute dans la main de l'aventurier receveur la carte qu'on enlève de la main de l'aventurier donneur
        nbActions--;                                                                            //Réduit le compteur d'action de 1

    }
    
    public void gagneTresor(){
        if(getAventurierEnCours().toString().equals("Ingenieur")) {
            getAventurierEnCours().setPouvoir(false);
        }
        CarteTresor tresor;
        switch(grille.getTuille(getAventurierEnCours().getPosition()).getSpecial()){    //A partir de la caractéristique Spécial de la tuille, détermine le trésor à obtenir
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
        tresors.replace(tresor, true);                                                  //Place la valeur boolean associé au tresor à true
        
        int k;
        for(int i = 0; i < 4; i++){                                                     //Supprime 4 cartes du trésor obtenu de la main du joueur
            k = 0;
            while(getAventurierEnCours().getMain().get(k) != tresor)
                k++;
            getAventurierEnCours().enleverCarte(k);
        }
        
        nbActions--;                                                                    //Réduit le compteur d'action de 1
    }
    
    /*
        CALCULS DES POSSIBILITES : ACTIONS
    */
    public int[] deplacementPossible(){
        return getAventurierEnCours().deplacementPossible(grille).stream().mapToInt(i -> i).toArray();  //Renvoie la lise des tuiles que l'aventurier dont c'est le tour peut atteindre
    }
    
    public int[] assechePossible(){
        ArrayList<Integer> possibles;
        if (getAventurierEnCours().toString().equals("Pilote") || aventuriers.get(tour % aventuriers.size()).toString().equals("Plongeur")){
            Messager m = new Messager(getAventurierEnCours().getPosition());
            possibles = m.deplacementPossible(grille);
        }
        else
            possibles = getAventurierEnCours().deplacementPossible(grille); //Calcul la liste des tuiles à portée du joueur ...
        possibles.add(getAventurierEnCours().getPosition());                //... et y ajoute sa propre position
        ArrayList<Integer> caseAssechables = new ArrayList<>();
        for(int i = 0; i < possibles.size();i++){
            if(grille.getTuille(possibles.get(i)).getEtat() == Etat.INONDE) 
                caseAssechables.add(possibles.get(i));
        }
        return caseAssechables.stream().mapToInt(i -> i).toArray();         //Ne renvoie que celle qui sont inondées
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
        
    public ArrayList<Integer> PersonnagesProches(){
        
        ArrayList<Integer> pp = new ArrayList<>();
        
        for(int i = 0; i < aventuriers.size(); i++){
            if(i != tour % aventuriers.size() && (aventuriers.get(i).getPosition() == getAventurierEnCours().getPosition() || getAventurierEnCours().toString().equals("Messager")) )
                pp.add(i);
        }
        
        return pp;  //Calcul la liste des personnages sur la même tuile que le joueur en cours (ou tous s'il s'agit du Messager)
    }
    
    /*
        ACTIONS SPECIALES
    */
    public int[] assechePossibleSacDeSable() {
    	ArrayList<Integer> caseAssechables = new ArrayList<>();
        for(int i = 0; i < grille.getTuilles().length;i++){
            if(grille.getTuille(i).getEtat() == Etat.INONDE)
                caseAssechables.add(i);
        }
        return caseAssechables.stream().mapToInt(i -> i).toArray(); //Renvoie la liste des tuiles inondées
    }
    
    public void sacDeSable(int joueur, int carte) {
        nbActions++;                                                        //Ajoute une action pour annuler le décompte de la méthode *assecher()*
        defausseTresor.push(aventuriers.get(joueur).enleverCarte(carte));   //Supprime le sac de sable de la main du joueur qui l'a utilisé
    }
    
    public int[] positionsJoueurs(int joueur){
        
        utilisateur = joueur;
        
        ArrayList<Integer> positions = new ArrayList<>();
        for(int i = 0; i < aventuriers.size(); i++)
            positions.add(aventuriers.get(i).getPosition());    //Liste la position de chaque joueurs
        Collections.sort(positions);
        
        for(int i = 0; i < positions.size()-1; i++){
            int j = i+1;
            while(j < positions.size()){
                if(positions.get(j) == positions.get(i))        //Supprime les tuiles en double de la liste
                    positions.remove(j);
                else
                    j++;
            }
        }
        
        return positions.stream().mapToInt(i -> i).toArray();
    }
    
    public int[] pasInondee(){
        ArrayList<Integer> ret = new ArrayList<>();
        for(int i = 0; i < grille.getTuilles().length; i++) {
            if (grille.getTuille(i).getEtat() != Etat.ABYSSE)
                ret.add(i);
        }
        return ret.stream().mapToInt(i -> i).toArray(); //Renvoie la liste des tuiles qui n'ont pas encore sombré dans l'abysse
    }
    
    public void helico(int ancien, int nouveau, boolean helicoTrop){

        for(int i = 0; i < aventuriers.size(); i++)
            if(aventuriers.get(i).getPosition() == ancien)
                aventuriers.get(i).changerPosition(nouveau,grille);             //Change la position de chaque aventurier se trouvant sur la case de départ de l'hélico à celle d'arrivée
        int k = 0;
        while(aventuriers.get(utilisateur).getMain().get(k) != CarteTresor.HELICO && !helicoTrop)
            k++;
        
        if (!helicoTrop)
            defausseTresor.push(aventuriers.get(utilisateur).enleverCarte(k));  //Enlève la carte Hélicoptère de la main du joueur qui l'a utilisé
    }
    
    /*
        CALCULS POSSIBILITEES : PARTIE
    */
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
        
        if(PersonnagesProches().isEmpty() || getAventurierEnCours().getMain().isEmpty())
            clic.add(false);
        else{
            clic.add(true);
        }
        
        clic.add(tresorPossible());
        
        return clic;    //Renvoie une liste de booléen indiquant si : se déplacer / assecher / donner un tresor / gagner un trésor : est possible
    }
    
    public boolean estGagnable(){
        
        int j = 0;
        while(!(grille.getTuille(j).getSpecial().equals("HELICO")))             //Cherche la position de l'héliport
            j++;
        
        int k = 0;
        while(k < aventuriers.size() && aventuriers.get(k).getPosition() == j)  //Compte le nombre d'aventurier sur l'héliport
            k++;
       
        if(k == aventuriers.size()){
            if((boolean) tresors.get(CarteTresor.TRESOR_CALICE) &&              //Vérifie que tout les trésors ont été récupéré
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
    
    /*
        AUTRES
    */
    public int nouveauTour(){
        utilisateur = -1;
        if(getAventurierEnCours().toString().equals("Pilote"))  //Réinitialise le pouvoir du pilote
            getAventurierEnCours().setPouvoir(true);
        tour++;                                                 //Incrémente le compteur de tour
        nbInondations = calculNbInondations();                  //Recalcule le nombre de carte Inondation qu'il va falloir piocher à la fin du tour
        nbActions = 3;                                          //Réinitialise le nombre d'action possible durant le tour ...
        if(getAventurierEnCours().toString().equals("Navigateur"))  
            nbActions++;                                        //... et en ajoute une si le prochain joueur est le Navigateur
        return tour;
    }    
    
    public int[] nagePossible(int numAv){
        return aventuriers.get(numAv).deplacementPossible(grille).stream().mapToInt(i -> i).toArray();  //Renvoie la liste des tuiles que l'aventurier peut rejoindre à la nage
    }

    /*
        CALCUL SPECIFIQUE
    */
        public int calculNbInondations(){
        
        int j;
        switch(niveau){ //Défini le nombre de carte à piocher en fonction du niveau d'eau
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
    
    /*
        INITIALISATIONS SPECIFIQUES
    */
    public Stack<CarteTresor> initPaquetTresor (){

        Stack paquet = new Stack<CarteTresor>();    //Place le bon nombre de chaque exemplaire de Carte Trésor dans le paquet ...
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
        Collections.shuffle(paquet);                //Puis le mélange
        return paquet;
    }

    public Stack<String> initRoles(){
        Stack roles = new Stack();  //Créé une pile contenant tous les rôles possibles ...
        roles.push("PLONGEUR");
        roles.push("MESSAGER");
        roles.push("EXPLORATEUR");
        roles.push("INGENIEUR");
        roles.push("PILOTE");
        roles.push("NAVIGATEUR");
        Collections.shuffle(roles); //... puis la mélange
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
