/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2104.ile_interdite.modele;

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
    public int[] deplacementPossible(Grille grille) {
        if(!pouvoir)
            //si il n'a pas son pouvoir son déplacement est celui de l'aventurier de base
            return super.deplacementPossible(grille);
        else {
            int[] ret = new int[36];
            for(int i = 0; i < ret.length; i++) {
                ret[i] = i;
            }
            return ret;
        }
    }

    public void setPouvoir(boolean pouvoir) {
        this.pouvoir = pouvoir;
    }

    @Override
    public String toString(){
        return "Pilote";
    }
}
