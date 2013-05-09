package cawfee

import scala.util.Success

class VanillaCompilerSpec extends CawfeeSpec {
  describe("Vanilla Compile") {
    it ("should compile vanilla coffee") {
      assert(Compile.vanilla("alert 'hello'") === Success(file("/vanilla/basic.js")))
    }
    
    it ("should compile bare coffee") {
      assert(Compile.vanilla("alert 'hello'", Options(bare = true)) == Success(file("/vanilla/bare.js")))
    }
  }
}
