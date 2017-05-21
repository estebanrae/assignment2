/**
 * Class Cyclist: Subclass of Athlete class.
 * 
 * @version		1.0 28 MAR 2017
 * @author		Esteban Ramírez
 */
package participants;

import games.Game;

public class Cyclist extends Athlete implements CanCycle{	

	public Cyclist(String ID, String name, int age, String state){
		super(ID, name, age, state);
	}
	
	public Cyclist(String ID, String name){
		super(ID, name);
	}
	public Cyclist(String ID, String name, int age, String state, int points){
		super(ID, name, age, state, points);
	}
	public Cyclist(String ID, String name, int age, String state, int points, double current_score, int place){
		super(ID, name, age, state, points, current_score, place);
	}
	/**
	 * (non-Javadoc)
	 * @see driver.Athlete#compete(driver.Game)
	 * calls cycle method
	 */
	public void compete(Game x) {
		cycle();
	}
	/**
	 * (non-Javadoc)
	 * @see driver.CanCycle#cycle()
	 */
	@Override
	public void cycle() {
		double result = -(this.getSpeed() * (MAX_CYCLE -MIN_CYCLE) ) + MAX_CYCLE;
		setCurrent_score(result);
	}
}