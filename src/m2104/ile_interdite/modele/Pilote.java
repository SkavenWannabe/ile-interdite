/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.modele;

import java.util.ArrayList;

/**
 *
 * @author PC SALON
 */
public class Pilote extends Aventurier{
    
    private boolean pouvoir;
    
    public Pilote(int p) {
        super(p);
        pouvoir = true;
    }

    @Override
    public ArrayList<Integer> deplacementPossible(Grille grille) {
        if(!pouvoir)
            //si il n'a pas son pouvoir son déplacement est celui de l'aventurier de base
            return super.deplacementPossible(grille);
        else {
            ArrayList<Integer> ret = new ArrayList<>();
            for(int i = 0; i < grille.getTuilles().length; i++) {
                if (grille.getTuille(i).getEtat() != Etat.ABYSSE)
                    ret.add(i);
            }
            return ret;
        }
    }

    @Override
    public void changerPosition(int position, Grille grille) {
        ArrayList<Integer> marche = super.deplacementPossible(grille);
        int k = 0;
        while(k < marche.size() && position != marche.get(k))
            k++;
        if(k == marche.size())
            pouvoir = false;
        super.changerPosition(position,grille);
    }
    
    public void setPouvoir(boolean pouvoir) {
        this.pouvoir = pouvoir;
    }

    @Override
    public String toString(){
        return "Pilote";
    }
}
