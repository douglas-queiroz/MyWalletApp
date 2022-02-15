package com.douglasqueiroz.mywallet.repository.local

import com.douglasqueiroz.mywallet.AppDatabase
import com.douglasqueiroz.mywallet.DatabaseDriverFactory

internal open class BaseDao(databaseDriverFactory: DatabaseDriverFactory) {

    val database = AppDatabase(databaseDriverFactory.createDriver())

}