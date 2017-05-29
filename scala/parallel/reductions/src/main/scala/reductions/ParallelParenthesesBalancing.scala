package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 10000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean = {
      def check(level: Int, remain: Array[Char]): Int = {
        val start = remain.indexOf('(')
        val end = remain.indexOf(')')
        if (level < 0) level
        else if (start >= 0 && (start < end || end < 0)) check(level + 1, remain.drop(start + 1)) 
        else if (end >= 0 && (end < start || start < 0)) check(level - 1, remain.drop(end + 1)) 
        else level
      }
      check(0, chars) == 0
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, level: Int, extraClose: Int): (Int, Int) = {
      //val rChars = chars.slice(idx, until)
      //println(rChars.mkString(" . "))
      val start0 = chars.indexOf('(', idx)
      val start = if (start0 < until) start0 else -1
      val end0 = chars.indexOf(')', idx)
      val end = if (end0 < until) end0 else -1
      val (newLevel0, newExtra) = if (level < 0) 
        (0, extraClose + 1)
      else 
        (level, extraClose) 
      
      if (start < 0 && end < 0) 
        (newLevel0, newExtra)
      else {
        val (newIdx, newLevel) = if (start >= 0 && (start < end || end < 0))
          (start + 1, newLevel0 + 1)
        else 
          (end + 1, newLevel0 - 1)
        traverse(newIdx, until, newLevel, newExtra) 
      }
    }

    def reduce(from: Int, until: Int): (Int, Int) = {
      if (until - from <= threshold) {
        traverse(from, until, 0, 0)
      } else {
        val mid = from + (until - from) / 2
        //println(from, until, mid)
        val ((a, b), (c, d)) = parallel(reduce(from, mid), 
          reduce(mid, until))
        if (a >= d) (a - d + c, b)
        else (c, b + d - a)
      }
    }

    reduce(0, chars.length) == (0, 0)
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
