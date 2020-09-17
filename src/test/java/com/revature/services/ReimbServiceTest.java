package com.revature.services;

import com.revature.dtos.Principal;
import com.revature.exceptions.InvalidInputException;


import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.*;
import com.revature.repos.ReimbRepo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.doNothing;


public class ReimbServiceTest {

    private ReimbRepo mockedRepo = Mockito.mock(ReimbRepo.class);
    private ReimbService reimbService;
    ArrayList<Reimb> mockedReimbs = new ArrayList<>();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = dateFormat.parse("23/09/2007");
    long time = date.getTime();
    Timestamp timeFinal =  new Timestamp(time);

    public ReimbServiceTest() throws ParseException {
    }

    @Before
    public void setup() {
        reimbService = new ReimbService();
        mockedReimbs.add(new Reimb(1, 500, timeFinal, "hello", 1, ReimbStatusTypes.PENDING, 2));
        mockedReimbs.add(new Reimb(2, 100, timeFinal, "how", 2, ReimbStatusTypes.PENDING, 1));
        mockedReimbs.add(new Reimb(3, 600, timeFinal, "are", 3, ReimbStatusTypes.PENDING, 3));
        mockedReimbs.add(new Reimb(4, 1000, timeFinal, "you", 4, ReimbStatusTypes.PENDING, 4));
        reimbService.reimbRepo = mockedRepo;
    }

    @After
    public void teardown() {
        reimbService = null;
        mockedReimbs.removeAll(mockedReimbs);
    }

    @Test
    public void addNewReimbursementTest() {
        Mockito.when(mockedRepo.save(1000, "doing", 3, 2))
                .thenReturn(true);
        boolean actualResult = reimbService.addNewReimbursement(1000, "doing", "food", 2);
        Assert.assertEquals(true, actualResult);
    }

    @Test(expected = InvalidInputException.class)
    public void addNewReimbursementFailTest() {
        reimbService.addNewReimbursement(1000, "", "food", 2);

    }

