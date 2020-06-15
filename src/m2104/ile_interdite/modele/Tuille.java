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
public class Tuille {
    
    private int num;
    private Etat etat;
    private CarteTresor special;
    
    Tuille(int num, Etat etat, CarteTresor special){
        this.num = num;
        this.setEtat(etat);
        this.special = special;
    }
    
    public Etat getEtat(){
        return etat;
    }
    
    public CarteTresor getSpecial(){
        return special;
    }
    
    public void setEtat(Etat etat){
        this.etat = etat;
    }
}
