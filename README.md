# coffeehaus

A coffeescript shop for JVM locals.

## /!\ Extraction in progress

This library is the extraction of the coffeescript compiler used
in [coffeescripted-sbt](https://github.com/softprops/coffeescripted-sbt) for use as a standalone library

## install

(todo)

## usage

This library provides scala interfaces for compiling [vanilla][vanilla] and [iced][iced] CoffeeScript.
It uses the versions `1.6.3` and `1.6.3-b` respectively. 

To compile vanilla coffeescript

```scala
coffeehaus.Compile.vanilla()("alert 'vanilla'")
```

or simply

```scala
coffeehaus.Compile("alert 'vanilla'")
```

To compile iced coffeescript

```scala
coffeehaus.Compile.iced()("alert 'iced'")
```

These will return a `Either[coffeehaus.CompilerError, String]` with the compiled source.

Don't have time to wait while your coffee's being brewed? Try moving to the side of the counter.

```scala
import coffeehaus.Compile
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
Future(Compile("alert 'vanilla'")).map { coffee =>
  Thread.sleep(1000)
  coffee.fold(println, println)
}
println("checkin' my tweets")
```

### Be Your Own Barista

This library will always make a best attempt at providing interfaces to the latest coffeescript compilers.

That doesn't mean you can't experiment with your own brews ( other versions of coffeescript ) by extending `coffeehaus.Compile` providing
a resource path to the source of the script. As an example the built-in vanilla coffeescript compiler is defined as follows

```scala
Vanilla extends Compile("vanilla/coffee-script.js")
```

enjoy.

Doug Tangren (softprops) 2013

[vanilla]: http://coffeescript.org/
[iced]: http://maxtaco.github.io/coffee-script/
