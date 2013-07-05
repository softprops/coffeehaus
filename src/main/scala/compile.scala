package coffeehaus

import org.mozilla.javascript.{
  Context, Function, JavaScriptException, NativeObject }
import java.io.InputStreamReader
import java.nio.charset.Charset

case class CompileError(msg: String)

sealed trait Output
case class JsSource(js: String) extends Output
case class SourceMapping(js: String, sourceMap: String) extends Output

object Compile {
  type Result = Either[CompileError, Output]
  val charset = Charset.forName("utf-8")
  val iced = Iced
  val vanilla = Vanilla
  /** use the default (vanilla) compiler */
  def apply(code: String, options: Options = Options()) =
    vanilla(code, options)
}

/**
 * A Scala / Rhino Coffeescript compiler.
 * @author daggerrz
 * @author doug
 */
abstract class Compile(src: String)
       extends ((String, Options) => Compile.Result) {

  /** compiler arguments in addition to `bare` */
  protected def args: Map[String, Any] = Map.empty[String, Any]

  override def toString = "%s(%s)" format(getClass.getSimpleName, src)

  /**
   * Compiles a string of Coffeescript code to Javascript.
   *
   * @param code the Coffeescript source code
   * @param options coffeescript compiler options
   * @return Compile.Result of either a compilation error description or
   *   the compiled Javascript code
   */
  def apply(code: String, options: Options): Compile.Result =
    withContext { ctx =>
      val coffee = scope.get("CoffeeScript", scope).asInstanceOf[NativeObject]
      val compileFunc = coffee.get("compile", scope).asInstanceOf[Function]
      val opts = ctx.evaluateString(scope, jsArgs(options), null, 1, null)
      try {
        println("args %s" format(jsArgs(options)))
        Right(compileFunc.call(ctx, scope, coffee,
                       Array(code, opts)) match {
                         case str: String => JsSource(str)
                         case no: NativeObject =>
                           val js = no.get("js", null).asInstanceOf[String]
                           val srcMapJson = no.get("v3SourceMap", null).asInstanceOf[String]
                           // to make configuring tooling easier, we may wish to simpily
                           // append the //@ sourceMappingURL comment to the js source
                           //val withMapping =
                           //  """%s
                           //    |//# sourceMappingURL=data:application/json;base64,%s
                           //    |""".stripMargin.format(js, base64(js))
                           SourceMapping(js, srcMapJson)
                         case other =>
                           throw new RuntimeException("%s was not a string or source mapping" format other)
                       })
      } catch {
        case e: JavaScriptException =>
          Left(CompileError(e.getValue.toString))
      }
    }

  /** Same as apply(code, options) but with default options */
  def apply(code: String): Compile.Result = apply(code, Options())

  /** Evaluate compiler source once in a shared scope */
  private lazy val scope = withContext { ctx =>
    val scope = ctx.initStandardObjects()
    ctx.evaluateReader(
      scope,
      new InputStreamReader(
        getClass().getResourceAsStream("/%s" format src),
        Compile.charset
      ),
      src,  //  source name
      1,    //  line number
      null) //  security domain

    scope
  }

  private def withContext[T](f: Context => T): T =
    try {
      val ctx = Context.enter()
      // Do not compile to byte code (max 64kb methods)
      ctx.setOptimizationLevel(-1)
      f(ctx)
    } finally {
      Context.exit()
    }

  private def jsArgs(opts: Options) =
    ((List.empty[String] /:
      (Map("bare" -> opts.bare, "sourceMap" -> opts.sourceMap) ++
       opts.filename.map("filename" -> _) ++
       opts.inline.map("inline" -> _) ++
       opts.generatedFile.map("generatedFile" -> _) ++
       opts.sourceRoot.map("sourceRoot" -> _) ++
       opts.sourceFiles.map("sourceFiles" -> _) ++ args)) {
         (a,e) => e match {
           case (k, v) =>
             "%s:%s".format(k, v match {
               case s: String => "'%s'" format s
               case s: Seq[_] => s.mkString("['", "','" ,"']")
               case lit => lit
             }) :: a
         }
       }).mkString("({",",","});")
}

object Vanilla extends Compile("vanilla/coffee-script.js")

object Iced extends Compile("iced/coffee-script.js") {
  override def args = Map("runtime" -> "inline")
}
