package control;

import javafx.util.StringConverter;
import participants.Athlete;

public class AthlConverter extends StringConverter<Athlete>{
	  
	@Override
	public Athlete fromString(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString(Athlete object) {	
		return object.getName();
	}
}
