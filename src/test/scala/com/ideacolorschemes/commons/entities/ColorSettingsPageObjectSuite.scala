package com.ideacolorschemes.commons.entities

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.ideacolorschemes.commons.json.ColorSettingsPageObjectFormats
import java.io.InputStreamReader
import net.liftweb.json.Serialization

/**
 * @author il
 * @version 11/8/11 12:33 PM
 */

@RunWith(classOf[JUnitRunner])
class ColorSettingsPageObjectSuite extends FunSuite with ShouldMatchers {
  implicit val formats = ColorSettingsPageObjectFormats

  test("parse ColorSettingsPageObject") {
    val input = this.getClass.getClassLoader.getResourceAsStream("ColorSettings.json")
    val reader = new InputStreamReader(input)

    try {
      val list = Serialization.read[List[ColorSettingsPageObject]](reader)

      list should not be 'empty

      list.head.attributes should not be 'empty

      list.head.colors should not be 'empty

      list.head.attributes.flatten.find(_.key == "ERRORS_ATTRIBUTES") should not be 'empty
    } finally {
      reader.close()
    }
  }
}