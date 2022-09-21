package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
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

    @Test  // зарегистрированный и активный
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");

        $x("//*[@data-test-id=\"login\"]//input").setValue(registeredUser.getLogin());
        $x("//*[@data-test-id=\"password\"]//input").setValue(registeredUser.getPassword());
        $x("//*[@data-test-id=\"action-login\"]//span[text()=\"Продолжить\"]").click();
        $x("//*[@id='root']").shouldHave(Condition.text("  Личный кабинет"));
    }

    @Test  // не зарегистрированный
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");

        $x("//*[@data-test-id=\"login\"]//input").setValue(notRegisteredUser.getLogin());
        $x("//*[@data-test-id=\"password\"]//input").setValue(notRegisteredUser.getPassword());
        $x("//*[@data-test-id=\"action-login\"]//span[text()=\"Продолжить\"]").click();
        $x("//*[@data-test-id=\"error-notification\"]").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test  // заблокированный
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");

        $x("//*[@data-test-id=\"login\"]//input").setValue(blockedUser.getLogin());
        $x("//*[@data-test-id=\"password\"]//input").setValue(blockedUser.getPassword());
        $x("//*[@data-test-id=\"action-login\"]//span[text()=\"Продолжить\"]").click();
        $x("//*[@data-test-id=\"error-notification\"]").shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));
    }

    @Test  // не верный логин
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();

        $x("//*[@data-test-id=\"login\"]//input").setValue(wrongLogin);
        $x("//*[@data-test-id=\"password\"]//input").setValue(registeredUser.getPassword());
        $x("//*[@data-test-id=\"action-login\"]//span[text()=\"Продолжить\"]").click();
        $x("//*[@data-test-id=\"error-notification\"]").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test  // не верный пароль
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();

        $x("//*[@data-test-id=\"login\"]//input").setValue(registeredUser.getLogin());
        $x("//*[@data-test-id=\"password\"]//input").setValue(wrongPassword);
        $x("//*[@data-test-id=\"action-login\"]//span[text()=\"Продолжить\"]").click();
        $x("//*[@data-test-id=\"error-notification\"]").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}