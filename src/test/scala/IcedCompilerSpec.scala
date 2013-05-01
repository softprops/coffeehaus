package cc

import org.scalatest.FunSpec

class IcedCompilerSpec extends FunSpec {
  describe("Vanilla Compiler") {
    it ("should compile iced coffee") {
      Iced.compile("alert 'hello'").fold(fail(_), { js =>
        assert(js === """(function() {
                      |
                      |  alert('hello');
                      |
                      |}).call(this);
                      |""".stripMargin)
      })
    }
  }
}
