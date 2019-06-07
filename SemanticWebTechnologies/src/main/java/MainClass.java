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
        try (RDFConnection conn = RDFConnectionFactory.connect("http://dbpedia.org/sparql/")) {
           
            QueryExecution qExec = conn.query("PREFIX dbpedia: <http://dbpedia.org/ontology/>\n"
                    + "   SELECT DISTINCT ?mag ?apoapsis ?escape ?rotPer\n"
                    + "   WHERE{\n"
                    + "      ?CelestialBody a dbpedia:Planet.\n"
                    + "      ?CelestialBody dbpedia:absoluteMagnitude ?mag.\n"
                    + "      ?CelestialBody dbpedia:apoapsis ?apoapsis.\n"
                    + "      ?CelestialBody dbpedia:escapeVelocity ?escape.\n"
                    + "      ?CelestialBody dbpedia:rotationPeriod ?rotPer.\n"
                    + "   } ");
            ResultSet rs = qExec.execSelect();
            
            LinkedList<LinkedList<Double>> dataset = new LinkedList<LinkedList<Double>>();
            while (rs.hasNext()) {
                QuerySolution qs = rs.next();
                LinkedList<Double> touple = new LinkedList<Double>();
                touple.add(qs.getLiteral("apoapsis").getDouble());
                touple.add(qs.getLiteral("escape").getDouble());
                touple.add(qs.getLiteral("rotPer").getDouble());
                touple.add(qs.getLiteral("mag").getDouble());
                dataset.add(touple);
            }
            System.out.println(dataset);
            qExec.close();
        }
    }
    
    public static Double[][] parseToArray(LinkedList<LinkedList<Double>> dataset){
        Double[][] array = new Double[dataset.size()][dataset.get(0).size()];
        for(int i = 0 ; i<dataset.size();i++ )
            for(int j =0 ; j< dataset.get(0).size();j++){
                array[i][j] = dataset.get(i).get(j);
            }
        return array;
    }

}
