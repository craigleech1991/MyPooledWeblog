/**
* I declare that this assignment is my own work and that all 
* material previously written or published in any source by any 
* other person has been duly acknowledged in the assignment. I 
* have not submitted this work, or a significant part thereof, 
* previously as part of any academic program. In submitting this 
* assignment I give permission to copy it for assessment purposes 
* only.
* 
* title: LookupTask2.java
* description: A program to get statistics from a weblog. 
* @author craigleech
* @version 1.0
* @since 2018-04-09
*/

import java.net.*;
import java.util.concurrent.Callable;

public class LookupTask2 implements Callable<String> {
	
		private String line;

		public LookupTask2(String line){
			this.line = line;
		}

	@Override
	public String call() {
		try{
			//seperate out bytes transmitted
			int index = line.lastIndexOf(' ');
			String bytes = line.substring(index+1);
			// if incomplete line return 0
			if(bytes.equals("-")){
				return "0";
			}
			// return bytes
			return bytes;
		}
		// catch io exception
		catch (Exception ex){
			return "0";
		}
	}
}