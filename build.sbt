enablePlugins(ScalaJSPlugin)

name := "ReincarnationHoukaiSekai"

version := "1.0"

scalaVersion := "2.11.11"

// This is an application with a main method
scalaJSUseMainModuleInitializer := true
scalaJSStage in Global := FastOptStage

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"

resolvers += "amateras-repo" at "http://amateras.sourceforge.jp/mvn/"
libraryDependencies += "com.scalawarrior" %%% "scalajs-createjs" % "0.0.2"
jsDependencies += "org.webjars.bower" % "EaselJS" % "0.8.2" / "lib/easeljs-0.8.2.combined.js"
jsDependencies += "org.webjars.bower" % "github-com-CreateJS-PreloadJS" % "0.6.2" / "lib/preloadjs-0.6.2.min.js"
