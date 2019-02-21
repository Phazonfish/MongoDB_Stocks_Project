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
  public static void by_industry(String targetIndustry) {
    try {
      MongoClient mongoClient = new MongoClient( "localhost" );
      DB db = mongoClient.getDB( "market" );
      DBCollection coll = db.getCollection("stocks");
      
      BasicDBObject document = new BasicDBObject();
      document.put("Industry", targetIndustry);
      
      DBCursor cursor = coll.find(document);
      while (cursor.hasNext()) {
          DBObject obj = cursor.next();
          System.out.println(obj.get("Ticker"));
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
      String industryArg = args[0];
      by_industry(industryArg);
  }
}