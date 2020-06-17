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

    public CarteTresor enleverCarte(int numCarte) {
        return main.remove(numCarte);
    }

    public void changerPosition(int position, Grille grille) {
        this.position = position;
    }

    public ArrayList<Integer> deplacementPossible(Grille grille) {
        ArrayList<Integer> deplacementPossible = new ArrayList<>();

        if(position - 6 >= 0 && grille.getTuille(position-6).getEtat() != Etat.ABYSSE)
            //si position - 6 (au dessus de l'aventurier) est dans la grille et que la tuile à cette position n'est pas dans l'abysse
            deplacementPossible.add(position - 6);

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

    public int getPosition() {
        return position;
    }
    public ArrayList<CarteTresor> getMain(){
        return main;
    }
}
