package com.questandglory.engine.statements;

import com.questandglory.engine.EngineFacade;
import com.questandglory.parser.antlr.Location;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Statement implements InterruptableHandler<EngineFacade> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Getter
    @Setter
    protected Location location;

    public Statement(Location location) {
        this.location = location;
    }

    /**
     * A callback method issued before all statements are executed (handled).
     *
     * @param facade Game facade.
     */
    public void prepare(EngineFacade facade) {
        // Default implementation does nothing
    }

    public void handle(EngineFacade facade) throws InterruptedException {
        logger.info("Processing line #{}.", location.lineNumber);
        handleInternal(facade);
    }

    protected abstract void handleInternal(EngineFacade facade) throws InterruptedException;
}
