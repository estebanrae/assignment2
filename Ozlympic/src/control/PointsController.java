package control;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import participants.Athlete;
import storage.Reader;
/**
 * The controller for the stage that displays the points for each athlete.
 * @author estebanramirez
 *
 */
public class PointsController {

	@FXML
	private VBox container;

	@FXML
	public void initialize(){
		System.out.println("Points per athlete displayed.");
		Athlete[] athletes;
		athletes = Reader.readAthletes("points DESC");

		GridPane results = new GridPane();
		Label titleID = new Label("ID");
		Label titleName = new Label("Athlete");
		Label titlePoints = new Label("Points");

		prepare(titleID, 0, 0);
		prepare(titleName, 1, 0);
		prepare(titlePoints, 2, 0);

		titleID.getStyleClass().add("title_cell");
		titleName.getStyleClass().add("title_cell");
		titlePoints.getStyleClass().add("title_cell");

		results.getChildren().addAll(titleID, titleName, titlePoints);

		int cnt = 1;
		for (Athlete a : athletes){
			Label id = new Label(a.getID());
			Label name = new Label(a.getName());
			Label points = new Label(Integer.toString(a.getPoints()));
			prepare(id, 0, cnt);
			prepare(name, 1, cnt);
			prepare(points, 2, cnt);
			results.getChildren().addAll(id, name, points);

			cnt++;
		}
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(15);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(65);
		ColumnConstraints col3 = new ColumnConstraints();
		col3.setPercentWidth(20);
		results.getColumnConstraints().addAll(col1,col2,col3);
		results.getStyleClass().add("grid");
		container.getChildren().add(results);

	}

	public void prepare(Label l, int x, int y){
		GridPane.setConstraints(l, x, y);
		l.setMaxWidth(Double.MAX_VALUE);
		l.setAlignment(Pos.CENTER);
		l.getStyleClass().add("points_cell");
	}
}
