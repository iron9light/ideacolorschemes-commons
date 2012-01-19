package com.ideacolorschemes.commons.entities

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * @author il
 * @version 11/8/11 1:25 PM
 */

@RunWith(classOf[JUnitRunner])
class VersionSuite extends FunSuite with ShouldMatchers {
  test("toString") {
    Version(1, 2, None, true).toString should be === "1.2"
    Version(1, 2, Some(3), true).toString should be === "1.2"
    Version(1, 2, Some(3)).toString should be === "1.2.3"
    Version(1, 2).toString should be === "1.2.*"
  }

  test("parse") {
    Version("1.2") should be === Version(1, 2, None, true)
    Version("1.2.3") should be === Version(1, 2, Some(3), false)
    Version("1.2.*") should be === Version(1, 2, None, false)
    Version("10.20") should be === Version(10, 20, None, true)
    Version("10.20.30") should be === Version(10, 20, Some(30), false)
    Version("1") should be === Version(1, 0, None, true)
  }
}