package org.springframework.samples.petclinic.recoveryroom;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recoveryroom")
public class RecoveryRoomController {
	
	private static final String VIEWS_RECOVERY_CREATE_OR_UPDATE_FORM = "recoveryroom/createOrUpdateRecoveryRoomForm";

	private final RecoveryRoomService recService;

	@Autowired
	public RecoveryRoomController(RecoveryRoomService recService) {
		this.recService = recService;
	}
	
	@GetMapping(value = "/create")
	public String initCreationForm(ModelMap model) {
		RecoveryRoom recoveryRoom = new RecoveryRoom();
		model.put("recoveryRoom", recoveryRoom);
		return VIEWS_RECOVERY_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/create")
	public String processCreationForm(@Valid RecoveryRoom recoveryRoom, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("recoveryRoom", recoveryRoom);
			return VIEWS_RECOVERY_CREATE_OR_UPDATE_FORM;
		}
		else {
                    try{
                    	this.recService.save(recoveryRoom);
                    }catch(DuplicatedRoomNameException ex){
                        result.rejectValue("name", "duplicate", "already exists");
                        return VIEWS_RECOVERY_CREATE_OR_UPDATE_FORM;
                    }
                    return "welcome";
		}
		}
}
