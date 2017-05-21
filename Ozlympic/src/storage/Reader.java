package storage;

import participants.Athlete;
import java.sql.*;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.GenericGameException;
import games.CycleGame;
import games.Game;
import games.RunningGame;
import games.SwimGame;
import participants.Cyclist;
import participants.Official;
import participants.Sprinter;
import participants.SuperAthlete;
import participants.Swimmer;

public class Reader {
	static public Connection c = null;

	public static Athlete[] readAthletes(String order) throws SQLException{
		LinkedList<Athlete> allAthletes = new LinkedList<Athlete>();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		c = DriverManager.getConnection("jdbc:sqlite:ozlympic.db");
		c.setAutoCommit(false);
		stmt = c.prepareStatement("SELECT * FROM participants ORDER BY " + order + " ;");
		rs = stmt.executeQuery();
		int cnt = 0;
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
			readAthletesFromFile();
		}
		Athlete[] athletes = new Athlete[allAthletes.size()];
		allAthletes.<Athlete>toArray(athletes);
		rs.close();
		stmt.close();
		c.close();
		return athletes;
	}

	public static Athlete[] readAthletesFromFile(){
		System.out.println("ERROR");
		Athlete[] athletes = null;
		return athletes;
	}

	public static void initDB(){
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ozlympic.db");
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
			c.close();


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
			setAthletes("s5", "Caroline", 29, "VIC", 2);
			setAthletes("s6", "James", 24, "VIC", 2);
			setAthletes("s7", "Nick", 23, "SA", 2);
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

	public static void setAthletes(String id, String name, int age, String state, int type){
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ozlympic.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "INSERT INTO participants (ppt_id,name,age,state,points,type) " +
					"VALUES ('" + id + "', '" + name + "', " + age + ", '" + state + "', 0, " + type + " );"; 
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			c.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void updateAthlete(Athlete athlete){
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ozlympic.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE participants SET  points = " + athlete.getPoints()
			+ " WHERE ppt_id = '" + athlete.getID() + "' ;"; 
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			c.close();
			updateAthleteInFile(athlete);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateAthleteInFile(Athlete athlete){

	}

	public static void storeGame(Game game){
		Connection c = null;
		String gameId = game.getID();
		String offId = game.getOfficial().getID();
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ozlympic.db");
			c.setAutoCommit(false);
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
			stmt.close();
			c.close();
			storeGameInFile(game);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void storeGameInFile(Game game){

	}

	public static Game[] readGames(){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		LinkedList<Game> allGames = new LinkedList<Game>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ozlympic.db");

			c.setAutoCommit(false);
			String[] idCodes = {"rg", "sg", "cg"};
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
					//stmt = c.prepareStatement("SELECT * FROM games_ppt;");
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
				}
			}
			rs.close();
			stmt.close();
			c.close();
			Game[] games = new Game[allGames.size()];
			return (Game[]) allGames.<Game>toArray(games);
		} catch (SQLException e) {

			e.printStackTrace();
			return readGamesFromFile();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return readGamesFromFile();
		}

	}

	public static Game[] readGamesFromFile(){
		Game[] games = null;
		return games;
	}
	public static String readGames(int i){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ozlympic.db");
			c.setAutoCommit(false);
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
			}
			rs.close();
			stmt.close();
			c.close();
			return idCode + (ids + 1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return readGamesFromFiles(i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return readGamesFromFiles(i);
		}


	}
	public static String readGamesFromFiles(int i){
		return "";
	}

	public static Official[] readOfficials(){
		Official[] offArray = new Official[100];
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			c = DriverManager.getConnection("jdbc:sqlite:ozlympic.db");
			c.setAutoCommit(false);
			stmt = c.prepareStatement("SELECT * FROM participants WHERE type = 5;");
			rs = stmt.executeQuery();
			int cnt = 0;
			while( rs.next()){
				if(rs.getInt("type") == 5){
					offArray[cnt] = new Official(rs.getString("ppt_id"), 
							rs.getString("name"), 
							rs.getInt("age"),
							rs.getString("state"));
				}else{
					continue;
				}
				cnt++;
			}

			rs.close();
			stmt.close();
			c.close();

			return offArray;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return readOfficialsFromFiles();
		}
	}

	private static Official[] readOfficialsFromFiles(){
		Official[] officials = null;
		return officials;
	}
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
