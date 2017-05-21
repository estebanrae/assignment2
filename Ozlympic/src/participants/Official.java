/**
 * Class Official: Yhe class that defines all game's officials.
 * 
 * @version		1.0 28 MAR 2017
 * @author		Esteban Ramírez
 */
package participants;

public class Official extends Participant{
	private int taken = 0;
	public Official(String ID, String name, int age, String state){
		super(ID, name, age, state);
	}
	public Official(String ID, String name){
		super(ID, name);
	}
	/**
	 * Accessor for taken variable
	 * @return the value of the variable that indicates if the official is already chosen by a game.
	 */
	public int getTaken() {
		return taken;
	}
	/**
	 * Mutator for taken variable.
	 * @param taken
	 */
	public void setTaken(int taken) {
		this.taken = taken;
	}
	
}
