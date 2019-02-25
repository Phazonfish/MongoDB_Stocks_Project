import json
import sys
from bson import json_util
from pymongo import MongoClient

//Connect to the database and collection
connection = MongoClient('localhost', 27017)
db = connection['market']
collection = db['stocks']

//function for section III A i (see rubric)
def find_moving_avg():
  try:
    lowEnd = float(sys.argv[1])
    highEnd = float(sys.argv[2])
    query = { "50-Day Simple Moving Average" : { "$gt" :  lowEnd, "$lt" : highEnd}}
    result=collection.find(query).count()
  except Exception as ve:         //generic exception handling, add specific exception possibilities when possible
    return ve
  return result
def main():
  output = find_moving_avg()
  print output                    //print results
main()
