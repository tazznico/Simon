package ButtonColor;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

/**
 * Class enfant de bouton, elle permet de créer un bouton avec des paramètres
 * gérant ça couleur.
 *
 * @author tazznico
 */
public class ButtonColor extends Button {

    private final Color color;
    private final Color colorShine;
    private final int freq;

    /**
     * Constructeur de ButtonColor.
     *
     * @param color couleur de baase du bouton
     * @param colorShine couleur du bouton illuminé
     * @param freq entier, fréquence du son
     */
    public ButtonColor(Color color, Color colorShine, int freq) {
        this.color = color;
        this.colorShine = colorShine;
        this.freq = freq;
    }

    /**
     * Constructeur de ButtonColor.
     *
     * @param color couleur de baase du bouton
     * @param colorShine couleur du bouton illuminé
     * @param freq entier, fréquence du son
     * @param string Nom du bouton
     */
    public ButtonColor(Color color, Color colorShine, int freq, String string) {
        super(string);
        this.color = color;
        this.colorShine = colorShine;
        this.freq = freq;
    }

    /**
     * Constructeur de ButtonColor.
     *
     * @param color couleur de baase du bouton
     * @param colorShine couleur du bouton illuminé
     * @param freq entier, fréquence du son
     * @param string Nom du bouton
     * @param node
     */
    public ButtonColor(Color color, Color colorShine, int freq, String string, Node node) {
        super(string, node);
        this.color = color;
        this.colorShine = colorShine;
        this.freq = freq;
    }

    /**
     * getteur de couleur principale
     *
     * @return Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * getteur de couleur illuminé
     *
     * @return Color
     */
    public Color getColorShine() {
        return colorShine;
    }

    /**
     * getteur de la fréquence du son
     *
     * @return int
     */
    public int getFreq() {
        return freq;
    }

    /**
     * Permet de changer la couleur du bouton appelant cette méthode
     *
     * @param color couleur que l'on veut mettre
     */
    private void changeBackGround(Color color) {
        this.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * Donne les paramètres de base du bouton appelant cette méthode
     *
     * @param nameButton String du nom du bouton
     */
    public void iniButton(String nameButton) {
        this.setPrefSize(250, 250);
        this.setBackground(new Background(new BackgroundFill(this.color, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setText(nameButton);
        this.setId(nameButton);
        this.setTextFill(Color.WHITE);
        //this.setStyle("-fx-opacity: 100%");        
    }

    /**
     * Permet de jouer le son pendant 1 seconde si le boolean est true pour le
     * bouton appelant cette méthode.
     *
     * @param sound boolean
     */
    public void sound(boolean sound) {
        if (sound) {
            try {
                var synth = MidiSystem.getSynthesizer();
                synth.open();
                var channel = synth.getChannels()[0];
                channel.noteOn(this.freq, 80);
                var pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e
                        -> channel.noteOff(this.freq)
                );
                pause.play();
            } catch (MidiUnavailableException ex) {
                Logger.getLogger(ButtonColor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Permet de changer la couleur pendant 1 seconde.
     */
    public void ChangeColor() {
        this.changeBackGround(this.colorShine);
        var pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e
                -> this.changeBackGround(this.color)
        );
        pause.play();
    }
    
        /**
     * permet de changer la grandeur du text quand on passe sur le bouton
     */
    public void onBoutonColor() {
        this.setOnMouseEntered(e -> {
           this.setStyle("-fx-font: 20 arial");
           //this.setStyle("-fx-font-weight: bold"); 
        });
        this.setOnMouseExited(ev -> {
           this.setStyle("-fx-font: 12 arial");            
           //this.setStyle("-fx-font-weight: regular"); 
        });
    }
}
