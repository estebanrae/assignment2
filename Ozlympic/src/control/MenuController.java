package control;

import java.io.IOException;
import java.sql.SQLException;

import exceptions.GenericGameException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * Holds the event handlers and actions for all interaction in the main menu.
 * @author estebanramirez
 *
 */
public class MenuController {
	@FXML
	private Button op_btn1;
	@FXML
	private Button op_btn2;
	@FXML
	private Button op_btn3;
	@FXML
	private Button op_btn4;
	@FXML
	private Button op_btn5;
	@FXML
	private Button add_btn;
	
	public void initialize(){
		System.out.println("Main Menu displayed.");
	}
	/**
	 * Sets the new stage to the main lobby, according to which button was pressed, it
	 * will generate a different type of game.
	 * @param e
	 * @throws GenericGameException
	 */
	public void goToLobby(ActionEvent e) throws GenericGameException{
		Stage lobby = new Stage();
		FXMLLoader root;
		try {
			root = new FXMLLoader(getClass().getResource("/graphics/Lobby.fxml"));
			Scene sc1 = new Scene(root.load());
			sc1.getStylesheets().add(getClass().getResource("/graphics/Lobby.css").toExternalForm());
			ControlLobby controller = root.<ControlLobby>getController();
			
			if(e.getSource() == op_btn1){
				controller.generateGame(1);	
			}else if(e.getSource() == op_btn2){
				controller.generateGame(2);
			}else if(e.getSource() == op_btn3){
				controller.generateGame(3);
			}
			controller.listOfAthletes();
			controller.listOfOfficials();
			lobby.setScene(sc1);
			lobby.setResizable(false);
			lobby.setTitle("Lobby");
			lobby.setOnCloseRequest(ae ->{
				controller.getCounter().stop();
			});
			System.out.println("New game created, Lobby displayed.");
			lobby.show();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	/**
	 * Displays the list of results for each race that has run.
	 * @param ae
	 */
	public void raceResults(ActionEvent ae){
		try {
			Stage resultStage = new Stage();
			FXMLLoader root;
			root = new FXMLLoader(getClass().getResource("/graphics/Race_Results.fxml"));
			Scene sc1;
			sc1 = new Scene(root.load());
			sc1.getStylesheets().add(getClass().getResource("/graphics/Race_Results.css").toExternalForm());
			resultStage.setScene(sc1);
			resultStage.setResizable(false);
			resultStage.setTitle("Results");
			resultStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Displays the total points per athlete.
	 * @param ae
	 */
	public void totalPoints(ActionEvent ae){
		try {
			Stage resultStage = new Stage();
			FXMLLoader root;
			root = new FXMLLoader(getClass().getResource("/graphics/Total_Points.fxml"));
			Scene sc1;
			sc1 = new Scene(root.load());
			sc1.getStylesheets().add(getClass().getResource("/graphics/Total_Points.css").toExternalForm());
			resultStage.setScene(sc1);
			resultStage.setResizable(false);
			resultStage.setTitle("Points");
			resultStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
