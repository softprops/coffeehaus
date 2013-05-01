package cc

import org.scalatest.FunSpec

class VanillaCompilerSpec extends FunSpec {
  describe("Vanilla Compiler") {
    it ("should compile vanilla coffee") {
      Vanilla.compile("alert 'hello'").fold(fail(_), { js =>
        assert(js === """(function() {
                      |  alert('hello');
                      |
                      |}).call(this);
                      |""".stripMargin)
      })
    }
  }
}
