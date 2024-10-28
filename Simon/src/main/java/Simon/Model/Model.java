package Simon.Model;

import Simon.Observer.Observable;
import Simon.Observer.Observer;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 * Class qui gère les vérification du jeu
 *
 * @author tazznico
 */
public class Model implements Observable {

    private ArrayList<Observer> listObserver;
    private ArrayList<Color> listColorPlayed;
    private ArrayList<Color> listColorClick;
    private ArrayList<Color> listColorLongest;
    private ArrayList<Color> listColorLast;
    private int pointer;
    private boolean answer;
    private boolean EndList;
    private boolean state;

    /**
     * Constructeur du Model
     */
    public Model() {
        this.listObserver = new ArrayList<>();
        this.pointer = 0;
        this.EndList = false;
    }

    @Override
    public void subscribe(Observer observer) {
        //vérifié si observer déjà dedans
        this.listObserver.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        if (this.listObserver.size() == 0) {
            //erreur
        } else {
            this.listObserver.remove(observer);
        }
    }

    @Override
    public void notify(ArrayList<Observer> listObserver) {
        for (Observer observer : listObserver) {
            observer.update();
        }
    }

    public void initialize() {
    }

    /**
     * Méthode permettant de permettant d'initialiser les paramètre nécéssaure
     * au fonctionnement au lancement du jeu.
     */
    public void start() {
        this.listColorClick = new ArrayList<>();
        this.listColorPlayed = new ArrayList<>();
        this.listColorPlayed.add(this.randomColor());
        this.EndList = true;
        this.state = true;
        notify(this.listObserver);
    }

    /**
     * Méthode vérifiant si le click du joueur est bon ou pas Su bon, rajoute
     * une couleur à jouer dans la playSéquence Si pas bon, arrête le jeu.
     *
     * @param color Couleur du click du joueur
     */
    public void click(Color color) {
        this.listColorClick.add(color);

        System.out.println("pointer: " + pointer);
        Color ColorClick = listColorClick.get(0);
        Color colorPlayed = listColorPlayed.get(pointer);

        if (pointer < listColorPlayed.size()) {
            this.EndList = false;
            if (ColorClick.equals(colorPlayed)) {
                this.listColorClick.remove(0);
                this.pointer++;

                if (this.listColorPlayed.size() == pointer) {
                    this.listColorPlayed.add(randomColor());
                    this.pointer = 0;
                    this.EndList = true;
                }
                this.answer = true;
                notify(this.listObserver);
            } else if (!ColorClick.equals(colorPlayed)) {
                if (this.listColorLongest == null || this.listColorLongest.size() < this.listColorPlayed.size()) {
                    this.listColorLongest = this.listColorPlayed;
                    this.EndList = true;
                }
                this.answer = false;
                this.EndList = true;
                this.state = false;
                this.pointer = 0;
                last(this.listColorPlayed);
                notify(this.listObserver);
            } else {
                //erreure
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("pointeur en dehors de la"
                    + " liste, pointer: " + this.pointer);
        }

    }

    /**
     * Méthode créer un int random entre 0 et 3 et permet de choisir une des 4
     * couleur du jeu.
     *
     * @return Color alétoire
     */
    private Color randomColor() {
        Random random = new Random();
        int numberR = random.nextInt(4);
        switch (numberR + 1) {
            case 1:
                return Color.DARKRED;

            case 2:
                return Color.DARKBLUE;

            case 3:
                return Color.DARKGREEN;

            case 4:
                return Color.GOLDENROD;
            default:
                throw new IllegalArgumentException("Chiffre alétoire entre 1 et"
                        + " 4 pas bon, chiffre R: " + numberR);
        }
    }

    /**
     * Permet de save la list de couleurs la plus longue
     *
     * @param listColorcurrent ArrayList de couleurs
     */
    public void longest(ArrayList<Color> listColorcurrent) {
        this.listColorLongest = listColorcurrent;
        notify(this.listObserver);
    }

    /**
     * Permet de save la dernière list de couleurs joué
     *
     * @param listColorcurrent ArrayList de couleurs
     */
    public void last(ArrayList<Color> listColorcurrent) {
        this.listColorLast = listColorcurrent;
        notify(this.listObserver);
    }

    /**
     * getteur de la liste de couleurs la plus longue
     *
     * @return ArrayList de couleurs
     */
    public ArrayList<Color> getListColorLongest() {
        return listColorLongest;
    }

    /**
     * getteur de la dernière liste de couleurs
     *
     * @return ArrayList de couleurs
     */
    public ArrayList<Color> getListColorLast() {
        return listColorLast;
    }

    /**
     * getteur de la liste de couleurs à jouer
     *
     * @return ArrayList de couleurs
     */
    public ArrayList<Color> getListColorPlayed() {
        return listColorPlayed;
    }

    /**
     * getteur de la liste clicker par le joueur
     *
     * @return ArrayList de couleurs
     */
    public ArrayList<Color> getListColorClick() {
        return listColorClick;
    }

    /**
     * getteur du pointer/lvl du joueur
     *
     * @return int pointeur/lvl
     */
    public int getPointer() {
        return pointer;
    }

    /**
     * getteur de la réponse, si le joueur à eu bon = true, si non, false
     *
     * @return boolean la réponse
     */
    public boolean getAnswer() {
        return answer;
    }

    /**
     * getteur de si on arrive en fin de list played = true, si non false;
     *
     * @return
     */
    public boolean getEndList() {
        return EndList;
    }

    /**
     * getteur de l'état du jeu, true si lancé et false, si arrêter.
     *
     * @return boolean état du jeu
     */
    public boolean isState() {
        return state;
    }
}
