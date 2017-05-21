/**
 * Class Swimmer: Subclass of Athlete class.
 * 
 * @version		1.0 28 MAR 2017
 * @author		Esteban Ramírez
 */

package participants;

import games.CycleGame;
import games.Game;
import games.RunningGame;
import games.SwimGame;

public class SuperAthlete extends Athlete implements CanSwim, CanRun, CanCycle{

	public SuperAthlete(String ID, String name, int age, String state){
		super(ID, name, age, state);
	}
	public SuperAthlete(String ID, String name){
		super(ID, name);
	}
	public SuperAthlete(String ID, String name, int age, String state, int points){
		super(ID, name, age, state, points);
	}
	public SuperAthlete(String ID, String name, int age, String state, int points, double current_score, int place){
		super(ID, name, age, state, points, current_score, place);
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
	/**
	 * (non-Javadoc)
	 * @see driver.Athlete#compete(driver.Game)
	 * calls a specific method depending on which type of game the SuperAthlete object is
	 * participating in.
	 */
	@Override
	public void compete(Game x) {
		if(x instanceof SwimGame){
			swim();
		}else if(x instanceof RunningGame){
			run();
		}else if(x instanceof CycleGame){
			cycle();
		}
		
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
