package com.dao.issues.network

import androidx.annotation.StringRes
import com.dao.issues.R
import com.dao.issues.util.json.GsonHelper
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created in 26/03/19 22:21.
 *
 * @author Diogo Oliveira.
 */
class ResultError()
{
    private var code: Int = 0
    private var status: Status
    var messageRes: Int

    init
    {
        this.code = 0
        this.status = Status.INTERNAL_ERROR_CLIENT
        this.messageRes = R.string.app_internal_error_client
    }

    constructor(status: Status, @StringRes messageRes: Int) : this()
    {
        this.status = status
        this.messageRes = messageRes
    }

    companion object
    {
        fun <T> parse(response: Response<T>): ResultError
        {
            val body: ResponseBody = response.errorBody()!!
            return GsonHelper.build().fromJson(body.string(), ResultError::class.java)
        }
    }
}