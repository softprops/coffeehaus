package coffeehaus

import org.mozilla.javascript.{
  Context, Function, JavaScriptException, NativeObject }
import java.io.InputStreamReader
import java.nio.charset.Charset

case class CompileError(msg: String)

sealed trait Output
case class JsSource(js: String) extends Output
case class SourceMapping(js: String, sourceMap: String) extends Output

trait Compile {
  def apply[T : InputSource](ins: T): Compile.Result
}

object Compile {
  type Result = Either[CompileError, Output]
  val charset = Charset.forName("utf-8")
  def iced(options: Options = Options()) = Iced.copy(options = options)
  def vanilla(options: Options = Options()) = Vanilla.copy(options = options)
  /** use the default (vanilla) compiler */
  def apply(code: String, options: Options = Options()) =
    vanilla(options).apply(code)
}

/**
 * A Scala / Rhino Coffeescript compiler.
 * @author daggerrz
 * @author doug
 */
case class Compiler(
  compiler: String,
  options: Options = Options(),
  args: Map[String, Any] = Map.empty[String, Any])
  extends Compile {

  override def toString = "%s(%s)" format(getClass.getSimpleName, compiler)

  /**
   * Compiles a string of Coffeescript code to Javascript.
   *
   * @param ins the Coffeescript input source
   * @return Compile.Result of either a compilation error description or
   *   the compiled Javascript code
   */
  def apply[T : InputSource](ins: T): Compile.Result =
    withContext { ctx =>
      val input = implicitly[InputSource[T]].apply(ins)
      val coffeescript = scope.get("CoffeeScript", scope).asInstanceOf[NativeObject]
      val compile = coffeescript.get("compile", scope).asInstanceOf[Function]
      val opts = ctx.evaluateString(scope, jsArgs(options), null, 1, null)
      try {
        println("args %s" format(jsArgs(options)))
        Right(compile.call(ctx, scope, coffeescript,
                       Array(input.src, opts)) match {
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

  /** Evaluate compiler source once in a shared scope */
  private lazy val scope = withContext { ctx =>
    val scope = ctx.initStandardObjects()
    ctx.evaluateReader(
      scope,
      new InputStreamReader(
        getClass().getResourceAsStream("/%s" format compiler),
        Compile.charset
      ),
      compiler,  //  source name
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

object Vanilla extends Compiler("vanilla/coffee-script.js")

object Iced extends Compiler(
  "iced/coffee-script.js",
  args = Map("runtime" -> "inline")
)
