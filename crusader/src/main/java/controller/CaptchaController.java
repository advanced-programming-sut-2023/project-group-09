package controller;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

import enumeration.Paths;
import lombok.SneakyThrows;
import model.captcha.Captcha;
import org.apache.commons.codec.binary.Base64;
public class CaptchaController {


    private static final String SECRET_KEY_1 = "ssdkF$HUy2A#D%kd";
    private static final String SECRET_KEY_2 = "weJiSEvR5yAC5ftB";

    private static IvParameterSpec ivParameterSpec;
    private static SecretKeySpec secretKeySpec;
    private static Cipher cipher;

    @SneakyThrows
    public static void makeControllerVariable() {
        ivParameterSpec = new IvParameterSpec(SECRET_KEY_1.getBytes("UTF-8"));
        secretKeySpec = new SecretKeySpec(SECRET_KEY_2.getBytes("UTF-8"), "AES");
        cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
    }


    @SneakyThrows
    public static String decrypt(String encrypted)  {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
        return new String(decryptedBytes);
    }

    public static String makeRandomNumber(){
        Random rand = new Random();
        int n = rand.nextInt(2) + 4;
        StringBuilder output = new StringBuilder();
        int ai;
        for (int i = 0; i < n;i++){
            ai = rand.nextInt(9);
            output.append(ai);
        }
        return output.toString();
    }


    @SneakyThrows
    private static String getStringOfCaptcha(char num) {
        String path = Paths.CAPTCHA_DATA_PATH.getPath() + num + ".txt";
        String cipherText = new String(Files.readAllBytes(Path.of(path)));
        CaptchaController.makeControllerVariable();
        return decrypt(cipherText);
    }

    @SneakyThrows
    public static String makePictureWithoutNoise(String value) {
        char[] valueChars = value.toCharArray();
        String[] picture = {"","","","","",""};


        for (int i = 0 ; i < valueChars.length; i++){
            String[] output = getStringOfCaptcha(valueChars[i]).split("\r\n");
            for (int j = 0; j < 6; j++){
                output[j] = fixSize(output[j]);
                picture[j] += output[j];
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 6; i++){
            result.append(picture[i]).append("\n");
        }
        return result.toString();
    }

    private static String fixSize(String str){
        StringBuilder result = new StringBuilder(str);
        if(str.length() < 10){
            result.append(" ".repeat(10 - str.length()));
        }
        return result.toString();
    }

    public static String addNoise(String picture,String value){
        String[] output = picture.split("\n");
        int length = value.length() * 10;
        int width = 6;
        Random random = new Random();
        for (int i = 0; i < value.length() * 6;i++){
            int y = random.nextInt(width);
            int x = random.nextInt(length);
            output[y] = output[y].substring(0,x) + "#" + output[y].substring(x + 1);
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 6; i++){
            result.append(output[i]).append("\n");
        }
        return result.toString();
    }

    @SneakyThrows
    public static Captcha createCaptcha(){
        Captcha captcha = new Captcha();
        System.out.println(captcha.getCaptchaImage());
        return captcha;
    }


    public static boolean isCaptchaTrue(Scanner scanner){
        Captcha captcha = createCaptcha();
        String input;
        int counter = 1;
        while (true){
            if(counter != 1){
                System.out.println("your input was not correct please try again!");
            }

            input = scanner.nextLine();

            if(input.equals("new captcha")){
                captcha = createCaptcha();
                counter = 0;
            } else if (input.equals("exit captcha")) {
                return false;
            }else if(captcha.isCaptchaTrue(input)){
                return true;
            } else if (counter == 5) {
                return false;
            }
            counter++;
        }
    }
}
