package com.ideacolorschemes.commons

/**
 * @author il
 * @version 11/7/11 8:21 PM
 */

case class Binary(bytes: Array[Byte]) {
  override def hashCode = bytes.hashCode

  override def equals(any: Any): Boolean = any match {
    case Binary(bs) => this.bytes.equals(bs)
    case _ => this.bytes.equals(any)
  }
}

object Binary {
  val Empty = Binary(Array[Byte]())

  implicit def toByteArray(binary: Binary) = binary.bytes

  implicit def fromByteArroy(bytes: Array[Byte]) = Binary(bytes)
}