package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import exceptions.DuplicateAthleteException;
import exceptions.GameFullException;
import exceptions.GenericGameException;
import exceptions.ImageNotFoundException;
import exceptions.NoRefereeException;
import exceptions.TooFewAthleteException;
import exceptions.WrongTypeException;
import games.CycleGame;
import games.Game;
import games.RunningGame;
import games.SwimGame;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import participants.Athlete;
import participants.CanCycle;
import participants.CanRun;
import participants.CanSwim;
import participants.Cyclist;
import participants.Official;
import participants.Sprinter;
import participants.SuperAthlete;
import participants.Swimmer;
import storage.Reader;
/**
 * Description: Holds all the interactions and event handlers that happen in the Lobby 
 * stage. 
 * @author estebanramirez
 *
 */
public class ControlLobby {
	static private Game currentGame;
	private int timerCounter;
	private Timeline counter;
	@FXML
	private VBox ppt_list;
	@FXML
	private Label counter_container;
	@FXML
	private Button start_btn;
	@FXML
	private ChoiceBox<Athlete> athlete_select;
	@FXML
	private ChoiceBox<Official> official_select;
	@FXML
	private Label error_msg;
	@FXML
	private Label official_label;
	@FXML
	protected void initialize(){
		/*
		 * Generates a countdown timer.
		 */
		timerCounter = 60;
		counter = new Timeline(new KeyFrame(
				Duration.millis(1000),
				ae -> countdown(ae)));
		counter.setCycleCount(Timeline.INDEFINITE);
		counter.playFromStart();
	}
	/**
	 * Generates a new game of a certain type.
	 * @param opt -> the type of game that will be generated (1 = Running, 2 = Swimming,
	 * 3 = Cycling)
	 * @throws GenericGameException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void generateGame(int opt) throws GenericGameException, ClassNotFoundException, SQLException{
		currentGame = Reader.createNewGame(opt);
	}

	public Timeline getCounter(){
		return counter;
	}
	/**
	 * Description: Generates the dropdown list of athletes depending of the type of game
	 * that was initialized.
	 * @throws SQLException
	 */
	public void listOfAthletes() throws SQLException{
		LinkedList<Athlete> athleteList = new LinkedList<Athlete>();
		Athlete[] aux = Reader.readAthletes("name");
		Athlete randAthl = null;
		randAthl = new Athlete("00", "Random"){
			@Override
			public void compete(Game x) {
			}
		};
		for(Athlete ath : aux){
			if(currentGame instanceof RunningGame && ath instanceof CanRun ||
				currentGame instanceof SwimGame && ath instanceof CanSwim ||
				currentGame instanceof CycleGame && ath instanceof CanCycle){
				athleteList.add(ath);
			}
		}
		
		/* This is necessary in order to store each athlete as an object in the
		 * ChoisePane but only displaying the Name. 
		 */
		Athlete[] allAthletes = new Athlete[athleteList.size()];
		athleteList.<Athlete>toArray(allAthletes);
		athlete_select.setConverter(new AthlConverter());
		athlete_select.getItems().add(randAthl);
		for(Athlete a: allAthletes){
			if(a != null){
				athlete_select.getItems().add(a);
			}else{
				break;
			}
		}
		athlete_select.getSelectionModel().select(0);
	}
	/**
	 * Description: Generates the list of officials available.
	 */
	public void listOfOfficials(){
		int cnt = 0;
		Official[] allOfficials = new Official[100];
		Official noOp = new Official("00", "--No official selected --");
		for(Official ath : Reader.readOfficials()){
			allOfficials[cnt] = ath;
			cnt++;
		}
		official_select.setConverter(new OffConverter());
		official_select.getItems().add(noOp);
		for(Official a: allOfficials){
			if(a != null){
				official_select.getItems().add(a);
			}else{
				break;
			}
		}
		//athlete_select.setValue("Random");
		official_select.getSelectionModel().select(0);
	}
	/**
	 * This function is called whenever a second passes to check if the countdown has 
	 * finished.
	 * @param ee
	 */
	public void countdown(ActionEvent ee){
		if(timerCounter == 0){
			counter.stop();
			try{
				startRace();		
			}catch(TooFewAthleteException ex){
				showError(1);
				cancelGame(1);
				ex.printStackTrace();
			}catch(NoRefereeException nre){
				showError(4);
				cancelGame(2);
				nre.printStackTrace();
			}
			return;
		}
		timerCounter--;
		counter_container.setText(Integer.toString(timerCounter));
	}
	/**
	 * Adds a new athlete to the current game depending on the choice selected in the 
	 * ChoicePane node.
	 * @param e -> Triggered by the "Add Athlete" button.
	 */
	public void addAthlete(ActionEvent e){
		try {
			Athlete curr;
			error_msg.setText("");
			if(currentGame instanceof RunningGame){
				if (athlete_select.getValue().getID() == "00"){
					curr = (Athlete) ((RunningGame) currentGame).addSprinter();
				}else{
					curr = (Athlete) ((RunningGame) currentGame).addSprinter(athlete_select.getValue());
				}
				if(!(curr instanceof Sprinter || curr instanceof SuperAthlete)){
					throw new WrongTypeException();
				}
				Label line = new Label(curr.getName());
				line.getStyleClass().add("ppt_line");
				ppt_list.getChildren().add(line);
			}else if(currentGame instanceof SwimGame){
				if (athlete_select.getValue().getID() == "00"){
					curr = (Athlete) ((SwimGame) currentGame).addSwimmer(); 
				}else{
					curr = (Athlete) ((SwimGame) currentGame).addSwimmer( athlete_select.getValue());
				} 
				if(!(curr instanceof Swimmer || curr instanceof SuperAthlete)){
					throw new WrongTypeException();
				}
				Label line = new Label(curr.getName());
				line.getStyleClass().add("ppt_line");
				ppt_list.getChildren().add(line);
			}else if(currentGame instanceof CycleGame){
				if (athlete_select.getValue().getID() == "00"){
					curr = (Athlete) ((CycleGame) currentGame).addCyclist();
				}else{
					curr = (Athlete) ((CycleGame) currentGame).addCyclist( athlete_select.getValue());
				}
				if(!(curr instanceof Cyclist || curr instanceof SuperAthlete)){
					throw new WrongTypeException();
				}
				Label line = new Label(curr.getName());
				line.getStyleClass().add("ppt_line");
				ppt_list.getChildren().add(line);
			}
			System.out.println("A new Athlete was added to the race.");
		} catch (GameFullException e1) {
			showError(2);
			//System.out.println("Cannot add more participants to this game");
			e1.printStackTrace();
		} catch(DuplicateAthleteException dae){
			showError(3);
			dae.printStackTrace();
		} catch (WrongTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * Sets the official for the game according to the selected option.
	 * @param e -> Triggered by the "Set Official" button.
	 */
	public void setOfficial(ActionEvent e){
		error_msg.setText("");
		if (official_select.getValue().getID() == "00"){
			currentGame.setOfficial(null);
			official_label.setVisible(false);
		}else{
			try {
				if(official_select.getValue() instanceof Official){
					currentGame.setOfficial((Official) official_select.getValue());
					official_label.setVisible(true);
					official_label.setText(official_select.getValue().getName());
					System.out.println("An official has been selected for the game.");
				}else{
					throw new WrongTypeException();
				}
			} catch (WrongTypeException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	/**
	 * Event that starts the current game. Must validate first using exceptions. Each 
	 * exception will generate a message for the user.
	 * @param e
	 */
	public void clickStart(ActionEvent e){
		try{
			startRace();
		}catch(TooFewAthleteException exc){
			exc.printStackTrace();
			showError(1);
		}catch(NoRefereeException nre){
			nre.printStackTrace();
			showError(4);
		}
	}
	/**
	 * Starts the current game.
	 * @throws TooFewAthleteException
	 * @throws NoRefereeException
	 */
	public void startRace() throws TooFewAthleteException, NoRefereeException{
		if(currentGame.getOfficial() == null){
			throw new NoRefereeException();
		}
		else{
			int exCnt = 8;
			for(int i = 0; i < 8; i++){
				if(currentGame.getCompetitors()[i] == null)
					exCnt--;
			}
			if(exCnt >= 4){
				counter.stop();
				Stage racing =  (Stage) start_btn.getScene().getWindow();
				FXMLLoader root;
				root = new FXMLLoader(getClass().getResource("/graphics/Race.fxml"));
				Scene sc1;
				try {
					sc1 = new Scene(root.load());
					sc1.getStylesheets().add(getClass().getResource("/graphics/RunRace.css").toExternalForm());
					if(currentGame instanceof SwimGame){
						sc1.getStylesheets().add(getClass().getResource("/graphics/SwimRace.css").toExternalForm());
					}
					RaceController controller = root.<RaceController>getController();
					try{
						controller.initData(currentGame);
					}catch(ImageNotFoundException infe){
						System.out.println("Image not found");
					}

					racing.setScene(sc1);
					racing.setTitle("Race");
					racing.show();
					controller.startAnimation();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("The game will start...");
			}else{
				throw new TooFewAthleteException();
			}
		}
	}
	/**
	 * Shows the error on screen depending on the parameter sent.
	 * @param e -> Error code.
	 */
	public void showError(int e){
		if(e == 1){
			error_msg.setText("At least 4 participants are necesary for a game to run.");
		}else if (e == 2){
			error_msg.setText("No more than 8 participants are allowed per game.");
		}else if (e == 3){
			error_msg.setText("That athlete is already participating in the game");
		}else if (e == 4){
			error_msg.setText("An official must be assigned");
		}
	}
	/**
	 * Cancels the current game if the timeout has expired and not enough athletes or
	 * officials have signed up.
	 * @param reason
	 */
	public void cancelGame(int reason){
		try {

			Stage errorStage = new Stage();
			FXMLLoader root;
			root = new FXMLLoader(getClass().getResource("/graphics/Error.fxml"));
			Scene sc1;
			sc1 = new Scene(root.load());
			//sc1.getStylesheets().add(getClass().getResource("/graphics/Error.css").toExternalForm());
			errorStage.setScene(sc1);
			errorStage.setResizable(false);
			errorStage.setTitle("Error");
			ErrorController cntllr = root.<ErrorController>getController();
			cntllr.setMessage(reason);
			errorStage.show();
			ppt_list.getScene().getWindow().hide();
			System.out.println("The game has timed out and will be cancelled.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
