package cawfee

class IcedCompilerSpec extends CawfeeSpec {
  describe("Vanilla Compiler") {
    it ("should compile iced coffee") {
      Iced.compile("alert 'hello'").fold(fail(_), { js =>
        assert(js === file("/iced/basic.js"))
      })
    }

    it ("should compile bare coffee") {
      Iced.compile("alert 'hello'", bare = true).fold(fail(_), { js =>
        assert(js === file("/iced/bare.js"))
      })
    }
  }
}
