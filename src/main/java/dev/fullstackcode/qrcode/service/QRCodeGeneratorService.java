package dev.fullstackcode.qrcode.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import dev.fullstackcode.qrcode.bean.WifiNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.zxing.qrcode.*;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeGeneratorService {

  @Autowired QRCodeWriter qrCodeWriter;

  @Autowired MultiFormatWriter multiFormatWriter;

  @Autowired
  private ResourceLoader resourceLoader;

  public byte[] generateQRCode(WifiNetwork wifiNetwork,int width,int height) throws WriterException {

    Map<EncodeHintType, Object> hints = new HashMap<>();
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

    BitMatrix bitMatrix = qrCodeWriter.encode(wifiNetwork.getWifiNetworkString(),
            BarcodeFormat.QR_CODE, width, height,
            hints);

    BufferedImage qrCodeImage = getBufferedImage(width, height, bitMatrix);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      ImageIO.write(qrCodeImage, "png", outputStream);
    } catch (IOException e) {
      throw new RuntimeException("Failed to write QR code image to output stream.", e);
    }

    return outputStream.toByteArray();
  }

  private static BufferedImage getBufferedImage(int width, int height, BitMatrix bitMatrix) {
    BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    qrCodeImage.createGraphics();

    Graphics2D graphics = (Graphics2D) qrCodeImage.getGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, width, height);
    graphics.setColor(Color.BLACK);

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        if (bitMatrix.get(i, j)) {
          graphics.fillRect(i, j, 1, 1);
        }
      }
    }
    return qrCodeImage;
  }


  public byte[] generateQRCode2(WifiNetwork wifiNetwork,int width,int height) throws WriterException {

    Map<EncodeHintType, Object> hints = new HashMap<>();
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

    BitMatrix bitMatrix = multiFormatWriter.encode(wifiNetwork.getWifiNetworkString(),
            BarcodeFormat.UPC_A, 200, 200,
            hints);

    BufferedImage qrCodeImage = getBufferedImage(width, height, bitMatrix);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      ImageIO.write(qrCodeImage, "png", outputStream);
    } catch (IOException e) {
      throw new RuntimeException("Failed to write QR code image to output stream.", e);
    }

    return outputStream.toByteArray();
  }

  public byte[] generateQRCode3(WifiNetwork wifiNetwork,int width,int height) throws WriterException {

    //     QRCodeWriter qrCodeWriter = new QRCodeWriter();
    Map<EncodeHintType, Object> hints = new HashMap<>();
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

    BitMatrix bitMatrix = qrCodeWriter.encode(wifiNetwork.getWifiNetworkString(),
            BarcodeFormat.QR_CODE, 200, 200,
            hints);


    BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      ImageIO.write(qrCodeImage, "png", outputStream);
    } catch (IOException e) {
      throw new RuntimeException("Failed to write QR code image to output stream.", e);
    }

    return outputStream.toByteArray();
  }

  public byte[] generateQRCode4(WifiNetwork wifiNetwork, int width, int height)
          throws WriterException, IOException {


    Map<EncodeHintType, Object> hints = new HashMap<>();
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

    BitMatrix bitMatrix = qrCodeWriter.encode(wifiNetwork.getWifiNetworkString(),
            BarcodeFormat.QR_CODE, 200, 200,
            hints);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    MatrixToImageWriter.writeToStream(bitMatrix,"PNG",outputStream);


    return outputStream.toByteArray();
  }

  public byte[] generateQRCode6(WifiNetwork wifiNetwork, int width, int height)
          throws WriterException, IOException {


    Map<EncodeHintType, Object> hints = new HashMap<>();
    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

    BitMatrix bitMatrix = qrCodeWriter.encode(wifiNetwork.getWifiNetworkString(),
            BarcodeFormat.QR_CODE, 500, 500,
            hints);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix,new MatrixToImageConfig(
            0xFF000000,
            0xFFFFFFFF));

    BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(),
            BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = (Graphics2D) combined.getGraphics();

    // Write QR code to new image at position 0/0
    g.drawImage(qrImage, 0, 0, null);

    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

    addOverlayImage(g, qrImage);


    try {
      ImageIO.write(combined, "png", outputStream);
    } catch (IOException e) {
      throw new RuntimeException("Failed to write QR code image to output stream.", e);
    }

    return outputStream.toByteArray();


  }

  private  BufferedImage addOverlayImage(Graphics2D g, BufferedImage qrImage
                                              ) throws IOException {

    Resource resource = resourceLoader.getResource("classpath:images3.png");
    BufferedImage overlay = ImageIO.read(resource.getInputStream());

    // Calculate the delta height and width between QR code and the logo
    // Note that we don't do any scaling, so the sizes need to kind of
    // work together without obscuring too much logo
    int deltaHeight = qrImage.getHeight() - overlay.getHeight();
    int deltaWidth = qrImage.getWidth() - overlay.getWidth();

    int woffset = Math.round(deltaWidth / 2);
    int hoffset = Math.round(deltaHeight / 2);

    // Write the logo into the combined image at position (deltaWidth / 2) and
    // (deltaHeight / 2), so that it's centered
    g.drawImage(overlay, woffset, hoffset, null);
    return overlay;
  }

}
