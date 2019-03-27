package com.dao.issues.model

/**
 * Created in 26/03/19 21:49.
 *
 * @author Diogo Oliveira.
 */
enum class State
{
    OPEN
    {
        override fun value(): String
        {
            return "open"
        }
    },

    CLOSE
    {
        override fun value(): String
        {
            return "close"
        }
    };

    abstract fun value(): String
}