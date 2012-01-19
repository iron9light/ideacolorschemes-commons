package com.ideacolorschemes.commons.json

import net.liftweb.json._
import com.ideacolorschemes.commons.entities.Version

/**
 * @author il
 * @version 11/8/11 10:11 AM
 */

class VersionSerializer extends Serializer[Version] {
  private val clazz = classOf[Version]

  def deserialize(implicit format: Formats) = {
    case (TypeInfo(this.clazz, _), json) =>
      json match {
        case JString(s) => try {
          Version(s)
        } catch {
          case e: Exception =>
            throw new MappingException("Can't convert %s to %s".format(json, clazz), e)
        }
        case _ =>
          throw new MappingException("Can't convert %s to %s".format(json, clazz))
      }
  }

  def serialize(implicit format: Formats) = {
    case version: Version => JString(version.toString)
  }
}

