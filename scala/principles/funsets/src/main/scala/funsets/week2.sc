package funsets

object week2 {
  val x = FunSets.singletonSet(1)                 //> x  : funsets.FunSets.Set = <function1>
  x(1)                                            //> res0: Boolean = true
  x(2)                                            //> res1: Boolean = false

  def r3to9(i: Int): Boolean = i >= 3 && i <= 9   //> r3to9: (i: Int)Boolean
  FunSets.exists(r3to9, x => x > 9)               //> res2: Boolean = false

  var y = FunSets.map(r3to9, x => x * 2)          //> y  : funsets.FunSets.Set = <function1>
  y(3)                                            //> res3: Boolean = false
  y(6)                                            //> res4: Boolean = true
  y(18)                                           //> res5: Boolean = true
  
  var m = Zero.successor                          //> m  : funsets.Nat = funsets.Succ@7a0ac6e3
  var n = Zero.successor.successor                //> n  : funsets.Nat = funsets.Succ@71be98f5
  m+n                                             //> res6: funsets.Nat = funsets.Succ@6fadae5d
}

abstract class Nat {
  def isZero: Boolean
  def predecessor: Nat
  def successor: Nat = new Succ(this)
  def +(that: Nat): Nat
  def -(that: Nat): Nat
}

object Zero extends Nat {
  def isZero: Boolean = true
  def predecessor: Nat = throw new Error("0.predecessor")
  def +(that: Nat): Nat = that
  def -(that: Nat): Nat = if (that.isZero) this else throw new Error("negative number")
}

class Succ(n: Nat) extends Nat {
  def isZero: Boolean = false
  def predecessor: Nat = n
  def +(that: Nat): Nat = new Succ(n + that)
  def -(that: Nat): Nat = if (that.isZero) this else n - that.predecessor
}