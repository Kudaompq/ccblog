package top.kudaompq.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import top.kudaompq.exception.BadRequestException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

/**
 * @description: 图片下载工具类
 * @author: kudaompq
 * @date: 2022/2/10 12:31
 * @version: v1.0
 */

@Component
public class ImageUtils {

    private static RestTemplate restTemplate = new RestTemplate();

    //GitHub上传文件API
    private static final String githubUploadApi = "https://api.github.com/repos/%s/%s/contents%s/%s";
    //GitHub上传文件API
    private static final String cdnUrl4Github = "https://cdn.jsdelivr.net/gh/%s/%s%s/%s";

    //服务访问地址，用于返回图片url
    private static String serverUploadPath;
    //服务器文件上传路径
    private static String serverUrl;
    //GitHub token
    private static String githubToken;
    //GitHub用户名
    private static String githubUsername;
    //GitHub仓库名
    private static String githubRepos;
    //GitHub仓库路径
    private static String githubReposPath;

    @Value("${upload.path}")
    public void setServerUploadPath(String serverUploadPath) {
        this.serverUploadPath = serverUploadPath;
    }

    @Value("${custom.url.api}")
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Value("${upload.github.token}")
    public void setGithubToken(String githubToken) {
        this.githubToken = githubToken;
    }

    @Value("${upload.github.username}")
    public void setGithubUsername(String githubUsername) {
        this.githubUsername = githubUsername;
    }

    @Value("${upload.github.repos}")
    public void setGithubRepos(String githubRepos) {
        this.githubRepos = githubRepos;
    }

    @Value("${upload.github.repos-path}")
    public void setGithubReposPath(String githubReposPath) {
        this.githubReposPath = githubReposPath;
    }

    @AllArgsConstructor
    @Getter
    static class ImageResource{
        byte[] data;
        // 图片扩展名
        String type;
    }

    public static ImageResource getImageByRequest(String url){
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(url, byte[].class);
        if ("image".equals(responseEntity.getHeaders().getContentType().getType())){
            return new ImageResource(responseEntity.getBody(),responseEntity.getHeaders().getContentType().getSubtype());
        }
        throw new BadRequestException("response contentType unlike image");
    }

    public static String saveImage(ImageResource image) throws IOException {
        File file = new File(serverUploadPath);
        if (!file.exists()){
            file.mkdirs();
        }
        String fileName = UUID.randomUUID()+"."+image.getType();
        FileOutputStream fileOutputStream = new FileOutputStream(serverUploadPath + fileName);
        fileOutputStream.write(image.getData());
        fileOutputStream.close();
        return serverUrl + "/image/" + fileName;
    }

    public static String pushGithub(ImageResource image){
        String fileName = UUID.randomUUID() + "." + image.getType();
        String url = String.format(githubUploadApi,githubUsername,githubRepos,githubReposPath,fileName);
        String imgBase64 = Base64.getEncoder().encodeToString(image.getData());
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", Collections.singletonList("token "+githubToken));
        HashMap<String, String> body = new HashMap<>();
        body.put("message","Add files CCBlog");
        body.put("content",imgBase64);
        HttpEntity httpEntity = new HttpEntity(body, headers);
        restTemplate.put(url,httpEntity);

        return String.format(cdnUrl4Github, githubUsername, githubRepos, githubReposPath, fileName);
    }
}
