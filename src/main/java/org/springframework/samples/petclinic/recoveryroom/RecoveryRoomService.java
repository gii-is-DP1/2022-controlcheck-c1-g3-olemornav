package org.springframework.samples.petclinic.recoveryroom;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecoveryRoomService {
	
	private RecoveryRoomRepository recRepository;
	

	@Autowired
	public RecoveryRoomService(RecoveryRoomRepository recRepository) {
		this.recRepository = recRepository;
	}
	
    public List<RecoveryRoom> getAll(){
        return recRepository.findAll();
    }

    public List<RecoveryRoomType> getAllRecoveryRoomTypes(){
        return recRepository.findAllRecoveryRoomTypes();
    }

    public RecoveryRoomType getRecoveryRoomType(String typeName) {
        return recRepository.getRecoveryRoomType(typeName);
    }
    @Transactional(rollbackFor = DuplicatedRoomNameException.class)
    public RecoveryRoom save(RecoveryRoom p) throws DuplicatedRoomNameException {
    	List<RecoveryRoom> recRooms = getAll();
    	for(RecoveryRoom r : recRooms) {
            if (r.getName().equals(p.getName()) && r.getRoomType().equals(p.getRoomType()) ) {            	
            	throw new DuplicatedRoomNameException();
            }   
    	}
        	return  recRepository.save(p);

    	}
               
     
    }

   
