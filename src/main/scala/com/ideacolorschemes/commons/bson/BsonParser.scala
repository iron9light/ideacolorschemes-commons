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

package com.ideacolorschemes.commons.bson

import org.bson.BSONObject
import org.bson.types.BasicBSONList
import java.util.ArrayList
import collection.JavaConversions._

/**
 * @author il
 * @version 11/22/11 4:09 PM
 */

trait BsonParser[T] {
  def fromBson(b: Any): T = fromBson(b.asInstanceOf[BSONObject])

  def fromBson(b: BSONObject): T

  def toBson(o: T): Option[BSONObject]

  def listFromBson(b: Any): List[T] = listFromBson(b.asInstanceOf[ArrayList[_]])

  def listFromBson(b: ArrayList[_]): List[T] = {
    b.toList.map(fromBson(_))
  }

  def listToBsonArray(l: List[T]): Array[BSONObject] = {
    l.flatMap(toBson(_)).toArray
  }

  def listToBson(l: List[T]) = {
    val b = new BasicBSONList()
    l.flatMap(toBson(_)).foreach(b.add(_))
    b
  }

  implicit def toX(x: T) = new X(x)

  class X(x: T) {
    def asBson = toBson(x)
  }

}









