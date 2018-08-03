/**
* I declare that this assignment is my own work and that all 
* material previously written or published in any source by any 
* other person has been duly acknowledged in the assignment. I 
* have not submitted this work, or a significant part thereof, 
* previously as part of any academic program. In submitting this 
* assignment I give permission to copy it for assessment purposes 
* only.
* 
* title: LookupTask.java
* description: A program to get statistics from a weblog. 
* @author craigleech
* @version 1.0
* @since 2018-04-09
*/

import java.net.*;
import java.util.concurrent.Callable;

public class LookupTask implements Callable<String> {
	
		private String line;

		public LookupTask(String line){
			this.line = line;
		}

	@Override
	public String call() {
		try{
			//separate address and try get host name
			int index = line.indexOf(' ');
			String address = line.substring(0, index);
			String hostname = InetAddress.getByName(address).getHostName();
			// return hostname
			return hostname; 
		}
		catch (Exception ex){
			int index = line.indexOf(' '); //separate ip addres if no host name
			// returns ip
			return line.substring(0,index); 
		}
	}
}
