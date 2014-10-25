/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tools;

import cz.muni.fi.mir.db.domain.Annotation;
import cz.muni.fi.mir.db.domain.User;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class AnnotationAction
{
    private Long id;
    private Long entityID;
    private String username;
    private String annotationContent;
    private String clazz;

    public AnnotationAction()
    {
    }
    
    public AnnotationAction(Annotation annotation)
    {
        this.username = annotation.getUser().getUsername();
        this.annotationContent = annotation.getAnnotationContent();
        this.id = annotation.getId();
    }

    public AnnotationAction(User user, String content, Long id)
    {
        this.username = user.getUsername();
        this.annotationContent = content;
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getAnnotationContent()
    {
        return annotationContent;
    }

    public void setAnnotationContent(String annotationContent)
    {
        this.annotationContent = annotationContent;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getClazz()
    {
        return clazz;
    }

    public void setClazz(String clazz)
    {
        this.clazz = clazz;
    }

    public Long getEntityID()
    {
        return entityID;
    }

    public void setEntityID(Long entityID)
    {
        this.entityID = entityID;
    }

    @Override
    public String toString()
    {
        return "AnnotationAction{" + "id=" + id + ", entityID=" + entityID + ", username=" + username + ", annotationValue=" + annotationContent + ", clazz=" + clazz + '}';
    }
}
