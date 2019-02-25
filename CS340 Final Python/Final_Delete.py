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
def delete_document():
  tickerString = sys.argv[1]
  deleteQuery = { "Ticker": tickerString }
  try:
    result=collection.delete_many(deleteQuery)
  except Exception as ve:         //generic exception handling, add specific exception possibilities when possible
    return ve
  return result
def main():
  print delete_document()         //print results
main()
