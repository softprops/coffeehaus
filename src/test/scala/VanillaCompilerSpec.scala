package cawfee

class VanillaCompilerSpec extends CawfeeSpec {
  describe("Vanilla Compile") {
    it ("should compile vanilla coffee") {
      assert(Compile.vanilla("alert 'hello'") === Right(file("/vanilla/basic.js")))
    }
    
    it ("should compile bare coffee") {
      assert(Compile.vanilla("alert 'hello'", Options(bare = true)) == Right(file("/vanilla/bare.js")))
    }
  }
}
