package gdg.toulouse.svg;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.core.scheme.VCard;
import net.glxn.qrgen.javase.QRCode;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class PNGQRCode {

    public static String createFrom(String identity, String mail) {
        final VCard vCard = new VCard(identity).setEmail(mail);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        QRCode.from(vCard).to(ImageType.PNG).withSize(200, 200).withCharset("UTF-8").writeTo(outputStream);

        return new String(Base64.getEncoder().encode(outputStream.toByteArray()));
    }

}
