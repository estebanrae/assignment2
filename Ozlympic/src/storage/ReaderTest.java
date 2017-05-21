/*package storage;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import games.CycleGame;
import games.RunningGame;
import games.SwimGame;
import participants.Athlete;
import participants.Official;

public class ReaderTest {

	@Test(expected=SQLException.class)
	public void readAthletesNoArg() throws SQLException {
		Athlete[] aux = Reader.readAthletes("");

	}
	@Test
	public void readAthletesOrderNameAsc() throws SQLException {
		Athlete[] aux = Reader.readAthletes("name");
	}
	@Test
	public void readAthletesOrderPointsDesc() throws SQLException {
		Athlete[] aux = Reader.readAthletes("points DESC");
	}
	
	@Test
	public void initDBnoTable() throws SQLException {
		Reader.initDB();
	}

	@Test
	public void insertAthletetoDB(){
		Reader.setAthletes("TEST", "TEST", 0, "TEST", 0);
	}

	@Test
	public void storeRunGameDB(){
		RunningGame aux = new RunningGame();
		aux.setOfficial(new Official("TEST", "TEST"));
		Reader.storeGame(aux);
	}
	@Test
	public void storeSwimGameDB(){
		SwimGame aux = new SwimGame();
		aux.setOfficial(new Official("TEST", "TEST"));
		Reader.storeGame(aux);
	}
	@Test
	public void storeCycleGameDB(){
		CycleGame aux = new CycleGame();
		aux.setOfficial(new Official("TEST", "TEST"));
		Reader.storeGame(aux);
	}
}
*/