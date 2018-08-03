/**
* I declare that this assignment is my own work and that all 
* material previously written or published in any source by any 
* other person has been duly acknowledged in the assignment. I 
* have not submitted this work, or a significant part thereof, 
* previously as part of any academic program. In submitting this 
* assignment I give permission to copy it for assessment purposes 
* only.
* 
* title: LookupTask3.java
* description: A program to get statistics from a weblog. 
* @author craigleech
* @version 1.0
* @since 2018-04-09
*/

import java.net.*;
import java.util.concurrent.Callable;

public class LookupTask3 implements Callable<String> {
	
		private String line;

		public LookupTask3(String line){
			this.line = line;
		}

	@Override
	public String call() {
		try{
			//index to separate host
			int index1 = line.indexOf(' ');
			// index to separate bytes transmitted
			int index2 = line.lastIndexOf(' ');
			String address = line.substring(0, index1);
			String hostname = InetAddress.getByName(address).getHostName();
			String bytes = line.substring(index2+1);
			// if no bytes were transferred set bytes t0 0
			if(bytes.equals("-")){
				bytes = "0";
			}
			// hostbytes = 
			String hostBytes = hostname + " " + bytes;
			// return hostbytes
			return hostBytes; 
		}
		// catch exceptions where the line is incomplete. 
		// ensures the line is returned in the correct format
		catch (Exception ex){
			int index1 = line.indexOf(' ');
			int index2 = line.lastIndexOf(' ');
			String addr = line.substring(0, index1);
			String byts = line.substring(index2+1);
			if(byts.equals("-")){
				byts = "0";
			}
			return addr+" "+byts; 
		}
	}
}