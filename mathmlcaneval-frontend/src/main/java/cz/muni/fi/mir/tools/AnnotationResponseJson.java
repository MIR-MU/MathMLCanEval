/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.tools;

import cz.muni.fi.mir.db.domain.User;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class AnnotationResponseJson
{
    private String username;
    private String content;
    private Long id;

    public AnnotationResponseJson()
    {
    }

    public AnnotationResponseJson(User user, String content, Long id)
    {
        this.username = user.getUsername();
        this.content = content;
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

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
    
    
}
