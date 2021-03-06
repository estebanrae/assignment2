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
import participants.CanSwim;
import participants.Official;
import storage.Reader;

public class SwimGame extends Game {

	public SwimGame(String ID, Official official, Athlete[] competitors){
		super(ID, official, competitors);
	}
	public SwimGame(String ID, Official official){
		super(ID, official);
	}
	public SwimGame(String ID){
		super(ID);
	}
	public SwimGame(){
		this(Reader.readGames(2));
	}
	/**
	 * This method adds a random swimmer to the current cycling game and returns the 
	 * reference to that athlete.
	 * @return
	 * @throws GameFullException
	 * @throws SQLException
	 */
	public CanSwim addSwimmer() throws GameFullException, SQLException{
		Random rand = new Random();
		Athlete randomAthlete = null; 
		while(true){
			randomAthlete = Reader.readAthletes("name")[rand.nextInt(Reader.readAthletes("name").length)];
			if(randomAthlete instanceof CanSwim){
				try{
					addCompetitor(randomAthlete);
					break;
				}catch(DuplicateAthleteException dae){
					continue;
				}
			}
		
		}
		return (CanSwim) randomAthlete;	
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
	public CanSwim addSwimmer(Athlete current) throws DuplicateAthleteException, GameFullException, WrongTypeException{
		if(!(current instanceof CanSwim))
			throw new WrongTypeException();
		addCompetitor(current);
		return (CanSwim) current;	
	}
}
