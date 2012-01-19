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









