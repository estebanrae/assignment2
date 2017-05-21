/**
 * Participant Class: The general class that defines variables all athletes and officials
 * will share. The way to decode the string read from a file into parameters is also 
 * described here.
 * 
 * @version		1.0 28 MAR 2017
 * @author		Esteban Ramírez
 */
package participants;

public abstract class Participant {
	private String ID;
	private String name;
	private int age;
	private String state;
	
	public Participant(String ID, String name, int age, String state){
		this.ID = ID;
		this.name = name;
		this.age = age;
		this.state = state;
	}
	
	public Participant(String ID, String name){
		this.ID = ID;
		this.name = name;
	}

	/**
	 * Accessor for name variable.
	 * @return name
	 */
	public String getName() {
		return name;
	}

	public String getID() {
		return ID;
	}

	public int getAge() {
		return age;
	}
	public String getState() {
		return state;
	}
}
