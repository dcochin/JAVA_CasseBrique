package Composants;

import java.awt.*;


public class Rectangle extends Sprite {
    private int largeur;
    private int longueur;

    //GETTER
    public int getlargeur() {
        return largeur;
    }

    public int getlongueur() {
        return longueur;
    }

    // SETTER
    public void setlongueur(int longueur) {
        this.longueur = longueur;
    }

    //CONSTRUCTEUR
    public Rectangle(Color couleur, int x, int y, int largeur, int longueur) {
        super(couleur, x, y);
        this.largeur = largeur;
        this.longueur = longueur;
    }

    //METHODE
    //Dans le case ou on ne veux pas des images
//    @Override
//    public void dessiner(Graphics2D g) {
//        g.setColor(getCouleur());
//        g.fillRect(getX(), getY(), longueur, largeur);
//    }


}
