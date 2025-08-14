package com.questandglory.language.compiler;

import com.questandglory.engine.GameState;
import com.questandglory.parser.CompilationException;
import com.questandglory.parser.InternalCompilationErrorException;

public interface Compiler {

    /**
     * Compiles a script into statements.
     *
     * @param script Script to parse and compile.
     * @return Compilation results.
     * @throws CompilationException              On error script error.
     * @throws InternalCompilationErrorException On any internal error related to the compiler.
     */
    CompiledScript compile(String script) throws CompilationException, InternalCompilationErrorException;

    /**
     * Compiles a script into statements.
     *
     * @param script Script to parse and compile.
     * @param state  Optional (non-{@code null}) game state, possibly with predefined variables.
     * @return Compilation results.
     * @throws CompilationException              On error script error.
     * @throws InternalCompilationErrorException On any internal error related to the compiler.
     */
    CompiledScript compile(String script, GameState state) throws CompilationException, InternalCompilationErrorException;

}
