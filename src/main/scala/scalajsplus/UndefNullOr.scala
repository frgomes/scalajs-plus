package scalajsplus

import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.|.Evidence

/**
  *
  * @tparam A
  */
sealed trait UndefNullOr[+A] extends js.Any

object UndefNullOr {

  implicit class UndefNullOrOps[A](private val self: UndefNullOr[A])
      extends AnyVal {

    @inline final def isEmpty: Boolean = js.isUndefined(self) || self == null

    @inline final def isDefined: Boolean = !isEmpty

    @inline final def get: A =
      if (isEmpty) throw new NoSuchElementException("undefinednullor.get")
      else forceGet

    @inline final private def forceGet: A = self.asInstanceOf[A]

    @inline final def getOrElse[B >: A](default: => B): B =
      if (isEmpty) default else this.forceGet

    @inline final def map[B](f: A => B): UndefNullOr[B] =
      if (isEmpty) null
      else f(this.forceGet).asInstanceOf[UndefNullOr[B]]

    @inline final def fold[B](ifEmpty: => B)(f: A => B): B =
      if (isEmpty) ifEmpty else f(this.forceGet)

    @inline final def nonEmpty: Boolean = isDefined

    @inline final def contains[A1 >: A](elem: A1): Boolean =
      !isEmpty && elem == this.forceGet

    @inline final def exists(p: A => Boolean): Boolean =
      !isEmpty && p(this.forceGet)

    @inline final def foreach[U](f: A => U): Unit =
      if (!isEmpty) f(this.forceGet)

    @inline final def toOption: Option[A] =
      if (isEmpty) None else Some(this.forceGet)
  }
}
