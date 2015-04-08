import re
import sys

string = 'WORKERGENID1.KEYSTOREPATH='
exp = re.compile(string)

filePath = '/resources/signserver/qs_pdfsigner_configuration.properties'
newFilePath = '/signserver-3.5.0/doc/sample-configs/qs_pdfsigner_configuration.properties'
keyPath = '/signserver-3.5.0/keys/signserver.p12'
fullNewFilePath = sys.argv[1]+newFilePath
fullFilePath = sys.argv[1]+filePath
fullKeyPath = sys.argv[1]+keyPath

fRead = open(fullFilePath, 'r')
fWrite = open(fullNewFilePath, 'w')

for line in fRead:
  match = exp.match(line)
  if match:
    fWrite.write(string+fullKeyPath+'\n')
  else:
    fWrite.write(line)

fRead.close()
fWrite.close()