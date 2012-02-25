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

