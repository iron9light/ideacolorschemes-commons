package com.ideacolorschemes.commons.json

import net.liftweb.json._

/**
 * @author il
 * @version 11/8/11 10:11 AM
 */

class ColorSchemeFormats extends DefaultFormats {
  override val customSerializers: List[Serializer[_]] = new VersionSerializer :: new TextAttributesObjectSerializer :: Nil
}

object ColorSchemeFormats extends ColorSchemeFormats