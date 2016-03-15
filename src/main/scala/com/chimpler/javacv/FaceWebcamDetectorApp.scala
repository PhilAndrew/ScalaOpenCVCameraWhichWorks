package com.chimpler.javacv

import org.bytedeco.javacpp.opencv_core._
import org.bytedeco.javacv.FrameGrabber.ImageMode
import org.bytedeco.javacv.{CanvasFrame, OpenCVFrameGrabber}

object FaceWebcamDetectorApp extends App {

  val WIDTH = 1280
  val HEIGHT = 720

  // holder for a single detected face: contains face rectangle and the two eye rectangles inside
  case class Face(id: Int, faceRect: Rect, leftEyeRect: Rect, rightEyeRect: Rect)

  // we need to clone the rect because openCV is recycling rectangles created by the detectMultiScale method
  private def cloneRect(rect: Rect): Rect = {
    new Rect(rect.x, rect.y, rect.width, rect.height)
  }

  val canvas = new CanvasFrame("Webcam")

  //  //Set Canvas frame to close on exit
  canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)

  //Declare FrameGrabber to import output from webcam
  val grabber = new OpenCVFrameGrabber(0)
  grabber.setImageWidth(WIDTH)
  grabber.setImageHeight(HEIGHT)
  grabber.setBitsPerPixel(CV_8U)
  grabber.setImageMode(ImageMode.COLOR)
  grabber.start()

  var lastRecognitionTime = 0L

  val mat = new Mat(WIDTH, HEIGHT, CV_8UC3)
  val greyMat = new Mat(WIDTH, HEIGHT, CV_8U)
  var faces: Seq[Face] = Nil
  while (true) {
    val img = grabber.grab()
    canvas.showImage(img)
  }

}
