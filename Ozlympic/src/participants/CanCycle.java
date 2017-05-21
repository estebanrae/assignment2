/**
 * Interface CanCycle: Applied to a Cyclist or SuperAthlete. Defines if the athlete can cycle, and defines 
 * the range of possible results.
 * 
 * @version		1.0 28 MAR 2017
 * @author		Esteban Ramírez
 */
package participants;

public interface CanCycle {
	public int MAX_CYCLE = 800;
	public int MIN_CYCLE = 500;
	/**
	 * Generates a random number which will be between the defined parameters and sets the number
	 * obtained as the current object's current_score value.
	 */
	public void cycle();
}
