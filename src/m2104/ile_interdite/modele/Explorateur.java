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
public class Explorateur extends Aventurier{
    
    public Explorateur(int p) {
        super(p);
    }

    @Override
    public int[] deplacementPossible(Grille grille) {
        ArrayList<Integer> deplacementPossible = new ArrayList<>();

        int position = super.getPosition();

        //on parcourt les trois tuilles au dessus de l'aventurier
        for(int i = position - 7; i <= position - 5; i++) {
            if(i >= 0 && grille.getTuille(i).getEtat() != Etat.ABYSSE)
                deplacementPossible.add(i);
        }

        if(position - 1 >= 0 && grille.getTuille(position - 1).getEtat() != Etat.ABYSSE)
            //si la position - 1 (à gauche de l'aventurier) est dans la grille et que la tuile à cette position n'est pas dans l'abysse
            deplacementPossible.add(position - 1);

        if(position + 1 < 36 && grille.getTuille(position + 1).getEtat() != Etat.ABYSSE)
            //si la position + 1 (à droite de l'aventurier est dans la grille et que la tuille à cette position n'est pas dans l'abysse
            deplacementPossible.add(position + 1);


        for(int i = position + 5; i <= position + 7; i++) {
            if(i < 36 && grille.getTuille(i).getEtat() != Etat.ABYSSE)
                deplacementPossible.add(i);
        }

        return deplacementPossible.stream().mapToInt(i -> i).toArray(); //on converti l'ArrayList en tableau
    }

    @Override
    public String toString(){
        return "Explorateur";
    }
}
