
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.PrintUtil;

/**
 *
 * @author enrico coppolecchia
 */
public class MainClass {

    public static void main(String[] args) throws IOException {

        try (RDFConnection conn = RDFConnectionFactory.connect("http://dbpedia.org/sparql/")) {
           
            QueryExecution qExec = conn.query("PREFIX dbpedia: <http://dbpedia.org/ontology/>\n" +
"   SELECT DISTINCT ?CelestialBody ?mag ?apoapsis ?periapsis ?escape ?rotPer ?orbitalPer ?albedo ?target\n" +
"   WHERE{\n" +
"      ?CelestialBody a dbpedia:Planet.\n" +
"      ?CelestialBody dbpedia:absoluteMagnitude ?mag.\n" +
"      ?CelestialBody dbpedia:apoapsis ?apoapsis.\n" +
"      ?CelestialBody dbpedia:escapeVelocity ?escape.\n" +
"      ?CelestialBody dbpedia:rotationPeriod ?rotPer.\n" +
"      ?CelestialBody dbpedia:orbitalPeriod ?orbitalPer.\n" +
"      ?CelestialBody dbpedia:periapsis ?periapsis.\n" +
"      ?CelestialBody dbpedia:albedo ?albedo.\n" +
"      OPTIONAL{\n" +
"       ?CelestialBody dbpedia:temperature ?target.\n" +
"      }\n"
                    + "FILTER(!bound(?target))\n" +
"      \n" +
"   } ");
            ResultSet rs = qExec.execSelect();
            
            BufferedWriter
            sc = new BufferedWriter(new FileWriter("C:\\Users\\enric\\Desktop\\python-works\\SemanticWeb\\Planets.n3"));
            
            while (rs.hasNext()) {
                QuerySolution qs = rs.next();
                sc.write("<"+qs.getResource("CelestialBody")+"> <https://cs.dbpedia.org/ontology/Planet/apoapsis> "+qs.getLiteral("apoapsis").getString()+".\n");
                sc.write("<"+qs.getResource("CelestialBody")+"> <https://cs.dbpedia.org/ontology/Planet/absoluteMagnitude> "+qs.getLiteral("mag").getString()+".\n");
                sc.write("<"+qs.getResource("CelestialBody")+"> <https://cs.dbpedia.org/ontology/Planet/periapsis> "+qs.getLiteral("periapsis").getString()+".\n");
                sc.write("<"+qs.getResource("CelestialBody")+"> <https://cs.dbpedia.org/ontology/Planet/escapeVelocity> "+qs.getLiteral("escape").getString()+".\n");
                sc.write("<"+qs.getResource("CelestialBody")+"> <https://cs.dbpedia.org/ontology/Planet/rotationPeriod> "+qs.getLiteral("rotPer").getString()+".\n");
                sc.write("<"+qs.getResource("CelestialBody")+"> <https://cs.dbpedia.org/ontology/Planet/orbitalPeriod> "+qs.getLiteral("orbitalPer").getString()+".\n");
                sc.write("<"+qs.getResource("CelestialBody")+"> <https://cs.dbpedia.org/ontology/Planet/albedo> "+qs.getLiteral("albedo").getString()+".\n");
            }
            qExec.close();
            sc.close();
        }
        Model data = FileManager.get().loadModel("C:\\Users\\enric\\Desktop\\python-works\\SemanticWeb\\Planets.n3");
        List rules = Rule.rulesFromURL("C:\\Users\\enric\\Desktop\\python-works\\SemanticWeb\\rules_result.rules");

        GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
        
        InfModel inf = ModelFactory.createInfModel(reasoner, data);
        
        
        
        StmtIterator i = inf.listStatements();
        while (i.hasNext()) {
            Statement st = i.nextStatement();
            if(st.asTriple().getPredicate().toString().contains("temperature")){
                System.out.println(st.asTriple().getSubject()+" ----> ");
                
                System.out.println(st.asTriple().getPredicate()+"---->>>>>");
                System.out.println(st.asTriple().getObject());
            }
        }
        
    }

}
