package com.candyseo.mearound.service.user;

import com.candyseo.mearound.model.dto.user.UserDevice;

public interface UserDeviceService {
    
    public boolean setUserActiveDevice(String userIdentifier, String deviceIdentifier);

    public UserDevice getUserDevice(String userIdentifier);

}
