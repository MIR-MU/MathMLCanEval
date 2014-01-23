/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tests;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.AnnotationFlag;
import cz.muni.fi.mir.db.domain.ApplicationRun;
import cz.muni.fi.mir.db.domain.Configuration;
import cz.muni.fi.mir.db.domain.Program;
import cz.muni.fi.mir.db.domain.Revision;
import cz.muni.fi.mir.db.domain.SourceDocument;
import cz.muni.fi.mir.db.domain.User;
import cz.muni.fi.mir.db.domain.UserRole;
import java.util.Comparator;

/**
 *
 * @author Empt
 */
public class TestTools
{

    public static final String ERROR_LIST_SIZE = "Given list does not have proper size";
    public static final String ERROR_WRONG_ID = "Given object does not have proper ID";

    public static Comparator<UserRole> userRoleComparator = new Comparator<UserRole>()
    {
        @Override
        public int compare(UserRole o1, UserRole o2)
        {
            return o1.getId().compareTo(o2.getId());
        }
    };

    public static Comparator<User> userComparator = new Comparator<User>()
    {
        @Override
        public int compare(User o1, User o2)
        {
            return o1.getId().compareTo(o2.getId());
        }
    };

    public static Comparator<SourceDocument> sourceDocumentComparator = new Comparator<SourceDocument>()
    {
        @Override
        public int compare(SourceDocument sd1, SourceDocument sd2)
        {
            return sd1.getId().compareTo(sd2.getId());
        }
    };

    /**
     *
     */
    public static Comparator<Revision> revisionComparator = new Comparator<Revision>()
    {
        @Override
        public int compare(Revision r1, Revision r2)
        {
            return r1.getId().compareTo(r2.getId());
        }
    };

    public static Comparator<Program> programComparator = new Comparator<Program>()
    {
        @Override
        public int compare(Program p1, Program p2)
        {
            return p1.getId().compareTo(p2.getId());
        }
    };

    public static Comparator<Configuration> confiurationComparator = new Comparator<Configuration>()
    {
        @Override
        public int compare(Configuration c1, Configuration c2)
        {
            return c1.getId().compareTo(c2.getId());
        }
    };

    public static Comparator<AnnotationFlag> annotationFlagComparator = new Comparator<AnnotationFlag>()
    {
        @Override
        public int compare(AnnotationFlag af1, AnnotationFlag af2)
        {
            return af1.getId().compareTo(af2.getId());
        }
    };

    public static Comparator<Annotation> annotationComparator = new Comparator<Annotation>()
    {
        @Override
        public int compare(Annotation a1, Annotation a2)
        {
            return a1.getId().compareTo(a2.getId());
        }
    };
    
    public static Comparator<ApplicationRun> applicationRunComparator = new Comparator<ApplicationRun>()
    {
        @Override
        public int compare(ApplicationRun ar1,ApplicationRun ar2)
        {
            return ar1.getId().compareTo(ar2.getId());
        }
    };

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
