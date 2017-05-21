package control;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * This is the main class for the program. it generates the primary stage and holds the
 * application's start() method. This will display the main stage.
 * @author estebanramirez
 *
 */
public class Ozlympic extends Application {
	
	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/graphics/Scene_1.fxml"));
			Scene sc1 = new Scene(root);
			sc1.getStylesheets().add(getClass().getResource("/graphics/Scene_1.css").toExternalForm());
			primaryStage.setScene(sc1);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Ozlympic Games");
	        primaryStage.show();
		} catch (IOException e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}

}
