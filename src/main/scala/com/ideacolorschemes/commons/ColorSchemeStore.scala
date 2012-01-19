package com.ideacolorschemes.commons

import entities.{ColorScheme, ColorSchemeId}


/**
 * @author il
 * @version 11/9/11 7:23 PM
 */

trait ColorSchemeStore {
  def get(id: ColorSchemeId): Option[ColorScheme]
}