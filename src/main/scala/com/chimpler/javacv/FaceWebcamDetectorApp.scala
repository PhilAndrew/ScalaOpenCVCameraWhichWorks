package com.chimpler.javacv

import java.awt.image.BufferedImage

import org.bytedeco.javacpp.opencv_core._
import org.bytedeco.javacpp.opencv_videoio.VideoCapture
import org.bytedeco.javacv.FrameGrabber.ImageMode
import org.bytedeco.javacv.{CanvasFrame, OpenCVFrameGrabber}

object FaceWebcamDetectorApp extends App {

  var b: Array[Byte] = null
  var image: BufferedImage = null

  def convert(m: Mat) {
    var `type` = BufferedImage.TYPE_BYTE_GRAY
    if (m.channels() > 1) {
      //Imgproc.cvtColor(m, m2, Imgproc.COLOR_BGR2RGB)
      `type` = BufferedImage.TYPE_3BYTE_BGR
    }
    val blen = m.channels() * m.cols() * m.rows()
    if (b == null || b.length != blen) b = Array.ofDim[Byte](blen)

    val byteBuffer = m.getByteBuffer
    for (n <- 0 until blen) {
      b(n) = byteBuffer.get(n)
    }
    if (image==null)
      image = new BufferedImage(m.cols(), m.rows(), `type`)
    image.getRaster.setDataElements(0, 0, m.cols(), m.rows(), b)
  }

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
  val camera = new VideoCapture(0)
/*  grabber.setImageWidth(WIDTH)
  grabber.setImageHeight(HEIGHT)
  grabber.setBitsPerPixel(CV_8U)
  grabber.setImageMode(ImageMode.COLOR)
  grabber.start()*/
  val frame = new Mat()

  var lastRecognitionTime = 0L

  //val mat = new Mat(WIDTH, HEIGHT, CV_8UC3)
  //val greyMat = new Mat(WIDTH, HEIGHT, CV_8U)
  //var faces: Seq[Face] = Nil
  while (true) {
    camera.read(frame)
    // convert to java.awt.Image

    //val bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR)
    //bufferedImage.(frame.getByteBuffer.array())
    convert(frame)
    canvas.showImage(image)
  }

}
