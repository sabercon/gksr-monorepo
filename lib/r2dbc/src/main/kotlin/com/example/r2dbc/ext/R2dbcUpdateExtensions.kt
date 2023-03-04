package com.example.r2dbc.ext

import org.springframework.data.r2dbc.core.ReactiveUpdateOperation.TerminatingUpdate
import org.springframework.data.r2dbc.core.ReactiveUpdateOperation.UpdateWithQuery
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.data.relational.core.query.Update
import kotlin.reflect.KProperty

fun <T> update(key: KProperty<T>, value: T): Update = Update.update(key.name, value)

fun <T> Update.set(key: KProperty<T>, value: T): Update = set(key.name, value)

fun UpdateWithQuery.matching(criteria: Criteria): TerminatingUpdate {
    return matching(Query.query(criteria))
}
