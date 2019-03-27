package com.dao.issues

/**
 * Created in 26/03/19 22:26.
 *
 * @author Diogo Oliveira.
 */
const val TAG = "Kotlin Issues"

object Extras
{
    const val ISSUE = "ISSUE"
}

object KeyParameter
{
    const val API_KEY = "api_key"
}

object API
{
    const val URL = "https://api.github.com"
    const val API_KEY = BuildConfig.GITHUB_API_KEY

    const val URL_ISSUES = ("$URL/repos/JetBrains/kotlin/issues")
}