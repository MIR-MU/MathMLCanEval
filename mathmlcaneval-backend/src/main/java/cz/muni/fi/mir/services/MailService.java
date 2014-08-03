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
    
    
    void example();
}
