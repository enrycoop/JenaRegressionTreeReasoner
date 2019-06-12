
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
