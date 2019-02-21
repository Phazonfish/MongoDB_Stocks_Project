import json
import sys
import pymongo
from bson import json_util
from pymongo import MongoClient
connection = MongoClient('localhost', 27017)
db = connection['market']
collection = db['stocks']
def delete_document():
  tickerString = sys.argv[1]
  deleteQuery = { "Ticker": tickerString }
  try:
    result=collection.delete_many(deleteQuery)
  except Exception as ve:
    return ve
  return result
def main():
  print delete_document()
main()