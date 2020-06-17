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
    private boolean[] dejaVerif = new boolean[36];

    public Plongeur(int p) {
        super(p);
    }

    @Override
    public ArrayList<Integer> deplacementPossible(Grille grille) {
        ArrayList<Integer> deplacementPossible = new ArrayList<>();
        resetDejaVerif();

        int position = super.getPosition();

        if (position - 6 >= 0 && !dejaVerif[position - 6]) {
            //si position - 6 (au dessus de l'aventurier) est dans la grille

            dejaVerif[position - 6] = true; //on met à jour la vérification de la tuille
            switch (grille.getTuille(position - 6).getEtat()){
                case SEC: //si elle est sec, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position - 6);
                    break;
                case INONDE: //si elle est inondée, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position - 6);
                case ABYSSE: //si elle est inondée ou dans l'abysse, on regarde les cases à côté.
                    deplacementPossible.addAll(deplacementPossible(grille,position - 6));
                    break;
            }
        }

        if (position != 18 && position != 12 && position - 1 >= 0 && !dejaVerif[position - 1]) {
            //si la positon est différente de 18 et 12 et que la position - 1 (à gauche de l'aventurier) est dans la grille

            dejaVerif[position - 1] = true;
            switch (grille.getTuille(position - 1).getEtat()){
                case SEC: //si elle est sec, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position - 1);
                    break;
                case INONDE: //si elle est inondée, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position - 1);
                case ABYSSE: //si elle est inondée ou dans l'abysse, on regarde les cases à côté.
                    deplacementPossible.addAll(deplacementPossible(grille,position - 1));
                    break;
            }

        }

        if (position != 17 && position != 12 && position + 1 < 36 && !dejaVerif[position + 1]) {
            //si la positon est différente de 17 et 12 et que la position - 1 (à droite de l'aventurier) est dans la grille

            dejaVerif[position + 1] = true;
            switch (grille.getTuille(position + 1).getEtat()) {
                case SEC: //si elle est sec, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position + 1);
                    break;
                case INONDE: //si elle est inondée, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position + 1);
                case ABYSSE: //si elle est inondée ou dans l'abysse, on regarde les cases à côté.
                    deplacementPossible.addAll(deplacementPossible(grille, position + 1));
                    break;
            }
        }

        if(position + 6 < 36 && !dejaVerif[position + 6]) {
            //si position + 6 (en dessous de l'aventurier) est dans la grille

            dejaVerif[position + 6] = true; //on met à jour la vérification de la tuille
            switch (grille.getTuille(position + 6).getEtat()) {
                case SEC: //si elle est sec, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position + 6);
                    break;
                case INONDE: //si elle est inondée, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position + 6);
                case ABYSSE: //si elle est inondée ou dans l'abysse, on regarde les cases à côté.
                    deplacementPossible.addAll(deplacementPossible(grille, position + 6));
                    break;
            }
        }


        return deplacementPossible;
    }

    public ArrayList<Integer> deplacementPossible(Grille grille, int position) {
        ArrayList<Integer> deplacementPossible = new ArrayList<>();

        if (position - 6 >= 0 && !dejaVerif[position - 6]) {
            //si position - 6 (au dessus de l'aventurier) est dans la grille

            dejaVerif[position - 6] = true; //on met à jour la vérification de la tuille
            switch (grille.getTuille(position - 6).getEtat()){
                case SEC: //si elle est sec, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position - 6);
                    break;
                case INONDE: //si elle est inondée, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position - 6);
                case ABYSSE: //si elle est inondée ou dans l'abysse, on regarde les cases à côté.
                    deplacementPossible.addAll(deplacementPossible(grille,position - 6));
                    break;
            }
        }

        if (position != 18 && position != 12 && position - 1 >= 0 && !dejaVerif[position - 1]) {
            //si la positon est différente de 18 et 12 et que la position - 1 (à gauche de l'aventurier) est dans la grille

            dejaVerif[position - 1] = true;
            switch (grille.getTuille(position - 1).getEtat()){
                case SEC: //si elle est sec, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position - 1);
                    break;
                case INONDE: //si elle est inondée, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position - 1);
                case ABYSSE: //si elle est inondée ou dans l'abysse, on regarde les cases à côté.
                    deplacementPossible.addAll(deplacementPossible(grille,position - 1));
                    break;
            }

        }

        if (position != 17 && position != 12 && position + 1 < 36 && !dejaVerif[position + 1]) {
            //si la positon est différente de 17 et 12 et que la position - 1 (à droite de l'aventurier) est dans la grille

            dejaVerif[position + 1] = true;
            switch (grille.getTuille(position + 1).getEtat()) {
                case SEC: //si elle est sec, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position + 1);
                    break;
                case INONDE: //si elle est inondée, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position + 1);
                case ABYSSE: //si elle est inondée ou dans l'abysse, on regarde les cases à côté.
                    deplacementPossible.addAll(deplacementPossible(grille, position + 1));
                    break;
            }
        }

        if(position + 6 < 36 && !dejaVerif[position + 6]) {
            //si position + 6 (en dessous de l'aventurier) est dans la grille

            dejaVerif[position + 6] = true; //on met à jour la vérification de la tuille
            switch (grille.getTuille(position + 6).getEtat()) {
                case SEC: //si elle est sec, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position + 6);
                    break;
                case INONDE: //si elle est inondée, on l'ajoute à deplacementPossible
                    deplacementPossible.add(position + 6);
                case ABYSSE: //si elle est inondée ou dans l'abysse, on regarde les cases à côté.
                    deplacementPossible.addAll(deplacementPossible(grille, position + 6));
                    break;
            }
        }


        return deplacementPossible;
    }


    private void resetDejaVerif() {
        for(int i =0; i<dejaVerif.length; i++){
            switch(i) {
                case 0: case 1: case 4: case 5: case 6: case 11:
                case 24: case 29: case 30: case 31: case 34: case 35:
                    dejaVerif[i] = true;
                    break;
                default:
                    dejaVerif[i] = false;
                    break;
            }
        }
    }
    @Override
    public String toString(){
        return "Plongeur";
    }
}
