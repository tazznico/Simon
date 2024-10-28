package Simon.view;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.sound.midi.MidiSystem;
import ButtonColor.ButtonColor;
import Simon.Controller.Controller;
import Simon.Model.Model;
import Simon.Observer.Observer;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Mise en place des différents éléments de la vue du Simon
 *
 * @author tazznico
 */
public class View implements Observer {

    private Controller controller;
    private Model model;

    /**
     * Constructeur de la classe View et la mets en observateur de la classe
     * Model
     *
     * @param controller Controller
     * @param model Model
     */
    public View(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
        model.subscribe(this);
    }

    private Button Start, Longest, Last;
    private ButtonColor RED, GREEN, BLUE, YELLOW;
    private CheckBox Mute;
    private Slider Speed;
    private GridPane Grid_Centrale;
    private StackPane stackPane;
    private HBox Hbox_haut, Hbox_bas;
    private BorderPane root;
    private Scene scene;

    private int lvl;
    private ArrayList<Color> listColorPlayed;
    private ArrayList<Color> ListColorLongest;
    private ArrayList<Color> ListColorLast;
    private boolean answer;
    private boolean state;

    @Override
    public void update() {
        this.lvl = model.getPointer();
        this.listColorPlayed = model.getListColorPlayed();
        this.ListColorLongest = model.getListColorLongest();
        this.ListColorLast = model.getListColorLast();
        this.answer = model.getAnswer();
        this.state = model.isState();
    }

    /**
     * Permet d'initialiser tout les boutons nécessaire au jeu
     *
     * @param primaryStage
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Jeu du Simon");

        var synthe = MidiSystem.getSynthesizer();
        synthe.open();

        //Start Button
        this.Start = new Button();
        this.Start.setPrefSize(100, 100);
        this.Start.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        this.Start.setTextFill(Color.WHITE);
        this.Start.setText("START");
        clickButtonStart(this.Start);
        //this.Start.setStyle("-fx-opacity: 100%");

        //Button HboxHaut Longest and Last
        //Longest
        this.Longest = new Button();
        this.Longest.setPrefSize(250, 25);
        this.Longest.setText("Longest");
        this.Longest.setId("longest");
        this.Longest.setTextFill(Color.BLACK);
        playSeqLongestOrLast(this.Longest);

        //Last
        this.Last = new Button();
        this.Last.setPrefSize(250, 25);
        this.Last.setId("last");
        this.Last.setText("Last");
        this.Last.setTextFill(Color.BLACK);
        playSeqLongestOrLast(this.Last);

        //Button HboxBas Mute and Speed
        //Mute
        this.Mute = new CheckBox();
        this.Mute.setPrefSize(125, 25);
        this.Mute.setText("Mute");
        this.Mute.setTextFill(Color.BLACK);
        this.Mute.setIndeterminate(false);

        //Speed
        this.Speed = new Slider(1.0, 2.0, 1.0);
        this.Speed.setPrefSize(250, 25);
        this.Speed.setShowTickMarks(true);
        this.Speed.setShowTickLabels(true);
        this.Speed.setMajorTickUnit(0.250);

        //Button hautGauche
        this.RED = new ButtonColor(Color.DARKRED, Color.RED, 69);
        this.RED.iniButton("RED");
        clickButtonColor(this.RED);

        //Button HautDroite
        this.GREEN = new ButtonColor(Color.DARKGREEN, Color.GREEN, 71);
        this.GREEN.iniButton("GREEN");
        clickButtonColor(this.GREEN);

        //Button BasGauche
        this.BLUE = new ButtonColor(Color.DARKBLUE, Color.BLUE, 72);
        this.BLUE.iniButton("BLUE");
        clickButtonColor(this.BLUE);

        //Button BasDroite
        this.YELLOW = new ButtonColor(Color.GOLDENROD, Color.GOLD, 74);
        this.YELLOW.iniButton("YELLOW");
        clickButtonColor(this.YELLOW);

        //Hbox
        //Haut
        this.Hbox_haut = new HBox();
        this.Hbox_haut.setPrefSize(500, 25);
        this.Hbox_haut.setAlignment(Pos.CENTER);
        this.Hbox_haut.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.Hbox_haut.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.Hbox_haut.getChildren().addAll(Longest, Last);
        this.Hbox_haut.setStyle("-fx-border-width: 2");
        this.Hbox_haut.setStyle("-fx-border-color: BLACK");
        //Bas
        this.Hbox_bas = new HBox();
        this.Hbox_bas.setPrefSize(500, 25);
        this.Hbox_bas.setAlignment(Pos.CENTER);
        this.Hbox_bas.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.Hbox_bas.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.Hbox_bas.getChildren().addAll(Mute, Speed);
        this.Hbox_bas.setStyle("-fx-border-width: 2");
        this.Hbox_bas.setStyle("-fx-border-color: BLACK");

        //gridPane
        this.Grid_Centrale = new GridPane();
        this.Grid_Centrale.setAlignment(Pos.CENTER);
        this.Grid_Centrale.add(RED, 0, 0);
        this.Grid_Centrale.add(GREEN, 0, 1);
        this.Grid_Centrale.add(BLUE, 1, 0);
        this.Grid_Centrale.add(YELLOW, 1, 1);
        this.Grid_Centrale.setStyle("-fx-border-width: 2");
        this.Grid_Centrale.setStyle("-fx-border-color: BLACK");

        //StackPane
        this.stackPane = new StackPane();
        this.stackPane.setAlignment(Pos.CENTER);
        this.stackPane.getChildren().addAll(this.Grid_Centrale, this.Start);

        //BorderPane
        this.root = new BorderPane();
        this.root.setPrefSize(500, 550);
        this.root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.root.setTop(this.Hbox_haut);
        this.root.setCenter(this.stackPane);
        this.root.setBottom(this.Hbox_bas);

        //Scene
        this.scene = new Scene(this.root, 500, 550);
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }

    /**
     * Permet de désactiver les boutons de couleurs si c'est sur true et
     * réactiver si c'est sur false
     *
     * @param disable
     */
    private void checkButtonColor(boolean disable) {
        RED.setDisable(disable);
        GREEN.setDisable(disable);
        BLUE.setDisable(disable);
        YELLOW.setDisable(disable);
    }

