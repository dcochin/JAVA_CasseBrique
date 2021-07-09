package Composants;

import javax.swing.*;
import java.awt.*;

public class Barre extends Rectangle {
    private int vitesse;
    private boolean versDroite = false;
    private boolean versGauche = false;

    //GETTER
    public boolean isVersDroite() {
        return versDroite;
    }

    public boolean isVersGauche() {
        return versGauche;
    }

    // SETTER
    public void setVersDroite(boolean versDroite) {
        this.versDroite = versDroite;
    }

    public void setVersGauche(boolean versGauche) {
        this.versGauche = versGauche;
    }

    //CONSTRUCTEUR
    public Barre(Color couleur, int x, int y, int largeur, int longueur, int vitesse) {
        super(couleur, x, y, largeur, longueur);
        this.vitesse = vitesse;
    }

    //METHODES
    public void deplacementDroite() {

        if (isVersDroite()) {
            setX(getX() + vitesse);
        }
    }

    public void deplacementGauche() {
        if (isVersGauche()) {
            setX(getX() - vitesse);
        }
    }

    public void grandir(int grandir) {
        setlongueur(getlongueur() + grandir);
    }

    public void retressir(int retressir) {
        setlongueur(getlongueur() - retressir);
    }


    @Override
    public void dessiner(Graphics2D g) {
        Image leftImage = new ImageIcon("src/Image/barre_gauche.png").getImage();
        Image rightImage = new ImageIcon("src/Image/barre_droite.png").getImage();
        g.drawImage(leftImage, getX(), getY(), null);
        g.drawImage(rightImage, getX() + getlongueur() - rightImage.getWidth(null), getY(), null);
    }
}
