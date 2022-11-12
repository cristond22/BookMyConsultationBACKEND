package com.upgrad.bookmyconsultation.controller;

import com.upgrad.bookmyconsultation.entity.User;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.service.AppointmentService;
import com.upgrad.bookmyconsultation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserAdminController {

  @Autowired
  private UserService userService;

  @Autowired
  private AppointmentService appointmentService;

  @GetMapping(path = "/{id}")
  public ResponseEntity<User> getUser(
      @RequestHeader("authorization") String accessToken,
      @PathVariable("id") final String userUuid) {
    final User User = userService.getUser(userUuid);
    return ResponseEntity.ok(User);
  }

  @RequestMapping(
      value = "/register",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> createUser(@RequestBody final User createUserRequest)
      throws InvalidInputException {
    return ResponseEntity.ok(userService.register(createUserRequest));
  }

  @GetMapping("/{userId}/appointments")
  public ResponseEntity getAppointmentForUser(@PathVariable("userId") String userId) {
    return ResponseEntity.ok(appointmentService.getAppointmentsForUser(userId));
  }
}
