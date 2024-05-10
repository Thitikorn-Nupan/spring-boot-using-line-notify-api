package com.ttknpdev.service;

import com.ttknpdev.logging.Logback;
import com.ttknpdev.repository.LineNotifyRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;

@Service
public class LineNotifyService implements LineNotifyRepo {
    // **** Remember you can not access Properties if you are not injected by @Autowrite this Bean (LineNotifyRepo,LineNotifyService)
    @Value(value = "${line.notify.url}")
    private String url;
    @Value(value = "${line.notify.token}")
    private String token;
    // for requests
    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private Logback logback;
    // No HttpMessageConverter for java.util.LinkedHashMap,Map,... and content type "application/x-www-form-urlencoded"
    // only MultiValueMap,LinkedMultiValueMap work fine
    private HttpEntity<MultiValueMap<String, Object>> request;
    private MultiValueMap<String, Object> map;


    public LineNotifyService() {
        restTemplate = new RestTemplate();
        logback = new Logback(LineNotifyService.class);
        map = new LinkedMultiValueMap<>();
    }

    @Override
    public LinkedHashMap<String, Object> sendLineNotifyMessage(String message) throws Exception {
        map.clear();
        map.add("message", message);
        return callLineNotifyByMultiValueMap(map);
    }

    @Override
    public LinkedHashMap<String, Object> sendLineNotifyMessageSticker(String message, int stickerPackageId, int stickerId) throws Exception {
        map.clear();
        map.add("message", message);
        map.add("stickerPackageId", stickerPackageId);
        map.add("stickerId", stickerId);
        return callLineNotifyByMultiValueMap(map);
    }

    @Override
    public LinkedHashMap<String, Object> sendLineNotifyMessageImageUrl(String message, String imageUrl) throws Exception {
        map.clear();
        map.add("message", message);
        map.add("imageThumbnail", imageUrl);
        map.add("imageFullsize", imageUrl);
        return callLineNotifyByMultiValueMap(map);
    }



    @Override
    public LinkedHashMap<String, Object> sendLineNotifyMessageImageFile(String message,MultipartFile file) throws Exception {
        map.clear();
        map.add("message", message);
        map.add("imageFile", file.getResource());
        return callLineNotifyByMultiValueMap(map);
    }


    private LinkedHashMap<String, Object> callLineNotifyByMultiValueMap(MultiValueMap<String, Object> map) throws Exception {

        headers = new HttpHeaders(); // **** have to create new object when use method Always
        headers.add("Authorization", "Bearer " + token);

        logback.log.info("callLineNotifyByMultiValueMap(MultiValueMap) method is working");
        logback.log.info("map store : {}", map); // map store {message=hello}

        if (map.get("imageFile") != null) {
            headers.setContentType(MediaType.MULTIPART_FORM_DATA); //  for multipart/ form-data.
        } else {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            // Or You can set up like
            // headers.add("Content-Type", "application/x-www-form-urlencoded");
        }

        request = new HttpEntity<>(map, headers);

        // many ways to request by RestTemplate
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(url, HttpMethod.POST, request, LinkedHashMap.class);
        logback.log.info("response store : {}", response); // <200 OK OK,{status=200, message=ok},[Server:"nginx", Date:"Thu, 09 May 2024 06:15:35 GMT", Content-Type:"application/json", Transfer-Encoding:"chunked", Keep-Alive:"timeout=9", Vary:"Accept-Encoding", X-RateLimit-Limit:"1000", X-RateLimit-ImageLimit:"50", X-RateLimit-Remaining:"974", X-RateLimit-ImageRemaining:"50", X-RateLimit-Reset:"1715236089", X-Robots-Tag:"noindex, nofollow, nosnippet, noarchive"]>
        logback.log.info("response.getBody() store : {}", response.getBody()); // {status=200, message=ok}

        return response.getBody();
    }
}
