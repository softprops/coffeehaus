organization := "me.lessis"

name := "coffeehaus"

version := "0.1.0-SNAPSHOT"

crossScalaVersions := Seq("2.9.3", "2.10.0", "2.10.1")

description := "A coffeescript shop for JVM locals"

scalacOptions := Seq(Opts.compile.deprecation, "-feature")

libraryDependencies += "org.mozilla" % "rhino" % "1.7R4"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

licenses := Seq(
  ("MIT", url("https://github.com/softprops/%s/blob/%s/LICENSE"
              .format(name.value, version.value))))

seq(bintraySettings:_*)

bintray.Keys.packageLabels in bintray.Keys.bintray := Seq("sbt", "coffeescript", "coffeehaus")

seq(lsSettings:_*)

(LsKeys.tags in LsKeys.lsync) := Seq("sbt", "coffeescript", "coffeehaus")
