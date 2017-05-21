/**
 * Interface CanCycle: Applied to a Sprinter or SuperAthlete. Defines if the athlete can run, and defines 
 * the range of possible results.
 * 
 * @version		1.0 28 MAR 2017
 * @author		Esteban Ramírez
 */

package participants;

public interface CanRun {
	public int MAX_RUN = 20;
	public int MIN_RUN = 10;
	/**
	 * Generates a random number which will be between the defined parameters and sets the number
	 * obtained as the current object's current_score value.
	 */
	public void run();
}
