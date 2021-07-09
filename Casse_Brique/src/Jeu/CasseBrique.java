package Jeu;

import Composants.Balle;
import Composants.Barre;
import Composants.Brique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

//La classe principale hérite de Canvas, un composant permettant de réaliser des dessins
public class CasseBrique extends Canvas implements KeyListener {
    //Propriétés
    //Dimensions de la fenetre
    private int largeur;
    private int hauteur;

    //Objets du Jeu
    private Barre barre;
    private Balle ball;
    private ArrayList<Brique> listBrique;

    //Variables de fonctionnement du JEu
    private int compteurVie;
    private int compteurPoints;
    private Boolean alive;
    private Boolean win;
    private Boolean pause;

    //Image des Objets
    private Image backImg;
    private Image backDownImg;


    public CasseBrique(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;

        //fenetre principale
        JFrame fenetre = new JFrame("Casse brique");
        //On récupère le panneau de la fenetre principale
        JPanel panneau = (JPanel) fenetre.getContentPane();
        //On définie la hauteur / largeur de l'écran
        panneau.setPreferredSize(new Dimension(this.largeur, this.hauteur));
        setBounds(0, 0, largeur, hauteur);
        //On ajoute cette classe (qui hérite de Canvas) comme composant du panneau principal
        panneau.add(this);
        fenetre.addKeyListener(this);
        fenetre.pack();
        fenetre.setResizable(false);
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.requestFocus();

        //On indique que le raffraichissement de l'ecran doit être fait manuellement.
        createBufferStrategy(2);
        setIgnoreRepaint(true);
        this.setFocusable(false);

        //on initilise l'interface
        initInterface();
        //initialisation des composants
        initJeu();
        //On appel la méthode contenant la boucle infinie qui va raffraichir notre écran
        demarrer();
    }

    //On initialise les composants
    public void initJeu() {

        //on initialise les valeurs des boolean stoppant le jeu
        alive = true;
        win = false;
        pause = true;

        //on initialise les variables de points et vies
        compteurVie = 3;
        compteurPoints = 0;

        //on initialise les objets du jeu
        ball = new Balle(Color.RED, getWidth() / 2, 0, 10, 5, -5);
        barre = new Barre(Color.ORANGE, 0, 0, 15, 105, 8);
        ball.setY(backImg.getHeight(null) - barre.getlargeur() - 10 - ball.getRayon());
        barre.setY(backImg.getHeight(null) - barre.getlargeur() - 10);
        barre.setX(getWidth() / 2 - barre.getlongueur() / 2);

        //on defini le nombre de brique dans la fenetre
        int hauteurBrique = 50;
        int longueurBrique = 50;
        int nbBriqueLargeur = getWidth() / longueurBrique - 1;
        int videLargeur = getWidth() - (longueurBrique * nbBriqueLargeur);
        int decalageLargeur = videLargeur / (nbBriqueLargeur);
        int nbBriqueHauteur = ((getHeight() / 10) * 5) / hauteurBrique;
        int videHauteur = ((getHeight() / 10) * 5) - (hauteurBrique * nbBriqueHauteur);
        int decalageHauteur = videHauteur / (nbBriqueHauteur + 1);

        //on creer les briques
        listBrique = new ArrayList<>();
        int compteurBrique = -1;
        for (int i = decalageHauteur; i <= (getHeight() / 10) * 5 - decalageHauteur; i = i + listBrique.get(compteurBrique).getlargeur() + decalageHauteur) {
            for (int j = decalageLargeur; j <= getWidth() - decalageLargeur; j = j + listBrique.get(compteurBrique).getlongueur() + decalageLargeur) {
                compteurBrique++;
                //on défini une couleur aleatoirement
                int random = (int) (Math.random() * 40);
                Color randomColor;
                if (random <= 9) {
                    randomColor = Color.MAGENTA;
                } else if (random <= 19) {
                    randomColor = Color.GREEN;
                } else if (random <= 29) {
                    randomColor = Color.GRAY;
                } else {
                    randomColor = Color.BLACK;
                }
                listBrique.add(new Brique(randomColor, j, i, hauteurBrique, longueurBrique));
            }
        }
    }

    public void initInterface() {
        Graphics2D dessin = (Graphics2D) getBufferStrategy().getDrawGraphics();
        backImg = new ImageIcon("src/Image/background.png").getImage();
        dessin.drawImage(backImg, 0, 0, null);
        backDownImg = new ImageIcon("src/Image/backdown.png").getImage();
        dessin.drawImage(backDownImg, 0, backImg.getHeight(null), null);
    }

