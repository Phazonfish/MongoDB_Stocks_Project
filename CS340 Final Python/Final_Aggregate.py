import json
import sys
from bson import json_util
from pymongo import MongoClient
connection = MongoClient('localhost', 27017)
db = connection['market']
collection = db['stocks']
def aggregate_stocks():
  try:
    pipeline = [ {"$match" : {"Sector" : sys.argv[1]} }, {"$group" : {"_id" : "$Industry", "Total Outstanding Shares" : {"$sum" : "$Shares Outstanding"} } } ]
    result=collection.aggregate(pipeline)
  except Exception as ve:
    return ve
  return result
def main():
  output = aggregate_stocks()
  for entry in output:
    print entry
main()