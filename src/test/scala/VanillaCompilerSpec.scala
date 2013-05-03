package cc

import org.scalatest.FunSpec
import io.Source

class VanillaCompilerSpec extends FunSpec with Files {
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
