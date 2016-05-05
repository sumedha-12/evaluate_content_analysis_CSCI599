import os
import httplib
import requests
import time
import json

def get_filepaths(directory):
    """
    This function will generate the file names in a directory 
    tree by walking the tree either top-down or bottom-up. For each 
    directory in the tree rooted at directory top (including top itself), 
    it yields a 3-tuple (dirpath, dirnames, filenames).
    """
    json_file = open("data.json", "w")
    regEXoutput = {}
    bashCommand = "java -classpath $NER_RES:$TIKA_APP org.apache.tika.cli.TikaCLI --config=tika-config.xml -m "
    for root, directories, files in os.walk(directory):
        for filename in files:
    	    orgString = ""
    	    locString = ""
	    timeString = ""
            percntString = ""
	    personString = ""
	    distString = ""
		    tempString = ""
            pressString = ""
	    wghtString = ""
	    heatString = ""
            speedString = ""
	    dumString = ""
	    fileRegExoutput = {}
            filepath = os.path.join(root, filename)
	    output = os.popen(bashCommand+filepath).read()
	    results = output.split('\n')
	    for result in results:
                if 'NER_TIME' in result:		    
		    timeString+=result[10:]+","
		if 'NER_PERCENTAGE' in result:
		    percntString+=result[16:]+","
		if 'NER_DIST' in result:
		    distString+=result[17:]+","
    		if 'NER_TEMP' in result:
		    tempString+=result[17:]+","
    		if 'NER_PRES' in result:
		    pressString+=result[17:]+","
    		if 'NER_WGHT' in result:
		    wghtString+=result[17:]+","
    		if 'NER_HEAT' in result:
		    heatString+=result[17:]+","
    		if 'NER_SPEED' in result:
		    speedString+=result[18:]+","
    		if 'NER_DUM' in result:
		    dumString+=result[21:]+","
	
            fileRegExoutput["NER_TIME"]=timeString[:-1].split(",")
	    fileRegExoutput["NER_PERCENTAGE"]=percntString[:-1].split(",")
            fileRegExoutput["NER_DISTANCE"]=distString[:-1].split(",")
            fileRegExoutput["NER_TEMPERATURE"]=tempString[:-1].split(",")
            fileRegExoutput["NER_PRESSURE"]=pressString[:-1].split(",")
            fileRegExoutput["NER_WEIGHT"]=wghtString[:-1].split(",")
            fileRegExoutput["NER_HEAT"]=heatString[:-1].split(",")
            fileRegExoutput["NER_SPEED"]=speedString[:-1].split(",")
            fileRegExoutput["NER_DUMMY"]=dumString[:-1].split(",")
            regEXoutput[filename] = fileRegExoutput
            json.dump(regEXoutput, json_file, indent=2)
    json_file.close()
# Run the above function and store its results in a variable.   
#full_file_paths = get_filepaths("/var/www/html/sorteddata/application/pdf")
get_filepaths("/home/sumedha_12/NER/input")

