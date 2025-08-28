package com.questandglory.language.compiler;

import com.questandglory.language.script.Script;
import com.questandglory.language.variables.VariablesDefinition;
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
     * @param script Script compile.
     * @return Compilation results.
     * @throws CompilationException              On error script error.
     * @throws InternalCompilationErrorException On any internal error related to the compiler.
     */
    CompiledScript compile(Script script) throws CompilationException, InternalCompilationErrorException;

    /**
     * Compiles a script into statements.
     *
     * @param script    Script to compile.
     * @param variables Optional (non-{@code null}) variables defintions with predefined variables.
     * @return Compilation results.
     * @throws CompilationException              On error script error.
     * @throws InternalCompilationErrorException On any internal error related to the compiler.
     */
    CompiledScript compile(Script script, VariablesDefinition variables) throws CompilationException, InternalCompilationErrorException;

}
