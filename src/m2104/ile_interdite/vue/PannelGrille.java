package m2104.ile_interdite.vue;

import m2104.ile_interdite.modele.Aventurier;
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
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PannelGrille extends JPanel {
    private ArrayList<BufferedImage> tuilles_sec = new ArrayList<>(); //contient les images des tuilles non innondé
    private ArrayList<BufferedImage> tuilles_innondes = new ArrayList<>(); //contient les images des tuilles innondé
    private ArrayList<BufferedImage> pions = new ArrayList<>(); //contient les images des pions
    private BufferedImage abysse; //contient l'image de la tuile de l'abysse
    private String[] tuilles;
    private String[] tuillesEtat;
    private String[] aventurier;
    private boolean[] tuillesSelectionnable;


    PannelGrille(Tuille[] tuis) {
        initImagesTuiles();
        initImagesPions();
        initTuilles(tuis);
//        initJoueur(aventurier);
    }

    @Override
    protected void paintComponent(Graphics g) {
    	// effacerComposant();
    	// dessinerTuilles();
    	// dessinerJoueurs();
    	// dessinerSelection();
    	
        for(int i = 0; i < tuilles.length; i++) {
            if(tuillesEtat[i] == "ABYSSE") {
                g.drawImage(abysse.getScaledInstance(getWidth() / 6, getHeight() / 6, Image.SCALE_DEFAULT), (i % 6) * getWidth() / 6, i/6 * getHeight()/6, null, null);
            } else {
                int nbTuilles = 0;

                switch (tuilles[i]) {
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
                    case "PorteDor":
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
                
                if(tuillesSelectionnable[i]) {
                	g.drawRect(i%6*getWidth(), i/6*getHeight(), getWidth()/6, getHeight()/6);
                	g.setColor(Color.green);
                }
                
                
            }
        }
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
                    .sorted()
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

    private void initTuilles(Tuille[] tuis) {
        tuilles = new String[tuis.length];
        tuillesEtat = new String[tuis.length];
        tuillesSelectionnable = new boolean[tuis.length];

        boolean[] premierTrouve = new boolean[4];
        Stack nomNormal = new Stack();

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



        for(int i = 0; i < tuilles.length; i++) {
            if(tuis[i].getEtat() == Etat.ABYSSE) {
            	tuilles[i]="";
                tuillesEtat[i] = "ABYSSE";
            } else {
                if(tuis[i].getSpecial() == "HELICO") {
                    tuilles[i] = "Heliport";
                } else if(tuis[i].getSpecial() == "TRESOR_PIERRE") {
                    if(!premierTrouve[0]) {
                        tuilles[i] = "TempleLune";
                        premierTrouve[0] = true;
                    } else {
                        tuilles[i] = "TempleSoleil";
                    }
                } else if(tuis[i].getSpecial() == "TRESOR_CALICE") {
                    if(!premierTrouve[1]) {
                        tuilles[i] = "PalaisCorail";
                        premierTrouve[1] = true;
                    } else {
                        tuilles[i] = "PalaisMarees";
                    }
                } else if(tuis[i].getSpecial() == "TRESOR_CRISTAL") {
                    if(!premierTrouve[2]) {
                        tuilles[i] = "CaverneOmbres";
                        premierTrouve[2] = true;
                    } else {
                        tuilles[i] = "CaverneBrasier";
                    }
                } else if(tuis[i].getSpecial() == "TRESOR_STATUE") {
                    if(!premierTrouve[3]) {
                        tuilles[i] = "JardinHurlments";
                        premierTrouve[3] = true;
                    } else {
                        tuilles[i] = "JardinMurmures";
                    }
                } else if(tuis[i].getSpecial() == "PLONGEUR") {
                    tuilles[i] = "PorteFer";
                } else if(tuis[i].getSpecial() == "MESSAGER") {
                    tuilles[i] = "PorteArgent";
                } else if(tuis[i].getSpecial() == "EXPLORATEUR") {
                    tuilles[i] = "PorteCuivre";
                } else if(tuis[i].getSpecial() == "INGENIEUR") {
                    tuilles[i] = "PorteBronze";
                } else if(tuis[i].getSpecial() == "NAVIGATEUR") {
                    tuilles[i] = "PorteDor";
                } else {
                    tuilles[i] = (String) nomNormal.pop();
                }

                if(tuis[i].getEtat() == Etat.INONDE) {
                    tuillesEtat[i] = "INONDE";
                } else {
                    tuillesEtat[i] = "SEC";
                }
            }
            tuillesSelectionnable[i] = false;
        }
    }
    
    public void initImagesPions() {
    	try {
    		//récupération des images des pions
	
	        //on récupére les Path vers tous les fichiers dans src/assets/tuiles
	        Stream<Path> walk = Files.walk(Paths.get("src/assets/pions"));
	
	
	        List<String> res = walk.map(x -> x.toString()) //on convertie tous les Path en String
	                .filter(f -> f.endsWith(".png")) //on filtre de manière a ne garder que les fichiers finissant par .png
	                .collect(Collectors.toList()); // on convertie le résultat en list
	        
	      //pour tous les éléments trouvés
            res.forEach(
                    x -> {
                try {
                    pions.add(ImageIO.read(new File(x))); //on les ajoutes dans l'ArrayList correspondant
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    	}catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void initJoueur(String[] av) {
        this.aventurier = new String[av.length];
        for(int i = 0; i < aventurier.length; i++) {
        	aventurier[i] = av[i];
        }
    }

    public void changerEtatTuile(int tuile, String etat) {
    	tuillesEtat[tuile] = etat;
    	effacerSelectionnable();
    	repaint();
    }

    public void deplacerJoueur(int joueur, int tuile) {
    	// MAJ joeur
    	effacerSelectionnable();
    	repaint();
    }
    
    public void selectionnerTuiles(int[] tab) {
    	for (int i = 0; i < tab.length; i++) {
    		tuillesSelectionnable[tab[i]] = true;
    	}  	
    	repaint();
    }
    
    public void effacerSelectionnable() {
    	for (int i = 0; i < tuillesSelectionnable.length; i++) {
    		tuillesSelectionnable[i] = false;
    	}
    }
}
