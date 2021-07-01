package com.example.hardwarestore.item;

import com.example.hardwarestore.BaseControllerTest;
import com.example.hardwarestore.TestCreationFactory;
import com.example.hardwarestore.item.model.DTO.ItemDTO;
import com.example.hardwarestore.report.ReportService;
import com.example.hardwarestore.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


import static com.example.hardwarestore.UrlMapping.ITEMS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;


class ItemControllerTest extends BaseControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        itemController = new ItemController(itemService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }


    @Test
    void findAll() throws Exception {

        List<ItemDTO> itemDTOS = TestCreationFactory.listOf(ItemDTO.class);
        when(itemService.findAllDto()).thenReturn(itemDTOS);

        ResultActions response = mockMvc.perform(get(ITEMS));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonContentToBe(itemDTOS));

    }


//    @Test
//    void create() throws Exception {
//        PatientDto patientDto = TestCreationFactory.object(PatientDto.class);
//
//        when(patientService.create(patientDto)).thenReturn(patientDto);
//
//        ResultActions result = performPostWithRequestBody(PATIENT, patientDto);
//        result.andDo(MockMvcResultHandlers.print())
//                .andExpect(status().isOk())
//                .andExpect(jsonContentToBe(patientDto));
//    }
}