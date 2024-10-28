package Simon.Controller;

import Simon.Model.Model;
import Simon.view.View;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * fait le pont entre la class view et model
 *
 * @author tazznico
 */
public class Controller {

    private Model model;
    private View view;

    /**
     * Constructeur du Controller
     *
     * @param model Model
     * @param stage Stage
     * @throws Exception
     */
    public Controller(Model model, Stage stage) throws Exception {
        this.model = model;
        this.view = new View(this, model);
        this.view.start(stage);
        model.initialize();
    }

    /**
     * Appelle la méthode start de Model
     */
    public void Start() {
        model.start();
    }

    /**
     * Appelle la méthode click du Model
     *
     * @param color Couleur du bouton
     */
    public void click(Color color) {
        model.click(color);
    }
}
