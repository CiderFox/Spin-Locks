/** Journal.java
 * 
 * this class is used from the Farm program. This class
 * helps write to the JTextArea in the GUI and outputs a 
 * file with the same information produced on the JTextArea. 
 * 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class Journal {
	private TextAreaWrite writeAreaText;
	private BufferedWriter writer;
	private File file;

	/** Journal()
	 * 
	 * this constructor creates a new file by calling the method
	 * setFile() within this class.
	 * 
	 */

	public Journal() {
		try {
			setFile(new File("notebook.txt"));
		} catch (IOException e) {
			System.out.println("Error creating file.");
		}
	}
	public void setReadout(TextAreaWrite taw) {
		writeAreaText = taw;
	}

	/** set(String string, int length)
	 * 
	 * this method takes in a sting and sets the format according
	 * to the length that was passed in.
	 * 
	 */

	public static String set(String string, int length) {
		return String.format("%1$"+length+ "s", string);
	}

	/** note(String string)
	 * 
	 * this method has the buffered writer write each string to the file.
	 * and also reads out the text onto the JTextArea on the GUI.
	 * 
	 */

	public void note(String string) {
		if (writeAreaText != null) {
			writeAreaText.writeText(string);
		}
		if (writer != null) {
			try {
				writer.write(string);
				writer.write(System.getProperty("line.separator"));
				writer.flush();
			} catch (IOException e) {}
		}
	}

	/** setFile(File outFile)
	 * 
	 * this method creates the new file of 
	 * information to be written to.	
	 * 
	 */

	public void setFile(File outFile) throws IOException {
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		file = outFile;
		FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
		writer = new BufferedWriter(fw);
	}
}