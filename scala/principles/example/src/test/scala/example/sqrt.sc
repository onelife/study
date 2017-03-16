package example

object sqrtfunc {

def abs(x:Double) = if (x < 0) -x else x          //> abs: (x: Double)Double

def sqrtIter(guess: Double, x: Double): Double =
  if (isGoodEnough(guess, x)) guess
  else sqrtIter(improve(guess, x), x)             //> sqrtIter: (guess: Double, x: Double)Double

def isGoodEnough(guess: Double, x: Double) = abs(guess * guess - x) / x < 0.0001
                                                  //> isGoodEnough: (guess: Double, x: Double)Boolean

def improve(guess: Double, x: Double) =(guess + x / guess) / 2
                                                  //> improve: (guess: Double, x: Double)Double

def sqrt(x: Double) = sqrtIter(1.0, x)            //> sqrt: (x: Double)Double

sqrt(2)                                           //> res0: Double = 1.4142156862745097
sqrt(4)                                           //> res1: Double = 2.0000000929222947
sqrt(0.01)                                        //> res2: Double = 0.10000052895642693
sqrt(0.1e-20)                                     //> res3: Double = 3.1622778383672726E-11
sqrt(1.0e20)                                      //> res4: Double = 1.0000021484861237E10
sqrt(1.0e50)                                      //> res5: Double = 1.0000003807575104E25

}