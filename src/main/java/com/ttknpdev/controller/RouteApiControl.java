package com.ttknpdev.controller;

import com.ttknpdev.logging.Logback;
import com.ttknpdev.repository.LineNotifyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;

@RestController
@RequestMapping(value = "/line")
public class RouteApiControl {
    private LineNotifyRepo lineNotifyRepo;
    private Logback logback;

    @Autowired
    public RouteApiControl(LineNotifyRepo lineNotifyRepo) {
        this.lineNotifyRepo = lineNotifyRepo;
        logback = new Logback(RouteApiControl.class);
    }

    @GetMapping
    private ResponseEntity testServer() {
        return ResponseEntity.ok("8080 port is running");
    }

    @GetMapping("/message")
    private ResponseEntity<LinkedHashMap<String, Object>> sendMessage(@RequestBody(required = false) LinkedHashMap<String, String> linkedHashMap) throws Exception {
        logback.log.info("requested {}", "localhost:8080/line/message");
        String message = linkedHashMap.get("message");
        LinkedHashMap<String, Object> response = lineNotifyRepo.sendLineNotifyMessage(message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/message/{condition}")
    private ResponseEntity<LinkedHashMap<String, Object>> sendMessage2(@PathVariable(required = false) int condition) throws Exception {
        String message = "";
        // for pretty
        switch (condition) {
            case 1: {
                message = "**********\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "** 7 AM , Time to wake up **\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************";
            }
            break;
            case 2: {
                message = "**********\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "****** 8 AM - 11 AM *********\n" +
                        "****** Time to practice *******\n" +
                        "************ Maths ************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************";
            }
            break;
            case 3: {
                message = "**********\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "******* 11 AM - 13 PM *******\n" +
                        "********** Break time *********\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************";
            }
            break;
            case 4: {
                message = "**********\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "******* 13 PM - 14 PM *******\n" +
                        "****** Time to reading *******\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************";
            }
            break;
            case 5: {
                message = "**********\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "******* 14 PM - 18 PM *******\n" +
                        "****** Time to practice *******\n" +
                        "*********** Coding ************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************";
            }
            break;
            case 6: {
                message = "**********\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "******* 18 PM - 19 PM *******\n" +
                        "********** Break time *********\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************";
            }
            break;
            case 7: {
                message = "**********\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "******* 19 PM - 21 PM *******\n" +
                        "****** Time to practice *******\n" +
                        "************ Maths ************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************";
            }
            break;
            case 8: {
                message = "**********\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "******* 21 PM - 7 AM *******\n" +
                        "********** Break time *********\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************\n" +
                        "*********************************";
            }
            break;
            default:
                break;
        }
        LinkedHashMap<String, Object> response = lineNotifyRepo.sendLineNotifyMessage(message);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/message+sticker")
    private ResponseEntity<LinkedHashMap<String, Object>> sendMessageSticker(@RequestBody(required = false) LinkedHashMap<String, String> linkedHashMap) throws Exception {
        logback.log.info("requested {} and pass json on http body {}", "localhost:8080/line/message+sticker", linkedHashMap);
        String message = linkedHashMap.get("message");
        int stickerPackageId = Integer.parseInt(linkedHashMap.get("stickerPackageId"));
        int stickerId = Integer.parseInt(linkedHashMap.get("stickerId"));
        LinkedHashMap<String, Object> response = lineNotifyRepo.sendLineNotifyMessageSticker(message,stickerPackageId,stickerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/message+imageUrl")
    public ResponseEntity<LinkedHashMap<String, Object>> sendMessageImageUrl(@RequestBody(required = false) LinkedHashMap<String, String> linkedHashMap) throws Exception {
        logback.log.info("requested {} and pass json on http body {}", "localhost:8080/line/message+imageUrl", linkedHashMap);
        String message = linkedHashMap.get("message");
        String imageUrl = linkedHashMap.get("imageUrl"); // no url in pc
        LinkedHashMap<String, Object> response = lineNotifyRepo.sendLineNotifyMessageImageUrl(message,imageUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/message+imageFile")
    // *** send thu form-data on Postman
    public ResponseEntity<LinkedHashMap<String, Object>> sendMessageImageFile(@RequestParam String message,@RequestParam MultipartFile file) throws Exception {
        logback.log.info("requested {} and pass params message {} , file {}", "localhost:8080/line/message+imageFile", message , file);
        // MultipartFile resource [file] cannot be resolved to absolute file path
        LinkedHashMap<String, Object> response = lineNotifyRepo.sendLineNotifyMessageImageFile(message,file);
        return ResponseEntity.ok(response);
    }
}
