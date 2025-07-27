package com.questandglory.engine.channels;

import com.questandglory.engine.messages.ClientMessage;
import lombok.Getter;

import java.io.Closeable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>A mailbox is a peer entity that maintains messages (events) sent from client side to a process in the server.
 * </p>
 * Messages/events are passed through a channel. The channel routes each message to a designated mailbox.
 *
 * @author Guy Raz Nir
 * @since 2025/07/21
 */
public class Mailbox implements Closeable {

    @Getter
    protected final String mailboxId;

    protected MailboxOwner owner;

    protected BlockingQueue<ClientMessage> messages = new LinkedBlockingQueue<>();

    public Mailbox(String mailboxId, MailboxOwner owner) {
        this.mailboxId = mailboxId;
        this.owner = owner;
    }

    public void postMessage(ClientMessage message) {
        messages.add(message);
    }

    public ClientMessage pullMessage() throws InterruptedException {
        return messages.take();
    }

    public <M extends ClientMessage> M pullMessage(Class<M> clazz) throws InterruptedException {
        ClientMessage message = messages.take();
        if (!clazz.isInstance(message)) {
            throw new IllegalStateException("Expected to receive message of type "
                    + clazz.getName()
                    + ", but got " + message.getClass().getName()
                    + " instead.");
        }
        //noinspection unchecked
        return (M) message;
    }

    @Override
    public void close() {
        owner.closeMailbox(this);
        owner = null;
        messages = null;
    }
}