    @Test
    public void updateReimbTest() {
        Reimb expectedResult = new Reimb(2, 100, Timestamp.valueOf(LocalDateTime.now()), "what", 2, ReimbStatusTypes.PENDING, 1);
        Mockito.when(mockedRepo.selectReimbursement(2)).thenReturn(expectedResult);
        doNothing().when(mockedRepo).updateReimb(1000, "what", 4, 2);
        Reimb actualResult = reimbService.updateReimb(1000, "what", "other", 2);
        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test(expected = InvalidRequestException.class)
    public void updateReimbInvalidTest() {
        Reimb expectedResult = new Reimb(2, 100, Timestamp.valueOf(LocalDateTime.now()), "what", 2, ReimbStatusTypes.APPROVED, 1);
        Mockito.when(mockedRepo.selectReimbursement(2)).thenReturn(expectedResult);
        doNothing().when(mockedRepo).updateReimb(1000, "what", 4, 2);
        reimbService.updateReimb(1000, "what", "other", 2);

    }

    @Test(expected = InvalidInputException.class)
    public void updateReimbInvalidInputTest() {

        reimbService.updateReimb(0, "what", "", 2);

    }

    @Test
    public void updateReimbStatus() {
        Reimb expectedResult = new Reimb(2, 100, Timestamp.valueOf(LocalDateTime.now()), "what", 2, ReimbStatusTypes.PENDING, 1);
        AppUser finMan = new AppUser(4, "bbailey", "dev", "bob", "bailey", "shawnreasin86@gmail.com", Role.FINANCE_MANAGER);
        Principal currentFinMan = new Principal(finMan);
        Mockito.when(mockedRepo.selectReimbursement(2)).thenReturn(expectedResult);
        Mockito.when(mockedRepo.updateReimbStatus(expectedResult, "Approved", currentFinMan)).thenReturn(true);
        Reimb actualResult = reimbService.updateReimbStatus("Approved", 2, currentFinMan);
        Assert.assertEquals(expectedResult, actualResult);

    }

    @Test(expected = InvalidInputException.class)
    public void updateReimbInvalidStatus() {
        AppUser finMan = new AppUser(4, "bbailey", "dev", "bob", "bailey", "shawnreasin86@gmail.com", Role.FINANCE_MANAGER);
        Principal currentFinMan = new Principal(finMan);
        reimbService.updateReimbStatus("Approved", 0, currentFinMan);
    }

    @Test
    public void getAllReimbsTest() {
        ArrayList<Reimb> expectedResult = new ArrayList<>();
        expectedResult.add(new Reimb(1, 500, timeFinal, "hello", 1, ReimbStatusTypes.PENDING, 2));
        expectedResult.add(new Reimb(2, 100, timeFinal, "how", 2, ReimbStatusTypes.PENDING, 1));
        expectedResult.add(new Reimb(3, 600, timeFinal, "are", 3, ReimbStatusTypes.PENDING, 3));
        expectedResult.add(new Reimb(4, 1000, timeFinal, "you", 4, ReimbStatusTypes.PENDING, 4));
        Mockito.when(mockedRepo.findAllReimbs())
                .thenReturn(mockedReimbs);
        ArrayList<Reimb> actualResult = reimbService.getAllReimbs();
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getAllReimbsNoneTest() {
        ArrayList<Reimb> expectedResult = new ArrayList<>();
        Mockito.when(mockedRepo.findAllReimbs())
                .thenReturn(expectedResult);
        reimbService.getAllReimbs();

    }

    @Test
    public void getAllReimbsbyTypeTest() {
        ArrayList<Reimb> expectedResult = new ArrayList<>();
        expectedResult.add(new Reimb(1, 500, timeFinal, "hello", 1, ReimbStatusTypes.PENDING, 2));
        Mockito.when(mockedRepo.findReimbByType(ReimbTypes.TRAVEL))
                .thenReturn(expectedResult);
        ArrayList<Reimb> actualResult = reimbService.getReimbsByType("Travel");
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getAllReimbsbyTypeNoneTest() {
        Mockito.when(mockedRepo.findReimbByType(ReimbTypes.TRAVEL))
                .thenReturn(null);
        reimbService.getReimbsByType("Travel");
    }

    @Test
    public void getAllReimbsbyStatusTest() {
        ArrayList<Reimb> expectedResult = new ArrayList<>();
        expectedResult.add(new Reimb(1, 500, timeFinal, "hello", 1, ReimbStatusTypes.PENDING, 2));
        Mockito.when(mockedRepo.findReimbByStatus(1))
                .thenReturn(expectedResult);
        ArrayList<Reimb> actualResult = reimbService.getReimbByStatus(1);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getAllReimbsbyStatusNoneTest() {
        Mockito.when(mockedRepo.findReimbByStatus(2))
                .thenReturn(null);
        reimbService.getReimbByStatus(2);
    }

    @Test(expected = InvalidInputException.class)
    public void getAllReimbsbyStatusZeroTest() {
        Mockito.when(mockedRepo.findReimbByStatus(0))
                .thenReturn(null);
        reimbService.getReimbByStatus(0);
    }

    @Test
    public void getAllReimbsbyUSerIdTest() {
        ArrayList<Reimb> expectedResult = new ArrayList<>();
        expectedResult.add(new Reimb(1, 500, timeFinal, "hello", 1, ReimbStatusTypes.PENDING, 2));
        Mockito.when(mockedRepo.findReimbsByUser(1))
                .thenReturn(expectedResult);
        ArrayList<Reimb> actualResult = reimbService.getReimbsByUserId(1);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getAllUSerReimbsbyStatusNoneTest() {
        Mockito.when(mockedRepo.findReimbsByUser(2))
                .thenReturn(null);
        reimbService.getReimbsByUserId(2);
    }

    @Test(expected = InvalidInputException.class)
    public void getAllUserReimbsbyStatusZeroTest() {
        Mockito.when(mockedRepo.findReimbsByUser(0))
                .thenReturn(null);
        reimbService.getReimbsByUserId(0);
    }

    @Test
    public void getAllReimbsbyIdStatusTest() {
        ArrayList<Reimb> expectedResult = new ArrayList<>();
        expectedResult.add(new Reimb(1, 500, timeFinal, "hello", 1, ReimbStatusTypes.PENDING, 2));
        Mockito.when(mockedRepo.findReimbsByUserStatus(1, "pending"))
                .thenReturn(expectedResult);
        ArrayList<Reimb> actualResult = reimbService.getReimbsByUserIdStatus(1, "pending");
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getAllUSerReimbsbyIDStatusNoneTest() {
        Mockito.when(mockedRepo.findReimbsByUserStatus(2, "pending"))
                .thenReturn(null);
        reimbService.getReimbsByUserIdStatus(2, "pending");
    }

    @Test(expected = InvalidInputException.class)
    public void getAllUserReimbsbyIDStatusZeroTest() {
        Mockito.when(mockedRepo.findReimbsByUserStatus(0, "pending"))
                .thenReturn(null);
        reimbService.getReimbsByUserIdStatus(0,"pending");
    }
}