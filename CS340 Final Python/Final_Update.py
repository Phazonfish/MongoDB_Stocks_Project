import json
import sys
import pymongo
from bson import json_util
from pymongo import MongoClient
connection = MongoClient('localhost', 27017)
db = connection['market']
collection = db['stocks']
def update_document():
  targetTicker = sys.argv[1]
  newVolume = sys.argv[2]
  if newVolume > 0:
    try:
      result = collection.update_many({"Ticker" : targetTicker}, {'$set' : {"Volume" : newVolume}})
    except Exception as ve:
      return ve
  else:
    result = "Please enter a positive value."
  return result
def main():
  print update_document()
main()