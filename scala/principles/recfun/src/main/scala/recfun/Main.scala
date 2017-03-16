package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if (c == 0 || c == r) 1 else pascal(c - 1, r - 1) + pascal(c, r - 1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      def check(level: Int, remain: List[Char]): Int = {
        var start = remain.indexOf('(')
        var end = remain.indexOf(')')
        if (level < 0) level
        else if (start >= 0 && (start < end || end < 0)) check(level + 1, remain.drop(start + 1)) 
        else if (end >= 0 && (end < start || start < 0)) check(level - 1, remain.drop(end + 1)) 
        else level
      }
      check(0, chars) == 0
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      var count = 0
      
      def incCoinNum(rMoney: Int, value: Int, num: Int, rCoins: List[Int]): Int = {
        var remain = rMoney - value * num
        if (remain == 0) count + 1 
        else if (remain < 0) count
        else {
          count + check(remain, rCoins) + 
          incCoinNum(rMoney, value, num + 1, rCoins)
        }
      }
      
      def check(rMoney: Int, rCoins: List[Int]): Int = {
        if (rCoins.length == 0) count 
        else if (rCoins.length == 1) {
          if (rMoney % rCoins.head == 0) count + 1 else count
        }
        else {
          count + incCoinNum(rMoney, rCoins.head, 0, rCoins.tail)
        }
      }
      
      check(money, coins)
    }
    countChange(4,List(1,2))
  }
