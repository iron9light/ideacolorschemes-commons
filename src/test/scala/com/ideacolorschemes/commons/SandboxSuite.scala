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

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import net.liftweb.json._

/**
 * @author il
 * @version 11/7/11 8:06 PM
 */

@RunWith(classOf[JUnitRunner])
class SandboxSuite extends FunSuite {
  implicit val formats = DefaultFormats // + new BytesSerializer

  ignore("Map to json") {
    val map = Map("a" -> Some(1), "b" -> Some(2), "c" -> None)
    println(Serialization.write(map))
  }

  ignore("json test") {
//    println(Serialization.write(Array[Byte](1, 2, 3, 4)))
    println(Serialization.read[Array[Byte]](Serialization.write(Array[Byte](1, 2, 3, 4))))
  }

//  class BytesSerializer extends Serializer[Array[Byte]] {
//    private val clazz = classOf[Array[Byte]]
//
//    def deserialize(implicit format: Formats) = {
//      case (TypeInfo(this.clazz, _), json) =>
//        json match {
//          case JString(s) => net.liftweb.util.Helpers.base64Decode(s)
//          case JNull | JNothing => Array[Byte]()
//        }
//    }
//
//    def serialize(implicit format: Formats) = {
//      case bytes: Array[Byte] => JString(net.liftweb.util.Helpers.base64Encode(bytes))
//    }
//  }

}