package wtbox.util;

import java.util.Date; 
import java.text.SimpleDateFormat; 

/** 
 * TimeTool utility for Chon, who is lazy to remember things.  :-) */
public class TimeTool {
	
	/** Return the current time in "MMM dd,yyyy HH:mm:ss" format. */ 
	public static String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        return sdf.format(new Date().getTime()); 
	}

}
