package control;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import storage.Reader;
/**
 * Description: This is the controller that holds the event handlers and initialization
 * of the primary stage.
 * @author estebanramirez
 *
 */
public class Controllers {
	@FXML
	private Button btn1;
	
	
	public void initialize(){
		System.out.println("Game has started");
		Reader.initDB();
	}
	/**
	 * Generates the scene for the main menu and sets it in the primary stage.
	 * @param e -> the event that triggers this action.
	 */
	public void goToMain(ActionEvent e){
		Stage primary;
		Parent root;
		if(e.getSource() == btn1){
			primary = (Stage) btn1.getScene().getWindow();
			try {
				root = FXMLLoader.load(getClass().getResource("/graphics/Scene_2.fxml"));
				Scene sc1 = new Scene(root);
				sc1.getStylesheets().add(getClass().getResource("/graphics/Scene_2.css").toExternalForm());
				primary.setScene(sc1);
				primary.setTitle("Ozlympic Games");
				primary.setOnCloseRequest(ex ->{
					Reader.closeDB();
				});
		        primary.show();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			System.out.println("ERROR");
		}
	}
	
}
