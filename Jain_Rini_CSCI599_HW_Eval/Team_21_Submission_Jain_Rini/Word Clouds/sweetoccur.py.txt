import subprocess
import os
import json
import operator

currentFolder = os.path.dirname(os.path.realpath(__file__))
output_folder = currentFolder + '/output_new/'

def getfiles(filename):
    command = 'java -Dner.impl.class=org.apache.tika.parser.ner.regex.RegexNERecogniser -classpath '
    command += currentFolder + '/:' + currentFolder +'/tika-app-1.12.jar org.apache.tika.cli.TikaCLI'
    command += ' --config=' + currentFolder + '/tika-config.xml -m ' + filename
    data = {}
    for line in runProcess(command.split()):
               if line.startswith('NER_SWEET_'):
            line = line[10:]
            index = line.index(':')
            OWL_class = line[:index]
            values = line[index+1:].strip().lower()
            if OWL_class in data:
                class_data = data[OWL_class]
                if values in class_data:
                    class_data[values] += 1
                else:
                    class_data[values] = 1
            else:
                class_data = {values: 1}
                data[OWL_class] = class_data
    file_data = {
        'file' : filename,
        'tags': data
    }
    return file_data

def readFiles():
    finalList = {}
    for root, directories, files in os.walk(output_folder):
        for filename in files:
            if filename != '.DS_Store':
                filepath = os.path.join(root, filename)

                d2 = json.load(open(filepath))
                for OWL_class, OWL_value in d2['tags'].iteritems():
                    if OWL_class in finalList:
                        total_count = finalList[OWL_class]
                        for class_data, count in OWL_value.iteritems():
                            total_count += count
                        finalList[OWL_class] = total_count
                    else:
                        total_count = 0
                        for class_data, count in OWL_value.iteritems():
                            total_count += count
                        finalList[OWL_class] = total_count
    sorted_x = sorted(finalList.items(), key=operator.itemgetter(1), reverse=True)
    print json.dumps(sorted_x[:20])
    print len(sorted_x)

    def writeFile(filename, content):
    if '.' in filename:
        filename = filename[:filename.index('.')]
    json.dump(content, open(output_folder+filename,'w'))

def getTags():
    finalList = {}
    for root, directories, files in os.walk(output_folder):
        for filename in files:
            if filename != '.DS_Store':
                filepath = os.path.join(root, filename)

                jsonObj = json.load(open(filepath))
                for OWL_class, OWL_value in jsonObj['tags'].iteritems():
                    if OWL_class in finalList:
                        total_count = finalList[OWL_class]
                        for class_data, count in OWL_value.iteritems():
                            total_count += count
                        finalList[OWL_class] = total_count
                    else:
                        total_count = 0
                        for class_data, count in OWL_value.iteritems():
                            total_count += count
                        finalList[OWL_class] = total_count
def runProcess(exe):
    p = subprocess.Popen(exe, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    while(True):
        retcode = p.poll()
        line = p.stdout.readline()
        yield line
        if(retcode is not None):
            break

def get_filepaths(directory):
       for root, directories, files in os.walk(directory):
        for filename in files:
    	    filepath = os.path.join(root, filename)

            owlList = getfiles(filepath)

            print 'Done with ' + filename
            print 'Found ' + str(len(owlList['tags'])) + ' tags.'
            writeFile(filename, owlLis
if __name__ == '__main__':
    getTags()