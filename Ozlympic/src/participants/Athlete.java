/**
 * Class Athlete: Abstract class for all athlete objects that will compete in the game
 * 
 * @version		1.0 28 MAR 2017
 * @author		Esteban Ramírez
 */
package participants;

import java.util.Random;

import games.Game;

public abstract class Athlete extends Participant{
	private double current_score = 0;
	private int points = 0;
	private int place;
	private double speed;
	/*
	 * All constructors for this class generate a random speed for the athlete.
	 */
	public Athlete(String ID, String name, int age, String state, int points){
		super(ID, name, age, state);
		this.points = points;
		setRandomSpeed();
	}
	public Athlete(String ID, String name, int age, String state, int points, double current_score, int place){
		this(ID, name, age, state, points);
		this.current_score = current_score;
		this.place = place;
	}
	public Athlete(String ID, String name, int age, String state){
		super(ID, name, age, state);
		setRandomSpeed();
	}
	public Athlete(String ID, String name){
		super(ID, name);
		setRandomSpeed();
	}
	/**
	 * Allows an athlete to compete on a game, after defining the type of game
	 * @param x
	 * 		the game that the Athlete will compete in
	 */
	public abstract void compete(Game x);
	
	/**
	 *  Accessor for points variable
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * Mutator for points variable
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	/**
	 * Accessor for current_score variable
	 */
	public double getCurrent_score() {
		return current_score;
	}
	/**
	 * Mutator for points variable
	 */
	public void setCurrent_score(double current_score) {
		this.current_score = current_score;
	}
	
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public void setRandomSpeed(){
		Random rand = new Random();
		setSpeed(rand.nextDouble());
	}
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}
}
