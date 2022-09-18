package ru.netology.testmode.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = false;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
        $x("//*[@data-test-id=\"login\"]//input").setValue(registeredUser.getLogin());
        $x("//*[@data-test-id=\"password\"]//input").setValue(registeredUser.getPassword());
        $x("//*[@data-test-id=\"action-login\"]//span[text()=\"Продолжить\"]").click();
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
        $x("//*[@data-test-id=\"login\"]//input").setValue(notRegisteredUser.getLogin());
        $x("//*[@data-test-id=\"password\"]//input").setValue(notRegisteredUser.getPassword());
        $x("//*[@data-test-id=\"action-login\"]//span[text()=\"Продолжить\"]").click();
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
        $x("//*[@data-test-id=\"login\"]//input").setValue(blockedUser.getLogin());
        $x("//*[@data-test-id=\"password\"]//input").setValue(blockedUser.getPassword());
        $x("//*[@data-test-id=\"action-login\"]//span[text()=\"Продолжить\"]").click();
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
        $x("//*[@data-test-id=\"login\"]//input").setValue(wrongLogin);
        $x("//*[@data-test-id=\"password\"]//input").setValue(registeredUser.getPassword());
        $x("//*[@data-test-id=\"action-login\"]//span[text()=\"Продолжить\"]").click();
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
        $x("//*[@data-test-id=\"login\"]//input").setValue(registeredUser.getLogin());
        $x("//*[@data-test-id=\"password\"]//input").setValue(wrongPassword);
        $x("//*[@data-test-id=\"action-login\"]//span[text()=\"Продолжить\"]").click();
    }
}