package com.arctouch.codechallenge.details.domainobject

import com.google.gson.annotations.Expose

data class RecommendedMovie  (
    val id: Int?,

    @Expose
    val poster_path: String?
)