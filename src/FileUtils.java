import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Scanner;

public class FileUtils {
	public static void insertIntoFileAfter(String filePath, String token,
			String text) {
		StringBuilder fileContents = new StringBuilder();
		fileContents.append(readFileIntoString(filePath));

		int tokenIndex = 0;
		while ((tokenIndex = fileContents.indexOf(token, tokenIndex)) >= 0) {
			System.out.println(token+ " found At Index: " + tokenIndex);
			fileContents.insert(tokenIndex + token.length(), text);
			tokenIndex += token.length()+ text.length();
		}
		writeStringToFile(filePath, fileContents.toString());
	}

	public static String readFileIntoString(String filePath) {
		String str = "";
		BufferedReader in = null;
		String fileString = "";
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(
					filePath), "UTF-8"));
			while ((str = in.readLine()) != null) {
				fileString += str +"\n";
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					// close stream
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return fileString;
	}
	public static void writeStringToFile(File file, String text)
	{

		try {
			file.createNewFile();
		} catch (IOException e1) {
			System.out.println("Error Creating file");
			e1.printStackTrace();
		}
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "UTF-8"));
			out.write(text);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void writeStringToFile(String fileName, String text) {

		File file = new File(fileName);
		writeStringToFile(file, text);
	}

	public static String readFileFromTemplate(String templateFilePath) {
		String templateString = "";

		// read in template
		try {
			templateString = new Scanner(new File(templateFilePath))
					.useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			System.out.println("Error Reading file: " + templateFilePath);
		}

		// create doc in directory
		// write string to doc
		return templateString;
	}
}
