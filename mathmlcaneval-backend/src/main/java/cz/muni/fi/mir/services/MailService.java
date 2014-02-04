/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.services;

import cz.muni.fi.mir.db.domain.ApplicationRun;

/**
 *
 * @author Empt
 */
public interface MailService
{

    void sendMail(ApplicationRun applicationRun);
    
}
