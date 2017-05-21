/**
 * Interface CanCycle: Applied to a Swimmer or SuperAthlete object. Defines if the athlete can swim, 
 * and defines the range of possible results.
 * 
 * @version		1.0 28 MAR 2017
 * @author		Esteban Ramírez
 */

package participants;

public interface CanSwim{
	public int MAX_SWIM = 200;
	public int MIN_SWIM = 100;
	/**
	 * Generates a random number which will be between the defined parameters and sets the number
	 * obtained as the current object's current_score value.
	 */
	public void swim();
}
