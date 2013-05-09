package cawfee

import scala.util.{ Failure, Try }

class IcedCompilerSpec extends CawfeeSpec {
  describe("Vanilla Compiler") {
    it ("should compile iced coffee") {
      assert(Compile.iced("alert 'hello'")
             .recover({ case CompilerError(e) => Try(fail(e)) })
             .get === file("/iced/basic.js"))
    }

    it ("should compile bare coffee") {
      assert(Compile.iced("alert 'hello'", Options(bare = true))
             .recover({ case CompilerError(e) => Try(fail(e)) })
             .get === file("/iced/bare.js"))
    }
  }
}
