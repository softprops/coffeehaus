package cc

import io.Source

trait Files {
  def file(path: String) =
    Source.fromURL(getClass.getResource(path)).getLines().mkString("\n")
}
