/**
 * Class RunningGame: Subclass of Game class, makes sure only Sprinter objects are added as competitors
 * 
 * @version		1.0 28 MAR 2017
 * @author		Esteban Ramírez
 */
package games;

import java.sql.SQLException;
import java.util.Random;

import exceptions.DuplicateAthleteException;
import exceptions.GameFullException;
import exceptions.WrongTypeException;
import participants.Athlete;
import participants.CanRun;
import participants.Official;
import storage.Reader;

public class RunningGame extends Game {

	public RunningGame(String ID, Official official, Athlete[] competitors){
		super(ID, official, competitors);
	}
	public RunningGame(String ID, Official official){
		super(ID, official);
	}
	public RunningGame(String ID){
		super(ID);
	}
	public RunningGame(){
		this(Reader.readGames(1));
	}
	public CanRun addSprinter() throws GameFullException, SQLException{
		Random rand = new Random();

		Athlete randomAthlete = null; 
		while(true){
			randomAthlete = Reader.readAthletes("name")[rand.nextInt(Reader.readAthletes("name").length)];
			if(randomAthlete instanceof CanRun){
				try{
					addCompetitor(randomAthlete);
					break;
				}catch(DuplicateAthleteException dae){
					continue;
				}
			}

		}
		return (CanRun) randomAthlete;		
	}

	public CanRun addSprinter(Athlete current) throws DuplicateAthleteException, GameFullException, WrongTypeException{
		if(!(current instanceof CanRun))
			throw new WrongTypeException();	
		addCompetitor(current);
		return (CanRun) current;		
	}
}
