package control;

import java.io.IOException;

import games.CycleGame;
import games.Game;
import games.RunningGame;
import games.SwimGame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import storage.Reader;
/**
 * This class generates the tables which display the results for all the races that have
 * happened.
 * @author estebanramirez
 *
 */
public class ResultsController {
	@FXML
	private VBox run_results;
	@FXML
	private VBox swim_results;
	@FXML
	private VBox cycle_results;


	public void initialize() throws IOException{
		Game[] games = null;
		System.out.println("Results for the race are displayed.");
		games = Reader.readGames();
		if(games == null){
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
			cntllr.setMessage(3);
			errorStage.show();
			errorStage.setAlwaysOnTop(true);
			return;
		}
		for (Game cur : games){
			VBox cont = new VBox();
			HBox titlebox = new HBox();
			Label gameId = new Label("Game ID: " + cur.getID());
			gameId.getStyleClass().add("head_text");
			Label official = new Label("Officiated by: " + cur.getOfficial().getName());
			official.getStyleClass().add("head_text");
			titlebox.getChildren().addAll(gameId, official);

			Label[][] elem = new Label[4][3]; 
			GridPane winners = new GridPane();
			elem[0][0] = new Label("Place");
			elem[0][1] = new Label("Athlete");
			elem[0][2] = new Label("Score");
			elem[0][0].getStyleClass().add("title");
			elem[0][1].getStyleClass().add("title");
			elem[0][2].getStyleClass().add("title");
			elem[1][0] = new Label("1st");
			elem[1][1] = new Label(cur.getCompetitors()[0].getName());
			elem[1][2] = new Label(String.valueOf(cur.getCompetitors()[0].getCurrent_score()));
			elem[2][0] = new Label("2nd");
			elem[2][1] = new Label(cur.getCompetitors()[1].getName());
			elem[2][2] = new Label(String.valueOf(cur.getCompetitors()[1].getCurrent_score()));
			elem[3][0] = new Label("3rd");
			elem[3][1] = new Label(cur.getCompetitors()[2].getName());
			elem[3][2] = new Label(String.valueOf(cur.getCompetitors()[2].getCurrent_score()));
			for(int ii = 0; ii < 4; ii++){
				for(int jj = 0; jj < 3; jj++){
					GridPane.setConstraints(elem[ii][jj], jj, ii);
					elem[ii][jj].setMaxWidth(Double.MAX_VALUE);
					elem[ii][jj].setAlignment(Pos.CENTER);
					elem[ii][jj].getStyleClass().add("win_cell");
					winners.getChildren().addAll(elem[ii][jj]);
				}
			}
			ColumnConstraints col1 = new ColumnConstraints();
			col1.setPercentWidth(20);
			ColumnConstraints col2 = new ColumnConstraints();
			col2.setPercentWidth(40);
			ColumnConstraints col3 = new ColumnConstraints();
			col3.setPercentWidth(40);
			winners.getColumnConstraints().addAll(col1,col2,col3);
			titlebox.getStyleClass().add("title_box");
			winners.getStyleClass().add("grid_res");
			cont.getChildren().addAll(titlebox, winners);
			if(cur instanceof RunningGame){
				run_results.getChildren().add(cont);
			}else if(cur instanceof SwimGame){
				swim_results.getChildren().add(cont);
			}else if(cur instanceof CycleGame){
				cycle_results.getChildren().add(cont);
			}
		}
	}

}