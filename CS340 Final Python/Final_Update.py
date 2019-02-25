import json
import sys
import pymongo
from bson import json_util
from pymongo import MongoClient

//Connect to the database and collection
connection = MongoClient('localhost', 27017)
db = connection['market']
collection = db['stocks']

//function for section II C (see rubric)
def update_document():
  targetTicker = sys.argv[1]
  newVolume = sys.argv[2]
  if newVolume > 0:
    try:
      result = collection.update_many({"Ticker" : targetTicker}, {'$set' : {"Volume" : newVolume}})
    except Exception as ve:         //generic exception handling, add specific exception possibilities when possible
      return ve
  else:
    result = "Please enter a positive value."
  return result
def main():
  print update_document()           //print results
main()
