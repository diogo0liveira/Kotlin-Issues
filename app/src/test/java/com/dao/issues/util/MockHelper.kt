package com.dao.issues.util

import org.mockito.Mockito

fun <T> eq(obj: T): T = Mockito.eq<T>(obj)

fun <T> any(): T = Mockito.any<T>()