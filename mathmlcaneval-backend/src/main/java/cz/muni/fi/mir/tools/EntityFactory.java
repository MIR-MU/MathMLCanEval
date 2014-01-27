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
import org.joda.time.DateTime;

/**
 * Class used for creating objects.
 * @author Empt
 */
public class EntityFactory
{
    
    /**
     * Method creates empty user
     * @return empty user
     */
    public static User createUser()
    {
        return new User();
    }
    
    /**
     * Method creates user with given input
     * @param username username of user
     * @param password user's <b>hashed</b> password
     * @param realName user's real name
     * @param roles roles of user to which (s)he belongs
     * @return user with set fields as were given on input
     */
    public static User createUser(String username, String password, String realName, List<UserRole> roles)
    {
        User u =createUser();
        u.setPassword(password);
        u.setRealName(realName);
        u.setUsername(username);
        u.setUserRoles(roles);
        
        return u;
    }
    
    /**
     * Method creates user with given input
     * @param username username of user
     * @param password hashed password of user
     * @param realName real name of user
     * @param userRole single role
     * @return user with fields set as input
     */
    public static User createUser(String username, String password, String realName, UserRole userRole)
    {
        User u =createUser();
        u.setPassword(password);
        u.setRealName(realName);
        u.setUsername(username);
        List<UserRole> rolez = new ArrayList<>();
        rolez.add(userRole);
        u.setUserRoles(rolez);
        
        return u;
    }
    
    /**
     * Method creates empty UserRole
     * @return new UserRole
     */
    public static UserRole createUserRole()
    {
        return new UserRole();
    }
    
    /**
     * Method creates UserRole with given name. By default, or at least it used to be,
     * all userRoles had to start with prefix <b>ROLE_</b> and then rest of role should be in 
     * uppercase. Therefore roleName is converted into uppercase just in case it was not. Method
     * however does not add required prefix. Use {@link #createUserRoleWithPrefix(java.lang.String) } instead.
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
     * Method creates UserRole with given name. By default, or at least it used to be,
     * all userRoles had to start with prefix <b>ROLE_</b> and then rest of role should be in 
     * uppercase. Therefore roleName is converted into uppercase just in case it was not.
     * Method also adds <b>ROLE_</b> prefix as opposing to {@link #createUserRole(java.lang.String) }.
     * If roleName already starts with <b>ROLE_</b> it is not added again.
     * @param roleName name of role
     * @return UserRole with given rolename and with prefix in case it was not set.
     */
    public static UserRole createUserRoleWithPrefix(String roleName)
    {
        UserRole ur = createUserRole();
        if(roleName.startsWith("ROLE_"))
        {
            ur.setRoleName(roleName.toUpperCase());
        }
        else
        {
            ur.setRoleName("ROLE_"+roleName.toUpperCase());
        }        
        
        return ur;
    }
    
    
    /**
     * Method used for creating new SourceDocument without setting any field
     * @return empty SourceDocument
     */
    public static SourceDocument createSourceDocument()
    {
        return new SourceDocument();
    }
    
    
    /**
     * Method used for creating new SourceDocument with fields set as were given on input.
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
     * @return empty Revision
     */
    public static Revision createRevision()
    {
        return new Revision();
    }
    
    /**
     * Method used for creating new Revision with fields set as were given on input
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
     * @return empty Program
     */
    public static Program createProgram()
    {
        return new Program();
    }
    
    /**
     * Method used for creating program with fields set as were given on input
     * @param name name of the program
     * @param note note for program
     * @param parameters 
     * @param version
     * @return program with fields set as were given on input
     */
    public static Program createProgram(String name, String note,String parameters,String version)
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
     * @return empty configuration entity
     */
    public static Configuration createConfiguration()
    {
        return new Configuration();
    }
    
    
    /**
     * Method used for creating Configuration out of given input
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
    
    
    public static AnnotationFlag createAnnotaionFlag()
    {
        return new AnnotationFlag();
    }
    
    public static AnnotationFlag createAnnotaionFlag(String value)
    {
        AnnotationFlag af = createAnnotaionFlag();
        af.setFlagValue(value);
        
        return af;
    }
    
    
    public static Annotation createAnnotation()
    {
        return new Annotation();
    }
    
    public static Annotation createAnnotation(String note,User user,AnnotationFlag af)
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
    
    public static ApplicationRun createApplicationRun(String note,DateTime startTime,DateTime stopTime, User user, Revision revision, Configuration configuration)
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
    
    public static CanonicOutput createCanonicOutput(String outputForm,String similarForm,List<Formula> parents,long runningTime, ApplicationRun ar,List<Annotation> annotations)
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
}