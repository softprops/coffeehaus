# cawfee

A coffeescript show for JVM locals

## /!\ Extraction in progress

This library is the extraction of the coffeescript compiler used
in coffeescripted-sbt for use as a standalone library

## install

(todo)

## usage

This library provides scala interfaces for compiling [vanilla][vanilla] and [iced][iced] CoffeeScript.
It used the versions `1.6.2` and `1.6.2a` respectively. 

To compile vanilla coffeescript

```scala
cawfee.Compile.vanilla("alert 'vanilla'")
```

To compile iced coffeescript

```scala
cawfee.Compile.iced("alert 'iced'")
```

These will return a `scala.util.Try[String]` with the compiled source. Compiler errors will be encoded
as `scala.util.Failure(CompilerError(msg))`.


Don't have time to wait while your coffees being brewed? Try moving to the side of the counter.

```scala
import scala.concurrent.Future
import ExecutionContext.Implicits.global
Future(Compile.vanilla("alert 'vanilla'")).map { coffee =>
  Thread.sleep(1000)
  println(coffee)
}
println("checkin' my tweets")
```


Doug Tangren (softprops) 2013

[vanilla]: http://coffeescript.org/
[iced]: http://maxtaco.github.io/coffee-script/
