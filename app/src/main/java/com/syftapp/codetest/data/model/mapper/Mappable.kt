package com.syftapp.codetest.data.model.mapper

interface Mappable<Api, Domain> {
    fun map(api: Api): Domain
}

fun <Api, Domain> List<Api>.apiToDomain(model: Mappable<Api, Domain>) : List<Domain> {
    return this.map { model.map(it) }
}