package Composants;


import java.awt.*;

public abstract class Sprite {
    private Color couleur;
    private int x;
    private int y;


    //GETTER
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getCouleur() {
        return couleur;
    }

    //SETTER
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    //CONSTRUCTEUR
    public Sprite(Color couleur, int x, int y) {
        this.couleur = couleur;
        this.x = x;
        this.y = y;
    }

    //METHODES
    public void dessiner(Graphics2D g) {
    }

}

