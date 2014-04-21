/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tools;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationFlag;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.CanonicOutput;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Formula;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.joda.time.DateTime;

/**
 * Class used for creating objects.
 *
 * @author Empt
 */
public class EntityFactory
{

    /**
     * Method creates empty user
     *
     * @return empty user
     */
    public static User createUser()
    {
        return new User();
    }

    /**
     * Method creates user with given id.
     *
     * @param id of user
     * @return user with given id
     */
    public static User createUser(Long id)
    {
        if (id == null)
        {
            return createUser();
        } 
        else
        {
            User u = createUser();
            u.setId(id);

            return u;
        }
    }

    /**
     * Method creates user with given input
     *
     * @param username username of user
     * @param password user's <b>hashed</b> password
     * @param realName user's real name
     * @param email users email
     * @param roles roles of user to which (s)he belongs
     * @return user with set fields as were given on input
     */
    public static User createUser(String username, String password, String realName, String email, List<UserRole> roles)
    {
        User u = createUser();
        u.setPassword(password);
        u.setRealName(realName);
        u.setUsername(username);
        u.setUserRoles(roles);
        u.setEmail(email);

        return u;
    }

    /**
     * Method creates user with given input
     *
     * @param username username of user
     * @param password hashed password of user
     * @param realName real name of user
     * @param email users email
     * @param userRole single role
     * @return user with fields set as input
     */
    public static User createUser(String username, String password, String realName, String email, UserRole userRole)
    {
        User u = createUser();
        u.setPassword(password);
        u.setRealName(realName);
        u.setUsername(username);
        List<UserRole> rolez = new ArrayList<>();
        rolez.add(userRole);
        u.setUserRoles(rolez);
        u.setEmail(email);
        return u;
    }

    /**
     * Method creates empty UserRole
     *
     * @return new UserRole
     */
    public static UserRole createUserRole()
    {
        return new UserRole();
    }

    /**
     * Method creates UserRole with given id
     *
     * @param id to be set
     * @return user role with give id
     */
    public static UserRole createUserRole(Long id)
    {
        if (id == null)
        {
            return createUserRole();
        } 
        else
        {
            UserRole ur = createUserRole();
            ur.setId(id);

            return ur;
        }
    }

    /**
     * Method creates UserRole with given name. By default, or at least it used
     * to be, all userRoles had to start with prefix <b>ROLE_</b> and then rest
     * of role should be in uppercase. Therefore roleName is converted into
     * uppercase just in case it was not. Method however does not add required
     * prefix. Use {@link #createUserRoleWithPrefix(java.lang.String) } instead.
     *
     * @param roleName name of the role
     * @return UserRole with roleName as specified on input
     */
    public static UserRole createUserRole(String roleName)
    {
        UserRole ur = createUserRole();
        ur.setRoleName(roleName.toUpperCase());

        return ur;
    }

    /**
     * Method creates UserRole with given name. By default, or at least it used
     * to be, all userRoles had to start with prefix <b>ROLE_</b> and then rest
     * of role should be in uppercase. Therefore roleName is converted into
     * uppercase just in case it was not. Method also adds <b>ROLE_</b> prefix
     * as opposing to {@link #createUserRole(java.lang.String) }. If roleName
     * already starts with <b>ROLE_</b> it is not added again.
     *
     * @param roleName name of role
     * @return UserRole with given rolename and with prefix in case it was not
     * set.
     */
    public static UserRole createUserRoleWithPrefix(String roleName)
    {
        UserRole ur = createUserRole();
        if (roleName.startsWith("ROLE_"))
        {
            ur.setRoleName(roleName.toUpperCase());
        } 
        else
        {
            ur.setRoleName("ROLE_" + roleName.toUpperCase());
        }

        return ur;
    }

    /**
     * Method used for creating new SourceDocument without setting any field
     *
     * @return empty SourceDocument
     */
    public static SourceDocument createSourceDocument()
    {
        return new SourceDocument();
    }

    /**
     * Method used for creating SourceDocument with given id
     *
     * @param id of source document
     * @return source document with given id
     */
    public static SourceDocument createSourceDocument(Long id)
    {
        if (id == null)
        {
            return createSourceDocument();
        }
        else
        {
            SourceDocument sc = createSourceDocument();
            sc.setId(id);

            return sc;
        }
    }

    /**
     * Method used for creating new SourceDocument with fields set as were given
     * on input.
     *
     * @param note note for new SourceDocument
     * @param path path for new SourceDocument
     * @return SourceDocument with given parameters
     */
    public static SourceDocument createSourceDocument(String note, String path)
    {
        SourceDocument sc = createSourceDocument();
        sc.setNote(note);
        sc.setDocumentPath(path);

        return sc;
    }

    /**
     * Method used for creating new Revision without setting any fields
     *
     * @return empty Revision
     */
    public static Revision createRevision()
    {
        return new Revision();
    }

    /**
     * Method creates revision with given id.
     *
     * @param id of revision to be set
     * @return revision with given ID.
     */
    public static Revision createRevision(Long id)
    {
        if (id == null)
        {
            return createRevision();
        } 
        else
        {
            Revision r = createRevision();
            r.setId(id);

            return r;
        }
    }

    /**
     * Method used for creating new Revision with fields set as were given on
     * input
     *
     * @param hash hash of revision, meant as unique identifier
     * @param note note for this revision
     * @return Revision with given parameters
     */
    public static Revision createRevision(String hash, String note)
    {
        Revision r = createRevision();
        r.setNote(note);
        r.setRevisionHash(hash);

        return r;
    }

