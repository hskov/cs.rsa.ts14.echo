package cs.rsa.ts14.echo;

import cs.rsa.ts14.framework.LineType;
import cs.rsa.ts14.framework.LineTypeClassifierStrategy;

public class implLineTypeClassifierStrategy implements
		LineTypeClassifierStrategy {

	private String lastError;
	
	@Override
	public LineType classify(String line) {
		LineType type = null;
		
		// Week specification line
		if(line.matches("^Week\\s[1-9][0-2]{0,1}\\s+:\\s+[0-5]\\s+:\\s+[0-5]$"))
		{
			type = LineType.WEEK_SPECIFICATION;
		}
		// Weekday specification line
		else if(line.matches("^(Mon|Tue|Wed|Thu|Fri|Sat|Sun)\\s+(Bi|Ca|Pu|Tr|No|Ho).*$"))
		{
			 type = LineType.WEEKDAY_SPECIFICATION;
		}
		// Work specification line
		else if(line.matches("^\\s[a-zæøåA-ZÆØÅ]\\w*\\s+([a-zæøåA-ZÆØÅ]{1,}|-)\\s+\\d+(\\.5)?(\\s+.*)?$"))
		{
			type = LineType.WORK_SPECIFICATION;
		}
		// Assignment specification line
		else if(line.matches("^[a-zæøåA-ZÆØÅ]+\\s+=\\s+\\d+([.,]{1}\\d+){0,1}?$")) 
		{
			type = LineType.ASSIGNMENT_LINE;
		}
		// Empty line
		else if(line.trim().length() == 0)
		{
			type = LineType.EMPTY_LINE;
		}
		// Comment line
		else if(line.matches("^\\s*#.*$"))
		{
			type = LineType.COMMENT_LINE;
		}
		else
		{
			type = LineType.INVALID_LINE;
		
			lastError = line + " - Line does not conform to specification.";
		}
		
		return type;
	}

	@Override
	public String lastError() {
		return lastError;
	}

}
