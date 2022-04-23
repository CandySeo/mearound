package com.candyseo.mearound.service;

import com.candyseo.mearound.model.dto.user.User;

public interface UserService {
    
    String regist(User user);

    User get(String userId);
}
