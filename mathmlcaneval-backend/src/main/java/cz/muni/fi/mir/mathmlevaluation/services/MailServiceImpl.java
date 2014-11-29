/* 
 * Copyright 2014 MIR@MU.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mir.mathmlevaluation.services;

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
    public void sendMail(String subject, String message) throws IllegalArgumentException
    {
        if (isEnabled())
        {
            if (StringUtils.isEmpty(message))
            {
                throw new IllegalArgumentException("Mail message (text) cannot be empty");
            }
            SimpleMailMessage mess = new SimpleMailMessage();
            mess.setFrom(sender);
            mess.setTo(sender);
            if (subjectPrefix != null)
            {
                mess.setSubject(subjectPrefix + subject);
            }
            else
            {
                mess.setSubject(subject);
            }

            mess.setText(message);

            mailSender.send(mess);
        }
    }
}
