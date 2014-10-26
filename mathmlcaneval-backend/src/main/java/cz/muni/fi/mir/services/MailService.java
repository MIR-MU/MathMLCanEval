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
package cz.muni.fi.mir.services;

/**
 *
 * @author emptulik@gmail.com.com
 */
public interface MailService
{
    /**
     * Sends mail <b>from</b> given email to given <b>receiver</b> a <b>message</b> with specified <b>subject</b>.
     * @param from value saying who sent the email. if set to empty string, or null then value from configuration file is used.
     * @param receiver required value saying who is the receiver of mail
     * @param subject required subject for mail
     * @param message Message of mail
     * @throws IllegalArgumentException if receiver, subject or message is null or empty.
     */
    void sendMail(String from, String receiver, String subject, String message) throws IllegalArgumentException;
    
    
    /**
     * Method sends mail to address specified by key <b>mail.from</b> in configuration file.
     * @param subject of message
     * @param message text of message
     * @throws IllegalArgumentException if message is empty.
     */
    void sendMail(String subject, String message) throws IllegalArgumentException;
}
