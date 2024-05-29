//package com.gateway.statistics_collector;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.gateway.Constants;
//import com.gateway.statistics_collector.models.RequestStatisticData;
//import com.gateway.statistics_collector.models.StatisticDataDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class StatisticsControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private StatisticsCollector statisticsCollector;
//
//    @Test
//    public void testGetStatistics() throws Exception {
//        String service_id = "service1";
//        long time_interval = 1000L;
//
//        StatisticDataDTO data1 = new StatisticDataDTO(Constants.SERVICE_TWO, 58);
//        StatisticDataDTO data2 = new StatisticDataDTO(Constants.SERVICE_ONE, 12);
//
//        List<StatisticDataDTO> recods = new ArrayList<>();
//        recods.add(data1);
//
//        when(statisticsCollector.getAllRecords()).thenReturn(recods);
//
//        mockMvc.perform(get("/statistics/EXT_SERVICE_2/24", service_id, time_interval))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].serviceName").value(Constants.SERVICE_TWO))
//                .andExpect(jsonPath("$[0].count").value(58));
//    }
//}