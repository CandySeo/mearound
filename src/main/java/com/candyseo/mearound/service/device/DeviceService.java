package com.candyseo.mearound.service.device;

import java.util.List;
import java.util.Set;

import com.candyseo.mearound.exception.DataNotFoundException;
import com.candyseo.mearound.model.dto.device.Device;

public interface DeviceService {
    
    /***
     * 새로운 Device를 저장하고 새로 생성된 UUID 를 String으로 변환하여 반환한다.
     * @param device
     * @return 등록된 Device의 생성된 UUID의 {@link UUID#toString()} 한 결과 
     * @throws IllegalArgumentException
     */
    public String regist(Device device) throws IllegalArgumentException;

    /***
     * identifier를 UUID로 변환하여 DeviceRepository 에서 DeviceEntity 를 조회하고, 결과를 Device로 변환하여 반환한다.
     * @param identifier : UUID 로 변환 가능한 DeviceEntity.identifier
     * @return : 조회한 Device 객체
     * @throws DataNotFoundException : {@param identifier} 에 조회되는 데이터가 없는 경우 발생
     */
    public Device get(String identifier) throws DataNotFoundException;

    /***
     * 
     * @param identifier
     * @return
     * @throws DataNotFoundException
     */
    public List<Device> findAll(Set<String> identifier) throws DataNotFoundException;

}
