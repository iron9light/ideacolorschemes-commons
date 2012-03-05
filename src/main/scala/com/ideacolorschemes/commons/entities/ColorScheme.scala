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

import java.security.MessageDigest
import org.apache.commons.codec.binary.Base64
import java.util.Date

/**
 * @author il
 * @version 11/7/11 6:35 PM
 */

case class ColorScheme(id: ColorSchemeId,
                       dependencies: Option[List[ColorSchemeId]] = None,
                       isDefaultScheme: Boolean = false,
                       fontSetting: Option[FontSetting] = None,
                       colors: Map[String, Int] = Map.empty,
                       attributes: Map[String, TextAttributesObject] = Map.empty,
                       summary: Option[String] = None,
                       tags: Option[List[String]] = None,
                       timestamp: Date = new Date) extends WithTimestamp {
  def isGeneral = id.target == ColorScheme.GENERAL
}

object ColorScheme {
  val GENERAL = "General"
}

case class ColorSchemeId(author: String,
                         name: String,
                         version: Version,
                         target: String) {
  lazy val codeString = {
    val bytes = (author :: name :: version.toString :: target :: Nil).mkString(" ").getBytes("UTF-8")
    val messageDigest = MessageDigest.getInstance("SHA-1")
    val digest = messageDigest.digest(bytes)
    Base64.encodeBase64URLSafeString(digest)
  }
}

case class Version(major: Int, minor: Int, incremental: Option[Int] = None, isRelease: Boolean = false) {
  override def toString = {
    if (isRelease)
      "%s.%s".format(major, minor)
    else
      "%s.%s.%s".format(major, minor, incremental.map(_.toString).getOrElse("*"))
  }
}

object Version {
  private val pattern = """(\d+)\.(\d+)(.(\d+|\*))?""".r

  val NoVersion = apply(0, 0, None, true)

  def parse(s: String) = {
    s match {
      case pattern(majorStr, minorStr, _, incrementalStr) =>
        val major = majorStr.toInt
        val minor = minorStr.toInt
        incrementalStr match {
          case "*" =>
            Version(major, minor)
          case null =>
            Version(major, minor, None, true)
          case x =>
            Version(major, minor, Some(x.toInt))
        }
      case _ =>
        Version(s.toInt, 0, None, true)
    }
  }

  def apply(s: String): Version = parse(s)

  def apply(): Version = NoVersion
}

case class FontSetting(editorFontName: Option[String] = None, editorFontSize: Option[Int] = None, lineSpacing: Option[Float] = None, quickDocFontSize: Option[Int] = None)

object FontSetting {
  val Empty = FontSetting()
}

case class TextAttributesObject(foregroundColor: Option[Int] = None, backgroundColor: Option[Int] = None, effectColor: Option[Int] = None, effectType: Option[EffectType.Value] = None, fontType: Option[FontType.Value] = None, errorStripeColor: Option[Int] = None)

object TextAttributesObject {
  val Empty = TextAttributesObject()
}

object FontType extends Enumeration {
  val PLAIN,
  BOLD,
  ITALIC,
  BOLD_ITALIC = Value
}

object EffectType extends Enumeration {
  val LINE_UNDERSCORE,
  WAVE_UNDERSCORE,
  BOXED,
  STRIKEOUT,
  BOLD_LINE_UNDERSCORE,
  BOLD_DOTTED_LINE,
  SEARCH_MATCH,
  ROUNDED_BOX = Value
}