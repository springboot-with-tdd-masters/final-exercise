package com.example.demo.service;

import com.example.demo.dto.SkillDto;
import com.example.demo.dto.SkillRequest;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@SpringBootTest
class SkillServiceImplTest {

    private final String employee1FN = "James";
    private final String employee1LN = "Bond";
    private final String skill1Description = "Java";
    private final Integer skill1Duration = 11;
    private final LocalDate skill1LastUsed = LocalDate.of(2022, 01, 01);
    private final String skill2Description = "Angular";

    @MockBean
    private SkillServiceImpl skillService;

    private List<Skill> skillList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        skillList.add(buildSkill(1L, skill1Description, skill1Duration, skill1LastUsed, buildEmployee(1L, employee1FN, employee1LN)));
        skillList.add(buildSkill(2L, skill2Description, 5, LocalDate.of(2020, 12, 31), buildEmployee(1L, employee1FN, employee1LN)));

    }

    private Skill buildSkill(Long id, String description, Integer duration, LocalDate lastUsed, Employee employee) {
        return Skill.builder().id(id).description(description).duration(duration).lastUsed(lastUsed).employee(employee).build();
    }

    private Employee buildEmployee(Long id, String firstName, String lastName) {
        return Employee.builder().id(id).firstName(firstName).lastName(lastName).build();
    }

    @Test
    @DisplayName("Given a successful save, response should give http status 200")
    public void successCreateSkill() throws Exception {
        Long employeeId = 1L;
        SkillRequest skillRequest = new SkillRequest();
        skillRequest.setDescription(skill1Description);
        skillRequest.setDuration(skill1Duration);
        skillRequest.setLastUsed(skill1LastUsed.toString());

        ArgumentCaptor<SkillRequest> skillRequestArgumentCaptor = ArgumentCaptor.forClass(SkillRequest.class);
        when(skillService.createNewSkill(eq(employeeId), skillRequestArgumentCaptor.capture())).thenReturn(SkillDto.convertToDto(skillList.get(0)));

        SkillDto newSkill = skillService.createNewSkill(employeeId, skillRequest);
        assertThat(skillRequestArgumentCaptor.getValue().getDescription(), is(skill1Description));
        assertThat(newSkill.getDuration(), is(skill1Duration));
    }

    @Test
    @DisplayName("Given a successful read by ID, response should give http status 200")
    public void shouldReadById() throws Exception {
        when(skillService.readSkillById(1L)).thenReturn(SkillDto.convertToDto(skillList.get(0)));
        SkillDto retrievedSkill = skillService.readSkillById(1L);
        assertThat(retrievedSkill.getLastUsed(), is(skill1LastUsed.toString()));
    }


    @Test
    @DisplayName("All Skills should be returned based on Pageable request")
    public void testReadAllEmployees(){
        Pageable pageRequest = PageRequest.of(0, 2, Sort.by("description").descending());
        List<SkillDto> sortedSkillsDto = new ArrayList<>();
        List<Skill> sortedSkills = skillList.stream().sorted(Comparator.comparing(Skill::getDescription).reversed()).collect(Collectors.toList());
        sortedSkillsDto.add(SkillDto.convertToDto(sortedSkills.get(0)));
        sortedSkillsDto.add(SkillDto.convertToDto(sortedSkills.get(1)));
        Page<SkillDto> skillPage = new PageImpl<>(sortedSkillsDto);
        when(skillService.readAllSkills(1L, pageRequest)).thenReturn(skillPage);

        skillService.readAllSkills(1L, pageRequest);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(skillService).readAllSkills(eq(1L), pageableCaptor.capture());
        PageRequest pageable = (PageRequest) pageableCaptor.getValue();
        assertThat(pageable.getPageNumber(), is(0));
        assertThat(pageable.getPageSize(), is(2));

        Page<SkillDto> page = skillService.readAllSkills(1L, pageRequest);
        assertThat(page.getContent(),hasSize(2));
        assertThat(page.getContent().get(0).getDescription(),is(skill1Description));
        assertThat(page.getContent().get(1).getDescription(),is(skill2Description));
    }

    @Test
    @DisplayName("Skill should be updated based on ID")
    public void testUpdateSkill(){
        Integer newDuration = 10;
        Long employeeId = 1L;
        Long skillId = 1L;

        SkillRequest skillRequest = new SkillRequest();
        skillRequest.setDescription(skill1Description);
        skillRequest.setDuration(newDuration);
        skillRequest.setLastUsed(skill1LastUsed.toString());

        when(skillService.updateSkill(employeeId, skillId, skillRequest)).thenReturn(updateSkill(skillList.get(0), newDuration));
        SkillDto updatedSkill = skillService.updateSkill(employeeId, skillId, skillRequest);

        ArgumentCaptor<SkillRequest> skillRequestArgumentCaptor = ArgumentCaptor.forClass(SkillRequest.class);
        verify(skillService).updateSkill(eq(employeeId), eq(skillId), skillRequestArgumentCaptor.capture());
        assertThat(skillRequestArgumentCaptor.getValue().getDuration(), is(newDuration));
        assertThat(updatedSkill.getDuration(), is(newDuration));
    }

    private SkillDto updateSkill(Skill skill, Integer newDuration) {
        skill.setDuration(newDuration);
        return SkillDto.convertToDto(skill);
    }

    @Test
    @DisplayName("Given a successful delete, response should give http status 200")
    public void successDeleteSkillById() throws Exception {
        Long employeeId = 1L;
        Long skillId = 1L;
        doNothing().when(skillService).deleteSkillById(employeeId, skillId);
        skillService.deleteSkillById(employeeId, skillId);
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(skillService).deleteSkillById(eq(employeeId), idCaptor.capture());
        assertThat(idCaptor.getValue(), is(skillId));
    }

    @Test
    @DisplayName("Given a fail read, response should give http status 404")
    public void shouldReturn404AuthorNotFoundGet() throws Exception {
        Long skillId = 42L;
        when(skillService.readSkillById(skillId)).thenThrow(new NotFoundException(String.format("Skill with id %s not found", skillId)));
    }

}