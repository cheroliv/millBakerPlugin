package com.cheroliv.millBaker

case class BakeConfiguration(
    val srcPath: String,
    val destDirPath: String,
    val cname: Option[String],
)