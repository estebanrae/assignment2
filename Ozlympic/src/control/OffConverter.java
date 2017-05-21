package control;

import javafx.util.StringConverter;
import participants.Official;

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