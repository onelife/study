package calculator

object test {
  def exprOfInput(input: String): Signal[Expr] = {
    Signal {
      try {
        parseExpr(input)
      } catch {
        case e: IllegalArgumentException =>
          Literal(Double.NaN)
      }
    }
  }                                               //> exprOfInput: (input: String)calculator.Signal[calculator.Expr]

  def parseExpr(text: String): Expr = {
    def parseSimple(text: String): Expr = {
      if (text.forall(l => l >= 'a' && l <= 'z')) {
        Ref(text)
      } else {
        try {
          Literal(text.toDouble)
        } catch {
          case e: NumberFormatException =>
            throw new IllegalArgumentException(s"$text is neither a variable name nor a number")
        }
      }
    }

    text.split(" ").map(_.trim).filter(_ != "") match {
      case Array(x) => parseSimple(x)
      case Array(aText, op, bText) =>
        val a = parseSimple(aText)
        val b = parseSimple(bText)
        op match {
          case "+" => Plus(a, b)
          case "-" => Minus(a, b)
          case "*" => Times(a, b)
          case "/" => Divide(a, b)
          case _ =>
            throw new IllegalArgumentException(s"$op is not a valid operator")
        }
      case _ =>
        throw new IllegalArgumentException(s"$text is not a valid simple expression")
    }
  }                                               //> parseExpr: (text: String)calculator.Expr


  val names = (0 until 10).map(i => ('a' + i).toChar.toString)
                                                  //> names  : scala.collection.immutable.IndexedSeq[String] = Vector(a, b, c, d,
                                                  //|  e, f, g, h, i, j)
  val inputs = Vector("22", "a + 1", "b - 1", "c * 2", "d / 4", "a + b", "h + 2", "g * 2", "f", "z")
                                                  //> inputs  : scala.collection.immutable.Vector[String] = Vector(22, a + 1, b -
                                                  //|  1, c * 2, d / 4, a + b, h + 2, g * 2, f, z)
  val exprs = inputs.map(exprOfInput)             //> exprs  : scala.collection.immutable.Vector[calculator.Signal[calculator.Exp
                                                  //| r]] = Vector(calculator.Signal@2096442d, calculator.Signal@9f70c54, calcula
                                                  //| tor.Signal@234bef66, calculator.Signal@737996a0, calculator.Signal@61dc03ce
                                                  //| , calculator.Signal@50f8360d, calculator.Signal@2cb4c3ab, calculator.Signal
                                                  //| @13c78c0b, calculator.Signal@12843fce, calculator.Signal@3dd3bcd)
  
  val namedExpressions = names.zip(exprs).toMap   //> namedExpressions  : scala.collection.immutable.Map[String,calculator.Signal
                                                  //| [calculator.Expr]] = Map(e -> calculator.Signal@61dc03ce, j -> calculator.S
                                                  //| ignal@3dd3bcd, f -> calculator.Signal@50f8360d, a -> calculator.Signal@2096
                                                  //| 442d, i -> calculator.Signal@12843fce, b -> calculator.Signal@9f70c54, g ->
                                                  //|  calculator.Signal@2cb4c3ab, c -> calculator.Signal@234bef66, h -> calculat
                                                  //| or.Signal@13c78c0b, d -> calculator.Signal@737996a0)
  val namedValues = Calculator.computeValues(namedExpressions)
                                                  //> namedValues  : Map[String,calculator.Signal[Double]] = Map(e -> calculator.
                                                  //| Signal@239963d8, j -> calculator.Signal@3abbfa04, f -> calculator.Signal@59
                                                  //| 8067a5, a -> calculator.Signal@5f341870, i -> calculator.Signal@553f17c, b 
                                                  //| -> calculator.Signal@6fd02e5, g -> calculator.Signal@5bcab519, c -> calcula
                                                  //| tor.Signal@e45f292, h -> calculator.Signal@5f2108b5, d -> calculator.Signal
                                                  //| @31a5c39e)
  
  def isLiteral(expr: Expr): Boolean = {
  	expr match {
  		case Literal(v) => true
  		case _ => false
  	}
  }                                               //> isLiteral: (expr: calculator.Expr)Boolean
  
  namedExpressions.filter({case (k, v) => {v() match {
  	case Literal(w) => true
  	case _ => false
  	}}})                                      //> res0: scala.collection.immutable.Map[String,calculator.Signal[calculator.Ex
                                                  //| pr]] = Map(a -> calculator.Signal@2096442d)
  
  for ((name, valueSignal) <- namedValues) {
  	println(s"Name: $name, Result: ${valueSignal().toString}")
                                                  //> Name: e, Result: 11.0
                                                  //| Name: j, Result: NaN
                                                  //| Name: f, Result: 45.0
                                                  //| Name: a, Result: 22.0
                                                  //| Name: i, Result: 45.0
                                                  //| Name: b, Result: 23.0
                                                  //| Name: g, Result: NaN
                                                  //| Name: c, Result: 22.0
                                                  //| Name: h, Result: NaN
                                                  //| Name: d, Result: 44.0
  }
 
}