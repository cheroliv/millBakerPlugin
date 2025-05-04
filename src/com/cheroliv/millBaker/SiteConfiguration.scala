package com.cheroliv.millBaker

case class SiteConfiguration(
    val pushConfiguration: GitPushConfiguration,
    val cname: Option[String] = None,
    val pushSource: Option[GitPushConfiguration] = None,
    val pushTemplate: Option[GitPushConfiguration] = None
)