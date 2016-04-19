import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.tika.language.*;


import org.xml.sax.SAXException;

public class LanguageAnalysis1 {
 
	public static String detectLanguage(String FilePath) throws IOException, SAXException, TikaException{
				
		  String filePath = FilePath.replace("\\", "\\\\");

		  File file = new File(filePath);

	      //Parser method parameters
	      Parser parser = new AutoDetectParser();
	      BodyContentHandler handler = new BodyContentHandler();
	      Metadata metadata = new Metadata();

	      FileInputStream content = new FileInputStream(file);

	      //Parsing the given document
	      parser.parse(content, handler, metadata, new ParseContext());

	      LanguageIdentifier object = new LanguageIdentifier(handler.toString());
	      
	      return object.getLanguage();
	
}
	
	
public static void writeJson() throws IOException{
		
		JSONArray langDetail = new JSONArray();
		
		try (FileWriter file = new FileWriter("E:\\text_htmlfile1.json")) {
			file.write(langDetail.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + langDetail);
		}
		
	}

	
	public static void showFiles(File[] files)throws Exception{
		String language1 = null;
		
		for (File file: files){
			if (file.isDirectory()){
				//System.out.println("Directory : " + file.getName());
				showFiles(file.listFiles());
				
			} else {
				//System.out.println("File : " + file.getName());
				//System.out.println("File Path : " + file.getCanonicalPath());
				language1 = detectLanguage(file.getCanonicalPath());
				//System.out.println("Language : " + language);
				
				}
				
			}
			
		
		writeJson();
		
	}
	
	
	
	

	
	
	public static void main(String[] args) throws Exception{
		File[] files = new File("E:\\text_html\\").listFiles();
		showFiles(files);
	}
	
}