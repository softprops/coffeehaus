# coffeehaus

A coffeescript shop for JVM locals.

## /!\ Extraction in progress

This library is the extraction of the coffeescript compiler used
in [coffeescripted-sbt](https://github.com/softprops/coffeescripted-sbt) for use as a standalone library

## install

(todo)

## usage

This library provides scala interfaces for compiling [vanilla][vanilla] and [iced][iced] CoffeeScript.
It used the versions `1.6.2` and `1.6.2a` respectively. 

To compile vanilla coffeescript

```scala
coffeehaus.Compile.vanilla("alert 'vanilla'")
```

or simply

```scala
coffeehaus.Compile("alert 'vanilla'")
```

To compile iced coffeescript

```scala
coffeehaus.Compile.iced("alert 'iced'")
```

These will return a `Either[coffeehaus.CompilerError, String]` with the compiled source.

Don't have time to wait while your coffee's being brewed? Try moving to the side of the counter.

```scala
import coffeehaus.Compile
import scala.concurrent.Future
import ExecutionContext.Implicits.global
Future(Compile("alert 'vanilla'")).map { coffee =>
  Thread.sleep(1000)
  coffee.fold(println, println)
}
println("checkin' my tweets")
```

Doug Tangren (softprops) 2013

[vanilla]: http://coffeescript.org/
[iced]: http://maxtaco.github.io/coffee-script/
