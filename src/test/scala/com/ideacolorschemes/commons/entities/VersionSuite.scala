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