/**
 * Class Swimmer: Subclass of Athlete class.
 * 
 * @version		1.0 28 MAR 2017
 * @author		Esteban Ramírez
 */
package participants;

import games.Game;

public class Swimmer extends Athlete implements CanSwim{
	public Swimmer(String ID, String name, int age, String state){
		super(ID, name, age, state);
	}
	public Swimmer(String ID, String name){
		super(ID, name);
	}
	public Swimmer(String ID, String name, int age, String state, int points){
		super(ID, name, age, state, points);
	}
	public Swimmer(String ID, String name, int age, String state, int points, double current_score, int place){
		super(ID, name, age, state, points, current_score, place);
	}
	/**
	 * (non-Javadoc)
	 * @see driver.Athlete#compete(driver.Game)
	 * calls swim method
	 */
	public void compete(Game x) {
		swim();
	}
	/**
	 * (non-Javadoc)
	 * @see driver.CanSwim#swim()
	 */
	@Override
	public void swim() {
		double result = -(this.getSpeed() * (MAX_SWIM -MIN_SWIM) ) + MAX_SWIM;
		setCurrent_score(result);
	}
}
