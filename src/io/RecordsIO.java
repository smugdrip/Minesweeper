/**
 * 
 */
package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Handle the reading and writing of score files.
 * @author John
 *
 */
public class RecordsIO {

	
	private static final String DEFAULT_RECORDS = "Easy,Anonymous,9999,Intermediate,Anonymous,9999,Expert,Anonymous,9999";
	
	
	public static final String RECORD_FILE_NAME = System.getProperty("user.home") + "\\AppData\\Minesweeper\\records.txt";
	
	
	/**
	 * Read records from the user's system or create a records file if there is none
	 * @return
	 */
	public static String readRecords() {
		
		String records = "";
		try {
			Scanner scan = new Scanner(new FileInputStream(RECORD_FILE_NAME));
			while (scan.hasNextLine()) {
				records = records + scan.nextLine();
			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			
			if(new File(System.getProperty("user.home") + "\\AppData\\Minesweeper\\").mkdirs()) {
				writeRecords(DEFAULT_RECORDS);
				return DEFAULT_RECORDS;
			} else {
				throw new IllegalArgumentException("Could not generate required file");
			}
			
		}
		return records;
	}
	
	/**
	 * Write the new records to the user's record file.
	 * @param records
	 */
	public static void writeRecords(String records) {
		try (PrintStream writer = new PrintStream(new File(RECORD_FILE_NAME))) {
			writer.print(records);
			writer.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new IllegalArgumentException("Unable to save file.");
		}
	}
	

}
