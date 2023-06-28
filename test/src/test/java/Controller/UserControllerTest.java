package Controller;

import controller.Application;
import controller.DBController;
import controller.UserController;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class UserControllerTest {
    @Test
    public void isStrongPasswordTest() {
        Assertions.assertEquals(1 , UserController.isPasswordStrong("Ab#23"));
        Assertions.assertEquals(1 , UserController.isPasswordStrong("12A@a"));
        Assertions.assertEquals(2 , UserController.isPasswordStrong("A13@@BB343"));
        Assertions.assertEquals(3 , UserController.isPasswordStrong("a13@@bb343"));
        Assertions.assertEquals(4 , UserController.isPasswordStrong("ajskjDfsd##@"));
        Assertions.assertEquals(5 , UserController.isPasswordStrong("ABCabc1234"));
        Assertions.assertEquals(1 , UserController.isPasswordStrong("AA12"));
        // correct passwords and random passwords will be check in the next method
    }

    @Test
    public void convertToPasswordToHashTest() throws Exception {
        Assertions.assertEquals(UserController.convertPasswordToHash("SomePass123#") , UserController.convertPasswordToHash("SomePass123#"));
        for (int i = 0; i != 100; i++) {
            String pass = UserController.generateRandomPassword();
            Assertions.assertEquals(UserController.convertPasswordToHash(pass) , UserController.convertPasswordToHash(pass));
        }
    }

    @Test
    public void removeSloganTest() {
        if (Application.getCurrentUser() == null)
            Assertions.assertEquals("You didn't log in!" , UserController.removeSlogan());
        else
            Assertions.assertEquals("slogan removed successfully!" , UserController.removeSlogan());
    }

    @Test
    public void changeSloganTest() {
        Assertions.assertEquals("slogan field is required!" , UserController.changeSlogan(""));
        Assertions.assertEquals("slogan changed successfully!" , UserController.changeSlogan("hj"));
        for (int i = 0; i != 100; i++) {
            String randomSlogan = UserController.generateRandomSlogan();
            Assertions.assertEquals("slogan changed successfully!", UserController.changeSlogan(randomSlogan));
        }
    }
    @Test
    public void changeEmailTest() {
        Assertions.assertEquals("email field is required!" , UserController.changeEmail(""));
        Assertions.assertEquals("email is invalid!" , UserController.changeEmail("@fhjdhj"));
        Assertions.assertEquals("email is invalid!" , UserController.changeEmail("fh.jdhj"));
        Assertions.assertEquals("email is invalid!" , UserController.changeEmail("dfdf.fd@fhjdhj"));
        Assertions.assertEquals("email is invalid!" , UserController.changeEmail("fdfdf@f@hjdhj"));
        Assertions.assertEquals("email is invalid!" , UserController.changeEmail("fhjdhj"));
        Assertions.assertEquals("email is invalid!" , UserController.changeEmail("fh.j@dhj"));
        Assertions.assertEquals("email is invalid!" , UserController.changeEmail("dsd@.fhjdhj"));
    }



    @Test
    public void changeUserNameTest(){

        DBController.loadAllUsers();
        DBController.loadCurrentUser();

        //check change username error and action
        String output = UserController.changeUsername(null);
        System.out.println(output);

        output = UserController.changeUsername("user2");
        System.out.println(output);

        output = UserController.changeUsername("user$");
        System.out.println(output);

        output = UserController.changeUsername("user5");
        System.out.println(output);
        System.out.println(Application.getCurrentUser().getUsername());
    }

    @Test
    public void changeNickNameTest(){

        DBController.loadAllUsers();
        DBController.loadCurrentUser();

        //check nickname error and action
        String output = UserController.changeNickname(null);
        System.out.println(output);

        output = UserController.changeNickname("this is a nick name");
        System.out.println(output);
        System.out.println(Application.getCurrentUser().getNickname());
    }

    @Test
    public void changePasswordTest(){
        DBController.loadAllUsers();
        DBController.loadCurrentUser();
        //check errors of change password
        String newPassword = null;
        String oldPassword = "MyPass#12";
        String output = UserController.changePassword(oldPassword,newPassword);
        System.out.println(output);

        newPassword = "MyPass#12";
        oldPassword = null;
        output = UserController.changePassword(oldPassword,newPassword);
        System.out.println(output);


        newPassword = "MyPass#12";
        oldPassword = "MyPass#11";
        output = UserController.changePassword(oldPassword,newPassword);
        System.out.println(output);

        newPassword = "MyPass#12";
        oldPassword = "MyPass#12";
        output = UserController.changePassword(oldPassword,newPassword);
        System.out.println(output);

        newPassword = "MyPass222";
        oldPassword = "MyPass#12";
        output = UserController.changePassword(oldPassword,newPassword);
        System.out.println(output);

    }

    /*@Test
    public void changeEmailTest(){

        DBController.loadAllUsers();
        DBController.loadCurrentUser();

        //check change username error and action
        String output = UserController.changeEmail(null);
        System.out.println(output);

        output = UserController.changeEmail("user2@gmail.com");
        System.out.println(output);

        output = UserController.changeEmail("user$@ljs.");
        System.out.println(output);

        output = UserController.changeEmail("sd@.ll");
        System.out.println(output);

        output = UserController.changeEmail("user5@gmail.com");
        System.out.println(output);

        System.out.println(Application.getCurrentUser().getEmail());
    }*/

    //@Test
    /*public void changeSloganTest(){
        DBController.loadAllUsers();
        DBController.loadCurrentUser();

        //check chane and remove slogan error and action
        String output = UserController.changeSlogan(null);
        System.out.println(output);

        output = UserController.changeSlogan("this is a slogan");
        System.out.println(output);
        System.out.println(Application.getCurrentUser().getSlogan());

        output = UserController.removeSlogan();
        System.out.println(output);
        System.out.println(Application.getCurrentUser().getSlogan());
    }*/

    @Test
    public void displayedProfileFunctionsTest(){
        DBController.loadAllUsers();
        DBController.loadCurrentUser();

        System.out.println(UserController.displayProfile());
        System.out.println(UserController.displayRank());
        System.out.println(UserController.displayHighScore());
    }
}
