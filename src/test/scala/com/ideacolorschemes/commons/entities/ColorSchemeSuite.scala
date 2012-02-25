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

package com.ideacolorschemes.commons.entities

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import com.ideacolorschemes.commons.json.ColorSchemeFormats
import java.io.InputStreamReader
import net.liftweb.json.Serialization
import com.ideacolorschemes.commons.bson.ColorSchemeParser
import org.bson.{BasicBSONDecoder, BasicBSONEncoder}
import collection.JavaConversions._

/**
 * @author il
 * @version 11/8/11 6:23 PM
 */

@RunWith(classOf[JUnitRunner])
class ColorSchemeSuite extends FunSuite with ShouldMatchers {
  implicit val formats = ColorSchemeFormats

  test("parse DefaultColorSchemes") {
    val input = this.getClass.getClassLoader.getResourceAsStream("DefaultColorSchemes.json")
    val reader = new InputStreamReader(input)

    try {
      val list = Serialization.read[List[ColorScheme]](reader)

      list should not be 'empty

      list.find(_.isGeneral) should not be 'empty

      list.forall(_.isDefaultScheme) should be === true

      val generalScheme = list.find(_.isGeneral).get

      generalScheme.colors should not be 'empty
    } finally {
      reader.close()
    }
  }

  test("bson") {
    val input = this.getClass.getClassLoader.getResourceAsStream("DefaultColorSchemes.json")
    val reader = new InputStreamReader(input)

    try {
      val list = Serialization.read[List[ColorScheme]](reader)
      val encoder = new BasicBSONEncoder
      val bytes = encoder.encode(ColorSchemeParser.listToBson(list))
      val decoder = new BasicBSONDecoder
      val b = decoder.readObject(bytes)
      val list2 = for{
        key <- b.keySet.toList
        bson <- Option(b.get(key))
      } yield {
        ColorSchemeParser.fromBson(bson)
      }

      list2 should be === list
    } finally {
      reader.close()
    }
  }
}