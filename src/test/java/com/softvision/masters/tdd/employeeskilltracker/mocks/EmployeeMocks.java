package com.softvision.masters.tdd.employeeskilltracker.mocks;

import com.softvision.masters.tdd.employeeskilltracker.model.Employee;
import com.softvision.masters.tdd.employeeskilltracker.model.Skill;

import java.time.LocalDate;

public class EmployeeMocks {
    public static String MOCK_EMPLOYEE_FIRSTNAME_1 = "Liscia";
    public static String MOCK_EMPLOYEE_LASTNAME_1 = "Elfrieden";

    public static String MOCK_EMPLOYEE_FIRSTNAME_2 = "Roroa";
    public static String MOCK_EMPLOYEE_LASTNAME_2 = "Amidonia";

    public static String MOCK_EMPLOYEE_FIRSTNAME_3 = "Naden";
    public static String MOCK_EMPLOYEE_LASTNAME_3 = "Souma";


    public static String MOCK_SKILL_DESCRIPTION_1 = "Military";
    public static int MOCK_SKILL_DURATION_1 = 2;
    public static LocalDate MOCK_SKILL_LAST_USED_1 = LocalDate.of(2022, 5, 27);

    public static String MOCK_SKILL_DESCRIPTION_2 = "Accounting";
    public static int MOCK_SKILL_DURATION_2 = 7;
    public static LocalDate MOCK_SKILL_LAST_USED_2 = LocalDate.of(2022, 4, 30);

    public static Employee getMockEmployee1(){
        return new Employee(MOCK_EMPLOYEE_FIRSTNAME_1, MOCK_EMPLOYEE_LASTNAME_1);
    }

    public static Employee getMockEmployee2(){
        return new Employee(MOCK_EMPLOYEE_FIRSTNAME_2, MOCK_EMPLOYEE_LASTNAME_2);
    }

    public static Employee getMockEmployee3(){
        return new Employee(MOCK_EMPLOYEE_FIRSTNAME_3, MOCK_EMPLOYEE_LASTNAME_3);
    }


    public static Skill getMockSkill1(){
        return new Skill(MOCK_SKILL_DESCRIPTION_1, MOCK_SKILL_DURATION_1, MOCK_SKILL_LAST_USED_1);
    }

    public static Skill getMockSkill2(){
        return new Skill(MOCK_SKILL_DESCRIPTION_2, MOCK_SKILL_DURATION_2, MOCK_SKILL_LAST_USED_2);
    }
}
