package com.ozansan.sanews.data.models

data class News(
    // Basic elements
    var id: Int?,
    var title: String? = null,
    var url: String? = null,
    var deleted: Boolean? = false,
    var type: String? = null,
    var by: String? = null,
    var time: Int? = null,
    var text: String? = null,
    var dead: Boolean? = false,
    var parent: Int? = null,
    var poll: Int? = null,
    var kids: MutableList<Int>? = null,
    var score: Int? = null,
    var parts: MutableList<Int>? = null,
    var descendants: Int? = null,
)