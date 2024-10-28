package Simon;

import Simon.Controller.Controller;
import Simon.Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Class principale du jeu.
 *
 * @author tazznico
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Model();
        Controller controller = new Controller(model, stage);
    }
}
