/**
 * Class Sprinter: Subclass of Athlete class.
 * 
 * @version		1.0 28 MAR 2017
 * @author		Esteban Ramírez
 */
package participants;

import games.Game;

public class Sprinter extends Athlete implements CanRun{
	
	public Sprinter(String ID, String name, int age, String state){
		super(ID, name, age, state);
	}
	
	public Sprinter(String ID, String name){
		super(ID, name);
	}
	public Sprinter(String ID, String name, int age, String state, int points){
		super(ID, name, age, state, points);
	}
	public Sprinter(String ID, String name, int age, String state, int points, double current_score, int place){
		super(ID, name, age, state, points, current_score, place);
	}
	/**
	 * (non-Javadoc)
	 * @see driver.Athlete#compete(driver.Game)
	 * calls run method
	 */
	public void compete(Game x) {
		run();
	}
	/**
	 * (non-Javadoc)
	 * @see driver.CanRun#run()
	 */
	@Override
	public void run() {
		double result = -(this.getSpeed() * (MAX_RUN -MIN_RUN) ) + MAX_RUN;
		setCurrent_score(result);
	}
	
}