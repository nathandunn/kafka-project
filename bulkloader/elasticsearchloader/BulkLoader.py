
from datetime import datetime
from elasticsearch import Elasticsearch
import time
import datetime

# by default we connect to localhost:9200
es = Elasticsearch()

from Bio import SeqIO
#record = next(SeqIO.parse("/Users/NathanDunn/hg/kafka-project/elasticsearch/SRA/SAMPLE.fastq", "fastq-sanger"))
#print record 
#print "NAME: " + record.name
#print "DESCRIPTION: " + record.description
#print "SEQ: " + record.seq
#print "QUAL: " + str(record.letter_annotations["phred_quality"])

#print len(record)

handle = open("/Users/NathanDunn/hg/kafka-project/elasticsearch/SRA/DRR000007.fastq", "rU")

count = 0 
start_time = time.time()
epoch_start_time = time.time()
epoch_count = 10000

for record in SeqIO.parse(handle, "fastq") :
#  print record
#  print "NAME: " + record.name
#  print "DESCRIPTION: " + record.description
#  print "SEQ: " + record.seq
#  print "QUAL: " + str(record.letter_annotations["phred_quality"])
  es.index(index="kafka.project",doc_type="test-type",body={ "header":record.description ,"sequence": str(record.seq) ,"quality":str(record.letter_annotations["phred_quality"]) })
  count = count + 1
  if(count%epoch_count==0):
    epoch_stop_time = time.time()
    epoch_time = epoch_stop_time - epoch_start_time
    print "count " + str(count)  + " in time " + str(epoch_stop_time - epoch_start_time) + " " + str(  epoch_count / epoch_time * 1.0 ) 
    epoch_start_time = epoch_stop_time

 
stop_time = time.time()
print "done: "+str(count) + " total time " + str(stop_time - start_time) + " rate: " + str( count / (stop_time-start_time) * 1.0)

# datetimes will be serialized
#es.index(index="my-index", doc_type="test-type", id=42, body={"any": "data", "timestamp": datetime.now()})
#{u'_id': u'42', u'_index': u'my-index', u'_type': u'test-type', u'_version': 1, u'ok': True}

