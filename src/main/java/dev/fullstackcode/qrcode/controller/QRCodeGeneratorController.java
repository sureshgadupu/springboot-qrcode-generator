package dev.fullstackcode.qrcode.controller;

import com.google.zxing.WriterException;
import dev.fullstackcode.qrcode.bean.QRCodeRequest;
import dev.fullstackcode.qrcode.service.QRCodeGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/generateQRCode")
public class QRCodeGeneratorController {

  @Autowired QRCodeGeneratorService qrCodeGeneratorService;


  @PostMapping(produces = MediaType.IMAGE_PNG_VALUE)
  public byte[] generateQRCode(@RequestBody QRCodeRequest qrCodeRequest) throws WriterException {
    return qrCodeGeneratorService.generateQRCode(qrCodeRequest.wifiNetwork(),
            qrCodeRequest.width(),qrCodeRequest.height());
  }

  @PostMapping(value = "/q2",produces = MediaType.IMAGE_PNG_VALUE)
  public byte[] generateQRCode2(@RequestBody QRCodeRequest qrCodeRequest) throws WriterException,
          IOException {
    return qrCodeGeneratorService.generateQRCode2(qrCodeRequest.wifiNetwork(),
            qrCodeRequest.width(),qrCodeRequest.height());
  }

  @PostMapping(value = "/q3",produces = MediaType.IMAGE_PNG_VALUE)
  public byte[] generateQRCode3(@RequestBody QRCodeRequest qrCodeRequest) throws WriterException {
    return qrCodeGeneratorService.generateQRCode3(qrCodeRequest.wifiNetwork(),
            qrCodeRequest.width(),qrCodeRequest.height());
  }

  @PostMapping(value = "/q4",produces = MediaType.IMAGE_PNG_VALUE)
  public byte[] generateQRCode4(@RequestBody QRCodeRequest qrCodeRequest) throws WriterException,
          IOException {
    return qrCodeGeneratorService.generateQRCode4(qrCodeRequest.wifiNetwork(),
            qrCodeRequest.width(),qrCodeRequest.height());
  }

  @PostMapping(value = "/qrOverlay",produces = MediaType.IMAGE_PNG_VALUE)
  public byte[] generateOverlayQRCode(@RequestBody QRCodeRequest qrCodeRequest) throws WriterException,
          IOException {
    return qrCodeGeneratorService.generateQRCode6(qrCodeRequest.wifiNetwork(),
            qrCodeRequest.width(),qrCodeRequest.height());
  }
}
