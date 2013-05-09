package cawfee

class IcedCompilerSpec extends CawfeeSpec {
  describe("Vanilla Compiler") {
    it ("should compile iced coffee") {
      Compile.iced("alert 'hello'").fold(fail(_), { js =>
        assert(js === file("/iced/basic.js"))
      })
    }

    it ("should compile bare coffee") {
      Compile.iced("alert 'hello'", Options(bare = true)).fold(fail(_), { js =>
        assert(js === file("/iced/bare.js"))
      })
    }
  }
}
