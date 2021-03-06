name := "MilkShake"

version := "1.0"

lazy val `milkshake` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc , cache , ws   ,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test" )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  