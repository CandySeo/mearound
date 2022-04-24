package com.candyseo.mearound.service.user;

import com.candyseo.mearound.model.dto.user.User;

public interface UserService {
    
    String regist(User user);

    User get(String userId);
}
