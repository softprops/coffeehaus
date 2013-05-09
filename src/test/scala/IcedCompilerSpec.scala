package cawfee

import scala.util.Success

class IcedCompilerSpec extends CawfeeSpec {
  describe("Iced Compile") {
    it ("should compile iced coffee") {
      assert(Compile.iced("alert 'hello'") === Success(file("/iced/basic.js")))
    }

    it ("should compile bare coffee") {
      assert(Compile.iced("alert 'hello'", Options(bare = true)) === Success(file("/iced/bare.js")))
    }
  }
}
