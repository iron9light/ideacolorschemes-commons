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