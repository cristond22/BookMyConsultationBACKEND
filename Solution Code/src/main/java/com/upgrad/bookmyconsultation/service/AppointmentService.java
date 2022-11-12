package com.upgrad.bookmyconsultation.service;

import com.upgrad.bookmyconsultation.entity.Appointment;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.exception.ResourceUnAvailableException;
import com.upgrad.bookmyconsultation.exception.SlotUnavailableException;
import com.upgrad.bookmyconsultation.repository.AppointmentRepository;
import com.upgrad.bookmyconsultation.repository.UserRepository;
import com.upgrad.bookmyconsultation.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {



	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;


	public String appointment(Appointment appointment) throws SlotUnavailableException
			, InvalidInputException{
			ValidationUtils.validate(appointment);
		  var appointmentExist = appointmentRepository.findByDoctorIdAndTimeSlotAndAppointmentDate(
					appointment.getDoctorId(),appointment.getTimeSlot(),appointment.getAppointmentDate());
			if(appointmentExist != null){
				 throw new SlotUnavailableException();
			}
			appointmentRepository.save(appointment);
			return appointment.getAppointmentId();
	}

	public Appointment getAppointment(String appointmentId) throws ResourceUnAvailableException{
		var appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow( () -> new ResourceUnAvailableException());
		return appointment;
	}

	public List<Appointment> getAppointmentsForUser(String userId) {
		return appointmentRepository.findByUserId(userId);
	}
}
