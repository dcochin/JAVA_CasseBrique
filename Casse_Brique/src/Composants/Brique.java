package Composants;

import javax.swing.*;
import java.awt.*;


public class Brique extends Rectangle {

    //CONSTRUCTEUR
    public Brique(Color couleur, int x, int y, int largeur, int longueur) {
        super(couleur, x, y, largeur, longueur);
    }

    //METHODES
    @Override
    public void dessiner(Graphics2D g) {
        Image ballImage;
        //on attribut une image pour chaque couleur de brique
        //on garde la notion de couleur dans le cas ou on ne voudrait pas des images
        if (getCouleur().getRGB() == -16777216) {
            // BLACK.RGB = -16777216
            ballImage = new ImageIcon("src/Image/brique_noir.png").getImage();
            g.drawImage(ballImage, getX(), getY(), null);


        } else if (getCouleur().getRGB() == -65281) {
            // MAGENTA.RGB = -65281
            ballImage = new ImageIcon("src/Image/brique_rose.png").getImage();
            g.drawImage(ballImage, getX(), getY(), null);


        } else if (getCouleur().getRGB() == -16711936) {
            // GREEN.RGB = -16711936
            ballImage = new ImageIcon("src/Image/brique_vert.png").getImage();
            g.drawImage(ballImage, getX(), getY(), null);

        } else if (getCouleur().getRGB() == -8355712) {
            // GRAY.RGB = -8355712
            ballImage = new ImageIcon("src/Image/brique.png").getImage();
            g.drawImage(ballImage, getX(), getY(), null);
        }


    }
}



