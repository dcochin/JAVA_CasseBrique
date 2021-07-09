package Composants;

import Jeu.CasseBrique;

import java.awt.*;

public class Balle extends Sphere {


    private int vitesseHorizontale;
    private int vitesseVerticale;

    //GETTER
    public int getVitesseHorizontale() {
        return vitesseHorizontale;
    }
    public int getVitesseVerticale() {
        return vitesseVerticale;
    }

    //CONSTRUCTEUR
    public Balle(Color couleur, int x, int y, int rayon, int vH, int vV) {
        super(couleur, x, y, rayon);
        this.vitesseHorizontale = vH;
        this.vitesseVerticale = vV;
    }

    //METHODES
    public void inverseVitesseHorizontale() {
        this.vitesseHorizontale = this.vitesseHorizontale * -1;
    }

    public void inverseVitesseVerticale() {
        this.vitesseVerticale = this.vitesseVerticale * -1;
    }

    public void acceler(int A) {
        if (vitesseHorizontale >= 1) {
            vitesseHorizontale += A;
        } else if (vitesseHorizontale <= -1) {
            vitesseHorizontale -= A;
        }

        if (vitesseVerticale >= 1) {
            vitesseVerticale += A;
        } else if (vitesseVerticale <= -1) {
            vitesseVerticale -= A;
        }
    }

    public void ralentir(int R) {
        if (vitesseHorizontale >= 1) {
            vitesseHorizontale -= R;
        } else if (vitesseHorizontale <= -1) {
            vitesseHorizontale += R;
        }

        if (vitesseVerticale >= 1) {
            vitesseVerticale -= R;
        } else if (vitesseVerticale <= -1) {
            vitesseVerticale += R;
        }

    }

    public void collisionBarre(Barre barre) {
        //on verifie la position en Y
        if (barre.getY() <= getY() + getRayon() && barre.getY() + barre.getlargeur() >= getY() + getRayon()) {
            //on verifie la position en Y
            if (getX() >= barre.getX() && getX() <= barre.getX() + barre.getlongueur()) {
                //si la balle descend, elle est renvoyé
                //on verifie cette contion pour eviter de voir rebondir la balle dans la barre plusieurs fois avant de repartir
                if (getVitesseVerticale() > 0) {
                    inverseVitesseVerticale();
                }
            }
        }
    }

    public void collisionEcran(CasseBrique casseBrique) {
        //si collision avec les bordures Gauche/Droite
        if (getX() - getRayon() <= 0 || getX() + getRayon() >= casseBrique.getWidth()) {
            inverseVitesseHorizontale();
        }
        //si collision avec la bordure Haute
        if (getY() - getRayon() <= 0) {
            inverseVitesseVerticale();
        }

    }

}
