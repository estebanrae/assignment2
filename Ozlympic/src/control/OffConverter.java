package control;

import javafx.util.StringConverter;
import participants.Official;
/**
 * Description: This is a helper class used to specify which field should be displayed
 * in the ChoicePane, in this case the ChoicePane will hold Official objects but only 
 * display the name of the official.
 * @author estebanramirez
 *
 */
public class OffConverter extends StringConverter<Official>{
	  
	@Override
	public Official fromString(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString(Official object) {
		// TODO Auto-generated method stub
		return object.getName();
	}
}