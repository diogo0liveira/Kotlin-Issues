package com.dao.issues.features.issues.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dao.issues.model.Issue
import javax.inject.Inject

/**
 * Created in 02/04/19 11:30.
 *
 * @author Diogo Oliveira.
 */
class IssuesDataSourceFactory @Inject constructor(
        private val dataSource: IssuesPageKeyedDataSource): DataSource.Factory<Int, Issue>()
{
    val source = MutableLiveData<IssuesPageKeyedDataSource>()

    override fun create(): DataSource<Int, Issue>
    {
        this.source.postValue(dataSource)
        return dataSource
    }
}