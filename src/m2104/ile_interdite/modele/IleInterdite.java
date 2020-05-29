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
    private int tour = 1;
    private HashMap tresors = new HashMap(4);
    private Stack paquetTresor = new Stack<CarteTresor>();
    private Stack defausseTresor = new Stack<CarteTresor>();
    private Stack paquetInonde = new Stack();
    private Stack defausseInonde = new Stack();
    private Grille grille = new Grille();
    private ArrayList<Aventurier> aventuriers = new ArrayList<>();
    
    public IleInterdite(Observateur<Message> observateur) {
        this.addObservateur(observateur);
    }

    public String[] inscrireJoueurs(int nbJoueurs) {
        // TODO: à remplacer par une réelle assignation des types d'aventuriers
        String[] nomAventuriers = new String[nbJoueurs];
        Arrays.fill(nomAventuriers, "Aventurier");
        return nomAventuriers;
    }
    
    public void initialisation(int nbJoueurs){
       
        System.out.println("INITIALISATION ...");
        diff = 2;
        System.out.println("DIFFICULTE INITIALISEE");
        tresors.put(CarteTresor.TRESOR_PIERRE,false);
        tresors.put(CarteTresor.TRESOR_CALICE,false);
        tresors.put(CarteTresor.TRESOR_CRISTAL,false);
        tresors.put(CarteTresor.TRESOR_STATUE,false);
        System.out.println("TRESORS INITIALISES");
        paquetTresor = initPaquetTresor();
        paquetInonde = grille.tuillesValide();
        Collections.shuffle(paquetInonde);
        System.out.println("PAQUETS INITIALISES");
        Stack roles = new Stack();
        roles = initRoles();
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
        }
        System.out.println("ROLES INITIALISES");
        System.out.println("INITIALISATION TERMINEE !");
    }
    
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
    
    public ArrayList<Aventurier> getAventuriers(){
        return aventuriers;
    }
}
