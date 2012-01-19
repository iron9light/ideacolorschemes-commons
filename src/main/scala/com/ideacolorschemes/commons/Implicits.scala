package com.ideacolorschemes.commons

import entities.{FontType, FontSetting}


/**
 * @author il
 * @version 11/8/11 11:50 AM
 */

object Implicits {
  implicit def binary2binaryOption(binary: Binary): Option[Binary] = binary match {
    case Binary.Empty => None
    case b => Some(b)
  }

  implicit def binaryOption2binary(binaryOption: Option[Binary]): Binary = binaryOption match {
    case None => Binary.Empty
    case Some(b) => b
  }

  implicit def list2listOption[T](list: List[T]): Option[List[T]] = list match {
    case Nil => None
    case l => Some(l)
  }

  implicit def listOption2list[T](listOption: Option[List[T]]): List[T] = listOption match {
    case None => Nil
    case Some(l) => l
  }

  implicit def fontSetting2fontSettingOption(fontSetting: FontSetting) = fontSetting match {
    case FontSetting.Empty => None
    case f => Some(f)
  }

  implicit def fontSettingOption2fontSetting(fontSettingOption: Option[FontSetting]) = fontSettingOption match {
    case None => FontSetting.Empty
    case Some(f) => f
  }

  implicit def fontType2fontTypeOption(fontType: FontType.Value) = fontType match {
    case FontType.PLAIN => None
    case f => Some(f)
  }

  implicit def fontTypeOption2fontType(fontTypeOption: Option[FontType.Value]) = fontTypeOption match {
    case None => FontType.PLAIN
    case Some(f) => f
  }
}