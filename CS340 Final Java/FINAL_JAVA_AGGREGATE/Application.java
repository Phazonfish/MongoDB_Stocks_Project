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
import com.mongodb.client.AggregateIterable;
import com.mongodb.AggregationOptions;
import com.mongodb.AggregationOutput;
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
import java.util.Arrays;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Application {
  public static void final_aggr(String targetSector) {
    try {
      MongoClient mongoClient = new MongoClient( "localhost" );
      DB db = mongoClient.getDB( "market" );
      DBCollection coll = db.getCollection("stocks");
      BasicDBObject newDocument = new BasicDBObject();
      
      Cursor output = coll.aggregate(Arrays.asList(
        new BasicDBObject("$match", new BasicDBObject("Sector", targetSector)),
        new BasicDBObject("$group", 
          new BasicDBObject("_id", "$Industry")
            .append("Total Outstanding Shares",
              new BasicDBObject("$sum", "$Shares Outstanding"))
                         ) ), AggregationOptions.builder().build() ) ;
      
      while(output.hasNext()){
        System.out.println(output.next());
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static void main( String[] args ) {
    try {
      String inputSector = args[0];
      final_aggr(inputSector);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}