package Composants;


import java.awt.*;


public class Sphere extends Sprite {
    private int rayon;

    //CONSTRUCTEUR
    public Sphere(Color couleur, int x, int y, int rayon) {
        super(couleur, x, y);
        this.rayon = rayon;
    }

    //GETTER
    public int getRayon() {
        return rayon;
    }

    //SETTER
    public void setRayon(int rayon) {
        this.rayon = rayon;
    }

    @Override
    public void dessiner(Graphics2D g) {
        //si on veux une image
        //Image ballImage = new ImageIcon("src/Image/ball.png").getImage();
        //g.drawImage(ballImage, getX() - getRayon(), getY() - getRayon(), null);

        g.setColor(getCouleur());
        g.fillOval(getX() - getRayon(), getY() - getRayon(), getRayon() * 2, getRayon() * 2);

    }
}