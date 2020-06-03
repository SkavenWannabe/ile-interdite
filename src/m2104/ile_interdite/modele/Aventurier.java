package m2104.ile_interdite.modele;

/**
 *
 * @author IUT2-Dept Info
 */
public abstract class Aventurier {
    
    private int position;
    
    Aventurier(int p){
        position = p;
    }

    public int getPosition() {
        return position;
    }

    public abstract String toString();
}
