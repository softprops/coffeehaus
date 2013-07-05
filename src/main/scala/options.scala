package coffeehaus

/** Container for coffeescript options */
case class Options(
  bare: Boolean = false,
  sourceMap: Boolean = false,
  header: Boolean = false,
  shiftLine: Boolean = false,
  filename: Option[String] = None,
  // source map options
  inline: Option[Boolean] = None,
  generatedFile: Option[String] = None,
  sourceRoot: Option[String] = None,
  sourceFiles: Option[Seq[String]] = None
)
