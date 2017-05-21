/*package games;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import exceptions.DuplicateAthleteException;
import exceptions.GameFullException;
import exceptions.WrongTypeException;
import participants.Athlete;
import participants.Sprinter;

public class GameTest {
	Game game;
	RunningGame rgame;
	SwimGame sgame;
	Athlete a1;
	Athlete[] a;
	Sprinter s;
	@Before
	public void setUp() throws Exception {
		game = new Game("T1"){
			
		};
		rgame = new RunningGame("Test");
		a1 = new Athlete("T1", "Test"){
			@Override
			public void compete(Game x) {
			}
		};
		sgame = new SwimGame("Test");
		a = new Athlete[10];
		for(int i = 0; i< 10 ; i++){
			a[i] = new Athlete("T" + i, "Test"){
				@Override
				public void compete(Game x) {
				}
			};
		}
		s = new Sprinter("s1", "Test");
		
	}

	@Test(expected=DuplicateAthleteException.class)
	public void testAddCompetitorDuplicate() throws GameFullException, DuplicateAthleteException {
		game.addCompetitor(a1);
		game.addCompetitor(a1);
	}
	
	@Test(expected=GameFullException.class)
	public void testAddCompetitorFull() throws GameFullException, DuplicateAthleteException {
		for(int i = 0; i< 10 ; i++){
			game.addCompetitor(a[i]);
		}
	}

	@Test
	public void addSprinterRandom() throws GameFullException, SQLException{
		rgame.addSprinter();
		rgame.addSprinter();
		rgame.addSprinter();
		rgame.addSprinter();
		rgame.addSprinter();
		rgame.addSprinter();
		rgame.addSprinter();
		rgame.addSprinter();
	}
	
	@Test
	public void addSprinterSet() throws DuplicateAthleteException, GameFullException, WrongTypeException{
		rgame.addSprinter(s);
	}
	
	@Test(expected=DuplicateAthleteException.class)
	public void addSprinterSetDup() throws DuplicateAthleteException, GameFullException, WrongTypeException{
		rgame.addSprinter(s);
		rgame.addSprinter(s);
	}
	
	@Test(expected=WrongTypeException.class)
	public void addSwimmerWrongType() throws DuplicateAthleteException, GameFullException, WrongTypeException{
		sgame.addSwimmer(s);
	}
	
}*/
