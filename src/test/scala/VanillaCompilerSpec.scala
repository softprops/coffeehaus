package cawfee

import scala.util.{ Failure, Try }

class VanillaCompilerSpec extends CawfeeSpec {
  describe("Vanilla Compiler") {
    it ("should compile vanilla coffee") {
      assert(Compile.vanilla("alert 'hello'")
             .recover({ case CompilerError(e) => Try(fail(e)) })
             .get === file("/vanilla/basic.js"))
    }
    
    it ("should compile bare coffee") {
      assert(Compile.vanilla("alert 'hello'", Options(bare = true))
             .recover({ case CompilerError(e) => Try(fail(e)) })
             .get === file("/vanilla/bare.js"))
    }
  }
}
