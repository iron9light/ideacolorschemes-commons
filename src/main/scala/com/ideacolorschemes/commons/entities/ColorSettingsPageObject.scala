package com.ideacolorschemes.commons
package entities

import com.ideacolorschemes.commons.Binary
import java.util.Date

/**
 * @author il
 * @version 11/7/11 6:22 PM
 */

case class ColorSettingsPageObject(id: String,
                                   version: Option[String],
                                   name: String,
                                   icon: Option[Binary],
                                   colors: Option[List[ColorDescriptorObject]],
                                   attributes: Option[List[AttributesDescriptorObject]],
                                   codeSnippet: CodeSnippet,
                                   timestamp: Date = new Date) extends WithTimestamp {
  def isGeneral = name == ColorScheme.GENERAL
}

case class CodeSnippet(text: String, highlightMarks: List[HighlightMark])

case class HighlightMark(startOffset: Int, endOffset: Int, keys: Option[List[String]])

case class AttributesDescriptorObject(key: String, name: String)

case class ColorDescriptorObject(key: String, name: String, kind: ColorKind.Value)

object ColorKind extends Enumeration {
  val FOREGROUND,
  BACKGROUND = Value
}