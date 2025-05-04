package com.cheroliv.millBaker

case class GitPushConfiguration(
  val repo: RepositoryConfiguration,
  val branch: String,
  val message: String
)