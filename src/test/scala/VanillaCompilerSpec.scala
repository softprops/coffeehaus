package cawfee

class VanillaCompilerSpec extends CawfeeSpec {
  describe("Vanilla Compiler") {
    it ("should compile vanilla coffee") {
      Compile.vanilla("alert 'hello'").fold(fail(_), { js =>
        assert(js === file("/vanilla/basic.js"))
      })
    }
    
    it ("should compile bare coffee") {
      Compile.vanilla("alert 'hello'", Options(bare = true)).fold(fail(_), { js =>
        assert(js === file("/vanilla/bare.js"))
      })
    }
  }
}
