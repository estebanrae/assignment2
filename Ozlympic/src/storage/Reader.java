package storage;

import participants.Athlete;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.DuplicateAthleteException;
import exceptions.GameFullException;
import exceptions.GenericGameException;
import games.CycleGame;
import games.Game;
import games.RunningGame;
import games.SwimGame;
import participants.Cyclist;
import participants.Official;
import participants.Participant;
import participants.Sprinter;
import participants.SuperAthlete;
import participants.Swimmer;

/**
 * Description: This class implements all the necessary methods to read and store data 
 * about the athletes and the races both in a database and in a file.
 * The file names are the following:
 * participants.txt -> stores data about all participants
 * gameResults.txt -> stores data about the games
 *  
 * @author estebanramirez
 *
 */

public class Reader {
	static public Connection c;

	/**
	 * Description: Reads athletes from an embedded database, takes into account an
	 * order parameter. If an exception is thrown or if the total number of rows read
	 * from the database is zero, this function will call a file reading function to read
	 * the data from a text file instead.
	 * @param order - can be any parameter of the database table "participants"
	 * 			(ppt_id, name, age, state, points, type)".
	 * @return the array of all athletes 
	 */
	public static Athlete[] readAthletes(String order) {
		LinkedList<Athlete> allAthletes = new LinkedList<Athlete>();
		Athlete[] athletes = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = c.prepareStatement("SELECT * FROM participants ORDER BY " + order + " ;");
			rs = stmt.executeQuery();
			int cnt = 0;
			/* Goes through the resultSet and stores the values in an object subclass of
			 *  Athlete (Sprinter, Swimmer, Cyclist or SuperAthlete).
			 */
			while( rs.next()){
				if(rs.getInt("type") == 1){
					allAthletes.add(new Sprinter(rs.getString("ppt_id"), 
							rs.getString("name"), 
							rs.getInt("age"),
							rs.getString("state"), 
							rs.getInt("points")));
				}else if(rs.getInt("type") == 2){
					allAthletes.add(new Swimmer(rs.getString("ppt_id"), 
							rs.getString("name"), 
							rs.getInt("age"),
							rs.getString("state"),
							rs.getInt("points")));
				}else if(rs.getInt("type") == 3){
					allAthletes.add(new Cyclist(rs.getString("ppt_id"), 
							rs.getString("name"), 
							rs.getInt("age"),
							rs.getString("state"),
							rs.getInt("points")));
				}else if(rs.getInt("type") == 4){
					allAthletes.add(new SuperAthlete(rs.getString("ppt_id"), 
							rs.getString("name"), 
							rs.getInt("age"),
							rs.getString("state"),
							rs.getInt("points")));
				}else{
					continue;
				}
				cnt++;
			}

			if(cnt == 0){
				athletes = readAthletesFromFile();
			}else{
				athletes = new Athlete[allAthletes.size()];
				allAthletes.<Athlete>toArray(athletes);
			}

			return athletes;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return readAthletesFromFile();
		}finally{	
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * Description: Reads data from the text file.
	 * @return
	 */
	public static Athlete[] readAthletesFromFile(){
		BufferedReader input = null;
		LinkedList<Athlete> allAthletes = new LinkedList<Athlete>();
		Athlete[] athletes = null;
		try {
			input = new BufferedReader(new FileReader("files/participants.txt"));
			String next;
			while((next = input.readLine()) != null){

				String[] arr = next.split(",");
				String id = arr[0].trim();
				String type = arr[1].trim();
				String name = arr[2].trim();
				int age = Integer.parseInt(arr[3].trim());
				String state = arr[4].trim();
				int points = Integer.parseInt(arr[5].trim());
				Athlete curr = null;
				if(id.length() > 0 && type.length() > 0 
						&& name.length() > 0 && state.length() > 0
						&& age > 0){
					if(type.equals("sprinter")){
						curr = new Sprinter(id, name, age, state, points);
					}else if(type.equals("swimmer")){
						curr = new Swimmer(id, name, age, state, points);
					}else if(type.equals("cyclist")){
						curr = new Cyclist(id, name, age, state, points);
					}else if(type.equals("super")){
						curr = new SuperAthlete(id, name, age, state, points);
					}else{
						continue;
					}
					if(!checkForDups(athletes, curr)){
						allAthletes.add(curr);
						athletes = new Athlete[allAthletes.size()];
						allAthletes.<Athlete>toArray(athletes);
					}
				}
			}
			return athletes;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Descrption: Checks if the participant "check" exists in the array "arr" by comparing
	 * its IDs.
	 * @param arr
	 * @param check
	 * @return
	 */
	public static boolean checkForDups(Participant[] arr, Participant check){
		if(arr == null)
			return false;
		for(Participant x: arr){
			if(x.getID().equals(check.getID())){
				return true;
			}
		}
		return false;
	}

	/**
	 * Description: Different from an update database query, using this function we can
	 * overwrite the whole file. 
	 * @param athletes - the list of athletes to be stored in the file.
	 */
	public static void storeAthletesInFile(Athlete[] athletes){
		FileWriter writer = null;
		Official[] allOfficials = readOfficialsFromFiles();
		try {
			writer = new FileWriter("files/participants.txt");
			String outstr = "";
			for(Athlete a : athletes){

				String type = "";
				if(a instanceof Sprinter)
					type = "sprinter";
				if(a instanceof Swimmer)
					type = "swimmer";
				if(a instanceof Cyclist)
					type = "cyclist";
				if(a instanceof SuperAthlete)
					type = "super";

				outstr = outstr.concat(a.getID() + ", " + type + ", " + a.getName()
				+ ", " + a.getAge() + ", " + a.getState()
				+ ", " + a.getPoints() + "\n");
			}

			writer.write(outstr);

			for(Official o : allOfficials ){
				writer.write(o.getID() + ", official, " + o.getName()
				+ ", " + o.getAge() + ", " + o.getState() + ", 0\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Description: Initializes the database. If the tables already exist this method 
	 * will throw an exception, if the tables don't exist it will create them and 
	 * populate them by calling the setAthletes function. Note that if the database is 
	 * previously created created and populated this function would not be necessary
	 * and neither would the setAthletes() function.  
	 */
	public static void initDB(){
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ozlympic.db");
			c.setAutoCommit(false);
			Statement stmt = null;
			stmt = c.createStatement();
			String sql = "CREATE TABLE participants " +
					"(ppt_id       VARCHAR(11) PRIMARY KEY     NOT NULL," +
					" name         VARCHAR(100)    NOT NULL, " + 
					" age          INT     NOT NULL, " + 
					" state        VARCHAR(4), " + 
					" points       INT, " + 
					" type         INT)"; 
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE games " +
					"( game_id 		VARCHAR(11) PRIMARY KEY     NOT NULL," +
					" off_id        VARCHAR(11)    NOT NULL)"; 
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE games_ppt " +
					"( game_id 		VARCHAR(11)    NOT NULL," +
					" ppt_id        VARCHAR(11)    NOT NULL," +
					" score         DOUBLE    NOT NULL," +
					" place         DOUBLE    NOT NULL)"; 
			stmt.executeUpdate(sql);
			stmt.close();

			
			setAthletes("r1", "Max", 21, "VIC", 1);
			setAthletes("r2", "Rene", 30, "ACT", 1);
			setAthletes("r3", "John", 24, "QLD", 1);
			setAthletes("r4", "Caroline", 23, "WA", 1);
			setAthletes("r5", "Sarah", 22, "NSW", 1);
			setAthletes("r6", "Mike", 27, "ACT", 1);
			setAthletes("r7", "Tom", 29, "TAS", 1);
			setAthletes("r8", "Nick", 27, "QLD", 1);
			setAthletes("r9", "Javier", 22, "NSW", 1);
			setAthletes("r10", "Nicolas", 19, "ACT", 1);
			setAthletes("s1", "Andrea", 25, "VIC", 2);
			setAthletes("s2", "Claudia", 27, "NT", 2);
			setAthletes("s3", "Mary", 31, "VIC", 2);
			setAthletes("s4", "Susan", 24, "QLD", 2);
			setAthletes("s5", "Christine", 29, "VIC", 2);
			setAthletes("s6", "James", 24, "VIC", 2);
			setAthletes("s7", "Nate", 23, "SA", 2);
			setAthletes("s8", "Pepe", 25, "NT", 2);
			setAthletes("s9", "Eduardo", 22, "VIC", 2);
			setAthletes("s10", "Renata", 21, "TAS", 2);
			setAthletes("c1", "Lily", 22, "NSW", 3);
			setAthletes("c2", "Gus", 24, "VIC", 3);
			setAthletes("c3", "Rachelle", 25, "VIC", 3);
			setAthletes("c4", "Esteban", 27, "ACT", 3);
			setAthletes("c5", "Diego", 25, "WA", 3);
			setAthletes("c6", "Maria", 26, "NT", 3);
			setAthletes("c7", "Fernanda", 22, "SA", 3);
			setAthletes("c8", "Celina", 31, "VIC", 3);
			setAthletes("c9", "Bernardino", 18, "ACT", 3);
			setAthletes("c10", "Jose", 21, "VIC", 3);
			setAthletes("sa1", "Lucia", 23, "NSW", 4);
			setAthletes("sa2", "Andrew", 23, "VIC", 4);
			setAthletes("sa3", "Jason", 22, "NT", 4);
			setAthletes("sa4", "Jacob", 21, "VIC", 4);
			setAthletes("sa5", "Samuel", 25, "ACT", 4);
			setAthletes("sa6", "Stav", 24, "WA", 4);
			setAthletes("sa7", "Agus", 26, "NSW", 4);
			setAthletes("sa8", "Fareed", 20, "VIC", 4);
			setAthletes("sa9", "Natalie", 20, "SA", 4);
			setAthletes("sa10", "Ana", 20, "ACT", 4);

			// OFFICIALS
			setAthletes("o1", "Isaac", 40, "NSW", 5);
			setAthletes("o2", "Albert", 43, "VIC", 5);
			setAthletes("o3", "Blaise", 37, "NT", 5);
			setAthletes("o4", "Stephen", 29, "VIC", 5);
			setAthletes("o5", "Louis", 35, "ACT", 5);
			setAthletes("o6", "William", 41, "WA", 5);
			setAthletes("o7", "Edgar", 34, "NSW", 5);
			setAthletes("o8", "Ray", 32, "VIC", 5);
			setAthletes("o9", "Alan", 36, "SA", 5);
			setAthletes("o10", "Peter", 29, "ACT", 5);
			 



		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * closes the connection to the database
	 */
	public static void closeDB(){
		try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Description: Stores a participant in the database.
	 * @param id
	 * @param name
	 * @param age
	 * @param state
	 * @param type
	 */
	public static void setAthletes(String id, String name, int age, String state, int type){
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			String sql = "INSERT INTO participants (ppt_id,name,age,state,points,type) " +
					"VALUES ('" + id + "', '" + name + "', " + age + ", '" + state + "', 0, " + type + " );"; 
			stmt.executeUpdate(sql);
			c.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Description: Updates the points of a participant. Also calls the function to update
	 * the text file so that both the database and the files are synchronized.
	 * @param athlete -> The athlete to update.
	 */
	public static void updateAthlete(Athlete athlete){
		Statement stmt = null;
		try {
			updateAthleteInFile(athlete);
			stmt = c.createStatement();
			String sql = "UPDATE participants SET  points = " + athlete.getPoints()
			+ " WHERE ppt_id = '" + athlete.getID() + "' ;"; 
			stmt.executeUpdate(sql);
			c.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * Updates the athletes in the file by reading all athletes in the file, finding
	 * the current athlete to update, and then rewriting the file.
	 * @param athlete
	 */
	public static void updateAthleteInFile(Athlete athlete){
		Athlete[] athletes = null;
		athletes = readAthletes("name");

		for(Athlete a: athletes){
			if(a.getID().equals(athlete.getID())){
				a.setPoints(athlete.getPoints());
			}
		}
		storeAthletesInFile(athletes);

	}
	/** 
	 * Description: Stores the results of a game into the database. Also calls the 
	 * method to store the data in the text file.
	 * @param game
	 */
	public static void storeGame(Game game){
		String gameId = game.getID();
		String offId = game.getOfficial().getID();
		Statement stmt = null;
		try {
			storeGameInFile(game);
			
			stmt = c.createStatement();


			String sql = "INSERT INTO games (game_id, off_id)"
					+ " VALUES ('" + gameId + "', '" + offId + "')";
			stmt.executeUpdate(sql);
			for(Athlete a : game.getCompetitors()){
				if(a != null){
					stmt = c.createStatement();
					sql = "INSERT INTO games_ppt (game_id, ppt_id, score, place)"
							+ " VALUES ('" + gameId + "', '"
							+ a.getID() + "', '" 
							+ a.getCurrent_score() + "', " + a.getPlace() +  ")";
					stmt.executeUpdate(sql);
				}
			}
			c.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * Stores the game results in the specified file. Also generates a timestamp that
	 * will be shown in the file.
	 * @param game
	 */
	public static void storeGameInFile(Game game){
		FileWriter writer = null;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {
			writer = new FileWriter("files/gameResults.txt", true);
			String outstr = "";
			outstr = outstr.concat(game.getID() + ", " 
					+ game.getOfficial().getID() + ", " +  timestamp + "\n"); 
			game.orderCompetitors();
			for(Athlete a : game.getCompetitors()){
				if(a != null){
					outstr = outstr.concat(a.getID() + ", " + a.getCurrent_score()
					+ ", " + a.getPlace() + "\n");
				}
			}
			outstr = outstr.concat("\n");
			writer.write(outstr);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Reads all games from the database and stores them in a Game class array as their
	 * defined subclass. To find the type of game that was stored in the DB, the game
	 * ID was analyzed.
	 * @return
	 */
	public static Game[] readGames(){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		LinkedList<Game> allGames = new LinkedList<Game>();
		try {
			String[] idCodes = {"rg", "sg", "cg"};
			int cnt2 = 0;
			for(String code: idCodes){
				stmt = c.prepareStatement("SELECT * FROM games g "
						+ "LEFT JOIN participants p ON g.off_id = p.ppt_id WHERE game_id LIKE '" + code + "%';");
				rs = stmt.executeQuery();
				
				while( rs.next()){
					Athlete[] competitors = new Athlete[8];
					Official off = null;
					stmt = c.prepareStatement("SELECT * FROM games_ppt gp LEFT JOIN participants p"
							+ " ON gp.ppt_id = p.ppt_id WHERE game_id LIKE '" 
							+ rs.getString("game_id") + "' ORDER BY place ;");

					rs2 = stmt.executeQuery();
					int cnt = 0;
					while( rs2.next()){
						if(rs2.getInt("type") == 1){
							competitors[cnt] = new Sprinter(rs2.getString("ppt_id"), 
									rs2.getString("name"),
									rs2.getInt("age"),
									rs2.getString("state"),
									rs2.getInt("points"),
									Math.round(rs2.getDouble("score") *1000.0) /1000.0,
									rs2.getInt("place"));
						}else if(rs2.getInt("type") == 2){
							competitors[cnt] = new Swimmer(rs2.getString("ppt_id"), 
									rs2.getString("name"),
									rs2.getInt("age"),
									rs2.getString("state"),
									rs2.getInt("points"),
									Math.round(rs2.getDouble("score") *1000.0) /1000.0,
									rs2.getInt("place"));
						}else if(rs2.getInt("type") == 3){
							competitors[cnt] = new Cyclist(rs2.getString("ppt_id"), 
									rs2.getString("name"),
									rs2.getInt("age"),
									rs2.getString("state"),
									rs2.getInt("points"),
									Math.round(rs2.getDouble("score") *1000.0) /1000.0,
									rs2.getInt("place"));
						}else if(rs2.getInt("type") == 4){
							competitors[cnt] = new SuperAthlete(rs2.getString("ppt_id"), 
									rs2.getString("name"),
									rs2.getInt("age"),
									rs2.getString("state"),
									rs2.getInt("points"),
									Math.round(rs2.getDouble("score") *1000.0) /1000.0,
									rs2.getInt("place"));
						}
						cnt++;
					}
					off = new Official(rs.getString("off_id"), 
							rs.getString("name"),
							rs.getInt("age"),
							rs.getString("state"));
					if(code == "rg"){
						allGames.add(new RunningGame(rs.getString("game_id"), 
								off, competitors));
					}else if(code == "sg"){
						allGames.add(new SwimGame(rs.getString("game_id"), 
								off, competitors));
					}else if(code == "cg"){
						allGames.add(new CycleGame(rs.getString("game_id"), 
								off, competitors));
					}
					//cnt2++;
				}
			}
			rs.close();
			rs2.close();
			stmt.close();
			if(cnt2 == 0){
				return readGamesFromFile();
			}
			Game[] games = new Game[allGames.size()];
			return (Game[]) allGames.<Game>toArray(games);
		} catch (SQLException e) {

			e.printStackTrace();
			return readGamesFromFile();
		}finally{
			try {
				rs.close();
				rs2.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
	/**
	 * Reads the games from file.
	 * @return
	 */
	public static Game[] readGamesFromFile(){
		BufferedReader input = null;
		LinkedList<Game> allGames = new LinkedList<Game>();
		Game[] games = null;
		try {
			input = new BufferedReader(new FileReader("files/gameResults.txt"));
			String next;
			boolean start = true;
			Game aux = null;
			while((next = input.readLine()) != null){
				String[] arr = next.split(",");
				if(start){
					Official curOf = null;
					for(Official o : readOfficials()){
						
						if(o.getID().equals(arr[1].trim())){
							curOf = o;
						}
					}
					if(arr[0].trim().toCharArray()[0] == 'r'){
						aux = new RunningGame(arr[0].trim(), curOf);
					}else if(arr[0].toCharArray()[0] == 's'){
						aux = new SwimGame(arr[0].trim(), curOf);
					}else if(arr[0].toCharArray()[0] == 'c'){
						aux = new CycleGame(arr[0].trim(), curOf);
					}
					start = false;
				}else if (next.equals("")){
					allGames.add(aux);
					aux = null;
					start = true;
				}else{
					Athlete currentAthlete = null;
					for(Athlete a : readAthletes("name")){
						if(a.getID().equals(arr[0].trim())){
							currentAthlete = a;
						}
					}
					currentAthlete.setCurrent_score(Double.parseDouble(arr[1].trim()));
					currentAthlete.setPlace(Integer.parseInt(arr[2].trim()));
					try {
						aux.addCompetitor(currentAthlete);
					} catch (GameFullException | DuplicateAthleteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			games = new Game[allGames.size()];
			allGames.<Game>toArray(games);
			return games;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Reads the games that belong to a specific category, checks the database to find 
	 * all games of that category, and returns the next id available for that game.
	 * @param i
	 * @return
	 */
	public static String readGames(int i){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String idCode = "";
			if(i == 1){
				idCode = "rg";
			}else if( i == 2 ){
				idCode = "sg";
			}else if( i == 3 ){
				idCode = "cg";
			}
			String sql = "SELECT * FROM games WHERE game_id LIKE '" + idCode + "%';";
			stmt = c.prepareStatement(sql);
			rs = stmt.executeQuery();
			int ids = 0;
			int cnt = 0;
			while( rs.next()){
				Pattern p = Pattern.compile("\\d");
				Matcher set  = p.matcher(rs.getString("game_id"));
				int tmp = 0;
				if(set.find(1)){
					tmp = Integer.parseInt(set.group(0));
				}
				if(tmp > ids){
					ids = tmp;
				}
				cnt ++;
			}
			if(cnt == 0){
				return readGamesFromFiles(i);
			}else{
				return idCode + (ids + 1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return readGamesFromFiles(i);
		}finally{
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * Reads the games that belong to a specific category, checks the file to find 
	 * all games of that category, and returns the next id available for that game.
	 * @param i
	 * @return
	 */
	public static String readGamesFromFiles(int i){
		BufferedReader input = null;
		LinkedList<Game> allGames = new LinkedList<Game>();
		Game[] games = null;
		String idCode = "";
		if(i == 1){
			idCode = "rg";
		}else if( i == 2 ){
			idCode = "sg";
		}else if( i == 3 ){
			idCode = "cg";
		}
		int ids = 0;
		try {
				input = new BufferedReader(new FileReader("files/gameResults.txt"));
				String next;
				boolean start = true;
				Game aux = null;
				while((next = input.readLine()) != null){
					String[] arr = next.split(",");
					if(start){
						Pattern p = Pattern.compile("\\d");
						Matcher set  = p.matcher(arr[0].trim());
						int tmp = 0;
						if(set.find(1)){
							tmp = Integer.parseInt(set.group(0));
						}
						if(tmp > ids){
							ids = tmp;
						}
						start = false;
					}else if (next.equals("")){
						allGames.add(aux);
						aux = null;
						start = true;
					}
				}
			return idCode + (ids + 1);

		} catch (FileNotFoundException e) {
			return idCode + (ids + 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Reads the officials from the database
	 * @return
	 */
	public static Official[] readOfficials(){
		LinkedList<Official> allOfficials = new LinkedList<Official>();
		Official[] officials = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = c.prepareStatement("SELECT * FROM participants WHERE type = 5;");
			rs = stmt.executeQuery();
			int cnt = 0;
			while( rs.next()){
				if(rs.getInt("type") == 5){
					allOfficials.add(new Official(rs.getString("ppt_id"), 
							rs.getString("name"), 
							rs.getInt("age"),
							rs.getString("state")));
				}else{
					continue;
				}
				cnt++;
			}
			if(cnt == 0){
				return readOfficialsFromFiles();
			}else{
				officials = new Official[allOfficials.size()];
				allOfficials.<Official>toArray(officials);
				return officials;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return readOfficialsFromFiles();
		}finally{
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * Reads the officials from the participants file.
	 * @return
	 */
	private static Official[] readOfficialsFromFiles(){
		BufferedReader input = null;
		LinkedList<Official> allOfficials = new LinkedList<Official>();
		Official[] officials = null;
		try {
			input = new BufferedReader(new FileReader("files/participants.txt"));
			String next;
			while((next = input.readLine()) != null){
				String[] arr = next.split(",");
				String id = arr[0].trim();
				String type = arr[1].trim();
				String name = arr[2].trim();
				int age = Integer.parseInt(arr[3].trim());
				String state = arr[4].trim();
				Official curr = null;
				if(id.length() > 0 && type.length() > 0 
						&& name.length() > 0 && state.length() > 0
						&& age > 0){

					if(type.equals("official")){
						curr = new Official(id, name, age, state);
					}else{
						continue;
					}
					if(!checkForDups(officials, curr)){
						allOfficials.add(curr);
						officials = new Official[allOfficials.size()];
						allOfficials.<Official>toArray(officials);
					}
				}
			}
			return officials;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Creates a new game according to the option specified.
	 * @param option
	 * @return
	 * @throws GenericGameException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	static public Game createNewGame(int option) throws GenericGameException, ClassNotFoundException, SQLException{
		if(option == 1){
			return new RunningGame();	
		}else if(option == 2){
			return new SwimGame();
		}else if(option == 3){
			return new CycleGame();
		}else{
			throw new GenericGameException();
		}
	}
}
