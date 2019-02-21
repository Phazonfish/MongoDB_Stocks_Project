import json
import sys
from bson import json_util
from pymongo import MongoClient
connection = MongoClient('localhost', 27017)
db = connection['market']
collection = db['stocks']
def read_document():
  try:
    with open("Insert_Input.json", "r") as read_file:
      result = json.load(read_file)
    collection.save(result)
  except Exception as ve:
    return ve
  return result
def main():
  output = read_document()
  print output
main()