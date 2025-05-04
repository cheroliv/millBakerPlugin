package com.cheroliv.millBaker

case class SiteConfiguration(
    val bake: BakeConfiguration,
    val pushPage: GitPushConfiguration,
    val pushSource: Option[GitPushConfiguration] = None,
    val pushTemplate: Option[GitPushConfiguration] = None,
)