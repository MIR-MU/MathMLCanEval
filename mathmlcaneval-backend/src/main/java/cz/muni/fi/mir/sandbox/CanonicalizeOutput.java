/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.sandbox;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.io.IOUtils;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.xml.sax.SAXException;

/**
 * TEST CLASS DO NOT USE
 * TODO
 * @author Empt
 */
public class CanonicalizeOutput
{

    // iba pokus je to pomale ....
    public static void main(String[] args) throws TransformerConfigurationException, TransformerException
    {
        
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<math>\n"
                    + "  <mfrac linethickness=\"1\">\n"
                    + "    \n"
                    + "    <mrow>\n"
                    + "      <mi> x </mi>\n"
                    + "      <mo> + </mo>\n"
                    + "      <mi> y </mi>\n"
                    + "      <mo> + </mo>\n"
                    + "      <mi> z </mi>\n"
                    + "    </mrow>\n"
                    + "    <!-- test -->\n"
                    + "    <mrow>\n"
                    + "      <mi> x </mi>\n"
                    + "      \n"
                    + "      <mo> + </mo>\n"
                    + "      <mi> z </mi>\n"
                    + "    </mrow>\n"
                    + "  </mfrac>\n"
                    + "  <mfenced>\n"
                    + "  </mfenced>\n"
                    + "</math>";
        
        
        CanonicalizeOutput co = new CanonicalizeOutput();
        try
        {
            System.out.println(co.canonicalize(xml));
        } 
        catch (InvalidCanonicalizerException | ParserConfigurationException | IOException | SAXException | CanonicalizationException ex)
        {
            Logger.getLogger(CanonicalizeOutput.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String canonicalize(String input) throws TransformerConfigurationException, TransformerException, InvalidCanonicalizerException, ParserConfigurationException, IOException, SAXException, CanonicalizationException
    {
        
        Canonicalizer.registerDefaultAlgorithms();
        Canonicalizer canon = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N11_OMIT_COMMENTS); // toto dako vobec nejde ako by clovek predpokladal ze pojde... vyhodi to sice comment ale necha tam medzeru


        byte canonXmlBytes[] = canon.canonicalize(xsltTransform(input).getBytes());


        return xsltTransform(new String(canonXmlBytes));
    }
    
    private static Transformer transformer;
    
    private String xsltTransform(String input)
    {
        Source text = new StreamSource(IOUtils.toInputStream(input));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            transformer.transform(text, new StreamResult(bos));
        } catch (TransformerException ex)
        {
            Logger.getLogger(CanonicalizeOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new String(bos.toByteArray());
    }
    
    public CanonicalizeOutput()
    {
        InputStream inputStream = CanonicalizeOutput.class.getClassLoader().getResourceAsStream("other/canon.xsl");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try
        {
            transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(reader));
        } catch (TransformerConfigurationException ex)
        {
            Logger.getLogger(CanonicalizeOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException
//    {
//        TransformerFactory tf = TransformerFactory.newInstance();
//        Transformer transformer = tf.newTransformer();
//        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
//        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
//
//        transformer.transform(new DOMSource(doc),
//                new StreamResult(new OutputStreamWriter(out, "UTF-8")));
//    }
    
    
   
}
