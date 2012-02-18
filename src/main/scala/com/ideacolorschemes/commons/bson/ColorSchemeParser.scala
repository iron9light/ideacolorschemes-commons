package com.ideacolorschemes.commons.bson

import com.ideacolorschemes.commons.entities._
import org.bson.{BasicBSONObject, BSONObject}
import java.util.{Date, ArrayList}
import collection.JavaConversions._

/**
 * @author il
 * @version 11/22/11 4:09 PM
 */

object ColorSchemeParser extends BsonParser[ColorScheme] {
  def fromBson(b: BSONObject) = {
    val id = ColorSchemeIdParser.fromBson(b.get("_id"))
    val dependencies = Option(b.get("dependencies")).map(ColorSchemeIdParser.listFromBson(_)).filterNot(_.isEmpty)
    val isDefaultScheme = Option(b.get("isDefaultScheme")).map(_.asInstanceOf[Boolean]).getOrElse(false)
    val fontSetting = Option(b.get("fontSetting")).map(FontSettingParser.fromBson(_))
    val colors = Option(b.get("colors")).map(_.asInstanceOf[ArrayList[BSONObject]].map(b => (b.get("k").asInstanceOf[String], b.get("v").asInstanceOf[Int])).toMap).getOrElse(Map.empty[String, Int])
    val attributes = Option(b.get("attributes")).map(_.asInstanceOf[ArrayList[BSONObject]].map(b => (b.get("k").asInstanceOf[String], TextAttributesObjectParser.fromBson(b.get("v")))).toMap).getOrElse(Map.empty[String, TextAttributesObject])
    val summary = Option(b.get("summary")).map(_.asInstanceOf[String])
    val tags = Option(b.get("tags")).map(_.asInstanceOf[ArrayList[String]].toList).filterNot(_.isEmpty)
    val timestamp = b.get("timestamp").asInstanceOf[Date]
    ColorScheme(id,
      dependencies,
      isDefaultScheme,
      fontSetting,
      colors,
      attributes,
      summary,
      tags,
      timestamp)
  }

  def toBson(colorScheme: ColorScheme) = colorScheme match {
    case ColorScheme(id,
    dependencies,
    isDefaultScheme,
    fontSetting,
    colors,
    attributes,
    summary,
    tags,
    timestamp) =>
      val b = new BasicBSONObject()
      ColorSchemeIdParser.toBson(id).foreach(b.put("_id", _))
      dependencies.filterNot(_.isEmpty).foreach(x => b.put("dependencies", ColorSchemeIdParser.listToBsonArray(x)))
      b.put("isDefaultScheme", isDefaultScheme)
      fontSetting.flatMap(FontSettingParser.toBson(_)).foreach(b.put("fontSetting", _))
      if (!colors.isEmpty)
        b.put("colors", colors.map {
          case (key, value) => new BasicBSONObject().append("k", key).append("v", value)
        }.toArray)
      val attributeBsonMap = (for {
        (k, a) <- attributes
        bObj <- TextAttributesObjectParser.toBson(a)
      } yield new BasicBSONObject().append("k", k).append("v", bObj)).toArray
      if (!attributeBsonMap.isEmpty)
        b.put("attributes", attributeBsonMap)
      summary.foreach(b.put("summary", _))
      tags.filterNot(_.isEmpty).foreach(x => b.put("tags", x.toArray))
      b.put("timestamp", timestamp)
      Some(b)
  }
}

object TextAttributesObjectParser extends BsonParser[TextAttributesObject] {
  def fromBson(b: BSONObject) = {
    val foregroundColor = Option(b.get("foregroundColor")).map(_.asInstanceOf[Int])
    val backgroundColor = Option(b.get("backgroundColor")).map(_.asInstanceOf[Int])
    val effectColor = Option(b.get("effectColor")).map(_.asInstanceOf[Int])
    val effectType = if (effectColor.isDefined)
      Option(b.get("effectType")).map(x => EffectType(x.asInstanceOf[Int]))
    else
      None
    val fontType = Option(b.get("fontType")).map(x => FontType(x.asInstanceOf[Int]))
    val errorStripeColor = Option(b.get("errorStripeColor")).map(_.asInstanceOf[Int])
    TextAttributesObject(foregroundColor, backgroundColor, effectColor, effectType, fontType, errorStripeColor)
  }

  def toBson(textAttribute: TextAttributesObject) = textAttribute match {
    case TextAttributesObject.Empty => None
    case TextAttributesObject(foregroundColor, backgroundColor, effectColor, effectType, fontType, errorStripeColor) =>
      val b = new BasicBSONObject
      foregroundColor.foreach(b.put("foregroundColor", _))
      backgroundColor.foreach(b.put("backgroundColor", _))
      effectColor.foreach {
        x => {
          b.put("effectColor", x)
          effectType.foreach(t => b.put("effectType", t.id))
        }
      }
      fontType.foreach(x => b.put("fontType", x.id))
      errorStripeColor.foreach(b.put("errorStripeColor", _))
      Some(b)
  }
}

object FontSettingParser extends BsonParser[FontSetting] {
  def fromBson(b: BSONObject) = {
    val editorFontName = Option(b.get("editorFontName")).map(_.asInstanceOf[String])
    val editorFontSize = Option(b.get("editorFontSize")).map(_.asInstanceOf[Int])
    val lineSpacing = Option(b.get("lineSpacing")).map(_.asInstanceOf[Double].toFloat)
    val quickDocFontSize = Option(b.get("quickDocFontSize")).map(_.asInstanceOf[Int])
    FontSetting(editorFontName, editorFontSize, lineSpacing, quickDocFontSize)
  }

  def toBson(fontSetting: FontSetting) = fontSetting match {
    case FontSetting.Empty => None
    case FontSetting(editorFontName, editorFontSize, lineSpacing, quickDocFontSize) =>
      val b = new BasicBSONObject
      editorFontName.foreach(b.put("editorFontName", _))
      editorFontSize.foreach(b.put("editorFontSize", _))
      lineSpacing.foreach(b.put("lineSpacing", _))
      quickDocFontSize.foreach(b.put("quickDocFontSize", _))
      Some(b)
  }
}

object ColorSchemeIdParser extends BsonParser[ColorSchemeId] {
  def fromBson(b: BSONObject) = {
    val author = b.get("author").asInstanceOf[String]
    val name = b.get("name").asInstanceOf[String]
    val version = VersionParser.fromBson(b.get("version"))
    val target = b.get("target").asInstanceOf[String]
    ColorSchemeId(author, name, version, target)
  }

  def toBson(colorSchemeId: ColorSchemeId) = colorSchemeId match {
    case ColorSchemeId(author, name, version, target) =>
      val b = new BasicBSONObject()
        .append("author", author)
        .append("name", name)
      VersionParser.toBson(version).foreach(b.put("version", _))
      b.append("target", target)
      Some(b)
  }
}

object VersionParser extends BsonParser[Version] {
  def fromBson(b: BSONObject) = {
    val major = b.get("major").asInstanceOf[Int]
    val minor = b.get("minor").asInstanceOf[Int]
    val incremental = Option(b.get("incremental")).map(_.asInstanceOf[Int])
    val isRelease = b.get("isRelease").asInstanceOf[Boolean]
    Version(major, minor, incremental, isRelease)
  }

  def toBson(version: Version) = version match {
    case Version(major, minor, incremental, isRelease) =>
      val b = new BasicBSONObject()
        .append("major", major)
        .append("minor", minor)
      incremental.foreach(b.put("incremental", _))
      b.append("isRelease", isRelease)
      Some(b)
  }
}