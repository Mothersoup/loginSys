package com.example.loginsystem;


import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class QrcodeTest {
    @Test
    public void TestQrcodeImage(){

        QrcodeGenerator generator = new QrcodeGenerator();
        try {
            generator.showImage( QrcodeGenerator.genQrcodeImage(""));

            // 暫停 5 秒讓視窗停留
            Thread.sleep(5000000);
        } catch (WriterException | IOException e) {
            System.out.println("pls");
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
