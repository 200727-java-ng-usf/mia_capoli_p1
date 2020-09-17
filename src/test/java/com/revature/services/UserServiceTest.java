package com.revature.services;

import com.revature.exceptions.AuthenticatorException;
import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.AppUser;
import com.revature.models.Role;
import com.revature.repos.AppUserRepo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

import org.mockito.*;


public class UserServiceTest {

    private AppUserRepo mockedRepo = Mockito.mock(AppUserRepo.class);

    private UserService userService;
    ArrayList<AppUser> mockUsers = new ArrayList<>();



    @Before
    public void setup() {
        userService = new UserService();
        mockUsers.add(new AppUser(1, "admin", "secret", "Adam", "inn", "aanderson@revature.com", Role.ADMIN));
        mockUsers.add(new AppUser(2, "man", "manager", "Manny", "gerr", "capolimia@gmail.com", Role.ADMIN));
        mockUsers.add(new AppUser(3, "aandserson", "password", "Alice", "Anderson", "mia@gmail.com", Role.EMPLOYEE));
        mockUsers.add(new AppUser(4, "bbailey", "dev", "bob", "bailey", "shawnreasin86@gmail.com", Role.FINANCE_MANAGER));
        userService.userRepo = mockedRepo;
    }

    @After
    public void teardown() {
        userService = null;
        mockUsers.removeAll(mockUsers);
    }

    @Test
    public void authenticationWithValidCredentials() {
        AppUser expectedUser = new AppUser(1, "Adam", "Inn", "admin", "secret", "aanderson@revature.com", Role.ADMIN);
        Mockito.when(mockedRepo.findUser("admin", "secret"))
                .thenReturn(java.util.Optional.of(expectedUser));
        AppUser actualResult = userService.authenticate("admin", "secret");
        Assert.assertEquals(expectedUser, actualResult);
    }

    @Test(expected = AuthenticatorException.class)
    public void authenticationWithInvalidCredentials() {
        //no arrange

        // Act
        userService.authenticate("","");

        //no assert, should raise an exception
    }

    @Test(expected = AuthenticatorException.class)
    public void authenticationWithUnknownCredentials() {
        userService.authenticate("garbage", "user");
    }

    @Test
    public void isUserValidValidser() {
        AppUser mockedCorrectAppUser = new AppUser(20, "Mia","Capoli", "cp", "nerd", "miao@revature.com", Role.ADMIN);

        boolean actualResult = userService.isUserValid(mockedCorrectAppUser);

        Assert.assertEquals(actualResult, true);

    }

    @Test
    public void isUserValidValidserFaulty() {
        AppUser mockedFaultyAppUser = new AppUser("Mia","", "cp", "nerd");

        boolean actualResult = userService.isUserValid(mockedFaultyAppUser);

        Assert.assertEquals(actualResult, false);

    }
    @Test(expected = InvalidInputException.class)
    public void registrationFaulty() {
        AppUser mockedFaultyAppUser = new AppUser("Mia","", "cp", "nerd");
        userService.registration(mockedFaultyAppUser);

    }
    @Test(expected = AuthenticatorException.class)
    public void registrationUserExists() {
        AppUser mockedFaultyAppUser = new AppUser(1, "Adam", "Inn", "admin", "secret");

        Mockito.when(mockedRepo.findUserByUsername("admin"))
                .thenReturn(Optional.of(mockedFaultyAppUser));

        userService.registration(mockedFaultyAppUser);
    }

    @Test
    public void registrationUserSuccess() {
        AppUser mockedCorrectAppUser = new AppUser("Mia","Capoli", "cp", "nerd");

        userService.registration(mockedCorrectAppUser);

        Assert.assertNotNull(mockedRepo.findUser("cp", "nerd"));
    }

    @Test
    public void updateUserSuccess() {
        AppUser expectedUser = new AppUser(1, "Adam", "Inn", "admin", "secreto");
        Mockito.when(mockedRepo.findUserByUsername("admin"))
                .thenReturn(java.util.Optional.of(expectedUser));
        AppUser actualResult = userService.updateUser(expectedUser);

        Assert.assertEquals(expectedUser, actualResult);
    }


