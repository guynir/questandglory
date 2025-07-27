package com.questandglory.engine;

import com.questandglory.utils.GlobalIdGenerator;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class GameStep implements Serializable {

    @Getter
    private final int lineNumber;

    @Getter
    private final GameStepEnum type;

    @Getter
    private String componentKey = null;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected GameStep(int lineNumber, GameStepEnum type) {
        this.lineNumber = lineNumber;
        this.type = type;
    }

    protected void defineComponentKey() {
        this.componentKey = "key-" + GlobalIdGenerator.generateId();
    }

    /**
     * Perform validation of this step.
     *
     * @param steps List of all steps in the game. Sometimes required for dependency validation.
     */
    public void validate(List<GameStep> steps) {
    }

    public void prepare(EngineExecutionContext context) {
    }

    public abstract GameStep execute(EngineExecutionContext context) throws InterruptedException;

    /**
     * Helper function to return a stream of game steps of specific type, which are not this instance.
     *
     * @param steps     List of steps.
     * @param type      Expected type.
     * @param predicate A predicate to further filter the
     * @param <T>       Generic type of the target {@link GameStep} we want to find.
     * @return A stream of {@link GameStep}s instance(s) that match criteria.
     */
    @SuppressWarnings("unchecked")
    protected <T extends GameStep> Stream<T> find(List<GameStep> steps, Class<T> type, Predicate<T> predicate) {
        Stream<T> stream = steps
                .stream()
                .filter(step -> step != this && type.isInstance(step))
                .map(step -> (T) step);

        if (predicate != null) {
            stream = stream.filter(predicate);
        }

        return stream;
    }

    protected <T extends GameStep> Optional<T> findFirst(List<GameStep> steps, Class<T> type, Predicate<T> predicate) {
        return find(steps, type, predicate).findFirst();
    }

    protected <T extends GameStep> boolean exist(List<GameStep> steps, Class<T> type, Predicate<T> predicate) {
        return findFirst(steps, type, predicate).isPresent();
    }

    protected GameStep findNext(List<GameStep> steps) {
        Iterator<GameStep> itr = steps.iterator();
        while (itr.hasNext()) {
            if (itr.next() == this) {
                return itr.hasNext() ? itr.next() : null;
            }
        }
        throw new NoSuchElementException();
    }


}
