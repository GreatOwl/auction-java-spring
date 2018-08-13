package com.auction.andrew.auction.utility.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Someone Beat you to that bid")
public class Conflict extends RuntimeException {

}
