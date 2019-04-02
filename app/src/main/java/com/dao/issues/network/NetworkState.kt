package com.dao.issues.network

/**
 * Created in 02/04/19 12:06.
 *
 * @author Diogo Oliveira.
 */
enum class State
{
    RUNNING, SUCCESS, FAILED
}

data class NetworkState constructor(val status: State, val message: String? = null)
{
    companion object
    {
        val LOADED = NetworkState(State.SUCCESS)
        val LOADING = NetworkState(State.RUNNING)
        fun error(message: String?) = NetworkState(State.FAILED, message)
    }
}