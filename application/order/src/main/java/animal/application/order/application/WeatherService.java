package animal.application.order.application;

import animal.application.order.dto.weather.WeatherInfoRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "WeatherService")
@Transactional(readOnly = true)
public class WeatherService {

    private final RestTemplate weatherRestTemplate;

    @Value("${weather.service.key}")
    String weatherServiceKey;

    @Cacheable(cacheNames = "WeatherInfo", key = "args[0]")
    public String getWeatherInfo(String date) {
        String requestURL = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?" +
            "serviceKey=" + weatherServiceKey +
            "&pageNo=1" +
            "&numOfRows=217" +
            "&dataType=JSON" +
            "&base_date=" + date +
            "&base_time=0500" +
            "&nx=63" +
            "&ny=124";
        log.info(requestURL);
        WeatherInfoRes info = new WeatherInfoRes();

        try {
            weatherRestTemplate.getInterceptors().add((request, body, execution) -> {
                ClientHttpResponse response = execution.execute(request, body);
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return response;
            });
            final HttpHeaders headers = new HttpHeaders();
            final HttpEntity<?> entity = new HttpEntity<>(headers);

            info = weatherRestTemplate.exchange(requestURL, HttpMethod.GET, entity, WeatherInfoRes.class).getBody();

            log.info(info.toString());
            log.info("날씨 정보 호출 성공!");

            return info.getInfos();
        } catch (Exception e) {
            log.error(e.getMessage());

        }
        return "ERROR";
    }

}