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

public class LanguageAnalysis1
{

	public static String detectLanguage(String FilePath) throws IOException, SAXException, TikaException
	{
				
		
		Parser parser = new AutoDetectParser();
	    BodyContentHandler handler = new BodyContentHandler();
	    Metadata metadata = new Metadata();
		LanguageIdentifier object = new LanguageIdentifier(handler.toString());
		String filePath = FilePath.replace("\\", "\\\\");
		File file = new File(filePath);
		FileInputStream content = new FileInputStream(file);
	    parser.parse(content, handler, metadata, new ParseContext());
	    return object.getLanguage();
	}

	static HashMap<String, ArrayList<String>> languageMap = new HashMap<String, ArrayList<String>>();
	
	public static void showFiles(File[] files)throws Exception
	{
		String language1 = null;
		
		for (File file: files){
			if (file.isDirectory()){
				showFiles(file.listFiles());
				
			} else 
			{
				language1 = detectLanguage(file.getCanonicalPath());
				String FileName=file.getCanonicalPath();
				boolean keyPresent = languageMap.containsKey(language1);
				if (keyPresent)
				{
					languageMap.get(language1).add(FileName);
				}
				else
				{
					ArrayList<String> fileNameList = new ArrayList<String>();
					fileNameList.add(FileName);
					languageMap.put(language1, fileNameList);
				}
				
				for(String language1 : languageMap.keySet())
				{
					System.out.println("Language identified for the file " + file.getName() + " is : " + language1);
					
				}
				
			}
			
		}
		
		JSONArray langDetail = new JSONArray();
		JSONObject[] language_obj = new JSONObject[languageMap.size()];
		int i =0 ;
		for(String language : languageMap.keySet())
		{
			language_obj[i] = new JSONObject();
			language_obj[i].put("Language", language);
			language_obj[i].put("Num", languageMap.get(language).size());
			
			langDetail.add(language_obj[i]);
			i++;
		}
		
		try (FileWriter file = new FileWriter("E:\\text_htmlfile1.json"))
		{
			file.write(langDetail.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + langDetail);
		}
		
	}
	
	public static void main(String[] args) throws Exception
	{
		File[] files = new File("E:\\text_html\\").listFiles();
		showFiles(files);
	}
	
}