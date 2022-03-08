package com.workshop.projectmanagement.service;

import com.workshop.projectmanagement.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {
    private String usermanagementServiceBaseUrl;
    private RestTemplate restTemplate;

    public UserService(@Value("${usermanagement.user.baseUrl}")
            String usermanagementServiceBaseUrl,
            RestTemplate restTemplate ) {

        this.usermanagementServiceBaseUrl = usermanagementServiceBaseUrl;
        this.restTemplate = restTemplate;
    }

    public UserDto getByUserId(Integer id) {
        return restTemplate.getForObject(usermanagementServiceBaseUrl + "/" + id,
                UserDto.class);
    }

    public List<UserDto> getByUserIdList(List<Integer> idList){
        String userList= idList.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        UserDto[] userDtoArray = restTemplate.getForObject(usermanagementServiceBaseUrl + "/all/" + userList,
                UserDto[].class);
        return Arrays.asList(userDtoArray);
    }
}
