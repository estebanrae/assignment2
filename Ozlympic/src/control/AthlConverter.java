package control;

import javafx.util.StringConverter;
import participants.Athlete;
/**
 * Description: This is a helper class used to specify which field should be displayed
 * in the ChoicePane, in this case the ChoicePane will hold Athlete objects but only 
 * display the name of the athlete.
 * @author estebanramirez
 *
 */
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
