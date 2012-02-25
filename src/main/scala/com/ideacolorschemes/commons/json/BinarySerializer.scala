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