    /**
     * Joue une séquence de couleurs donné par la model avec un nombre de
     * seconde défini par le slider Speed.
     *
     * @param listColor liste de couleurs à jouer
     */
    private void playSequence(List<Color> listColor) {
        disableAll(true);
        var timeline = new Timeline(new KeyFrame(
                Duration.millis(this.Speed.getValue() * 1000), new EventHandler<ActionEvent>() {
            int pointer = 0;

            @Override
            public void handle(ActionEvent e) {
                Color color = listColor.get(pointer);
                if (color.equals(Color.DARKRED)) {
                    RED.ChangeColor();
                    RED.sound(!Mute.isSelected());
                    System.out.println("red");
                } else if (color.equals(Color.DARKGREEN)) {
                    GREEN.ChangeColor();
                    GREEN.sound(!Mute.isSelected());
                    System.out.println("green");
                } else if (color.equals(Color.DARKBLUE)) {
                    BLUE.ChangeColor();
                    BLUE.sound(!Mute.isSelected());
                    System.out.println("blue");
                } else if (color.equals(Color.GOLDENROD)) {
                    YELLOW.ChangeColor();
                    YELLOW.sound(!Mute.isSelected());
                    System.out.println("yellow");
                }
                pointer++;
                if(pointer == listColor.size()) {
                    disableAll(false);
                }
            }
        })
        );
        timeline.setCycleCount(listColor.size());
        timeline.play();
    }

    /**
     * Permet le click sur un buttonColor pour permettre de changer, jouer et
     * bloquer les boutons pendant un temps définit de 1 seconde.
     *
     * @param button un bouton de couleurs
     */
    private void clickButtonColor(ButtonColor button) {
        button.setOnAction(e -> {
            model.click(button.getColor());
            checkIfPlaySeq();
            button.ChangeColor();
            checkButtonColor(true);
            button.sound(!Mute.isSelected());
            var pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event
                    -> checkButtonColor(false)
            );
            System.out.println("answer: " + this.answer + " state: " + this.state);
            if (!this.answer && !this.state) {
                this.Start.setDisable(false);
            }
            pause.play();
        });
    }

    /**
     * Permet le click du boutton start et play le séquence et le désactive
     *
     * @param button bouton
     */
    private void clickButtonStart(Button button) {
        button.setOnAction(e -> {
            controller.Start();
            checkIfPlaySeq();
            this.Start.setDisable(true);
            this.Start.setText("RESTART");
        });
    }

    /**
     * regarde si il peut jouer la séquence de couleur oui, si endList est sur
     * true et si state est sur true aussi non, ne joue pas la séquence
     */
    private void checkIfPlaySeq() {
        if (model.getEndList() && model.isState()) {
            playSequence(this.listColorPlayed);
        }
    }

    /**
     * vérifier quel boutons à été passé en paramètre et joue la séquence de ce
     * bouton
     *
     * @param button Bouton
     */
    private void playSeqLongestOrLast(Button button) {
        button.setOnAction(e -> {
            switch (button.getId()) {
                case "longest":
                    if (this.ListColorLongest != null) {
                        if (!this.ListColorLongest.isEmpty()) {
                            playSequence(this.ListColorLongest);
                        }
                    }
                    break;
                case "last":
                    if (this.ListColorLast != null) {
                        if (!this.ListColorLast.isEmpty()) {
                            playSequence(this.ListColorLast);
                        }
                    }
                    break;
                default:
                    System.out.println("problème");
            }
        });
    }
    
    private void disableAll(boolean disable) {
        this.RED.setDisable(disable);
        this.BLUE.setDisable(disable);
        this.GREEN.setDisable(disable);
        this.YELLOW.setDisable(disable);

        this.Start.setDisable(disable);
        this.Longest.setDisable(disable);
        this.Last.setDisable(disable);
    }
}
