package codeTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class CodeTestExerciseBadCode {

	public CodeTestExerciseBadCode(){
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CodeTestExerciseBadCode part2 = new CodeTestExerciseBadCode();
		System.out.println(part2.readFile());
		System.out.println(part2.readFileFixed());
	}

	public String readFile(){
		File f = null;
		FileReader fr = null;
		StringBuffer content = null;
		try{
			f = new File("c:/samplefile.txt");
			fr = new FileReader(f);
			
			int c;			
			while((c = fr.read()) != -1){				
				if(content == null){
					content = new StringBuffer();
				}
				
				content.append((char)c);
			}
			
			fr.close();			
		}
		catch (Exception e) {
			throw new RuntimeException("An error occured reading your file");
		}		
		
		return content.toString();
	}
	
	/* Code Comments
	 * - Method is not private and making it private does nothing to limit functionality
	 * - Exception being caught is not concise enough. should be an IOException which throws a FileNotFoundException
	 * - No closing of inputreader after reading data into StringBuilder
	 * - Use of BufferedReader was more of a design decision that an error correction which I believe helped reduce code complexity
	 * 	 and amount of variables used in performing function
	 * - Changing the return type to StringBuilder as opposed to String means that toString() does not to be called when returning 
	 *   data
	 */
	
	/**
	 * This method reads the file from the disk if it exists and returns the text in the file to the console. If the file does
	 * not exist a FileNotFoundException is thrown
	 * @return a StringBuilder of the text file split by lines
	 */
	private StringBuilder readFileFixed() {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			String line;
			br = new BufferedReader(new FileReader("c:/samplefile.txt"));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
		} catch (IOException err) {
			System.out.println (err.toString());
		}
		return sb;
	}
}