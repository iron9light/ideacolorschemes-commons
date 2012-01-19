package com.ideacolorschemes.commons.json

import com.ideacolorschemes.commons.Binary
import net.liftweb.json._
import org.apache.commons.codec.binary.Base64._

/**
 * @author il
 * @version 11/8/11 10:11 AM
 */

class BinarySerializer extends Serializer[Binary] {
  private val clazz = classOf[Binary]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), Binary] = {
    case (TypeInfo(this.clazz, _), json) =>
      json match {
        case JString(s) => decodeBase64(s)
        case value => throw new MappingException("Can't convert " + value + " to " + clazz)
      }
  }

  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
    case Binary(bytes) =>
      JString(encodeBase64String(bytes));
  }
}