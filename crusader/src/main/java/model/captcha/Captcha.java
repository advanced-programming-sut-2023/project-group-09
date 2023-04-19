package model.captcha;

import controller.CaptchaController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Captcha {
    String value;

    public Captcha() {
        this.value = CaptchaController.makeRandomNumber();
    }

    public boolean isCaptchaTrue(String input){
        return value.equals(input);
    }

    public String getCaptchaImage() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String picture = CaptchaController.makePictureWithoutNoise(value);
        return CaptchaController.addNoise(picture,value);
    }


}