    @Test(expected = AuthenticatorException.class)
    public void updateUserFailNoUser() {
        AppUser expectedUser = new AppUser(1, "Adam", "Inn", "admino", "secret", "aanderson@revature.com", Role.ADMIN);
        Mockito.when(mockedRepo.findUserByUsername("admin"))
                .thenReturn(java.util.Optional.of(expectedUser));
        userService.updateUser(expectedUser);

    }

    @Test(expected = InvalidInputException.class)
    public void updateUserFail() {
        AppUser expectedUser = new AppUser(1, "admin", "secret", "Adam", "inn", "aanderson@revature.com", Role.ADMIN);

        AppUser inputUser = new AppUser("admin", "", "Adam", "Inn", "aanderson@revature.com");
        Mockito.when(mockedRepo.findUserByUsername("admin"))
                .thenReturn(java.util.Optional.of(expectedUser));
        userService.updateUser(inputUser);
    }

    @Test
    public void deleteUser() {
        AppUser expectedUser = new AppUser(1, "admin", "secret", "Adam", "inn", "aanderson@revature.com", Role.ADMIN);
        Mockito.when(mockedRepo.findUserById(1))
                .thenReturn(java.util.Optional.of(expectedUser));
        boolean actualResult = userService.deleteUser(1);

        Assert.assertTrue(actualResult);
    }

    @Test(expected = InvalidInputException.class)
    public void deleteUserFail() {
        Mockito.when(mockedRepo.findUserById(200))
                .thenReturn(java.util.Optional.ofNullable(null));
        userService.deleteUser(200);

    }

    @Test
    public void getAllUsersTest() {
        ArrayList<AppUser> expectedResult = new ArrayList<>();
        expectedResult.add(new AppUser(1, "admin", "secret", "Adam", "inn", "aanderson@revature.com", Role.ADMIN));
        expectedResult.add(new AppUser(2, "man", "manager", "Manny", "gerr", "capolimia@gmail.com", Role.ADMIN));
        expectedResult.add(new AppUser(3, "aandserson", "password", "Alice", "Anderson", "mia@gmail.com", Role.EMPLOYEE));
        expectedResult.add(new AppUser(4, "bbailey", "dev", "bob", "bailey", "shawnreasin86@gmail.com", Role.FINANCE_MANAGER));
        Mockito.when(mockedRepo.findAllUsers())
                .thenReturn(mockUsers);
        ArrayList<AppUser> actualResult = userService.getAllUsers();
        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void getAllUsersNoneTest() {
        ArrayList<AppUser> expectedResult = new ArrayList<>();
        Mockito.when(mockedRepo.findAllUsers())
                .thenReturn(expectedResult);
        userService.getAllUsers();

    }

    @Test
    public void getUserByIdTest() {
        AppUser expectedResult = new AppUser(1, "admin", "secret", "Adam", "inn", "aanderson@revature.com", Role.ADMIN);
        Mockito.when(mockedRepo.findUserById(1))
                .thenReturn(Optional.of(expectedResult));
        AppUser actualResult = userService.getUserById(1);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = InvalidRequestException.class)
    public void getUserByIdBadIDTest() {
        userService.getUserById(-1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getUserByIdNoIDTest() {
        Mockito.when(mockedRepo.findUserById(1))
                .thenReturn(Optional.ofNullable(null));
        userService.getUserById(1);
    }

    @Test
    public void isUsernameAvailableTest() {
        boolean expectedResult = true;
        Mockito.when(mockedRepo.findUserByUsername("mamaMia"))
                .thenReturn(Optional.ofNullable(null));
        Boolean actualResult = userService.isUsernameAvailable("mamaMia");
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void isEmailAvailableTest() {
        boolean expectedResult = true;
        Mockito.when(mockedRepo.findUserByEmail("mamaMia@"))
                .thenReturn(Optional.ofNullable(null));
        Boolean actualResult = userService.isEmailAvailable("mamaMia@");
        Assert.assertEquals(expectedResult, actualResult);
    }
}