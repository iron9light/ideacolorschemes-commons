package com.ideacolorschemes.commons.bson

import com.ideacolorschemes.commons.entities._
import com.ideacolorschemes.commons.Binary
import org.bson.{BasicBSONObject, BSONObject}
import collection.JavaConversions._
import java.util.{Date, ArrayList}

/**
 * @author il
 * @version 11/22/11 11:48 PM
 */

object ColorSettingsPageObjectParser extends BsonParser[ColorSettingsPageObject] {
  def fromBson(b: BSONObject) = {
    val bsonId = b.get("_id").asInstanceOf[BSONObject]
    val id = bsonId.get("id").asInstanceOf[String]
    val version = Option(bsonId.get("version")).map(_.asInstanceOf[String])
    val name = bsonId.get("name").asInstanceOf[String]
    val icon = Option(b.get("icon")).map(x => Binary(x.asInstanceOf[Array[Byte]]))
    val colors = Option(b.get("colors")).map(ColorDescriptorObjectParser.listFromBson(_)).filterNot(_.isEmpty)
    val attributes = Option(b.get("attributes")).map(AttributesDescriptorObjectParser.listFromBson(_)).filterNot(_.isEmpty)
    val codeSnippet = CodeSnippetParser.fromBson(b.get("codeSnippet"))
    val timestamp = b.get("timestamp").asInstanceOf[Date]
    ColorSettingsPageObject(id,
      version,
      name,
      icon,
      colors,
      attributes,
      codeSnippet,
      timestamp)
  }

  def toBson(colorSetting: ColorSettingsPageObject) = colorSetting match {
    case ColorSettingsPageObject(id,
    version,
    name,
    icon,
    colors,
    attributes,
    codeSnippet,
    timestamp) =>
      val bsonId = new BasicBSONObject().append("id", id)
      version.foreach(bsonId.put("version", _))
      bsonId.put("name", name)
      val b = new BasicBSONObject().append("_id", bsonId)
      icon.foreach(x => b.put("icon", x.bytes))
      colors.filterNot(_.isEmpty).foreach(x => b.put("colors", ColorDescriptorObjectParser.listToBsonArray(x)))
      attributes.filterNot(_.isEmpty).foreach(x => b.put("attributes", AttributesDescriptorObjectParser.listToBsonArray(x)))
      CodeSnippetParser.toBson(codeSnippet).foreach(b.put("codeSnippet", _))
      b.put("timestamp", timestamp)
      Some(b)
  }
}

object CodeSnippetParser extends BsonParser[CodeSnippet] {
  def fromBson(b: BSONObject) = {
    val text = b.get("text").asInstanceOf[String]
    val highlightMarks = Option(b.get("highlightMarks")).map(HighlightMarkParser.listFromBson(_)).getOrElse(Nil)
    CodeSnippet(text, highlightMarks)
  }

  def toBson(codeSnippet: CodeSnippet) = codeSnippet match {
    case CodeSnippet(text, highlightMarks) =>
      val b = new BasicBSONObject().append("text", text).append("highlightMarks", HighlightMarkParser.listToBsonArray(highlightMarks))
      Some(b)
  }
}

object HighlightMarkParser extends BsonParser[HighlightMark] {
  def fromBson(b: BSONObject) = {
    val startOffset = b.get("startOffset").asInstanceOf[Int]
    val endOffset = b.get("endOffset").asInstanceOf[Int]
    val keys = Option(b.get("keys")).map(_.asInstanceOf[ArrayList[String]].toList).filterNot(_.isEmpty)
    HighlightMark(startOffset, endOffset, keys)
  }

  def toBson(marks: HighlightMark) = marks match {
    case HighlightMark(startOffset, endOffset, keys) =>
      val b = new BasicBSONObject().append("startOffset", startOffset).append("endOffset", endOffset)
      keys.filterNot(_.isEmpty).foreach(x => b.put("keys", x.toArray))
      Some(b)
  }
}

object AttributesDescriptorObjectParser extends BsonParser[AttributesDescriptorObject] {
  def fromBson(b: BSONObject) = {
    val key = b.get("key").asInstanceOf[String]
    val name = b.get("name").asInstanceOf[String]
    AttributesDescriptorObject(key, name)
  }

  def toBson(attribute: AttributesDescriptorObject) = attribute match {
    case AttributesDescriptorObject(key, name) =>
      val b = new BasicBSONObject().append("key", key).append("name", name)
      Some(b)
  }
}

object ColorDescriptorObjectParser extends BsonParser[ColorDescriptorObject] {
  def fromBson(b: BSONObject) = {
    val key = b.get("key").asInstanceOf[String]
    val name = b.get("name").asInstanceOf[String]
    val kind = ColorKind(b.get("kind").asInstanceOf[Int])
    ColorDescriptorObject(key, name, kind)
  }

  def toBson(color: ColorDescriptorObject) = color match {
    case ColorDescriptorObject(key, name, kind) =>
      val b = new BasicBSONObject().append("key", key).append("name", name).append("kind", kind.id)
      Some(b)
  }
}