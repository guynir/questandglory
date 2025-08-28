package com.questandglory.engine.channels;

import com.questandglory.engine.messages.ClientMessage;
import com.questandglory.utils.GlobalIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IncomingMessagesChannel implements MailboxOwner {

    private static final Logger logger = LoggerFactory.getLogger(IncomingMessagesChannel.class);
    private final Map<String, Mailbox> mailboxMap = new ConcurrentHashMap<>();

    public IncomingMessagesChannel() {
    }

    public Mailbox createMailbox() {
        Mailbox mailbox;
        Mailbox existingMailbox;
        do {
            String mailboxId = GlobalIdGenerator.generateId();
            mailbox = new Mailbox(mailboxId, this);
            existingMailbox = mailboxMap.putIfAbsent(mailboxId, mailbox);
        } while (existingMailbox != null);

        return mailbox;
    }

    public void postMessage(String mailboxId, ClientMessage message) {
        Mailbox mailbox = mailboxMap.get(mailboxId);
        if (mailbox != null) {
            logger.info("Posting message to mailbox: {}.", mailboxId);
            mailbox.postMessage(message);
        } else {
            logger.warn("Attempt to post message into non existing mailbox: {}.", mailboxId);
        }
    }

    @Override
    public void removeMailbox(String mailboxId) {
        //noinspection resource
        mailboxMap.remove(mailboxId);
    }
}
