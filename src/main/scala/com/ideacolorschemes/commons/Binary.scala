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