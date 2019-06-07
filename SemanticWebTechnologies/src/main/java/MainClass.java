import java.io.PrintWriter;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDFS;


/**
 *
 * @author enrico
 */
public class MainClass {

    public static void main(String[] args) {
        String sURI = "http://dbpedia.org/page/Virtuoso_Universal_Server/";
        
        try {
            // Creazione di un grafo vuoto
            Model model = ModelFactory.createDefaultModel();
            model.read(sURI);
            Resource res = model.getResource("http://www.semanticweb.org/ontologies/2018/9/untitled-ontology-53#Body_Temp");
            StmtIterator iter = res.listProperties();
            while(iter.hasNext()){
                Statement stmt = iter.next();
                Resource r = stmt.getSubject();
                System.err.print(r.getLocalName());
                
                Property p = stmt.getPredicate();
                System.err.print(" ---- " + p.getLocalName());
                
                RDFNode node = stmt.getObject();
                
                System.err.println(" ----> " + node.toString());
            }
            String s = ""
                    + "\nSELECT ?a ?c"
                    + "\nWHERE{?a rdf:type ?c.}";
            //------------------------------------------------------QUERY
            Query query = QueryFactory.create(s);
            // Esecuzione della query e cattura dei risultati
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();
            // Stampa dei risultati su schermo
            ResultSetFormatter.out(System.out, results, query);
            // Chiusura della struttura dati per il rilascio di memoria
            qe.close();

            } catch (Exception e) {
                System.out.println("Failed: " + e);
            }
            
        }
        
    }
