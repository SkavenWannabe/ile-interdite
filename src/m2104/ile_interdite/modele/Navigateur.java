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
public class Navigateur extends Aventurier{
    
    public Navigateur(int p) {
        super(p);
    }

    //Prévue mais finalement pas implantée
    public void deplaceAutreJoueur(Aventurier aventurier, int position, Grille grille) {
        aventurier.changerPosition(position,grille);
    }

    @Override
    public String toString(){
        return "Navigateur";
    }
}
