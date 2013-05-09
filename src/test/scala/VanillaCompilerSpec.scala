package cawfee

class VanillaCompilerSpec extends CawfeeSpec {
  describe("Vanilla Compiler") {
    it ("should compile vanilla coffee") {
      Vanilla.compile("alert 'hello'").fold(fail(_), { js =>
        assert(js === file("/vanilla/basic.js"))
      })
    }
    
    it ("should compile bare coffee") {
      Vanilla.compile("alert 'hello'", bare = true).fold(fail(_), { js =>
        assert(js === file("/vanilla/bare.js"))
      })
    }
  }
}
