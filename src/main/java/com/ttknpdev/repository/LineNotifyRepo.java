package com.ttknpdev.repository;

import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;

public interface LineNotifyRepo {
    LinkedHashMap<String, Object> sendLineNotifyMessage(String message) throws Exception;
    LinkedHashMap<String, Object> sendLineNotifyMessageSticker(String message, int stickerPackageId, int stickerId) throws Exception;
    LinkedHashMap<String, Object> sendLineNotifyMessageImageUrl(String message, String imageUrl) throws Exception;
    LinkedHashMap<String, Object> sendLineNotifyMessageImageFile(String message,MultipartFile file) throws Exception;
}
