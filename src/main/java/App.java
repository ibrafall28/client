import javafx.application.Application;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projet.utils.LoadView;
import projet.utils.Outils;


public class App extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception{
            LoadView.showView("cv","login.fxml",1);

        }


        public static void main(String[] args) {
            launch(args);
        }
    }


