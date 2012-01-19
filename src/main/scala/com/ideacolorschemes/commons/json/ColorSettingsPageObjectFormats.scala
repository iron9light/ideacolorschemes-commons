package com.ideacolorschemes.commons.json

import net.liftweb.json._
import ext.EnumSerializer
import com.ideacolorschemes.commons.entities.ColorKind

/**
 * @author il
 * @version 11/8/11 10:11 AM
 */

class ColorSettingsPageObjectFormats extends DefaultFormats {
  override val customSerializers: List[Serializer[_]] = new EnumSerializer(ColorKind) :: new BinarySerializer :: Nil
}

object ColorSettingsPageObjectFormats extends ColorSettingsPageObjectFormats