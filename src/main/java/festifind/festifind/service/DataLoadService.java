package festifind.festifind.service;

import festifind.festifind.entity.Event;
import festifind.festifind.entity.Genre;
import festifind.festifind.entity.Organization;
import festifind.festifind.entity.Region;
import festifind.festifind.repository.event.EventRepository;
import festifind.festifind.repository.GenreRepository;
import festifind.festifind.repository.OrganizationRepository;
import festifind.festifind.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@RequiredArgsConstructor
@Service
public class DataLoadService {

    private final EventRepository eventRepository;
    private final GenreRepository genreRepository;
    private final OrganizationRepository organizationRepository;
    private final RegionRepository regionRepository;

    public void fetchAndSaveDataFromApi() throws IOException {
        String apiUrl = buildApiUrl(); //url을 build하여 apiurl에 저장
        String jsonData = fetchDataFromApi(apiUrl);  //api를 호출하여 데이터를 받아와 string 형태로 반환
        saveDataToDatabase(jsonData);
    }

    private String buildApiUrl() throws UnsupportedEncodingException {

        // 1. URL을 만들기 위한 StringBuilder.
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/

        // 2. 오픈 API의 요청 규격에 맞는 파라미터 생성
        String apiKey = System.getenv("API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("API key not found");
        }

        urlBuilder.append("/" + URLEncoder.encode(apiKey, "UTF-8")); /*인증키*/
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("culturalEventInfo", "UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode("200", "UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        return urlBuilder.toString();
    }

    private String fetchDataFromApi(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);

        // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 5. 통신을 위한 메소드 SET (Get 요청)
        conn.setRequestMethod("GET");

        // 6. 통신을 위한 Content-type SET. (json으로 설정해야됨)
        conn.setRequestProperty("Content-type", "application/json");

        // 7. 통신 응답 코드 확인.
        System.out.println("Response Code " + conn.getResponseCode());

        // 8. 전달받은 데이터를 BufferedReader 객체로 저장. 오류가 날 경우 error 발생
        BufferedReader br;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
        }

        // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line);
        }

        br.close();
        conn.disconnect();
        return result.toString();
    }

    private void saveDataToDatabase(String jsonData) {

        try {
            // 1. 문자열 형태의 JSON을 파싱하기 위한 JSONParser 객체 생성.
            JSONParser parser = new JSONParser();
            // 2. 문자열을 JSON 형태로 JSONObject 객체에 저장.
            JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
            // 3. "culturalEventInfo" 키에 해당하는 값을 추출한 후,
            // 접근을 위해 culturalEventInfoObject로 저장
            JSONObject culturalEventInfoObject = (JSONObject) jsonObject.get("culturalEventInfo");
            // 4. "row" 키에 해당하는 값을 추출한 후, 접근을 위해 jsonArray로 저장
            JSONArray jsonArray = (JSONArray) culturalEventInfoObject.get("row");

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i); //json 객체 하나식 가져옴

                Genre genre = saveGenre(object);
                Region region = saveRegion(object);
                Organization organization = saveOrganization(object);
                saveEvent(object,genre, region, organization);
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Genre saveGenre(JSONObject object) {
        String genreName = (String) object.get("CODENAME");
        Genre genre = getOrCreateGenre(genreName);
        Genre savedGenre = genreRepository.save(genre);
        return savedGenre;
    }

    private Genre getOrCreateGenre(String genreName) {
        Genre genre = genreRepository.findGenreByName(genreName);
        if (genre == null) {
            genre = new Genre(genreName);
        }
        return genre;
    }

    private Region saveRegion(JSONObject object) {
        String regionName = (String) object.get("GUNAME");
        String longitude = (String) object.get("LOT");
        String latitude = (String) object.get("LAT");

        Region region = getOrCreateRegion(regionName, longitude, latitude);
        Region savedRegion = regionRepository.save(region);
        return savedRegion;
    }

    private Region getOrCreateRegion(String regionName, String longitude, String latitude ) {
        Region region = regionRepository.findRegionByName(regionName);
        if(region == null){
            region = new Region(regionName, longitude, latitude);
        }
        return region;
    }

    private Organization saveOrganization(JSONObject object) {
        String orgName = (String) object.get("ORG_NAME");
        String orgUrl = (String) object.get("ORG_LINK");

        Organization organization = getOrCreateOrganization(orgName, orgUrl);
        Organization savedOrganization = organizationRepository.save(organization);
        return savedOrganization;
    }

    private Organization getOrCreateOrganization(String orgName, String orgUrl){
        Organization organization = organizationRepository.findOrganizationByName(orgName);
        if(organization == null){
            organization = new Organization(orgName, orgName);
        }

        return organization;
    }

    private void saveEvent(JSONObject object, Genre genre, Region region, Organization organization) {
        String title = (String) object.get("TITLE");
        String place = (String) object.get("PLACE");
        String startDate = (String) object.get("STRTDATE");
        String endDate = (String) object.get("END_DATE");
        String userTarget = (String) object.get("USE_TRGT");
        String userFee = (String) object.get("USE_FEE");
        String player = (String) object.get("PLAYER");
        String program = (String) object.get("PROGRAM");
        String mainImg = (String) object.get("MAIN_IMG");

        Event event = new Event(title, place, startDate, endDate, userTarget, userFee, player, program, mainImg, genre, region, organization);
        eventRepository.save(event);
    }
}


