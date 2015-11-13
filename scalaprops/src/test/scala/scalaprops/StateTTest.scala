package scalaprops

import scalaz._
import scalaz.std.tuple._
import scalaz.std.anyVal._

object StateTTest extends Scalaprops {
  private[this] val e = new FunctionEqual(10)
  import e._

  implicit def stateTEqual[F[_], A, B](implicit F: Equal[A => F[(A, B)]]): Equal[StateT[F, A, B]] =
    F.contramap(_.apply _)

  val id = {
    type F[A] = State[Byte, A]
    Properties.list(
      scalazlaws.monad.all[F],
      scalazlaws.equal.all[F[Int]]
    )
  }

  val testIList = {
    type F[A] = StateT[IList, Int, A]

    Properties.list(
      scalazlaws.monadPlusStrong.all[F],
      scalazlaws.equal.all[F[Int]]
    )
  }

  val testMaybe = {
    type F[A] = StateT[Maybe, Int, A]

    Properties.list(
      scalazlaws.monadPlusStrong.all[F],
      scalazlaws.equal.all[F[Int]]
    )
  }

  val tree = {
    type F[A] = StateT[Tree, Int, A]

    Properties.list(
      scalazlaws.monad.all[F],
      scalazlaws.equal.all[F[Int]]
    )
  }

  val bifunctor = scalazlaws.bifunctor.laws[({type l[a, b] = IndexedStateT[Maybe, Int, a, b]})#l]

  val monadTrans = scalazlaws.monadTrans.all[({type l[f[_], a] = StateT[f, Int, a]})#l]

}
