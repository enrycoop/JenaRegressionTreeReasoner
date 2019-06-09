import java.util.LinkedList;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
/**
 *
 * @author enrico coppolecchia
 */
public class MainClass {
    
    public static void main(String[] args) {
        PreProcessingPlanetData downloader = new PreProcessingPlanetData();
        System.out.println(downloader.downloadData());
    }
    
}
