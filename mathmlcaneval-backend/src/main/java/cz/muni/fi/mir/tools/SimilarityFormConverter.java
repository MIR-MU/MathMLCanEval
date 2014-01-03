/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tools;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Class responsible for converting given MathML input into some kind of value
 * that will be used for similarity matching algorithm. This class and required dependencies
 * are managed by Spring application context. To use this class in project use @Autowired with
 * <b>similarityFormConverter</b> qualifier.
 * @author Dominik Szalai
 * @version 1.0
 */
@Component(value = "similarityFormConverter")
public class SimilarityFormConverter
{
    @Autowired DocumentBuilder documentBuilder; // wired bean used for creating documents
    
    
    /**
     * Method used for obtaining similar form for given MathML input. The main goal
     * is to obtain some kind of canonic form of canonic form obtained via Canonicalizer.
     * Let's consider following MathML input
     * <pre>
     * &lt;root&gt;
     *  &lt;a&gt;
     *      &lt;b&gt;x&lt;/b&gt;
     *      &lt;bb&gt;+&lt;/bb&gt;
     *      &lt;bbb&gt;y&lt;/bbb&gt;
     *  &lt;/a&gt;
     *  &lt;c&gt;
     *      &lt;d&gt;E&lt;/d&gt;
     *      &lt;dd&gt;m&lt;/dd&gt;
     *      &lt;ddd&gt;c&lt;/ddd
     *  &lt;/c&gt;
     * &lt;/root&gt;
     * </pre>
     * For this MathML it's similar canonic form would be x+yEmc. The conversion is pretty
     * easy. As we traverse the DOM model we put each text node on output. So in our 
     * example first hit for text node is <b>x</b> then <b>+</b> and so on. If input does not have
     * well formed XML then exception is catch and we return empty String.
     * @param input string representation of MathML document
     * @return so called similarity form
     */
    public String convert(String input)
    {        
        Document doc = null;
        try
        {
            doc = documentBuilder.parse(new InputSource(new StringReader(input)));
        } catch (SAXException | IOException ex)
        {
            Logger.getLogger(SimilarityFormConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return convert(doc);
        
    }
    
    
    /**
     * See {@link #convert(java.lang.String) } for details how method works
     * @param doc MathML in form of Document
     * @return so called similarity form
     */
    public String convert(Document doc)
    {
        StringBuilder sb = new StringBuilder();
        
        if(doc != null)
        {            
            travel(doc.getDocumentElement(), sb);
        }
        
        return sb.toString();        
    }
    
    
    /**
     * Method used for traveling inside XML tree. We recursively call this method on each node.
     * Last node, that has some text has 1 child <b>text node</b>, thats why we stop there, and append
     * its contents into StringBuilder class.
     * @param node to be examined
     * @param sb into which is output text appended
     */
    private void travel(Node node,StringBuilder sb)
    {
        if(node == null)
        {
            return;
        }
        
        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i < nodeList.getLength();i++)
        {
            Node currentNode = nodeList.item(i);
            if(currentNode.getNodeType() == Node.ELEMENT_NODE)
            {   //last node has 1 chiled ~> text value
                if(currentNode.getChildNodes().getLength() == 1)
                {
                    sb.append(StringUtils.trimAllWhitespace(currentNode.getTextContent()));                    
                }
                else
                {   // there is something more so we continue in greater depth
                    travel(currentNode, sb);
                }  
            }
        }
    }
    
}
