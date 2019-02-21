package hello;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import java.util.List;
import java.util.Set;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.lang.ClassNotFoundException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.FileReader;
import java.nio.file.Paths;
import java.nio.file.Files;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Application {
  public static void update_document(String targetTicker, double newVolume) {
    try {
      MongoClient mongoClient = new MongoClient( "localhost" );
      DB db = mongoClient.getDB( "market" );
      DBCollection coll = db.getCollection("stocks");
      
      BasicDBObject newDocument = new BasicDBObject();
      newDocument.append("$set", new BasicDBObject().append("Volume", newVolume));
      BasicDBObject searchQuery = new BasicDBObject().append("Ticker", targetTicker);
      if (newVolume > 0.0) {
        coll.update(searchQuery, newDocument);
      } else {
        System.out.println("Please use a positive value.");
      }
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static void main( String[] args ) {
    try {
      String tickerArg = args[0];
      double volumeArg = Double.parseDouble(args[1]);
      update_document(tickerArg, volumeArg);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}