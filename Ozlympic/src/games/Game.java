/**
 * Class Game: Defines the parameters and methods that will exist for all games.
 * 
 * @version		1.0 28 MAR 2017
 * @author		Esteban Ramírez
 */
package games;

import exceptions.DuplicateAthleteException;
import exceptions.GameFullException;
import participants.Athlete;
import participants.Official;

public abstract class Game {
	private String ID;
	private Official official;
	private Athlete[] competitors;
	//private int hasRun = 0;

	public Game(String ID, Official official, Athlete[] competitors){
		this.ID = ID;
		//		this.name = name;
		this.official = official;
		this.competitors = competitors;
	}
	public Game(String ID, Official official){
		this.ID = ID;
		this.official = official;
		this.competitors = new Athlete[8];
	}

	public Game(String ID){
		this.ID = ID;
		this.competitors = new Athlete[8];
	}
	/**
	 * Accessor for ID variable
	 * @return The current Game ID
	 */
	public String getID() {
		return ID;
	}
	/** 
	 * Accessor for official variable
	 * @return The current Game's official
	 */
	public Official getOfficial() {
		return official;
	}

	/**
	 * Mutator for official variable
	 * @param official
	 */
	public void setOfficial(Official official) {
		this.official = official;
	}
	/** 
	 * Accessor for name variable
	 * @return The current Game's name
	 */
	/**
	 * Accessor for competitors variable
	 * @return The current Game's competitors
	 */
	public Athlete[] getCompetitors() {
		return competitors;
	}
	/**
	 * Mutator for competitors variable
	 * @param competitors
	 */
	public void setCompetitors(Athlete[] competitors) {
		this.competitors = competitors;
	}
	/**
	 * Adds a new competitor to the current game.
	 * @param competitor
	 * @throws GameFullException
	 * @throws DuplicateAthleteException
	 */
	public void addCompetitor(Athlete competitor) throws GameFullException, DuplicateAthleteException{
		boolean dup = false;
		for(int i = 0; i< 8; i++){
			if(this.competitors[i] == null)
				continue;
			else{
			}
			if(this.competitors[i].getID().equals(competitor.getID()) ){
				dup = true;
			}
		}
		if(!dup){
			boolean inserted = false;
			for(int i = 0; i< 8; i++){
				if(competitors[i] == null){
					inserted = true;
					competitors[i] = competitor;
					break;
				}
			}
			if(!inserted){
				throw new GameFullException();
			}

		}else{
			throw new DuplicateAthleteException();
		}
	}
	/**
	 * This method orders the array of competitors according to their place in the 
	 * current race.
	 */
	public void orderCompetitors(){
		Athlete aux = null;
		int top = 0;
		int j;
		for(j=0; j < competitors.length -1; j++ )
		{
			if(competitors[j] == null){
				break;
			}
			top++;
		}
		
		boolean flag = true;
		int temp;   
		while ( flag )
		{
			flag= false;
			for(j=0; j < top - 1; j++ )
			{
				if ( competitors[j].getPlace() > competitors[j+1].getPlace() )  
				{
					aux = competitors[ j ];
					competitors[ j ] = competitors[ j+1 ];
					competitors[ j+1 ] = aux;
					flag = true;
				} 
			} 
		} 
	}
}
