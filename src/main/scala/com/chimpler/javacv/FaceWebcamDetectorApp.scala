package com.chimpler.javacv


import java.awt.Color
import java.awt.Graphics
import java.awt.Image
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.awt.image.WritableRaster
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.nio.ByteBuffer
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel

import FaceWebcamDetectorApp._
import org.bytedeco.javacpp.opencv_core._
import org.bytedeco.javacpp.opencv_videoio._
//remove if not needed
import scala.collection.JavaConversions._

// Paste from http://answers.opencv.org/question/46638/java-how-capture-webcam-and-show-it-in-a-jpanel-like-imshow/
object FaceWebcamDetectorApp {

  val WIDTH = 640
  val HEIGHT = 480

  def main(args: Array[String]) {
    //    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    val t = new FaceWebcamDetectorApp()
    val camera = new VideoCapture(0)
    val frame = new Mat()
    camera.read(frame)
    if (!camera.isOpened) {
      println("Error")
    } else {

      var frameA:JFrame = null
      var frameB:JFrame = null

      while (true) {
        if (camera.read(frame)) {
          println("Have frame" + frame)
          val image = t.MatToBufferedImage(frame)
          if (frameA==null) {
            frameA = t.window(image, "Original Image", 0, 0)
            frameB = t.window(t.grayscale(image), "Processed Image", 40, 60)
          } else {
            t.replaceWindow(frameA, image, "Original Image", 0, 0)
            t.replaceWindow(frameB, t.grayscale(image), "Processed Image", 40, 60)
          }
          Thread.sleep(500)
        }
      }
    }
    camera.release()
  }
}

class FaceWebcamDetectorApp extends JPanel {

  var image: BufferedImage = _

  override def paint(g: Graphics) {
    g.drawImage(image, 0, 0, this)
  }

  def this(img: BufferedImage) {
    this()
    image = img
  }

  def window(img: BufferedImage,
             text: String,
             x: Int,
             y: Int):JFrame = {
    val frame0 = new JFrame()
    frame0.getContentPane.add(new FaceWebcamDetectorApp(img))
    frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame0.setTitle(text)
    frame0.setSize(img.getWidth, img.getHeight + 30)
    frame0.setLocation(x, y)
    frame0.setVisible(true)
    frame0
  }

  def replaceWindow(frame0: JFrame, img: BufferedImage,
                    text: String,
                    x: Int,
                    y: Int):JFrame = {
    frame0.getContentPane.removeAll()
    frame0.getContentPane.add(new FaceWebcamDetectorApp(img))
    frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame0.setTitle(text)
    frame0.setSize(img.getWidth, img.getHeight + 30)
    frame0.setVisible(true)
    frame0
  }

  def loadImage(file: String): BufferedImage = {
    var img: BufferedImage = null
    try {
      val input = new File(file)
      img = ImageIO.read(input)
      return img
    } catch {
      case e: Exception => println("erro")
    }
    null
  }

  def saveImage(img: BufferedImage) {
    try {
      val outputfile = new File("Images/new.png")
      ImageIO.write(img, "png", outputfile)
    } catch {
      case e: Exception => println("error")
    }
  }

  def grayscale(img: BufferedImage): BufferedImage = {
    for (i <- 0 until img.getHeight; j <- 0 until img.getWidth) {
      val c = new Color(img.getRGB(j, i))
      val red = (c.getRed * 0.299).toInt
      val green = (c.getGreen * 0.587).toInt
      val blue = (c.getBlue * 0.114).toInt
      val newColor = new Color(red + green + blue, red + green + blue, red + green + blue)
      img.setRGB(j, i, newColor.getRGB)
    }
    img
  }

  def MatToBufferedImage(frame: Mat): BufferedImage = {
    var `type` = 0
    if (frame.channels() == 1) {
      `type` = BufferedImage.TYPE_BYTE_GRAY
    } else if (frame.channels() == 3) {
      `type` = BufferedImage.TYPE_3BYTE_BGR
    }
    val image = new BufferedImage(WIDTH, HEIGHT, `type`)
    val raster = image.getRaster
    val dataBuffer = raster.getDataBuffer.asInstanceOf[DataBufferByte]
    val data = dataBuffer.getData
    //frame.get(0, 0, data)
    val buffer: ByteBuffer = frame.createBuffer()
    buffer.get(data)
    image
  }
}
