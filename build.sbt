name := "ShoppingCart"

version := "1.0"

lazy val `shoppingcart` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws, specs2 % Test,
  "net.debasishg" %% "redisclient" % "3.0",
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "mysql" % "mysql-connector-java" % "5.1.21")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  