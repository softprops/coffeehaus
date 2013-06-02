package cawfee

class IcedCompilerSpec extends CawfeeSpec {
  describe("Iced Compile") {
    it ("should compile iced coffee") {
      assert(Compile.iced("alert 'hello'") === Right(file("/iced/basic.js")))
    }

    it ("should compile bare coffee") {
      assert(Compile.iced("alert 'hello'", Options(bare = true)) === Right(file("/iced/bare.js")))
    }
  }
}
