/**
 * Class CycleGame: Subclass of Game class, makes sure only Cyclist objects are added as competitors
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
import participants.CanCycle;
import participants.Official;
import storage.Reader;

public class CycleGame extends Game {

	public CycleGame(String ID, Official official, Athlete[] competitors){
		super(ID, official, competitors);
	}
	public CycleGame(String ID, Official official){
		super(ID, official);
	}
	public CycleGame(String ID){
		super(ID);
	}
	public CycleGame(){
		this(Reader.readGames(3));
	}
	/**
	 * This method adds a random cyclist to the current cycling game and returns the 
	 * reference to that athlete.
	 * @return
	 * @throws GameFullException
	 * @throws SQLException
	 */
	public CanCycle addCyclist() throws GameFullException, SQLException{
		Random rand = new Random();
		Athlete randomAthlete = null; 
		while(true){
			randomAthlete = Reader.readAthletes("name")[rand.nextInt(Reader.readAthletes("name").length)];
			if(randomAthlete instanceof CanCycle){
				try{
					addCompetitor(randomAthlete);
					break;
				}catch(DuplicateAthleteException dae){
					continue;
				}
			}
		
		}
		return (CanCycle) randomAthlete;		
	}
	/**
	 * This method checks to see if the athlete that will be added already exists. And
	 * if not it will add it.
	 * @param current
	 * @return
	 * @throws DuplicateAthleteException
	 * @throws GameFullException
	 * @throws WrongTypeException
	 */
	public CanCycle addCyclist(Athlete current) throws DuplicateAthleteException, GameFullException, WrongTypeException{
		if(!(current instanceof CanCycle))
			throw new WrongTypeException();
		addCompetitor(current);
		return (CanCycle) current;	
	}
}
