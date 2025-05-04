package com.cheroliv.millBaker

case class RepositoryConfiguration(
    val name: String,
    val repository: String,
    val credentials: RepositoryCredentials,
)

object RepositoryConfiguration {
  val CName = "CNAME"
  val Origin = "origin"
  val Url = "url"
  val Remote = "remote"
}

//group::artifact:version