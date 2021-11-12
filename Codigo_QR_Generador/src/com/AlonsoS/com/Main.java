package com.AlonsoS.com;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
public class Main {

    public static void main(String[] args) {
        Map hints = new HashMap();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        QRCodeWriter creador = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        ByteArrayOutputStream outputArray = new ByteArrayOutputStream();
        try {
            // Crea el codigo en base a la url, con tamaño en  250x250 pixels
            bitMatrix = creador.encode("https://github.com/Tsuna542", BarcodeFormat.QR_CODE, 250, 250, hints);
            MatrixToImageConfig config = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);
            // Carga la imagen QR
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
            // Carga el logo al QR
            File file = new File("/home/tsuna/Pictures/logo.png");
            BufferedImage logoImage = ImageIO.read(file);
            // Calcula la altura y anchura delta del logo.
            int deltaHeight = qrImage.getHeight() - logoImage.getHeight();
            int deltaWidth = qrImage.getWidth() - logoImage.getWidth();
            // Inicializa la imagen combinada
            BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = (Graphics2D) combined.getGraphics();
            // Escribe el codigo QR en una posicion 0/0
            g.drawImage(qrImage, 0, 0, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            // EScribe el codigo en imagen combinada en (deltaWidth / 2) y
            // (deltaHeight / 2). Fondo: Left/Right y Top/Bottom tienen que ser
            // el mismo espacio para que el logo este centrado.
            g.drawImage(logoImage, Math.round(deltaWidth / 2), Math.round(deltaHeight / 2), null);
            // DIbujar la imagen combinada como png
            ImageIO.write(combined, "png", new File("/home/tsuna/Pictures/QR.png"));
            System.out.println("Done");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
