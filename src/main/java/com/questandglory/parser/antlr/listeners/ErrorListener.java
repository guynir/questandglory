package com.questandglory.parser.antlr.listeners;

import com.questandglory.parser.CompilationException;
import com.questandglory.parser.antlr.Location;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.BitSet;

/**
 * Listener for ANTLR parsing errors. Generate an immediate exception upon error.
 *
 * @author Guy Raz Nir
 * @since 2025/08/18
 */
public class ErrorListener implements ANTLRErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new CompilationException(msg, Location.of(line, charPositionInLine, ""));
    }

    @Override
    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambiguousAlts, ATNConfigSet configs) {
    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
        // That's not an error.
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
    }
}
