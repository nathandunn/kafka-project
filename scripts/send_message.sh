
curl -XPUT 'http://localhost:9200/twitter/' -d '
index :
    number_of_shards : 3 
    number_of_replicas : 2 
'

#PUT /blogs/_settings
#    {
#       "number_of_replicas" : 2
#    }
