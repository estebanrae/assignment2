package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
/**
 * Generates an error box that will display different messages.
 * @author estebanramirez
 *
 */
public class ErrorController {
	@FXML
	private Label error_msj;
	@FXML
	private Button error_btn;
	
	public void setMessage(int opt){
		if(opt == 1){
			error_msj.setText("Not enough participants signed up for this race. The game was cancelled.");
		}else if(opt == 2){
			error_msj.setText("No referee was assigned to this race. The game was cancelled.");
		}else if(opt == 3){
			error_msj.setText("No games have been played");
		}
	}
	
	public void closeWindow(ActionEvent ae){
		Node source = (Node) ae.getSource();
		source.getScene().getWindow().hide();
	    
	}
}
