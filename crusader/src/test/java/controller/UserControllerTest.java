package controller;

import org.junit.Test;


public class UserControllerTest {
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

    @Test
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
    }

    @Test
    public void changeSloganTest(){
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
    }

    @Test
    public void displayedProfileFunctionsTest(){
        DBController.loadAllUsers();
        DBController.loadCurrentUser();

        System.out.println(UserController.displayProfile());
        System.out.println(UserController.displayRank());
        System.out.println(UserController.displayHighScore());
    }
}
