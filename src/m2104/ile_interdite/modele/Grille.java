/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.modele;

import java.util.Collections;
import java.util.Stack;

/**
 *
 * @author PC SALON
 */
public class Grille {
    
    private Tuille[] tuilles = new Tuille[36];
    
    Grille(){
        Stack special;
        special = initSpecial();
        for(int i =0; i<36; i++){
            switch(i) {
                case 0: case 1: case 4: case 5: case 6: case 11:
                case 24: case 29: case 30: case 31: case 34: case 35: 
                    tuilles[i] = new Tuille(i,Etat.ABYSSE,"");
                    break;
                default:
                    tuilles[i] = new Tuille(i,Etat.SEC, (String) special.pop());
                    break;
            }
        }
    }

    public Tuille[] getTuilles() {
        return tuilles;
    }

    public Stack<String> initSpecial(){
        Stack special = new Stack<String>();
        for(int i=0; i<2; i++)
            special.push("TRESOR_PIERRE");
        for(int i=0; i<2; i++)
            special.push("TRESOR_CALICE");
        for(int i=0; i<2; i++)
            special.push("TRESOR_CRISTAL");
        for(int i=0; i<2; i++)
            special.push("TRESOR_STATUE");
        special.push("HELICO");
        special.push("PLONGEUR");
        special.push("MESSAGER");
        special.push("EXPLORATEUR");
        special.push("INGENIEUR");
        special.push("PILOTE");
        special.push("NAVIGATEUR");
        for(int i=0; i<9; i++)
            special.push("");
        Collections.shuffle(special);
        return special;
    }
    
    public Stack tuillesValide(){
        Stack valides = new Stack();

        for(int i =0; i<36; i++){
            if(tuilles[i].getEtat() != Etat.ABYSSE){
                valides.push(i);
            }
        }

        return valides;
    }
    
    public Tuille getTuille(int i){
        return tuilles[i];
    }
}
