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
	
	public CanSwim addSwimmer(Athlete current) throws DuplicateAthleteException, GameFullException, WrongTypeException{
		if(!(current instanceof CanSwim))
			throw new WrongTypeException();
		addCompetitor(current);
		return (CanSwim) current;	
	}
}
