The upgrade to JavaCV 3 fails.

```
[info] Resolving org.fusesource.jansi#jansi;1.4 ...
[info] Done updating.
[info] Compiling 1 Scala source to C:\home\projects\blog-scala-javacv-master\tar
get\scala-2.10\classes...
[info] Running com.chimpler.javacv.FaceWebcamDetectorApp
[error] Exception in thread "main" org.bytedeco.javacv.FrameGrabber$Exception: c
vRetrieveFrame() Error: Could not retrieve frame. (Has start() been called?)
[error]         at org.bytedeco.javacv.OpenCVFrameGrabber.grab(OpenCVFrameGrabbe
r.java:239)
[error]         at com.chimpler.javacv.FaceWebcamDetectorApp$delayedInit$body.ap
ply(FaceWebcamDetectorApp.scala:38)
[error]         at scala.Function0$class.apply$mcV$sp(Function0.scala:40)
[error]         at scala.runtime.AbstractFunction0.apply$mcV$sp(AbstractFunction
0.scala:12)
[error]         at scala.App$$anonfun$main$1.apply(App.scala:71)
[error]         at scala.App$$anonfun$main$1.apply(App.scala:71)
[error]         at scala.collection.immutable.List.foreach(List.scala:318)
[error]         at scala.collection.generic.TraversableForwarder$class.foreach(T
raversableForwarder.scala:32)
[error]         at scala.App$class.main(App.scala:71)
[error]         at com.chimpler.javacv.FaceWebcamDetectorApp$.main(FaceWebcamDet
ectorApp.scala:7)
[error]         at com.chimpler.javacv.FaceWebcamDetectorApp.main(FaceWebcamDete
ctorApp.scala)
```





















