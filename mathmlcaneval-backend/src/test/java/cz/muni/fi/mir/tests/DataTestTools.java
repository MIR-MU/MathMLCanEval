/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationFlag;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import cz.muni.fi.mir.tools.EntityFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.joda.time.DateTime;

/**
 *
 * @author Empt
 */
public class DataTestTools
{

    /**
     * Method provides List of AnnotationFlags with following values and order:
     * <ul>
     * <li>VALUE_PROPER_RESULT</li>
     * <li>VALUE_MIGHT_BE_PROPER</li>
     * <li>VALUE_WRONG</li>
     * <li>VALUE_CHECK_PLS</li>
     * </ul>
     *
     * @return List of AnnotationFlags
     */
    public static List<AnnotationFlag> provideAnnotationFlagList()
    {
        List<AnnotationFlag> result = new ArrayList<>();

        result.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_PROPER_RESULT));
        result.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_MIGHT_BE_PROPER));
        result.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_WRONG));
        result.add(EntityFactory.createAnnotaionFlag(AnnotationFlag.VALUE_CHECK_PLS));

        return result;
    }

    /**
     * Method provides List of UserRoles with following values and order:
     * <ul>
     * <li>ROLE_ANONYMOUS</li>
     * <li>ROLE_USER</li>
     * <li>ROLE_ADMINISTRATOR</li>
     * </ul>
     *
     * @return List of UserRoles
     */
    public static List<UserRole> provideUserRolesList()
    {
        List<UserRole> result = new ArrayList<>();
        result.add(EntityFactory.createUserRole("ROLE_ANONYMOUS"));
        result.add(EntityFactory.createUserRole("ROLE_USER"));
        result.add(EntityFactory.createUserRole("ROLE_ADMINISTRATOR"));

        return result;
    }

    /**
     * Method provides List of Users with following values and order
     * <table border="1">
     * <tr>
     * <td>username</td>
     * <td>password</td>
     * <td>real name</td>
     * <td>email</td>
     * <td>roles</td>
     * </tr>
     * <tr>
     * <td>username1</td>
     * <td>password1</td>
     * <td>druhe viac aslovne cislo1</td>
     * <td>example@example.com</td>
     * <td>values from input parameter</td>
     * </tr>
     * <tr>
     * <td>username2</td>
     * <td>password2</td>
     * <td>druhe viac aslovne cislo2</td>
     * <td>example@example.com</td>
     * <td>values from input parameter</td>
     * </tr>
     * <tr>
     * <td>username3</td>
     * <td>password3</td>
     * <td>druhe viac aslovne cislo3</td>
     * <td>example@example.com</td>
     * <td>values from input parameter</td>
     * </tr>
     * </table>
     *
     * @param roles which will be set for all users
     * @return List of users
     */
    public static List<User> provideUserRoleListGeneral(List<UserRole> roles)
    {
        List<User> result = new ArrayList<>();
        result.add(EntityFactory.createUser("username1", "password1", "druhe viac aslovne cislo1", "example1@example.com", roles));
        result.add(EntityFactory.createUser("username2", "password2", "druhe viac aslovne cislo2", "example2@example.com", roles));
        result.add(EntityFactory.createUser("username3", "password3", "druhe viac aslovne cislo3", "example3@example.com", roles));

        return result;
    }

    /**
     * Method is same as {@link #provideUserRoleListGeneral(java.util.List) }
     * with following difference. Roles are set in this way:
     * <table>
     * <tr><td>real name</td><td>roles</td></tr>
     * <tr><td>druhe viac aslovne cislo1</td><td>roles</td></tr>
     * <tr><td>tretie aslovne cislo2</td><td>roles.subList(0, 2)</td></tr>
     * <tr><td>druhe viac aslovne cislo3</td><td>roles.get(0)</td></tr>
     * </table>
     *
     * @param roles to be set for users in specific way
     * @return List of users
     */
    public static List<User> provideUserRoleListSpecific(List<UserRole> roles)
    {
        List<User> result = new ArrayList<>();
        result.add(EntityFactory.createUser("username1", "password1", "druhe viac aslovne cislo1", "example1@example.com", roles));
        result.add(EntityFactory.createUser("username2", "password2", "tretie aslovne cislo2", "example2@example.com", roles.subList(0, 2)));
        result.add(EntityFactory.createUser("username3", "password3", "druhe viac aslovne cislo3", "example3@example.com", roles.get(0)));

        return result;
    }

    /**
     * Method provides List of annotation with following values and order:
     * <table>
     * <tr>
     * <td>note</td>
     * <td>user</td>
     * <td>flag</td>
     * </tr>
     * <tr>
     * <td>poznamka</td>
     * <td>user at index 0</td>
     * <td>flag at index 0</td>
     * </tr>
     * <tr>
     * <td>poznamkaaa</td>
     * <td>user at index 0</td>
     * <td>flag at index 1</td>
     * </tr>
     * <tr>
     * <td>poznamka12313</td>
     * <td>user at index 0</td>
     * <td>flag at index 1</td>
     * </tr>
     * <tr>
     * <td>aha2313</td>
     * <td>user at index 1</td>
     * <td>flag at index 1</td>
     * </tr>
     * <tr>
     * <td>pozyuyiua12313</td>
     * <td>user at index 1</td>
     * <td>flag at index 1</td>
     * </tr>
     * </table>
     *
     * @param users from which values will be selected
     * @param aFlags from which values will be selected
     * @return List of Annotations
     */
    public static List<Annotation> provideAnnotationList(List<User> users, List<AnnotationFlag> aFlags)
    {
        List<Annotation> result = new ArrayList<>();

        result.add(EntityFactory.createAnnotation("poznamka", users.get(0), aFlags.get(0)));
        result.add(EntityFactory.createAnnotation("poznamkaaa", users.get(0), aFlags.get(1)));
        result.add(EntityFactory.createAnnotation("poznamka12313", users.get(0), aFlags.get(1)));
        result.add(EntityFactory.createAnnotation("aha2313", users.get(1), aFlags.get(1)));
        result.add(EntityFactory.createAnnotation("pozyuyiua12313", users.get(1), aFlags.get(1)));

        return result;
    }

    /**
     * Method provides List of Configurations where config triplet is how 
     * {@link TestTools#getConfig(boolean, boolean, boolean) }is called.
     * <table>
     * <tr>
     * <td>config</td>
     * <td>name</td>
     * <td>note</td>
     * </tr>
     * <tr>
     * <td>(true, true, true)</td>
     * <td>vsetko true</td>
     * <td>vsetky hodnoty su true lebo kacka</td>
     * </tr>
     * <tr>
     * <td>(true, true, false)</td>
     * <td>priemerny config 2:1</td>
     * <td>vsetky hodnoty su true lebo medved</td>
     * </tr>
     * <tr>
     * <td>(false, false, false)</td>
     * <td>vsetko false</td>
     * <td>vsetky podnoty su true lebo holub</td>
     * </tr>
     * </table>
     *
     * @return List of Configurations with values specified by table above
     */
    public static List<Configuration> provideConfigurationList()
    {
        List<Configuration> result = new ArrayList<>();

        result.add(EntityFactory.createConfiguration(getConfig(true, true, true), "vsetko true", "vsetky hodnoty su true lebo kacka"));
        result.add(EntityFactory.createConfiguration(getConfig(true, true, false), "priemerny config 2:1", "vsetky hodnoty su true lebo medved"));
        result.add(EntityFactory.createConfiguration(getConfig(false, false, false), "vsetko false", "vsetky podnoty su true lebo holub"));

        return result;
    }

    /**
     * Method provides List of SourceDocuments with given values and order:
     * <table>
     * <tr><td>note</td><td>path</td></tr>
     * <tr><td>poznamka testovacia cislo
     * 1</td><td>/home/empt/input/test1.xhtml</td></tr>
     * <tr><td>poznamka testovacia cislo
     * 2</td><td>/home/empt/input/data/test2.xhtml</td></tr>
     * <tr><td>poznamka testovacia cislo
     * 3</td><td>/home/empt/input/fata/test3.xhtml</td></tr>
     * <tr><td>poznamka testovacia cislo
     * 4</td><td>/home/empt/input/data/test4.xhtml</td></tr>
     * </table>
     *
     * @return List of source documents with values specified by table above
     */
    public static List<SourceDocument> provideSourceDocuments()
    {
        List<SourceDocument> result = new ArrayList<>();

        result.add(EntityFactory.createSourceDocument("poznamka testovacia cislo 1", "/home/empt/input/test1.xhtml"));
        result.add(EntityFactory.createSourceDocument("poznamka testovacia cislo 2", "/home/empt/input/data/test2.xhtml"));
        result.add(EntityFactory.createSourceDocument("poznamka testovacia cislo 3", "/home/empt/input/fata/test3.xhtml"));
        result.add(EntityFactory.createSourceDocument("poznamka testovacia cislo 4", "/home/empt/input/data/test4.xhtml"));

        return result;
    }

    /**
     * Method provides List of formulas with following order and values:
     *
     * @param users
     * @param sourceDocuments
     * @return
     */
    public static List<Formula> provideFormulas(List<User> users, List<SourceDocument> sourceDocuments)
    {
        List<Formula> result = new ArrayList<>();

        result.add(EntityFactory.createFormula(getFirstXML(), DateTime.now(), users.get(0), sourceDocuments.get(0)));
        result.add(EntityFactory.createFormula(getFirstXML(), DateTime.now(), users.get(1), sourceDocuments.get(1)));
        result.add(EntityFactory.createFormula(getSecondXML(), DateTime.now(), users.get(0), sourceDocuments.get(0)));
        result.add(EntityFactory.createFormula(getSecondXML(), DateTime.now(), users.get(1), sourceDocuments.get(1)));

        return result;
    }

    /**
     * Method provides List of Revision with following order and values:
     * <table>
     * <tr><td>hash</td><td>note</td></tr>
     * <tr><td>f383d4a196c27992bf9bcb903919cf354024554a</td><td>nahodna poznamka
     * aby sa nieco naslo</td></tr>
     * <tr><td>f383d4a196c27992bf9bcb903919cf354024554b</td><td>nahodna poznamka
     * aby sa nieco naslo</td></tr>
     * <tr><td>f383d4a196c27992bf9bcb903919cf354024554c</td><td>nahodna poznamka
     * aby sa nieco naslo</td></tr>
     * <tr><td>f383d4a196c27992bf9bcb903919cf354024554d</td><td>toto sa
     * nenajde</td></tr>
     * </table>
     *
     * @return List of revision with values specified by table above.
     */
    public static List<Revision> provideRevisions()
    {
        List<Revision> result = new ArrayList<>();

        result.add(EntityFactory.createRevision("f383d4a196c27992bf9bcb903919cf354024554a", "nahodna poznamka aby sa nieco naslo"));
        result.add(EntityFactory.createRevision("f383d4a196c27992bf9bcb903919cf354024554b", "nahodna poznamka aby si nieco naslo"));
        result.add(EntityFactory.createRevision("f383d4a196c27992bf9bcb903919cf354024554c", "nahodna poznamka aby so nieco naslo"));
        result.add(EntityFactory.createRevision("f383d4a196c27992bf9bcb903919cf354024554d", "toto sa nenajde"));

        return result;
    }

    /**
     * Method create List of programs with given values and order:
     *
     * <table>
     * <tr><td>name</td><td>linux verzia</td><td>-d -s
     * -p</td><td>version</td></tr>
     * <tr><td>texlive</td><td>linux verzia</td><td>-d
     * -s</td><td>2013.2</td></tr>
     * <tr><td>texlive</td><td>linux verzia</td><td>-d
     * -p</td><td>2013.2</td></tr>
     * <tr><td>texlive</td><td>linux verzia</td><td>-a
     * -q</td><td>2013.3</td></tr>
     * <tr><td>miktex</td><td>windows verzia</td><td>-a
     * -q</td><td>2011.4</td></tr>
     * <tr><td>miktex</td><td>linux verzia</td><td>-a
     * -q</td><td>2014.1beta.0.0.3</td></tr>
     * </table>
     *
     * @return
     */
    public static List<Program> providePrograms()
    {
        List<Program> result = new ArrayList<>();

        result.add(EntityFactory.createProgram("texlive", "linux verzia", "-d -s -p", "2013.2"));
        result.add(EntityFactory.createProgram("texlive", "linux verzia", "-d -s", "2013.2"));
        result.add(EntityFactory.createProgram("texlive", "linux verzia", "-d -p", "2013.3"));
        result.add(EntityFactory.createProgram("miktex", "windows verzia", "-a -q", "2011.4"));
        result.add(EntityFactory.createProgram("miktex", "linux verzia", "-a -q", "2014.1beta.0.0.3"));

        return result;
    }

    /**
     * TODO DOCU
     *
     * @param users
     * @param revs
     * @param configs
     * @return
     */
    public static List<ApplicationRun> provideApplicationRuns(List<User> users, List<Revision> revs, List<Configuration> configs)
    {
        List<ApplicationRun> result = new ArrayList<>();

        result.add(EntityFactory.createApplicationRun("poznamka 1", new DateTime(2013, 02, 02, 12, 13), new DateTime(2013, 02, 02, 12, 14), users.get(0), revs.get(0), configs.get(0)));
        result.add(EntityFactory.createApplicationRun("poznamka 2", new DateTime(2013, 02, 02, 13, 13), new DateTime(2013, 02, 02, 14, 14), users.get(0), revs.get(0), configs.get(0)));
        result.add(EntityFactory.createApplicationRun("poznamka 3", new DateTime(2013, 02, 02, 14, 13), new DateTime(2013, 02, 02, 14, 14), users.get(1), revs.get(1), configs.get(0)));
        result.add(EntityFactory.createApplicationRun("poznamka 4", new DateTime(2013, 02, 02, 15, 13), new DateTime(2013, 02, 02, 15, 14), users.get(1), revs.get(2), configs.get(1)));
        result.add(EntityFactory.createApplicationRun("poznamka 5", new DateTime(2013, 02, 02, 16, 13), new DateTime(2013, 02, 02, 16, 14), users.get(0), revs.get(1), configs.get(2)));

        return result;
    }

    /**
     * Method generates random date.
     *
     * @return random date
     */
    public static DateTime getRandomDate()
    {
        Random r = new Random(1355226236);

        long seed = r.nextLong();
        
        if(seed<0)
        {
            seed *= -1;
        }

        return new DateTime(seed);
    }

    public static String getFirstXML()
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

    public static String getSecondXML()
    {
        return "<math mathvariant='italic'>\n"
                + "    <mrow>\n"
                + "        <mmultiscripts>\n"
                + "            <mtext>R</mtext>\n"
                + "            <mtext>i</mtext>\n"
                + "            <none></none>\n"
                + "            <none></none>\n"
                + "            <mtext>j</mtext>\n"
                + "            <mtext>kl</mtext>\n"
                + "            <none></none>\n"
                + "        </mmultiscripts>\n"
                + "        <mo>=</mo>\n"
                + "        <msup>\n"
                + "            <mtext>g</mtext>\n"
                + "            <mtext>jm</mtext>\n"
                + "        </msup>\n"
                + "        <msub>\n"
                + "            <mtext>R</mtext>\n"
                + "            <mtext>imkl</mtext>\n"
                + "        </msub>\n"
                + "        <mo>+</mo>\n"
                + "        <msqrt>\n"
                + "            <mn>1</mn>\n"
                + "            <mo>-</mo>\n"
                + "            <msup>\n"
                + "                <mtext>g</mtext>\n"
                + "                <mtext>jm</mtext>\n"
                + "            </msup>\n"
                + "            <msub>\n"
                + "                <mtext>R</mtext>\n"
                + "                <mtext>mikl</mtext>\n"
                + "            </msub>\n"
                + "        </msqrt>\n"
                + "    </mrow>\n"
                + "</math>";
    }

    public static String getConfig(boolean omit, boolean allow, boolean indent)
    {
        StringBuilder sb = new StringBuilder("<toor>");

        sb.append("\t<omit>").append(omit).append("</omit>");
        sb.append("\t<allow>").append(allow).append("</allow>");
        sb.append("\t<indent>").append(indent).append("</indent>");

        sb.append("</toor>");

        return sb.toString();
    }
}
