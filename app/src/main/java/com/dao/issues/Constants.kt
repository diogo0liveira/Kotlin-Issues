package com.dao.issues

/**
 * Created in 26/03/19 22:26.
 *
 * @author Diogo Oliveira.
 */
const val TAG = "Kotlin Issues"
const val DEVELOPER_GITHUB= ("https://github.com/diogo0liveira")

object Extras
{
    const val ISSUE = "ISSUE"
}

object KeyHeader
{
    const val ACCEPT = "Accept"
}

object KeyParameter
{
    const val API_KEY = "api_key"
    const val STATE = "state"
    const val PAGE = "page"
}

object GithubApi
{
    const val VERSION = "application/vnd.github.v3+json"
    const val KEY = BuildConfig.GITHUB_API_KEY
    var URL = "https://api.github.com"

    const val ISSUES = ("/repos/JetBrains/kotlin/issues")
}