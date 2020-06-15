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
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PannelGrille extends JPanel {
    private ArrayList<BufferedImage> tuilles_sec = new ArrayList<>(); //contient les images des tuilles non innondé
    private ArrayList<BufferedImage> tuilles_innondes = new ArrayList<>(); //contient les images des tuilles innondé
    private BufferedImage abysse; //contient l'image de la tuile de l'abysse
    private String[] tuilles;


    PannelGrille(Tuille[] tuis) {
        initTuilles(tuis);
        init_images();
    }

    @Override
    protected void paintComponent(Graphics g) {

        for(int i = 0; i < tuilles.length; i++) {
            if(tuilles[i] == "ABYSSE") {
                g.drawImage(abysse.getScaledInstance(getWidth() / 6, getHeight() / 6, Image.SCALE_DEFAULT), (i % 6) * getWidth() / 6, i/6 * getHeight()/6, null, null);
            } else {
                int nbTuilles = 0;

                switch (tuilles[i].split(" ")[0]) {
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
                    case "PorteArgent":
                        nbTuilles = 4;
                        break;
                    case "PorteBronze":
                        nbTuilles = 5;
                        break;
                    case "PorteCuivre":
                        nbTuilles = 6;
                        break;
                    case "PorteFer":
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
                    case "DunesIllusion":
                        nbTuilles = 18;
                        break;
                    case "FalaiseOubli":
                        nbTuilles = 19;
                        break;
                    case "TempleLune":
                        nbTuilles = 20;
                        break;
                    case "TempleSoleil":
                        nbTuilles = 21;
                        break;
                    case "ValCrepuscule":
                        nbTuilles = 22;
                        break;
                    case "Observatoire":
                        nbTuilles = 23;
                        break;
                }

                if(tuilles[i].split(" ")[1].equals("SEC")) {
                    g.drawImage(tuilles_sec.get(nbTuilles).getScaledInstance(getWidth() / 6, getHeight() / 6, Image.SCALE_DEFAULT), (i % 6) * getWidth() / 6, i/6 * getHeight()/6, null, null);
                } else {
                    g.drawImage(tuilles_innondes.get(nbTuilles).getScaledInstance(getWidth() / 6, getHeight() / 6, Image.SCALE_DEFAULT), (i % 6) * getWidth() / 6, i/6 * getHeight()/6, null, null);
                }
            }
        }
    }



    private void init_images() {
        try {
            //récupération de l'image de l'abysse
            abysse = ImageIO.read(new File("src/assets/ocean.jpg"));

            //récupération des tuiles innondées

            //on récupére les Path vers tous les fichiers dans src/assets/tuiles
            Stream<Path> walk = Files.walk(Paths.get("src/assets/tuiles"));


            List<String> res = walk.map(x -> x.toString()) //on convertie tous les Path en String
                    .filter(f -> f.contains("_Inonde") && f.endsWith(".png")) //on filtre de manière a ne garder que les fichiers contenant _Inonde et finissant par .png
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
                tuilles[i] = "ABYSSE";
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
                    tuilles[i] += " INONDE";
                } else {
                    tuilles[i] += " SEC";
                }
            }
        }
    }
}
