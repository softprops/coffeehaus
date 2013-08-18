package coffeehaus

class IcedCompileSpec extends CoffeehausSpec {
  describe("Iced Compile") {
    it ("should compile iced coffee") {
      assert(Compile.iced()("alert 'hello'") ===
        Right(JsSource(file("/iced/basic.js"))))
    }

    it ("should compile bare coffee") {
      assert(Compile.iced(Options(bare = true))("alert 'hello'") ===
        Right(JsSource(file("/iced/bare.js"))))
    }
  }
}
