package com.questandglory.engine;

import com.questandglory.engine.constructs.Identifier;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Map;

/**
 * Provide access to the game's state, typically all variables within the game's scope.
 *
 * @author Guy Raz Nir
 * @since 2025/07/31
 */
public interface GameState {

    /**
     * Get a variable from the game state by its name.
     *
     * @param name Name of variable to retrieve.
     * @param <T>  Generic type of the variable's value.
     * @return Variable's value.
     * @throws IllegalArgumentException If the variable name is {@code null} or empty.
     * @throws UnknownVariableException If the variable is not found in the game state.
     * @throws ClassCastException       If the variable's value cannot be cast to the specified type.
     */
    <T> T getVariable(String name) throws IllegalArgumentException, UnknownVariableException, ClassCastException;

    /**
     * Set a variable in the game state.
     *
     * @param name  Name of the variable to set.
     * @param value Value to set for the variable.
     * @throws IllegalArgumentException If the variable name is {@code null} or empty.
     */
    void setVariable(String name, Object value);

    /**
     * Sets variable in the game state.
     *
     * @param identifier Identifier of variable.
     * @param value      Value to set.
     */
    void setVariable(Identifier identifier, Object value);

    /**
     * Remove a variable from the game state.
     *
     * @param name Name of the variable to remove.
     */
    void removeVariable(String name);

    /**
     * @return All variables in the game state as a map.
     */
    Map<String, Object> getVariables();

    /**
     * Register a new variable in the game state.
     *
     * @param name Name of variable.
     * @param type Type of variable (currently supported types are String, Integer, Boolean).
     * @param <T>  Generic type of the variable.
     * @throws IllegalArgumentException If the variable name is {@code null} or empty, or if the type is not supported.
     * @throws IllegalStateException    If the variable is already defined in the game state.
     */
    <T> void registerVariable(String name, Class<T> type) throws IllegalArgumentException, IllegalStateException;

    /**
     * Register a new variable in the game state.
     *
     * @param name  Name of variable.
     * @param type  Type of variable (currently supported types are String, Integer, Boolean).
     * @param value Initial value of the variable.
     * @param <T>   Generic type of the variable.
     * @throws IllegalArgumentException If the variable name is {@code null} or empty, or if the type is not supported.
     * @throws IllegalStateException    If the variable is already defined in the game state.
     */
    <T> void registerVariable(String name, Class<T> type, T value) throws IllegalArgumentException;

    /**
     * Lookup a variable's type.
     *
     * @param name Name of variable.
     * @return Class of the variable's type.
     * @throws IllegalArgumentException If the variable name is {@code null} or empty.
     * @throws UnknownVariableException If the variable is not found in the game state.
     */
    Class<?> getVariableType(String name) throws IllegalArgumentException, UnknownVariableException;

    /**
     * Test if a variable is defined or not.
     *
     * @param name Name of variable to check.
     * @return {@code true} if the variable is defined, {@code false} otherwise.
     * @throws IllegalArgumentException If the variable name is {@code null} or empty.
     */
    boolean isDefined(String name) throws IllegalArgumentException;

    /**
     * Test if a variable is of a specific type and defined in the game state.
     *
     * @param name Name of variable to check.
     * @return {@code true} if the variable is defined as the given type, {@code false} otherwise.
     * @throws IllegalArgumentException If either variable name is {@code null} or empty, or <i>type</i> is {@code null}.
     */
    boolean is(String name, Class<?> type) throws IllegalArgumentException, UnknownVariableException;

    boolean exists(ParserRuleContext variableName);
}
