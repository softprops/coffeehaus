organization := "me.lessis"

name := "coffeehaus"

version := "0.1.0-SNAPSHOT"

crossScalaVersions := Seq("2.9.3", "2.10.0", "2.10.1")

description := "A coffeescript shop for JVM locals"

scalacOptions <++= (scalaVersion).map { sv =>
  if (sv.startsWith("2.10")) Seq(Opts.compile.deprecation, "-feature")
  else Seq(Opts.compile.deprecation)
}

libraryDependencies += "org.mozilla" % "rhino" % "1.7R4"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

licenses <<= (version)(v => Seq(
  ("MIT", url("https://github.com/softprops/coffeehaus/blob/%s/LICENSE" format v))
))

publishTo := Some(Opts.resolver.sonatypeStaging)

publishArtifact in Test := false

publishMavenStyle := true

pomExtra := (
  <scm>
    <url>git@github.com:softprops/coffeehaus.git</url>
    <connection>scm:git:git@github.com:softprops/coffeehaus.git</connection>
  </scm>
  <developers>
    <developer>
      <id>softprops</id>
      <name>Doug Tangren</name>
      <url>https://github.com/softprops</url>
    </developer>
  </developers>
)

seq(lsSettings:_*)

(LsKeys.tags in LsKeys.lsync) := Seq("sbt", "coffeescript")
