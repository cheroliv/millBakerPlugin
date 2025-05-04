package com.cheroliv.millBaker

case class GitPushConfiguration(
  val from: String,
  val to: String,
  val repo: RepositoryConfiguration,
  val branch: String,
  val message: String,
)