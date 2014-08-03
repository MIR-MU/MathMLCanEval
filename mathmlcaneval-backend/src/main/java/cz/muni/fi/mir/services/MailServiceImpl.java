/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author emptulik@gmail.com
 */
public class MailServiceImpl implements MailService
{
    private static final Logger logger = Logger.getLogger(MailServiceImpl.class);
    
    private MailSender mailSender;
    private String sender;
    private String subjectPrefix;
    private boolean enabled;

    public void setMailSender(MailSender mailSender)
    {
        this.mailSender = mailSender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }

    public void setSubjectPrefix(String subjectPrefix)
    {
        this.subjectPrefix = subjectPrefix;
    }

    private boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    @Override
    public void sendMail(String from, String receiver, String subject, String message) throws IllegalArgumentException
    {
        if (isEnabled())
        {
            if (StringUtils.isEmpty(receiver))
            {
                throw new IllegalArgumentException("Receiver of email is not set. to value is [" + receiver + "]");
            }
            if (StringUtils.isEmpty(subject))
            {
                throw new IllegalArgumentException("Subject of email is not set. subject value is [" + subject + "]");
            }
            if (StringUtils.isEmpty(message))
            {
                throw new IllegalArgumentException("Message of email is not set. message value is [" + message + "]");
            }

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            if (StringUtils.isEmpty(from))
            {
                mailMessage.setFrom(sender);
            }
            else
            {
                mailMessage.setFrom(from);
            }

            if (StringUtils.isEmpty(subjectPrefix))
            {
                mailMessage.setSubject(subjectPrefix + subject);
            }
            else
            {
                mailMessage.setSubject(subject);
            }
            mailMessage.setTo(receiver);
            mailMessage.setText(message);

            mailSender.send(mailMessage);
        }
    }

    @Override
    public void example()
    {
        logger.info("MailService is enabled? ["+isEnabled()+"]");
        if(isEnabled())
        {
            SimpleMailMessage smm = new SimpleMailMessage();
            smm.setFrom("emptulik@gmail.com");
            smm.setTo("emptulik@gmail.com");
            smm.setSubject("yolo-test");
            smm.setText("yolo-teszstastast");
            
            mailSender.send(smm);
        }
    }
}
