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
    public ArrayList<Integer> deplacementPossible(Grille grille) {
        ArrayList<Integer> deplacementPossible = new ArrayList<>();

        int position = super.getPosition();

        if(position != 12 && position != 18 && position - 7 >= 0 && grille.getTuille(position - 7).getEtat() != Etat.ABYSSE)
            //si la position - 7 (en haut à gauche de l'aventurier) est dans la grille et que la tuile à cette position n'est pas dans l'abysse
            deplacementPossible.add(position - 7);
        
        if(position - 6 >= 0 && grille.getTuille(position - 6).getEtat() != Etat.ABYSSE)
            //si la position - 6 (en haut de l'aventurier) est dans la grille et que la tuile à cette position n'est pas dans l'abysse
            deplacementPossible.add(position - 6);
        
        if(position != 17 && position != 23 && position - 5 >= 0 && grille.getTuille(position - 5).getEtat() != Etat.ABYSSE)
            //si la position - 5 (en haut à droite de l'aventurier) est dans la grille et que la tuile à cette position n'est pas dans l'abysse
            deplacementPossible.add(position - 5);
        
        if(position != 12 && position != 18 && position - 1 >= 0 && grille.getTuille(position - 1).getEtat() != Etat.ABYSSE)
            //si la position - 1 (à gauche de l'aventurier) est dans la grille et que la tuile à cette position n'est pas dans l'abysse
            deplacementPossible.add(position - 1);

        if(position != 17 && position != 23 && position + 1 < 36 && grille.getTuille(position + 1).getEtat() != Etat.ABYSSE)
            //si la position + 1 (à droite de l'aventurier est dans la grille et que la tuille à cette position n'est pas dans l'abysse
            deplacementPossible.add(position + 1);

        if(position != 12 && position != 18 && position + 5 < 36 && grille.getTuille(position + 5).getEtat() != Etat.ABYSSE)
            //si la position + 5 (en bas à gauche de l'aventurier est dans la grille et que la tuille à cette position n'est pas dans l'abysse
            deplacementPossible.add(position + 5);
        
        if(position + 6 < 36 && grille.getTuille(position + 6).getEtat() != Etat.ABYSSE)
            //si la position + 6 (en bas de l'aventurier est dans la grille et que la tuille à cette position n'est pas dans l'abysse
            deplacementPossible.add(position + 6);
        
        if(position != 17 && position != 23 && position + 7 < 36 && grille.getTuille(position + 7).getEtat() != Etat.ABYSSE)
            //si la position + 7 (en bas à droite de l'aventurier est dans la grille et que la tuille à cette position n'est pas dans l'abysse
            deplacementPossible.add(position + 7);

        return deplacementPossible; //on converti l'ArrayList en tableau
    }

    @Override
    public String toString(){
        return "Explorateur";
    }
}
