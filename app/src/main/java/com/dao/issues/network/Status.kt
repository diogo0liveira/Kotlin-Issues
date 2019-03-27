package com.dao.issues.network

/**
 * Created in 26/03/19 22:25.
 *
 * @author Diogo Oliveira.
 */
enum class Status(val value: Int)
{
    NOT_SPECIFIED(-1),
    SUCCESS(200),
    INTERNAL_ERROR_CLIENT(400),
    UNAUTHORIZED(401),
    INVALID_REFRESH_TOKEN(403),
    NOT_FOUND(404),
    INTERNAL_ERROR_SERVER(500),
    SERVICE_UNAVAILABLE(503);

    companion object
    {
        operator fun get(value: Int): Status
        {
            for(status in Status.values())
            {
                if(status.value == value)
                {
                    return status
                }
            }

            return NOT_SPECIFIED
        }
    }
}