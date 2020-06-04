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
public class Messager extends Aventurier{
    
    Messager(int p){
        super(p);
    }
    
    @Override
    public String toString(){
        return "Messager " + super.toString();
    }
}
