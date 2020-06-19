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
public class Ingenieur extends Aventurier{
    private boolean pouvoir; //vrai si il a déjà assécher se
    public Ingenieur(int p) {
        super(p);
        pouvoir = false;
    }

    @Override
    public boolean getPouvoir() {
        return pouvoir;
    }

    public void setPouvoir(boolean val) {
        this.pouvoir = val;
    }
    @Override
    public String toString(){
        return "Ingenieur";
    }
}
