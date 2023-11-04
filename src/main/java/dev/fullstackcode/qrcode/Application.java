package dev.fullstackcode.qrcode;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public QRCodeWriter getQrCodeWriter(){
		return new QRCodeWriter();
	}

	@Bean
	public MultiFormatWriter getMultiFormatWriter(){
		return new MultiFormatWriter();
	}

}