    public void demarrer() {
        //on démarre le jeu
        //boucle infinie
        while (true) {
            //on Appel la classe Graphics2D
            Graphics2D dessin = (Graphics2D) getBufferStrategy().getDrawGraphics();
            //Mise en pause du Jeu via  la touche Echap
            while (pause) {
                writeText(dessin, "PAUSE", "Press 'ESCAPE' to Pause/Start", Color.WHITE, Color.BLACK);
                dessin.dispose();
                getBufferStrategy().show();
            }

            //on stoppe le jeu si perdu ou gagné
            while (!alive || win) {
                if (!alive) {
                    //on appel la methode qui affiche GAME OVER
                    writeText(dessin, "GAME OVER", "Press 'ENTER' to Try Again", Color.RED, Color.BLACK);
                }
                if (win) {
                    //on appel la methode qui affiche YOU WON
                    writeText(dessin, "YOU WON", "Press 'ENTER' to Try Again", Color.YELLOW, Color.BLACK);
                }
                dessin.dispose();
                getBufferStrategy().show();
            }

            //on réinitialise l'interface
            initInterface();

            //on dessine tout les compossants du jeu
            ball.dessiner(dessin);
            barre.dessiner(dessin);

            //on dessine les briques
            //le try/catch resout un crash qui apparait parfois lors d'une défaite/victoire enmpechant de relancer la parti
            try {
                for (Brique brique : listBrique) {
                    brique.dessiner(dessin);
                }
            } catch (ConcurrentModificationException ignored) {

            }

            //on initialise le deplacement de la balle
            ball.setX(ball.getX() + ball.getVitesseHorizontale());
            ball.setY(ball.getY() + ball.getVitesseVerticale());

            //on initiliatise les regles de déplacement de la barre
            if (barre.getX() < 0) {
                barre.deplacementDroite();
            } else if (barre.getX() + barre.getlongueur() > getWidth()) {
                barre.deplacementGauche();
            } else {
                barre.deplacementDroite();
                barre.deplacementGauche();
            }

            //On Verifie si la balle rencontre une brique
            for (int i = 0; i < listBrique.size(); i++) {
                //Balle Vs Brique Contact dessus/dessous
                // on verifie le positionnement en Y
                if (ball.getY() >= listBrique.get(i).getY() && ball.getY() < listBrique.get(i).getY() + listBrique.get(i).getlargeur()) {
                    // on verifie si il y a contact en X
                    if (ball.getX() + ball.getRayon() >= listBrique.get(i).getX() && ball.getX() - ball.getRayon() <= listBrique.get(i).getX() + listBrique.get(i).getlongueur()) {
                        collisionBrique(i);
                        ball.inverseVitesseHorizontale();
                    }
                }

                //Balle Vs Brique Contact gauche/droite
                // on verifie le positionnement en X
                else if (ball.getX() >= listBrique.get(i).getX() && ball.getX() <= listBrique.get(i).getX() + listBrique.get(i).getlongueur()) {
                    // on verifie si il y a contact en Y
                    if (ball.getY() + ball.getRayon() >= listBrique.get(i).getY() && ball.getY() - ball.getRayon() <= listBrique.get(i).getY() + listBrique.get(i).getlargeur()) {
                        collisionBrique(i);
                        ball.inverseVitesseVerticale();
                    }
                }
            }

            //on verifie les autres collisions
            ball.collisionBarre(barre);
            ball.collisionEcran(this);

            //on realise les actions lié au comptage des points de vie
            vie(dessin);
            //on affiche les points
            points(dessin);

            //on verifie si la partie est gagné
            if (listBrique.size() == 0) {
                win = true;
            }

            //La dernière opération consiste a valider tous nos changement et à les afficher :
            dessin.dispose();
            getBufferStrategy().show();


            //pause de quelques milisecondes afin de n'effectuer que 60 raffraichissements par seconde
            try {
                Thread.sleep(1000 / 60);
            } catch (Exception e) {
            }
        }
    }


