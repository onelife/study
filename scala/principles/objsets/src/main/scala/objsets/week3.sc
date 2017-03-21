package objsets

object week3 {
  val set1 = new Empty                            //> set1  : objsets.Empty = .E.
  val a = new Tweet("a", "a body", 20)            //> a  : objsets.Tweet = User: a
                                                  //| Text: a body [20]
  val set2 = set1.incl(a)                         //> set2  : objsets.TweetSet = {.E.}User: a
                                                  //| Text: a body [20]{.E.}
  val b =new Tweet("b", "b body", 20)             //> b  : objsets.Tweet = User: b
                                                  //| Text: b body [20]
  val set3 = set2.incl(b)                         //> set3  : objsets.TweetSet = {.E.}User: a
                                                  //| Text: a body [20]{{.E.}User: b
                                                  //| Text: b body [20]{.E.}}
  val c = new Tweet("c", "c body", 7)             //> c  : objsets.Tweet = User: c
                                                  //| Text: c body [7]
  val d = new Tweet("d", "d body", 9)             //> d  : objsets.Tweet = User: d
                                                  //| Text: d body [9]
  val set4c = set3.incl(c)                        //> set4c  : objsets.TweetSet = {.E.}User: a
                                                  //| Text: a body [20]{{.E.}User: b
                                                  //| Text: b body [20]{{.E.}User: c
                                                  //| Text: c body [7]{.E.}}}
  val set4d = set3.incl(d)                        //> set4d  : objsets.TweetSet = {.E.}User: a
                                                  //| Text: a body [20]{{.E.}User: b
                                                  //| Text: b body [20]{{.E.}User: d
                                                  //| Text: d body [9]{.E.}}}
  val set5 = set4c.incl(d)                        //> set5  : objsets.TweetSet = {.E.}User: a
                                                  //| Text: a body [20]{{.E.}User: b
                                                  //| Text: b body [20]{{.E.}User: c
                                                  //| Text: c body [7]{{.E.}User: d
                                                  //| Text: d body [9]{.E.}}}}
  var x = set5.filter(tw => tw.retweets == 20)    //> x  : objsets.TweetSet = {{.E.}User: a
                                                  //| Text: a body [20]{.E.}}User: b
                                                  //| Text: b body [20]{.E.}
  set5.filter(tw => tw.user == "b")               //> res0: objsets.TweetSet = {.E.}User: b
                                                  //| Text: b body [20]{.E.}
                                                  
	var y = set5.remove(a)                    //> y  : objsets.TweetSet = {.E.}User: b
                                                  //| Text: b body [20]{{.E.}User: c
                                                  //| Text: c body [7]{{.E.}User: d
                                                  //| Text: d body [9]{.E.}}}
	y.filter(tw => tw.retweets == 20)         //> res1: objsets.TweetSet = {.E.}User: b
                                                  //| Text: b body [20]{.E.}
	set2 union set3                           //> res2: objsets.TweetSet = {.E.}User: a
                                                  //| Text: a body [20]{{.E.}User: b
                                                  //| Text: b body [20]{.E.}}
  Map() ++ Seq(("1" -> 1), ("2", 2))              //> res3: scala.collection.immutable.Map[String,Int] = Map(1 -> 1, 2 -> 2)
  
  //TweetReader.ParseTweets.getTweetData("gizmodo", TweetData.gizmodo)
  GoogleVsApple.trending foreach println          //> User: gizmodo
                                                  //| Text: iPhone 5's brain dissected. Guess what, it's made by Samsung. http://t
                                                  //| .co/wSyjvpDc [321]
                                                  //| User: gizmodo
                                                  //| Text: Warning: Security bug can wipe out your Android phone just by visiting
                                                  //|  a web page-not only limited to Samsung http://t.co/0y6vnOKw [290]
                                                  //| User: mashable
                                                  //| Text: Our iPhones Are Depleting the Earth's Resources [INFOGRAPHIC] http://t
                                                  //| .co/XnTLqe0p [205]
                                                  //| User: gizmodo
                                                  //| Text: The weirdest thing people hate about the iPhone 5: http://t.co/GMwuRp8
                                                  //| D [202]
                                                  //| User: mashable
                                                  //| Text: iPad 4 Has Carbon Fiber Body, Flexible Display [REPORT] http://t.co/Df
                                                  //| t5VoXD via @tabtimes [198]
                                                  //| User: gizmodo
                                                  //| Text: The definitive comparison of iOS 5 Google Maps vs iOS 6 Apple Maps in 
                                                  //| one single image: http://t.co/fTwTfVMy [191]
                                                  //| User: mashable
                                                  //| Text: iOS 6 Users Complain About Wi-Fi, Connectivity Issues - http://t.co/io
                                                  //| gRstNn [180]
                                                  //| User: CNET
                                                  //| Text: RT @CNETNews: Apple "fell short" with iOS 6 ma
                                                  //| Output exceeds cutoff limit.
}