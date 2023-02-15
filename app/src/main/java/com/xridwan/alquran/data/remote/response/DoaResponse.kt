package com.xridwan.alquran.data.remote.response

data class Response(
    val result: Result
)

data class DataItem(
    val arabic: String,
    val translation: String,
    val latin: String,
    val title: String
)

data class Result(
    val data: ArrayList<DataItem>
)
