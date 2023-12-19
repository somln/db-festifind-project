package festifind.festifind.controller;

import festifind.festifind.service.DataLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class DataLoadController {

    private final DataLoadService dataLoadService;

    @PostMapping("/api/load")
    public void getApiData() throws IOException {
        dataLoadService.fetchAndSaveDataFromApi();;
    }
}