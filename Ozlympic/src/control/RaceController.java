package control;

import exceptions.ImageNotFoundException;
import games.CycleGame;
import games.Game;
import games.RunningGame;
import games.SwimGame;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import participants.Athlete;
import storage.Reader;
/** 
 * The controller for the Race animations. This will display an animation of all the
 * athletes that are competing and will allocate their results at the bottom of the +
 * screen.
 * @author estebanramirez
 *
 */
public class RaceController {
	private Game currentGame;
	private int exCnt;
	private int place = 0;
	
	@FXML
	private VBox roads;

	@FXML
	private GridPane result_grid;
	@FXML
	private AnchorPane shadowbox;
	@FXML
	public void initialize(){

	}
	/**
	 * Initializes the data according to how many athletes are participating and the type 
	 * of game that will run.
	 * @param current
	 * @throws ImageNotFoundException
	 */
	public void initData(Game current) throws ImageNotFoundException{
		this.currentGame = current;
		exCnt = 8;
		for(int i = 0; i < 8; i++){
			if(currentGame.getCompetitors()[i] == null)
				exCnt--;
		}
		Image img;
		if(currentGame instanceof RunningGame){
			img = new Image("file:resources/running_image.png");
		}else if(currentGame instanceof SwimGame){
			img = new Image("file:resources/swimmer_image.png");
		}else if(currentGame instanceof CycleGame){
			img = new Image("file:resources/cyclist_image.png");
		}else{
			throw new ImageNotFoundException();
		}
		/*
		 * This loop generates all the tracks in which the athletes will compete.
		 */
		for(int jj = 1; jj <= exCnt; jj++){

			StackPane track = new StackPane();
			ImageView imgVw = new ImageView(img);
			track.setPrefHeight( Math.floor(roads.getPrefHeight() / exCnt) );
			track.getStyleClass().add("road");
			track.setId("track-" + Integer.toString(jj));
			imgVw.setId("runner-" + Integer.toString(jj));
			imgVw.getStyleClass().add("runner");
			
			Label name = new Label(currentGame.getCompetitors()[jj - 1].getName());
			name.setId("name-" + Integer.toString(jj));
			name.getStyleClass().add("name_label");

			imgVw.setFitHeight(Math.floor(0.6*(roads.getPrefHeight() / exCnt)));
			imgVw.setPreserveRatio(true);
			track.getChildren().addAll(imgVw, name);
			track.setAlignment(Pos.CENTER_LEFT);
			//System.out.println(track);
			roads.getChildren().add(track);
		}

	}
	/**
	 * Generates a particular animation for each athlete on its track, and uses the 
	 * parallel transition to animate all the tracks at the same time.
	 */
	public void startAnimation(){
		System.out.println("Race has started!!!");
		ParallelTransition parallel = new ParallelTransition();
		int ii = 1;
		/*
		 * This loop generates the animation for all athletes according to their
		 * speed parameter.
		 */
		for (Athlete comp : currentGame.getCompetitors()){
			if(comp != null){
				// Duration has to be: Max Duration = Slowest time = 5 secs. 
				// Min Duration = Fastest time = 2 secs. 
				// Duration is inversly proportional to speed: Duration = -2 * speed + 5. 
				Duration duration = Duration.millis(((-2*comp.getSpeed()) + 5)*1000);
				//Duration duration = Duration.millis(1000);
				TranslateTransition transition = new TranslateTransition();
				transition.setDuration(duration);
				double img_ratio = ((ImageView) roads.lookup("#runner-" + ii) ).getImage().getWidth() / 
						((ImageView) roads.lookup("#runner-" + ii)).getImage().getHeight();
				double newImgWidth = ((ImageView) roads.lookup("#runner-" + ii)).getFitHeight() * img_ratio;
				double endZone = ((StackPane) roads.lookup("#track-" + ii)).getWidth() - 
						newImgWidth;
				ImageView curr = (ImageView) roads.lookup("#runner-" + ii);
				transition.setNode(curr);
				transition.setFromX(0.0);
				transition.setToX(endZone);
				transition.setOnFinished(e -> {
					//System.out.println(comp);
					retrieveResults(comp);
				});
				parallel.getChildren().add(transition);
				
				Label name = (Label) roads.lookup("#name-" + ii);
				TranslateTransition transition2 = new TranslateTransition();
				transition2.setDuration(duration);
				transition2.setNode(name);
				transition2.setFromX(0.0);
				transition2.setToX(endZone);
				parallel.getChildren().add(transition2);
				ii++;
			}
		}

		parallel.play();
	}
	/**
	 * This method is called every time an athlete's animation finishes, indicating that 
	 * they have finished the race. This method assigns the points to each athlete and 
	 * stores the results.
	 * @param finisher
	 */
	public void retrieveResults(Athlete finisher){
		place++;
		if(place == 1){
			finisher.setPoints(finisher.getPoints() + 5);
		}else if(place == 2){
			finisher.setPoints(finisher.getPoints() + 2);
		}else if(place == 3){
			finisher.setPoints(finisher.getPoints() + 1);
		}
		finisher.setPlace(place);
		Reader.updateAthlete(finisher);
		finisher.compete(currentGame);
		System.out.println("Atlhete: " + finisher.getName() + " has finished the race");
		System.out.println("Time: " + finisher.getCurrent_score());
		Label text = new Label(finisher.getName() + ", time: " 
								+ (Math.round(finisher.getCurrent_score() * 1000.0) / 1000.0) 
								+ " secs. " + finisher.getPoints() + " points in total.");
		text.getStyleClass().add("ppt_result");	
		HBox container = (HBox) result_grid.lookup("#resbox" + place);
		container.getChildren().add(text);
		
		if(place == exCnt){
			finishedRace();
		}
	}
	
	public void finishedRace(){
		System.out.println("The game has finished");
		Reader.storeGame(currentGame);
		shadowbox.setVisible(true);
	}
	public void closeWindow(ActionEvent ae){
		Node source = (Node) ae.getSource();
		source.getScene().getWindow().hide();
	    
	}
	
}
