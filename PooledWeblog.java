/**
* title: MyPooledWeblog.java
* description: A program to get statistics from a weblog. 
* option 1 = number of accesses by each remote host
* option 2 = total bytes transferred
* option 3 = total bytes transferred by each remote host
* @author craigleech
* @version 1.0
* @since 2018-04-05
*/

/** import java packages*/
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**primary class for MyPooledWeblog*/
public class PooledWeblog {

	/** declare (final) number of threads*/
	private final static int NUM_THREADS = 4;

	public static void main(String[] args) throws IOException {
		ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
		Queue<LogEntry> results = new LinkedList<LogEntry>();

		/// shows user if they have entered a valid option or not
		int option = new Integer(args[1]);
			if(option == 1 || option == 2 || option == 3){
				System.out.println("option: "+ option + ". this may take a while...");
			}
			else{
			System.out.println("Invalid option");
			}

			// if option 1 is selected....
			if(option == 1){

				try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));) {
					for (String entry = in.readLine(); entry != null; entry = in.readLine()) {
						LookupTask task = new LookupTask(entry);
						Future<String> future = executor.submit(task);
						LogEntry result = new LogEntry(entry, future);
						results.add(result);	
					}
				}

						LinkedList<String> tempList = new LinkedList<String>();//store one instance of each remote host
						LinkedList<Integer> counterList = new LinkedList<Integer>();// store counters for each remote host
						for (LogEntry result : results) {
							try {
								if(tempList.size() == 0){
									tempList.push(result.future.get());
									counterList.push(1);
								}
								else{
									boolean exists = false;
									for(int i = 0; i<tempList.size(); i++){
										if(result.future.get().equals(tempList.get(i))){
											int tempCount = counterList.get(i)+1;
											counterList.set(i,tempCount);
											exists = true;
											break;
										}
									}
									if(exists == false){	
										tempList.push(result.future.get());
										counterList.push(1);
									}
								}
							} catch (InterruptedException | ExecutionException ex) {
								System.out.println(result.original);
							}
						}
						//// print out the number of accesses by each remote host
						for(int i = 0; i<tempList.size(); i++){
							System.out.println(tempList.get(i) + " number of accesses: " +counterList.get(i));
						}
			}

			// if option 2 is selected....
			if(option == 2){
				try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));) {
					for (String entry = in.readLine(); entry != null; entry = in.readLine()) {
						LookupTask2 task = new LookupTask2(entry);
						Future<String> future = executor.submit(task);
						LogEntry result = new LogEntry(entry, future);
						results.add(result);
					}
				}
				////count the total bytes transmitted.
				int totalBytesTransmitted = 0;
				for (LogEntry result : results) {
					try {
						System.out.println(result.future.get());
						int temp = Integer.parseInt(result.future.get());
						totalBytesTransmitted = totalBytesTransmitted + temp;
					} catch (InterruptedException | ExecutionException ex) {
						System.out.println(result.original);
					}
				}
				System.out.println("total bytes transmitted: "+ totalBytesTransmitted);
			}

			// if option 3 is selected..........
			if(option == 3){
				try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF-8"));) {
					for (String entry = in.readLine(); entry != null; entry = in.readLine()) {
						LookupTask3 task = new LookupTask3(entry);
						Future<String> future = executor.submit(task);
						LogEntry result = new LogEntry(entry, future);
						results.add(result);
					}
				}
				
				LinkedList<String> hostNames = new LinkedList<String>();
				LinkedList<Integer> bytesTransmitted = new LinkedList<Integer>();

				for (LogEntry result : results) {
					try {
						int index1 = result.future.get().indexOf(' ');
						int index2 = result.future.get().lastIndexOf(' ');

						String tempHost = result.future.get().substring(0,index1+1);
						int tempBytes = Integer.parseInt(result.future.get().substring(index2+1));
						
						if(hostNames.size() == 0){
							hostNames.push(tempHost);
							bytesTransmitted.push(tempBytes);
						}
						else{
							boolean exists = false;
							for(int i = 0; i<hostNames.size(); i++){
								if(tempHost.equals(hostNames.get(i))){
									bytesTransmitted.set(i, bytesTransmitted.get(i)+tempBytes);
									exists = true; 
								}
							}
							if(exists == false){
								hostNames.push(tempHost);
								bytesTransmitted.push(tempBytes);
							}
						}
					} 
					catch (InterruptedException | ExecutionException ex) {
						System.out.println(result.original);
					}
				}
				for(int i = 0; i<hostNames.size(); i++){
					System.out.println(hostNames.get(i) + " total Bytes Transmitted: " +bytesTransmitted.get(i));
				}
			}

		executor.shutdown();
	}

	private static class LogEntry {
		String original;
		Future<String> future;

		LogEntry(String original, Future<String> future) {
			this.original = original;
			this.future = future;
		}
	}
}
