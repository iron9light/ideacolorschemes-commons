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
import JsonDSL._
import com.ideacolorschemes.commons.entities.{EffectType, FontType, TextAttributesObject}

/**
 * @author il
 * @version 11/8/11 1:04 PM
 */

class TextAttributesObjectSerializer extends Serializer[TextAttributesObject] {
  private val clazz = classOf[TextAttributesObject]

  def deserialize(implicit format: Formats) = {
    case (TypeInfo(this.clazz, _), json) =>
      json match {
        case jObject: JObject =>
          val values = jObject.values
          val foregroundColor = values.get("foregroundColor") match {
            case Some(bigInt: BigInt) => Some(bigInt.toInt)
            case _ => None
          }
          val backgroundColor = values.get("backgroundColor") match {
            case Some(bigInt: BigInt) => Some(bigInt.toInt)
            case _ => None
          }
          val effectColor = values.get("effectColor") match {
            case Some(bigInt: BigInt) => Some(bigInt.toInt)
            case _ => None
          }
          val effectType = values.get("effectType") match {
            case Some(bigInt: BigInt) if (bigInt <= EffectType.maxId) => Some(EffectType(bigInt.toInt))
            case _ => None
          }
          val fontType = values.get("fontType") match {
            case Some(bigInt: BigInt) if (bigInt <= FontType.maxId) => Some(FontType(bigInt.toInt))
            case _ => None
          }
          val errorStripeColor = values.get("errorStripeColor") match {
            case Some(bigInt: BigInt) => Some(bigInt.toInt)
            case _ => None
          }

          TextAttributesObject(foregroundColor, backgroundColor, effectColor, effectType, fontType, errorStripeColor)
        case _ =>
          throw new MappingException("Can't convert " + json + " to " + clazz)
      }
  }

  def serialize(implicit format: Formats) = {
    case TextAttributesObject(foregroundColor, backgroundColor, effectColor, effectType, fontType, errorStripeColor) =>
      val list: List[Option[JField]] = foregroundColor.map(JField("foregroundColor", _)) ::
        backgroundColor.map(JField("backgroundColor", _)) ::
        effectColor.map(JField("effectColor", _)) ::
        effectType.map(x => JField("effectType", x.id)) ::
        fontType.map(x => JField("fontType", x.id)) ::
        errorStripeColor.map(JField("errorStripeColor", _)) :: Nil

      JObject(list.flatten)
  }
}