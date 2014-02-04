/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.mir.services;

import cz.muni.fi.mir.db.domain.ApplicationRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * zaslanie mailu ak pojde o vypocetne narocnu ulohu atd.   
 * @author Empt
 */
@Component("mailService")
public class MailServiceImpl implements MailService
{
    @Autowired private MailSender mailsender;
    @Autowired SimpleMailMessage templateMessage;
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MailServiceImpl.class);
    
    @Override
    public void sendMail(ApplicationRun applicationRun)
    {
        SimpleMailMessage msg = new SimpleMailMessage(templateMessage);
        msg.setTo(applicationRun.getUser().getEmail());
        
        msg.setText("apprun has finished its job.");
        
        
        try
        {
            mailsender.send(templateMessage);
        }
        catch(MailException me)
        {
            logger.error(me);
        }
    }
}
