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
public class Plongeur extends Aventurier{
    //todo cpafini
    public Plongeur(int p) {
        super(p);
    }

    @Override
    public ArrayList<Integer> deplacementPossible(Grille grille) {
        ArrayList<Integer> deplacementPossible = new ArrayList<>();

        int position = super.getPosition();

        boolean[] dejaVerifier = new boolean[36]; //De base les tableaux de boolean sont initialisé à false.


        /*
            Position au dessus de l'aventurier
         */
        if(position - 6 >= 0 && grille.getTuille(position-6).getEtat() != Etat.ABYSSE) {
            //si position - 6 (au dessus de l'aventurier) est dans la grille et que la tuile à cette position n'est pas dans l'abysse
            deplacementPossible.add(position - 6);
        }

        if(position - 1 >= 0 && grille.getTuille(position - 1).getEtat() != Etat.ABYSSE)
            //si la position - 1 (à gauche de l'aventurier) est dans la grille et que la tuile à cette position n'est pas dans l'abysse
            deplacementPossible.add(position - 1);

        if(position + 1 < 36 && grille.getTuille(position + 1).getEtat() != Etat.ABYSSE)
            //si la position + 1 (à droite de l'aventurier est dans la grille et que la tuille à cette position n'est pas dans l'abysse
            deplacementPossible.add(position + 1);

        if(position + 6 < 36 && grille.getTuille(position + 6).getEtat() != Etat.ABYSSE)
            //si la position + 6 (en dessous de l'aventurier) est dans la grille et que la tuille à cette position n'est pas dans l'abysse
            deplacementPossible.add(position + 6);

        return deplacementPossible; //on converti l'ArrayList en tableau
    }

    public ArrayList<Integer> deplacementPossible(Grille grille, int position, boolean[] dejaVerifier) {
        ArrayList<Integer> deplacementPossible = new ArrayList<>();

        /*
            Position au dessus de l'aventurier
         */
        if(position - 6 >= 0 && grille.getTuille(position-6).getEtat() != Etat.ABYSSE) {
            //si position - 6 (au dessus de l'aventurier) est dans la grille et que la tuile à cette position n'est pas dans l'abysse
            deplacementPossible.add(position - 6);
        }

        if(position - 1 >= 0 && grille.getTuille(position - 1).getEtat() != Etat.ABYSSE)
            //si la position - 1 (à gauche de l'aventurier) est dans la grille et que la tuile à cette position n'est pas dans l'abysse
            deplacementPossible.add(position - 1);

        if(position + 1 < 36 && grille.getTuille(position + 1).getEtat() != Etat.ABYSSE)
            //si la position + 1 (à droite de l'aventurier est dans la grille et que la tuille à cette position n'est pas dans l'abysse
            deplacementPossible.add(position + 1);

        if(position + 6 < 36 && grille.getTuille(position + 6).getEtat() != Etat.ABYSSE)
            //si la position + 6 (en dessous de l'aventurier) est dans la grille et que la tuille à cette position n'est pas dans l'abysse
            deplacementPossible.add(position + 6);

        return deplacementPossible; //on converti l'ArrayList en tableau
    }


    @Override
    public String toString(){
        return "Plongeur";
    }
}
