package cz.muni.fi.mir.sandbox;


import cz.muni.fi.mir.domain.User;
import cz.muni.fi.mir.domain.UserRole;
import cz.muni.fi.mir.service.UserRoleService;
import cz.muni.fi.mir.service.UserService;
import cz.muni.fi.mir.tools.EntityFactory;
import cz.muni.fi.mir.tools.SimilarityFormConverter;
import cz.muni.fi.mir.tools.Tools;
import cz.muni.fi.mir.wrappers.ApplicationContextWrapper;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Empt
 */
public class Main
{
    private static final ApplicationContextWrapper A = ApplicationContextWrapper.getInstance();
    
    private static UserService userService = A.getBean("userService");
    private static UserRoleService userRoleService = A.getBean("userRoleService");
    private static SimilarityFormConverter similarityFormConverter = A.getBean("similarityFormConverter");
    
    public static void main(String[] args)
    {  
      
        System.out.println(similarityFormConverter.convert(getFirstXML()));
        System.out.println(similarityFormConverter.convert(getSecondXML()));
        
    }
    
    private static String getFirstXML()
    {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
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
    }
    
    
    private static String getSecondXML()
    {
        return "<math mathvariant='italic'>\n" +
"    <mrow>\n" +
"        <mmultiscripts>\n" +
"            <mtext>R</mtext>\n" +
"            <mtext>i</mtext>\n" +
"            <none></none>\n" +
"            <none></none>\n" +
"            <mtext>j</mtext>\n" +
"            <mtext>kl</mtext>\n" +
"            <none></none>\n" +
"        </mmultiscripts>\n" +
"        <mo>=</mo>\n" +
"        <msup>\n" +
"            <mtext>g</mtext>\n" +
"            <mtext>jm</mtext>\n" +
"        </msup>\n" +
"        <msub>\n" +
"            <mtext>R</mtext>\n" +
"            <mtext>imkl</mtext>\n" +
"        </msub>\n" +
"        <mo>+</mo>\n" +
"        <msqrt>\n" +
"            <mn>1</mn>\n" +
"            <mo>-</mo>\n" +
"            <msup>\n" +
"                <mtext>g</mtext>\n" +
"                <mtext>jm</mtext>\n" +
"            </msup>\n" +
"            <msub>\n" +
"                <mtext>R</mtext>\n" +
"                <mtext>mikl</mtext>\n" +
"            </msub>\n" +
"        </msqrt>\n" +
"    </mrow>\n" +
"</math>";
    }
}
