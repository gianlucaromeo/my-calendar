package mycalendar.controller;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextFormatter;

public class MyTextFormatter extends TextFormatter<Integer> {
	
	public MyTextFormatter(int limit) {
		
		/* Limit == 24 -> Hours Formatter */
		/* Limit == 60 -> Seconds Formatter */
		
		super(new UnaryOperator<Change>() {
			@Override
			public Change apply(Change change) {
				if (change.isDeleted())
	                return change;
	            String txt = change.getControlNewText();	            
	            try {
	                int n = Integer.parseInt(txt);
	                return 0 <= n && n <= limit ? change : null;
	            } catch (NumberFormatException e) {
	                return null;
	            }	            
			}			
		});
		
	}
	
}