    public void collisionBrique(int i) {
        //on attribut une action pour chaque type de brique

        if (listBrique.get(i).getCouleur().getRGB() == -16777216) {
            // BLACK.RGB = -16777216
            compteurPoints += 4;
            //on accelere le deplacement de la balle
            ball.acceler(1);


        } else if (listBrique.get(i).getCouleur().getRGB() == -65281) {
            // MAGENTA.RGB = -65281
            compteurPoints += 2;
            //longueur maxi pour grandir
            if (barre.getlongueur() <= 120) {
                barre.grandir(10);
                //on décale de 5 à gauche pour donner l'impression de manière symetrique
                barre.setX(barre.getX() - 5);
            }

        } else if (listBrique.get(i).getCouleur().getRGB() == -16711936) {
            // GREEN.RGB = -16711936
            compteurPoints += 3;
            //longueur mini pour retressir
            if (barre.getlongueur() >= 90) {
                barre.retressir(10);
                //on décale de 5 à droite pour donner l'impression de manière symetrique
                barre.setX(barre.getX() + 5);
            }


        } else if (listBrique.get(i).getCouleur().getRGB() == -8355712) {
            // GRAY.RGB = -8355712
            compteurPoints += 1;
            //vitesse mini pour ralentir
            if ((ball.getVitesseHorizontale() > 5 || ball.getVitesseHorizontale() < -5) &&
                    (ball.getVitesseVerticale() > 5 || ball.getVitesseVerticale() < -5)) {
                ball.ralentir(1);
            }

        }
        //on supprime la brique
        listBrique.remove(i);
    }

    public void vie(Graphics2D dessin) {

        //Si la barre passe sous la terre
        if (ball.getY() - ball.getRayon() >= backImg.getHeight(null)) {
            compteurVie--;
            //on remet la ball sur la barre
            ball.setX(barre.getX()+barre.getlongueur()/2);
            ball.setY(backImg.getHeight(null) - barre.getlargeur() - 10 - ball.getRayon());
            ball.inverseVitesseVerticale();

            //on laisse le temps au joueur de se remetre de ses émotions
            //on attend 1 seconde
            if (compteurVie > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //on dessine le text affichant le nombre de points
        dessin.setFont(new Font("Harrington", Font.BOLD, 25));
        dessin.setColor(Color.BLACK);
        dessin.drawString("Vie : " + compteurVie, 10, getHeight() - 10);

        //si le compteur de vie tombe à 0, c'est perdu
        if (compteurVie == 0) {
            alive = false;
        }

    }

    public void points(Graphics2D dessin) {
        //on dessine le text affichant le nombre de points
        dessin.setFont(new Font("Harrington", Font.BOLD, 25));
        String strPoints = "Points : " + compteurPoints;
        int widthText = dessin.getFontMetrics().stringWidth(strPoints);
        dessin.setColor(Color.BLACK);
        dessin.drawString(strPoints, getWidth() - widthText-5, getHeight() - 10);

    }

    public void writeText(Graphics2D dessin, String textUp, String textDown, Color colorText, Color colorRectangle) {
        //Cette classe A pour but d'afficher un Text au milieu de lécran
        Font font = new Font("Harrington", Font.BOLD, 25);
        dessin.setFont(font);
        String strPause = textUp;
        int widthText = dessin.getFontMetrics().stringWidth(strPause);
        int heightText = dessin.getFontMetrics().getHeight();

        dessin.setColor(colorRectangle);
        dessin.fillRect(getWidth() / 2 - widthText / 2 - 7, getHeight() / 2 - heightText * 2, widthText + 14, heightText + 14);

        dessin.setColor(colorText);
        dessin.drawString(strPause, getWidth() / 2 - widthText / 2, getHeight() / 2 - heightText);

        strPause = textDown;
        widthText = dessin.getFontMetrics().stringWidth(strPause);
        heightText = dessin.getFontMetrics().getHeight();

        dessin.setColor(colorRectangle);
        dessin.fillRect(getWidth() / 2 - widthText / 2 - 7, getHeight() / 2 + heightText / 2 - 14, widthText + 14, heightText + 14);

        dessin.setColor(colorText);
        dessin.drawString(strPause, getWidth() / 2 - widthText / 2, getHeight() / 2 + heightText);
    }

    //KeyListener
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            barre.setVersGauche(true);
            barre.setVersDroite(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            barre.setVersDroite(true);
            barre.setVersGauche(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pause = !pause;
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!alive || win) {
                initJeu();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            barre.setVersGauche(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            barre.setVersDroite(false);
        }

    }
}



