package m2104.ile_interdite.vue;

import m2104.ile_interdite.modele.CarteTresor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Stack;

public class PanelDefausse extends JPanel {

    private BufferedImage[] img_defausse;
    private Stack defausse;

    PanelDefausse(Stack defausse) {
            this.defausse = defausse;
            init_image();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Stack draw = (Stack) defausse.clone();
        int j = draw.size();

        for(int i = 0; i < j; i++) {

            int ligne = i / 6;
            int col = i % 6;

            switch ((CarteTresor) draw.pop()) {
                case HELICO:
                    g.drawImage(
                            img_defausse[2].getScaledInstance(getWidth()/6, getHeight() / 4, Image.SCALE_DEFAULT),
                            col * getWidth() / 6,
                            ligne * getHeight() / 4,
                            null,
                            null);
                    break;
                case TRESOR_STATUE:
                    g.drawImage(
                            img_defausse[6].getScaledInstance(getWidth()/6, getHeight() / 4, Image.SCALE_DEFAULT),
                            col * getWidth() / 6,
                            ligne * getHeight() / 4,
                            null,
                            null);
                    break;
                case TRESOR_CRISTAL:
                    g.drawImage(
                            img_defausse[1].getScaledInstance(getWidth()/6, getHeight() / 4, Image.SCALE_DEFAULT),
                            col * getWidth() / 6,
                            ligne * getHeight() / 4,
                            null,
                            null);
                    break;
                case TRESOR_CALICE:
                    g.drawImage(
                            img_defausse[0].getScaledInstance(getWidth()/6, getHeight() / 4, Image.SCALE_DEFAULT),
                            col * getWidth() / 6,
                            ligne * getHeight() / 4,
                            null,
                            null);
                    break;
                case TRESOR_PIERRE:
                    g.drawImage(
                            img_defausse[4].getScaledInstance(getWidth()/6, getHeight() / 4, Image.SCALE_DEFAULT),
                            col * getWidth() / 6,
                            ligne * getHeight() / 4,
                            null,
                            null);
                    break;
                case MONTEE_EAU:
                    g.drawImage(
                            img_defausse[3].getScaledInstance(getWidth()/6, getHeight() / 4, Image.SCALE_DEFAULT),
                            col * getWidth() / 6,
                            ligne * getHeight() / 4,
                            null,
                            null);
                    break;
                case SAC_SABLE:
                    g.drawImage(
                            img_defausse[5].getScaledInstance(getWidth()/6, getHeight() / 4, Image.SCALE_DEFAULT),
                            col * getWidth() / 6,
                            ligne * getHeight() / 4,
                            null,
                            null);
                    break;
            }

        }
    }

    public void setDefausse(Stack defausse) {
        this.defausse = defausse;
        repaint();
    }


    private void init_image() {
        img_defausse = new BufferedImage[7];

        try {
            img_defausse[0] = ImageIO.read(new File("src/assets/cartes/Calice.png"));
            img_defausse[1] = ImageIO.read(new File("src/assets/cartes/Cristal.png"));
            img_defausse[2] = ImageIO.read(new File("src/assets/cartes/Helicoptere.png"));
            img_defausse[3] = ImageIO.read(new File("src/assets/cartes/MonteeDesEaux.png"));
            img_defausse[4] = ImageIO.read(new File("src/assets/cartes/Pierre.png"));
            img_defausse[5] = ImageIO.read(new File("src/assets/cartes/SacsDeSable.png"));
            img_defausse[6] = ImageIO.read(new File("src/assets/cartes/Zephyr.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
