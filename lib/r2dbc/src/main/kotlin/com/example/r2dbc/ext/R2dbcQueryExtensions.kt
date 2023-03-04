package com.example.r2dbc.ext

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.ReactiveSelectOperation.SelectWithQuery
import org.springframework.data.r2dbc.core.ReactiveSelectOperation.TerminatingSelect
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import kotlin.reflect.KProperty

fun where(key: KProperty<*>): Criteria.CriteriaStep = Criteria.where(key.name)

infix fun Criteria.and(key: KProperty<*>): Criteria.CriteriaStep = and(key.name)

infix fun Criteria.sort(sort: Sort): Query = Query.query(this).sort(sort)

infix fun Criteria.pageable(pageable: Pageable): Query = Query.query(this).with(pageable)

fun <T> SelectWithQuery<T>.matching(criteria: Criteria): TerminatingSelect<T> {
    return matching(Query.query(criteria))
}
