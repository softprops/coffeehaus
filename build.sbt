organization := "me.lessis"

name := "cc"

version := "0.1.0-SNAPSHOT"

description := "A local brewery for CoffeeScripts"

scalacOptions += Opts.compile.deprecation

libraryDependencies += "org.mozilla" % "rhino" % "1.7R3"

licenses <<= (version)(v => Seq(
  ("MIT", url("https://github.com/softprops/cc/blob/%s/LICENSE" format v))
))

publishTo := Some(Opts.resolver.sonatypeStaging)

publishArtifact in Test := false

publishMavenStyle := true

pomExtra := (
  <scm>
    <url>git@github.com:softprops/coffeescripted-sbt.git</url>
    <connection>scm:git:git@github.com:softprops/coffeescripted-sbt.git</connection>
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
