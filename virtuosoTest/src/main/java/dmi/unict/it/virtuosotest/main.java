/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmi.unict.it.virtuosotest;


import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.iterator.ExtendedIterator;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Node_URI;

/**
 *
 * @author Daniele Francesco Santamaria
 */
public class main
  {
    public static void main ( String args[])
      {
        /* Access graph */
        // see http://docs.openlinksw.com/virtuoso/virtdsnsetup/ for ODBC configuration
       	VirtGraph set = new VirtGraph ("jdbc:virtuoso://localhost:1111", "dba", "dba");
        /* Query the graph */
        Query sparql = QueryFactory.create("SELECT * WHERE { GRAPH ?graph { ?s ?p ?o } } limit 100");        
	VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, set);
	ResultSet results = vqe.execSelect();
	while (results.hasNext())
         {
	   QuerySolution result = results.nextSolution();
	   RDFNode graph = result.get("graph");
	   RDFNode s = result.get("s");
	   RDFNode p = result.get("p");
	   RDFNode o = result.get("o");
	   System.out.println(graph + " { " + s + " " + p + " " + o + " . }");
	 }
        /* upload a graph */
        set.read("http://www.w3.org/People/Berners-Lee/card#i", "RDF/XML");   
        /* add a triple*/
        set.add(Triple.create(NodeFactory.createURI("http://example.org/#foo1"),
                              NodeFactory.createURI("http://example.org/#bar1"), 
                              NodeFactory.createURI("http://example.org/#baz1")));
        /* query */
        sparql = QueryFactory.create("SELECT ?s ?p ?o WHERE { ?s ?p ?o }");
	vqe = VirtuosoQueryExecutionFactory.create (sparql, set);
        results = vqe.execSelect();
        while (results.hasNext())
         {
	   QuerySolution result = results.nextSolution();
	   RDFNode graph_name = result.get("graph");
	   RDFNode s = result.get("s");
	   RDFNode p = result.get("p");
	   RDFNode o = result.get("o");
	   System.out.println(graph_name + " { " + s + " " + p + " " + o + " . }");
	 }
        /* transact */
        set.getTransactionHandler().begin();
	System.out.println("Begin Transaction.");
	System.out.println("Add triples to graph.");
        set.add(Triple.create(NodeFactory.createURI("http://example.org/#foo2"),
                              NodeFactory.createURI("http://example.org/#bar2"), 
                              NodeFactory.createURI("http://example.org/#baz2")));
        set.getTransactionHandler().commit(); // set.getTransactionHandler().abort();
              
        
        /* explore the graph */
        ExtendedIterator iter = set.find(NodeFactory.createURI("http://example.org/#foo1"), Node.ANY, Node.ANY);
	System.out.println ("\ngraph.find(foo1, Node.ANY, Node.ANY) \nResult:");
	for ( ; iter.hasNext() ; )
	    System.out.println ((Triple) iter.next());
        /* clear graph */
        set.clear ();
     }  
  }
