package m2104.ile_interdite.vue;

import m2104.ile_interdite.modele.Etat;
import m2104.ile_interdite.modele.Tuille;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PanelGrille extends JPanel {
    private ArrayList<BufferedImage> tuilles_sec = new ArrayList<>(); //contient les images des tuilles non innondé
    private ArrayList<BufferedImage> tuilles_innondes = new ArrayList<>(); //contient les images des tuilles innondé
    private HashMap<String,BufferedImage> pions = new HashMap<>(); //contient les images des pions
    private HashMap<String,BufferedImage> tresors = new HashMap<>(); //contient les images des cartes tresors
    private BufferedImage abysse; //contient l'image de la tuile de l'abysse
    
    private String[] tuiles;
    private String[] tuillesEtat;
    private HashMap<String,Integer> aventuriers;
    private HashMap<String,Boolean> tresorsGagner;
    private boolean[] tuilesSelectionnable;

    PanelGrille(Tuille[] tuis, HashMap<String,Integer> aventuriers) {
        initImagesTuiles();
        initImagesPions();
        initImageTresor();
        
        initTuilles(tuis);
        initAventuriers(aventuriers);
        initTresor();
    }

    private void initImagesTuiles() {
        try {
            //récupération de l'image de l'abysse
            abysse = ImageIO.read(new File("src/assets/ocean.jpg"));

            //récupération des tuiles innondées

            //on récupére les Path vers tous les fichiers dans src/assets/tuiles
            Stream<Path> walk = Files.walk(Paths.get("src/assets/tuiles"));


            List<String> res = walk.map(x -> x.toString()) //on convertie tous les Path en String
                    .filter(f -> f.contains("_Inonde") && f.endsWith(".png")) //on filtre de manière a ne garder que les fichiers contenant _Inonde et finissant par .png
                    .sorted()
                    .collect(Collectors.toList()); // on convertie le résultat en list

            //pour tous les éléments trouvés
            res.forEach(x -> {
                try {
                    tuilles_innondes.add(ImageIO.read(new File(x))); //on les ajoutes dans l'ArrayList correspondant
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            //récupération des tuiles non innondées

            //on récupére les Path vers tous les fichiers dans src/assets/tuiles
            walk = Files.walk(Paths.get("src/assets/tuiles/"));


            res = walk.map(x -> x.toString()) //on convertie tous les Path en String
                    .filter(f -> !f.contains("_Inonde") && f.endsWith(".png")) //on filtre de manière a ne garder que les fichiers ne contenant pas _Inonde et finissant par .png
                    .sorted() //on met les cartes toujours dans le même ordre pour eviter les erreurs suivant les pc
                    .collect(Collectors.toList()); // on convertie le résultat en list

            //pour tous les éléments trouvés
            res.forEach(
                    x -> {
                try {
                    tuilles_sec.add(ImageIO.read(new File(x))); //on les ajoutes dans l'ArrayList correspondant
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initImagesPions() {
    	try {
    		//récupération des images des pions
	
	        //on récupére les Path vers tous les fichiers dans src/assets/tuiles
	        Stream<Path> walk = Files.walk(Paths.get("src/assets/pions"));
	
	
	        List<String> res = walk.map(x -> x.toString()) //on convertie tous les Path en String
	                .filter(f -> f.endsWith(".png")) //on filtre de manière a ne garder que les fichiers finissant par .png
                    .sorted() //on met les cartes toujours dans le même ordre pour eviter les erreurs suivant les pc
	                .collect(Collectors.toList()); // on convertie le résultat en list
	        
	      //pour tous les éléments trouvés 
            res.forEach(
                    x -> {
                try {
                    pions.put(x.substring(x.lastIndexOf(File.separator)+5, x.length()-4),ImageIO.read(new File(x))); //on les ajoutes dans l'ArrayList correspondant
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    	}catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void initImageTresor(){ 
        try {
     		//récupération des images des cartes tresors
 	
 	        //on récupére les Path vers tous les fichiers dans src/assets/tresors   
 	        Stream<Path> walk = Files.walk(Paths.get("src/assets/tresors/"));
 	
 	
 	        List<String> res = walk.map(x -> x.toString()) //on convertie tous les Path en String
 	                .filter(f -> f.endsWith(".png")) //on filtre de manière a ne garder que les fichiers finissant par .png
                     .sorted() //on met les cartes toujours dans le même ordre pour eviter les erreurs suivant les pc
 	                .collect(Collectors.toList()); // on convertie le résultat en list
 	      //pour tous les éléments trouvés
             res.forEach(
                     x -> { 
                 try {
                     tresors.put(x.substring(x.lastIndexOf(File.separator)+1, x.length()-4),ImageIO.read(new File(x))); //on les ajoutes dans l'ArrayList correspondant
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             });
     	}catch (Exception e) {
             e.printStackTrace(); 
         } 
     }
    
    private void initTuilles(Tuille[] tuis) {
        tuiles = new String[tuis.length];
        tuillesEtat = new String[tuis.length];
        tuilesSelectionnable = new boolean[tuis.length];

        boolean[] premierTrouve = new boolean[4];
        Stack<String> nomNormal = new Stack<String>();

        nomNormal.push("ForetPourpre");
        nomNormal.push("TourGuet");
        nomNormal.push("LagonPerdu");
        nomNormal.push("MaraisBrumeux");
        nomNormal.push("PontAbimes");
        nomNormal.push("RocheFantome");
        nomNormal.push("DunesIllusion");
        nomNormal.push("FalaiseOubli");
        nomNormal.push("ValCrepuscule");
        nomNormal.push("Observatoire");

        Collections.shuffle(nomNormal);


        for(int i = 0; i < tuiles.length; i++) {
            if(tuis[i].getEtat() == Etat.ABYSSE) {
            	tuiles[i]="";
                tuillesEtat[i] = "ABYSSE";
            } else {
                if(tuis[i].getSpecial() == "HELICO") {
                    tuiles[i] = "Heliport";
                } else if(tuis[i].getSpecial() == "TRESOR_PIERRE") {
                    if(!premierTrouve[0]) {
                        tuiles[i] = "TempleLune";
                        premierTrouve[0] = true;
                    } else {
                        tuiles[i] = "TempleSoleil";
                    }
                } else if(tuis[i].getSpecial() == "TRESOR_CALICE") {
                    if(!premierTrouve[1]) {
                        tuiles[i] = "PalaisCorail";
                        premierTrouve[1] = true;
                    } else {
                        tuiles[i] = "PalaisMarees";
                    }
                } else if(tuis[i].getSpecial() == "TRESOR_CRISTAL") {
                    if(!premierTrouve[2]) {
                        tuiles[i] = "CaverneOmbres";
                        premierTrouve[2] = true;
                    } else {
                        tuiles[i] = "CaverneBrasier";
                    }
                } else if(tuis[i].getSpecial() == "TRESOR_STATUE") {
                    if(!premierTrouve[3]) {
                        tuiles[i] = "JardinHurlments";
                        premierTrouve[3] = true;
                    } else {
                        tuiles[i] = "JardinMurmures";
                    }
                } else if(tuis[i].getSpecial() == "PLONGEUR") {
                    tuiles[i] = "PorteFer";
                } else if(tuis[i].getSpecial() == "MESSAGER") {
                    tuiles[i] = "PorteArgent";
                } else if(tuis[i].getSpecial() == "EXPLORATEUR") {
                    tuiles[i] = "PorteCuivre";
                } else if(tuis[i].getSpecial() == "INGENIEUR") {
                    tuiles[i] = "PorteBronze";
                } else if(tuis[i].getSpecial() == "NAVIGATEUR") {
                    tuiles[i] = "PorteOr";
                } else {
                    tuiles[i] = (String) nomNormal.pop();
                }

                if(tuis[i].getEtat() == Etat.INONDE) {
                    tuillesEtat[i] = "INONDE";
                } else {
                    tuillesEtat[i] = "SEC";
                }
            }
            tuilesSelectionnable[i] = false;
        }
    }

    public void initAventuriers(HashMap<String,Integer> aventuriers) {
        this.aventuriers = new HashMap<String, Integer>();
        this.aventuriers.putAll(aventuriers);
    }
    
    public void initTresor(){
    	tresorsGagner = new HashMap<>();
        for(String list : tresors.keySet()){
            switch(list){ 
                case "calice": 
                case "cristal":
                case "pierre" :
                case "statue" :
                    tresorsGagner.put(list, Boolean.FALSE); 
                break; 
            }
        }
    }
    
    
    public void changerEtatTuile(int tuile, String etat) {
    	effacerSelection();
    	System.out.println("Etat : " + etat);
    	tuillesEtat[tuile] = etat;
    	repaint();
    }

    public void deplacerAventurier(String role, int tuile) {
    	effacerSelection();
    	System.out.println("PAN : deplaceraventuriers");
    	aventuriers.put(role, tuile);
    	repaint();
    }
    
    
    public void changerEtatTresor(HashMap<String, Boolean> tresor){
        tresor.forEach( (k,v) -> { 
            tresorsGagner.put((String) k,(Boolean) v);
            System.out.println("PAN : changer etat tresor : k " + k + " v " + v);
        });
        repaint();
    }
    
    public void selectionnerTuiles(int[] tab) {
    	effacerSelection();
    	for (int i = 0; i < tab.length; i++) {
    		tuilesSelectionnable[tab[i]] = true;
    	}  	
    	repaint();
    }
    
    public int getNumeroTuile(int x, int y) {
    	int colonne = (int) (x / (getWidth() / 6));
    	int ligne= (int) (y / (getHeight() / 6));
    	return ligne * 6 + colonne;
    }
    
    public void effacerSelection() {
    	for (int i = 0; i < tuilesSelectionnable.length; i++) {
    		tuilesSelectionnable[i] = false;
    	}
    	repaint();
    }
    
    public boolean estSelectionnable(int tuile) {
    	return(tuilesSelectionnable[tuile] == true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	dessinerTuiles(g);
        dessinerAventuriers(g);
        dessinerSelection(g);
        dessinerTresor(g); 
    }

    private void dessinerTuiles(Graphics g) {
        for(int i = 0; i < tuiles.length; i++) {
            if(tuillesEtat[i] == "ABYSSE") {
                g.drawImage(abysse.getScaledInstance(getWidth() / 6, getHeight() / 6, Image.SCALE_DEFAULT), (i % 6) * getWidth() / 6, i/6 * getHeight()/6, null, null);
            } else {
                int nbTuilles = 0;

                switch (tuiles[i]) {
                    case "Heliport":
                        nbTuilles = 0;
                        break;
                    case "CaverneOmbres":
                        nbTuilles = 1;
                        break;
                    case "CaverneBrasier":
                        nbTuilles = 2;
                        break;
                    case "ForetPourpre":
                        nbTuilles = 3;
                        break;
                    case "PorteBronze":
                        nbTuilles = 4;
                        break;
                    case "PorteCuivre":
                        nbTuilles = 5;
                        break;
                    case "PorteFer":
                        nbTuilles = 6;
                        break;
                    case "PorteArgent":
                        nbTuilles = 7;
                        break;
                    case "PorteOr":
                        nbTuilles = 8;
                        break;
                    case "TourGuet":
                        nbTuilles = 9;
                        break;
                    case "JardinHurlments":
                        nbTuilles = 10;
                        break;
                    case "JardinMurmures":
                        nbTuilles = 11;
                        break;
                    case "LagonPerdu":
                        nbTuilles = 12;
                        break;
                    case "MaraisBrumeux":
                        nbTuilles = 13;
                        break;
                    case "PalaisCorail":
                        nbTuilles = 14;
                        break;
                    case "PalaisMarees":
                        nbTuilles = 15;
                        break;
                    case "PontAbimes":
                        nbTuilles = 16;
                        break;
                    case "RocheFantome":
                        nbTuilles = 17;
                        break;
                    case "TempleLune":
                        nbTuilles = 18;
                        break;
                    case "TempleSoleil":
                        nbTuilles = 19;
                        break;
                    case "ValCrepuscule":
                        nbTuilles = 20;
                        break;
                    case "DunesIllusion":
                        nbTuilles = 21;
                        break;
                    case "FalaiseOubli":
                        nbTuilles = 22;
                        break;
                    case "Observatoire":
                        nbTuilles = 23;
                        break;
                }

                if(tuillesEtat[i].equals("SEC")) {
                    g.drawImage(tuilles_sec.get(nbTuilles).getScaledInstance(getWidth() / 6, getHeight() / 6, Image.SCALE_DEFAULT), (i % 6) * getWidth() / 6, i/6 * getHeight()/6, null, null);
                } else {
                    g.drawImage(tuilles_innondes.get(nbTuilles).getScaledInstance(getWidth() / 6, getHeight() / 6, Image.SCALE_DEFAULT), (i % 6) * getWidth() / 6, i/6 * getHeight()/6, null, null);
                }
            }
        }	
    }
    
    private void dessinerAventuriers(Graphics g) {
        HashMap<Integer, Integer> nbAventurierParCase = new HashMap<>();

        aventuriers.forEach((k,v) -> {
            if(nbAventurierParCase.get(v) == null) {
                nbAventurierParCase.put(v, 1);
            } else {
                nbAventurierParCase.put(v, nbAventurierParCase.get(v) + 1);
            }
        });

        HashMap<Integer, Integer> aventurierRestantSurCase = (HashMap<Integer, Integer>) nbAventurierParCase.clone();

    	aventuriers.forEach((k,v) -> {
    	    //k est le nom de l'aventurier
            //v est la position de l'aventurier

    		String couleur = "Violet";
            switch (k) {
            	case "Explorateur" : couleur = "Vert"; break;
            	case "Ingenieur" : couleur = "Rouge"; break;
            	case "Messager" : couleur = "Gris"; break;
            	case "Navigateur" : couleur = "Jaune"; break;
            	case "Pilote" : couleur = "Bleu"; break;
            	case "Plongeur" : couleur = "Noir"; break;
            }

            int sizeX = (int) ((getWidth() / 6)*0.7) / (nbAventurierParCase.get(v) < 2 ? 1 : 2);
            int sizeY = (int) ((getHeight() / 6)*0.7) / (nbAventurierParCase.get(v) < 3 ? 1 : 2);


            int posX = (v % 6) * getWidth() / 6 + (aventurierRestantSurCase.get(v) % 2 == 0 ? 1 : 0) * sizeX + sizeX / 3;
            int posY = v / 6 * getHeight() / 6 + (aventurierRestantSurCase.get(v) < 3 ? 0 : 1) * sizeY + sizeY / 4;

            g.drawImage(pions.get(couleur).getScaledInstance(sizeX, sizeY, Image.SCALE_DEFAULT),posX, posY, null, null);
            aventurierRestantSurCase.put(v, aventurierRestantSurCase.get(v) - 1);
        } );
    }
    
    private void dessinerSelection(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g;
    	float epaisseur = 5;
    	Stroke trait = g2.getStroke();
    	g2.setStroke(new BasicStroke(epaisseur));
    	
    	for(int i = 0; i < tuiles.length; i++) {
	        if(tuilesSelectionnable[i]) {
	        	g.setColor(Color.green);
	        	g.drawRect((i % 6) * getWidth() / 6, (i / 6) * getHeight() / 6, getWidth() / 6, getHeight() / 6);
	        }
    	}
    	g2.setStroke(trait);
    }
    
    public void dessinerTresor(Graphics g){
        
    	tresorsGagner.forEach( (k,v) -> { 
    		int i = 0;
    		switch (k) {
	        	case "calice" : i = 0; break;
	        	case "statue" : i = 5; break;
	        	case "pierre" : i = 30; break;
	        	case "cristal" : i = 35; break;
    		}
           
           if(v){ 
               System.out.println("k = " + k);
               System.out.println("tresors.get(k) = " + tresors.get(k));
               System.out.println("tresors.get(CALICE) = " + tresors.get("CALICE"));
               g.drawImage(tresors.get(k).getScaledInstance(getWidth()/6, getHeight()/6, Image.SCALE_DEFAULT),(i%6)*getWidth()/6, i/6*getHeight()/6, null, null);   
           }
        });
    }

}
