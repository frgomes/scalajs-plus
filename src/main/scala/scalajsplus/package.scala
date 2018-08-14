import scala.scalajs.js

package object scalajsplus {

  implicit class UndefOr_Ext_Methods(val value: js.UndefOr[_]) extends AnyVal {
    @inline def isUndefinedOrNull: Boolean = value.isEmpty || value == null

    @inline def isDefinedAndNotNull: Boolean = value.isDefined && value != null
  }
}
