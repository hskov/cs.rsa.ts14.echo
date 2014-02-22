package cs.rsa.ts14.framework;

public class implLineTypeClassifierStrategy implements
		LineTypeClassifierStrategy {

	private String lastError;
	
	@Override
	public LineType classify(String line) {
		LineType type = LineType.INVALID_LINE;
		
		// Week specification line
		if(line.matches("^Week\\s[1-9][0-2]*\\s+:\\s+[0-9]\\s+:\\s+[0-9]$"))
		{
			type = LineType.WEEK_SPECIFICATION;
		}
		// Weekday specification line
		else if(line.matches("^(Mon|Tue|Wed|Thu|Fri|Sat|Sun)\\s+(Bi|Ca|Pu|Tr|No|Ho).*$"))
		{
			 type = LineType.WEEKDAY_SPECIFICATION;
		}
		// Work specification line
		else if(line.matches("^\\s[a-zæøåA-ZÆØÅ]\\w*\\s+([a-zæøåA-ZÆØÅ]{1,}|-)\\s+\\d+(\\.5)?$"))
		{
			type = LineType.WORK_SPECIFICATION;
		}
		// Assignment specification line
		else if(line.matches("^[a-zæøåA-ZÆØÅ]+\\s+=\\s+\\d+?$")) 
		{
			type = LineType.ASSIGNMENT_LINE;
		}
		// Empty line
		else if(line.length() == 0)
		{
			type = LineType.EMPTY_LINE;
		}
		// Comment line
		else if(line.matches("^#.*$"))
		{
			type = LineType.COMMENT_LINE;
		}
		else
		{
			type = LineType.INVALID_LINE;
		
			//TODO: ADD DETAILS ABOUT THE LINE
			lastError = line + " - Line does not conform to specification.";
		}
		
		return type;
	}

	@Override
	public String lastError() {
		return lastError;
	}

}