    /**
     * Method used for creating new Program without setting any fields
     *
     * @return empty Program
     */
    public static Program createProgram()
    {
        return new Program();
    }

    /**
     * Method used for creating Program with given id
     *
     * @param id of program
     * @return program with given id
     */
    public static Program createProgram(Long id)
    {
        if (id == null)
        {
            return createProgram();
        } 
        else
        {
            Program p = createProgram();
            p.setId(id);

            return p;
        }
    }

    /**
     * Method used for creating program with fields set as were given on input
     *
     * @param name name of the program
     * @param note note for program
     * @param parameters
     * @param version
     * @return program with fields set as were given on input
     */
    public static Program createProgram(String name, String note, String parameters, String version)
    {
        Program p = createProgram();
        p.setName(name);
        p.setNote(note);
        p.setParameters(parameters);
        p.setVersion(version);

        return p;
    }

    /**
     * Method used for creating empty configuration.
     *
     * @return empty configuration entity
     */
    public static Configuration createConfiguration()
    {
        return new Configuration();
    }

    /**
     * Method used for creating Configuration with given id
     *
     * @param id of configuration
     * @return configuration with given id
     */
    public static Configuration createConfiguration(Long id)
    {
        if (id == null)
        {
            return createConfiguration();
        } 
        else
        {
            Configuration c = createConfiguration();
            c.setId(id);

            return c;
        }
    }

    /**
     * Method used for creating Configuration out of given input
     *
     * @param config String representation of XML configuration file
     * @param name name of configuration
     * @param note note for this configuration
     * @return configuration with fields set as were given on input
     */
    public static Configuration createConfiguration(String config, String name, String note)
    {
        Configuration c = createConfiguration();
        c.setName(name);
        c.setConfig(config);
        c.setNote(note);

        return c;
    }

    /**
     * Method creates empty AnnotationFlag.
     * @return empty AnnotationFlag without any values.
     */
    public static AnnotationFlag createAnnotaionFlag()
    {
        return new AnnotationFlag();
    }

    /**
     * Method creates AnnotationFlag with given value as argument.
     * @param value to be set to new AnnotationFlag
     * @return AnnotationFlag with given value passed as method argument.
     */
    public static AnnotationFlag createAnnotaionFlag(String value)
    {
        AnnotationFlag af = createAnnotaionFlag();
        af.setFlagValue(value);

        return af;
    }

    /**
     * Method creates empty Annotation.
     * @return empty Annotation without any values.
     */
    public static Annotation createAnnotation()
    {
        return new Annotation();
    }

    public static Annotation createAnnotation(String note, User user, AnnotationFlag af)
    {
        Annotation a = createAnnotation();
        a.setNote(note);
        a.setUser(user);
        a.setAnnotationFlag(af);

        return a;
    }

    public static ApplicationRun createApplicationRun()
    {
        return new ApplicationRun();
    }

    public static ApplicationRun createApplicationRun(String note, DateTime startTime, DateTime stopTime, User user, Revision revision, Configuration configuration)
    {
        ApplicationRun ap = createApplicationRun();
        ap.setNote(note);
        ap.setStartTime(startTime);
        ap.setStopTime(stopTime);
        ap.setUser(user);
        ap.setRevision(revision);
        ap.setConfiguration(configuration);

        return ap;
    }

    public static CanonicOutput createCanonicOutput()
    {
        return new CanonicOutput();
    }

    public static CanonicOutput createCanonicOutput(String outputForm, String similarForm, List<Formula> parents, long runningTime, ApplicationRun ar, List<Annotation> annotations)
    {
        CanonicOutput co = createCanonicOutput();
        co.setOutputForm(outputForm);
        co.setSimilarForm(similarForm);
        co.setParents(parents);
        co.setRunningTime(runningTime);
        co.setApplicationRun(ar);
        co.setAnnotations(annotations);

        return co;
    }

    /**
     * Method creates CanonicOutput with given id.
     *
     * @param id of CanonicOutput
     * @return CannonicOutput with given id
     */
    public static CanonicOutput createCanonicOutput(Long id)
    {
        if (id == null)
        {
            return createCanonicOutput();
        }
        else
        {
            CanonicOutput co = createCanonicOutput();
            co.setId(id);

            return co;
        }
    }

    /**
     * Method creates empty Formula.
     * @return empty Formula without any values.
     */
    public static Formula createFormula()
    {
        return new Formula();
    }

    /**
     * Method used for creating Formula with given id
     *
     * @param id of formula
     * @return formula formula with given id
     */
    public static Formula createFormula(Long id)
    {
        if (id == null)
        {
            return createFormula();
        }
        else
        {
            Formula f = createFormula();
            f.setId(id);

            return f;
        }
    }

    public static Formula createFormula(String xml, String note, SourceDocument sourceDocument, DateTime insertTime, Program program, User user, List<CanonicOutput> outputs, List<Formula> similarFormulas)
    {
        Formula f = createFormula();
        f.setXml(xml);
        f.setNote(note);
        f.setSourceDocument(sourceDocument);
        f.setInsertTime(insertTime);
        f.setProgram(program);
        f.setUser(user);
        f.setOutputs(outputs);
        f.setSimilarFormulas(similarFormulas);
        return f;
    }
    
    public static Formula createFormula(String xml, DateTime inserTime, User user, SourceDocument sourceDocument)
    {
        Formula f = createFormula();
        f.setXml(xml);
        f.setInsertTime(inserTime);
        f.setUser(user);
        f.setSourceDocument(sourceDocument);
        
        return f;
    }
}
