package course.homework.test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();

        MongoDatabase database = client.getDatabase("students");
        final MongoCollection<Document> collection = database.getCollection("grades");


        List<Document> all = collection.find(new Document("type", "homework"))
                .sort(new Document("value", 1).append("student_id", 1)).into(new ArrayList<>());
        int prevsid = -1;
        for(Document d: all){
            if( d.getInteger("student_id")!= prevsid)
                collection.deleteOne(Filters.eq("_id", d.getObjectId("_id")));
                //System.out.println(d);

            prevsid =  d.getInteger("student_id");
        }

    }
}
