package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
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

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    private MongoClient mongoClient = new MongoClient( "localhost" );
    private DB db = mongoClient.getDB( "market" );
    private DBCollection coll = db.getCollection("stocks");
     

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
  
    @RequestMapping(value = "/stocks/api/v1.0/createStock/{ticker}", method = RequestMethod.POST)
    public BasicDBObject rest_create(@PathVariable("ticker") String ticker, @RequestBody BasicDBObject newStock) {
      BasicDBObject createdStock = newStock;
      createdStock.append("Ticker", ticker);
      coll.insert(createdStock);
      return createdStock;
    }
  
    @RequestMapping(value = "/stocks/api/v1.0/deleteStock/{ticker}", method = RequestMethod.DELETE)
    public void rest_delete(@PathVariable("ticker") String ticker) {
      BasicDBObject document = new BasicDBObject();
      document.put("Ticker", ticker);
      coll.remove(document);
    }
    
    @RequestMapping(value = "/stocks/api/v1.0/getStock/{ticker}")
    public DBObject rest_read(@PathVariable("ticker") String ticker) {
      return coll.findOne(new BasicDBObject("Ticker", ticker));
    }
  
    @RequestMapping(value = "/stocks/api/v1.0/updateStock/{ticker}", method = RequestMethod.PUT)
    public DBObject rest_update(@PathVariable("ticker") String ticker, @RequestBody BasicDBObject revisedStock) {
      BasicDBObject updated_version = revisedStock;
      updated_version.append("Ticker", ticker);
      coll.update(new BasicDBObject("Ticker", ticker) , updated_version);
      return coll.findOne(new BasicDBObject("Ticker", ticker));
    }
  
    @RequestMapping(value = "/stocks/api/v1.0/stockReport", method = RequestMethod.POST)
    public String stock_report(@RequestBody BasicDBObject[] inputStocks) {
      return "";
    }
  
    @RequestMapping(value = "/stocks/api/v1.0/industryReport/{industry}")
    public String industry_report(@PathVariable("industry") String industry) {
      return "";
    }
  
}
