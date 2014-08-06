/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.similarity;

import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.tools.MapValueComparator;
import cz.muni.fi.mir.tools.XMLUtils;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.log4j.Logger;

/**
 * Class responsible for converting given MathML input into some kind of value
 * that will be used for similarity matching algorithm. This class and required
 * dependencies are managed by Spring application context. To use this class in
 * project use @Autowired with <b>similarityFormConverter</b> qualifier.
 *
 * @author Dominik Szalai
 * @version 1.0
 * @since 1.0
 */
@Component(value = "similarityFormConverter")
public class SimilarityFormConverter
{

    private static final Logger logger = Logger.getLogger(SimilarityFormConverter.class);

    @Autowired
    private XMLUtils xmlUtils;

    public SimilarityForms process(CanonicOutput canonicOutput)
    {
        SimilarityForms sf = new SimilarityForms();
        sf.setDefaultForm(canonicOutput.getOutputForm());
        sf.setCountForm(getElementCountMethod(canonicOutput.getOutputForm()).toString());
        sf.setDistanceForm(getDistanceMethod(canonicOutput.getOutputForm()));
        sf.setLongestBranch(getBranchMethod(canonicOutput.getOutputForm()));

        return sf;
    }

    /**
     * Method used for obtaining similar form based on element count method.
     *
     * @param document
     * @return
     */
    private Map<String, Integer> getElementCountMethod(String input)
    {
        Document document = stringToDoc(input);

        Map<String, Integer> map = new HashMap<>();

        countElementTravel(document.getDocumentElement(), map);

        MapValueComparator<String, Integer> mvc = new MapValueComparator<>();

        Map<String, Integer> sortedMap = mvc.sortByValues(map);

        return sortedMap;
    }

    /**
     * Method used for obtaining similar form for given MathML input. The main
     * goal is to obtain some kind of canonic form of canonic form obtained via
     * Canonicalizer. Let's consider following MathML input
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
     * </pre> For this MathML it's similar canonic form would be x+yEmc. The
     * conversion is pretty easy. As we traverse the DOM model we put each text
     * node on output. So in our example first hit for text node is <b>x</b>
     * then <b>+</b> and so on. If input does not have well formed XML then
     * exception is catch and we return empty String.
     *
     * @param doc document representing String content of MathML document
     * @return so called similarity form
     */
    private String getDistanceMethod(String input)
    {
        Document doc = stringToDoc(input);
        StringBuilder sb = new StringBuilder();

        if (doc != null)
        {
            distanceTravel(doc.getDocumentElement(), sb);
        }

        return sb.toString();
    }

    /**
     * Method returns lenght of longes branch in xml document.
     *
     * @param input
     * @return
     */
    private String getBranchMethod(String input)
    {
        Document doc = stringToDoc(input);

        MaxValue mv = new MaxValue(0);
        if (doc != null)
        {
            int depth = 0;
            branchTravel(doc.getDocumentElement(), depth, mv);
        }

        // lucene works only with strings and numbers should be padded
        // at least by reference manual
        return org.apache.commons.lang3.StringUtils.leftPad(Integer.toString(mv.getValue()), 4, "0");
    }

    /**
     * Method used for traveling inside XML tree. We recursively call this
     * method on each node. Last node, that has some text has 1 child <b>text
     * node</b>, thats why we stop there, and append its contents into
     * StringBuilder class.
     *
     * @param node to be examined
     * @param sb into which is output text appended
     */
    private void distanceTravel(Node node, StringBuilder sb)
    {
        if (node == null)
        {
            return;
        }

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE)
            {   //last node has 1 child ~> text value
                if (currentNode.getChildNodes().getLength() == 1)
                {
                    sb.append(StringUtils.trimAllWhitespace(currentNode.getTextContent()));
                }
                else
                {   // there is something more so we continue in greater depth
                    distanceTravel(currentNode, sb);
                }
            }
        }
    }

    /**
     * Method uses document travel based on DFS algorithm, each time node is
     * read it is put into map with element name as key and value as count (1).
     * If there is already such key then value is incremented by 1.
     *
     * @param node Root node of document
     * @param map map containing tuples [element,occurences]
     */
    private void countElementTravel(Node node, Map<String, Integer> map)
    {
        if (node == null)
        {
            return;
        }

        NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                if (map.containsKey(currentNode.getNodeName()))
                {
                    Integer value = map.get(currentNode.getNodeName());
                    value++;
                    map.put(currentNode.getNodeName(), value);
                }
                else
                {
                    map.put(currentNode.getNodeName(), 1);
                }
                countElementTravel(currentNode, map);
            }
        }
    }

    private void branchTravel(Node node, int depth, MaxValue mv)
    {
        depth++;

        if (depth > mv.getValue())
        {
            mv.setValue(depth);
        }
        if (node == null)
        {
            return;
        }

        NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE)
            {
                branchTravel(currentNode, depth, mv);

            }
        }
    }

    /**
     * Method convers given input string into Document.
     *
     * @param input string representation of xml document
     * @return Document where content is string input
     */
    private Document stringToDoc(String input)
    {
        return xmlUtils.parse(input);
    }

    // nasty workaround because int is immutable :)
    // and we cannot use static variable, as this SimilarityFormCOnverter
    // is singleton (shared accross many instances)
    private class MaxValue
    {

        private int value;
        private int position;   //specifies where the longest branch starts
                                //not yet implemented

        /**
         * Sets new default max value
         *
         * @param value new max value
         */
        public MaxValue(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }

        public void setValue(int value)
        {
            this.value = value;
        }

        public int getPosition()
        {
            return position;
        }

        public void setPosition(int position)
        {
            this.position = position;
        }
    }
}
