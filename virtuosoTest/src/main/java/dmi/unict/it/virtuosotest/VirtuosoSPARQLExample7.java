package dmi.unict.it.virtuosotest;

/*
 *  $Id: VirtuosoSPARQLExample7.java,v 1.1 2008/04/14 07:31:30 source Exp $
 *
 *  This file is part of the OpenLink Software Virtuoso Open-Source (VOS)
 *  project.
 *
 *  Copyright (C) 1998-2007 OpenLink Software
 *
 *  This project is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the
 *  Free Software Foundation; only version 2 of the License, dated June 1991.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */

//package virtuoso.jena.driver;

import java.util.*;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.util.iterator.ExtendedIterator;



import virtuoso.jena.driver.*;

public class VirtuosoSPARQLExample7
{

    public static void main(String[] args)
    {

	Node foo1 = NodeFactory.createURI("http://example.org/#foo1");
	Node bar1 = NodeFactory.createURI("http://example.org/#bar1");
	Node baz1 = NodeFactory.createURI("http://example.org/#baz1");

	Node foo2 = NodeFactory.createURI("http://example.org/#foo2");
	Node bar2 = NodeFactory.createURI("http://example.org/#bar2");
	Node baz2 = NodeFactory.createURI("http://example.org/#baz2");

	Node foo3 = NodeFactory.createURI("http://example.org/#foo3");
	Node bar3 = NodeFactory.createURI("http://example.org/#bar3");
	Node baz3 = NodeFactory.createURI("http://example.org/#baz3");

	List triples1 = new ArrayList();
	triples1.add(new Triple(foo1, bar1, baz1));
	triples1.add(new Triple(foo2, bar2, baz2));
	triples1.add(new Triple(foo3, bar3, baz3));

	List triples2 = new ArrayList();
	triples2.add(new Triple(foo1, bar1, baz1));
	triples2.add(new Triple(foo2, bar2, baz2));

	VirtGraph graph = new VirtGraph ("Example7", "jdbc:virtuoso://localhost:1111", "dba", "dba");

	graph.clear ();

	System.out.println("graph.isEmpty() = " + graph.isEmpty());
	System.out.println("Add List with 3 triples to graph <Example7> via BulkUpdateHandler.");

        /*
        Bulk updates are not necessarily transactions; that is, a bulk update may fail part-way through, 
        leaving some but not all triples added or deleted. However, if a bulk update does not fail (ie throw an exception) 
        then the addition or removal of triples must have been successfully completed in accordance with the operation of the owning graph.
        */
        
	//Deprecated //graph.getBulkUpdateHandler().add(triples1);
        GraphUtil.add(graph, triples1);
        
	System.out.println("graph.isEmpty() = " + graph.isEmpty());
	System.out.println("graph.getCount() = " + graph.getCount());

	ExtendedIterator iter = graph.find(Node.ANY, Node.ANY, Node.ANY);
	System.out.println ("\ngraph.find(Node.ANY, Node.ANY, Node.ANY) \nResult:");
	for ( ; iter.hasNext() ; )
	    System.out.println ((Triple) iter.next());


	System.out.println("\n\nDelete List of 2 triples from graph <Example7> via BulkUpdateHandler.");

	//Deprecated // graph.getBulkUpdateHandler().delete(triples2);
        GraphUtil.delete(graph, triples1);
        
	System.out.println("graph.isEmpty() = " + graph.isEmpty());
	System.out.println("graph.getCount() = " + graph.getCount());

	iter = graph.find(Node.ANY, Node.ANY, Node.ANY);
	System.out.println ("\ngraph.find(Node.ANY, Node.ANY, Node.ANY) \nResult:");
	for ( ; iter.hasNext() ; )
	    System.out.println ((Triple) iter.next());

	graph.clear ();
	System.out.println("\nCLEAR graph <Example7>");

    }
}
