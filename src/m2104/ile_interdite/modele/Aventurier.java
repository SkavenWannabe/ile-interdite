package m2104.ile_interdite.modele;

import java.util.ArrayList;

/**
 *
 * @author IUT2-Dept Info
 */
public abstract class Aventurier {
    
    private int position;
    private ArrayList<CarteTresor> main;
    
    Aventurier(int p){
        position = p;
        main = new ArrayList<>();
    }

    public void ajouterCarte(CarteTresor carte){
        main.add(carte);
    }
    
    public int getPosition() {
        return position;
    }

    public String toString(){
        String affich = "|";
        int k = 0;
        while(k<main.size()){
            affich = affich + main.get(k)+"|";
            k++;
        }
        return affich;
    }
}
