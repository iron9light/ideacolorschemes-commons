/*
 * Copyright 2012 IL <iron9light AT gmali DOT com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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