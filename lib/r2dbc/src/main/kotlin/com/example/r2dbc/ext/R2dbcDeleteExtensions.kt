package com.example.r2dbc.ext

import org.springframework.data.r2dbc.core.ReactiveDeleteOperation
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query

fun ReactiveDeleteOperation.DeleteWithQuery.matching(criteria: Criteria): ReactiveDeleteOperation.TerminatingDelete {
    return matching(Query.query(criteria))
}
