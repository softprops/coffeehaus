package coffeehaus

class VanillaCompileSpec extends CoffeehausSpec {
  describe("Vanilla Compile") {
    it ("should compile vanilla coffee") {
      assert(Compile.vanilla()("alert 'hello'") ===
        Right(JsSource(file("/vanilla/basic.js"))))
    }
    
    it ("should compile bare coffee") {
      assert(Compile.vanilla(Options(bare = true))("alert 'hello'") ===
        Right(JsSource(file("/vanilla/bare.js"))))
    }

    it ("should compile source maps") {
      val coffeeFile = "/Users/dougtangren/code/scala/projects/coffeehaus/src/test/resources/vanilla/sourcemap.coffee"
      assert(Compile.vanilla(Options(sourceMap = true, inline = Some(true)))(file("/vanilla/sourcemap.coffee")) ===
        Right(SourceMapping(file("/vanilla/sourcemap.js"), file("/vanilla/sourcemap.map"))))
    }
  }
}
